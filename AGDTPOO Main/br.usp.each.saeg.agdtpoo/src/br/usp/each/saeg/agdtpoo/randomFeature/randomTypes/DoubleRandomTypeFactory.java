
package br.usp.each.saeg.agdtpoo.randomFeature.randomTypes;

import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import java.util.Random;

public class DoubleRandomTypeFactory     
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
        double returnValue;
    
        if (this._primitiveTips != null)
        {
            Random rand = new Random();
            
            returnValue = (double) ((rand.nextDouble() * (this._primitiveTips.getMaximalValueDouble() - this._primitiveTips.getMinimalValueDouble())) + this._primitiveTips.getMinimalValueDouble());
        }
        else {    
            Random rand = new Random();

            returnValue = (double) (rand.nextDouble() * 100);
        }
        
        return returnValue;
    }   
}