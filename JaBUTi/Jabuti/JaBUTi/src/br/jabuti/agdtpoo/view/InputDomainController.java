
package br.jabuti.agdtpoo.view;

import br.usp.each.saeg.agdtpoo.entity.PrimitiveType;
import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InputDomainController extends JPanel {

    private PrimitiveRandomTip tips;
    private PrimitiveType _type;
    private String _title;
    private int _width;
    private int _height;
    private JTextArea text;
    private JTextField textMin;
    private JTextField textMax;
    private JTextField textChar;
    
    public InputDomainController(PrimitiveType type)
    {
        super();      
        
        // Inicializar controles
        initializaControls(type, "Input domain controller", 500, new PrimitiveRandomTip());        
    }
    
    public InputDomainController(PrimitiveType type, String title, int width)
    {
        super();       
        
        // Inicializar controles
        initializaControls(type, title, width, new PrimitiveRandomTip());
    }
    
    public InputDomainController(PrimitiveType type, String title, int width, PrimitiveRandomTip primitiveRandomTip)
    {
        super();       
        
        // Inicializar controles
        initializaControls(type, title, width, primitiveRandomTip);
    }
    
    private void initializaControls(PrimitiveType type, String title, int width, PrimitiveRandomTip tip)
    {
        // Atribuir título do controle
        this._title = title;
        
        // Atribuir tipo sob análise
        this._type = type;
        
        // Atribuir largura
        this._width = width;
        
        // Atribuir altura
        this._height = defineHeight();
        
        // Inicializar tipos primitivos
        this.tips = tip;
        
        // Definir tamanho
        this.setSize(this._width, this._height);
        this.setPreferredSize(new Dimension(this._width, this._height));
                
        // Criar controles na tela
        buildControls();   
    }
    
    // Define a altura dos controles a serem renderizados
    private int defineHeight()
    {
        int height = 0;
        
        if (this._type == PrimitiveType.String) 
            height = 120;
        else if (this._type == PrimitiveType.Character)
            height = 75;
        else if ((this._type == PrimitiveType.Long) ||
                 (this._type == PrimitiveType.Double) ||
                 (this._type == PrimitiveType.Float) ||
                 (this._type == PrimitiveType.Integer) ||
                 (this._type == PrimitiveType.Short) )
        {
            height = 75;
        }
        else
        {
            height = 75;
        } 
        
        return height;
    }
    
    // Executa rotinas de criacao dos controles
    private void buildControls()
    {           
        JTitlePanel newPanel = new JTitlePanel(this._title, null, null, null);
        
        // Definir tamanho
        newPanel.setSize(this._width, this._height - 5);
        newPanel.setPreferredSize(new Dimension(this._width, this._height - 5));
        
        // Identifica o tipo renderizado
        if (this._type == PrimitiveType.String) 
            buildControlsToString(newPanel);
        else if (this._type == PrimitiveType.Character)
            buildControlsToChar(newPanel);
        else if ((this._type == PrimitiveType.Long) ||
                 (this._type == PrimitiveType.Double) ||
                 (this._type == PrimitiveType.Float) ||
                 (this._type == PrimitiveType.Integer) ||
                 (this._type == PrimitiveType.Short) )
        {
            buildControlsToGeneric(this._type, newPanel);
        }
        else
        {
            buildControlsNotSupport(newPanel);
        }       
       
        this.add(newPanel);
    }
    
    // Controi os controles para os tipos nao suportados
    private void buildControlsNotSupport(JPanel mainPanel)
    {       
        JPanel auxPanel = new JPanel(new FlowLayout());
        
        JLabel label = new JLabel("Type not supported.");
      
        auxPanel.add(label);
                
        mainPanel.add(auxPanel);        
    }
    
    // Constroi os controles para os tipos inteiros, float, decimal e short
    private void buildControlsToGeneric(PrimitiveType type, JPanel mainPanel)
    {
        JPanel auxPanel = new JPanel(new FlowLayout());
        
        this._title += " (" + type.toString().toLowerCase() + ")";
        
        JLabel labelMin = new JLabel(type.toString() + " values - Min: ");        
        JLabel labelMax = new JLabel(" Max: ");        
        
        textMin = new JTextField(10);        
        textMin.setColumns(5);
        
        textMax = new JTextField(10);        
        textMax.setColumns(5);    
        
        labelMin.setAlignmentX(Component.LEFT_ALIGNMENT);
        textMin.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelMax.setAlignmentX(Component.LEFT_ALIGNMENT);
        textMax.setAlignmentX(Component.LEFT_ALIGNMENT);
                        
        auxPanel.add(labelMin);
        auxPanel.add(textMin);
        auxPanel.add(labelMax);
        auxPanel.add(textMax);        
        
        // Double    
        
        if (this._type == PrimitiveType.Double)
        {        
            this.textMax.setText(String.valueOf(tips.getMaximalValueDouble()));    
            this.textMin.setText(String.valueOf(tips.getMinimalValueDouble()));
        }
        // Long
        else if (this._type == PrimitiveType.Long)
        {    
            this.textMax.setText(String.valueOf(tips.getMaximalValueLong()));    
            this.textMin.setText(String.valueOf(tips.getMinimalValueLong()));
        }
        // Integer
        else if (this._type == PrimitiveType.Integer)
        {    
            this.textMax.setText(String.valueOf(tips.getMaximalValueInt()));    
            this.textMin.setText(String.valueOf(tips.getMinimalValueInt()));
        }
        // Float
        else if (this._type == PrimitiveType.Float)
        {    
            this.textMax.setText(String.valueOf(tips.getMaximalValueFloat()));    
            this.textMin.setText(String.valueOf(tips.getMinimalValueFloat()));
        }
        // Short
        else if (this._type == PrimitiveType.Short)
        {    
            this.textMax.setText(String.valueOf(tips.getMaximalValueShort()));    
            this.textMin.setText(String.valueOf(tips.getMinimalValueShort()));
        }
        
        mainPanel.add(auxPanel);
    }
    
    // Constroi os controles para o tipo char
    private void buildControlsToChar(JPanel mainPanel)
    {
        JPanel auxPanel = new JPanel(new FlowLayout());
        
        this._title += " (char)";
        
        JLabel label = new JLabel("Char Values (separated by comma):");
        
        textChar = new JTextField(30);
        
        textChar.setColumns(20);
        
        auxPanel.add(label);
        auxPanel.add(textChar);
        
        mainPanel.add(auxPanel);
    }
    
    // Constroi os controles para string
    private void buildControlsToString(JPanel mainPanel)
    {
        JPanel auxPanel = new JPanel(new FlowLayout());
        
        this._title += " (string)";
        
        JLabel label = new JLabel("String Values (separated by comma):");
        
        text = new JTextArea();
        text.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        
        text.setColumns(20);
        text.setLineWrap(true);
        text.setRows(5);
        
        auxPanel.add(label);
        auxPanel.add(text);
                
        mainPanel.add(auxPanel);
    } 
    
    public PrimitiveRandomTip  getPrimitiveRandomTip()
    {
        PrimitiveRandomTip tips = new PrimitiveRandomTip();
        
        // String      
        if (this._type == PrimitiveType.String)
        {
            if (!this.text.getText().equals(""))
                tips.setPossibleStrings(this.text.getText().split(","));
            
            return tips;
        }
        // Char
        else if (this._type == PrimitiveType.Character)
        {
            if (!this.textChar.getText().equals(""))
            {
                String[] charValuesTextBox = this.textChar.getText().split(",");
                char[] charValues = new char[charValuesTextBox.length];
                int i = 0;
                for (String charValue : charValuesTextBox) {
                    charValues[i] = charValue.charAt(0);
                    i++;
                }
                tips.setPossibleChar(charValues);
            }
            
            return tips;
        }
        // Double    
        else if (this._type == PrimitiveType.Double)
        {        
            tips.setMaximalValueDouble(Double.parseDouble(this.textMax.getText()));
            tips.setMinimalValueDouble(Double.parseDouble(this.textMin.getText()));
            
            return tips;
        }
        // Long
        else if (this._type == PrimitiveType.Long)
        {    
            tips.setMaximalValueLong(Long.parseLong(this.textMax.getText()));
            tips.setMinimalValueLong(Long.parseLong(this.textMin.getText()));
            
            return tips;
        }
        // Integer
        else if (this._type == PrimitiveType.Integer)
        {    
            tips.setMaximalValueInt(Integer.parseInt(this.textMax.getText()));
            tips.setMinimalValueInt(Integer.parseInt(this.textMin.getText()));
            
            return tips;
        }
        // Float
        else if (this._type == PrimitiveType.Float)
        {    
            tips.setMaximalValueFloat(Float.parseFloat(this.textMax.getText()));
            tips.setMinimalValueFloat(Float.parseFloat(this.textMin.getText()));
            
            return tips;
        }
        // Short
        else if (this._type == PrimitiveType.Short)
        {    
            tips.setMaximalValueShort(Short.parseShort(this.textMax.getText()));
            tips.setMinimalValueShort(Short.parseShort(this.textMin.getText()));
            
            return tips;
        }
                
        return null;
    }    
}
