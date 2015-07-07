package br.net.eia.notafiscal.ide;

public enum Versao {
	VERSAO_2("2.0"),
	VERSAO_3_1("3.10");
	
	String value;
	private Versao(String v){
		this.value = v;
	}
	
	public String getVersao(){
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
