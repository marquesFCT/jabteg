
package br.usp.each.saeg.agdtpoo.bytecodeFoundation;

import br.usp.each.saeg.agdtpoo.entity.ReflectionConstructor;
import br.usp.each.saeg.agdtpoo.entity.ReflectionParameter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

public class ConstructorParser {
    public static ReflectionConstructor parse(Constructor constructor)
    {
        ReflectionConstructor returnValue;
        
        returnValue = new ReflectionConstructor();
        // Define o nome do construtor
        returnValue.setName(constructor.getName());
        // Definir tipo de retorno do construtor
        returnValue.setReturnType(null);
        // Executar a leitura de todos os parâmetros do construtor
        for (Type item : constructor.getGenericParameterTypes())
        {
            // Converter o parâmetro do reflection para a entidade do modelo
            ReflectionParameter parameter = ParameterParser.parse(item);
            // Adicionar o parâmetro ao construtor de nosso modelo de dados
            returnValue.addParameter(parameter);
        }
        
        return returnValue;                
    }
}
