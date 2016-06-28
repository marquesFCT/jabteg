
package br.usp.each.saeg.agdtpoo.entity;

public class ByteCodeFoundationException extends Exception {

    public ByteCodeFoundationException(String msg){                
        super(msg);        
    }
    
    public ByteCodeFoundationException(Throwable  exception){                
        super(exception);        
    }
}