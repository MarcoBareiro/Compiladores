/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompiladorJson;

import Clase.GramaticJson;
import Exception.TokenInvalidoException;
import static Lenguaje.TokenJson.TOKEN_EOF;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marco
 */
public class MainJson {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArrayList<GramaticJson> listaG = new ArrayList<>();
        ArrayList<GramaticJson> lg = new ArrayList<>();

        try 
        {
            //final String cadenaJson = "[ \"data\"  [\"table\", { \"id\" : \"persona\" },  [\"column\", { \"id\" : \"ci\" },\"1495256\"],[ \"column\",{ \"id\" : \"nombre\" },\"Juan Perez\" ]  ],  [ \"sql\",    { \"id\" : \"select_all\" },    \"select * from persona order by ci\" ]]";
            
            String direccionArchivoInput = "C:/Users/marco/Documents/NetBeansProjects/input.txt";
            
            //// RUTA del input que se encuentra dentro del proyecto            
            //String direccionArchivoInput = "../input.txt";
            File archivoEntrada = new File(direccionArchivoInput);
            
            //String direccionArchivoOutput = "C:/Users/marco/Documents/NetBeansProjects/output.txt";
            //File archivoSalida = new File(direccionArchivoOutput);
            
            // lee el archivo de entrada
            try (Scanner entrada = new Scanner(archivoEntrada))
            {
                String cadenaEntrada;
                GramaticJson gramatic;
                
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
                
                
                //// verifica que este sintacticamente correcto los token
                boolean sintaxis = CompiladorJson.AnalizadorSintactico(listaGramatica);

                if(sintaxis)
                {   
                    System.out.println("\n##### El lenguaje es sintacticamente correcto ####");

                    //// proximamente
                    CompiladorJson.GeneradorXML(listaG);
                }
                else
                {
                    System.out.println("\n##### El lenguaje es sintacticamente incorrecto ####");
                }
                
            }
            catch (FileNotFoundException e)
            {
                System.out.println(e.getMessage());
            }
                        
            //// Verifica los lexemas y tokens ingresados
           /* listaGramatica = CompiladorJson.AnalizadorLexico(cadenaJson);
            
            //// verifica que este sintacticamente correcto los token
            boolean sintaxis = CompiladorJson.AnalizadorSintactico(listaGramatica);
            
            if(sintaxis)
            {   
                System.out.println("\n##### El lenguaje es sintacticamente correcto ####");
                
                //// proximamente
                CompiladorJson.GeneradorXML(listaGramatica);
            }
            else
            {
                System.out.println("\n##### El lenguaje es sintacticamente incorrecto ####");
            }*/
            

        } catch (TokenInvalidoException ex) {
            Logger.getLogger(MainJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
