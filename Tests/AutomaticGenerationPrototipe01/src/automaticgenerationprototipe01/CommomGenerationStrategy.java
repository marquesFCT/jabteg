/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automaticgenerationprototipe01;

import java.util.ArrayList;

/**
 *
 * @author Fernando
 */
public abstract class CommomGenerationStrategy {
        
        protected ArrayList<Individual> Solution = new ArrayList<Individual>();
        protected Individual[] CurrentPopulation;
        protected BranchNode[] Targets;
        protected BranchNode CurrentTarget;

        // Passo 00 - Iniciar a geração automática de dados de teste
        public Individual[] Execute()
        {            
            // Processo de inicialização 
            InitializeGeneration();
            // Geração da população inicial de indivíduos
            this.CurrentPopulation = GenerateInitialPopulation();
            // Identificação dos ramos/nós que devem ser cobertos
            this.Targets = IdentifyTargets();

            // Variável que indica se um novo objetivo deve ser selecionado
            boolean selectNewTarget = true;

            for (int i = 0; i < this.Targets.length; i++)
            {
                // Seleciona o objetivo que deve ser coberto
                if (selectNewTarget || i == 0)
                    this.CurrentTarget = SelectNextTarget();

                // Executar indivíduos
                ExecuteIndividuals();

                if (this.CurrentTarget.isCovered())
                {
                    // Permitir que um novo item seja selecionado na próxima iteração do laço
                    selectNewTarget = true;
                }
                else
                {
                    // Não permitir que um novo item seja selecionado na próxima iteração do laço
                    selectNewTarget = false;

                    // Calcular a fitness dos indivíduos
                    for (int j = 0; j < this.CurrentPopulation.length; j++)
                    {
                        Individual itemIndividual = this.CurrentPopulation[j];
                        itemIndividual.setFitnesss(ComputeFitness(itemIndividual, this.CurrentTarget));
                    }

                    // Executar mutação dos itens
                    this.CurrentPopulation = ExecuteMutation(this.CurrentPopulation);
                }
            }

            // Processo de finalização 
            FinalizeGeneration();

            return (Individual[])this.Solution.toArray();
        }

        // Passo 01 - Inicialização do processo de geração de dados de teste
        public abstract void InitializeGeneration();

        // Passo 02 - Gerar população inicial
        public abstract Individual[] GenerateInitialPopulation();

        // Passo 03 - Identificar ramos/nós que devem ser cobertos
        public abstract BranchNode[] IdentifyTargets();

        // Passo 04 - Selecionar o próximo objetivo que deve ser coberto
        public abstract BranchNode SelectNextTarget();

        // Passo 05 - Executar indivíduos e armazenar caminhos percorridos
        public void ExecuteIndividuals()
        {
            for (int i = 0; i < this.CurrentPopulation.length; i++)
            {
                // Seleciona um indivíduo
                Individual currentIndividual = this.CurrentPopulation[i];
                // Executa o indivíduo e armazena o caminho percorrido
                BranchNode[] coveragePath = ExecuteIndividual(currentIndividual);
                currentIndividual.setCoverage(coveragePath);

                // Verificar se o objetivo foi coberto
                boolean isCovered = VerifyIndividualCoverage(this.CurrentTarget,
                                                          coveragePath);
                // Executa ações com relação a cobertura dos itens
                if (isCovered)
                {
                    // Se foi coberto
                    this.CurrentTarget.isCovered(true);
                    this.Solution.add(currentIndividual);
                    return;
                }
                else
                {
                    // Se não foi coberto

                    // Executa uma tentativa complementar de cobertura
                    coveragePath = ExecuteSupplementarCoberage(currentIndividual);
                    // Verificar se o objetivo foi coberto
                    isCovered = VerifyIndividualCoverage(this.CurrentTarget,
                                                         coveragePath);
                    // Verifica se a execução complementar foi capaz de 
                    // cobrir o objetivo
                    if (isCovered)
                    {
                        // Se foi coberto
                        this.CurrentTarget.isCovered(true);
                        this.Solution.add(currentIndividual);
                        return;
                    }

                    // Marcar o item como não coberto
                    this.CurrentTarget.isCovered(false);
                }
            }
        }

        // Passo 06 - Executar indivíduo e armazenar caminhos percorrido
        public abstract BranchNode[] ExecuteIndividual(Individual individual);

        // Passo 07 - Verificar se o objetivo foi coberto
        public abstract boolean VerifyIndividualCoverage(BranchNode target, BranchNode[] coveragePath);

        // Passo 08 - Executar operação complementar de validação de cobertura
        public abstract BranchNode[] ExecuteSupplementarCoberage(Individual individual);
        
        // Passo 09 - Calcular função de aptidão
        public abstract double ComputeFitness(Individual individual, BranchNode target);

        // Passo 10 - Executar mutação da população
        public abstract Individual[] ExecuteMutation(Individual[] population);

        // Passo 11 - Finalizar
        public abstract void FinalizeGeneration();
}
