
package PDV.Controller;

import PDV.Model.Pedido;

public class PDVController {
    
    public void savePedido(Pedido pedido)
    {
        //pedido.processarPedido();
    }    
    
    public void processarPedido(Pedido pedido)
    {
        boolean okCliente; 
        boolean okEntrega;  
        boolean okPagamento;
        boolean okId;
        boolean okVendedor;
        
        if (pedido.getCliente() != null)
            okCliente = true;
        
        if (pedido.getEntrega() != null)
            okEntrega = true;
        
        if (pedido.getFormaPagamento() != null)
            okPagamento = true;
        
        if (pedido.getId() > 0)
            okId = true;
        
        if (pedido.getVendedor() != null)
            okVendedor = true;
    }
}
