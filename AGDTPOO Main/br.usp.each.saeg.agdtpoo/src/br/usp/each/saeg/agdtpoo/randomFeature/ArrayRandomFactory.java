
package br.usp.each.saeg.agdtpoo.randomFeature;

import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import br.usp.each.saeg.agdtpoo.entity.ReflectionParameter;
import java.util.ArrayList;
import java.util.Random;

public class ArrayRandomFactory
    implements IRandomFactory {
    
    private PrimitiveRandomTip _primitiveTips;
    private int minArraySize = 0;
    private int maxArraySize = 10;
    
    public ArrayRandomFactory(PrimitiveRandomTip tips)
    {
        this._primitiveTips = tips;
    }
    
    @Override
    public Object getRandomValue(ReflectionParameter parameter)
    {   
        int arraySize = 0;
        Random rnd = new Random();
        IRandomFactory valuesGenerator = null;
        ReflectionParameter refParameter = null;
        ArrayList<Object> returnValue = new ArrayList<Object>();
        
        refParameter = new ReflectionParameter();
        refParameter.setParameterType(parameter.getComponentType());
                
        if (refParameter.isPrimitive())
        {
            valuesGenerator = new PrimitiveRandomFactory(this._primitiveTips);
        }
        
        if (valuesGenerator != null)
        {
            arraySize = ((rnd.nextInt(maxArraySize - minArraySize)) + minArraySize);
            
            for (int i = 0; i < arraySize; i++) {
                Object randomValue = null;
                    
                randomValue = valuesGenerator.getRandomValue(refParameter);
                        
                if (randomValue != null)
                    returnValue.add(randomValue);            
            }
        }
        
        return returnValue;
    }    
}
