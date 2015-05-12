package br.net.eia.movimento;

public enum TpMovimento {
	PEDIDO("S","Saída"),COMPRA("E","Entrada");
	
	String valor;
	String desc;
	
	TpMovimento(String string, String des){
			this.valor = string;
			this.desc = des;
		}

}
