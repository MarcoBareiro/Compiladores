/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParserJson;

import Clase.PilaToken;
import Clase.Accion;
import Clase.GramaticJson;
import static Lenguaje.TipoAccion.*;
import static Lenguaje.PanicMode.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author marco
 */
public class AnalizadorSintactico 
{
    private static PilaToken pilaToken = new PilaToken();
    
    public static boolean VerificarSintaxisJson(ArrayList listaGramatica)
    {
        boolean sintaxis = true;
        
        //// inicializa para el lenguaje Json  
        pilaToken.inicializarPilaJson();
        
        //// verifica la sintaxis correcta del Json
        for (Iterator it = listaGramatica.iterator(); it.hasNext();) 
        {
            //// obtiene la gramatica
            GramaticJson gramaticJson = (GramaticJson) it.next();
            
            while(true)
            {
                //// obtiene el elemento de la pila
                Accion accion = pilaToken.obtenerPrimero();
                
                System.out.println(pilaToken.toString());
                
                //// si el primero de la pila es token
                if(TOKEN.equals(accion.getTipo()))
                {
                    //// pertenece a un TOKEN
                    if(POP.equals(accion.getAccion()))
                    {
                        //// llamar a match
                        match();
                        System.out.println("match");
                    }
                    else
                    {    
                        if(gramaticJson.getToken().equals(accion.getAccion()))
                        {
                            //// llamar a match
                            match();
                            System.out.println("match");
                            break;
                        }
                        else
                        {
                            sintaxis = false;
                            
                            if(SCAN.equals(pilaToken.obtenerPrimero().getAccion()))
                                match();
                            
                            String lexerError = gramaticJson.getLexema();
                            String tokenError = gramaticJson.getToken();
                            int lineaError = gramaticJson.getNroLinea();

                            //// error panic mode
                            do
                            {   
                                accion = pilaToken.obtenerPrimero();
                                
                                if(PRODUCCION.equals(accion.getTipo()))
                                {
                                    //// llamar a generar (consulta a la tabla de parsing)
                                    generar(gramaticJson.getToken(), accion.getAccion());
                                    
                                    if(SCAN.equals(pilaToken.obtenerPrimero().getAccion()))
                                        match();
                                } 
                                else
                                {   
                                    if(!it.hasNext())
                                        break;                                  
                                    
                                    gramaticJson = (GramaticJson) it.next();
                                } 
                            }
                            while(!gramaticJson.getToken().equals(pilaToken.obtenerPrimero().getAccion()));
                            
                            System.out.println("################ ERROR #################");
                            System.out.println("error cerca de: " + tokenError + " " + lexerError + ". Linea " + lineaError);
                            System.out.println("----------------------------------------");
                            
                            if(!it.hasNext())
                                break;
                            //break;
                        } 
                    }
                }
                //// si el primero de la pila es una produccion
                if(PRODUCCION.equals(accion.getTipo()))
                {
                    //// llamar a generar (consulta a la tabla de parsing)
                    generar(gramaticJson.getToken(), accion.getAccion());
                }    

            }    
       
        }
            
        return sintaxis;
    }        
    
    private static void generar(Object token, String accion)
    {
        //// obtiene la lista de acciones segun la produccion que esta en la cabecera de la lista
        ArrayList<Accion> listaAccion = TablaParsing.obtenerAccion(accion, (String)token);
        //// desapila la accion actual
        pilaToken.desapilar(); 
        
        //// apila las nuevas producciones
        for (int i = (listaAccion.size() -1); i >= 0; i--) 
        {
            pilaToken.apilar(listaAccion.get(i));
        }

    }        
    
    private static Accion match()
    {
        return pilaToken.desapilar();
    }        
}
