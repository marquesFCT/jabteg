package br.usp.each.saeg.agdtpoo.individualFactory;

import br.usp.each.saeg.agdtpoo.entity.*;
import br.usp.each.saeg.agdtpoo.util.Features;
import br.usp.each.saeg.agdtpoo.util.ManegementTypeSingleton;
import java.util.ArrayList;

public class UnitTestFactory {
    
    private int _internalDepth;
    
    public String[] getRepresentation(Individual[] individuals)
    {
        String[] result;
        
        result = new String[individuals.length];
        
        int count = 0;
        for (Individual itemIndividual : individuals) {
            result[count] = getRepresentation(itemIndividual, count + 1);
            count++;
        }
        
        return result;
    }
    
    public String getUnitTestClass(Individual[] individuals)
    {
        String returnValue = "";
        String[] representations; 
        String importsStructure = "import org.junit.*;\n" + getPackagesToImport() + "\n";
        String classStructure = "public class AutomaticTest {\n{SOURCE}}";
        
        // Captura as representações dos métodos de teste
        representations = getRepresentation(individuals);
        
        // Concatenar as chamadas a métodos
        for (String rep : representations) {
            rep = rep.replace("\n", "\n\t");            
            returnValue += "\t" + rep + "\n";
        }
        
        returnValue = importsStructure + classStructure.replace("{SOURCE}", returnValue);
        
        return returnValue;
    }
    
    private String getPackagesToImport()
    {   
        ManegementTypeSingleton mType = ManegementTypeSingleton.getInstance(); 
        
        String[] packages = mType.getPackages();
        String returnValue = "";
                
        for (String packageName : packages) {
            if (packageName.trim().equals(""))
                continue;
            returnValue += "import " + packageName + ".*;\n";
        }
        
        return returnValue;        
    }
    
    public String getRepresentation(Individual individual)
    {
        return getRepresentation(individual, 1);
    }
    
    private String getRepresentation(Individual individual, int methodIndex)
    {
        String result;
       
        // Criar as linhas de comando
        result = getRepresentation(individual.getTestIndividual());
        
        // Adiciona os comandos a um método
        result = formatJUnitTest(result, methodIndex);
        
        return result;
    }
    
    private String formatJUnitTest(String lineOfCommands, int methodIndex)
    {
        String returnValue = "";
        String methodFormat = "@Test\npublic void testMethod{INDEX}(){\n\t{CODE}}\n";
                
        // Adicionar quebra de linha nos métodos
        lineOfCommands = lineOfCommands.replace(";", ";\n\t");
        if (lineOfCommands.endsWith("\t"))
            lineOfCommands = lineOfCommands.substring(0, lineOfCommands.length() - 1);
        
        // Adicionar as linhas de código a estrutura do método
        returnValue = methodFormat.replace("{INDEX}", String.valueOf(methodIndex));
        returnValue = returnValue.replace("{CODE}", lineOfCommands);
                
        return returnValue;
    }
            
    private void initializeDeep()
    {
        this._internalDepth = 0;
    }
    
    private void addDeep()
    {
        this._internalDepth++;
    }
    
    private void removeDeep()
    {
        this._internalDepth--;
    }    
    
    public String getRepresentation(TestIndividual individual)
    {
        // Inicializa o controle interno de profundidade
        initializeDeep();
        
        RepresentationElement repElement = null;
        
        repElement = getUnitTestRepresentation(individual);
        
        String finalUnitTestRepresentation = repElement.getLeftSide() + ";" + repElement.getRigthSide();
        
        finalUnitTestRepresentation = removeInperfections(finalUnitTestRepresentation);
        
        InstanceManegementeSingleton.clearInstance();
        
        return finalUnitTestRepresentation;
    }
    
    private String removeInperfections(String value)
    {
        value = value.replace(",)", ")");
        value = value.replace(":", ";");
        value = value.replace("@, ", "@");
        value = value.replace(",, ", ",");
        value = value.replace(";;", ";");
        value = value.replace("$", "");
        value = value.replace(",}", "}");
        
        if (value.endsWith(","))
            value = value.substring(0, value.length() - 1);
        
        if (value.startsWith(";"))
            value = value.substring(1);
        
        if (!value.endsWith(";"))
            value += ";";
        
        return value;
    }
    
