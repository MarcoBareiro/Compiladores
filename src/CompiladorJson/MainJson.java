/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompiladorJson;

import Clase.GramaticJson;
import Exception.TokenInvalidoException;
import static Lenguaje.TokenJson.TOKEN_EOF;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author marco
 */
public class MainJson {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArrayList<GramaticJson> listaGramatica = null;
        
        try 
        {
            //// RUTA del input Json            
            String direccionArchivoInput = "C:/Users/marco/Documents/NetBeansProjects/input.txt";
            
            File archivoEntrada = new File(direccionArchivoInput);
            
            //String direccionArchivoOutput = "C:/Users/marco/Documents/NetBeansProjects/output.txt";
            //File archivoSalida = new File(direccionArchivoOutput);
            
            // lee el archivo de entrada
            try (Scanner entrada = new Scanner(archivoEntrada))
            {
                //// verifica verifica el lexema de Json y devuelve lista de TokenJson
                listaGramatica = obtenerListaTokenJson(entrada);
            }
            catch (FileNotFoundException e)
            {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            
            //// verifica que este sintacticamente correcto los tokenJson
            boolean sintaxis = CompiladorJson.AnalizadorSintactico(listaGramatica);

            if(!sintaxis)
            {   
                System.out.println("\n##### El lenguaje es sintacticamente incorrecto ####");
                System.exit(1);
            }
            
            System.out.println("\n##### El lenguaje es sintacticamente correcto ####");

            //// Traduce a XML - Retorna Etiquetas XML
            ArrayList ListaEtiquetaXML = CompiladorJson.GeneradorXML(listaGramatica);
            ImprimirEtiquetaXML(ListaEtiquetaXML);
            CrearArchivoXML(ListaEtiquetaXML);

        } catch (TokenInvalidoException ex) {
            System.out.println("lexema no reconocido: " + ex.getMessage());
            System.exit(1);
        }
        
    }
    
    
    public static ArrayList<GramaticJson> obtenerListaTokenJson(Scanner entrada) throws TokenInvalidoException
    {
        String cadenaEntrada;
        GramaticJson gramatic;
        ArrayList<GramaticJson> listaG = new ArrayList<>();
        ArrayList<GramaticJson> listaGramatica = new ArrayList<>();

        while (entrada.hasNext()) 
        { 
            cadenaEntrada = entrada.nextLine();
            //// Verifica los lexemas y tokens ingresados
            listaG = CompiladorJson.AnalizadorLexico(cadenaEntrada);

            for (GramaticJson g : listaG)
            {   
                gramatic = new GramaticJson();
                gramatic.setLexema(g.getLexema());
                gramatic.setToken(g.getToken());
                gramatic.setTraductor(g.getTraductor());
                gramatic.setNroLinea(g.getNroLinea());

                listaGramatica.add(gramatic);
            }
        }

        //// si no hay errores lexicos, se agrega fin de archivo
        gramatic = new GramaticJson();
        gramatic.setLexema("EOF");
        gramatic.setToken(TOKEN_EOF);
        gramatic.setTraductor("EOF");
        listaGramatica.add(gramatic);
                
        return listaGramatica; 
    }    
         
    
    public static void ImprimirEtiquetaXML(ArrayList EtiquetaXML)
    {
        EtiquetaXML.stream().forEach((xml) -> {
            System.out.println(xml);
        });
    } 
    
    public static void CrearArchivoXML(ArrayList EtiquetaXML)
    {
        String ruta = "C:\\Users\\marco\\Desktop\\XML.txt";
        
        try
        { 
            File archivo = new File(ruta);
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

            EtiquetaXML.stream().forEach((xml) -> {

                try {
                    bw.write((String) xml); 
                    bw.newLine();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    System.exit(1);
                }

            });
            
            bw.close();
        
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1);
        } 
        
    } 
    
    
}
