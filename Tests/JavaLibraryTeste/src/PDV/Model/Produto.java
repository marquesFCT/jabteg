
package PDV.Model;

public class Produto {
    private int _id;
    
    private String _nome;
    
    private double _valorCompra;
    
    private String _fabricante;
    
    private double _valorVenda;
    
    private int _estoqueAtual;
    
    private String _descricao;

    public String getDescricao() {
        return _descricao;
    }

    public void setDescricao(String _descricao) {
        this._descricao = _descricao;
    }

    public int getEstoqueAtual() {
        return _estoqueAtual;
    }

    public void setEstoqueAtual(int _estoqueAtual) {
        this._estoqueAtual = _estoqueAtual;
    }

    public String getFabricante() {
        return _fabricante;
    }

    public void setFabricante(String _fabricante) {
        this._fabricante = _fabricante;
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

    public double getValorCompra() {
        return _valorCompra;
    }

    public void setValorCompra(double _valorCompra) {
        this._valorCompra = _valorCompra;
    }

    public double getValorVenda() {
        return _valorVenda;
    }

    public void setValorVenda(double _valorVenda) {
        this._valorVenda = _valorVenda;
    }
    
    
}
