
package br.usp.each.saeg.agdtpoo.individualFactory;

import br.usp.each.saeg.agdtpoo.entity.TestIndividual;
import br.usp.each.saeg.agdtpoo.util.Features;
import java.util.ArrayList;

// Esta classe é responsável por contar as intâncias de objetos 
// e evitar que se repitam duas variáveis com o mesmo nome dentro da 
// representação de Tonella
public class InstanceManegementeSingleton {
    
    // Instância estática da classe Singleton
    private static InstanceManegementeSingleton _instance;
    
    // Array de controle de instâncias
    private ArrayList<InstanceCounter> _instances;
    
    // Construtor privado
    private InstanceManegementeSingleton()
    {        
        // Inicializando o array interno de controle de instâncias
        _instances = new ArrayList<InstanceCounter>();
    }
    
    // Formata e retorna o devido nome de uma instância
    public String getInstanceName(TestIndividual individual)
    {
        String typeName;
        String instanceName;
        
        typeName = Features.getInstanceName(individual);
                
        int count = getInstanceCount(typeName);
        
        // Se nenhuma ocorrência for encontrada, então é preciso adicionar uma nova
        if (count == 0)
        {
            addNewInstanceCounter(typeName);
            count = 1;
        }
        
        // Esta condição verifica se é necessário ou não 
        // adicionar o indexador para diferenciar uma variável da outra...
        // Não quero adicionar indexadores sem que seja necessário...
        if (count > 1)
        {
            String index;
            index = String.valueOf(count);
            instanceName = typeName + "_" + index;
        }
        else
            instanceName = typeName;
        
        return instanceName;
    }
    
    // Adiciona uma nova instância na lista de ocorrência de instâncias
    private void addNewInstanceCounter(String typeName)
    {
        InstanceCounter newCounter = new InstanceCounter();
        
        newCounter.setTypeName(typeName);
        newCounter.addNewOccurrence();
        
        _instances.add(newCounter);
    }
    
    // Retorna a quantidade de ocorrências de um tipo de dados
    // dentro da lista de itens
    private int getInstanceCount(String typeName)
    {
        int result = 0;
        
        for (InstanceCounter instanceCounter : _instances) {
            if (instanceCounter.getTypeName().equals(typeName))
            {
                instanceCounter.addNewOccurrence();
                result = instanceCounter.getCount();
                break;
            }
        }
        
        return result;
    }
    
    // Limpa a instância em memória
    public static void clearInstance()
    {
        _instance = null;
    }
    
    // Cria uma nova instância de uma classe singleton em memória
    public static InstanceManegementeSingleton getInstance()
    {
        if (_instance == null)
            _instance = new InstanceManegementeSingleton();
        
        return _instance;
    }
}
