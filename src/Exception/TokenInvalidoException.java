/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author marco
 */
public class TokenInvalidoException extends Exception
{
    public TokenInvalidoException() {
        super("TOKEN_INVALIDO");               
    }
    
    public TokenInvalidoException(String mensaje) {
        super("TOKEN_INVALIDO: " + mensaje);               
    }

}
