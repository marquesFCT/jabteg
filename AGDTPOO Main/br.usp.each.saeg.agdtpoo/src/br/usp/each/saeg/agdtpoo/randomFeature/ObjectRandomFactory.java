
package br.usp.each.saeg.agdtpoo.randomFeature;

import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import br.usp.each.saeg.agdtpoo.entity.BytecodeType;
import br.usp.each.saeg.agdtpoo.entity.InputPrimitiveDomain;
import br.usp.each.saeg.agdtpoo.entity.ReflectionClass;
import br.usp.each.saeg.agdtpoo.entity.ReflectionConstructor;
import br.usp.each.saeg.agdtpoo.entity.ReflectionMethod;
import br.usp.each.saeg.agdtpoo.entity.ReflectionParameter;
import br.usp.each.saeg.agdtpoo.entity.TestIndividual;
import br.usp.each.saeg.agdtpoo.util.Features;
import br.usp.each.saeg.agdtpoo.util.ManegementTypeSingleton;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class ObjectRandomFactory
    implements IRandomFactory {
    private boolean _controlObjectDepth;
    private InputPrimitiveDomain _inputDomain;
    private int internalDepth;

    public ObjectRandomFactory()
    {
        this._inputDomain = null;
        this.internalDepth = 0;
        this._controlObjectDepth = false;
    } 
    
    public ObjectRandomFactory(InputPrimitiveDomain inputDomain)
    {
        this._inputDomain = inputDomain;
        this.internalDepth = 0;        
        this._controlObjectDepth = false;
    }
    
    public void setInternalDepth(int depth)
    {
        this.internalDepth = depth;
    }
    
    private void controlObjectDepth()
    {
        int maxSize = 2;
        
        if (this._inputDomain != null)
        {
            maxSize = this._inputDomain.getIntermediateMethodPrimitiveRandomTip().getObjectGenerationMaxDepth();
        }
            
        if (this.internalDepth >= maxSize)
            this._controlObjectDepth = true;
        else
            this._controlObjectDepth = false;
    }
    
    @Override
    public Object getRandomValue(ReflectionParameter parameter)
    {
        TestIndividual result = null;
        BytecodeType byteType = null;
        
        byteType = getRegisteredBytecodeType(parameter);
        
        if (byteType != null)
        {
            result = generateRandomTestIndividual(byteType);
        }
        
        return result;
    }
           
    public void setMethodUnderTest(BytecodeType byteType, TestIndividual individual, String methodUnderTest)
    {
        ReflectionClass targetClass;
        ReflectionMethod[] methods;
        
        targetClass = byteType.getCurrentClass();
        methods = targetClass.getMethods();
        
        for (ReflectionMethod reflectionMethod : methods) {
            String methodSignature = reflectionMethod.getSignature();
            if (methodSignature.equals(methodUnderTest))
            {
                individual.setMethodUnderTest(reflectionMethod);
                break;
            }
        }
    }
    
    public TestIndividual generateRandomTestIndividual(BytecodeType byteType)
    {
        TestIndividual result;
        ReflectionClass targetClass;
        ReflectionConstructor randomConstructor;
        ReflectionMethod[] randomMethods;
        
        // Identifica se é preciso controlar a profundidade 
        // da geração aleatória de objetos
        controlObjectDepth();
        
        targetClass = byteType.getCurrentClass();
                
        randomConstructor = getOneRandomConstructor(targetClass);
        randomMethods = getRandomMethods(targetClass);
        
        result = new TestIndividual(byteType);        
        
        result.setConstructor(randomConstructor);
        result.setMethods(randomMethods);
        
        return result;
    }
    
    private ReflectionConstructor getOneRandomConstructor(ReflectionClass target)
    {
        ReflectionConstructor constructor = null;
        ReflectionConstructor[] constructors = null;
        
        constructors = target.getConstructors();
        
        if (constructors == null || constructors.length == 0)
            return null;
        else if (constructors.length == 1)
            constructor = constructors[0];
        else 
        {
            Random rand = new Random();
            int arrayIndex = rand.nextInt(constructors.length);
            
            constructor = constructors[arrayIndex];
        }

        return constructor;
    }
    
    private ReflectionMethod[] getRandomMethods(ReflectionClass target)
    {
        int methodIndex = 0;
        int randomMethods = 0;
        ArrayList<ReflectionMethod> result = null;
        ReflectionMethod[] methods = null;
        
        result = new ArrayList<ReflectionMethod>();
        methods = target.getMethods();
        
        if (methods == null || methods.length == 0)
            return null; 
                
        Random rand = new Random();
        randomMethods = rand.nextInt((methods.length / 2));
        
        ReflectionMethod targetMethod;
        for (int i = 0; i < randomMethods; i++) {
            rand = new Random();
            
            methodIndex = rand.nextInt(methods.length);
            
            targetMethod = methods[methodIndex];
            
            // Se o controle de profundidade de objetos estiver ativo,
            // então não será mais possível adicionar novos métodos que 
            // façam uso de objetos como seus parâmetros.
            if (this._controlObjectDepth)
            {
                for (ReflectionParameter reflectionParameter : targetMethod.getParameters()) {
                    if (!reflectionParameter.isPrimitive())
                        continue;
                }
            }
            
            result.add(targetMethod);
        }
        
        ReflectionMethod[] ref = new ReflectionMethod[result.size()];
        return result.toArray(ref);
    }
    
    private BytecodeType getRegisteredBytecodeType(ReflectionParameter parameter)
    {
        String typeName = "";
        Type paramType = null;
        BytecodeType bytecodeType = null;
        
        paramType = parameter.getParameterType();
        
        typeName = Features.getTypeName(paramType);
        
        bytecodeType = getBytecodeType(typeName);
        
        return bytecodeType;
    }
    
    public BytecodeType getBytecodeType(String typeName)
    {
        BytecodeType result;
        ManegementTypeSingleton mngTypes;
        
        mngTypes = ManegementTypeSingleton.getInstance();
        
        if (mngTypes.containsBytecodeType(typeName))
        {
            result = mngTypes.getBytecodeType(typeName);
            return result;
        }
        else
        {            
            return null;
        }
    }
}
