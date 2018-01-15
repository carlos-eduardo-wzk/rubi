package tarefas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import model.Colaborador;
import util.ProcessaMarcacoes;


@Named
public class CalcImportacao implements Runnable, Serializable {

	private static final long serialVersionUID = 1L;

	private Date data;
	private Date data2;
	private List<Colaborador> listacolab;
	

	@Inject
	private ProcessaMarcacoes pr;
	
	public CalcImportacao() {
	}
	
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getData2() {
		return data2;
	}

	public void setData2(Date data2) {
		this.data2 = data2;
	}

	public List<Colaborador> getListacolab() {
		return listacolab;
	}

	public void setListacolab(List<Colaborador> listacolab) {
		this.listacolab = listacolab;
	}

	
	public void CalcImportacaoRun(Date data, Date data2, boolean sobrepor,
			List<Colaborador> listacolab, boolean repropcessar,
			Object listaAcerto) {
		 this.data = data;
		 this.data2 = data2;
		 this.listacolab = listacolab;
		 run();
		
	}

	@Override
	public void run() {
		System.out.println("entrei no calculo via executor");
		System.out.println(data);
		System.out.println(data2);
		System.out.println(listacolab.size());
		
		try {
			//pr.pro = new ProcessaMarcacoes();
			pr.processarMarcacao(data,
								data2, false, listacolab, true, null);
			System.out.println("-------------------------");
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

}
