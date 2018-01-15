package controle;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import util.jsf.FacesUtil;
import util.report.ExecutorRelatorio;

@Named
@Stateless
public class RelatorioFaltaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	
	public RelatorioFaltaBean() {
		super();
	}




	private Date dataInicio;
	private Date dataFim;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@PersistenceContext(unitName = "safiraPU")
	private Session session;

	public void emitir() {
		System.out.println("  emissao relatorio");
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatoriofaltas.jasper",
				this.response, parametros, "faltas.pdf");
		
		
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}


	
	
	public Date getDataInicio() {
		return dataInicio;
	}




	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}




	public Date getDataFim() {
		return dataFim;
	}




	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}




	public void teste(){
		System.out.println(" as as as sa sa s");
	}



}