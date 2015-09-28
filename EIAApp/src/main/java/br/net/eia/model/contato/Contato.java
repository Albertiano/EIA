package br.net.eia.model.contato;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotEmpty;
import br.net.eia.model.BaseEntity;
import br.net.eia.model.emitente.Emitente;
import br.net.eia.model.municipio.Municipio;
import br.net.eia.model.municipio.UF;
import br.net.eia.model.pais.Pais;

@Entity
@XmlRootElement
public class Contato extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.STRING)
    @Column(name="tp_dest")
    private TpDestinatario tpDest;
    private String codigo;
    @Enumerated(EnumType.STRING)
    @Column(name="tp_doc")
    private TpDoc tpDoc;
    @Column(name="num_doc")
    private String numDoc;
    private String IE;
    @NotEmpty
    private String nome;
    private String fone;
    private String obs;
    private String email;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    @Enumerated(EnumType.STRING)
    @NotNull
    private UF uf;
    @ManyToOne
    @NotNull
    @JoinColumn(name="municipio")
    private Municipio municipio;
    @ManyToOne
    @NotNull
    @JoinColumn(name="pais")
    private Pais pais;
    private String contato;
    private boolean desabilitado;
    private boolean bloqueado;
    @Column(name="fone_res")
    private String foneRes;
    private String celular;
    private String fantasia;
    private String ISUF;
    @ManyToOne
    @JoinColumn(name="emitente")
    private Emitente emitente;
    @Enumerated(EnumType.STRING)
    @Column(name="tp_contato")
    private TpContato tpContato;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private List<CampoExtra> camposExtras;
    @Enumerated(EnumType.STRING)
    @Column(name="indiedest")
    private IndIEDest indIEDest;

    public Contato() {

    }

	public TpDestinatario getTpDest() {
		return tpDest;
	}

	public void setTpDest(TpDestinatario tpDest) {
		this.tpDest = tpDest;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public TpDoc getTpDoc() {
		return tpDoc;
	}

	public void setTpDoc(TpDoc tpDoc) {
		this.tpDoc = tpDoc;
	}

	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	public String getIE() {
		return IE;
	}

	public void setIE(String iE) {
		IE = iE;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public UF getUf() {
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public boolean isDesabilitado() {
		return desabilitado;
	}

	public void setDesabilitado(boolean desabilitado) {
		this.desabilitado = desabilitado;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public String getFoneRes() {
		return foneRes;
	}

	public void setFoneRes(String foneRes) {
		this.foneRes = foneRes;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getISUF() {
		return ISUF;
	}

	public void setISUF(String iSUF) {
		ISUF = iSUF;
	}

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}

	public TpContato getTpContato() {
		return tpContato;
	}

	public void setTpContato(TpContato tpContato) {
		this.tpContato = tpContato;
	}

	public List<CampoExtra> getCamposExtras() {
		return camposExtras;
	}

	public void setCamposExtras(List<CampoExtra> camposExtras) {
		this.camposExtras = camposExtras;
	}

	public IndIEDest getIndIEDest() {
		return indIEDest;
	}

	public void setIndIEDest(IndIEDest indIEDest) {
		this.indIEDest = indIEDest;
	}

}
