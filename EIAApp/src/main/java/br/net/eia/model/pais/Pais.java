package br.net.eia.model.pais;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.model.BaseEntity;

@Entity
@XmlRootElement
public class Pais extends BaseEntity implements Serializable{
  
	private static final long serialVersionUID = 1L;
	private int cPais;
    private String xPais;

    public Pais(){
    }

    public Pais(int codigo, String nome){
        this.cPais = codigo;
        this.xPais = nome;
    }

    /**
     * @return the cPais
     */
    public int getcPais() {
        return cPais;
    }

    /**
     * @param cPais the cPais to set
     */
    public void setcPais(int cPais) {
        this.cPais = cPais;
    }

    /**
     * @return the xPais
     */
    public String getxPais() {
        return xPais;
    }

    /**
     * @param xPais the xPais to set
     */
    public void setxPais(String xPais) {
        this.xPais = xPais;
    }
 
	@Override
	public String toString() {
		if(this.getxPais()!=null){
			return getxPais();
		}
		return super.toString();
	}
}
