package br.net.eia.notafiscal.ide;

public enum TpAmb {
	
	/**
	 * Ambiente de Produção.
	 */
	PRODUCAO("1"),
	/**
	 * Ambiente de Homologação.
	 */
	HOMOLOGACAO("2");

private String value;
	
	private TpAmb(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
