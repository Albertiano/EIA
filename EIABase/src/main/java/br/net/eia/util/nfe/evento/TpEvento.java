package br.net.eia.util.nfe.evento;

public enum TpEvento {
	CANCELAMENTO("110111");
	
	private String valor;
	
	private TpEvento(String value){
		this.valor = value;
	}
	
	public String getValor() {
        return valor;
    }
}
