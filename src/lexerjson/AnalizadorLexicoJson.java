/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexerjson;

import Clase.GramaticJson;
import Exception.TokenInvalidoException;
import static Lenguaje.LexerJson.*;
import static Lenguaje.TokenJson.*;
import java.util.ArrayList;

/**
 * Validacion del lenguaje Json
 * @author marco
 */
public class AnalizadorLexicoJson 
{
    /**
     * Genera un archivo con lexemas encontrados en la cadena pasada
     * @param cadenaEntrada
     * @param archivoSalida
     * @param linea
     * @return 
     * @throws java.io.IOException 
     */
   /* public static boolean GenerarArchivoResultadoJson(String cadenaEntrada ,File archivoSalida, int linea) throws IOException
    {        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSalida, true))) 
        {        
            String cadenaSalida;
            
            try
            {
                cadenaSalida = ObtenerTokenJson(cadenaEntrada);
                
                writer.write(cadenaSalida); // genera salida en texto
                writer.newLine(); // salto de linea
            }
            catch(TokenInvalidoException e)
            {
                writer.write(e.getMessage() + " Linea: " + linea); // genera salida en texto
                writer.newLine(); // salto de linea
                writer.close();
                return false;
            }      
        } 
        return true;
    }      */  
     
    private static int nroLinea = 0;
    
    /**
     * Obtiene la gramatica del lenguaje Json de una cadena
     * @param cadenaJson
     * @return Lexema de Json
     * @throws TokenInvalidoException 
     */
    public static ArrayList<GramaticJson> ObtenerGramaticaJson(String cadenaJson) throws TokenInvalidoException
    {
        String caracter;
        GramaticJson gramatica;
        ArrayList<GramaticJson> listaGramatic = new ArrayList<>();
        nroLinea++;
        
        for (int x=0; x<cadenaJson.length(); x++)
        {   
            caracter = String.valueOf(cadenaJson.charAt(x));
            
            //// ignora espacio en blanco
            if(caracter.equals(" "))
                continue;    
            
            //// controla si el token es un numero           
            if(Character.isDigit(cadenaJson.charAt(x)))
            { 
                int nroLetra = x;
                String stringNro = "";
                
                while(true)
                {   
                    if(!Character.isDigit(cadenaJson.charAt(nroLetra)) ||
                        cadenaJson.length() == nroLetra)
                    {
                        x = nroLetra;
                        gramatica = new GramaticJson();
                        gramatica.setLexema(stringNro);
                        gramatica.setToken(TOKEN_LITERAL_NUM);
                        gramatica.setTraductor(stringNro);
                        gramatica.setNroLinea(nroLinea);
                        
                        listaGramatic.add(gramatica);
                        caracter = String.valueOf(cadenaJson.charAt(x));
                        break;
                    }
                    
                    //// almacena el valor
                    stringNro += String.valueOf(cadenaJson.charAt(nroLetra));
                    
                    nroLetra++;    
                } 
                //continue;
            }              
            
            
            //// controla si el token es una cadena
            if(caracter.equals("\""))
            {
                try
                {
                    String cadena = "";
                    String letra;
                    int nroLetra = x + 1;
                    while(true)
                    {   
                        letra = String.valueOf(cadenaJson.charAt(nroLetra));
                        
                        if(letra.equals("\""))
                        { 
                            gramatica = new GramaticJson();
                            gramatica.setLexema(cadena);
                            gramatica.setToken(TOKEN_LITERAL_CADENA);
                            gramatica.setTraductor(cadena);
                            gramatica.setNroLinea(nroLinea);
                            
                            break;
                        }
                        if(cadenaJson.length() == nroLetra)
                            throw new TokenInvalidoException("Se esperaba un LITERAL_CADENA");
                        
                        //// almacena la cadena
                        cadena += cadenaJson.charAt(nroLetra);
                        
                        nroLetra++;    
                    }
                    x = nroLetra;
                    listaGramatic.add(gramatica);
                    continue;
                }
                catch(Exception e)
                {
                    throw new TokenInvalidoException("Se esperaba un LITERAL_CADENA");
                }
            }
            
            //// controla si el token es null
            if((caracter.toUpperCase()).equals("N"))
            {
                try
                {
                    String _null = cadenaJson.substring(x, x + 4);
                    
                    gramatica = new GramaticJson();
                    gramatica.setLexema(_null);
                    listaGramatic.add(reconocedorToken(gramatica));
                    
                    x += _null.length() -1;
                }
                catch(Exception e)
                {
                    throw new TokenInvalidoException("La cadena no pertenece al lenguaje");
                }
                
                continue;
            }

            //// controla si el token es TRUE
            if((caracter.toUpperCase()).equals("T"))
            {
                try
                {
                    String _true = cadenaJson.substring(x, x + 4);

                    gramatica = new GramaticJson();
                    gramatica.setLexema(_true);
                    listaGramatic.add(reconocedorToken(gramatica));
                    
                    x += _true.length() -1;
                }
                catch(Exception e)
                {
                    throw new TokenInvalidoException("La cadena no pertenece al lenguaje");
                }
                
                continue;
            }

            //// controla si el token es FALSE
            if((caracter.toUpperCase()).equals("F"))
            {
                try
                {
                    String _false = cadenaJson.substring(x, x + 5);
                    
                    gramatica = new GramaticJson();
                    gramatica.setLexema(_false);
                    listaGramatic.add(reconocedorToken(gramatica));
                    
                    x += _false.length() -1;
                }
                catch(Exception e)
                {
                    throw new TokenInvalidoException("La cadena no pertenece al lenguaje");
                }
                
                continue;
            }

            //// controla si el token es un caracter especial
            gramatica = new GramaticJson();
            gramatica.setLexema(caracter);
            listaGramatic.add(reconocedorToken(gramatica));   
        }
        
        //// si todo sale bien, se agrega fin de archivo
       /* gramatica = new GramaticJson();
        gramatica.setLexema("EOF");
        gramatica.setToken(TOKEN_EOF);
        gramatica.setTraductor("EOF");
        gramatica.setNroLinea(nroLinea);
        listaGramatic.add(gramatica);*/
        
        return listaGramatic;
    }
        
    
    
