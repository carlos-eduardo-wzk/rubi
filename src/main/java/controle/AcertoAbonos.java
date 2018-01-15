package controle;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceContext;

import model.Acerto;
import model.AcertoAbono;
import model.Colaborador;
import model.Depto;
import model.Empresa;
import model.Feriado;
import model.Filial;
import model.Historico;
import model.HorarioAvulso;
import model.HorarioColaborador;
import model.Jornada;
import model.MarcacaoDetalhe;
import model.MarcacaoDetalheTmp;
import model.MarcacaoOriginal;
import model.MotivoAbono;
import model.MotivoAfastamento;
import model.OcorrenciaApurada;
import model.UnidadeFederacao;

import org.hibernate.Session;
import org.primefaces.event.SelectEvent;

import repository.AcertosAbonos;
import repository.Colaboradores;
import repository.Deptos;
import repository.Empresas;
import repository.Feriados;
import repository.Filiais;
import repository.HorarioAvulsos;
import repository.Jornadas;
import repository.MarcacaoDetalhes;
import repository.Marcacoes;
import repository.MotivoAfastamentos;
import repository.OcorrenciaApuradas;
import tarefas.CalcImportacao;
import util.ProcessaMarcacoes;
import util.Rotinas;
import util.jsf.FacesUtil;
import filter.AcertoFilter;

