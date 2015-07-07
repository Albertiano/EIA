package br.net.eia.enums;



public enum UF {
	
    PB("25", "Paraíba"),
        
	RO("11", "Rondônia"),
	AC("12", "Acre"),
	AM("13", "Amazonas"),
	RR("14", "Roraima"),
	PA("15", "Pará"),
	AP("16", "Amapá"),
	TO("17", "Tocantins"),
	MA("21", "Maranhão"),
	PI("22", "Piauí"),
	CE("23", "Ceará"),
	RN("24", "Rio Grande do Norte"),
	
	PE("26", "Pernambuco"),
	AL("27", "Alagoas"),
	SE("28", "Sergipe"),
	BA("29", "Bahia"),
	MG("31", "Minas Gerais"),
	ES("32", "Espírito Santo"),
	RJ("33", "Rio de Janeiro"),
	SP("35", "São Paulo"),
	PR("41", "Paraná"),
	SC("42", "Santa Catarina"),
	RS("43", "Rio Grande do Sul"),
	MS("50", "Mato Grosso do Sul"),
	MT("51", "Mato Grosso"),
	GO("52", "Goiás"),
	DF("53", "Distrito Federal"),
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