    private RepresentationElement getUnitTestRepresentation(TestIndividual individual)
    {
        // Acresce um grau na profundidade
        addDeep();
        
        RepresentationElement element;
        RepresentationElement[] repMethods;
        RepresentationElement repMethodUnderTest;
        RepresentationElement repConstructor;
        String partialRepresentation;
        String instanceName;
        String className;
                
        className = individual.getBytecodeType().getTypeName();
        // Remover o package da classe, para diminuir o tamanho da string de representação do teste unitario
        if (className.contains("."))
            className = className.substring(className.lastIndexOf(".") + 1);        
        
        instanceName = InstanceManegementeSingleton.getInstance().getInstanceName(individual);
                
        repMethods = getMethodsRepresentations(instanceName, individual.getMethods());        
        repMethodUnderTest = getMethodRepresentation(instanceName, individual.getMethodUnderTest());
        repConstructor = getConstructorRepresentation(instanceName, className, individual.getConstructor());
                
        element = new RepresentationElement();
        
        element.setInstanceName(instanceName);
        
        // Concatenando elementos para formar a representação do teste unitario
        if (repConstructor != null)
            element.concat(repConstructor);
        
        for (RepresentationElement representationElement : repMethods) {
            element.concat(representationElement);
        }
        
        if (repMethodUnderTest != null)
            element.concat(repMethodUnderTest);
        
        // Decresce um grau na profundidade
        removeDeep();      
        
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
        methodCall = className + " " + instanceName + "= new " + className + "(";
        
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
                        rightSide += "";
                    }
                    else if (reflectionParameter.isArray() && Features.isPrimitive(reflectionParameter.getComponentType()))
                    {
                        methodCall += itemElement.getLeftSide() + ",";
                        rightSide += "";
                    }                            
                    // Se o tipo for um objeto, então será formatada a sua chamada,
                    // concatenando seus métodos intermediários, a criação de sua instância e 
                    // a sua passagem como parâmetro para o método.
                    else
                    {
                        methodCall = itemElement.getLeftSide() + ";" + methodCall + itemElement.getInstanceName() + ",";
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
        if (method.isStatic()) {
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
                        rightSide += "";
                    }
                    else if (reflectionParameter.isArray() && Features.isPrimitive(reflectionParameter.getComponentType()))
                    {
                        methodCall += itemElement.getLeftSide() + ",";
                        rightSide += "";
                    }
                    // Se o tipo for um objeto, então será formatada a sua chamada,
                    // concatenando seus métodos intermediários, a criação de sua instância e 
                    // a sua passagem como parâmetro para o método.
                    else
                    {
                        methodCall = itemElement.getLeftSide() + ";" + methodCall + itemElement.getInstanceName() + ",";
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
            element.setLeftSide(parameter.getParameterValue().toString());
            element.setRightside("");
        }
        else if (parameter.isArray() && Features.isPrimitive(parameter.getComponentType()))
        {
            String typeName;
            String arrayCode;
            
            typeName = parameter.getComponentType().toString();
            if (typeName.contains("."))
                typeName = typeName.substring(typeName.lastIndexOf(".") + 1);
                    
            arrayCode = "new " + typeName + "[]{";
            
            ArrayList<Object> parameterValues = (ArrayList<Object>)parameter.getParameterValue();
            for (Object objectValue : parameterValues) {
                arrayCode += objectValue.toString() + ","; 
            }
            arrayCode += "}";
            
            element.setLeftSide(arrayCode);
            element.setRightside("");
        }
        // Se não for um tipo primitivo, então é preciso iniciar o processo recursivo
        else
        {
            if (parameterValue instanceof TestIndividual)
            {
                element = getUnitTestRepresentation((TestIndividual)parameterValue);
            }
        }
                
        return element;
    }
}
