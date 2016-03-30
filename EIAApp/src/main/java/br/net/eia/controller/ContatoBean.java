package br.net.eia.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
import br.net.eia.model.contato.Contato;
import br.net.eia.model.contato.TpContato;
import br.net.eia.model.contato.TpDoc;
import br.net.eia.model.emitente.Emitente;
import br.net.eia.model.municipio.Municipio;
import br.net.eia.model.municipio.UF;
import br.net.eia.model.pais.Pais;
import br.net.eia.repository.Contatos;
import br.net.eia.repository.Municipios;
import br.net.eia.repository.Paises;
import br.net.eia.util.Formatador;

@Named
@ViewScoped
public class ContatoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private Set<ConstraintViolation<Contato>> validationErrors;
	
	@Inject
	private Contatos contatosRepo;
	
	private List<Contato> contatos;
		
	private Contato contato;
	
	private String mensClass;
	
	@Inject
	private Paises paisRepo;
	private List<Pais> paises;
	
	@Inject
	private Municipios municipioRepo;
	
	private String search;
	
	@PostConstruct
	private void init(){
		this.paises = (List<Pais>) paisRepo.retrieve();
		this.mensClass = "oculta";
	}
	
	public void carregar(){
		this.contatos = (List<Contato>) contatosRepo.filtrar(null, Emitente.Token(), search, null, null, null);
	}
	
	public void preparar(AjaxBehaviorEvent event){
		mensClass = "oculta";
		id =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if(id!=null){
			this.contato = contatosRepo.retrieve(Long.parseLong(id));
			id=null;
		}else{
			contato = new Contato();
			contato.setPais(paises.get(26));
			contato.setUf(UF.PB);
		}
	}
	
	private boolean validar(){
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        validationErrors = validator.validate(contato);
        if(validationErrors.isEmpty()){
            return true;
        }
        for(ConstraintViolation<Contato> error : validationErrors){
        	mensClass = "mostra alert-danger";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(
            		FacesMessage.SEVERITY_WARN,
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
				if(contato.getId()==null){
					Emitente e = new Emitente();
					e.setId(1L);
					contato.setEmitente(e);
					contatosRepo.create(contato);
					mensClass = "mostra alert-success";
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inserido com Sucesso!" ,"Sucesso"));
				}else{					
					contatosRepo.update(contato);
					mensClass = "mostra alert-info";
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alterado com Sucesso!" ,"Sucesso"));
				}				
				carregar();
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds()
	            .add("tabela");
			}catch(Exception e){
				mensClass = "mostra alert-danger";
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro" , e.getMessage()));
			}
		}	
		
	}
	
	public void delete(){
		contatosRepo.delete(contato);
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
	
	public List<Pais> getPaises(){
		return paises;
	}
	
	public UF[] getUFs(){
		return UF.values();
	}

	public List<Municipio> getMunicipios(){
		try {
			if(contato!=null)
			return (List<Municipio>) municipioRepo.retrieveUF(contato.getUf().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	
	
	public void formataCNPJCPF(AjaxBehaviorEvent event) {
		try {
			Formatador formatter = new Formatador();
			String formatado = formatter.formatterCNPJCPF(contato.getNumDoc());
			contato.setNumDoc(formatado);
			if (formatado.length() == 14) {
				contato.setTpDoc(TpDoc.CPF);
			} else if (formatado.length() == 18) {
				contato.setTpDoc(TpDoc.CNPJ);
			} else {
				contato.setTpDoc(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void formataCEP(AjaxBehaviorEvent event) {
		try {
			Formatador formatter = new Formatador();
			String formatado = formatter.formatterCEP(contato.getCep());
			contato.setCep(formatado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//gets e setters
	
	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	public Contato getContato() {
		return contato;
	}
	
	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public String getUserID() {
		return id;
	}

	public void setUserID(String userID) {
		this.id = userID;
	}

	public String getMensClass() {
		return mensClass;
	}

	public void setMensClass(String mensClass) {
		this.mensClass = mensClass;
	}
}
