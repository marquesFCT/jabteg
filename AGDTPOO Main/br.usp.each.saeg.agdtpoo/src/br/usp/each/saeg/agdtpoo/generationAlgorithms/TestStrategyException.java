
package br.usp.each.saeg.agdtpoo.generationAlgorithms;

public class TestStrategyException extends Exception {

    public TestStrategyException(String msg){                
        super(msg);        
    }
    
    public TestStrategyException(Throwable  exception){                
        super(exception);        
    }
}