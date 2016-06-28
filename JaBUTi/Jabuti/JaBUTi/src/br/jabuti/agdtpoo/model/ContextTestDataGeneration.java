
package br.jabuti.agdtpoo.model;

import br.jabuti.agdtpoo.view.CustomParameterController;
import br.jabuti.project.JabutiProject;
import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.entity.GUIGenerationCustomParameter;
import br.usp.each.saeg.agdtpoo.entity.InputPrimitiveDomain;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationResource;

public class ContextTestDataGeneration {
    
    private TestDataGenerationResource _resource;
    private String _classUnderTest;
    private String _methodUnderTest;
    private int _initialPopulationSize;
    private String _techniqueName;
    private String _fitnessName;
    private String _selectionName;
    private String _jarPath;
    private String _dependenciesPath;
    private String _junitPath;
    private InputPrimitiveDomain _inputDomain;
    private JabutiProject _jbtProject;    
    private EnumCriterion _criterion;    
    private GUIGenerationCustomParameter[] _customParameters;
   
    public TestDataGenerationResource getResource() {
        return this._resource;
    }
            
    public void setCustomParameters(GUIGenerationCustomParameter[] customParameters)
    {
        this._customParameters = customParameters;
    }
    
    public GUIGenerationCustomParameter[] getCustomParameters()
    {
        return this._customParameters;
    }
    
    public void setResource(TestDataGenerationResource resource) {
        this._resource = resource;
    }
    
    public EnumCriterion getCriterion() {
        return this._criterion;
    }
            
    public void setCriterion(EnumCriterion criterion) {
        this._criterion = criterion;
    }
 
    public String getClassUnderTest() {
        return _classUnderTest;
    }

    public void setClassUnderTest(String _classUnderTest) {
        this._classUnderTest = _classUnderTest;
    }

    public String getDependenciesPath() {
        return _dependenciesPath;
    }

    public void setDependenciesPath(String _dependenciesPath) {
        this._dependenciesPath = _dependenciesPath;
    }

    public String getFitnessName() {
        return _fitnessName;
    }

    public void setFitnessName(String _fitnessName) {
        this._fitnessName = _fitnessName;
    }

    public int getInitialPopulationSize() {
        return _initialPopulationSize;
    }

    public void setInitialPopulationSize(int _initialPopulationSize) {
        this._initialPopulationSize = _initialPopulationSize;
    }

    public String getJarPath() {
        return _jarPath;
    }

    public void setJarPath(String _jarPath) {
        this._jarPath = _jarPath;
    }

    public JabutiProject getJbtProject() {
        return _jbtProject;
    }

    public void setJbtProject(JabutiProject _jbtProject) {
        this._jbtProject = _jbtProject;
    }

    public String getJunitPath() {
        return _junitPath;
    }

    public void setJunitPath(String _junitPath) {
        this._junitPath = _junitPath;
    }

    public String getMethodUnderTest() {
        return _methodUnderTest;
    }

    public void setMethodUnderTest(String _methodUnderTest) {
        this._methodUnderTest = _methodUnderTest;
    }

    public InputPrimitiveDomain getInputDomain() {
        return _inputDomain;
    }

    public void setInputDomain(InputPrimitiveDomain inputDomain) {
        this._inputDomain = inputDomain;
    }

    public String getSelectionName() {
        return _selectionName;
    }

    public void setSelectionName(String _selectionName) {
        this._selectionName = _selectionName;
    }

    public String getTechniqueName() {
        return _techniqueName;
    }

    public void setTechniqueName(String _techniqueName) {
        this._techniqueName = _techniqueName;
    }
}
