
package PDV.Model;

public class Entrega {
    
    private Pedido _pedido;
    
    private Cliente _cliente;
    
    private Endereco _endereco;
    
    private Transportadora _transportadora;

    public Transportadora getTransportadora() {
        return _transportadora;
    }

    public void setTransportadora(Transportadora _transportadora) {
        this._transportadora = _transportadora;
    }
    
    private int _id;

    public Cliente getCliente() {
        return _cliente;
    }

    public void setCliente(Cliente _cliente) {
        this._cliente = _cliente;
    }

    public Endereco getEndereco() {
        return _endereco;
    }

    public void setEndereco(Endereco _endereco) {
        this._endereco = _endereco;
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
    
    
}
