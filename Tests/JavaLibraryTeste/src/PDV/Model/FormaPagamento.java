
package PDV.Model;

public class FormaPagamento {
    
    private String _descricao;
    
    private Pedido _pedido;
    
    private int _id;
    
    private double _valor;

    public String getDescricao() {
        return _descricao;
    }

    public void setDescricao(String _descricao) {
        this._descricao = _descricao;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public Pedido getPedido() {
        return _pedido;
    }

    public void setPedido(Pedido _pedido) {
        this._pedido = _pedido;
    }

    public double getValor() {
        return _valor;
    }

    public void setValor(double _valor) {
        this._valor = _valor;
    }
        
}
