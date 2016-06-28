
package br.jabuti.agdtpoo.model;

import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.entity.GenerationRequirement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MethodStructure {
    
    private Map<EnumCriterion, GenerationRequirement[]> map;
    private String _methodName;
    
    public MethodStructure()
    {
        this.map = new HashMap<EnumCriterion, GenerationRequirement[]>();
    }
    
    public String getMethodName()
    {
        return _methodName;
    }
    
    public void setMethodName(String methodName)
    {
        _methodName = methodName;
    }
    
    public GenerationRequirement[] getRequirements(EnumCriterion criterion)
    {
        return this.map.get(criterion);
    }
    
    public void addCriterionAndRequirements(EnumCriterion criterion, Object[] requirements)            
    {   
        ArrayList<GenerationRequirement> reqs = new ArrayList<GenerationRequirement>();
        
        for (Object objectRequirement : requirements) {
            
            String idRequirement = objectRequirement.toString();
            
            if (requirementExists(idRequirement, reqs))
                continue;
            
            GenerationRequirement genRequirement = new GenerationRequirement();
            
            genRequirement.setIdRequirement(idRequirement);
            
            reqs.add(genRequirement);
        }
        
        GenerationRequirement[] arrayReq = reqs.toArray(new GenerationRequirement[reqs.size()]);
        
        this.map.put(criterion, arrayReq);
    }
    
    private boolean requirementExists(String idRequirement, ArrayList<GenerationRequirement> reqs)
    {
        for (GenerationRequirement requirement : reqs) {
            if (requirement.getIdRequirement().equals(idRequirement))
                return true;
        }
        
        return false;
    }
}
