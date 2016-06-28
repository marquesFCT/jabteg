
package testTechniques;

import br.usp.each.saeg.agdtpoo.bytecodeFoundation.BytecodeReader;
import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.entity.GUIGenerationCustomParameter;
import br.usp.each.saeg.agdtpoo.entity.IGUIGenerationStrategy;
import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.entity.InputPrimitiveDomain;
import br.usp.each.saeg.agdtpoo.entity.ReflectionClass;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.IFitness;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.BaseGenerationStrategy;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.IStrategyPrimitiveTips;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.ISelection;
import br.usp.each.saeg.agdtpoo.randomFeature.RandomValueFactory;

public class RandomGenerationStrategy extends BaseGenerationStrategy 
                                      implements IStrategyPrimitiveTips, 
                                                 IGUIGenerationStrategy{    
    private InputPrimitiveDomain _inputDomain;

    @Override
    public GUIGenerationCustomParameter[] getCustomParameters() {
        return null;
    }

    @Override
    public EnumCriterion[] getSupportedCriterions() {
        EnumCriterion[] criterions = 
                new EnumCriterion[]{ EnumCriterion.AllNodesEi, 
                                     EnumCriterion.AllEdgesEi, 
                                     EnumCriterion.AllPotUsesEi, 
                                     EnumCriterion.AllUsesEi,
                                     EnumCriterion.AllNodesEd, 
                                     EnumCriterion.AllEdgesEd, 
                                     EnumCriterion.AllPotUsesEd, 
                                     EnumCriterion.AllUsesEd };        
        return criterions;
    }

    @Override
    public IFitness[] getSupportedFitnesses() {
        return null;
    }

    @Override
    public ISelection[] getSupportedSelections() {
        return null;
    }
         
    @Override
    public void setInputDomain(InputPrimitiveDomain inputDomain) {        
        // Atribui os dados primitivos que guiarão a geração dos dados de teste
        this._inputDomain = inputDomain;    
    }
 
    @Override
    protected void finalizeGeneration() {
    }

    @Override
    protected Individual[] generateInitialPopulation()
    {
        // Lê os dados do bytecode
        BytecodeReader reader = new BytecodeReader(this.JarPath, 
                                                   this.ClassUnderTest);
        
        ReflectionClass rc = reader.getReflectionClass();        
        // Gera indivíduos aleatórios
        Individual[] individuals = RandomValueFactory
                                        .getNewPopulation(this.PopulationSize, 
                                                          rc, 
                                                          this.MethodUnderTest, 
                                                          this._inputDomain);      
        return individuals;        
    }

    @Override
    protected void initializeGeneration() {
    }
    
    @Override
    public String getGenerationStrategyName() {
        return "Random strategy";
    }
}
