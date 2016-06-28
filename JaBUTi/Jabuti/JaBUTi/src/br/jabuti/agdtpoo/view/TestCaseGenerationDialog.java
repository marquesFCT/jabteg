
package br.jabuti.agdtpoo.view;

import br.jabuti.gui.JabutiGUI;
import br.jabuti.agdtpoo.control.TestCaseController;
import br.jabuti.agdtpoo.control.TestCaseGenerationController;
import br.jabuti.agdtpoo.control.TestCaseGenerationDialogController;
import br.jabuti.agdtpoo.model.ContextTestDataGeneration;
import br.jabuti.agdtpoo.model.InputMethodDomain;
import br.jabuti.agdtpoo.model.UtilBehaviors;
import br.jabuti.lookup.Program;
import br.jabuti.project.ClassFile;
import br.jabuti.project.JabutiProject;
import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.entity.GUIGenerationCustomParameter;
import br.usp.each.saeg.agdtpoo.entity.IGUIGenerationStrategy;
import br.usp.each.saeg.agdtpoo.entity.InputPrimitiveDomain;
import br.usp.each.saeg.agdtpoo.entity.MethodPrimitiveDomain;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationResource;
import br.usp.each.saeg.agdtpoo.entity.TestGenerationResult;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.BaseGenerationStrategy;
import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.apache.bcel.classfile.Method;

public class TestCaseGenerationDialog extends javax.swing.JDialog implements ListSelectionListener {

    enum DialogMode
    {
        Dynamic,
        Static
    }
    
    private IGUIGenerationStrategy _selectedStrategy;
    public static String FORMLABEL = "Basic Test Data Generation";
    private TestDataGenerationResource _resource;
    private JabutiProject _jbtProject;
    private ArrayList<InputMethodDomain> _methodDomains;

    @Override
    public void valueChanged(ListSelectionEvent e) {
        
        boolean isEnabled = true;
        
        if (this.lstTestTechnique.getSelectedValue() == null)
            return;
        
        String selectedValue = this.lstTestTechnique.getSelectedValue().toString();
         
        BaseGenerationStrategy strategy = _resource.getStrategy(selectedValue);

        if (strategy == null)
            return;

        _selectedStrategy = (IGUIGenerationStrategy)((Object)strategy);

        populateControls(); 
    }
       
    public TestCaseGenerationDialog(JabutiProject jbtProject, TestDataGenerationResource resource) {
        super(JabutiGUI.mainWindow(), resource.getPlugInName(), true);
        
        initializeDependencies(jbtProject, resource);
        
        initComponents();
        
        populateControls();
    }    
    
    public TestCaseGenerationDialog(JabutiProject jbtProject) {
        super(JabutiGUI.mainWindow(), FORMLABEL, true);
        
        initializeDependencies(jbtProject, null);
        
        initComponents();
        
        populateControls();
    }

    private void enableControls()
    {
        boolean isEnabled = true;

        this.lstFitnessFunction.setBackground(Color.white);
        this.lstSelectionTechnique.setBackground(Color.white);
        this.txtMaxCountOfInteractions.setBackground(Color.white);
        
        this.lstFitnessFunction.enable(isEnabled);
        this.lstSelectionTechnique.enable(isEnabled);    
        this.txtMaxCountOfInteractions.enable(isEnabled); 
    }
    
    private void disableControls()
    {
        disableSelection();
        disableFitness();
        
        this.txtMaxCountOfInteractions.setBackground(Color.getColor("CCCCCC"));
          
        this.txtMaxCountOfInteractions.enable(false); 
    }
    
    private void disableSelection()
    {
        this.lstSelectionTechnique.enable(false);
        this.lstSelectionTechnique.clearSelection();
        this.lstSelectionTechnique.setBackground(Color.getColor("CCCCCC"));
    }
    
    private void disableFitness()
    {
        this.lstFitnessFunction.enable(false);
        this.lstFitnessFunction.clearSelection();
        this.lstFitnessFunction.setBackground(Color.getColor("CCCCCC"));
    }
    
    private void enableCriterions()
    {
        boolean isEnabled = true;
        
        this.lstCriterion.setBackground(Color.white);
        
        this.lstCriterion.enable(isEnabled);
    }  
    
    private void disableCriterions()
    {
        boolean isEnabled = false;
        
        this.lstCriterion.clearSelection();
        this.lstCriterion.setBackground(Color.getColor("CCCCCC"));
        
        this.lstCriterion.enable(isEnabled);
    }    
    
