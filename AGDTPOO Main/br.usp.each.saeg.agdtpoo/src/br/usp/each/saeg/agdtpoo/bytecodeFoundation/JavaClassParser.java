
package br.usp.each.saeg.agdtpoo.bytecodeFoundation;

import br.usp.each.saeg.agdtpoo.entity.JavaClass;

public class JavaClassParser {
    public static JavaClass Parse(Class targetClass)
    {
        JavaClass returnValue;        
                
        // Criando instância de um JavaClass
        returnValue = new JavaClass();
        
        returnValue.setClassUnderTest(targetClass);
            
        // Fazer leitura dos métodos públicos existentes
        returnValue.setMethods(targetClass.getMethods());
                        
        // Fazer leitura dos construtores da classe sob teste
        returnValue.setConstructors(targetClass.getConstructors());         
            
        // Definir o nome da classe
        returnValue.setClassName(targetClass.getName());
        
        return returnValue;
    }
}
