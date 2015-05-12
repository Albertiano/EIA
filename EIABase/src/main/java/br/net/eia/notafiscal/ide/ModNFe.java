package br.net.eia.notafiscal.ide;

/**
 * Modelos de NF.
 * 
 * @author Mauricio Fernandes de Castro
 */
public enum ModNFe {
	
	/**
	 * NF-e, emitida em substituição ao modelo 1 ou 1A.
	 */
	_55,
	
	/**
	 * NFC-e, utilizada nas operações de vendas no varejo, onde não for exigida
	 * a NF-e por dispositivo legal.
	 */
	_65,
	
	/**
	 * NF modelo 1 ou 1A.
	 */
	_01,
	
	/**
	 * Cupom Fiscal emitido por máquina registradora (não ECF).
	 */
	_2B,
	
	/**
	 * Cupom Fiscal PDV (não ECF).
	 */
	_2C,
	
	/**
	 * Cupom Fiscal ECF.
	 */
	_2D,
	
	/**
	 * NF de Produtor.
	 */
	_04;
	
	@Override
	public String toString() {
		//Apagando o primeiro caractere e retornando o restante
		return name().substring(1);
	}

}