    private void initializeDependencies(JabutiProject jbtProject, TestDataGenerationResource resource)
    {               
        // Atibuir projeto recebido por parâmetro 
        this._jbtProject = jbtProject;
        
        // Recursos de geração de dados de teste
        this._resource = resource;
        
        // Inicializar lista com valores primitivos dos dominios dos metodos
        this._methodDomains = new ArrayList<InputMethodDomain>();        
    }
    
    private void populateControls()
    {
        // Popular caminhos de dependências para execução do framework
        populateEnvironmentVariablesControls();
        
        // Popular controles atrelados a entrada de valores do domínio
        populateInputDomainControls();
        
        if (this._selectedStrategy == null)
        {
            // Popular treeview de métodos das classes
            populateClassMethods();
            // Popular técnicas de geração
            populateGenerationTechniques();
            
            // Desabilitar controles
            disableControls();
            disableCriterions();
        }
        else
        {
            // Habilitar controle
            enableControls();
            enableCriterions();
        }
        
        // Popular fitnesses
        populateFitnesses();
        
        // Popular os métodos de seleção
        populateSelections();
        
        // Popular critérios de teste
        populateCriterions();
        
        // Popular parâmetros
        populateParameters();
    }
    
    private void populateParameters()
    {
        this.pnlCustomParameter.removeAll();
        
        if (this._selectedStrategy == null)
            return;
                       
        GUIGenerationCustomParameter[] parameters = this._selectedStrategy.getCustomParameters();
        
        this.pnlCustomParameter.setLayout(new GridBagLayout());
        
        // Verifica se os parametros da tecnica de teste nao sao nulos
        if (parameters == null)
            return;
        
        for (GUIGenerationCustomParameter customParameter : parameters) {
            
            GridBagConstraints constraint = new GridBagConstraints();
            constraint.fill = GridBagConstraints.HORIZONTAL;
            constraint.anchor = GridBagConstraints.PAGE_START;       
            constraint.gridx = 0;
            constraint.weighty = 5;
            constraint.gridy = this.pnlCustomParameter.getComponentCount();
            
            CustomParameterController newCustomParameter = new CustomParameterController(customParameter);
            
            this.pnlCustomParameter.add(newCustomParameter, constraint);
        }                 
    }
    
    private void populateInputDomainControls()
    {
        PrimitiveRandomTip tips = new PrimitiveRandomTip();
        
        // String
        this.txtStringValues.setText("");
        // Char
        this.txtCharValues.setText("");
        // Double
        this.txtDoubleMax.setText(String.valueOf(tips.getMaximalValueDouble()));
        this.txtDoubleMin.setText(String.valueOf(tips.getMinimalValueDouble()));
        // Long
        this.txtLongMax.setText(String.valueOf(tips.getMaximalValueLong()));
        this.txtLongMin.setText(String.valueOf(tips.getMinimalValueLong()));
        // Integer
        this.txtIntMax.setText(String.valueOf(tips.getMaximalValueInt()));
        this.txtIntMin.setText(String.valueOf(tips.getMinimalValueInt()));
        // Float
        this.txtFloatMax.setText(String.valueOf(tips.getMaximalValueFloat()));
        this.txtFloatMin.setText(String.valueOf(tips.getMinimalValueFloat()));
        // Short
        this.txtShortMax.setText(String.valueOf(tips.getMaximalValueShort()));
        this.txtShortMin.setText(String.valueOf(tips.getMinimalValueShort()));
        // Max Depth
        this.txtMaxDepth.setText(String.valueOf(tips.getObjectGenerationMaxDepth()));
        // Max cout of interactions
        this.txtMaxCountOfInteractions.setText(String.valueOf(tips.getMaxCountOfInteractions()));
    }
    
    private void populateEnvironmentVariablesControls()
    {
        this.txtJUnitPath.setText("C:\\Program Files (x86)\\JUnit\\junit-4.10.jar");
        this.txtDependenciesPath.setText("C:\\JavaDev\\Tests\\JavaLibraryTeste\\build\\classes\\");
        this.txtJARPath.setText("C:\\JavaDev\\Tests\\JavaLibraryTeste\\dist\\JavaLibraryTeste.jar");
    }
    
