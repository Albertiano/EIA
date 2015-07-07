package br.net.eia.util.nfe;

import br.net.eia.notafiscal.NotaFiscal;
import br.net.eia.util.ConversorDate;

public class ChaveAcessoNFe {
	
	public ChaveAcessoNFe() {

	}

	public static String getChave(NotaFiscal nf) {
		String chaveAcesso = null;
		String cUF = nf.getEmitente().getUf().getCUF();                           // Código da UF do emitente do Documento Fiscal.
		String data = ConversorDate.retornaDataHora(nf.getDhEmi()).replaceAll("[^0-9]", ""); 
		String dataAAMM = data.substring(6, 8)+data.substring(2,4);               // Ano e Mês de emissão da NF-e.
		String cnpjCpf = nf.getEmitente().getNumDoc();   // CNPJ do emitente.
		if (cnpjCpf != null) {
			cnpjCpf = cnpjCpf.replaceAll("[^0-9]", "");
		}else{
			cnpjCpf = "";
		}
		String mod = nf.getMod().toString();                                      // Modelo do Documento Fiscal.
		String serie = nf.getSerie().toString();                                  // Série do Documento Fiscal.
		String nNF = nf.getNumero().toString();                                   // Número do Documento Fiscal.
		String tpEmis = nf.getTpEmis().getValue();                                // Forma de emissão da NF-e
		String cNF = data.substring(0,2)+data.substring(8,14);                    // Código Numérico que compõe a Chave de Acesso.

		try {

			StringBuilder chave = new StringBuilder();
			chave.append(lpadTo(cUF, 2, '0'));
			chave.append(lpadTo(dataAAMM, 4, '0'));
			chave.append(lpadTo(cnpjCpf.replaceAll("\\D", ""), 14, '0'));
			chave.append(lpadTo(mod, 2, '0'));
			chave.append(lpadTo(serie, 3, '0'));
			chave.append(lpadTo(nNF, 9, '0'));
			chave.append(lpadTo(tpEmis, 1, '0'));
			chave.append(lpadTo(cNF, 8, '0'));
			chave.append(modulo11(chave.toString()));

			chaveAcesso = chave.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return chaveAcesso;
	}

	public static int modulo11(String chave) {
		int total = 0;
		int peso = 2;

		for (int i = 0; i < chave.length(); i++) {
			total += (chave.charAt((chave.length() - 1) - i) - '0') * peso;
			peso++;
			if (peso == 10)
				peso = 2;
		}
		int resto = total % 11;
		return (resto == 0 || resto == 1) ? 0 : (11 - resto);
	}

	public static String lpadTo(String input, int width, char ch) {
		String strPad = "";

		StringBuffer sb = new StringBuffer(input.trim());
		while (sb.length() < width)
			sb.insert(0, ch);
		strPad = sb.toString();

		if (strPad.length() > width) {
			strPad = strPad.substring(0, width);
		}
		return strPad;
	}
}