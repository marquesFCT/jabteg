
package br.usp.each.saeg.agdtpoo.individualFactory;

import br.usp.each.saeg.agdtpoo.entity.*;
import br.usp.each.saeg.agdtpoo.util.Features;
import java.util.ArrayList;

public class TonellaFactory {
        
    public String[] getRepresentation(Individual[] individuals)
    {
        String[] result;
        
        result = new String[individuals.length];
        
        int count = 0;
        for (Individual itemIndividual : individuals) {
            result[count] = getRepresentation(itemIndividual);
            count++;
        }
        
        return result;
    }
    
    public String getRepresentation(Individual individual)
    {
        String result;
        
        result = getRepresentation(individual.getTestIndividual());
                        
        return result;
    }
   
    public String getRepresentation(TestIndividual individual)
    {
        RepresentationElement repElement = null;
        
        repElement = getTonellaRepresentation(individual);
        
        String finalTonellaRepresentation = repElement.getLeftSide() + "@" + repElement.getRigthSide();
        
        finalTonellaRepresentation = removeInperfections(finalTonellaRepresentation);
        
        InstanceManegementeSingleton.clearInstance();
        
        return finalTonellaRepresentation;
    }
    
    private String removeInperfections(String value)
    {
        value = value.replace(",)", ")");
        value = value.replace("@, ", "@");
        value = value.replace(",, ", ",");
        value = value.replace("::", ":");
        value = value.replace(",]", "]");
        value = value.replace("@ ,", "@");
        value = value.replace(",,,", ",");
        value = value.replace(",,", ",");
        value = value.replace("@,", "@");
        value = value.replace("@ ", "@");
                
        if (value.endsWith(","))
            value = value.substring(0, value.length() - 1);
        
        if (value.startsWith(":"))
            value = value.substring(1);
        
        return value;
    }
    
    private RepresentationElement getTonellaRepresentation(TestIndividual individual)
    {
        RepresentationElement element;
        RepresentationElement[] repMethods;
        RepresentationElement repMethodUnderTest;
        RepresentationElement repConstructor;
        String instanceName;
        String className;
        
        className = individual.getBytecodeType().getTypeName();
        // Remover o package da classe, para diminuir o tamanho da string de representação de Tonella
        if (className.contains("."))
            className = className.substring(className.lastIndexOf(".") + 1);        
        
        instanceName = InstanceManegementeSingleton.getInstance().getInstanceName(individual);
                
        repMethods = getMethodsRepresentations(instanceName, individual.getMethods());        
        repMethodUnderTest = getMethodRepresentation(instanceName, individual.getMethodUnderTest());
        repConstructor = getConstructorRepresentation(instanceName, className, individual.getConstructor());
                
        element = new RepresentationElement();
        
        element.setInstanceName(instanceName);
        
        // Concatenando elementos para formar a representação de Tonella
        if (repConstructor != null)
            element.concat(repConstructor);
        
        for (RepresentationElement representationElement : repMethods) {
            element.concat(representationElement);
        }
        
        if (repMethodUnderTest != null)
            element.concat(repMethodUnderTest);

        return element;
    }
    
    private RepresentationElement[] getMethodsRepresentations(String instanceName, ReflectionMethod[] methods)
    {
        RepresentationElement[] elements;
        
        elements = new RepresentationElement[methods.length];
        
        int count = 0;
        for (ReflectionMethod reflectionMethod : methods) {
            elements[count] = getMethodRepresentation(instanceName, reflectionMethod);
            count++;
        }
        
        return elements;
    }
    
    private RepresentationElement getConstructorRepresentation(String instanceName, String className, ReflectionConstructor constructor)
    {
        if (constructor == null)
            return null;
        
        String methodCall;
        RepresentationElement element;
                
        element = new RepresentationElement();
        
        // Capturar nome da classe vinculada ao construtor 
        if (instanceName.contains("."))
            instanceName = instanceName.substring(instanceName.lastIndexOf(".")+1);
                      
        // Construção da chamada do construtor
        methodCall = instanceName + "=" + className + "(";
        
        if (constructor.getParameters().length == 0)
        {
            methodCall += ")";
            element.setLeftSide(methodCall);
            element.setRightside("");
        }
        else
        { 
            String rightSide = "";
            
            for (ReflectionParameter reflectionParameter : constructor.getParameters()) {
                RepresentationElement itemElement = null;

                itemElement = getParameterRepresentation(reflectionParameter);

                if (itemElement != null)
                {                   
                    // Se o parâmetro for do tipo primitivo, então será formatado
                    // adicionando seu tipo do lado direito e seu valor do lado esquerdo
                    if (reflectionParameter.isPrimitive())
                    {
                        methodCall += itemElement.getLeftSide() + ",";
                        rightSide += itemElement.getRigthSide() + ",";
                    }
                    // Se for um array de tpos primitivos
                    else if (reflectionParameter.isArray() && reflectionParameter.getComponentType().isPrimitive())
                    {
                        methodCall += itemElement.getLeftSide() + ",";
                        rightSide += itemElement.getRigthSide() + ","; 
                    }                            
                    // Se o tipo for um objeto, então será formatada a sua chamada,
                    // concatenando seus métodos intermediários, a criação de sua instância e 
                    // a sua passagem como parâmetro para o método.
                    else
                    {
                        methodCall = itemElement.getLeftSide() + ":" + methodCall + itemElement.getInstanceName() + ",";
                    }                    
                }
            }
            
            methodCall += ")";
            element.setLeftSide(methodCall);
            element.setRightside(rightSide);        
        }
        
        return element;
    }
    
