package controle;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import filter.AcertoFilter;
import model.Acerto;
import model.Colaborador;
import model.Historico;
import model.HorarioColaborador;
import model.Jornada;
import model.MarcacaoDetalhe;
import model.MarcacaoDetalheTmp;
import model.MarcacaoOriginal;
import model.MotivoAbono;
import model.OcorrenciaApurada;
import repository.Acertos;
import repository.Colaboradores;
import repository.Jornadas;
import repository.MarcacaoDetalhes;
import repository.Marcacoes;
import repository.OcorrenciaApuradas;
import util.ProcessaMarcacoes;
import util.Rotinas;
import util.jsf.FacesUtil;

@Named
@Stateless
public class AcertoAbono implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipo;
	private String matricula;
	private String nome;
	private String empresa;
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
	
	
	@PersistenceContext(unitName = "safiraPU")
	private Session session;
	
	private List<Acerto> lstobj;

	private List<MarcacaoDetalheTmp> lstMarcacaoDetalheTmp;

	private MarcacaoOriginal marcacaoOriginal;

	private List<MotivoAbono> listaMotivoAbono;
	private List<Historico> listaHistorico;

	private List<Acerto> selectedAcertos;

	@Inject
	private Acertos acertos;

	@Inject
	private AcertoFilter acertoFilter;

	private MotivoAbono motivoAbonoSelecionado;
	private MotivoAbono motivoAbonoSelecionadoGrupo;

	private Acerto acertoSelecionado;

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

	@PostConstruct
	public void inicializar() {
		listaMotivoAbono = carregarListaMotivoAbonos();
		listaHistorico = carregarListaHistoricos();
		lstMarcacaoDetalheTmp = new ArrayList<MarcacaoDetalheTmp>();

	}

	public MotivoAbono getMotivoAbonoSelecionadoGrupo() {
		return motivoAbonoSelecionadoGrupo;
	}

	public void setMotivoAbonoSelecionadoGrupo(MotivoAbono motivoAbonoSelecionadoGrupo) {
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

	public List<Acerto> carregaAcerto(ActionEvent event) {
		acertoFilter.setNome(nome);
		acertoFilter.setMatricula(matricula);
		acertoFilter.setEmpresa(0L); // criar variavel
		acertoFilter.setFilial(0L);
		acertoFilter.setDepto(0L);
		acertoFilter.setDataini(dtini);
		acertoFilter.setDatafim(dtfim);
		acertoFilter.setTipo(tipo);

		lstobj = acertos.carregarAcertos(acertoFilter);

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

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
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

	public List<Acerto> getLstobj() {
		return lstobj;
	}

	public void setLstobj(List<Acerto> lstobj) {
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

	public Acerto getAcertoSelecionado() {
		return acertoSelecionado;
	}

	public void setAcertoSelecionado(Acerto acertoSelecionado) {
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
		HorarioColaborador hc = marcacoes.horarioColaboradorDia(acertoSelecionado.getData(),
				acertoSelecionado.getPis());

		String jpql = " select  h2.jornada_id from horario  as h "
				+ " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id "
				+ " left join Jornada AS j   ON h2.Jornada_id = j.Jornada_id "
				+ " where (1=1) and (h.horario_id = :horario) and " + "                (h2.seq =  ( SELECT "
				+ " 1 +  mod( (SELECT  :dia - cast( data_base as date) from Horario where horario_id= h2.horario_id  )  , CAST( vw_contaseqhorario.Expr1 AS BIGINT) ) "
				+ " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
				+ " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id"
				+ " WHERE        (vw_contaSeqHorario.horario_id = h2.horario_id ) ) )";

		BigInteger resultado = (BigInteger) session.createSQLQuery(jpql)
				.setParameter("dia", acertoSelecionado.getData()).setParameter("horario", hc.getHorario().getId())
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

		marcacaoOriginal = marcacaoDetalhes.MarcacoesDoColaboradorOriginalNoDiaPorPis(acertoSelecionado.getData(),
				acertoSelecionado.getPis());

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

	public Boolean getOrderna() {
		return orderna;
	}

	public void setOrderna(Boolean orderna) {
		this.orderna = orderna;
	}

	public List<MarcacaoDetalheTmp> getLstMarcacaoDetalheTmp() {
		return lstMarcacaoDetalheTmp;
	}

	public void setLstMarcacaoDetalheTmp(List<MarcacaoDetalheTmp> lstMarcacaoDetalheTmp) {
		this.lstMarcacaoDetalheTmp = lstMarcacaoDetalheTmp;
	}

	public List<Acerto> getSelectedAcertos() {
		return selectedAcertos;
	}

	public void setSelectedAcertos(List<Acerto> selectedAcertos) {
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
			} // for

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
		} // for

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
				marcacaoDetalhe.setColaborador(colaboradores.porPIS(acertoSelecionado.getPis()));
				marcacaoDetalhe.setData(acertoSelecionado.getData());
				marcacaoDetalhe.setHora(lstMarcacaoDetalheTmp.get(k).getHora());
				marcacaoDetalhe.setOriginal(false);
				marcacaoDetalhe.setTipo(lstMarcacaoDetalheTmp.get(k).getTipo());
				marcacaoDetalhe.setMotivo(lstMarcacaoDetalheTmp.get(k).getMotivo());
				marcacaoDetalhes.salvaMarcacaoDetalhe(marcacaoDetalhe);
			}

			if (lstMarcacaoDetalheTmp.get(k).getTipo() == "D") {

				marcacaoDetalhe = marcacaoDetalhes.achMarcacaoDetalheDoColaboradorNoDiaPis(acertoSelecionado.getData(),
						acertoSelecionado.getPis(), lstMarcacaoDetalheTmp.get(k).getHora());

				System.out.println(marcacaoDetalhe);

				// se for original true .. consultar para saber o que gravar
				if ((marcacaoDetalhe == null) || (marcacaoDetalhe.getOriginal() == false)) {
					marcacaoDetalhe.setOriginal(false);
				} else {
					marcacaoDetalhe.setOriginal(true);
				}

				marcacaoDetalhe.setTipo(lstMarcacaoDetalheTmp.get(k).getTipo());
				marcacaoDetalhe.setMotivo(lstMarcacaoDetalheTmp.get(k).getMotivo());
				marcacaoDetalhes.salvaMarcacaoDetalhe(marcacaoDetalhe);
			}
		} // for

		listacolab.add(colaboradores.porPIS(acertoSelecionado.getPis()));

		pr.processarMarcacao(acertoSelecionado.getData(), acertoSelecionado.getData(), false, listacolab, false, null);
	}// processaAcerto

	public void processarAbono() {
		// ocorrenciaapurada

		// acha ocorrenciaApurada data,colaborador,ocorrencia

		System.out.println("motivo abono" + motivoAbonoSelecionado.getMotivoAbono());

		System.out.println(acertoSelecionado.getData());
		System.out.println(acertoSelecionado.getHoras());
		System.out.println(acertoSelecionado.getColaborador_id());
		System.out.println(motivoAbonoSelecionado.getMotivoAbono());
		System.out.println(motivoAbonoSelecionado.getId());

		// nao esta vindo certo para falta = 7 mas ta vindo 0
		System.out.println(acertoSelecionado.getOcorrencia_id());
		System.out.println(acertoSelecionado.getOcorrencia());

		OcorrenciaApurada oa = new OcorrenciaApurada();
		System.out.println("ssass " + oa);

		oa = ocorrenciaApuradas.achaOcorrenciaApuradaDataColaboradorOcorrencia(acertoSelecionado);
		System.out.println("ggggg22");
		System.out.println("ao depois de pesquisar " + oa);

		System.out.println("depois do ocorrenciaapurada ");

		MotivoAbono ma = new MotivoAbono();
		ma.setId(motivoAbonoSelecionado.getId());
		ma.setMotivoAbono(motivoAbonoSelecionado.getMotivoAbono());
		oa.setMotivoAbono(ma);
		System.out.println("horas no abono de falta " + acertoSelecionado.getHoras());
		oa.setHorasAbonadas(acertoSelecionado.getHoras());

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

		System.out.println("motivo abono" + motivoAbonoSelecionado.getMotivoAbono());

		for (Acerto a : selectedAcertos) {

			OcorrenciaApurada oa = new OcorrenciaApurada();
			oa = ocorrenciaApuradas.achaOcorrenciaApuradaDataColaboradorOcorrencia(a);
			MotivoAbono ma = new MotivoAbono();
			ma.setId(motivoAbonoSelecionado.getId());
			ma.setMotivoAbono(motivoAbonoSelecionado.getMotivoAbono());
			oa.setMotivoAbono(ma);
			oa.setHorasAbonadas(acertoSelecionado.getHoras());
			oa.setMotivoAbono(ma);
			oa.setDataAbono(new Date());
			ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

		} // for

	}//

	public boolean isSelecionouAbono() {
		boolean r = false;

		try {
			System.out.println("selectedAcertos.size()" + selectedAcertos.size());
			r = selectedAcertos.size() > 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return r;

	}

	public String createLabel(Integer i) {
		// System.out.println(i);
		// System.out.println(getLstobj().get(i).getOcorrencia());

		if (i == null) {
			return null;
		}

		switch (getTipo()) {

		case "SUCCESS":
			return "SUCCESS";

		case "ATRASO":
			System.out.println("entrei no atraso");
			if (getLstobj().get(i).getDataAbono() == null) {
				return "ATRASO";
			} else {
				return "ABONADO";
			}

		case "Divergencia":
			return "DIVERGENCIA";

		case "FALTA":
			return "FALTA";

		default:
			if (getTipo().contains("HE")) {
				return "HE";
			} else {
				return "DEFAULT";
			}

		}
	}
}
