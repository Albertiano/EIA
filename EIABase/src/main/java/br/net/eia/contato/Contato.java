package br.net.eia.contato;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.contato.IContato;
import br.net.eia.contato.campoextra.CampoExtra;
import br.net.eia.emitente.Emitente;
import br.net.eia.entities.BaseEntity;
import br.net.eia.entities.HATEOASEntity;
import br.net.eia.entities.Link;
import br.net.eia.enums.TpDoc;
import br.net.eia.enums.UF;
import br.net.eia.municipio.Municipio;
import br.net.eia.notafiscal.adicionais.IndIEDest;
import br.net.eia.notafiscal.adicionais.TpDestinatario;
import br.net.eia.pais.Pais;

@Entity
@XmlRootElement
public class Contato extends BaseEntity implements
        HATEOASEntity, IContato {

    @Transient
    private Collection<Link> links = new HashSet<Link>();

    @Enumerated(EnumType.STRING)
    private TpDestinatario tpDest;
    private String codigo;
    @Enumerated(EnumType.STRING)
    private TpDoc tpDoc;
    private String numDoc;
    private String IE;
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
    private UF uf;
    @ManyToOne
    private Municipio municipio;
    @ManyToOne
    private Pais pais;
    private String contato;
    private boolean desabilitado;
    private boolean bloqueado;
    private String foneRes;
    private String celular;
    private boolean clienteFonte;
    private String fantasia;
    private String ISUF;
    @ManyToOne
    private Emitente emitente;
    @Enumerated(EnumType.STRING)
    private TpContato tpContato;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private List<CampoExtra> camposExtras;
    @Enumerated(EnumType.STRING)
    private IndIEDest indIEDest;

    public Contato() {

    }

    public List<CampoExtra> getCamposExtras() {
        return camposExtras;
    }

    public void setCamposExtras(List<CampoExtra> camposExtras) {
        this.camposExtras = camposExtras;
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

    /**
     * @return the logradouro
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * @param logradouro the logradouro to set
     */
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    public UF getUf() {
        if (getMunicipio() != null) {
            return getMunicipio().getUF();
        }
        return uf;
    }

    public void setUf(UF uf) {
        this.uf = uf;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the municipio
     */
    public Municipio getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the pais
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(Pais pais) {
        this.pais = pais;
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

    public void setContato(String contato) {
        this.contato = contato;
    }

    public void setDesabilitado(boolean desabilitado) {
        this.desabilitado = desabilitado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public void setFoneRes(String foneRes) {
        this.foneRes = foneRes;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setContatoFonte(boolean clienteFonte) {
        this.clienteFonte = clienteFonte;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getContato() {
        return contato;
    }

    public boolean isDesabilitado() {
        return desabilitado;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public String getFoneRes() {
        return foneRes;
    }

    public String getCelular() {
        return celular;
    }

    public boolean isContatoFonte() {
        return clienteFonte;
    }

    public String getFantasia() {
        return fantasia;
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

    public void createStandardLinks() {
        addLink(new Link(UriBuilder.fromPath(getId().toString()).build()
                .toASCIIString(), "self"));
        /**
         * if (getPortrait() != null) { Link portraitLink = new
         * Link(UriBuilder.fromPath
         * ("{id}/portrait").build(getId()).toASCIIString(), "portrait");
         * addLink(portraitLink); setPortrait(null); }
		 *
         */
    }

    public HATEOASEntity addLink(Link link) {
        this.links.add(link);
        return this;
    }

    @XmlElement(name = "link", namespace = "http://www.w3.org/1999/xlink")
    public Collection<Link> getLinks() {
        return this.links;
    }

    public void setLinks(Collection<Link> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return getNome();
    }

    public TpDestinatario getTpDest() {
        return tpDest;
    }

    public void setTpDest(TpDestinatario tpDest) {
        this.tpDest = tpDest;
    }

    public IndIEDest getIndIEDest() {
        return indIEDest;
    }
    
    public void setIndIEDest(IndIEDest indIEDest){
        this.indIEDest = indIEDest;
    }

    public String getIm() {
        // TODO Auto-generated method stub
        return null;
    }

    public String idEstrangeiro() {
        // TODO Auto-generated method stub
        return null;
    }

    public TpContato getTpContato() {
        return tpContato;
    }

    public void setTpContato(TpContato tpContato) {
        this.tpContato = tpContato;
    }
}
