package br.net.eia.enums;

public enum StatusOperacao {

	EM_ABERTO("Aberto"),
	FINALIZADO("Finalizado"),
	CANCELADO("Cancelado");
	
	private String value;
	
	StatusOperacao(String status){
		value=status;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getValue();
	}
}
