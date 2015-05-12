package br.net.eia.notafiscal.adicionais;

public enum SitNFe {
	AUTORIZADA("Autorizada"), CANCELADA("Cancelada"), DIGITACAO("Em Digitação"), DENEGADA(
			"Denegada"), REJEITADA("Rejeitada");
	String txt;
	
	SitNFe(String string){
		this.txt = string;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return txt;
	}
}
