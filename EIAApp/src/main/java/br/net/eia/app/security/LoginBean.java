package br.net.eia.app.security;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginBean {

	@Inject
	private Usuario usuario;
	
	private String nomeUsuario;
	private String senha;

	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if ("1".equals(this.nomeUsuario) && "1".equals(this.senha)) {
			this.usuario.setNome(this.nomeUsuario);
			this.usuario.setDataLogin(new Date());
			System.out.println("Nome: "+usuario.getNome());
			System.out.println("Data: "+usuario.getDataLogin());
			return "/paises.xhtml";
		} else {
			FacesMessage mensagem = new FacesMessage();
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			mensagem.setSummary("Verifique!");
			mensagem.setDetail("Usuário/senha inválidos!");
			context.addMessage(null, mensagem);
		}
		
		return null;
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/Login?faces-redirect=true";
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
