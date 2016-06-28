
package br.usp.each.saeg.agdtpoo.randomFeature.randomTypes;

import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import java.util.Random;

public class StringRandomTypeFactory
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
        Random rand = new Random();
        String returnValue = "";

        if (this._primitiveTips != null && this._primitiveTips.getPossibleStrings() != null && this._primitiveTips.getPossibleStrings().length > 0)
        {
            int maxSize = this._primitiveTips.getPossibleStrings().length;
            int indexAux = (int) (Math.random() * maxSize);
            
            returnValue = this._primitiveTips.getPossibleStrings()[indexAux];
        }
        else
        {        
            int passLength = (int) (Math.random() * 40);
            char[] goodChar = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
                'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'w',
                'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1',
                '2', '3', '4', '5', '6', '7', '8', '9', '0',};

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passLength; i++) {
                sb.append(goodChar[rand.nextInt(goodChar.length)]);
            }
            returnValue = sb.toString();
        }        
        
        returnValue = "\"" + returnValue + "\"";
        
        return returnValue;
    }   
}
