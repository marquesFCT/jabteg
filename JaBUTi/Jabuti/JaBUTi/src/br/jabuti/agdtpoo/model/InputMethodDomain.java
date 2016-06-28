
package br.jabuti.agdtpoo.model;

import br.jabuti.agdtpoo.view.InputDomainController;
import java.util.ArrayList;

public class InputMethodDomain {
    private ArrayList<InputDomainController> _controllers;
    private String _methodName;
    
    public InputMethodDomain()
    {
        _controllers = new ArrayList<InputDomainController>();
    }
    
    public InputDomainController[] getInputDomainController()
    {
        return _controllers.toArray(new InputDomainController[_controllers.size()]);
    }
    
    public void addInputDomainController(InputDomainController controller)
    {
        this._controllers.add(controller);
    }
    
    public void addInputDomainControllers(InputDomainController[] controllers)
    {
        for (InputDomainController inputDomainController : controllers) {
            this._controllers.add(inputDomainController);
        }        
    }
    
    public void setMethodName(String methodName)
    {
        this._methodName = methodName;
    }
    
    public String getMethodName()
    {
        return this._methodName;
    }
}
