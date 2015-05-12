package br.net.eia.notafiscal.ide;

public enum IndFinal {
	/*
	 * Indica operação com Consumidor final
	 */
	NAO("0"),
	CONSUMIDOR_FINAL("1");
	private String value;
	private IndFinal(String v){
		this.value = v;
	}	
	public String getValue() {
		return value;
	}
}