@Named
@Stateless 
public class AcertoAbonos implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipo;
	private String matricula;
	private String nome;
	private Integer empresa;
	private String filial;
	private String depto;
	private Date dtini;
	private Date dtfim;
	private String hora;

	private Boolean orderna;
	private String mE1;
	private String mS1;
	private String mE2;
	private String mS2;
	private String mE3;
	private String mS3;
	private String mE4;
	private String mS4;

	private String horasAbon;

	private List<AcertoAbono> lstobj;

	private List<MarcacaoDetalheTmp> lstMarcacaoDetalheTmp;

	private MarcacaoOriginal marcacaoOriginal;

	private List<MotivoAbono> listaMotivoAbono;
	private List<Historico> listaHistorico;

	private List<AcertoAbono> selectedAcertos;

	@PersistenceContext(unitName = "safiraPU")
	private Session session;

	
	@Inject
	private CalcImportacao calcImportacao;
	
	
	@Inject
	private AcertosAbonos acertosAbonos;

	@Inject
	private AcertoFilter acertoFilter;

	private MotivoAbono motivoAbonoSelecionado;
	private MotivoAbono motivoAbonoSelecionadoGrupo;

	private AcertoAbono acertoSelecionado;

	@Inject
	private Marcacoes marcacoes;

	@Inject
	private Jornadas jornadas;

	@Inject
	private OcorrenciaApuradas ocorrenciaApuradas;

	private Jornada jornada;
	private MarcacaoDetalhe marcacaoDetalhe;

	@Inject
	private ProcessaMarcacoes pr;

	@Inject
	private Colaboradores colaboradores;

	@Inject
	private MarcacaoDetalhes marcacaoDetalhes;

	@Inject
	private Empresas empresas;

	@Inject
	private Filiais filiais;

	@Inject
	private Deptos deptos;

	@Inject
	private Feriados feriados;

	@Inject
	private MotivoAfastamentos motivoAfastamentos;

	@Inject
	private HorarioAvulsos horarioAvulsos;

	private List<Empresa> listaEmpresa;
	private List<Filial> listaFilial;
	private List<Depto> listaDepto;

	private Boolean chkAtraso;
	private Boolean chkSaida;
	private Boolean chkFalta;

	private Empresa empresaSelecionada;
	private Filial filialSelecionada;
	private Depto deptoSelecionada;

	private String[] selectedTipos;

	private String nomeSele;;

	private String dadosCalculo;

	@PostConstruct
	public void inicializar() {
		listaMotivoAbono = carregarListaMotivoAbonos();
		listaHistorico = carregarListaHistoricos();
		lstMarcacaoDetalheTmp = new ArrayList<MarcacaoDetalheTmp>();
		listaEmpresa = carregaEmpresa();
		listaFilial = carregaFilial();
		listaDepto = carregaDepto();

	}

	public String[] getSelectedTipos() {
		return selectedTipos;
	}

	public void setSelectedTipos(String[] selectedTipos) {
		this.selectedTipos = selectedTipos;
	}

	public String getDadosCalculo() {
		return dadosCalculo;
	}

	public void setDadosCalculo(String dadosCalculo) {
		this.dadosCalculo = dadosCalculo;
	}

	public String getNomeSele() {
		return nomeSele;
	}

	public void setNomeSele(String nomeSele) {
		this.nomeSele = nomeSele;
	}

	public Depto getDeptoSelecionada() {
		return deptoSelecionada;
	}

	public void setDeptoSelecionada(Depto deptoSelecionada) {
		this.deptoSelecionada = deptoSelecionada;
	}

	public Long getId() {
		return deptoSelecionada.getId();
	}

	public void setId(Long id) {
		deptoSelecionada.setId(id);
	}

	public String getSigla() {
		return deptoSelecionada.getSigla();
	}

	public void setSigla(String sigla) {
		deptoSelecionada.setSigla(sigla);
	}

	public boolean isImpressaoEspelho() {
		return deptoSelecionada.isImpressaoEspelho();
	}

	public void setImpressaoEspelho(boolean impressaoEspelho) {
		deptoSelecionada.setImpressaoEspelho(impressaoEspelho);
	}

	public String getEndereco() {
		return deptoSelecionada.getEndereco();
	}

	public void setEndereco(String endereco) {
		deptoSelecionada.setEndereco(endereco);
	}

	public String getBairro() {
		return deptoSelecionada.getBairro();
	}

	public void setBairro(String bairro) {
		deptoSelecionada.setBairro(bairro);
	}

	public String getCidade() {
		return deptoSelecionada.getCidade();
	}

	public void setCidade(String cidade) {
		deptoSelecionada.setCidade(cidade);
	}

	public UnidadeFederacao getUf() {
		return deptoSelecionada.getUf();
	}

	public void setUf(UnidadeFederacao uf) {
		deptoSelecionada.setUf(uf);
	}

	public String getCep() {
		return deptoSelecionada.getCep();
	}

	public void setCep(String cep) {
		deptoSelecionada.setCep(cep);
	}

	public String getEmail() {
		return deptoSelecionada.getEmail();
	}

	public void setEmail(String email) {
		deptoSelecionada.setEmail(email);
	}

	public boolean equals(Object obj) {
		return deptoSelecionada.equals(obj);
	}

	public List<Depto> getListaDepto() {
		return listaDepto;
	}

	public void setListaDepto(List<Depto> listaDepto) {
		this.listaDepto = listaDepto;
	}

	public List<Filial> getListaFilial() {
		return listaFilial;
	}

	public void setListaFilial(List<Filial> listaFilial) {
		this.listaFilial = listaFilial;
	}

	public Filial getFilialSelecionada() {
		return filialSelecionada;
	}

	public void setFilialSelecionada(Filial filialSelecionada) {
		this.filialSelecionada = filialSelecionada;
	}

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public List<Depto> carregaDepto() {
		return this.deptos.carregarListaDeptos();
	}

	public List<Filial> carregaFilial() {
		return this.filiais.carregarListaFiliais();
	}

	public List<Empresa> carregaEmpresa() {
		return this.empresas.carregarListaEmpresas();
	}

	public List<Empresa> getListaEmpresa() {
		return listaEmpresa;
	}

	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}

	public Boolean getChkAtraso() {
		return chkAtraso;
	}

	public void setChkAtraso(Boolean chkAtraso) {
		this.chkAtraso = chkAtraso;
	}

	public Boolean getChkSaida() {
		return chkSaida;
	}

	public void setChkSaida(Boolean chkSaida) {
		this.chkSaida = chkSaida;
	}

	public Boolean getChkFalta() {
		return chkFalta;
	}

	public void setChkFalta(Boolean chkFalta) {
		this.chkFalta = chkFalta;
	}

	public MotivoAbono getMotivoAbonoSelecionadoGrupo() {
		return motivoAbonoSelecionadoGrupo;
	}

	public void setMotivoAbonoSelecionadoGrupo(
			MotivoAbono motivoAbonoSelecionadoGrupo) {
		this.motivoAbonoSelecionadoGrupo = motivoAbonoSelecionadoGrupo;
	}

	public List<MotivoAbono> carregarListaMotivoAbonos() {
		return session.createQuery("from MotivoAbono").list();
	}

	public List<Historico> carregarListaHistoricos() {
		return session.createQuery("from Historico").list();
	}

	public List<Historico> getListaHistorico() {
		return listaHistorico;
	}

	public void setListaHistorico(List<Historico> listaHistorico) {
		this.listaHistorico = listaHistorico;
	}

	/**
	 * 
	 * carrega maracoes de acordo com filtro
	 * 
	 */

	public List<AcertoAbono> carregaAcerto(ActionEvent event) {
		acertoFilter.setNome(nome);
		acertoFilter.setMatricula(matricula);
		acertoFilter.setEmpresa(empresaSelecionada.getId());

		if (filialSelecionada != null) {
			acertoFilter.setFilial(filialSelecionada.getId());
		}

		if (deptoSelecionada != null) {
			acertoFilter.setDepto(deptoSelecionada.getId());
		}

		acertoFilter.setDataini(dtini);
		acertoFilter.setDatafim(dtfim);
		acertoFilter.setTipo(tipo);
		acertoFilter.setNome(nomeSele);

		lstobj = acertosAbonos.carregarAcertos(acertoFilter, selectedTipos);
		// zero
		acertoFilter = new AcertoFilter();
		String[] selectedTipos = null;

		return lstobj;
	}

	public MarcacaoOriginal getMarcacaoOriginal() {
		return marcacaoOriginal;
	}

	public void setMarcacaoOriginal(MarcacaoOriginal marcacaoOriginal) {
		this.marcacaoOriginal = marcacaoOriginal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
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

	public Date getDtini() {
		return dtini;
	}

	public void setDtini(Date dtini) {
		this.dtini = dtini;
	}

	public Date getDtfim() {
		return dtfim;
	}

	public void setDtfim(Date dtfim) {
		this.dtfim = dtfim;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public List<AcertoAbono> getLstobj() {
		return lstobj;
	}

	public void setLstobj(List<AcertoAbono> lstobj) {
		this.lstobj = lstobj;
	}

	public List<MotivoAbono> getListaMotivoAbono() {
		return listaMotivoAbono;
	}

	public void setListaMotivoAbono(List<MotivoAbono> listaMotivoAbono) {
		this.listaMotivoAbono = listaMotivoAbono;
	}

	public MotivoAbono getMotivoAbonoSelecionado() {
		return motivoAbonoSelecionado;
	}

	public void setMotivoAbonoSelecionado(MotivoAbono motivoAbonoSelecionado) {
		this.motivoAbonoSelecionado = motivoAbonoSelecionado;
	}

	public AcertoAbono getAcertoSelecionado() {
		return acertoSelecionado;
	}

	public void setAcertoSelecionado(AcertoAbono acertoSelecionado) {
		this.acertoSelecionado = acertoSelecionado;
	}

	public Jornada getJornada() {
		return jornada;
	}

	public void setJornada(Jornada jornada) {
		this.jornada = jornada;
	}

	public void carregaJornada() {

		// inicializa
		lstMarcacaoDetalheTmp = new ArrayList<MarcacaoDetalheTmp>();

		// acho o horario programado para o dia
		HorarioColaborador hc = marcacoes.horarioColaboradorDia(
				acertoSelecionado.getData(), acertoSelecionado.getPis());

		String jpql = " select  h2.jornada_id from horario  as h "
				+ " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id "
				+ " left join Jornada AS j   ON h2.Jornada_id = j.Jornada_id "
				+ " where (1=1) and (h.horario_id = :horario) and "
				+ "                (h2.seq =  ( SELECT "
				+ " 1 +  mod( (SELECT  :dia - cast( data_base as date) from Horario where horario_id= h2.horario_id  )  , CAST( vw_contaseqhorario.Expr1 AS BIGINT) ) "
				+ " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
				+ " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id"
				+ " WHERE        (vw_contaSeqHorario.horario_id = h2.horario_id ) ) )";

		BigInteger resultado = (BigInteger) session.createSQLQuery(jpql)
				.setParameter("dia", acertoSelecionado.getData())
				.setParameter("horario", hc.getHorario().getId())
				.uniqueResult();

		jornada = jornadas.porId(resultado.longValue());

		// carrega maracoes para edicao
		Rotinas rt = new Rotinas();

		mE1 = "";
		mS1 = "";
		mE2 = "";
		mS2 = "";
		mE3 = "";
		mS3 = "";
		mE4 = "";
		mS4 = "";

		if (acertoSelecionado.getH1() != null) {
			setmE1(rt.InteitoToHora(acertoSelecionado.getH1()));
		}
		if (acertoSelecionado.getH2() != null) {
			setmS1(rt.InteitoToHora(acertoSelecionado.getH2()));
		}
		if (acertoSelecionado.getH3() != null) {
			setmE2(rt.InteitoToHora(acertoSelecionado.getH3()));
		}
		if (acertoSelecionado.getH4() != null) {
			setmS2(rt.InteitoToHora(acertoSelecionado.getH4()));
		}
		if (acertoSelecionado.getH5() != null) {
			setmE3(rt.InteitoToHora(acertoSelecionado.getH5()));
		}
		if (acertoSelecionado.getH6() != null) {
			setmS3(rt.InteitoToHora(acertoSelecionado.getH6()));
		}
		if (acertoSelecionado.getH7() != null) {
			setmE4(rt.InteitoToHora(acertoSelecionado.getH7()));
		}
		if (acertoSelecionado.getH8() != null) {
			setmS4(rt.InteitoToHora(acertoSelecionado.getH8()));
		}

		marcacaoOriginal = marcacaoDetalhes
				.MarcacoesDoColaboradorOriginalNoDiaPorPis(
						acertoSelecionado.getData(), acertoSelecionado.getPis());

	}

	public String getmE1() {
		return mE1;
	}

	public void setmE1(String mE1) {
		this.mE1 = mE1;
	}

	public String getmS1() {
		return mS1;
	}

	public void setmS1(String mS1) {
		this.mS1 = mS1;
	}

	public String getmE2() {
		return mE2;
	}

	public void setmE2(String mE2) {
		this.mE2 = mE2;
	}

	public String getmS2() {
		return mS2;
	}

	public void setmS2(String mS2) {
		this.mS2 = mS2;
	}

	public String getmE3() {
		return mE3;
	}

	public void setmE3(String mE3) {
		this.mE3 = mE3;
	}

	public String getmS3() {
		return mS3;
	}

	public void setmS3(String mS3) {
		this.mS3 = mS3;
	}

	public String getmE4() {
		return mE4;
	}

	public void setmE4(String mE4) {
		this.mE4 = mE4;
	}

	public String getmS4() {
		return mS4;
	}

	public void setmS4(String mS4) {
		this.mS4 = mS4;
	}

	public String getHorasAbon() {
		return horasAbon;
	}

	public void setHorasAbon(String horasAbon) {
		this.horasAbon = horasAbon;
	}

	public Boolean getOrderna() {
		return orderna;
	}

	public void setOrderna(Boolean orderna) {
		this.orderna = orderna;
	}

	public List<MarcacaoDetalheTmp> getLstMarcacaoDetalheTmp() {
		return lstMarcacaoDetalheTmp;
	}

	public void setLstMarcacaoDetalheTmp(
			List<MarcacaoDetalheTmp> lstMarcacaoDetalheTmp) {
		this.lstMarcacaoDetalheTmp = lstMarcacaoDetalheTmp;
	}

	public List<AcertoAbono> getSelectedAcertos() {
		return selectedAcertos;
	}

	public void setSelectedAcertos(List<AcertoAbono> selectedAcertos) {
		this.selectedAcertos = selectedAcertos;
	}

	public void ordenaMarcacao() {

		if (lstMarcacaoDetalheTmp.size() > 0) {
			lstMarcacaoDetalheTmp = new ArrayList<MarcacaoDetalheTmp>();
		}

		List<Integer> list = new ArrayList<>();

		int[] digitadas = { 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] original = { 0, 0, 0, 0, 0, 0, 0, 0 };
		boolean achou;

		list.clear();
		Rotinas rt = new Rotinas();

		if (!mE1.isEmpty()) {
			list.add(rt.Hora2int(mE1));
		}
		if (!mS1.isEmpty()) {
			list.add(rt.Hora2int(mS1));
		}
		if (!mE2.isEmpty()) {
			list.add(rt.Hora2int(mE2));
		}
		if (!mS2.isEmpty()) {
			list.add(rt.Hora2int(mS2));
		}
		if (!mE3.isEmpty()) {
			list.add(rt.Hora2int(mE3));
		}
		if (!mS3.isEmpty()) {
			list.add(rt.Hora2int(mS3));
		}
		if (!mE4.isEmpty()) {
			list.add(rt.Hora2int(mE4));
		}
		if (!mS4.isEmpty()) {
			list.add(rt.Hora2int(mS4));
		}

		if (acertoSelecionado.getH1() != null) {
			original[0] = acertoSelecionado.getH1();
		}
		if (acertoSelecionado.getH2() != null) {
			original[1] = acertoSelecionado.getH2();
		}
		if (acertoSelecionado.getH3() != null) {
			original[2] = acertoSelecionado.getH3();
		}
		if (acertoSelecionado.getH4() != null) {
			original[3] = acertoSelecionado.getH4();
		}
		if (acertoSelecionado.getH5() != null) {
			original[4] = acertoSelecionado.getH5();
		}
		if (acertoSelecionado.getH6() != null) {
			original[5] = acertoSelecionado.getH6();
		}
		if (acertoSelecionado.getH7() != null) {
			original[6] = acertoSelecionado.getH7();
		}
		if (acertoSelecionado.getH8() != null) {
			original[7] = acertoSelecionado.getH8();
		}

		mE1 = "";
		mS1 = "";
		mE2 = "";
		mS2 = "";
		mE3 = "";
		mS3 = "";
		mE4 = "";
		mS4 = "";

		if (orderna == false) {
			Collections.sort(list);
		}
		int j = 0;

		if ((list.size() > 0) && (list.get(0) != null)) {
			if (list.get(0) > 0) {
				mE1 = rt.InteitoToHora(list.get(0));
				digitadas[j] = list.get(0);
				j++;
			}

		}
		if ((list.size() > 1) && (list.get(1) != null)) {
			if (list.get(1) > 0) {
				mS1 = rt.InteitoToHora(list.get(1));
				digitadas[j] = list.get(1);
				j++;
			}
		}
		if ((list.size() > 2) && (list.get(2) != null)) {
			if (list.get(2) > 0) {
				mE2 = rt.InteitoToHora(list.get(2));
				digitadas[j] = list.get(2);
				j++;
			}
		}
		if ((list.size() > 3) && (list.get(3) != null)) {
			if (list.get(3) > 0) {
				mS2 = rt.InteitoToHora(list.get(3));
				digitadas[j] = list.get(3);
				j++;
			}

		}
		if ((list.size() > 4) && (list.get(4) != null)) {
			if (list.get(4) > 0) {
				mE3 = rt.InteitoToHora(list.get(4));
				digitadas[j] = list.get(4);
				j++;
			}

		}
		if ((list.size() > 5) && (list.get(5) != null)) {
			if (list.get(5) > 0) {
				mS3 = rt.InteitoToHora(list.get(5));
				digitadas[j] = list.get(5);
				j++;
			}

		}
		if ((list.size() > 6) && (list.get(6) != null)) {
			if (list.get(6) > 0) {
				mE4 = rt.InteitoToHora(list.get(6));
				digitadas[j] = list.get(6);
				j++;
			}

		}
		if ((list.size() > 7) && (list.get(7) != null)) {
			if (list.get(7) > 0) {
				mS4 = rt.InteitoToHora(list.get(7));
				digitadas[j] = list.get(7);
				j++;
			}

		}

		MarcacaoDetalheTmp mdt = new MarcacaoDetalheTmp();

		for (int a : original) {
			System.out.println(a);
		}

		for (int b : digitadas) {
			System.out.println(b);
		}

		// analisa descartado
		for (int k = 0; k < 8; k++) {
			achou = false;
			for (int i = 0; i < 8; i++) {
				if (original[k] == digitadas[i]) {
					achou = true;
				}
			}// for

			if ((achou == false) && (original[k] > 0)) {
				mdt = new MarcacaoDetalheTmp();
				// criar deletador
				mdt.setData(acertoSelecionado.getData());
				mdt.setHora(original[k]);
				mdt.setPis(acertoSelecionado.getPis());
				mdt.setTipo("D");
				mdt.setMotivo("");
				lstMarcacaoDetalheTmp.add(mdt);
			}
		}// for

		// analisa digitados
		for (int k = 0; k < 8; k++) {
			achou = false;
			for (int i = 0; i < 8; i++) {
				if (digitadas[k] == original[i]) {
					achou = true;
				}
			} // for
			if ((achou == false) && (digitadas[k] > 0)) {
				mdt = new MarcacaoDetalheTmp();
				// criar inseridos
				mdt.setData(acertoSelecionado.getData());
				mdt.setHora(digitadas[k]);
				mdt.setPis(acertoSelecionado.getPis());
				mdt.setTipo("I");
				mdt.setMotivo("");
				lstMarcacaoDetalheTmp.add(mdt);
			}

		} // for

	}// ordenaMarcacao

	public void processaAcerto() {

		List<Colaborador> listacolab = new ArrayList<Colaborador>();

		Rotinas rot = new Rotinas();

		// gravar as maracacoes novas e excluidas em macacoesDetalhe
		for (int k = 0; k < lstMarcacaoDetalheTmp.size(); k++) {

			if (lstMarcacaoDetalheTmp.get(k).getTipo() == "I") {

				MarcacaoDetalhe marcacaoDetalhe = new MarcacaoDetalhe();
				marcacaoDetalhe.setColaborador(colaboradores
						.porPIS(acertoSelecionado.getPis()));
				marcacaoDetalhe.setData(acertoSelecionado.getData());
				marcacaoDetalhe.setHora(lstMarcacaoDetalheTmp.get(k).getHora());
				marcacaoDetalhe.setOriginal(false);
				marcacaoDetalhe.setTipo(lstMarcacaoDetalheTmp.get(k).getTipo());
				marcacaoDetalhe.setMotivo(lstMarcacaoDetalheTmp.get(k)
						.getMotivo());
				marcacaoDetalhes.salvaMarcacaoDetalhe(marcacaoDetalhe);
			}

			if (lstMarcacaoDetalheTmp.get(k).getTipo() == "D") {

				marcacaoDetalhe = marcacaoDetalhes
						.achMarcacaoDetalheDoColaboradorNoDiaPis(
								acertoSelecionado.getData(),
								acertoSelecionado.getPis(),
								lstMarcacaoDetalheTmp.get(k).getHora());

				System.out.println(marcacaoDetalhe);

				// se for original true .. consultar para saber o que gravar
				if ((marcacaoDetalhe == null)
						|| (marcacaoDetalhe.getOriginal() == false)) {
					marcacaoDetalhe.setOriginal(false);
				} else {
					marcacaoDetalhe.setOriginal(true);
				}

				marcacaoDetalhe.setTipo(lstMarcacaoDetalheTmp.get(k).getTipo());
				marcacaoDetalhe.setMotivo(lstMarcacaoDetalheTmp.get(k)
						.getMotivo());
				marcacaoDetalhes.salvaMarcacaoDetalhe(marcacaoDetalhe);
			}
		}// for

		System.out.println("processaAcerto --->>> " + acertoSelecionado);

		listacolab.add(colaboradores.porPIS(acertoSelecionado.getPis()));

//		pr.processarMarcacao(acertoSelecionado.getData(),
//				acertoSelecionado.getData(), false, listacolab, true, null);
		
//		ExecutorService executorService  = Executors.newCachedThreadPool();	
//		executorService.execute(new CalcImportacao(acertoSelecionado.getData(),
//				acertoSelecionado.getData(), false, listacolab, true, null));
	
		// chama runnable
		calcImportacao.CalcImportacaoRun(acertoSelecionado.getData(),
				acertoSelecionado.getData(), false, listacolab, true, null);
		
	}// processaAcerto

	public void processarAbono() {
		// ocorrenciaapurada

		// acha ocorrenciaApurada data,colaborador,ocorrencia

		System.out.println("motivo abono"
				+ motivoAbonoSelecionado.getMotivoAbono());

		System.out.println("Data " + acertoSelecionado.getData());
		System.out.println("atraso " + acertoSelecionado.getAtraso());
		System.out.println("cola id " + acertoSelecionado.getColaborador_id());
		System.out.println(" mot " + motivoAbonoSelecionado.getMotivoAbono());
		System.out.println("ID " + motivoAbonoSelecionado.getId());

		// nao esta vindo certo para falta = 7 mas ta vindo 0
		System.out.println(acertoSelecionado.getMatricula());
		System.out.println(acertoSelecionado.getAgrupamento());
		System.out.println(acertoSelecionado.getAtraso());
		System.out.println("sele Atraso " + chkAtraso);
		System.out.println("sele saida " + chkSaida);
		System.out.println("sele falya " + chkFalta);
		System.out.println(acertoSelecionado.getOcorrenciaAtraso_id());

		OcorrenciaApurada oa = new OcorrenciaApurada();
		System.out.println("ssass " + oa);

		// oa = ocorrenciaApuradas
		// .achaOcorrenciaApuradaDataColaboradorOcorrencia(acertoSelecionado);

		if (chkAtraso != null) {
			if (chkAtraso == true) {
				oa = ocorrenciaApuradas
						.achaOcorrenciaApuradaDataColaboradorOcorrencia2(
								acertoSelecionado.getOcorrenciaAtraso_id(),
								acertoSelecionado);
			}
		}

		if (chkSaida != null) {
			if (chkSaida == true) {
				oa = ocorrenciaApuradas
						.achaOcorrenciaApuradaDataColaboradorOcorrencia2(
								acertoSelecionado.getOcorrenciaSaida_id(),
								acertoSelecionado);
			}
		}

		if (chkFalta != null) {
			if (chkFalta == true) {
				oa = ocorrenciaApuradas
						.achaOcorrenciaApuradaDataColaboradorOcorrencia2(
								acertoSelecionado.getOcorrenciaFalta_id(),
								acertoSelecionado);
			}
		}
		System.out.println("ggggg22");
		System.out.println("ao depois de pesquisar " + oa);

		System.out.println("depois do ocorrenciaapurada ");

		MotivoAbono ma = new MotivoAbono();
		ma.setId(motivoAbonoSelecionado.getId());
		ma.setMotivoAbono(motivoAbonoSelecionado.getMotivoAbono());
		oa.setMotivoAbono(ma);
		System.out.println("horas no abono de falta "
				+ acertoSelecionado.getAtraso());

		Rotinas rt = new Rotinas();

		oa.setHorasAbonadas(rt.Hora2int(horasAbon));

		oa.setMotivoAbono(ma);
		oa.setDataAbono(new Date());

		System.out.println("oa " + oa);

		ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

	}// processarAbono

	public void AbonarGrupo() {

		if (selectedAcertos.size() <= 0) {
			FacesUtil.addInfoMessage("Nenhum dia selecionado para abonar");
			return;
		}

		try {
			if (motivoAbonoSelecionado.getId() == null) {
				FacesUtil.addInfoMessage("Nenhum Motivo escolhido");
				return;
			}
		} catch (Exception e) {
			FacesUtil.addInfoMessage("Nenhum Motivo escolhido");
			return;

		}

		// System.out.println("abona em grupo " + selectedAcertos.size());

		System.out.println("motivo abono"
				+ motivoAbonoSelecionado.getMotivoAbono());

		for (AcertoAbono a : selectedAcertos) {

			OcorrenciaApurada oa = new OcorrenciaApurada();
			// ** oa = ocorrenciaApuradas
			// ** .achaOcorrenciaApuradaDataColaboradorOcorrencia(a);
			MotivoAbono ma = new MotivoAbono();
			ma.setId(motivoAbonoSelecionado.getId());
			ma.setMotivoAbono(motivoAbonoSelecionado.getMotivoAbono());
			oa.setMotivoAbono(ma);

			// *** oa.setHorasAbonadas(acertoSelecionado.getHoras());
			oa.setMotivoAbono(ma);
			oa.setDataAbono(new Date());
			ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

		}// for

	}//

	public void recalculo() {

		if (selectedAcertos.size() <= 0) {
			FacesUtil.addInfoMessage("Nenhum dia selecionado para abonar");
			return;
		}

		List<Colaborador> listacolab = new ArrayList<Colaborador>();
		List<AcertoAbono> listacerto = new ArrayList<AcertoAbono>();

		for (AcertoAbono a : selectedAcertos) {

			listacolab.add(colaboradores.porPIS(a.getPis()));
			listacerto.add(a);

		}// for

		pr.processarMarcacao(dtini, dtfim, false, listacolab, true, listacerto);

	}// recalculo

	public boolean isSelecionouAbono() {
		boolean r = false;

		try {
			System.out.println("selectedAcertos.size()"
					+ selectedAcertos.size());
			r = selectedAcertos.size() > 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return r;

	}

	public void colaboradorSelecionado(SelectEvent event) {
		System.out.println("COLABORADOR DIALOG "
				+ ((Colaborador) event.getObject()).getNome());
		nomeSele = ((Colaborador) event.getObject()).getNome();
	}

	public void carregaCalculoDados() {
		// inicializa
		lstMarcacaoDetalheTmp = new ArrayList<MarcacaoDetalheTmp>();
		List<Object[]> listaOcor = new ArrayList<Object[]>();
		String ocorrencias = "";
		String hav = "Nenhum";

		// acho o horario programado para o dia
		HorarioColaborador hc = marcacoes.horarioColaboradorDia(
				acertoSelecionado.getData(), acertoSelecionado.getPis());

		String jpql = " select  h2.jornada_id from horario  as h "
				+ " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id "
				+ " left join Jornada AS j   ON h2.Jornada_id = j.Jornada_id "
				+ " where (1=1) and (h.horario_id = :horario) and "
				+ "                (h2.seq =  ( SELECT "
				+ " 1 +  mod( (SELECT  :dia - cast( data_base as date) from Horario where horario_id= h2.horario_id  )  , CAST( vw_contaseqhorario.Expr1 AS BIGINT) ) "
				+ " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
				+ " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id"
				+ " WHERE        (vw_contaSeqHorario.horario_id = h2.horario_id ) ) )";

		BigInteger resultado = (BigInteger) session.createSQLQuery(jpql)
				.setParameter("dia", acertoSelecionado.getData())
				.setParameter("horario", hc.getHorario().getId())
				.uniqueResult();

		jornada = jornadas.porId(resultado.longValue());

		// carrega maracoes para edicao
		Rotinas rt = new Rotinas();

		mE1 = "";
		mS1 = "";
		mE2 = "";
		mS2 = "";
		mE3 = "";
		mS3 = "";
		mE4 = "";
		mS4 = "";

		if (acertoSelecionado.getH1() != null) {
			setmE1(rt.InteitoToHora(acertoSelecionado.getH1()));
		}
		if (acertoSelecionado.getH2() != null) {
			setmS1(rt.InteitoToHora(acertoSelecionado.getH2()));
		}
		if (acertoSelecionado.getH3() != null) {
			setmE2(rt.InteitoToHora(acertoSelecionado.getH3()));
		}
		if (acertoSelecionado.getH4() != null) {
			setmS2(rt.InteitoToHora(acertoSelecionado.getH4()));
		}
		if (acertoSelecionado.getH5() != null) {
			setmE3(rt.InteitoToHora(acertoSelecionado.getH5()));
		}
		if (acertoSelecionado.getH6() != null) {
			setmS3(rt.InteitoToHora(acertoSelecionado.getH6()));
		}
		if (acertoSelecionado.getH7() != null) {
			setmE4(rt.InteitoToHora(acertoSelecionado.getH7()));
		}
		if (acertoSelecionado.getH8() != null) {
			setmS4(rt.InteitoToHora(acertoSelecionado.getH8()));
		}

		Object[] lo = null;

		jpql = " Select h.horario_id as hid , h.horario, data_base, h2.jornada_id, tipoRegime, e1, s1, e2, s2, seq, fechamento, hc.colaborador_id , "
				+ " tole1ant ,tole1dep, tole2ant ,tole2dep, tole3ant ,tole3dep, tole4ant ,tole4dep, tole5ant ,tole5dep, tole6ant ,tole6dep, tole7ant ,tole7dep, tole8ant ,tole8dep, "
				+ " preassinaladoe2,preassinalados1, "
				+ " (select motivoAfastamento_id from afastamento  where :dia between data_afastamento_inicial and data_afastamento_final and colaborador_id= hc.colaborador_id ) as Afastamento, "
				+ " (select cast('Folga' as text) from folga  where :dia  between data_afastamento_inicial and data_afastamento_final and colaborador_id= hc.colaborador_id) as folga , "
				+ " (select feriado from feriado where data_feriado = :dia and empresa_id=cola.empresa_id  ) as feriado, dsr,"
				+ " hc.horario_id, empresa_id	"
				+ " from horario  as h "
				+ " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id "
				+ " left join jornada AS j ON h2.Jornada_id = j.Jornada_id  "
				+ " inner join  ( select * from horario_colaborador as hc where  cast(data_inicio as date) <= :dia and colaborador_id =:colaborador "
				+ " order by data_inicio desc limit 1) as hc on (1=1 and (h.horario_id = hc.horario_id)) "
				+ " inner join colaborador as cola ON (hc.colaborador_id=cola.id)	"
				+ " and "
				+ " (h2.seq =  ( SELECT "
				+ " 1 +  mod( (SELECT  :dia - cast( data_base as date) from Horario as ho where horario_id= hc.horario_id  )  , CAST( vw_contaseqhorario.Expr1 AS BIGINT) ) "
				+ " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
				+ " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id "
				+ " WHERE        (vw_contaSeqHorario.horario_id = hc.horario_id ) ) ) ";

		try {
			lo = (Object[]) session.createSQLQuery(jpql)
					.setParameter("dia", acertoSelecionado.getData())
					.setParameter("colaborador", hc.getColaborador())
					.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("  carregaCalculoDados ");

		MotivoAfastamento ma = new MotivoAfastamento();
		ma = null;

		if (lo[30] != null) {
			ma = motivoAfastamentos.porId((long) ((BigInteger) lo[30])
					.longValue());
		}

		listaOcor = ocorrenciaApuradas
				.achaOcorrenciasApuradasDataColaboradorDia(
						acertoSelecionado.getData(), hc.getColaborador());

		for (Object[] ob : listaOcor) {
			ocorrencias += "\n" + (String) ob[1] + " = "
					+ rt.InteitoToHora((Integer) ob[0]);
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy EEE");

		HorarioAvulso horarioAvulso = new HorarioAvulso();
		horarioAvulso = null;
		horarioAvulso = horarioAvulsos.porDataCola(acertoSelecionado.getData(),
				hc.getColaborador());

		if (horarioAvulso != null) {
			hav = horarioAvulso.getHorario().getHorario();
		}

		setDadosCalculo("Data : "
				+ dateFormat.format(acertoSelecionado.getData())
				+ "\nTipo regime : "
				+ (String) lo[4]
				+ "\nMarcacoes : "
				+ ((mE1 == "") || (mE1.equals("00:00")) ? "" : getmE1())
				+ " "
				+ ((mS1 == "") || (mS1.equals("00:00")) ? "" : getmS1())
				+ " "
				+ ((mE2.isEmpty()) || (mE2.equals("00:00")) ? "" : getmE2())
				+ " "
				+ ((mS2.isEmpty()) || (mS2.equals("00:00")) ? "" : getmS2())
				+ " "
				+ ((mE3.isEmpty()) || (mE3.equals("00:00")) ? "" : getmE3())
				+ " "
				+ ((mS3.isEmpty()) || (mS3.equals("00:00")) ? "" : getmS3())
				+ " "
				+ ((mE4.isEmpty()) || (mE4.equals("00:00")) ? "" : getmE4())
				+ " "
				+ ((mS4.isEmpty()) || (mS4.equals("00:00")) ? "" : getmS4())

				+ "\nJornada : "
				+ (jornada.getE1() > 0 ? rt.InteitoToHora(jornada.getE1()) : "")
				+ " "
				+ (jornada.getS1() > 0 ? rt.InteitoToHora(jornada.getS1()) : "")
				+ " "
				+ (jornada.getE2() > 0 ? rt.InteitoToHora(jornada.getE2()) : "")
				+ " "
				+ (jornada.getS2() > 0 ? rt.InteitoToHora(jornada.getS2()) : "")
				+ " "
				+ (jornada.getE3() > 0 ? rt.InteitoToHora(jornada.getE3()) : "")
				+ " "
				+ (jornada.getS3() > 0 ? rt.InteitoToHora(jornada.getS3()) : "")
				+ " "
				+ (jornada.getE4() > 0 ? rt.InteitoToHora(jornada.getE4()) : "")
				+ " "
				+ (jornada.getS4() > 0 ? rt.InteitoToHora(jornada.getS4()) : "")
				+ "\nHorario Avulso " + hav + " " + "\nAfastamento : "
				+ (ma != null ? ma.getMotivoAfastamento() : "") + "\nFolga : "
				+ ((String) lo[31] != null ? "Sim" : "") + "\nFeriado : "
				+ ((String) lo[32] != null ? (String) lo[32] : "")
				+ "\n\nOcorrencias : " + ocorrencias

		);
	}

	public String createLabel(Integer i) {
		// System.out.println(i);
		// System.out.println(getLstobj().get(i).getOcorrencia());

		return "";

		// switch (getTipo()) {
		//
		// case "SUCCESS":
		// return "SUCCESS";
		//
		// case "ATRASO":
		// System.out.println("entrei no atraso");
		// // if (getLstobj().get(i).getDataAbono() == null) {
		// // return "ATRASO";
		// // } else {
		// // return "ABONADO";
		// // }
		//
		// case "Divergencia":
		// return "DIVERGENCIA";
		//
		// case "FALTA":
		// return "FALTA";
		//
		// default:
		// if (getTipo().contains("HE")) {
		// return "HE";
		// } else {
		// return "DEFAULT";
		// }
		//
		// }
	}
}
