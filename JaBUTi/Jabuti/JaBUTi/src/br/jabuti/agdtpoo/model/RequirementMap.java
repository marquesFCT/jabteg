
package br.jabuti.agdtpoo.model;

import java.util.ArrayList;

public class RequirementMap {
    
    private ArrayList<MethodStructure> _methods;    
    private static RequirementMap _instance;
    
    private RequirementMap()
    {
        // Estrutura de métodos
        _methods = new ArrayList<MethodStructure>();
    }

    // Limpa da memória os requisitos dos métodos
    public void clear()
    {
        _methods = new ArrayList<MethodStructure>(); 
    }
    
    // Retorna a estrutura de métodos encontrada na estrutura
    public MethodStructure[] getMethodsStructure()
    {
                
        return _methods.toArray(new MethodStructure[_methods.size()]);
        
    }           
    
    public void addMethodStructure(MethodStructure methodStrucure)
    {
        clear();
        
        _methods.add(methodStrucure);
    }
    
    public boolean containsMethodStructure(String methodName)
    {
        boolean returnValue = false;
        
        for (MethodStructure methodStructure : _methods) {
            if (methodStructure.getMethodName().equals(methodName))
            {
                returnValue = true;
                
                break;
            }
        }
        
        return returnValue;
    }
    
    public static RequirementMap getInstance()
    {
        if (_instance == null)
            _instance = new RequirementMap();
        
        return _instance;
    }
}
