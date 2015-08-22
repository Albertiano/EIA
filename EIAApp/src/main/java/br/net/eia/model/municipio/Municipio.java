package br.net.eia.model.municipio;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.model.BaseEntity;

@Entity
@XmlRootElement
public class Municipio extends BaseEntity implements Serializable{
   
	private static final long serialVersionUID = 229805288694101112L;
    
    private int cMun;
    private String xMun;
    @Enumerated(EnumType.STRING)
    private UF UF;

    public Municipio(){
    }

    public int getcMun() {
        return cMun;
    }

    public void setcMun(int cMun) {
        this.cMun = cMun;
    }

    public String getxMun() {
        return xMun;
    }

    public void setxMun(String xMun) {
        this.xMun = xMun;
    }

    public UF getUF() {
        return UF;
    }

    public void setUF(UF uf) {
        this.UF = uf;
    }

    @Override
    public String toString(){
        return this.xMun;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((UF == null) ? 0 : UF.hashCode());
		result = prime * result + cMun;
		result = prime * result + ((xMun == null) ? 0 : xMun.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Municipio other = (Municipio) obj;
		if (UF != other.UF)
			return false;
		if (cMun != other.cMun)
			return false;
		if (xMun == null) {
			if (other.xMun != null)
				return false;
		} else if (!xMun.equals(other.xMun))
			return false;
		return true;
	}
    
}
