package controle;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.jsf.FacesUtil;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletRequest request;

	@Inject
	private HttpServletResponse response;

	public LoginBean() {
		if (FacesUtil.isPostback() == true) {

		} else {
			System.out.println("INICIADO LoginBean ");
		}

	}

	private String empresaId;
	private String username;
	private String password;

	public void preRender() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		String parameter_value = (String) facesContext.getExternalContext().getRequestParameterMap().get("invalid");

		if ("true".equals(parameter_value)) {
			FacesUtil.addErrorMessage("Usuário ou senha ou código do empregador inválido !");

		}

	}

	public String getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(String empresaId) {
		System.out.println(" setEmpresaId no login " + empresaId);
		// System.out.println(" email" + username);

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("schema", empresaId);

		this.empresaId = empresaId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String doLogin() throws IOException, ServletException {

		System.out.println("----------------" + username);
		System.out.println("----------------" + empresaId);

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(username, empresaId);

		// Usuario usu = usuarios.porEmail(username);
		//
		// if (usu != null) {
		//
		// System.out.println("achou usu ............");
		// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuok",
		// "true");
		//
		// }
		//

		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check");

		dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());

		FacesContext.getCurrentInstance().responseComplete();

		return null;
	}

}