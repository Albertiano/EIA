package br.net.eia.model.municipio;

public enum UF {
	
	AC("12", "Acre"),
	AL("27", "Alagoas"),
	AP("16", "Amapá"),
	AM("13", "Amazonas"),
	BA("29", "Bahia"),
	CE("23", "Ceará"),
	DF("53", "Distrito Federal"),
	ES("32", "Espírito Santo"),
	GO("52", "Goiás"),
	MA("21", "Maranhão"),
	MT("51", "Mato Grosso"),
	MS("50", "Mato Grosso do Sul"),
	MG("31", "Minas Gerais"),
	PA("15", "Pará"),
    PB("25", "Paraíba"),
    PE("26", "Pernambuco"),
    PI("22", "Piauí"),
    PR("41", "Paraná"),
    RJ("33", "Rio de Janeiro"),
    RO("11", "Rondônia"),
    RR("14", "Roraima"), 
    RN("24", "Rio Grande do Norte"),
    RS("43", "Rio Grande do Sul"),
    SE("28", "Sergipe"),
    SC("42", "Santa Catarina"),
    SP("35", "São Paulo"),
	TO("17", "Tocantins"),
	EX("99", "Exterior");
	
	private UF(String cUF, String nomeUF) {
		this.cUF = cUF;
		this.nomeUF = nomeUF;
	}
	
	private String cUF;
	private String nomeUF;
	
	/**
	 * C�digo de UF do IBGE.
	 */
	public String getCUF() {
		return cUF;
	}
	
	/**
	 * Nome da unidade da federação.
	 */
	public String getNomeUF() {
		return nomeUF;
	}
	
	/**
	 * Conveniente para ler o literal da UF a partir de cUF.
	 * 
	 * @param cUF
	 */
	public static UF getUF(String cUF) {
		for (UF uf: UF.values()) {
			if (uf.getCUF().equals(cUF)) {
				return uf;
			}
		}
		return null;
	}
	
}