package br.net.eia.util;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public class Formatador {

	public String formatterCNPJCPF(String value) {
		MaskFormatter mf = null;
		if(value==null){
			value="";
		}
		value = value.replaceAll("[^0-9]", "");
		try {
			if (value.length() == 11) {
				mf = new MaskFormatter("###.###.###-##");
			}
			if (value.length() == 14) {
				mf = new MaskFormatter("##.###.###/####-##");
			}
			if (mf == null) {
				return value;
			}
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(value);
		} catch (ParseException ex) {
			return value;
		}
	}

	public String formatterCEP(String value) {
		MaskFormatter mf = null;
		value = value.replaceAll("[^0-9]", "");
		try {
			if (value.length() == 8) {
				mf = new MaskFormatter("##.###-###");
			}
			if (mf == null) {
				return value;
			}
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(value);
		} catch (ParseException ex) {
			return value;
		}
	}

	public String formatterFone(String value) {
		MaskFormatter mf = null;
		value = value.replaceAll("[^0-9]", "");
		try {
			if (value.length() == 10) {
				mf = new MaskFormatter("(##)####-####");
			}
			if (value.length() == 11) {
				mf = new MaskFormatter("(##)#####-####");
			}
			if (mf == null) {
				return value;
			}
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(value);
		} catch (ParseException ex) {
			return value;
		}
	}
	
	public String formatterDate(String value) {
		MaskFormatter mf = null;
		value = value.replaceAll("[^0-9]", "");
		try {
			if (value.length() == 8) {
				mf = new MaskFormatter("##/##/####");
			}
			if (value.length() == 14) {
				mf = new MaskFormatter("##/##/#### ##:##:##");
			}
			if (mf == null) {
				return value;
			}
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(value);
		} catch (ParseException ex) {
			return value;
		}
	}		
	
}
