package br.net.eia.notafiscal.adicionais;

public enum IndIEDest {
	CONTRIBUINTE("1", "Contribuinte ICMS"), 
	ISENTO("2",	"Contribuinte isento de Inscrição no cadastro de Contribuintes do ICMS"), 
	NAO_CONTRIBUINTE("9", "Não Contribuinte, que pode ou não possuir Inscrição Estadual no Cadastro de Contribuintes do ICMS");
	
	String value;
	String desc;

	private IndIEDest(String v, String d) {
		this.value = v;
		this.desc = d;
	}

	public String getValue() {
		return value;
	}
}
