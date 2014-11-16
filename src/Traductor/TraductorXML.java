/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Traductor;

import Clase.GramaticJson;
import static Lenguaje.TokenJson.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author marco
 */
public class TraductorXML 
{
    private static StringBuilder xml;
    private static Stack pilaCerradura;
    private static ArrayList TraducXML;

    
    public static ArrayList JsonToXML(ArrayList<GramaticJson> listaGramatica)
    {
        TraductorXML.TraducXML = new ArrayList();
        TraductorXML.pilaCerradura = new Stack();
        
        for (int i = 0; i < listaGramatica.size(); i++) 
        {
            GramaticJson gramaticJson = listaGramatica.get(i);
            
            xml = new StringBuilder();
            int nro = i;
            switch(gramaticJson.getToken())
            {
                case TOKEN_L_CORCHETE:
                    xml.append("<");
                    String nombreCorchete = listaGramatica.get(++nro).getTraductor();
                    xml.append(nombreCorchete); // espera una LITERAL_CADENA
                    
                    if(TOKEN_COMA.equals(listaGramatica.get(++nro).getToken()))
                    {
                        if(TOKEN_L_LLAVE.equals(listaGramatica.get(++nro).getToken()))
                        {
                            //// { "id" : "persona" }
                            xml.append(" ");
                            //// se espera un string
                            xml.append(listaGramatica.get(++nro).getTraductor());
                            //// se espera DOS_PUNTOS
                            xml.append(listaGramatica.get(++nro).getTraductor());
                            //// se espera un string
                            xml.append("\"").append(listaGramatica.get(++nro).getTraductor()).append("\"");
                            //// se espera un R_LLAVE
                            ++nro;
                            if(TOKEN_R_CORCHETE.equals(listaGramatica.get(++nro).getToken()))
                            {
                                i = --nro;
                            }
                            else
                            {    
                                //// se espera una coma
                                ++nro;
                                if(TOKEN_L_CORCHETE.equals(listaGramatica.get(nro).getToken()))
                                {
                                    i = --nro;
                                }
                            }    
                        }
                        else
                        {   // si recibe de nuevo un L_CORCHETE
                            i = --nro;
                        }
                    }
                    else
                    {   //// ["id"]                        
                        i = --nro;
                    }
                    
                    if(TOKEN_LITERAL_CADENA.equals(listaGramatica.get(nro).getToken()))
                    {   //// se espera un R_CORCHETE
                        i = --nro;
                    }
                    
                    xml.append(">");
                    TraducXML.add(xml.toString());
                    pilaCerradura.add("</"+ nombreCorchete + ">");

                    break;

                case TOKEN_R_CORCHETE:
                        TraducXML.add(pilaCerradura.pop());
                    break;

                case TOKEN_LITERAL_CADENA:
                        xml.append("\"").append(listaGramatica.get(nro).getTraductor()).append("\"");
                        TraducXML.add(xml.toString());
                    break; 

            }    
        }
        
        return TraducXML;
    }        
}
