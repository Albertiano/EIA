package br.net.eia.notafiscal.ide;

/**
 * Forma de Emissão da NF-e.
 * 
 */
public enum TpEmis {
	
	/**
	 * Emissão Normal.
	 */
	NORMAL("1"),
	/**
	 * Emissão em contingência com impressão do DANFE em Formulário de
	 * Segurança.
	 */
	CONTINGENCIA_FS("2"),
	/**
	 * Emissão em contingência no Sistema de Contingência do Ambiente 
	 * Nacional - SCAN.
	 */
	CONTINGENCIA_SCAN("3"),
	/**
	 * Emiss�o em conting�ncia com envio da Declara��o Pr�via de Emiss�o 
	 * em Conting�ncia � DPEC.
	 */
	CONTINGENCIA_DPEC("4"),
	/**
	 * Emiss�o em conting�ncia com impress�o do DANFE em Formul�rio de 
	 * Seguran�a para Impress�o de Documento Auxiliar de Documento Fiscal 
	 * Eletr�nico (FS-DA).
	 */
	CONTINGENCIA_FSDA("5"),
	/*
	 * Contingência off-line da NFC-e
	 */
	CONTINGENCIA_OFFLINE("9");;
	
	private String value;
	
	private TpEmis(String value) {
		this.value = value;
	}
	
	/**
	 * Valor literal atribu�do � forma de Emiss�o da NF-e.
	 */
	public String getValue() {
		return this.value;
	}

}
