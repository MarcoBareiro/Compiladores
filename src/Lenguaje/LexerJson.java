/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lenguaje;

/**
 * 
 * @author marco
 */
public class LexerJson 
{
    public static final String LEXER_L_CORCHETE = "[";
    public static final String LEXER_R_CORCHETE = "]";
    
    public static final String LEXER_L_LLAVE = "{";
    public static final String LEXER_R_LLAVE = "}";
    
    public static final String LEXER_COMA = ",";
    public static final String LEXER_DOS_PUNTOS = ":";
    
    public static final String LEXER_LITERAL_CADENA = ""; 
    public static final String LEXER_LITERAL_NUM = "123456789";
    
    public static final String LEXER_PR_TRUE = "TRUE"; 
    public static final String LEXER_PR_FALSE = "FALSE"; 
    public static final String LEXER_PR_NULL = "NULL"; 
    
    public static final String LEXER_EOF = "EOF";
}
