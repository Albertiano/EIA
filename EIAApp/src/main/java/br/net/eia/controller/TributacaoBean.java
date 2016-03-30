package br.net.eia.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import br.net.eia.model.contato.TpContato;
import br.net.eia.model.contato.TpDoc;
import br.net.eia.model.emitente.Emitente;
import br.net.eia.model.municipio.UF;
import br.net.eia.model.produto.imposto.Destino;
import br.net.eia.model.produto.imposto.Tributacao;
import br.net.eia.model.produto.imposto.Tributo;
import br.net.eia.model.produto.imposto.cofins.COFINS;
import br.net.eia.model.produto.imposto.cofins.CST_COFINS;
import br.net.eia.model.produto.imposto.icms.CST_ICMS;
import br.net.eia.model.produto.imposto.icms.ICMS;
import br.net.eia.model.produto.imposto.icms.Origem;
import br.net.eia.model.produto.imposto.ipi.CST_IPI;
import br.net.eia.model.produto.imposto.ipi.IPI;
import br.net.eia.model.produto.imposto.pis.CST_PIS;
import br.net.eia.model.produto.imposto.pis.PIS;
import br.net.eia.repository.Tributacoes;

@Named
@ViewScoped
public class TributacaoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private Set<ConstraintViolation<Tributacao>> validationErrors;
	
	@Inject
	private Tributacoes registrosRepo;
	
	private List<Tributacao> registros;
		
	private Tributacao registro;
	
	private String mensClass;
		
	private String search;
	
	private Destino destinoAtual;
	
	@PostConstruct
	private void init(){
		this.setMensClass("oculta");
	}
	
	public void carregar(){
		this.setRegistros((List<Tributacao>) registrosRepo.filtrar(Emitente.Token()));
	}
	
	public void preparar(AjaxBehaviorEvent event){
		setMensClass("oculta");
		id =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if(id!=null){
			this.registro = registrosRepo.retrieve(Long.parseLong(id));
			id=null;
		}else{
			registro = new Tributacao();
			registro.setNome("Padrão");
			registro.setDescricao("Padrao do sistema");
			registro.setDestino(createDestinos());
		}
	}
	
	public void prepararDest(AjaxBehaviorEvent event){
		String idDestino =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idDestino");
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println(destinoAtual.getEstado());
		System.out.println(idDestino);
	}
	
	private boolean validar(){
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        validationErrors = validator.validate(registro);
        if(validationErrors.isEmpty()){
            return true;
        }
        for(ConstraintViolation<Tributacao> error : validationErrors){
        	setMensClass("mostra alert-danger");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(
            		FacesMessage.SEVERITY_ERROR,
            		error.getPropertyPath()+": "+error.getMessage(),
            		"Detalhe")
            	);
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds()
            .add("globalMessage");
        }
        
        return false;
	}
	
	public void gravar(){		
		if(validar()){
			try{
				if(registro.getId()==null){
					Emitente e = new Emitente();
					e.setId(1L);
					registro.setEmitente(e);
					registrosRepo.create(registro);
					setMensClass("mostra alert-success");
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inserido com Sucesso!" ,"Sucesso"));
				}else{					
					registrosRepo.update(registro);
					setMensClass("mostra alert-info");
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alterado com Sucesso!" ,"Sucesso"));
				}				
				carregar();
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds()
	            .add("tabela");
			}catch(Exception e){
				setMensClass("mostra alert-danger");
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro" , e.getMessage()));
			}
		}	
		
	}
	
	public void delete(){
		registrosRepo.delete(registro);
		carregar();
	}
	
	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}

	public TpContato[] getTpContatos(){
		return TpContato.values();
	}
	public TpDoc[] getTpDoc(){
		return TpDoc.values();
	}
	
	public UF[] getUFs(){
		return UF.values();
	}
	
	private List<Destino> createDestinos() {
        List<Destino> destinos = new ArrayList<>();
        for (UF uf : UF.values()) {
            Destino d = new Destino();            
            d.setEstado(uf);
            d.setTributos(createTributos900());
            destinos.add(d);
        }
        return destinos;
    }

    private Tributo createTributos900() {
        ICMS icms = new ICMS();
        icms.setCstICMS(CST_ICMS.SN_900);
        icms.setOrigem(Origem.NACIONAL);
        icms.setvBCICMS(BigDecimal.ZERO);        
        icms.setpICMS(BigDecimal.ZERO);
        icms.setvICMS(BigDecimal.ZERO);
        icms.setpMVAST(BigDecimal.ZERO);
        icms.setpICMSST(BigDecimal.ZERO);
        IPI ipi = new IPI();
        ipi.setCstIPI(CST_IPI.IPI_53);
        ipi.setcEnq("999");
        PIS pis = new PIS();
        pis.setCstPIS(CST_PIS.PIS_07);
        COFINS cofins = new COFINS();
        cofins.setCstCOFINS(CST_COFINS.COFINS_07);

        Tributo t = new Tributo();
        t.setNome("Padrao");
        t.setDescricao("Padrao");
        t.setCfop("5102");
        t.setIcms(icms);
        t.setIpi(ipi);
        t.setPis(pis);
        t.setCofins(cofins);

        return t;
    }

	public List<Tributacao> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Tributacao> registros) {
		this.registros = registros;
	}

	public String getMensClass() {
		return mensClass;
	}

	public void setMensClass(String mensClass) {
		this.mensClass = mensClass;
	}

	public Tributacao getRegistro() {
		return registro;
	}

	public void setRegistro(Tributacao registro) {
		this.registro = registro;
	}

	public Destino getDestinoAtual() {
		return destinoAtual;
	}

	public void setDestinoAtual(Destino destinoAtual) {
		this.destinoAtual = destinoAtual;
	}
	
}
