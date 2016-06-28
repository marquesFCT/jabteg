
package br.usp.each.saeg.agdtpoo.randomFeature.randomTypes;

import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;

public interface IRandomTypeFactory {    
    void setPrimitiveType(PrimitiveRandomTip primitiveTips);
    
    Object generateRandomValue();
}
