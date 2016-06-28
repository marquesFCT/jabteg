
public class Carro {
    private float _velocidadeAtual;
    private double _preco;
    private long _potencia;
    private boolean _cambioAutomatico;
    private Motor _motor;
    private int _ano;
    
    public Carro()
    {
        
    }
    
    public Carro(String placa, int ano)
    {
        
    }
    
    public void inicializar(String placa, long potencia, boolean cambioAutomatico, double valor, String cor)
    {
      if (placa.equals("X"))
          placa = "Y"; 
    }
       
    public void setPotenciaMotor(long potencia)
    {
        this._potencia = potencia;
    }
    
    public void setCambioAutomatico(boolean cambioAutomatico)
    {
        if (cambioAutomatico)
            setPreco(50000);
    }
    
    public void setPreco(double valor)
    {
        this._preco = valor;
    }
    
    public void setCor(String cor)
    {
        
    }
    
    public void setAno(int ano)
    {
        _ano = ano;
    }
    
    public void incluirMotor(Motor motor)
    {
        this._motor = motor;
    }
    
    public void acelerar(float velocidade)
    {
        if (velocidade == 0)
        {
            velocidade = 10;
        }
        
        if (velocidade <= 10)
        {
            if (this._ano == 2010)
            {                
            }
            else if (this._ano == 2011)
            {                
            }
            else if (this._ano == 2012)
            {
            }
            else
            {                
            }                
        }
        else if (velocidade <= 50)
        {
            if (this._ano == 2010)
            {                
            }
            else if (this._ano == 2011)
            {                
            }
            else if (this._ano == 2012)
            {
            }
            else
            {                
            }  
        }
        else if (velocidade <= 100)
        {
            if (this._ano == 2010)
            {                
            }
            else if (this._ano == 2011)
            {                
            }
            else if (this._ano == 2012)
            {
            }
            else
            {                
            }  
        }
        else if (velocidade <= 500)
        {
            if (this._ano == 2010)
            {
            }
            else if (this._ano == 2011)
            {
            }
            else if (this._ano == 2012)
            {
            }
            else
            {                
            }  
        }
    }
}
