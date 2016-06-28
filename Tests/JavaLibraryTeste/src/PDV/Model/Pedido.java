
package PDV.Model;

import java.util.ArrayList;
import java.util.Date;

public class Pedido {
    
    public Pedido()
    {
        this._itens = new ArrayList<ItemPedido>();                
    }
        
    private int _id;
    
    private ArrayList<ItemPedido> _itens;
    
    private Cliente _cliente;
    
    private Entrega _entrega;
    
    private FormaPagamento _formaPagamento;
    
    private Funcionario _vendedor;

    public Cliente getCliente() {
        return _cliente;
    }

    public void setCliente(Cliente _cliente) {
        this._cliente = _cliente;
    }

    public Entrega getEntrega() {
        return _entrega;
    }

    public void setEntrega(Entrega _entrega) {
        this._entrega = _entrega;
    }

    public FormaPagamento getFormaPagamento() {
        return _formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento _formaPagamento) {
        this._formaPagamento = _formaPagamento;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public ArrayList<ItemPedido> getItens() {
        return _itens;
    }

    public Funcionario getVendedor() {
        return _vendedor;
    }

    public void setVendedor(Funcionario _vendedor) {
        this._vendedor = _vendedor;
    }

    public void addItem(ItemPedido item)
    {
        this._itens.add(item);
    }
    
    public void processarPedido()
    {
        boolean okCliente; 
        boolean okEntrega;  
        boolean okPagamento;
        boolean okId;
        boolean okVendedor;
        
        if (this._cliente != null)
            okCliente = true;
        
        if (this._entrega != null)
            okEntrega = true;
        
        if (this._formaPagamento != null)
            okPagamento = true;
        
        if (this._id > 0)
            okId = true;
        
        if (this._vendedor != null)
            okVendedor = true;
    }
}
