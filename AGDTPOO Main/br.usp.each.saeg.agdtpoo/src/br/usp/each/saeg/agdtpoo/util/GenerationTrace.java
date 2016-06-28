
package br.usp.each.saeg.agdtpoo.util;

import java.util.ArrayList;

public class GenerationTrace {
    
    private static GenerationTrace _instance;
    
    ArrayList<String> _messages;    
    
    private GenerationTrace()
    {
        _messages = new ArrayList<String>();
    }
    
    public static void clear()
    {
        _instance = null;
        
        getInstance();
    }
    
    public static String getTrace()
    {
        ArrayList<String> messages = getInstance()._messages;
        
        StringBuilder builder = new StringBuilder();
        
        for (String msg : messages) {
            builder.append(msg);
            builder.append("\n");
        }
        
        return builder.toString();
    }
    
    public static void write(Object... params)
    {
        String message = "";
        
        for (Object obj : params) {
            message += (obj == null) ? "NULL" : obj.toString();
        }
        
        write(message);
    }
        
    public static void write(String message)
    {
        getInstance()._messages.add(message);
    }
    
    public static GenerationTrace getInstance()
    {
        if (_instance == null)
            _instance = new GenerationTrace();
        
        return _instance;
    }
}
