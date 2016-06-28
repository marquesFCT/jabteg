
package br.usp.each.saeg.agdtpoo.bytecodeFoundation;

import br.usp.each.saeg.agdtpoo.entity.JavaClass;
import br.usp.each.saeg.agdtpoo.entity.ReflectionClass;
import br.usp.each.saeg.agdtpoo.entity.ReflectionConstructor;
import br.usp.each.saeg.agdtpoo.entity.ReflectionMethod;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ClassParser {
     
    
    // Retorna uma instância de ReflectionClass a partir de um JavaClass
    public static ReflectionClass parse(JavaClass classInstance)
    {
        ReflectionClass returnValue;
        
        returnValue = new ReflectionClass();
        
        // Captura o nome da classe    
        returnValue.setClassName(classInstance.getClassName());        
        
        // Captura dos construtores
        for(Constructor item : classInstance.getConstructors())
        {
            // Adequar uma instância para o tipo adotado no modelo de dados
            ReflectionConstructor newConstrutor = ConstructorParser.parse(item);
            // Adicionar a nova instância a nossa classe
            returnValue.addConstructor(newConstrutor);
        }
        
        // Captura dos métodos
        for(Method item : classInstance.getMethods())
        {
            // Adequar uma instância para o tipo adotado no modelo de dados
            ReflectionMethod newMethod = MethodParser.parse(item);
                        
            if (newMethod != null)
            {                
                // Associa o método com sua respectiva classe
                newMethod.setReflectionClass(returnValue);
                
                // Adicionar a nova instância a nossa classe
                returnValue.addMethod(newMethod);
            }
        }
        
        return returnValue;
    }
    
    // Retorna uma instância de ReflectionClass a partir de um Class    
    public static ReflectionClass parse(Class classInstance)
    {
        ReflectionClass returnValue;
        
        returnValue = new ReflectionClass();
        
        // Captura o nome da classe
        // Talvez seja necessário adicionar mais método que retornem outras
        // opções de nomes de classes, como:
        // classInstance.getName()
        // classInstance.getCanonicalName()
        // classInstance.getSimpleName()       
        returnValue.setClassName(classInstance.getName());
       
        // Captura dos construtores
        for(Constructor item : classInstance.getConstructors())
        {
            // Adequar uma instância para o tipo adotado no modelo de dados
            ReflectionConstructor newConstrutor = ConstructorParser.parse(item);
            // Adicionar a nova instância a nossa classe
            returnValue.addConstructor(newConstrutor);
        }
        
        // Captura dos métodos
        for(Method item : classInstance.getMethods())
        {
            // Adequar uma instância para o tipo adotado no modelo de dados
            ReflectionMethod newMethod = MethodParser.parse(item);
            
            if (newMethod != null)
            {                
                // Associa o método com sua respectiva classe
                newMethod.setReflectionClass(returnValue);
            
                // Adicionar a nova instância a nossa classe
                returnValue.addMethod(newMethod);
            }
        }
        
        return returnValue;
    }
}
