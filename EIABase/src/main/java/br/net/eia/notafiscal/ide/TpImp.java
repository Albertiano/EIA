package br.net.eia.notafiscal.ide;

/**
 * Formato de Impress�o do DANFE.
 *  
 */
public enum TpImp {
	
	/**
	 * Impressão em formato "retrato".
	 */
	RETRATO("1"),
	/**
	 * Impress�o em formato "paisagem".
	 */
	PAISAGEM("2"),
	
	SIMPLIFICADO("3"),
	
	 NFCe("4"),
	 
	 NFCe_MENS_ELETRONICA("5");
	
	private String value;
	
	private TpImp(String value) {
		this.value = value;
	}
	
	/**
	 * Valor literal atribuído ao formato de Impressão do DANFE.
	 */
	public String getValue() {
		return this.value;
	}

}

