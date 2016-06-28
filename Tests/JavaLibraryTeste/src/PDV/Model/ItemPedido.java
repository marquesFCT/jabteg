
package PDV.Model;

public class ItemPedido {
    private int _id;
    
    private Produto _produto;
    
    private Pedido _pedido;
    
    private int _quantidade;
    
    private double _valor;

    public double getValor() {
        return _valor;
    }

    public void setValor(double _valor) {
        this._valor = _valor;
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

    public Produto getProduto() {
        return _produto;
    }

    public void setProduto(Produto _produto) {
        this._produto = _produto;
    }

    public int getQuantidade() {
        return _quantidade;
    }

    public void setQuantidade(int _quantidade) {
        this._quantidade = _quantidade;
    }
}
