
package br.usp.each.saeg.agdtpoo.randomFeature.randomTypes;

import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import java.util.Random;

public class LongRandomTypeFactory
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
        long returnValue = 0;
        Random rand = new Random();
        
        if (this._primitiveTips != null)
        {   
            returnValue = ((rand.nextInt((int)(this._primitiveTips.getMaximalValueLong() - this._primitiveTips.getMinimalValueLong()))) + this._primitiveTips.getMinimalValueLong());
        }
        else {   
            returnValue = rand.nextInt(100);
        }
        
        return returnValue;
    }   
}
