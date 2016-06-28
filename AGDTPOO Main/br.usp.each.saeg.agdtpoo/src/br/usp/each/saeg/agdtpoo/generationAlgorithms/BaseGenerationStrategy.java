
package br.usp.each.saeg.agdtpoo.generationAlgorithms;

import br.usp.each.saeg.agdtpoo.TestDataFacade;
import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.entity.GenerationCoverage;
import br.usp.each.saeg.agdtpoo.entity.GenerationRequirement;
import br.usp.each.saeg.agdtpoo.entity.ITestDataGenerationTool;
import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationToolException;
import br.usp.each.saeg.agdtpoo.util.GenerationTrace;

// Classe base para estratégias de geração de dados de teste
public abstract class BaseGenerationStrategy {
    
        protected String junitFileJavaPath = "c:\\temp\\AutomaticTest.java";
    
        protected EnumCriterion _criterion;
        protected int ExecutionCount;
        protected int InteractionCount;
        protected int PopulationSize;
        protected String JarPath;
        protected String ClassUnderTest;
        protected String MethodUnderTest;
        protected Individual[] CurrentPopulation;
        protected GenerationRequirement[] Targets;
        protected GenerationRequirement CurrentTarget;        
        protected Individual currentSolutionToRequirement; 
        
        protected ITestDataGenerationTool _testGenerationTool;
        
        public void initialize(String jarPath, int populationSize, 
                               String classUnderTest, String methodUnderTest)
        {
            this.JarPath = jarPath;
            this.ClassUnderTest = classUnderTest;
            this.MethodUnderTest = methodUnderTest;
            this.PopulationSize = populationSize;
        }
    
        public void setCriterion(EnumCriterion criterion)
        {
            this._criterion = criterion;
        }
        
        public int getCountOfInteractions()
        {
            return this.InteractionCount;
        }
        
        public int getCountOfExecutions()
        {
            return this.ExecutionCount;
        }
        
        public GenerationRequirement[] getRequirements()
        {
            return this.Targets;
        }
        
        public void setInitialPopulationSize(int populationSize)
        {
            this.PopulationSize = populationSize;
        }
        
        public void setTestDataGenerationTool(ITestDataGenerationTool testGenerationTool)
        {
            this._testGenerationTool = testGenerationTool;
        }
        
        // Passo 00 - Iniciar a geração automática de dados de teste
        public Individual[] generateData() throws TestStrategyException
        {                           
            // Zera a contagem de iterações
            this.InteractionCount = 0;
            GenerationTrace.write("Starting test data generation.");
            
            // Processo de inicialização 
            GenerationTrace.write("Initializing generation.");
            initializeGeneration();
              
            try                
            {
                // Geração da população inicial de indivíduos
                GenerationTrace.write("Generating initial population.");
                this.CurrentPopulation = generateInitialPopulation();
                            
                // Executa os indivíduos 
                GenerationTrace.write("Executing individuals.");
                executeIndividuals();
            
                // Identificação dos ramos/nós que devem ser cobertos
                GenerationTrace.write("Identifying targets.");
                this.Targets = identifyRequirements();
                                            
                // Atualizar os indivíduos
                GenerationTrace.write("Updating individuals.");
                updateIndividuals();
           
                GenerationTrace.write("Reading targets. Count: ", this.Targets.length);
                for (int i = 0; i < this.Targets.length; i++)
                {   
                    // Itera o contador de iterações
                    this.InteractionCount++;
                    
                    this.CurrentTarget = this.Targets[i];
                    GenerationTrace.write("New target selected. Target ", 
                                          this.CurrentTarget.getIdRequirement());

                    GenerationTrace.write("Verify if requirement ", 
                                          this.CurrentTarget.getIdRequirement(), 
                                          " has been covered.");
                    if (verifyIfRequirementHasBeenCovered(this.CurrentTarget))
                    {
                        GenerationTrace.write("Target ", 
                                              this.CurrentTarget.getIdRequirement(), 
                                              " covered.");
                        this.CurrentTarget.isCovered(true);
                    }
                    else
                    {
                        GenerationTrace.write("Target uncovered.");
                        this.CurrentTarget.isCovered(false);
                    }                    
                }
            }                                
            catch(TestDataGenerationToolException e)
            {
                throw new TestStrategyException(e);
            }
            catch(Exception e)
            {
                throw new TestStrategyException(e);
            }
            
            // Processo de finalização 
            GenerationTrace.write("Finalize generation.");
            finalizeGeneration();
        
            // Retorna a população atual
            GenerationTrace.write("Solution contains ", 
                                  this.CurrentPopulation.length, 
                                  " individuals.");
            return this.CurrentPopulation;
        }
    
