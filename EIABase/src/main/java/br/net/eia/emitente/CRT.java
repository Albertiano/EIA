package br.net.eia.emitente;

public enum CRT {
	SIMPLES_NACIONAL("1","Simples Nacional"),
	SIMPLES_NAC_EXC_SUBLIMETE("2","Simples Nacional, excesso sublimite de receita bruta"),
	NORMAL("3","Regime Normal");
	
	private String value;
	private String desc;
	
	private CRT(String v, String d) {
		this.value = v;
		this.desc = d;
	}
	public String getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}
}
