
package br.usp.each.saeg.agdtpoo.entity;

public class TestDataGenerationToolException extends Exception {

    public TestDataGenerationToolException(String msg){                
        super("TestDataGenerationToolException: " + msg);        
    }
    
    public TestDataGenerationToolException(Throwable  exception){                
        super(exception);        
    }
}