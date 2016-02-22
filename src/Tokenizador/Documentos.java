/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tokenizador;

import java.util.StringTokenizer;

/**
 *
 * @author nicolasgarciamunoz
 */
public class Documentos {
    private String nombreDocumento;
    private StringTokenizer tokens;

    public Documentos(String nombreDocumento, StringTokenizer tokens) {
        this.nombreDocumento = nombreDocumento;
        this.tokens = tokens;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public StringTokenizer getTokens() {
        return tokens;
    }
    
    
}
