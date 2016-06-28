
package br.jabuti.agdtpoo.control;

import br.jabuti.agdtpoo.model.InputMethodDomain;
import br.jabuti.agdtpoo.view.CustomParameterController;
import br.jabuti.agdtpoo.view.InputDomainController;
import br.usp.each.saeg.agdtpoo.entity.GUIGenerationCustomParameter;
import br.usp.each.saeg.agdtpoo.entity.MethodPrimitiveDomain;
import br.usp.each.saeg.agdtpoo.entity.PrimitiveType;
import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import br.usp.each.saeg.agdtpoo.util.Features;
import java.awt.Component;
import javax.swing.JPanel;

public class TestCaseGenerationDialogController {
        
    public MethodPrimitiveDomain[] getMethodsPrimitiveDomain(InputMethodDomain[] methodsDomain)
    {
        MethodPrimitiveDomain[] returnValue = new MethodPrimitiveDomain[methodsDomain.length];
        
        int index = 0;
        for (InputMethodDomain inputMethodDomain : methodsDomain) {
            
            MethodPrimitiveDomain newPrimitiveDomain = new MethodPrimitiveDomain();
                    
            newPrimitiveDomain.setMethodName(inputMethodDomain.getMethodName());
            
            PrimitiveRandomTip[] tips = new PrimitiveRandomTip[inputMethodDomain.getInputDomainController().length];
            int i = 0;
            for (InputDomainController inputDomainController : inputMethodDomain.getInputDomainController()) {
                tips[i] = inputDomainController.getPrimitiveRandomTip();
                i++;
            }            
            newPrimitiveDomain.setMethodPrimitiveRandomTips(tips);
            
            
            returnValue[index] = newPrimitiveDomain;
            
            index++;
        }        
        
        return returnValue;
    }
    
    public InputMethodDomain getInputMethodDomain(String method)
    {
        InputMethodDomain returnValue = new InputMethodDomain();
        
        // Atribuir nome do método 
        returnValue.setMethodName(method);
        
        // Domain controllers
        InputDomainController[] domainControllers = getInputDomainControllers(method);
        returnValue.addInputDomainControllers(domainControllers);
                
        return returnValue;
    }
    
    public InputDomainController[] getInputDomainControllers(MethodPrimitiveDomain method)
    {
        String[] parametersTypes = getParametersNames(method.getMethodName());
                        
        PrimitiveType[] parameters = getPrimitiveTypes(parametersTypes);
        
        InputDomainController[] controllers = getInputDomainControllers(method, parameters, parametersTypes);        
        
        return controllers;
    }
    
    public InputDomainController[] getInputDomainControllers(String method)
    {
        String[] parametersTypes = getParametersNames(method);
                        
        PrimitiveType[] parameters = getPrimitiveTypes(parametersTypes);
        
        InputDomainController[] controllers = getInputDomainControllers(parameters, parametersTypes);        
        
        return controllers;
    }
    
    public InputDomainController[] getInputDomainControllers(MethodPrimitiveDomain method, PrimitiveType[] parameters, String[] parametersTypes)
    {
        InputDomainController[] inputDomainControls = new InputDomainController[parametersTypes.length];
        
        for (int i = 0; i < parametersTypes.length; i++) {
            
            PrimitiveRandomTip currentPrimitiveRandomTip = method.getMethodPrimitiveRandomTips(i);
            String title = "Parameter " + String.valueOf(i + 1) + " ( " + parametersTypes[i] + " )";
            
            InputDomainController domainController = new InputDomainController(parameters[i], title, 700, currentPrimitiveRandomTip);
            
            inputDomainControls[i] = domainController;
        }
        
        return inputDomainControls;
    } 
    
    public InputDomainController[] getInputDomainControllers(PrimitiveType[] parameters, String[] parametersTypes)
    {
        InputDomainController[] inputDomainControls = new InputDomainController[parametersTypes.length];
        
        for (int i = 0; i < parametersTypes.length; i++) {
            
            String title = "Parameter " + String.valueOf(i + 1) + " ( " + parametersTypes[i] + " )";
            
            InputDomainController domainController = new InputDomainController(parameters[i], title, 700);
            
            inputDomainControls[i] = domainController;
        }
        
        return inputDomainControls;
    }    
    
    public PrimitiveType getPrimitiveType(String parameter)
    {
        parameter = parameter.toLowerCase();
                
        return Features.getPrimitiveType(parameter);
    }
    
    public PrimitiveType[] getPrimitiveTypes(String[] parameters)
    {
        PrimitiveType[] types = new PrimitiveType[parameters.length];
        
        for (int i = 0; i < parameters.length; i++) {
            
            types[i] = getPrimitiveType(parameters[i]);
            
        }
        
        return types;
    }    
    
    public String[] getParametersNames(String method)
    {
        if (!(method.contains("(") && method.contains(")")))
            return new String[0];
        
        method = method.substring(method.indexOf("(") + 1);
        method = method.substring(0, method.indexOf(")"));
               
        String[] parametersName = method.split(",");
        
        for (int i = 0; i < parametersName.length; i++) {
            parametersName[i] = parametersName[i].trim();
        }
        
        return parametersName;
    }
    
    public PrimitiveRandomTip[] getPrimitiveRandomTipsFromInputDomainControllers(InputDomainController[] inputDomainControllers)
    {
        PrimitiveRandomTip[] randomTips = new PrimitiveRandomTip[inputDomainControllers.length];
        
        for (int i = 0; i < inputDomainControllers.length; i++) {
            randomTips[i] = inputDomainControllers[i].getPrimitiveRandomTip();
        }
        
        return randomTips;
    }
    
    public PrimitiveRandomTip[] getPrimitiveRandomTipsFromParameters(JPanel panel)
    {
        InputDomainController[] inputDomainControls = getCurrentDomainControllers(panel);
        
        PrimitiveRandomTip[] randomTips = getPrimitiveRandomTipsFromInputDomainControllers(inputDomainControls);
        
        return randomTips;
    }    
    
    private InputDomainController[] getCurrentDomainControllers(JPanel panel)
    {
        int count = panel.getComponentCount();
        
        if (count == 0)
            return new InputDomainController[0];
        
        InputDomainController[] returnValue = new InputDomainController[count];
        Component[] components = panel.getComponents();
        
        for (int i = 0; i < count; i++) {
            
            returnValue[i] = (InputDomainController)components[i];
                        
        }
        
        return returnValue;
    }    
    
    public GUIGenerationCustomParameter[] getCustomParameters(JPanel panel)
    {
        int countOfControls = panel.getComponentCount();
        
        GUIGenerationCustomParameter[] returnValue = new GUIGenerationCustomParameter[countOfControls];
        
        for (int i = 0; i < countOfControls; i++) {
            
            if (panel.getComponent(i) instanceof CustomParameterController)
                returnValue[i] = ((CustomParameterController)panel.getComponent(i)).getParameter();
            
        }
        
        return returnValue;
    }
}
