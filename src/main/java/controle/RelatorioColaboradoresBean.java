package controle;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import util.jsf.FacesUtil;
import util.report.ExecutorRelatorio;

@Named
@Stateless
public class RelatorioColaboradoresBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@PersistenceContext(unitName = "safiraPU")
	private Session session;

	private String empresa; 
	private String filial; 
	private String depto; 
	private String demitido;

	public RelatorioColaboradoresBean() {
	}

	public void emitirRelatorio() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);		
		parametros.put("empresa", this.empresa);
		parametros.put("filial", this.filial);
		parametros.put("depto", this.depto);
	//	parametros.put("demitido", "");

		ExecutorRelatorio executor = new ExecutorRelatorio(
				"/relatorios/colaborador_doc.jasper", this.response, parametros,
				"colaboradores.pdf");

		
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil
					.addErrorMessage("A execução do relatório não retornou dados.");
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

	public String getFilial() {
		return filial;
	}

	public void setFilial(String filial) {
		this.filial = filial;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String getDemitido() {
		return demitido;
	}

	public void setDemitido(String demitido) {
		this.demitido = demitido;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}


	
	
}
