/**
 * 
 */
package br.net.eia.notafiscal.ide;

/**
 * @author Albertiano
 * 
 */
public enum ProcEmi {

	/**
	 * Emissão de NF-e com aplicativo do contribuinte;.
	 */
	APLICATIVO_CONTRIBUINTE("0"),

	/**
	 * Emissão de NF-e avulsa pelo Fisco.
	 */
	AVULSA_FISCO("1"),

	/**
	 * Emissão de NF-e avulsa, pelo contribuinte com seu certificado digital,
	 * através do site do Fisco.
	 */
	SITE_FISCO("2"),

	/**
	 * Emissão NF-e pelo contribuinte com aplicativo fornecido pelo Fisco.
	 */
	APLICATIVO_FISCO("3");

	private String value;

	private ProcEmi(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
