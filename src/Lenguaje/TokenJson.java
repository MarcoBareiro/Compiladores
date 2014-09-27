/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lenguaje;

/**
 * Constantes de tokens reconocibles para Json
 * @author marco
 */
public class TokenJson 
{
    public static final String L_CORCHETE = "L_CORCHETE ";
    public static final String R_CORCHETE = "R_CORCHETE ";
    
    public static final String L_LLAVE = "L_LLAVE ";
    public static final String R_LLAVE = "R_LLAVE ";
    
    public static final String COMA = "COMA ";
    public static final String DOS_PUNTOS = "DOS_PUNTOS ";
    
    public static final String LITERAL_CADENA = "LITERAL_CADENA "; 
    public static final String LITERAL_NUM = "LITERAL_NUM ";
    
    public static final String PR_TRUE = "PR_TRUE "; 
    public static final String PR_FALSE = "PR_FALSE "; 
    public static final String PR_NULL = "PR_NULL "; 
    
    public static final String EOF = "EOF "; 
   
}

/**
 * 
 * Ejemplo de input válido:
 
  [ "data",
    [ "table",
        { "id" : "persona" },
    [ "column",
        { "id" : "ci" },
        "1495256"
    ],
    [ "column",
      { "id" : "nombre" },
      "Juan Perez"
    ]
  ],
  [ "sql",
    { "id" : "select_all" },
    "select * from persona order by ci"
  ]
]

* 
*/