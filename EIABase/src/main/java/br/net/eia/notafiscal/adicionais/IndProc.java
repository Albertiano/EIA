package br.net.eia.notafiscal.adicionais;

public enum IndProc {
	SEFAZ("0"),
	JUSTICA_FEDERAL("1"),
	JUSTICA_ESTADUAL("2"),
	SECEX_RFB("3"),
	OUTROS("9");
	
	private String value;
	
	private IndProc(String v) {
		this.value = v;
	}
	
	public String getValue() {
		return value;
	}
}
