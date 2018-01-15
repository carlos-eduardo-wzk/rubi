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
public class RelatorioFaltasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@PersistenceContext(unitName = "safiraPU")
	private Session session;
	
	private Boolean abonado;
	private Integer empresa; 
	private Integer filial; 
	private Integer depto; 

	public RelatorioFaltasBean() {
		super();
	}

	public void emitirRelatorio() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);
//
//		if (abonado == true) {
//			parametros.put("Abonado", 1);
//		} else {
//			parametros.put("Abonado", 0);
//		}


		ExecutorRelatorio executor = new ExecutorRelatorio(
				"/relatorios/faltas.jasper", this.response, parametros,
				"faltas.pdf");

		
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

	public Boolean getAbonado() {
		return abonado;
	}

	public void setAbonado(Boolean abonado) {
		this.abonado = abonado;
	}

	public Integer getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}

	public Integer getFilial() {
		return filial;
	}

	public void setFilial(Integer filial) {
		this.filial = filial;
	}

	public Integer getDepto() {
		return depto;
	}

	public void setDepto(Integer depto) {
		this.depto = depto;
	}

	
	
}
