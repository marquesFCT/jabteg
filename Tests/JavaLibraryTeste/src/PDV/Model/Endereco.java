
package PDV.Model;

public class Endereco {
    
    private String _logradouro;
    
    private String _numero;
    
    private String _complemento;
    
    private String _bairro;
    
    private String _cidade;
    
    private String _estado;
    
    private String _pais;
    
    private String _cep;
    
    private int _id;
    
    private Cliente _cliente;

    public String getBairro() {
        return _bairro;
    }

    public void setBairro(String _bairro) {
        this._bairro = _bairro;
    }

    public String getCep() {
        return _cep;
    }

    public void setCep(String _cep) {
        this._cep = _cep;
    }

    public String getCidade() {
        return _cidade;
    }

    public void setCidade(String _cidade) {
        this._cidade = _cidade;
    }

    public Cliente getCliente() {
        return _cliente;
    }

    public void setCliente(Cliente _cliente) {
        this._cliente = _cliente;
    }

    public String getComplemento() {
        return _complemento;
    }

    public void setComplemento(String _complemento) {
        this._complemento = _complemento;
    }

    public String getEstado() {
        return _estado;
    }

    public void setEstado(String _estado) {
        this._estado = _estado;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getLogradouro() {
        return _logradouro;
    }

    public void setLogradouro(String _logradouro) {
        this._logradouro = _logradouro;
    }

    public String getNumero() {
        return _numero;
    }

    public void setNumero(String _numero) {
        this._numero = _numero;
    }

    public String getPais() {
        return _pais;
    }

    public void setPais(String _pais) {
        this._pais = _pais;
    }
    
    
            
}
