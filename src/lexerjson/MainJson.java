/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexerjson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        
        final String cadenaJson = "{ [ ]]{} :,:null true false}";
        //System.out.println(cadenaJson);
        
        String direccionArchivoInput = "C:/Users/marco/Documents/NetBeansProjects/input.txt";
        File archivoEntrada = new File(direccionArchivoInput);
        
        String direccionArchivoOutput = "C:/Users/marco/Documents/NetBeansProjects/output.txt";
        File archivoSalida = new File(direccionArchivoOutput);
          
        // lee el archivo de entrada
        try (Scanner entrada = new Scanner(archivoEntrada)) 
        {   
            String cadenaEntrada;
            int linea = 1;
            boolean lexerValido;
            while (entrada.hasNext()) 
            {
                cadenaEntrada = entrada.nextLine();
                lexerValido = LexerJson.GenerarArchivoResultadoJson(cadenaEntrada, archivoSalida, linea);
                if(!lexerValido)
                    System.exit(1);
                
                linea++;
            }
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println(e.getMessage());
        }
        catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
        }
        
    }
}
