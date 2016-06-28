
package br.jabuti.agdtpoo;

import br.jabuti.agdtpoo.control.TestCaseGenerationUploadPlugInController;
import br.jabuti.agdtpoo.model.TestDataGenerationPlugIn;
import br.jabuti.agdtpoo.view.TestCaseGenerationDialog;
import br.jabuti.agdtpoo.view.TestCaseGenerationUploadPlugInDialog;
import br.jabuti.project.JabutiProject;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationResource;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBuilder {

        private JabutiProject _jbtProject;
        // AGDTPOO Menu
	private JMenu agdtpooMenu = new JMenu();        
        // AGDTPOO ItemMenu        
        private JMenuItem agdtpooView = new JMenuItem();      
        // AGDTPOO Add New PlugIn ItemMenu        
        private JMenuItem agdtpooAddNewPlugInView = new JMenuItem();  
        // PlugIns instalados na JaBUTi
        private TestDataGenerationPlugIn[] plugIns = null;
                    
        // AGDTPOO build menu 
        public void buildTestDataGenerationMenu(JMenuBar menuBar, JabutiProject jabutiProject) {

                // Atribuição do projeto da JaBUTi
                this._jbtProject = jabutiProject;
            
		// AGDTPOO Menu
		agdtpooMenu.setText("JaBTeG");
		agdtpooMenu.setMnemonic('C');

                // Criação da opção de menu do basic test data generation
		//agdtpooView.setText(TestCaseGenerationDialog.FORMLABEL);
		//agdtpooView.addActionListener(new java.awt.event.ActionListener() {
                //         @Override
		//	public void actionPerformed(ActionEvent e) {
		//		agdtpooView_actionPerformed(e);
		//	}
		//});
                //agdtpooMenu.add(agdtpooView);
                
                //agdtpooMenu.addSeparator();
                
                agdtpooAddNewPlugInView.setText(TestCaseGenerationUploadPlugInDialog.FORMLABEL);
		agdtpooAddNewPlugInView.addActionListener(new java.awt.event.ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				agdtpooViewAddNewPlugIn_actionPerformed(e);
			}
		});
                agdtpooMenu.add(agdtpooAddNewPlugInView);

                // Adicionar intes dinâmicos ao menu
                buildDynamicMenu();
                
		// Adding Summary Menu to MenuBar
		menuBar.add(agdtpooMenu);
	}
        
        void buildDynamicMenu()
        {
            plugIns = TestCaseGenerationUploadPlugInController.getPlugIns();
            
            if (plugIns.length == 0)
                return;
            
            agdtpooMenu.addSeparator();
            
            for (TestDataGenerationPlugIn testDataGenerationPlugIn : plugIns) {
                // Adicionar novo item de menu
                addNewPlugInMenuItem(testDataGenerationPlugIn);
            }
        }
        
        public void addNewPlugInMenuItem(TestDataGenerationPlugIn testDataGenerationPlugIn)
        {
            JMenuItem plugInItem = new JMenuItem();

            // Definir o texto do item do menu
            plugInItem.setText(testDataGenerationPlugIn.Name);

            // Adição de evento ao clique do item
            plugInItem.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                            agdtpooView_plugInPerformed(e);
                    }
            });    

            // Adicionar o item ao menu
            agdtpooMenu.add(plugInItem);
        }
        
        // Fernando Henrique - AGDTPOO
	void agdtpooView_plugInPerformed(ActionEvent e) {
            
            TestDataGenerationResource resource = null;
            TestDataGenerationPlugIn plugIn = getPlugInByName(e.getActionCommand());
                    
            if (plugIn == null)
                showWarning("Plug-in not found. Try to uninstall and install plug-in again.");
            
            try
            {                
                // Captura dos recursos para preenchimento da tela
                resource = TestCaseGenerationUploadPlugInController.getResources(plugIn);
            
                TestCaseGenerationDialog testDialog = new TestCaseGenerationDialog(this._jbtProject, resource);
                testDialog.setLocationRelativeTo(null);
                testDialog.setVisible(true); 
            }
            catch(Exception ex)
            {
                showWarning("Error during plug-in load. Erro: " + ex.getMessage());
            }
	}
        
        // Fernando Henrique - AGDTPOO
	void agdtpooView_actionPerformed(ActionEvent ex) {
            TestCaseGenerationDialog testDialog = new TestCaseGenerationDialog(this._jbtProject);
            testDialog.setLocationRelativeTo(null);
            testDialog.setVisible(true);                    
	}
        
        // Fernando Henrique - Add new plug-in
	void agdtpooViewAddNewPlugIn_actionPerformed(ActionEvent ex) {
            TestCaseGenerationUploadPlugInDialog testDialog = new TestCaseGenerationUploadPlugInDialog();            
            testDialog.setMenuBuilder(this);
            testDialog.setLocationRelativeTo(null);
            testDialog.setVisible(true);
	}
        
        public void enabledMenu()
        {
            if (agdtpooMenu != null)
                agdtpooMenu.setEnabled(true);
        }
        
        public void disabledMenu()
        {
            if (agdtpooMenu != null)
                agdtpooMenu.setEnabled(false);
        }
        
        public void setJabutiProject(JabutiProject jabutiProject)
        {
            this._jbtProject = jabutiProject;
        }
        
        private TestDataGenerationPlugIn getPlugInByName(String plugInName)
        {
            plugIns = TestCaseGenerationUploadPlugInController.getPlugIns();
            
            for (TestDataGenerationPlugIn testDataGenerationPlugIn : plugIns) {
                if (testDataGenerationPlugIn.Name.equals(plugInName))
                {
                    return testDataGenerationPlugIn;
                }
            }
            
            return null;
        }     
                    
        private void showWarning(String message)
        {
            JOptionPane.showMessageDialog(null, message, "JaBUTi", JOptionPane.WARNING_MESSAGE);
        }
    
}