        // Inicilizar o processo de geração de dados de teste
        protected abstract void initializeGeneration();

        // Gerar população
        protected abstract Individual[] generateInitialPopulation() 
                            throws TestDataGenerationToolException;

        // Identificar os requisitos que devem ser cobertos
        protected GenerationRequirement[] identifyRequirements() 
                            throws TestDataGenerationToolException {

            GenerationTrace.write("Identifying targets.");

            GenerationRequirement[] returnValue = 
                            this._testGenerationTool.getRequirements(this._criterion);

            return returnValue;
        }
           
        // Compila os indivíduos e os executa
        protected void executeIndividuals() throws TestDataGenerationToolException {

            executeIndividuals(this.CurrentPopulation);

        }

        // Compila os indivíduos e os executa
        protected void executeIndividuals(Individual[] population) 
                            throws TestDataGenerationToolException {

            GenerationTrace.write("Executing individuals.");

            // Criar arquivo JUnit
            TestDataFacade.createUnitTestFile(junitFileJavaPath, population);    

            // Compila os indivíduos
            this._testGenerationTool.compileIndividuals();

            this.ExecutionCount++;

            GenerationTrace.write("Individuals compiled with sucess. Execution No. ", this.ExecutionCount);
        }  

        // Atualizar os indivíduos com os resultados obtidos após a execução dos mesmos        
        protected void updateIndividuals() throws TestDataGenerationToolException {

            this.CurrentPopulation = updateIndividuals(this.CurrentPopulation);
        }

        protected Individual[] updateIndividuals(Individual[] population) throws TestDataGenerationToolException {

            GenerationTrace.write("Updating individuals. Count ", population.length, " individuals.");

            if (this._testGenerationTool != null)
            {           
                GenerationCoverage[] coverages = this._testGenerationTool.getCoverages(this._criterion);

                int index = 0;
                for (Individual individual : population) {            

                    individual.setCoverage(coverages[index]);
                    index++;                
                }
            }

            return population;
        }  
        
        protected boolean verifyIfRequirementHasBeenCovered(GenerationRequirement requirement) {

            GenerationTrace.write("Verify if requirement ", requirement.getIdRequirement(), " has been covered.");

            currentSolutionToRequirement = null;

            boolean returnValue = false;

            // Varre a população atual identificando se o requisito foi coberto
            for (Individual individual : this.CurrentPopulation) {

                // Captura a cobertura de um indivíduo e verifica se o requisito foi coberto
                if (verifyIndividualRequirementCovered(requirement, individual.getCoverage()))
                {
                    GenerationTrace.write("Target ", requirement.getIdRequirement(), " covered.");

                    returnValue = true;   
                    currentSolutionToRequirement = individual;
                    break;
                }            
            }       

            return returnValue;

        }

        // Verifica se um indivíduo cobre um requisito
        protected boolean verifyIndividualRequirementCovered(GenerationRequirement requirement, GenerationCoverage coverage)
        {
            boolean covered = false;

            if (coverage != null)
            {
                if (coverage.getExecutionPath().contains("-" + requirement.getIdRequirement() + "-"))
                    covered = true;
            }

            return covered;
        }
        
        // Finalizar a geração de dados de teste 
        protected abstract void finalizeGeneration();
        
        // Nome da estratégia de geração de dados de teste 
        public abstract String getGenerationStrategyName();
}