    private static GramaticJson reconocedorToken(GramaticJson gram) throws TokenInvalidoException
    {
        GramaticJson gramatica = new GramaticJson();
        gramatica = gram;
        
        switch(gramatica.getLexema())
        {
            case LEXER_L_CORCHETE:
                gramatica.setToken(TOKEN_L_CORCHETE);
                gramatica.setNroLinea(nroLinea);
                gramatica.setTraductor(LEXER_L_CORCHETE);
                break;
                
            case LEXER_R_CORCHETE:
                gramatica.setToken(TOKEN_R_CORCHETE);
                gramatica.setNroLinea(nroLinea);
                gramatica.setTraductor(LEXER_R_CORCHETE);
                break;
                
            case LEXER_L_LLAVE:
                gramatica.setToken(TOKEN_L_LLAVE);
                gramatica.setNroLinea(nroLinea);
                gramatica.setTraductor(LEXER_L_LLAVE);
                break;
                
            case LEXER_R_LLAVE:
                gramatica.setToken(TOKEN_R_LLAVE);
                gramatica.setNroLinea(nroLinea);
                gramatica.setTraductor(LEXER_R_LLAVE);
                break;
                
            case LEXER_COMA:
                gramatica.setToken(TOKEN_COMA);
                gramatica.setNroLinea(nroLinea);
                gramatica.setTraductor(LEXER_COMA);
                break;
                
            case LEXER_DOS_PUNTOS:
                gramatica.setToken(TOKEN_DOS_PUNTOS);
                gramatica.setNroLinea(nroLinea);
                gramatica.setTraductor("=");
                break;
                
            case LEXER_PR_TRUE:
                gramatica.setToken(TOKEN_PR_TRUE);
                gramatica.setNroLinea(nroLinea);
                gramatica.setTraductor(LEXER_PR_TRUE);
                break;
                
            case LEXER_PR_FALSE:
                gramatica.setToken(TOKEN_PR_FALSE);
                gramatica.setNroLinea(nroLinea);
                gramatica.setTraductor(LEXER_PR_FALSE);
                break;    
            
            case LEXER_PR_NULL:
                gramatica.setToken(TOKEN_PR_NULL);
                gramatica.setNroLinea(nroLinea);
                gramatica.setTraductor(LEXER_PR_NULL);
                break;      
                
            default:
                throw new TokenInvalidoException("La cadena no pertenece al lenguaje");
        }    

        return gramatica;
    }    
}
