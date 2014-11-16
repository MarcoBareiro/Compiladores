/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompiladorJson;

import Clase.GramaticJson;
import Exception.TokenInvalidoException;
import ParserJson.AnalizadorSintactico;
import Traductor.TraductorXML;
import java.util.ArrayList;
import lexerjson.AnalizadorLexicoJson;

/**
 *
 * @author marco
 */
public class CompiladorJson
{
    /**
     * Verifica que la cadena pertenece al lenguaje Json
     * @param cadenaJson
     * @return lista de gramatica del lenguaje Json
     * @throws TokenInvalidoException 
     */
    public static ArrayList<GramaticJson> AnalizadorLexico(String cadenaJson) throws TokenInvalidoException
    {
        return AnalizadorLexicoJson.ObtenerGramaticaJson(cadenaJson);
    }  
    
    /**
     * Verifica que el leguaje Json este correcto sintacticamente
     * @param listGramatica
     * @return true o false
     */
    public static boolean AnalizadorSintactico(ArrayList<GramaticJson> listGramatica)
    {
        return AnalizadorSintactico.VerificarSintaxisJson(listGramatica);
    } 
    
    /**
     * Traduce de Json a XML
     * @param listaGramatica
     * @return Lista de etiquetas de XML
     */
    public static ArrayList GeneradorXML(ArrayList<GramaticJson> listaGramatica)
    {
        return TraductorXML.JsonToXML(listaGramatica);
    } 
}
