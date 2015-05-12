package br.net.eia.enums;




public enum TpDoc {
	CPF("###.###.###-##"),
	CNPJ("##.###.###/####-##");
	
	private String mascara;
	
	private TpDoc(String mc){
		mascara = mc;
	}
	
	public String getMascara(){
		return mascara;
	}
}