    private void populateFitnesses()
    {
        DefaultListModel lista = new DefaultListModel();
        String[] generationFitnesses = null;
             
        if (this._selectedStrategy != null)
            generationFitnesses = TestCaseGenerationController.getGenerationFitnessesLabel(this._selectedStrategy);
        else
            generationFitnesses = new String[] {" "};
        
        if (generationFitnesses != null)
        {
            for (String fitness : generationFitnesses) {
                lista.addElement(fitness);
            }
        }
        
        if (generationFitnesses.length == 1 && generationFitnesses[0].equals(""))
            disableFitness();
        
        this.lstFitnessFunction.setModel(lista);
    }
    
    private void populateCriterions()
    {
        String[] criterions = null;
        
        if (this._selectedStrategy != null)
            criterions = TestCaseGenerationController.getCriterions(this._selectedStrategy);
        else
            criterions = new String[] {" "};
        
        DefaultListModel lista = new DefaultListModel();

        for (String criterion : criterions) {
            lista.addElement(criterion);
        }
        
        this.lstCriterion.setModel(lista);
    }
    
    private void populateSelections()
    {
        DefaultListModel lista = new DefaultListModel();
        String[] selections = null;
               
        if (this._selectedStrategy != null)
            selections = TestCaseGenerationController.getGenerationSelectionLabel(this._selectedStrategy);
        else
            selections = new String[] { " " };
        
        for (String selection : selections) {
            lista.addElement(selection);
        }
        
        if (selections.length == 1 && selections[0].equals(""))
            disableSelection();
        
        this.lstSelectionTechnique.setModel(lista);
    }
    
    private void populateGenerationTechniques()
    {
        DefaultListModel lista = new DefaultListModel();
        String[] generationStrategies = null;
                
        generationStrategies = TestCaseGenerationController.getGenerationStrategiesLabel(this._resource);
        
        for (String genStrategy : generationStrategies) {
            lista.addElement(genStrategy);
        }
        
        this.lstTestTechnique.setModel(lista);
        this.lstTestTechnique.addListSelectionListener(this);
    }
        
    private void populateClassMethods()
    {        
        if (this._jbtProject == null) {
            return;
        }
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Class under test");
        TreeModel tm = new DefaultTreeModel(root);
        Program p = this._jbtProject.getProgram();
        String[] packs = p.getCodePackages();
        int k = 1;

        Hashtable classes = this._jbtProject.getClassFilesTable();
	Iterator it = classes.values().iterator();

	while (it.hasNext()) {
            ClassFile cf = (ClassFile) it.next();         
            DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(cf.getClassName());            
            Method[] methods = cf.getMethods();
            
            for (int i = 0; i < methods.length; i++)
            {
                Method currentMethod = methods[i];
                
                if (currentMethod.isPrivate())
                    continue;
                                
                String methodName = UtilBehaviors.getMethodSignature(currentMethod.toString());
                                                  
                if (methodName.contains("<clinit>"))
                    continue;
                
                if (methodName.contains("<init>"))
                    continue;
                
                DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode(methodName);

                classNode.add(methodNode);                
            }
            
            root.add(classNode);
        }
        
        this.treClassMethods.setModel(tm);
        int nRow = this.treClassMethods.getRowCount();         
    }
    
    private PrimitiveRandomTip  getPrimitiveRandomTips()
    {
        PrimitiveRandomTip tips = new PrimitiveRandomTip();
        
        // String        
        if (!this.txtStringValues.getText().equals(""))
        {
            tips.setPossibleStrings(this.txtStringValues.getText().split(","));
        }
        // Char
        if (!this.txtCharValues.getText().equals(""))
        {
            String[] charValuesTextBox = this.txtCharValues.getText().split(",");
            char[] charValues = new char[charValuesTextBox.length];
            int i = 0;
            for (String charValue : charValuesTextBox) {
                charValues[i] = charValue.charAt(0);
                i++;
            }
            tips.setPossibleChar(charValues);
        }
        // Double        
        tips.setMaximalValueDouble(Double.parseDouble(this.txtDoubleMax.getText()));
        tips.setMinimalValueDouble(Double.parseDouble(this.txtDoubleMin.getText()));
        // Long
        tips.setMaximalValueLong(Long.parseLong(this.txtLongMax.getText()));
        tips.setMinimalValueLong(Long.parseLong(this.txtLongMin.getText()));
        // Integer
        tips.setMaximalValueInt(Integer.parseInt(this.txtIntMax.getText()));
        tips.setMinimalValueInt(Integer.parseInt(this.txtIntMin.getText()));
        // Float
        tips.setMaximalValueFloat(Float.parseFloat(this.txtFloatMax.getText()));
        tips.setMinimalValueFloat(Float.parseFloat(this.txtFloatMin.getText()));
        // Short
        tips.setMaximalValueShort(Short.parseShort(this.txtShortMax.getText()));
        tips.setMinimalValueShort(Short.parseShort(this.txtShortMin.getText()));
        // Max Depth
        tips.setObjectGenerationMaxDepth(Integer.parseInt(this.txtMaxDepth.getText()));
        // Max count of interactions
        tips.setMaxCountOfInteractions(Integer.parseInt(this.txtMaxCountOfInteractions.getText()));
        
        return tips;
    }
            
