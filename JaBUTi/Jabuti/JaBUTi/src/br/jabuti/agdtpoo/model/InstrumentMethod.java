
package br.jabuti.agdtpoo.model;

import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.entity.GenerationRequirement;
import java.util.ArrayList;

public class InstrumentMethod {
    private int _id;
    private String _name;
    private ArrayList<GenerationRequirement> _nodes;
    
    public InstrumentMethod()
    {
        this._nodes = new ArrayList<GenerationRequirement>();                
    }
    
    public String getNodesConcat()
    {
        String coveragePath = "";
        GenerationRequirement[] subPaths = getRequirements();
        for (GenerationRequirement minimalPath : subPaths) {
            coveragePath += minimalPath.getIdRequirement() + "-";
        }                
            
        return "-" + coveragePath;
    }
        
    public void addRequirement(GenerationRequirement requirement)
    {
        this._nodes.add(requirement);
    }
    
    public int getId()
    {
        return this._id;
    }
    
    public void setId(int id)
    {
        this._id = id;
    }
        
    public String getName()
    {
        return this._name;
    }
    
    public void setName(String name)
    {
        this._name = name;
    }
    
    public GenerationRequirement[] getRequirements()
    {
        if (this._nodes.isEmpty())
            return null;
                
        return this._nodes.toArray(new GenerationRequirement[this._nodes.size()]);
    }
    
    public GenerationRequirement[] getRequirements(EnumCriterion criterion)
    {
        if (this._nodes.isEmpty())
            return null;
                
        return this._nodes.toArray(new GenerationRequirement[this._nodes.size()]);
    }    
}
