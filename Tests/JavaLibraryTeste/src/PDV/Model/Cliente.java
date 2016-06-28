
package PDV.Model;

import java.util.ArrayList;

public class Cliente {
    
    public Cliente()
    {
        this._enderecos = new ArrayList<Endereco>();
    }
    
    private String _nome;
    
    private int _id;
    
    private ArrayList<Endereco> _enderecos;
    
    public ArrayList<Endereco> getEnderecos() {
        return _enderecos;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getNome() {
        return _nome;
    }

    public void setNome(String _nome) {
        this._nome = _nome;
    }     
    
    public void addEndereco(Endereco novoEndereco)
    {
        this._enderecos.add(novoEndereco);
    }
}
