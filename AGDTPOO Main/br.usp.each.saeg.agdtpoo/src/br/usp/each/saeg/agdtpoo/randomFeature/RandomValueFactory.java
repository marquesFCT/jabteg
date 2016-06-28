
package br.usp.each.saeg.agdtpoo.randomFeature;

import br.usp.each.saeg.agdtpoo.entity.*;
import br.usp.each.saeg.agdtpoo.util.Features;
import java.lang.reflect.Type;

public class RandomValueFactory {
  
    private static InputPrimitiveDomain _inputDomain;
    private static String _methodUnderTest;
    private static int _currentDepth;
    
    public RandomValueFactory()
    {
        this._currentDepth = 0;
    }
    
    public static void setPrimitiveTips(InputPrimitiveDomain inputDomain)
    {
        _inputDomain = inputDomain;
    }
    
    public static Individual[] getNewPopulation(int size, ReflectionClass targetClass, String methodUnderTest, InputPrimitiveDomain inputDomain)
    {
        Individual[] individuals = null;
        TestIndividual[] testIndividuals = null;
        
        // Geração aleatória dirigida
        _inputDomain = inputDomain;
        
        // Capturar indivíduos de teste 
        testIndividuals = getNewTestPopulation(size, targetClass, methodUnderTest);
        
        if (testIndividuals == null)
            return null;
                
        // Instanciar retorno
        individuals = new Individual[size];
        
        for (int i = 0; i < size; i++) {
            individuals[i] = new Individual();
            individuals[i].setIndividual(testIndividuals[i]);
        }
        
        return individuals;
    }
    
    public static TestIndividual[] getNewTestPopulation(int size, ReflectionClass targetClass, String methodUnderTest)
    {
        _methodUnderTest =  methodUnderTest;
        
        TestIndividual[] individuals = null;
        BytecodeType byteType = null;
        ObjectRandomFactory rndFactory = null;
        
        // Cria instância do gerador de objetos aleatórios
        rndFactory = new ObjectRandomFactory();
        
        // Captura do byteCodeType
        byteType = rndFactory.getBytecodeType(targetClass.getClassName());
               
        if (byteType != null)
        {
            individuals = new TestIndividual[size];
            for (int i = 0; i < size; i++) {

                // Captura da instância de um indivíduo de teste
                individuals[i] = rndFactory.generateRandomTestIndividual(byteType);
 
                // Atribuir o método sob teste ao indivíduo
                rndFactory.setMethodUnderTest(byteType, individuals[i], _methodUnderTest);
                
                // Atribuição de valores aleatórios
                setRandomValue(individuals[i]);               
            }
        }
        
        return individuals;
    }
         
    public static void setRandomValue(TestIndividual individual)
    {
         ReflectionConstructor constructor;
         ReflectionMethod[] intermediateMethods;
         ReflectionMethod methodUnderTest;
         
         // Captura do construtor
         constructor = individual.getConstructor();
         // Captura dos métodos intermediários
         intermediateMethods = individual.getMethods();
         // Captura do método sob teste
         methodUnderTest = individual.getMethodUnderTest();
         
         // Atribuir valores aos construtores
         if (constructor != null)
         {
             setRandomValue(constructor, _inputDomain.getIntermediateMethodPrimitiveRandomTip());
             individual.setConstructor(constructor);
         }
         
         // Atribui valores ao metodo sob teste        
         if (methodUnderTest != null)             
         {
             setRandomValue(methodUnderTest, _inputDomain);
             individual.setMethodUnderTest(methodUnderTest);
         }
         
         // Atribui valores aos metodos intermediarios
         if (intermediateMethods != null)
         {
             for (ReflectionMethod reflectionMethod : intermediateMethods) {
                 
                 setRandomValue(reflectionMethod, _inputDomain);
                 
             }
             individual.setMethods(intermediateMethods);
         }
    }
    
    private static void setRandomValue(ReflectionMethod[] methods, PrimitiveRandomTip tip)
    {
        for (ReflectionMethod item : methods) {
            setRandomValue(item, tip);
        }
    }  
    
    public static void setRandomValue(ReflectionConstructor[] constructors, PrimitiveRandomTip tip)
    {
        for (ReflectionConstructor item : constructors) {
            setRandomValue(item, tip);
        }
    }  
    
    public static void setRandomValue(ReflectionConstructor constructor, PrimitiveRandomTip tip)
    {
        for (ReflectionParameter item : constructor.getParameters()) {
            setRandomValue(item, tip);
        }
    }    
    
    public static void setRandomValue(ReflectionMethod method, PrimitiveRandomTip tip)
    {      
        for (ReflectionParameter item : method.getParameters()) {
            setRandomValue(item, tip);
        }
    }
    
    public static void setRandomValue(ReflectionMethod method, InputPrimitiveDomain inputDomain)
    {
        int index = 0;
        
        MethodPrimitiveDomain methodDomain = inputDomain.getOrCreateMethodDomain(method);
        
        for (ReflectionParameter item : method.getParameters()) {
            
            PrimitiveRandomTip tip  =  methodDomain.getMethodPrimitiveRandomTips(index);
            
            setRandomValue(item, tip);
            
            index++;
        }
    }
    
    public static void setRandomValue(ReflectionParameter parameter, PrimitiveRandomTip randomTip)
    {        
        // Se o parâmetro for passado nulo, então não será feito nada
        if (parameter == null)
            return;
        
        Type parameterType = parameter.getParameterType();
        
        // Validar tipo de dados armazenado
        if (parameterType == null)
            return;
        
        Class currentClassType = parameterType.getClass();
        
        // Validar associada ao tipo
        if (currentClassType == null)
            return;
        
        Object randomValue = null;
        IRandomFactory randomFactory;
        
        // Verifica se o tipo de dados é primitivo
        if (Features.isPrimitive(parameterType, currentClassType))
            randomFactory = new PrimitiveRandomFactory(randomTip);
        else if (parameter.isArray())
            randomFactory = new ArrayRandomFactory(randomTip);
        else     
        {            
            randomFactory = new ObjectRandomFactory();
            
            // Aumentar nível de profundidade
            _currentDepth++;            
            ((ObjectRandomFactory)randomFactory).setInternalDepth(_currentDepth);
        }
            
        // Geração de valor aleatório
        randomValue = randomFactory.getRandomValue(parameter);
        
        // Atribuição de valor aleatório ao parâmetro
        parameter.setParameterValue(randomValue);
        
        // Definir o valor aleatório dos parâmetros dos parâmetros
        if (randomValue instanceof TestIndividual)
        {
            // Ponto crítico do projeto. 
            // Tomar cuidado para não cair em um laço infinito.            
            TestIndividual testIndividualParameter = (TestIndividual)randomValue;
            
            // Atribui valores aleatórios para os parâmetros do construtor
            setRandomValue(testIndividualParameter.getConstructor(), randomTip);
            // Atribui valores aleatórios para os parâmetros dos métodos
            setRandomValue(testIndividualParameter.getMethods(), randomTip);   
            // Diminuir nível de profundidade
            _currentDepth--;
        }
    }    
}
