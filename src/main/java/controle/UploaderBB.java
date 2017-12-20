package controle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Marcacao;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import util.ProcessaMarcacoes;
import util.jsf.FacesUtil;

@Named
@ViewScoped
public class UploaderBB implements Serializable {

	private static final long serialVersionUID = 1L;

	private UploadedFile file;

	@Inject
	private ImportacaoAFD importacaoAFD;;

	@Inject
	private ProcessaMarcacoes pr;
		
	private Marcacao marcacao;

	private Date dataini;
	private Date datafim;

	private boolean sobreporJuncao;
	private boolean sobreporCalculo;

	public UploaderBB() {

	}

	public void handleFileUpload(FileUploadEvent event) {
		String nomeArquivo = event.getFile().getFileName();
		String caminho = "c:/lixo";
		BufferedReader br = null;
		String line = "";
		List<Marcacao> lstMarcacao = new ArrayList<Marcacao>();

		try {
			File targetFolder = new File(caminho);
			nomeArquivo = event.getFile().getFileName();
			InputStream inputStream = event.getFile().getInputstream();
			OutputStream out = new FileOutputStream(new File(targetFolder,
					event.getFile().getFileName()));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputStream.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			String data;
			String hora;
			String pis;
			String nsr;
			String numeroRelogio = "";
			String fonteDados;

			fonteDados = caminho + "/" + nomeArquivo;
			br = new BufferedReader(new FileReader(caminho + "/" + nomeArquivo));
			
			DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat formatter2 = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm:ss.S");


			
			if (sobreporJuncao == true) {
				// apaga as marcacoes importadas
				System.out.println(" Apaguei marcacaoes " + dataini);
				System.out.println(" Apaguei marcacaoes " + datafim);
				
				apagarMarcacaoNoPeriodo(dataini, datafim);
			}

			while ((line = br.readLine()) != null) {
				
				Marcacao marcacao = new Marcacao();

				try {

					// se � primeira linha
					if (line.substring(9, 10).equals("1")) {
						numeroRelogio = line.substring(187, 204);
						continue;
					}

					// se nao � marcacao
					if (!line.substring(9, 10).equals("3")) {
						continue;
					}

					// pega data
					data = line.substring(10, 12) + "/"
							+ line.substring(12, 14) + "/"
							+ line.substring(14, 18);

					if (data == null || data.equals(""))
						continue;

					// pega hora
					hora = line.substring(18, 20) + ":"
							+ line.substring(20, 22);

					// pega PIS
					pis = line.substring(22, 34);

					// pega nsr
					nsr = line.substring(0, 9);

					Date date2 = formatter2.parse(line.substring(10, 12) + "/"
							+ line.substring(12, 14) + "/"
							+ line.substring(14, 18) + " 00:00:00.0");

					Date date1 = formatter1.parse(line.substring(10, 12) + "/"
							+ line.substring(12, 14) + "/"
							+ line.substring(14, 18));

					
									
					if ((date1.compareTo(dataini)<0) || (date1.compareTo(datafim)>0)) {
						continue;						
					}
					
					
					// importa para banco
					// arquivo.setTimestamp(7, data)

					marcacao.setData(date2);

					try {
						marcacao.setHora(Integer.parseInt(hora.substring(0, 2))
								* 60 + Integer.parseInt(hora.substring(3, 5)));

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

					marcacao.setPis(pis);
					marcacao.setNsr(nsr);
					marcacao.setNumeroRelogio(numeroRelogio);
					marcacao.setFonteDados(fonteDados);
					lstMarcacao.add(marcacao);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} // while

			importacaoAFD.salvaListaMarcacao(lstMarcacao);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// apagar arquivo
		File targetFolder = new File(caminho);
		targetFolder.delete();

		//System.out.println("Feito");

	}// handleFileUpload

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload() {
		if (file != null) {
			FacesMessage message = new FacesMessage("Succesful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);

		}
	}

	public Date getDataini() {
		return dataini;
	}

	public void setDataini(Date dataini) {
		this.dataini = dataini;
	}

	public Date getDatafim() {
		return datafim;
	}

	public void setDatafim(Date datafim) {
		this.datafim = datafim;
	}

	public Marcacao getMarcacao() {
		return marcacao;
	}

	public void setMarcacao(Marcacao marcacao) {
		this.marcacao = marcacao;
	}

	public void onDateSelect(SelectEvent event) {
		// FacesContext facesContext = FacesContext.getCurrentInstance();
	}

	public boolean isDataPreenchida() {
		return dataini == null ? true : false;
	}

	public boolean isSobreporJunao() {
		return sobreporJuncao;
	}

	public void setSobreporJunao(boolean sobreporJunao) {
		this.sobreporJuncao = sobreporJunao;
	}

	public boolean isSobreporCalculo() {
		return sobreporCalculo;
	}

	public void setSobreporCalculo(boolean sobreporCalculo) {
		this.sobreporCalculo = sobreporCalculo;
	}

	public void apagarMarcacaoNoPeriodo(Date di, Date df) {
		importacaoAFD.apagarMarcacaoNoPeriodo(di, df);
	}

	/**
	 * Importacao : criar dias importador 
	 */
	public void importacao() {
		
		System.out.println("entrei na importacao apos clicar em importar");

		Calendar cal = Calendar.getInstance();
		cal.setTime(dataini);

		// dias entre as datas
		int dias = daysBetween(dataini, datafim);
		for (int i = 0; i <= dias; i++) {
             			
			// dia acrescentado de 1
			importacaoAFD.diaParaImportacao(cal.getTime(), sobreporCalculo);

			// acrescentar 1 dia
			cal.add(Calendar.DATE, 1);

		} // for
		FacesUtil.addInfoMessage("Importação finalizada");
	}// importacao


	// retorna o numero de dias entre duas datas
	public int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	
	public void  processar(String a) {
		
		//ProcessaMarcacoes pr = new ProcessaMarcacoes() ;
		
		if (a.equals("1")){
	     	pr.processarMarcacao(dataini, datafim, false, null,false,null);
		}
		if (a.equals("2")){
	     	pr.processarMarcacao(dataini, datafim, true, null, false,null);
		}
		FacesUtil.addInfoMessage("Processamento finalizado");
	}
	
}