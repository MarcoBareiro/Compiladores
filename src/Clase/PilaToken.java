/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clase;

import static Lenguaje.TipoAccion.*;
import static Lenguaje.ProducionJson.PROD_JSONML;
import java.util.Stack;

/**
 *
 * @author marco
 */
public class PilaToken 
{
    private Stack<Accion> pila; 

    public PilaToken() 
    {
        pila = new Stack<>();
    }
    
    public void inicializarPilaJson()
    {
        pila.push(new Accion(PROD_JSONML, PRODUCCION));
    }
    
    public void apilar(Accion accion)
    {
        pila.push(accion);
    } 
    
    public Accion desapilar()
    {
        Accion desapila = pila.pop();
        return desapila;
    }
    
    public Accion obtenerPrimero()
    {
        return pila.peek();   
    }        

    @Override
    public String toString() {
        return "PilaToken"+ pila.toString();
    }
    
    
}
