/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexerjson;

import Exception.TokenInvalidoException;
import static Lenguaje.TokenJson.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Validacion del lenguaje Json
 * @author marco
 */
public class LexerJson 
{
    /**
     * Genera un archivo con lexemas encontrados en la cadena pasada
     * @param cadenaEntrada
     * @param archivoSalida
     * @param linea
     * @return 
     * @throws java.io.IOException 
     */
    public static boolean GenerarArchivoResultadoJson(String cadenaEntrada ,File archivoSalida, int linea) throws IOException
    {        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSalida, true))) 
        {        
            String cadenaSalida;
            
            try
            {
                cadenaSalida = ObtenerLexemaJson(cadenaEntrada);
                
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
    }        
     
    
    /**
     * Obtiene el lexema del lenguaje Json de una cadena
     * @param cadenaJson
     * @param nroLinea
     * @return Lexema de Json
     * @throws TokenInvalidoException 
     */
    private static String ObtenerLexemaJson(String cadenaJson) throws TokenInvalidoException
    {
        String carcacter;
        StringBuilder lexema = new StringBuilder();
        
        for (int x=0; x<cadenaJson.length(); x++)
        {   
            carcacter = String.valueOf(cadenaJson.charAt(x));
            
            //// ignora espacio en blanco
            if(carcacter.equals(" "))
                continue;    
            
            //// controla si el token es un numero           
            if(Character.isDigit(cadenaJson.charAt(x)))
            { 
                int nroLetra = x + 1;
                while(true)
                {   
                    if(!Character.isDigit(cadenaJson.charAt(nroLetra)) ||
                        cadenaJson.length() == nroLetra)
                    {
                        x = nroLetra;
                        lexema.append(LITERAL_NUM);
                        continue;
                    }
                                                
                    nroLetra++;    
                } 
            }              
            
            
            //// controla si el token es una cadena
            if(carcacter.equals("\""))
            {
                try
                {
                    String letra;
                    int nroLetra = x + 1;
                    while(true)
                    {   
                        letra = String.valueOf(cadenaJson.charAt(nroLetra));
                        
                        if(letra.equals("\""))
                            break;
                        if(cadenaJson.length() == nroLetra)
                            throw new TokenInvalidoException("Se esperaba un LITERAL_CADENA");
                        
                        nroLetra++;    
                    }
                    x = nroLetra;
                    lexema.append(LITERAL_CADENA);
                    continue;
                }
                catch(Exception e)
                {
                    throw new TokenInvalidoException("Se esperaba un LITERAL_CADENA");
                }
            }
            
            //// controla si el token es null
            if((carcacter.toUpperCase()).equals("N"))
            {
                try
                {
                    String _null = cadenaJson.substring(x, x + 4);
                    lexema.append(reconocedorLexico(_null.toUpperCase()));
                    x += _null.length() -1;
                }
                catch(Exception e)
                {
                    throw new TokenInvalidoException("La cadena no pertenece al lenguaje");
                }
                
                continue;
            }

            //// controla si el token es TRUE
            if((carcacter.toUpperCase()).equals("T"))
            {
                try
                {
                    String _true = cadenaJson.substring(x, x + 4);
                    lexema.append(reconocedorLexico(_true.toUpperCase()));
                    x += _true.length() -1;
                }
                catch(Exception e)
                {
                    throw new TokenInvalidoException("La cadena no pertenece al lenguaje");
                }
                
                continue;
            }

            //// controla si el token es FALSE
            if((carcacter.toUpperCase()).equals("F"))
            {
                try
                {
                    String _false = cadenaJson.substring(x, x + 5);
                    lexema.append(reconocedorLexico(_false.toUpperCase()));
                    x += _false.length() -1;
                }
                catch(Exception e)
                {
                    throw new TokenInvalidoException("La cadena no pertenece al lenguaje");
                }
                
                continue;
            }

            //// controla si el token es un caracter especial
            lexema.append(reconocedorLexico(carcacter));   
        }
        
        return lexema.toString();
    }
        
    
    
    private static String reconocedorLexico(String token) throws TokenInvalidoException
    {
        String componenteLexico;
        
        switch(token)
        {
            case "[":
                componenteLexico = L_CORCHETE;
                break;
                
            case "]":
                componenteLexico = R_CORCHETE;
                break;
                
            case "{":
                componenteLexico = L_LLAVE;
                break;
                
            case "}":
                componenteLexico = R_LLAVE;
                break;
                
            case ",":
                componenteLexico = COMA;
                break;
                
            case ":":
                componenteLexico = DOS_PUNTOS;
                break;
                
            case "TRUE":
                componenteLexico = PR_TRUE;
                break;
                
            case "FALSE":
                componenteLexico = PR_FALSE;
                break;    
            
            case "NULL":
                componenteLexico = PR_NULL;
                break;      
                
            default:
                throw new TokenInvalidoException("La cadena no pertenece al lenguaje");
        }    

        return componenteLexico;
    }    
}
