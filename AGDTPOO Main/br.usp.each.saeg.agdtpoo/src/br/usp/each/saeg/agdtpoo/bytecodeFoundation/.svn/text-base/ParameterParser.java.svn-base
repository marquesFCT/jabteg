
package br.usp.each.saeg.agdtpoo.bytecodeFoundation;

import br.usp.each.saeg.agdtpoo.entity.ReflectionParameter;
import br.usp.each.saeg.agdtpoo.util.Features;
import java.lang.reflect.Type;

public class ParameterParser {
    public static ReflectionParameter parse(Type parameter)
    {
        ReflectionParameter returnValue;
        
        returnValue = new ReflectionParameter();
                
        // Captura do tipo de dados do parâmetro
        returnValue.setParameterType(parameter);
        // Captura do valor do parâmetro, como ele não existe,
        // então devido como NULL
        returnValue.setParameterValue(null);
        
        // Determina se o tipo de dados é um array
        returnValue.isArray(Features.isArray(parameter));
        
        // Determina se o tipo de dados é abstrato
        returnValue.isAbstract(Features.isAbstract(parameter));
        
        if (returnValue.isAbstract())
            returnValue.setComponentType(Features.getArrayComponentType(parameter));
        
        return returnValue;
    }
}