    // Encaminha a execucao do teste para a controller
    private void testMethod(ContextTestDataGeneration context)
    {
        TestCaseController tcController = new TestCaseController();
        
        try
        {            
            tcController.executeTest(context);
            
            TestGenerationResult result = null;
            
            // Captura do resultado da execução
            result = tcController.getTestResult();
            
            this.txtResult.setText(tcController.getUnitTestResult());
            this.txtTrace.setText(tcController.getTrace());    
                
            // Formata o resultado da execução
            // para apresentar ao usuário final um conjunto 
            // de informações relevantes sobre a execução
            formatResult(result);
        }
        catch(java.lang.Throwable ex)
        {
            this.txtResultGeneration.setText(ex.getMessage()); 
            this.txtTrace.setText(tcController.getTrace());
        }
    }
    
    // Apresenta um conjunto de informações relevantes sobre a execução dos testes
    private void formatResult(TestGenerationResult result)
    {
        String showResult = "";
        
        int minutes = 0;
        double second = 0;
        
        if (result.getDuration() > 60)
        {
            minutes = (int)(result.getDuration() / 60);
            second = result.getDuration() - (minutes * 60);
        }
        else
        {
            second = result.getDuration();
        }            
                
        showResult += "Class under test: " + result.getClassUnderTest() + "\n";
        showResult += "Method under test: " + result.getMethodUnderTest() + "\n";
        showResult += "Generation strategy: " + result.getGenerationStrategy() + "\n";        
        showResult += "Fitness: " + result.getFitness() + "\n";        
        showResult += "Selection: " + result.getSelectionTechique() + "\n";                
        showResult += "Iteractions: " + String.valueOf(result.getCountOfInteractions()) + "\n";
        showResult += "Executions: " + String.valueOf(result.getCountOfExecutions()) + "\n";
        if (minutes == 0)            
            showResult += "Execution time: " + String.valueOf(second) + " seconds\n";
        else
            showResult += "Execution time: " + String.valueOf(minutes) + " minutes and " + String.valueOf(second) + " seconds \n";
                
        showResult += "Initial time: " + String.valueOf(result.getInitialDate()) + "\n";
        showResult += "Final time: " + String.valueOf(result.getFinalDate()) + "\n";
        showResult += "Requirements: " + String.valueOf(result.getCountOfRequirements()) + "\n";
        showResult += "Covered requirements: " + String.valueOf(result.getCountOfCoveredRequirements()) + "\n";
        showResult += "Uncovered requirements: " + String.valueOf(result.getCountOfUncoveredRequirements()) + "\n";
        
        this.txtResultGeneration.setText(showResult); 
    }
    
