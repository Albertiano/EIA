package br.net.eia.produto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.emitente.Emitente;
import br.net.eia.entities.BaseEntity;
import br.net.eia.entities.HATEOASEntity;
import br.net.eia.entities.Link;
import br.net.eia.produto.imposto.Tributacao;
import br.net.eia.produto.unidade.Unidade;

@Entity
@XmlRootElement
public class Produto extends BaseEntity  implements
HATEOASEntity {
	
	@Transient
	private Collection<Link> links = new HashSet<Link>();
	
	public void createStandardLinks() {
		addLink(new Link(UriBuilder.fromPath(getId().toString()).build()
				.toASCIIString(), "self"));
		/**
		 * if (getPortrait() != null) { Link portraitLink = new
		 * Link(UriBuilder.fromPath
		 * ("{id}/portrait").build(getId()).toASCIIString(), "portrait");
		 * addLink(portraitLink); setPortrait(null); }
		 **/
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
	
    private String codigo;
    private String referencia;
    private String descricao;
    @OneToOne
    private Unidade unidade;
    private boolean desativado;
    private String ncm;
    private BigDecimal precoCusto;
    private BigDecimal mLucro;
    private BigDecimal precoVenda;
    private BigDecimal descMax;
    private BigDecimal pesoBruto;
    private BigDecimal pesoLiquido;
    private BigDecimal estoqueMin;
    private BigDecimal estoque;    
    private String localizacao;
    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn
    private List<FornecedorProduto> fornecedores;
    @OneToOne
	private Tributacao tributacao;
    private String extipi;
    private String genero;
    private String cEan;
    private String cEanTrib;
    @OneToOne
    private Unidade utrib;
    private BigDecimal vuntrib;    
    @ManyToOne
	private Emitente emitente;

    /**
     * @return the barras
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param barras the barras to set
     */
    public void setCodigo(String barras) {
    	this.codigo = barras;    	
    }

    public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	/**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the unidade
     */
    public Unidade getUnidade() {
        return unidade;
    }

    /**
     * @param unidade the unidade to set
     */
    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }
    /**
     * @return the precoCusto
     */
    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    /**
     * @param precoCusto the precoCusto to set
     */
    public void setPrecoCusto(BigDecimal precoCusto) {
        this.precoCusto = precoCusto;
    }

    public BigDecimal getmLucro() {
		return mLucro;
	}

	public void setmLucro(BigDecimal mLucro) {
		this.mLucro = mLucro;
	}

	/**
     * @return the precoVenda
     */
    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    /**
     * @param precoVenda the precoVenda to set
     */
    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    

    public BigDecimal getDescMax() {
		return descMax;
	}

	public void setDescMax(BigDecimal descMax) {
		this.descMax = descMax;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	/**
     * @return the pesoBruto
     */
    public BigDecimal getPesoBruto() {
    	if(pesoBruto==null){
    		return BigDecimal.ZERO;
    	}
        return pesoBruto;
    }

    /**
     * @param pesoBruto the pesoBruto to set
     */
    public void setPesoBruto(BigDecimal pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    /**
     * @return the pesoLiquido
     */
    public BigDecimal getPesoLiquido() {
    	if(pesoLiquido==null){
    		return BigDecimal.ZERO;
    	}
        return pesoLiquido;
    }

    /**
     * @param pesoLiquido the pesoLiquido to set
     */
    public void setPesoLiquido(BigDecimal pesoLiquido) {
        this.pesoLiquido = pesoLiquido;
    }

    /**
     * @return the estoqueMin
     */
    public BigDecimal getEstoqueMin() {
        return estoqueMin;
    }

    /**
     * @param estoqueMin the estoqueMin to set
     */
    public void setEstoqueMin(BigDecimal estoqueMin) {
        this.estoqueMin = estoqueMin;
    }

    /**
     * @return the estoque
     */
    public BigDecimal getEstoque() {
        return estoque;
    }

    /**
     * @param estoque the estoque to set
     */
    public void setEstoque(BigDecimal estoque) {
        this.estoque = estoque;
    }

    /**
     * @return the desativado
     */
    public Boolean getDesativado() {
        return desativado;
    }

    /**
     * @param desativado the desativado to set
     */
    public void setDesativado(Boolean desativado) {
        this.desativado = desativado;
    }

    /**
     * @return the localizacao
     */
    public String getLocalizacao() {
        return localizacao;
    }

    /**
     * @param localizacao the localizacao to set
     */
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

	public final List<FornecedorProduto> getFornecedores() {
		return fornecedores;
	}

	public final void setFornecedores(List<FornecedorProduto> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}

	public void setDesativado(boolean desativado) {
		this.desativado = desativado;
	}

	public Tributacao getTributacao() {
		return tributacao;
	}

	public void setTributacao(Tributacao tributacao) {
		this.tributacao = tributacao;
	}

	public String getExtipi() {
		return extipi;
	}

	public void setExtipi(String extipi) {
		this.extipi = extipi;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getcEan() {
		return cEan;
	}

	public void setcEan(String cEan) {
		this.cEan = cEan;
	}

	public String getcEanTrib() {
		return cEanTrib;
	}

	public void setcEanTrib(String cEanTrib) {
		this.cEanTrib = cEanTrib;
	}

	public Unidade getUtrib() {
		return utrib;
	}

	public void setUtrib(Unidade utrib) {
		this.utrib = utrib;
	}

	public BigDecimal getVuntrib() {
		return vuntrib;
	}

	public void setVuntrib(BigDecimal vuntrib) {
		this.vuntrib = vuntrib;
	}
}
