
package br.usp.each.saeg.agdtpoo.randomFeature.randomTypes;

import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import java.util.Random;

public class IntegerRandomTypeFactory
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
        int returnValue = 0;
        Random rand = new Random();
                
        if (this._primitiveTips != null)
        {   
            returnValue = ((rand.nextInt(this._primitiveTips.getMaximalValueInt() - this._primitiveTips.getMinimalValueInt())) + this._primitiveTips.getMinimalValueInt());
        }
        else {   
            returnValue = rand.nextInt(100);
        }
        
        return returnValue;
    }   
}
