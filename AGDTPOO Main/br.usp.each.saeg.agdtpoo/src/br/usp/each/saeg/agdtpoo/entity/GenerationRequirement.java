
package br.usp.each.saeg.agdtpoo.entity;

import java.util.ArrayList;

public class GenerationRequirement {
    private ArrayList<GenerationRequirement> _parentRequirements;
    private ArrayList<String> _nextRequirements;
    private String _idRequirement;
    private boolean _isCovered;
    
    public GenerationRequirement()
    {
        setIdRequirement("");
        this._nextRequirements = new ArrayList<String>();
        this._parentRequirements = new ArrayList<GenerationRequirement>();
        this._isCovered = false;
    }
    
    public GenerationRequirement(String requirement)
    {
        setIdRequirement(requirement);
        this._nextRequirements = new ArrayList<String>();
        this._parentRequirements = new ArrayList<GenerationRequirement>();
        this._isCovered = false;
    }
    
    public void addParentRequirement(GenerationRequirement parentRequirement)
    {
        this._parentRequirements.add(parentRequirement);
        this._coveragePaths = null;
    }
    
    public GenerationRequirement[] getParentRequirements()
    {
        return this._parentRequirements.toArray(new GenerationRequirement[this._parentRequirements.size()]);
    }
        
    public void addNextRequirement(String requirement)
    {
        this._nextRequirements.add(requirement);
        this._coveragePaths = null;
    }
    
    public String[] getNextRequirements()
    {
        return this._nextRequirements.toArray(new String[this._nextRequirements.size()]);
    }
    
    public void isCovered(boolean coverage)
    {
        if (this._isCovered && !coverage)
            return;
        
        this._isCovered = coverage;
    }
    
    public boolean isCovered()
    {
        return this._isCovered;
    }
    
    public void setIdRequirement(String id)
    {
        this._idRequirement = id;
    }
    
    public String getIdRequirement()
    {
        return this._idRequirement;
    }
    
    private String[] _coveragePaths;
    
    public String[] getCoveragePaths()
    {
           
        if (this._coveragePaths != null)
            return this._coveragePaths;

        if (this._parentRequirements == null || this._parentRequirements.isEmpty())
        {
            this._coveragePaths = new String[] { "-" + this.getIdRequirement() + "-" };
        }
        // Para mais de 5 nós pais.
        else
        {
            ArrayList<String> paths = new ArrayList<String>();

            // Varrer os nós pais a fim de coletar as coberturas
            for (GenerationRequirement generationRequirement : this._parentRequirements) {

                try
                {                
                    int idParent = Integer.parseInt(generationRequirement.getIdRequirement());
                    int currentId = Integer.parseInt(this.getIdRequirement());
                    
                    if (idParent > currentId)
                    {
                        
                        String newPath = "-" + generationRequirement.getIdRequirement() + "-" + this.getIdRequirement() + "-";
                        // Concatena o Id do nó corrente 
                        if (!paths.contains(newPath))
                            paths.add(newPath);
                
                        continue;
                    }                      
                }
                catch(Exception ex)
                {
                    
                }
                
                // Captura as coberturas de um dos pais para análise
                String[] parentCoveragePaths = generationRequirement.getCoveragePaths();

                // Varre as coberturas concatenando as como resultado
                for (String parentPath : parentCoveragePaths) {
                    String newPath = parentPath + this.getIdRequirement() + "-";
                    // Concatena o Id do nó corrente 
                    if (!paths.contains(newPath))
                        paths.add(newPath);
                }                
            }

            this._coveragePaths = paths.toArray(new String[paths.size()]);
        }
            
        return this._coveragePaths;
    }    
}
