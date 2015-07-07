package br.net.eia.enums;




public enum TpLancamento {
	CREDITO(1),
	DEBITO(-1);
	
	private int tipo;
	
	private TpLancamento(int mc){
		tipo = mc;
	}
	
	public int getFator(){
		return tipo;
	}
}
