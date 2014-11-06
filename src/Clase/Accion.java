/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clase;

/**
 *
 * @author marco
 */
public class Accion 
{
    private String accion;
    private String tipo;

    public Accion(String accion, String tipo) {
        this.accion = accion;
        this.tipo = tipo;
    }

    
    
    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return accion;
    }
    
    
}
