import org.junit.*;
import PDV.Controller.*;
import PDV.Model.*;
import java.lang.*;

public class AutomaticTest {
	@Test
	public void testMethod1(){
		PDVController pdvcontroller= new PDVController();
		pdvcontroller.hashCode();
		Pedido pedido= new Pedido();
		pedido.getCliente();
		FormaPagamento formapagamento= new FormaPagamento();
		formapagamento.getId();
		formapagamento.hashCode();
		formapagamento.getPedido();
		pedido.setFormaPagamento(formapagamento);
		pedido.setId(71);
		Funcionario funcionario= new Funcionario();
		funcionario.setNome("0f81JRGs5T2dhOUhNjUngWAsrjBsUceK");
		funcionario.setNome("0f81JRGs5T2dhOUhNjUngWAsrjBsUceK");
		pedido.setVendedor(funcionario);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod2(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		pedido_2.getCliente();
		pedido_2.processarPedido();
		pedido_2.getItens();
		FormaPagamento formapagamento= new FormaPagamento();
		formapagamento.getDescricao();
		formapagamento.setDescricao("X");
		formapagamento.hashCode();
		pedido_2.setFormaPagamento(formapagamento);
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod3(){
		PDVController pdvcontroller= new PDVController();
		pdvcontroller.hashCode();
		Pedido pedido= new Pedido();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod4(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod5(){
		PDVController pdvcontroller= new PDVController();
		pdvcontroller.hashCode();
		Pedido pedido= new Pedido();
		pedido.getVendedor();
		pedido.processarPedido();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod6(){
		PDVController pdvcontroller= new PDVController();
		pdvcontroller.hashCode();
		Pedido pedido= new Pedido();
		pedido.getId();
		Funcionario funcionario= new Funcionario();
		funcionario.setCargo("CLkoigS");
		funcionario.getId();
		pedido.setVendedor(funcionario);
		ItemPedido itempedido= new ItemPedido();
		itempedido.getProduto();
		itempedido.getQuantidade();
		pedido.addItem(itempedido);
		FormaPagamento formapagamento= new FormaPagamento();
		formapagamento.setId(4);
		formapagamento.getValor();
		formapagamento.setDescricao("IepTqqcaZSOZoh72uVuSIp");
		formapagamento.setId(4);
		pedido.setFormaPagamento(formapagamento);
		FormaPagamento formapagamento_2= new FormaPagamento();
		formapagamento_2.setId(4);
		formapagamento_2.getValor();
		formapagamento_2.setDescricao("IepTqqcaZSOZoh72uVuSIp");
		formapagamento_2.setId(4);
		pedido.setFormaPagamento(formapagamento_2);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod7(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.getFormaPagamento();
		pedido.processarPedido();
		pedido.getVendedor();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod8(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		Entrega entrega= new Entrega();
		Transportadora transportadora= new Transportadora();
		transportadora.hashCode();
		transportadora.getNome();
		entrega.setTransportadora(transportadora);
		Cliente cliente= new Cliente();
		cliente.setNome("0TgyB95Ft2ws0AUOOZXV2OjHi1xhkczVtAbD8F");
		entrega.setCliente(cliente);
		entrega.setId(65);
		entrega.toString();
		Endereco endereco= new Endereco();
		endereco.getEstado();
		endereco.getCliente();
		endereco.toString();
		entrega.setEndereco(endereco);
		pedido.setEntrega(entrega);
		ItemPedido itempedido= new ItemPedido();
		itempedido.setId(47);
		itempedido.getPedido();
		Produto produto= new Produto();
		produto.getFabricante();
		produto.setDescricao("8sy9FQozvH");
		produto.getEstoqueAtual();
		produto.setValorCompra(41.2761740524354);
		produto.setId(35);
		produto.getValorCompra();
		produto.setId(35);
		itempedido.setProduto(produto);
		itempedido.getProduto();
		itempedido.hashCode();
		pedido.addItem(itempedido);
		pedido.hashCode();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod9(){
		PDVController pdvcontroller= new PDVController();
		pdvcontroller.toString();
		Pedido pedido= new Pedido();
		Funcionario funcionario= new Funcionario();
		pedido.setVendedor(funcionario);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod10(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.getId();
		pedido.getId();
		FormaPagamento formapagamento= new FormaPagamento();
		formapagamento.getId();
		pedido.setFormaPagamento(formapagamento);
		pedido.getCliente();
		pedido.getEntrega();
		Funcionario funcionario= new Funcionario();
		funcionario.getId();
		funcionario.setId(4);
		pedido.setVendedor(funcionario);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod11(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pdvcontroller.processarPedido(pedido);
		Pedido pedido_2= new Pedido();
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod12(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.toString();
		pedido.getFormaPagamento();
		FormaPagamento formapagamento= new FormaPagamento();
		formapagamento.toString();
		formapagamento.getId();
		formapagamento.getId();
		formapagamento.getId();
		pedido.setFormaPagamento(formapagamento);
		pedido.processarPedido();
		pedido.toString();
		ItemPedido itempedido= new ItemPedido();
		itempedido.setQuantidade(81);
		pedido.addItem(itempedido);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod13(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.getFormaPagamento();
		pedido.getCliente();
		pedido.getEntrega();
		FormaPagamento formapagamento= new FormaPagamento();
		formapagamento.getDescricao();
		pedido.setFormaPagamento(formapagamento);
		pedido.getFormaPagamento();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod14(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		ItemPedido itempedido= new ItemPedido();
		itempedido.setValor(87.2679729701029);
		itempedido.getQuantidade();
		itempedido.toString();
		itempedido.setQuantidade(67);
		Pedido pedido_2= new Pedido();
		ItemPedido itempedido_2= new ItemPedido();
		itempedido_2.getPedido();
		itempedido_2.getValor();
		itempedido_2.setQuantidade(97);
		pedido_2.addItem(itempedido_2);
		Funcionario funcionario= new Funcionario();
		funcionario.setCargo("Qmb2j3yjnpukc6gM72eksZP2Kj");
		funcionario.getId();
		funcionario.getNome();
		pedido_2.setVendedor(funcionario);
		pedido_2.getId();
		itempedido.setPedido(pedido_2);
		pedido.addItem(itempedido);
		pedido.toString();
		pedido.setId(7);
		pdvcontroller.processarPedido(pedido);
		Pedido pedido_3= new Pedido();
		pedido_3.getVendedor();
		FormaPagamento formapagamento= new FormaPagamento();
		formapagamento.setValor(34.35361838830353);
		formapagamento.getDescricao();
		pedido_3.setFormaPagamento(formapagamento);
		pedido_3.getFormaPagamento();
		Entrega entrega= new Entrega();
		Cliente cliente= new Cliente();
		cliente.getId();
		entrega.setCliente(cliente);
		entrega.toString();
		Transportadora transportadora= new Transportadora();
		entrega.setTransportadora(transportadora);
		Endereco endereco= new Endereco();
		Cliente cliente_2= new Cliente();
		cliente_2.hashCode();
		cliente_2.getId();
		endereco.setCliente(cliente_2);
		endereco.getBairro();
		endereco.getPais();
		endereco.setNumero("i3h8gsdJVYzovHGpFOy24pCiRbcd8p7AqD");
		endereco.setId(44);
		entrega.setEndereco(endereco);
		pedido_3.setEntrega(entrega);
		pdvcontroller.processarPedido(pedido_3);
	}
	
	@Test
	public void testMethod15(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.getEntrega();
		pedido.getFormaPagamento();
		ItemPedido itempedido= new ItemPedido();
		itempedido.setValor(70.2503984516044);
		itempedido.getQuantidade();
		itempedido.toString();
		itempedido.hashCode();
		itempedido.setValor(70.2503984516044);
		pedido.addItem(itempedido);
		pedido.processarPedido();
		FormaPagamento formapagamento= new FormaPagamento();
		pedido.setFormaPagamento(formapagamento);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod16(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		Entrega entrega= new Entrega();
		entrega.getPedido();
		pedido.setEntrega(entrega);
		pedido.getEntrega();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod17(){
		PDVController pdvcontroller= new PDVController();
		pdvcontroller.hashCode();
		Pedido pedido= new Pedido();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod18(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		ItemPedido itempedido= new ItemPedido();
		itempedido.getValor();
		itempedido.toString();
		Pedido pedido_2= new Pedido();
		pedido_2.getFormaPagamento();
		pedido_2.getCliente();
		pedido_2.getCliente();
		FormaPagamento formapagamento= new FormaPagamento();
		formapagamento.setDescricao("OwB");
		pedido_2.setFormaPagamento(formapagamento);
		pedido_2.toString();
		itempedido.setPedido(pedido_2);
		pedido.addItem(itempedido);
		pedido.getItens();
		pedido.getId();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod19(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.getVendedor();
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		Funcionario funcionario= new Funcionario();
		funcionario.getId();
		funcionario.getId();
		funcionario.getId();
		pedido_2.setVendedor(funcionario);
		Cliente cliente= new Cliente();
		Endereco endereco= new Endereco();
		cliente.addEndereco(endereco);
		Endereco endereco_2= new Endereco();
		cliente.addEndereco(endereco_2);
		pedido_2.setCliente(cliente);
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod20(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		FormaPagamento formapagamento= new FormaPagamento();
		formapagamento.setId(47);
		pedido.setFormaPagamento(formapagamento);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod21(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		FormaPagamento formapagamento= new FormaPagamento();
		pedido.setFormaPagamento(formapagamento);
		FormaPagamento formapagamento_2= new FormaPagamento();
		pedido.setFormaPagamento(formapagamento_2);
		pedido.hashCode();
		Funcionario funcionario= new Funcionario();
		funcionario.setNome("qV0iiy0Dqz9omebsZGgjXMrxmtMu");
		funcionario.toString();
		pedido.setVendedor(funcionario);
		ItemPedido itempedido= new ItemPedido();
		Produto produto= new Produto();
		produto.getFabricante();
		produto.setValorVenda(70.27541842203426);
		produto.getValorVenda();
		produto.setNome("o5INE1t6Ap3QaGfWtDDtK7nxkyCz3nylwo2Nh");
		produto.getFabricante();
		produto.getValorVenda();
		produto.setDescricao("hGU11aaKoiHsi2ZRRkjbB3VuYekkeE");
		itempedido.setProduto(produto);
		pedido.addItem(itempedido);
		pedido.processarPedido();
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod22(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod23(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		Entrega entrega= new Entrega();
		entrega.getPedido();
		entrega.getEndereco();
		entrega.setId(30);
		pedido.setEntrega(entrega);
		pedido.getVendedor();
		pedido.getId();
		pedido.hashCode();
		Entrega entrega_2= new Entrega();
		entrega_2.getPedido();
		entrega_2.getEndereco();
		entrega_2.setId(30);
		pedido.setEntrega(entrega_2);
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		pedido_2.setId(65);
		pedido_2.getVendedor();
		pedido_2.getVendedor();
		Entrega entrega_3= new Entrega();
		pedido_2.setEntrega(entrega_3);
		pedido_2.toString();
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod24(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.setId(41);
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		Cliente cliente= new Cliente();
		Endereco endereco= new Endereco();
		Cliente cliente_2= new Cliente();
		cliente_2.getNome();
		endereco.setCliente(cliente_2);
		endereco.getCidade();
		endereco.setLogradouro("Q3knfG5GbHQhmr");
		endereco.getBairro();
		endereco.getNumero();
		endereco.toString();
		cliente.addEndereco(endereco);
		pedido_2.setCliente(cliente);
		pedido_2.getCliente();
		Entrega entrega= new Entrega();
		entrega.getId();
		entrega.getCliente();
		entrega.getId();
		entrega.setId(89);
		pedido_2.setEntrega(entrega);
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod25(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.getItens();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod26(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		ItemPedido itempedido= new ItemPedido();
		itempedido.getValor();
		itempedido.getValor();
		pedido.addItem(itempedido);
		pedido.toString();
		pedido.getVendedor();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod27(){
		PDVController pdvcontroller= new PDVController();
		pdvcontroller.hashCode();
		Pedido pedido= new Pedido();
		pedido.getVendedor();
		FormaPagamento formapagamento= new FormaPagamento();
		formapagamento.getId();
		Pedido pedido_2= new Pedido();
		FormaPagamento formapagamento_2= new FormaPagamento();
		formapagamento_2.setValor(32.63599121212272);
		Pedido pedido_3= new Pedido();
		pedido_3.getFormaPagamento();
		pedido_3.getVendedor();
		formapagamento_2.setPedido(pedido_3);
		pedido_2.setFormaPagamento(formapagamento_2);
		pedido_2.getEntrega();
		formapagamento.setPedido(pedido_2);
		Pedido pedido_4= new Pedido();
		FormaPagamento formapagamento_3= new FormaPagamento();
		formapagamento_3.setValor(32.63599121212272);
		Pedido pedido_5= new Pedido();
		pedido_5.getFormaPagamento();
		pedido_5.getVendedor();
		formapagamento_3.setPedido(pedido_5);
		pedido_4.setFormaPagamento(formapagamento_3);
		pedido_4.getEntrega();
		formapagamento.setPedido(pedido_4);
		formapagamento.getPedido();
		pedido.setFormaPagamento(formapagamento);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod28(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.toString();
		pedido.getId();
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod29(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.processarPedido();
		ItemPedido itempedido= new ItemPedido();
		itempedido.hashCode();
		itempedido.toString();
		itempedido.setQuantidade(89);
		itempedido.getProduto();
		pedido.addItem(itempedido);
		pedido.toString();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod30(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		Cliente cliente= new Cliente();
		pedido.setCliente(cliente);
		pedido.getEntrega();
		pedido.getCliente();
		pedido.setId(45);
		pedido.getFormaPagamento();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod31(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		ItemPedido itempedido= new ItemPedido();
		itempedido.toString();
		itempedido.getValor();
		pedido_2.addItem(itempedido);
		pedido_2.getId();
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod32(){
		PDVController pdvcontroller= new PDVController();
		pdvcontroller.hashCode();
		Pedido pedido= new Pedido();
		Entrega entrega= new Entrega();
		Pedido pedido_2= new Pedido();
		pedido_2.getEntrega();
		entrega.setPedido(pedido_2);
		Cliente cliente= new Cliente();
		cliente.toString();
		cliente.getEnderecos();
		entrega.setCliente(cliente);
		entrega.hashCode();
		entrega.hashCode();
		entrega.getCliente();
		pedido.setEntrega(entrega);
		pedido.getVendedor();
		pedido.getVendedor();
		pedido.toString();
		pedido.toString();
		pedido.getFormaPagamento();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod33(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		FormaPagamento formapagamento= new FormaPagamento();
		Pedido pedido_2= new Pedido();
		pedido_2.setId(4);
		Cliente cliente= new Cliente();
		cliente.getId();
		pedido_2.setCliente(cliente);
		ItemPedido itempedido= new ItemPedido();
		itempedido.setId(74);
		pedido_2.addItem(itempedido);
		pedido_2.setId(4);
		pedido_2.processarPedido();
		FormaPagamento formapagamento_2= new FormaPagamento();
		formapagamento_2.getDescricao();
		formapagamento_2.getId();
		pedido_2.setFormaPagamento(formapagamento_2);
		formapagamento.setPedido(pedido_2);
		formapagamento.toString();
		formapagamento.toString();
		formapagamento.getPedido();
		pedido.setFormaPagamento(formapagamento);
		pedido.processarPedido();
		pedido.getCliente();
		ItemPedido itempedido_2= new ItemPedido();
		itempedido_2.hashCode();
		itempedido_2.getPedido();
		itempedido_2.getId();
		itempedido_2.getId();
		itempedido_2.getProduto();
		pedido.addItem(itempedido_2);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod34(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.getFormaPagamento();
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		Funcionario funcionario= new Funcionario();
		funcionario.setId(79);
		funcionario.setNome("LAQ5VqXKPhrsQid6KrxL9fHQswtkpRf");
		pedido_2.setVendedor(funcionario);
		pedido_2.getCliente();
		ItemPedido itempedido= new ItemPedido();
		itempedido.getPedido();
		itempedido.getQuantidade();
		itempedido.setId(70);
		itempedido.setQuantidade(1);
		itempedido.getQuantidade();
		pedido_2.addItem(itempedido);
		pedido_2.getCliente();
		pedido_2.getItens();
		pedido_2.getItens();
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod35(){
		PDVController pdvcontroller= new PDVController();
		pdvcontroller.toString();
		Pedido pedido= new Pedido();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod36(){
		PDVController pdvcontroller= new PDVController();
		pdvcontroller.toString();
		Pedido pedido= new Pedido();
		pedido.getFormaPagamento();
		pedido.getEntrega();
		pedido.setId(56);
		pedido.getFormaPagamento();
		pedido.toString();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod37(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		Cliente cliente= new Cliente();
		cliente.setNome("wMR1mP");
		cliente.setNome("wMR1mP");
		cliente.setNome("wMR1mP");
		pedido.setCliente(cliente);
		pedido.getId();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod38(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod39(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.getId();
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		Entrega entrega= new Entrega();
		entrega.getTransportadora();
		pedido_2.setEntrega(entrega);
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod40(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.getFormaPagamento();
		pedido.getEntrega();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod41(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.getCliente();
		pedido.getFormaPagamento();
		pedido.toString();
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		pedido_2.processarPedido();
		Entrega entrega= new Entrega();
		pedido_2.setEntrega(entrega);
		pedido_2.getEntrega();
		pedido_2.getFormaPagamento();
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod42(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.processarPedido();
		FormaPagamento formapagamento= new FormaPagamento();
		formapagamento.setDescricao("yEo6H0gKjDkAd3pl7b20W2Dq4utH7EqTVtzv9");
		Pedido pedido_2= new Pedido();
		pedido_2.setId(26);
		pedido_2.getVendedor();
		formapagamento.setPedido(pedido_2);
		formapagamento.getValor();
		pedido.setFormaPagamento(formapagamento);
		pedido.getCliente();
		pedido.getCliente();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod43(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		Funcionario funcionario= new Funcionario();
		pedido.setVendedor(funcionario);
		pedido.getEntrega();
		ItemPedido itempedido= new ItemPedido();
		itempedido.getId();
		pedido.addItem(itempedido);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod44(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		Entrega entrega= new Entrega();
		pedido.setEntrega(entrega);
		pedido.getItens();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod45(){
		PDVController pdvcontroller= new PDVController();
		pdvcontroller.hashCode();
		Pedido pedido= new Pedido();
		pedido.getFormaPagamento();
		Cliente cliente= new Cliente();
		pedido.setCliente(cliente);
		pedido.getCliente();
		ItemPedido itempedido= new ItemPedido();
		itempedido.getPedido();
		itempedido.getId();
		Produto produto= new Produto();
		itempedido.setProduto(produto);
		itempedido.toString();
		pedido.addItem(itempedido);
		pedido.getItens();
		FormaPagamento formapagamento= new FormaPagamento();
		pedido.setFormaPagamento(formapagamento);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod46(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod47(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		Cliente cliente= new Cliente();
		cliente.getId();
		cliente.setNome("SPA27Jjfrt929tGKbYVNns");
		cliente.setNome("SPA27Jjfrt929tGKbYVNns");
		pedido.setCliente(cliente);
		pedido.processarPedido();
		pedido.hashCode();
		pedido.setId(90);
		pedido.setId(90);
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod48(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.processarPedido();
		pedido.toString();
		ItemPedido itempedido= new ItemPedido();
		itempedido.hashCode();
		itempedido.setQuantidade(88);
		itempedido.getProduto();
		itempedido.setValor(20.626384171094813);
		pedido.addItem(itempedido);
		pedido.getItens();
		pedido.getVendedor();
		pedido.getVendedor();
		pdvcontroller.processarPedido(pedido);
	}
	
	@Test
	public void testMethod49(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		FormaPagamento formapagamento= new FormaPagamento();
		pedido.setFormaPagamento(formapagamento);
		Cliente cliente= new Cliente();
		pedido.setCliente(cliente);
		pedido.setId(80);
		pedido.getItens();
		pdvcontroller.savePedido(pedido);
		Pedido pedido_2= new Pedido();
		pdvcontroller.processarPedido(pedido_2);
	}
	
	@Test
	public void testMethod50(){
		PDVController pdvcontroller= new PDVController();
		Pedido pedido= new Pedido();
		pedido.getId();
		pedido.getItens();
		pedido.getCliente();
		Cliente cliente= new Cliente();
		pedido.setCliente(cliente);
		pedido.getEntrega();
		pdvcontroller.processarPedido(pedido);
	}
	
}