    // Exibicao de mensagens de erro
    private void showValidationErros(String message)
    {
        JOptionPane.showMessageDialog(rootPane, message, FORMLABEL, JOptionPane.WARNING_MESSAGE);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtShortMin1 = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        btnGenerateTest = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        treClassMethods = new javax.swing.JTree();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtResultGeneration = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtTrace = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtIntMax = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtLongMax = new javax.swing.JTextField();
        txtLongMin = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtFloatMax = new javax.swing.JTextField();
        txtShortMax = new javax.swing.JTextField();
        txtDoubleMin = new javax.swing.JTextField();
        txtFloatMin = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtIntMin = new javax.swing.JTextField();
        txtShortMin = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtDoubleMax = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaxDepth = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtCharValues = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtStringValues = new javax.swing.JTextArea();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 80), new java.awt.Dimension(0, 80), new java.awt.Dimension(32767, 80));
        jPanel16 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        pnlCustomInputDomain = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtJUnitPath = new javax.swing.JTextField();
        txtDependenciesPath = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtJARPath = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtResult = new javax.swing.JTextArea();
        jPanel18 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtInitialPopulationSize = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtMaxCountOfInteractions = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        pnlCustomParameter = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstTestTechnique = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstFitnessFunction = new javax.swing.JList();
        jPanel13 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        lstSelectionTechnique = new javax.swing.JList();
        jPanel14 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        lstCriterion = new javax.swing.JList();

        txtShortMin1.setText("jTextField1");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        btnGenerateTest.setText("Execute Test");
        btnGenerateTest.setName("btnExecuteTest"); // NOI18N
        btnGenerateTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateTestActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Select a class method to test:");

        treClassMethods.setAutoscrolls(true);
        treClassMethods.setName("treClassMethod"); // NOI18N
        treClassMethods.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                methodSelectedChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treClassMethods);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtResultGeneration.setColumns(20);
        txtResultGeneration.setRows(5);
        jScrollPane7.setViewportView(txtResultGeneration);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Result", jPanel12);

        txtTrace.setColumns(20);
        txtTrace.setRows(5);
        txtTrace.setName("txtTrace"); // NOI18N
        jScrollPane3.setViewportView(txtTrace);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Trace", jPanel5);

        jLabel12.setText("Max Value:");

        txtIntMax.setText("jTextField2");
        txtIntMax.setMinimumSize(new java.awt.Dimension(59, 20));

        jLabel7.setText("Max Value:");

        jLabel9.setText("DOUBLE - Min Value:");

        jLabel13.setText("SHORT - Min Value:");

        txtLongMax.setText("jTextField2");
        txtLongMax.setMinimumSize(new java.awt.Dimension(59, 20));

        txtLongMin.setText("jTextField1");
        txtLongMin.setMinimumSize(new java.awt.Dimension(59, 20));

        jLabel8.setText("Max Value:");

        txtFloatMax.setText("jTextField2");
        txtFloatMax.setMinimumSize(new java.awt.Dimension(59, 20));

        txtShortMax.setText("jTextField2");
        txtShortMax.setMinimumSize(new java.awt.Dimension(59, 20));

        txtDoubleMin.setText("jTextField1");
        txtDoubleMin.setMinimumSize(new java.awt.Dimension(59, 20));

        txtFloatMin.setText("jTextField1");
        txtFloatMin.setMinimumSize(new java.awt.Dimension(59, 20));

        jLabel6.setText("INT - Min Value:");

        txtIntMin.setText("jTextField1");
        txtIntMin.setMinimumSize(new java.awt.Dimension(59, 20));

        txtShortMin.setText("jTextField1");
        txtShortMin.setMinimumSize(new java.awt.Dimension(59, 20));

        jLabel10.setText("Max Value:");

        jLabel11.setText("FLOAT - Min Value:");

        jLabel14.setText("Max Value:");

        txtDoubleMax.setText("jTextField2");
        txtDoubleMax.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtDoubleMax.setMinimumSize(new java.awt.Dimension(59, 20));

        jLabel15.setText("LONG - Min Value:");

        jLabel3.setText("Max Depth:");

        txtMaxDepth.setText("jTextField1");
        txtMaxDepth.setMinimumSize(new java.awt.Dimension(59, 20));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtFloatMin, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txtFloatMax, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtLongMin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(txtDoubleMin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel14)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLongMax, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(txtDoubleMax, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)))))
                    .addComponent(jLabel11))
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIntMin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(txtMaxDepth, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(txtShortMin, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIntMax, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(txtShortMax, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIntMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(txtLongMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLongMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIntMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13)
                    .addComponent(txtShortMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtShortMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDoubleMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDoubleMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(txtFloatMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaxDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFloatMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel16.setText("Char Values (Separated by comma):");

        jLabel17.setText("String Values (Separated by comma):");

        txtStringValues.setColumns(20);
        txtStringValues.setLineWrap(true);
        txtStringValues.setRows(5);
        jScrollPane5.setViewportView(txtStringValues);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCharValues)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtCharValues, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Input Domain", jPanel4);

        javax.swing.GroupLayout pnlCustomInputDomainLayout = new javax.swing.GroupLayout(pnlCustomInputDomain);
        pnlCustomInputDomain.setLayout(pnlCustomInputDomainLayout);
        pnlCustomInputDomainLayout.setHorizontalGroup(
            pnlCustomInputDomainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1358, Short.MAX_VALUE)
        );
        pnlCustomInputDomainLayout.setVerticalGroup(
            pnlCustomInputDomainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 216, Short.MAX_VALUE)
        );

        jScrollPane10.setViewportView(pnlCustomInputDomain);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Input Domain - Selected Method", jPanel16);

        jLabel5.setText("JUnit Path:");

        txtJUnitPath.setText("jTextField1");

        txtDependenciesPath.setText("jTextField3");

        jLabel20.setText("Dependencies Path:");

        jLabel21.setText("JAR Path:");

        txtJARPath.setText("jTextField1");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel21))
                        .addGap(77, 77, 77)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJUnitPath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                            .addComponent(txtJARPath, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(34, 34, 34)
                        .addComponent(txtDependenciesPath, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJUnitPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDependenciesPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(9, 9, 9)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJARPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addContainerGap(132, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Environment Variables", jPanel9);

        txtResult.setColumns(20);
        txtResult.setRows(5);
        jScrollPane6.setViewportView(txtResult);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Resulting Individuals", jPanel10);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel18.setText("Initial size of population:");
        jLabel18.setMaximumSize(new java.awt.Dimension(140, 14));
        jLabel18.setMinimumSize(new java.awt.Dimension(140, 14));
        jLabel18.setPreferredSize(new java.awt.Dimension(160, 14));

        txtInitialPopulationSize.setText("50");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtInitialPopulationSize, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInitialPopulationSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel22.setText("Max count of attempts:");
        jLabel22.setMaximumSize(new java.awt.Dimension(140, 14));
        jLabel22.setMinimumSize(new java.awt.Dimension(140, 14));
        jLabel22.setPreferredSize(new java.awt.Dimension(160, 14));

        txtMaxCountOfInteractions.setText("50");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(txtMaxCountOfInteractions, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaxCountOfInteractions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlCustomParameterLayout = new javax.swing.GroupLayout(pnlCustomParameter);
        pnlCustomParameter.setLayout(pnlCustomParameterLayout);
        pnlCustomParameterLayout.setHorizontalGroup(
            pnlCustomParameterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 853, Short.MAX_VALUE)
        );
        pnlCustomParameterLayout.setVerticalGroup(
            pnlCustomParameterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 184, Short.MAX_VALUE)
        );

        jScrollPane11.setViewportView(pnlCustomParameter);

        jLabel25.setText("Custom parameters:");
        jLabel25.setMaximumSize(new java.awt.Dimension(140, 14));
        jLabel25.setMinimumSize(new java.awt.Dimension(140, 14));
        jLabel25.setPreferredSize(new java.awt.Dimension(160, 14));

        jLabel26.setText("Default parameters:");
        jLabel26.setMaximumSize(new java.awt.Dimension(140, 14));
        jLabel26.setMinimumSize(new java.awt.Dimension(140, 14));
        jLabel26.setPreferredSize(new java.awt.Dimension(160, 14));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jPanel8, 0, 44, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Parameters", jPanel18);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setToolTipText("");

        jLabel2.setText("Select an input data generation technique:");

        lstTestTechnique.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstTestTechnique.setName("lstTestTechnique"); // NOI18N
        jScrollPane2.setViewportView(lstTestTechnique);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Choose a fitness function:");

        lstFitnessFunction.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstFitnessFunction.setName("lstFitnessFunction"); // NOI18N
        jScrollPane4.setViewportView(lstFitnessFunction);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.setToolTipText("");

        jLabel23.setText("Choose a selection technique:");

        lstSelectionTechnique.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstSelectionTechnique.setName("lstTestTechnique"); // NOI18N
        jScrollPane8.setViewportView(lstSelectionTechnique);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addComponent(jLabel23))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.setToolTipText("");

        jLabel24.setText("Criterion:");

        lstCriterion.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstCriterion.setName("lstTestTechnique"); // NOI18N
        jScrollPane9.setViewportView(lstCriterion);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jLabel24))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGenerateTest, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(btnGenerateTest, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Tab");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Acao do botao de executar o teste
    private void btnGenerateTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateTestActionPerformed
        this.txtTrace.setText("");
        this.txtResult.setText("");
        this.txtResultGeneration.setText("");
        
        int initialPopulationSize = 50;
        String testCriterion = "";
        String jarPath = "";
        String dependenciesPath = "";
        String junitPath = "";
        String selectedMethod = "";
        String selectedClass = "";
        String selectedFitness = "";
        String selectedTestTechnique = "";
        String selectedSelectionTechnique = "";
        TreePath[] path = null;
        PrimitiveRandomTip tips = null;
        PrimitiveRandomTip[] parameterTips = null;
        InputPrimitiveDomain inputDomain = null;
        
        // Inicializa o dominio de valores aleatorios dirigidos
        inputDomain = new InputPrimitiveDomain();
         
        // Capturando os valores de entrada para geração aleatória dirigida
        tips = getPrimitiveRandomTips();
        inputDomain.setIntermediateMethodPrimitiveRandomTip(tips);
              
        MethodPrimitiveDomain[] methodsDomain = (new TestCaseGenerationDialogController())
                                                            .getMethodsPrimitiveDomain(this._methodDomains.toArray(new InputMethodDomain[this._methodDomains.size()]));
        for (MethodPrimitiveDomain methodPrimitiveDomain : methodsDomain) {
           inputDomain.addMethodDomain(methodPrimitiveDomain) ;
        }
        
        // Capturando o método selecionado
        path = this.treClassMethods.getSelectionPaths();
        if (path != null && path.length > 0)
        {
            if (path[0].getPathCount() == 3)
            {
                selectedMethod = path[0].getLastPathComponent().toString();
                selectedClass = path[0].getParentPath().getLastPathComponent().toString();
            }
            else
            {
                showValidationErros("Select a method to generate test cases.");
                return;
            }
        }
        else
        {
            showValidationErros("Select a method to generate test cases.");
            return;
        }     

        // Capturar técnica de geração de dados de teste
        if (this.lstTestTechnique.getSelectedValue() == null)
        {
            showValidationErros("Select a technique to generate test cases.");
            return;
        }
        else
        {
            selectedTestTechnique = this.lstTestTechnique.getSelectedValue().toString();
        }      
        
        if (this.lstFitnessFunction.isEnabled())
        {
            // Capturar fitness de geração de dados de teste
            if (this.lstFitnessFunction.getSelectedValue() == null)
            {
                showValidationErros("Select a fitness to generate test cases.");
                return;
            }
            else
            {
                selectedFitness = this.lstFitnessFunction.getSelectedValue().toString();
            }  
        }
                
        if (this.lstSelectionTechnique.isEnabled())
        {
            // Capturar o método de seleção que será utilizado
            if (this.lstSelectionTechnique.getSelectedValue() == null)
            {
                showValidationErros("Choose a selection technique to generate test cases.");
                return;
            }
            else
            {
                selectedSelectionTechnique = this.lstSelectionTechnique.getSelectedValue().toString();
            }  
        }

        // Capturar o critério de teste que será utilizado
        if (this.lstCriterion.getSelectedValue() == null)
        {
            showValidationErros("Choose a criterion to generate test cases.");
            return;
        }
        else
        {
            testCriterion = this.lstCriterion.getSelectedValue().toString();
        }  
        
        junitPath = this.txtJUnitPath.getText();        
        if (junitPath.trim().equals(""))
        {
            showValidationErros("Define the JUnit framework path.");
            return;            
        }
        
        dependenciesPath = this.txtDependenciesPath.getText();        
        if (dependenciesPath.trim().equals(""))
        {
            showValidationErros("Define the dependencies path of the JAR file.");
            return;            
        }
        
        jarPath = this.txtJARPath.getText();        
        if (dependenciesPath.trim().equals(""))
        {
            showValidationErros("Define the JAR file to read all classes and methods.");
            return;            
        }
                
        if (!this.txtInitialPopulationSize.getText().equals(""))
        {
            initialPopulationSize = Integer.parseInt(this.txtInitialPopulationSize.getText());
                    
        }
        
        ContextTestDataGeneration context = new ContextTestDataGeneration();
        
        // Classe sob teste
        context.setClassUnderTest(selectedClass);
        // Método sob teste
        context.setMethodUnderTest(selectedMethod);
        // Tamanho inicial da população
        context.setInitialPopulationSize(initialPopulationSize);
        // Técnica de geração de dados de teste
        context.setTechniqueName(selectedTestTechnique);
        // Fitness
        context.setFitnessName(selectedFitness);
        // Técnica de seleção
        context.setSelectionName(selectedSelectionTechnique);
        // Caminho do arquivo JAR
        context.setJarPath(jarPath);
        // Caminho das dependências
        context.setDependenciesPath(dependenciesPath);
        // Caminho do arquivo JUnit
        context.setJunitPath(junitPath);        
        // Valores para geração aleatória dirigida
        context.setInputDomain(inputDomain);         
        // Projeto JaBUTi 
        context.setJbtProject(_jbtProject);
        // Captura o tipo de critério a ser utilizado
        EnumCriterion criterion = UtilBehaviors.getCriterion(testCriterion); 
        context.setCriterion(criterion);
        // Definir o contexto
        context.setResource(this._resource);
        // Capturar os parametros customizados
        GUIGenerationCustomParameter[] customParameters = null;
        customParameters = (new TestCaseGenerationDialogController()).getCustomParameters(this.pnlCustomParameter);
        context.setCustomParameters(customParameters);
        
        // Executar teste a partir da classe de contexto
        testMethod(context);          
    }//GEN-LAST:event_btnGenerateTestActionPerformed
           
    private void addControlsToGUI(InputDomainController[] controllers)
    {
        int rowsCount = controllers.length;
                
        this.pnlCustomInputDomain.setLayout(new GridLayout(rowsCount, 1));
                
        for (InputDomainController inputDomainController : controllers) {
            this.pnlCustomInputDomain.add(inputDomainController);
        }
    }
    
    private void clearInputDomainControls()
    {
        this.pnlCustomInputDomain.removeAll();
    }
    
    private void buildInputDomainControls(String method)
    {
        clearInputDomainControls();
                
        if (method.endsWith("()"))
            return;
        
        TestCaseGenerationDialogController testCaseController = new TestCaseGenerationDialogController();
        
        // Criar dominio de tipos primitivos para o método selecionado
        InputMethodDomain methodDomain = getIfMethodDomainExists(method);
                
        // Registrar o domínio do método se ele não existir na lista
        if (methodDomain == null) {
            methodDomain = testCaseController.getInputMethodDomain(method); 
        
            this._methodDomains.add(methodDomain);         
        }
        
        // Criar controles a serem renderizados
        InputDomainController[] controllers = methodDomain.getInputDomainController();
        
        // Adiciona os controles a interface visual
        addControlsToGUI(controllers);
    }
    
    private InputMethodDomain getIfMethodDomainExists(String methodName)
    {        
        for (InputMethodDomain methodPrimitiveDomain : this._methodDomains) {
            if (methodPrimitiveDomain.getMethodName().equals(methodName))
                return methodPrimitiveDomain;
        }
        
        return null;
    }
    
    private void methodSelectedChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_methodSelectedChanged
        
        if (evt.getPath().getPathCount() != 3)
            return;
                
        // Captura o metodo selecionado
        String method = evt.getPath().getLastPathComponent().toString();
        
        // Constroi os controles do formulario
        buildInputDomainControls(method);
        
        this.pack();        
    }//GEN-LAST:event_methodSelectedChanged
            
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerateTest;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList lstCriterion;
    private javax.swing.JList lstFitnessFunction;
    private javax.swing.JList lstSelectionTechnique;
    private javax.swing.JList lstTestTechnique;
    private javax.swing.JPanel pnlCustomInputDomain;
    private javax.swing.JPanel pnlCustomParameter;
    private javax.swing.JTree treClassMethods;
    private javax.swing.JTextField txtCharValues;
    private javax.swing.JTextField txtDependenciesPath;
    private javax.swing.JTextField txtDoubleMax;
    private javax.swing.JTextField txtDoubleMin;
    private javax.swing.JTextField txtFloatMax;
    private javax.swing.JTextField txtFloatMin;
    private javax.swing.JTextField txtInitialPopulationSize;
    private javax.swing.JTextField txtIntMax;
    private javax.swing.JTextField txtIntMin;
    private javax.swing.JTextField txtJARPath;
    private javax.swing.JTextField txtJUnitPath;
    private javax.swing.JTextField txtLongMax;
    private javax.swing.JTextField txtLongMin;
    private javax.swing.JTextField txtMaxCountOfInteractions;
    private javax.swing.JTextField txtMaxDepth;
    private javax.swing.JTextArea txtResult;
    private javax.swing.JTextArea txtResultGeneration;
    private javax.swing.JTextField txtShortMax;
    private javax.swing.JTextField txtShortMin;
    private javax.swing.JTextField txtShortMin1;
    private javax.swing.JTextArea txtStringValues;
    private javax.swing.JTextArea txtTrace;
    // End of variables declaration//GEN-END:variables
}
