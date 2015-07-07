package br.net.eia.notafiscal.ide;

public enum FinNFe {
	
	/**
	 * .
	 */
	NORMAL("1"),
	/**
	 * .
	 */
	COMPLEMENTAR("2"),
	/**
	 * .
	 */
	AJUSTE("3"),
	/**
	 * .
	 */
	DEVOLUCAO_RETORNO("4");
	
	private String value;
	
	private FinNFe(String value) {
		this.value = value;
	}
	
	/**
	 * Valor literal atribu�do � finalidade de emiss�o da NF-e.
	 */
	public String getValue() {
		return this.value;
	}

}
