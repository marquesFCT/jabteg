
package br.jabuti.agdtpoo.view;

import br.usp.each.saeg.agdtpoo.entity.GUIGenerationCustomParameter;
import br.usp.each.saeg.agdtpoo.entity.PrimitiveType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class CustomParameterController extends JPanel {

    final int HEIGHT = 46;
    
    private GUIGenerationCustomParameter _parameter;
    private int _width;
    private JTextField textValue;
    
    public CustomParameterController(GUIGenerationCustomParameter parameter)
    {
        super();      
        
        // Inicializar controles
        initializaControls(parameter, 256);        
    }
    
    private void initializaControls(GUIGenerationCustomParameter parameter, int width)
    {      
        // Atribuir tipo sob análise
        this._parameter = parameter;
        
        // Atribuir largura
        this._width = width;
                
        // Definir tamanho
        this.setSize(this._width, this.HEIGHT);
        this.setPreferredSize(new Dimension(this._width, this.HEIGHT));
                
        // Criar controles na tela
        buildControls();   
    }
        
    // Executa rotinas de criacao dos controles
    private void buildControls()
    {           
        JPanel newPanel = new JPanel(new FlowLayout());
        
        newPanel.setBorder(new EtchedBorder());
        
        // Verificar se o tipo é suportado e deve ser renderizado
        if (!isSupportedType(this._parameter.getType()))
            buildControlsNotSupport(newPanel);
        else
            buildControlsToParameterType(newPanel);
        
        // Definir tamanho
        newPanel.setSize(this._width, HEIGHT - 5);
        newPanel.setPreferredSize(new Dimension(this._width, HEIGHT - 5));
        
        this.add(newPanel);
    }
    
    // Controi os controles para os tipos nao suportados
    private void buildControlsNotSupport(JPanel mainPanel)
    {   
        JLabel label = new JLabel("Type not supported.");
                      
        mainPanel.add(label);        
    }
    
    // Constroi os controles para o tipo char
    private void buildControlsToParameterType(JPanel mainPanel)
    {        
        JLabel label = new JLabel("   " + this._parameter.getLegend());
        
        textValue = new JTextField(10);
        
        textValue.setColumns(5);
        
        if (this._parameter.getDefaultValue() != null)
            textValue.setText(this._parameter.getDefaultValue().toString());
        
        mainPanel.add(label);
        mainPanel.add(textValue);
    }
   
    private boolean isSupportedType(PrimitiveType type)
    {
        if (type == PrimitiveType.Character)
            return true;
        else if (type == PrimitiveType.Double)
            return true;
        else if (type == PrimitiveType.Float)
            return true;
        else if (type == PrimitiveType.Integer)
            return true;
        else if (type == PrimitiveType.Long)
            return true;
        else if (type == PrimitiveType.String)
            return true;
        else if (type == PrimitiveType.Short)
            return true;
        
        return false;
    }
    
    private Object getDefaultValue(PrimitiveType type)
    {
        if (type == PrimitiveType.Character)
            return ' ';
        else if (type == PrimitiveType.Double)
            return 0.0;
        else if (type == PrimitiveType.Float)
            return 0.0;
        else if (type == PrimitiveType.Integer)
            return 0;
        else if (type == PrimitiveType.Long)
            return 0;
        else if (type == PrimitiveType.String)
            return "";
        else if (type == PrimitiveType.Short)
            return 0;
        
        return null;
    }
    
    private Object getValue(PrimitiveType type)
    {
        // Verifica se o tipo é suportado
        if (!isSupportedType(type))
            return null;
                        
        // Proteção de código 
        if (this.textValue == null)
            return null;
                
        // Se o valor do textbox estiver em branco,
        // então é retornado o valor padrão
        if (this.textValue.getText().trim().equals(""))
            return getDefaultValue(type);
        
        Object returnValue = null;        
        String valueToConvert = this.textValue.getText();
        
        try
        {

            // Converte o valor digitado para seu respectivo tipo            
            if (type == PrimitiveType.Character)                   
                returnValue =  valueToConvert.toCharArray()[0];
            else if (type == PrimitiveType.Double)
                return Double.parseDouble(valueToConvert);
            else if (type == PrimitiveType.Float)
                return Float.parseFloat(valueToConvert);
            else if (type == PrimitiveType.Integer)
                return Integer.parseInt(valueToConvert);
            else if (type == PrimitiveType.Long)
                return Long.parseLong(valueToConvert);
            else if (type == PrimitiveType.String)
                return valueToConvert;
            else if (type == PrimitiveType.Short)
                return Short.parseShort(valueToConvert);

        }
        catch(Exception e)
        {
            returnValue = getDefaultValue(type);
        }
        
        return returnValue;
    }
    
    public GUIGenerationCustomParameter getParameter()
    {
        Object value = getValue();
        
        this._parameter.setValue(value);
        
        return this._parameter;
    }
    
    public Object getValue()
    {
        return getValue(this._parameter.getType());
    }    
}
