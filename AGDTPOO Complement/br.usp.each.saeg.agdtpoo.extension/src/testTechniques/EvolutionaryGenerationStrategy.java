
package testTechniques;

import fitnessAlgorithms.IneditismFitness;
import fitnessAlgorithms.SimilarityFitness;
import selectionAlgorithms.TournamentSelection;
import selectionAlgorithms.ElitismSelection;
import selectionAlgorithms.AverageSelection;
import selectionAlgorithms.RouletteSelection;
import br.usp.each.saeg.agdtpoo.entity.*;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.*;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.*;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.*;

public class EvolutionaryGenerationStrategy 
                    extends MetaheuristicBaseGenerationStrategy 
                    implements IGUIGenerationStrategy
{
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
        return new IFitness[] { new SimilarityFitness(), 
                                new IneditismFitness() };
    }

    @Override
    public ISelection[] getSupportedSelections() {
        return new ISelection[]  { new ElitismSelection(),
                                   new TournamentSelection(),
                                   new AverageSelection(),
                                   new RouletteSelection()};
    }
   
    @Override
    public String getGenerationStrategyName()
    {
        return "Evolutionary strategy";
    }
}
