
package br.usp.each.saeg.agdtpoo.randomFeature.randomTypes;

import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import java.util.Random;

public class FloatRandomTypeFactory
    implements IRandomTypeFactory {
    
    private PrimitiveRandomTip _primitiveTips;
    
    @Override
    public void setPrimitiveType(PrimitiveRandomTip primitiveTips)
    {
        _primitiveTips = primitiveTips;
    }
    
    @Override
    public Object generateRandomValue()
    {
        float returnValue;
    
        Random rand = new Random();
        
        if (this._primitiveTips != null)
        {   
            returnValue = (float) ((rand.nextFloat() * (this._primitiveTips.getMaximalValueFloat() - this._primitiveTips.getMinimalValueFloat())) + this._primitiveTips.getMinimalValueFloat());
        }
        else {   
            returnValue = (float) (rand.nextFloat() * 100);
        }
        
        return String.valueOf(returnValue) + "F";
    }       
}
