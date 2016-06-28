
package br.jabuti.agdtpoo.model;

import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.util.Features;

public class UtilBehaviors {
    
    public static String getMethodSignature(String signature)
    {        
        signature = Features.getMethodSignature(signature);
        
        return signature;
    }
    
    public static EnumCriterion getCriterion(String criterion)
    {
        EnumCriterion returnValue = EnumCriterion.AllNodesEd;
        
        if (criterion.equals("All-Uses-ed"))
            returnValue = EnumCriterion.AllUsesEd;
        if (criterion.equals("All-Uses-ei"))
            returnValue = EnumCriterion.AllUsesEi;
        if (criterion.equals("All-Edges-ed"))
            returnValue = EnumCriterion.AllEdgesEd;
        if (criterion.equals("All-Edges-ei"))
            returnValue = EnumCriterion.AllEdgesEi;
        if (criterion.equals("All-Pot-Uses-ed"))
            returnValue = EnumCriterion.AllPotUsesEd;
        if (criterion.equals("All-Pot-Uses-ei"))
            returnValue = EnumCriterion.AllPotUsesEi;
        if (criterion.equals("All-Nodes-ei"))
            returnValue = EnumCriterion.AllNodesEi;
        if (criterion.equals("All-Nodes-ed"))
            returnValue = EnumCriterion.AllNodesEd;
       
        return returnValue;
    }
}
