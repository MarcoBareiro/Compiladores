/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParserJson;

import Clase.Accion;
import static Lenguaje.PanicMode.*;
import static Lenguaje.TipoAccion.*;
import static Lenguaje.ProducionJson.*;
import static Lenguaje.TokenJson.*;
import java.util.ArrayList;

/**
 *
 * @author marco
 */
public class TablaParsing 
{   
    /**
     * Obtiene un array de producciones segun el lexema solicitado
     * @param produccion
     * @param token
     * @return ArrayDeProducciones
     */
    public static ArrayList<Accion> obtenerAccion(String produccion, String token)
    {
        ArrayList<Accion> listaAccion = new ArrayList<>();
        Accion accion;
        
        switch(produccion)
        {
            case PROD_JSONML:
                if(TOKEN_L_CORCHETE.equals(token) || TOKEN_LITERAL_CADENA.equals(token))
                {
                    accion = new Accion(PROD_ELEMENT, PRODUCCION);
                    listaAccion.add(0, accion);
                    accion = new Accion(TOKEN_EOF, TOKEN);
                    listaAccion.add(1, accion);
                    break;
                }
                else
                {
                    // TODO: error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                }   
            
            case PROD_ELEMENT:
                if(TOKEN_L_CORCHETE.equals(token))
                {
                    accion = new Accion(TOKEN_L_CORCHETE, TOKEN);
                    listaAccion.add(0, accion);
                    accion = new Accion(PROD_TAG_NAME, PRODUCCION);
                    listaAccion.add(1, accion);
                    accion = new Accion(PROD_ELEMENT_COMA, PRODUCCION);
                    listaAccion.add(2, accion);
                    accion = new Accion(TOKEN_R_CORCHETE, TOKEN);
                    listaAccion.add(3, accion);
                    break;
                }
                if(TOKEN_LITERAL_CADENA.equals(token))
                {
                    accion = new Accion(TOKEN_LITERAL_CADENA, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                else
                {
                    // TODO: error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                } 
            
            case PROD_ELEMENT_COMA:
                if(TOKEN_COMA.equals(token))
                {
                    accion = new Accion(TOKEN_COMA, TOKEN);
                    listaAccion.add(0, accion);
                    accion = new Accion(PROD_ELEMENT_PRE, PRODUCCION);
                    listaAccion.add(1, accion);
                    break;
                }
                if(TOKEN_R_CORCHETE.equals(token))
                {
                    //// match
                    accion = new Accion(POP, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                else
                {
                    // TODO: error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                } 
            
            case PROD_ELEMENT_PRE:
                if(TOKEN_L_CORCHETE.equals(token) || TOKEN_LITERAL_CADENA.equals(token))
                {
                    accion = new Accion(PROD_ELEMENT_LIST, PRODUCCION);
                    listaAccion.add(0, accion);
                    break;
                }
                if(TOKEN_L_LLAVE.equals(token))
                {
                    accion = new Accion(PROD_ATRIBUTES, PRODUCCION);
                    listaAccion.add(0, accion);
                    accion = new Accion(PROD_ELEMENT_PRE_ATRIBUTES, PRODUCCION);
                    listaAccion.add(1, accion);
                    
                    break;
                }
                else
                {
                    // TODO: error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                } 
                
            case PROD_ELEMENT_PRE_ATRIBUTES:
                if(TOKEN_COMA.equals(token))
                {
                    accion = new Accion(TOKEN_COMA, TOKEN);
                    listaAccion.add(0, accion);
                    accion = new Accion(PROD_ELEMENT_LIST, PRODUCCION);
                    listaAccion.add(1, accion);
                    
                    break;
                }
                if(TOKEN_R_CORCHETE.equals(token))
                {
                    //// match
                    accion = new Accion(POP, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                else
                {
                    // TODO: error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                } 
                
            case PROD_TAG_NAME:
                if(TOKEN_LITERAL_CADENA.equals(token))
                {
                    accion = new Accion(TOKEN_LITERAL_CADENA, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                else
                {
                    // TODO: error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                } 
                
            case PROD_ATRIBUTES:
                if(TOKEN_L_LLAVE.equals(token))
                {
                    accion = new Accion(TOKEN_L_LLAVE, TOKEN);
                    listaAccion.add(0, accion);
                    accion = new Accion(PROD_ATRIBUTES_PRE, PRODUCCION);
                    listaAccion.add(1, accion);
                    break;
                }
                else
                {
                    // TODO: error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                } 
                
            case PROD_ATRIBUTES_PRE:
                if(TOKEN_R_LLAVE.equals(token))
                {
                    accion = new Accion(TOKEN_R_LLAVE, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                if(TOKEN_LITERAL_CADENA.equals(token))
                {
                    accion = new Accion(PROD_ATRIBUTES_LIST, PRODUCCION);
                    listaAccion.add(0, accion);
                    accion = new Accion(TOKEN_R_LLAVE, TOKEN);
                    listaAccion.add(1, accion);
                    break;
                }
                else
                {
                    // TODO: error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                } 
                
            case PROD_ATRIBUTES_LIST:
                if(TOKEN_LITERAL_CADENA.equals(token))
                {
                    accion = new Accion(PROD_ATRIBUTE, PRODUCCION);
                    listaAccion.add(0, accion);
                    accion = new Accion(PROD_ATRIBUTES_LIST_PRE, PRODUCCION);
                    listaAccion.add(1, accion);
                    break;
                }
                else
                {
                    // TODO: error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                } 
                
            case PROD_ATRIBUTES_LIST_PRE:
                if(TOKEN_COMA.equals(token))
                {
                    accion = new Accion(TOKEN_COMA, TOKEN);
                    listaAccion.add(0, accion);
                    accion = new Accion(PROD_ATRIBUTES_LIST_PRE, PRODUCCION);
                    listaAccion.add(1, accion);
                    break;
                }
                if(TOKEN_R_LLAVE.equals(token))
                {
                    //// match
                    accion = new Accion(POP, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                else
                {
                    //// error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                } 
                
            case PROD_ATRIBUTE:
                if(TOKEN_LITERAL_CADENA.equals(token))
                {
                    accion = new Accion(PROD_ATRIBUTE_NAME, PRODUCCION);
                    listaAccion.add(0, accion);
                    accion = new Accion(TOKEN_DOS_PUNTOS, TOKEN);
                    listaAccion.add(1, accion);
                    accion = new Accion(PROD_ATRIBUTE_VALUE, PRODUCCION);
                    listaAccion.add(2, accion);
                    break;
                }
                if(TOKEN_R_LLAVE.equals(token))
                {
                    //// match
                    accion = new Accion(POP, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                else
                {
                    // TODO: error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                } 
            
            case PROD_ATRIBUTE_NAME:
                if(TOKEN_LITERAL_CADENA.equals(token))
                {
                    accion = new Accion(TOKEN_LITERAL_CADENA, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                else
                {
                    //// error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                }    
                
            case PROD_ATRIBUTE_VALUE:
                if(TOKEN_LITERAL_CADENA.equals(token))
                {
                    accion = new Accion(TOKEN_LITERAL_CADENA, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                if(TOKEN_LITERAL_NUM.equals(token))
                {
                    accion = new Accion(TOKEN_LITERAL_NUM, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                if(TOKEN_PR_TRUE.equals(token))
                {
                    accion = new Accion(TOKEN_PR_TRUE, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                if(TOKEN_PR_FALSE.equals(token))
                {
                    accion = new Accion(TOKEN_PR_FALSE, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                if(TOKEN_PR_NULL.equals(token))
                {
                    accion = new Accion(TOKEN_PR_NULL, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }
                else
                {
                    //// error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);                    
                    break;
                }    
            
            case PROD_ELEMENT_LIST:
                if(TOKEN_L_CORCHETE.equals(token) || TOKEN_LITERAL_CADENA.equals(token))
                {
                    accion = new Accion(PROD_ELEMENT, PRODUCCION);
                    listaAccion.add(0, accion);
                    accion = new Accion(PROD_ELEMENT_LIST_PRE, PRODUCCION);
                    listaAccion.add(1, accion);
                    break;
                }
                else
                {
                    // error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                }    
                
            case PROD_ELEMENT_LIST_PRE:
                if(TOKEN_COMA.equals(token))
                {
                    accion = new Accion(TOKEN_COMA, TOKEN);
                    listaAccion.add(0, accion);
                    accion = new Accion(PROD_ELEMENT, PRODUCCION);
                    listaAccion.add(1, accion);
                    accion = new Accion(PROD_ELEMENT_LIST_PRE, PRODUCCION);
                    listaAccion.add(2, accion);
                    break;
                }
                if(TOKEN_R_CORCHETE.equals(token))
                {
                    //// match
                    accion = new Accion(POP, TOKEN);
                    listaAccion.add(0, accion);
                    break;
                }    
                else
                {
                    /// error
                    accion = new Accion(SCAN, TOKEN);
                    listaAccion.add(0,accion);
                    break;
                }    
            
            //// caso especial
            default:
                accion = new Accion(SCAN, TOKEN);
                listaAccion.add(0,accion);
                break;
        }    
        
        return listaAccion;
    }        
    
       
}