    private RepresentationElement getMethodRepresentation(String instanceName, ReflectionMethod method)
    {
        if (method == null)
            return null;
        
        String methodCall;
        String methodName;
        RepresentationElement element;
                
        element = new RepresentationElement();
        
        // Capturar nome do método 
        methodName = method.getName();
        
        // Montagem da chamada do método
        if (method.isStatic())
        {
            String className = method.getReflectionClass().getClassName();
            
            methodCall = className + "." + methodName + "(";
        }
        else
            methodCall = instanceName + "." + methodName + "(";
        
        // Se o método não possui parâmetros então é preciso apenas concatenar
        // o parenteses de fechamento do método
        if (method.getParameters().length == 0)
        {
            methodCall += ")";
            element.setLeftSide(methodCall);
            element.setRightside("");
        }
        else
        {     
            String rightSide = "";
            
            for (ReflectionParameter reflectionParameter : method.getParameters()) {
                RepresentationElement itemElement = null;

                itemElement = getParameterRepresentation(reflectionParameter);

                if (itemElement != null)
                {
                    // Se o parâmetro for do tipo primitivo, então será formatado
                    // adicionando seu tipo do lado direito e seu valor do lado esquerdo
                    if (reflectionParameter.isPrimitive())
                    {
                        methodCall += itemElement.getLeftSide() + ",";
                        rightSide += itemElement.getRigthSide() + ",";
                    }
                    // Se for um array de tpos primitivos
                    else if (reflectionParameter.isArray() && Features.isPrimitive(reflectionParameter.getComponentType()))
                    {
                        methodCall += itemElement.getLeftSide() + ",";
                        rightSide += itemElement.getRigthSide() + ","; 
                    }                              
                    // Se o tipo for um objeto, então será formatada a sua chamada,
                    // concatenando seus métodos intermediários, a criação de sua instância e 
                    // a sua passagem como parâmetro para o método.
                    else
                    {
                        methodCall = itemElement.getLeftSide() + ":" + methodCall + itemElement.getInstanceName() + ",";
                        rightSide += itemElement.getRigthSide() + ",";
                    }
                }
            }
            
            methodCall += ")";
            element.setLeftSide(methodCall);
            element.setRightside(rightSide);
        }
                
        return element;
    }
    
    // Retorna uma representação de um parâmetro
    private RepresentationElement getParameterRepresentation(ReflectionParameter parameter)
    {
        if (parameter.isArray())
        {
            int x = 10;
        }
        
        boolean isPrimitive = false;
        Object parameterValue = null;
        RepresentationElement element;
        
        element = new RepresentationElement();
        
        // Verifica se é um tipo primitivo
        isPrimitive = parameter.isPrimitive();
        
        // Captura o valor do parâmetro
        parameterValue = parameter.getParameterValue();
        
        // Se o valor do parâmetro for nulo,
        // então será retornado um valor em branco
        // que não impacte no processo...
        if (parameterValue == null)        
            return null;
                   
        // Se for um tipo primitivo, apenas retorna o valor
        if (isPrimitive)
        {         
            if (parameter.isString()){                
                element.setLeftSide("String");
            }
            else
                element.setLeftSide(parameter.getParameterType().toString());
            element.setRightside(parameter.getParameterValue().toString());
        }
        else if (parameter.isArray() && Features.isPrimitive(parameter.getComponentType()))
        {
            String typeName = parameter.getComponentType().toString();
            
            if (typeName.contains("."))
                typeName = typeName.substring(typeName.lastIndexOf(".") + 1);
            
            element.setLeftSide(typeName + "[]");
            String values = "";
            ArrayList<Object> objectValues = (ArrayList<Object>)parameter.getParameterValue();
            for (Object itemValue : objectValues) {
                values += itemValue.toString() + ",";
            }
            element.setRightside("[" + values + "]");
        }
        // Se não for um tipo primitivo e nao for um array, então é preciso iniciar o processo recursivo
        else
        {
            if (parameterValue instanceof TestIndividual)
            {
                element = getTonellaRepresentation((TestIndividual)parameterValue);
            }
        }
                
        return element;
    }
}
