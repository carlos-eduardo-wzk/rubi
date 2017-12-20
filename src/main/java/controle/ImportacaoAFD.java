package controle;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.HorarioColaborador;
import model.Marcacao;
import model.MarcacaoDetalhe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import repository.Afastamentos;
import repository.MarcacaoDetalhes;
import repository.Marcacoes;
import util.jsf.JsfExceptionHandler;

@Named
@ViewScoped
public class ImportacaoAFD implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(JsfExceptionHandler.class);

	@Inject
	private Marcacoes marcacoes;

	@Inject
	private MarcacaoDetalhes marcacaoDetalhes;

	@Inject
	private Afastamentos afastamentos;

	// @Inject
	// private ProcessaMarcacoes processaMarcacoes;

	public ImportacaoAFD() {
	}

	// apaga as marcacoes importadas anteriormente
	public void apagarMarcacaoNoPeriodo(Date di, Date df) {
		marcacoes.apagarMarcacaoNoPeriodo(di, df);
	}

	// grava as marcacoes vindas do AFD - txt
	public void salvaListaMarcacao(List<Marcacao> lstmarcacao) {
		marcacoes.salvaListaMarcacao(lstmarcacao);

	}

	public Date subtractDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	// -------------------------------------------

	// inicia criando dia com falta
	public void diaParaImportacao(Date dia, boolean sobreporMarcacaoProcessada) {
		Boolean estaAfastado = false;
		Boolean achouJornadaOntem = false;
		Boolean achouJornadaHoje = false;
		Boolean achouFeriado = false;
		Integer fechamento;

		if (sobreporMarcacaoProcessada == true) {
			// apaga MarcacaoProcessada
			// apaga MarcacaoDetalhe
			marcacoes.apagarMarcacaoProcessadaNoDia(dia);
		}

		// pesquisa se ja existe no banco o dia //
		if (marcacoes.diaJaFoiImportado(dia) <= 0L) {
			// cria MarcacaoProcessada
			marcacoes.criarRegistroMarcacaoProcessada(dia);
		}

		// Analisa marcacao para saber o dia que sera gravado //
		for (Marcacao marHoje : marcacoes.marcacaoDoDia(dia)) {
			
			
			// acha horarioColaborador do dia
			HorarioColaborador hc = marcacoes.horarioColaboradorDia(
					marHoje.getData(), marHoje.getPis());

			// se nao achou horario ou jornada
			if (hc == null) {
				log.error("Erro de sistema, sem horario/jornada PIS = "
						+ marHoje.getPis());
				continue;
			}

			fechamento = marcacoes.achaJornadaDiaFechamento(subtractDay(dia),
					marHoje.getPis());

			Integer maiorMarcacaoOntem = marcacoes
					.maiorMarcacaoDoDiaColaborador(subtractDay(dia),
							marHoje.getPis());

			// jornada ontem (dia de trabalho ontem) ?
			achouJornadaOntem = marcacoes.achaJornadaDia(subtractDay(dia),
					marHoje.getPis());

			// jornada trabalha hoje ?
			achouJornadaHoje = marcacoes.achaJornadaDia(dia, marHoje.getPis());
						

			try {
				// afastamento / ferias
				estaAfastado = afastamentos.estaAfastadoPorMatriculaDia(
						marHoje.getPis(), dia);
			} catch (Exception e) {
				System.out.println(" " + marHoje.getMatricula());
			}

			System.out.println("******************************************");
			System.out.println(" estaAfastado " + estaAfastado);
			System.out.println(" achouJornadaOntem " + achouJornadaOntem);
            System.out.println(" maiorMarcacaoOntem " + maiorMarcacaoOntem);
			System.out.println(" achouJornadaHoje " + achouJornadaHoje);
			System.out.println(" achouFeriado "  + achouFeriado);
			System.out.println(" fechamento " + fechamento);
			System.out.println(" pis " + marHoje.getPis());
            System.out.println(" marcacdo dia " + marHoje.getHora());
			
			
			// verificar codicoes
			if ((maiorMarcacaoOntem != null) && (achouJornadaOntem == true)
					&& (achouJornadaHoje == true) && (achouFeriado == false)) {

				System.out
						.println("1 ontem true, jornada hoje, achou nao feriado");
				System.out.println();

				if (fechamento != null) {
					if (marHoje.getHora() < fechamento) {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(subtractDay(marHoje.getData()));
						md.setHora(marHoje.getHora() + 1440);
						md.setOriginal(true);
						;

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;
					} else {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(marHoje.getData());
						md.setHora(marHoje.getHora());
						md.setOriginal(true);
						;

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;

					}
				}
                
			}

			if ((maiorMarcacaoOntem != null) && (achouJornadaOntem == true)
					&& (achouJornadaHoje == false) && (achouFeriado == false)) {
				System.out
						.println("2 maior marcacao ontem <> null,achou jornada ontem true, jornada hoje false, achou nao feriado");
				if (fechamento != null) {
					if (marHoje.getHora() < fechamento) {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(subtractDay(marHoje.getData()));
						md.setHora(marHoje.getHora() + 1440);
						md.setOriginal(true);
						;

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;
					} else {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(marHoje.getData());
						md.setHora(marHoje.getHora());
						md.setOriginal(true);
						;

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;

					}
				}

			}

			if ((maiorMarcacaoOntem != null) && (achouJornadaOntem == false)
					&& (achouJornadaHoje == true) && (achouFeriado == false)) {
				System.out
						.println("3 ontem false, jornada hoje true, achou nao feriado");
				if (fechamento != null) {
					if (marHoje.getHora() < fechamento) {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(subtractDay(marHoje.getData()));
						md.setHora(marHoje.getHora() + 1440);
						md.setOriginal(true);
						;

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;
					} else {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(marHoje.getData());
						md.setHora(marHoje.getHora());
						md.setOriginal(true);
						;

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;

					}
				}

			}

			if ((maiorMarcacaoOntem != null) && (achouJornadaOntem == false)
					&& (achouJornadaHoje == false) && (achouFeriado == false)) {
				System.out
						.println("4 ontem false, jornada hoje false,  feriado false");
				System.out.println("fechamento  " + fechamento);
				System.out.println(marHoje.getHora());
				
				
						// gravar no dia 
						System.out.println("gravo no dia "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(marHoje.getData());
						md.setHora(marHoje.getHora());
						md.setOriginal(true);
					
						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;
		
			}

			if ((maiorMarcacaoOntem != null) && (achouJornadaOntem == true)
					&& (achouJornadaHoje == true) && (achouFeriado == true)) {
				System.out
						.println("5 ontem false, jornada hoje false,  feriado false");
			}

			if ((maiorMarcacaoOntem != null) && (achouJornadaOntem == true)
					&& (achouJornadaHoje == false) && (achouFeriado == true)) {
				System.out
						.println("6 ontem false, jornada hoje false,  feriado false");
			}

			if ((maiorMarcacaoOntem != null) && (achouJornadaOntem == false)
					&& (achouJornadaHoje == true) && (achouFeriado == true)) {
				System.out
						.println("7 ontem false, jornada hoje false,  feriado false");
			}

			if ((maiorMarcacaoOntem != null) && (achouJornadaOntem == false)
					&& (achouJornadaHoje == false) && (achouFeriado == true)) {
				System.out
						.println("8 ontem false, jornada hoje false,  feriado false");

			}

			// ----

			if ((maiorMarcacaoOntem == null) && (achouJornadaOntem == true)
					&& (achouJornadaHoje == true) && (achouFeriado == false)) {
				System.out
						.println("9 achouJornadaOntem true, achouJornadaHoje hoje, achou nao feriado ,fechamento dia anterior "
								+ fechamento);
				System.out.println("marcacao hoje " + marHoje.getHora());

				if (fechamento != null) {
					if (marHoje.getHora() < fechamento) {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(subtractDay(marHoje.getData()));
						md.setHora(marHoje.getHora() + 1440);
						md.setOriginal(true);

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;
					} else {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(marHoje.getData());
						md.setHora(marHoje.getHora());
						md.setOriginal(true);

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;

					}
				}
			}

			if ((maiorMarcacaoOntem == null) && (achouJornadaOntem == true)
					&& (achouJornadaHoje == false) && (achouFeriado == false)) {
				System.out
						.println("10 ontem true, jornada hoje false, achou nao feriado");
				if (fechamento != null) {
					if (marHoje.getHora() < fechamento) {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(subtractDay(marHoje.getData()));
						md.setHora(marHoje.getHora() + 1440);
						md.setOriginal(true);
						;

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;
					} else {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(marHoje.getData());
						md.setHora(marHoje.getHora());
						md.setOriginal(true);

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;
					}
				}

			}

			if ((maiorMarcacaoOntem == null) && (achouJornadaOntem == false)
					&& (achouJornadaHoje == true) && (achouFeriado == false)) {
				System.out
						.println("11 ontem false, jornada hoje true, achou nao feriado");
				// gravar no dia anterior
				System.out.println("gravo no dia atual " + marHoje.getHora());
				MarcacaoDetalhe md = new MarcacaoDetalhe();

				md.setColaborador(hc.getColaborador());
				md.setData(marHoje.getData());
				md.setHora(marHoje.getHora());
				md.setOriginal(true);
				;

				marcacaoDetalhes.salvaMarcacaoDetalhe(md);
				continue;

			}

			if ((maiorMarcacaoOntem == null) && (achouJornadaOntem == false)
					&& (achouJornadaHoje == false) && (achouFeriado == false)) {
				System.out
						.println("12 ontem false, jornada hoje false,  feriado false");
				// gravar no dia anterior
				System.out.println("gravo no dia atual " + marHoje.getHora());
				MarcacaoDetalhe md = new MarcacaoDetalhe();

				md.setColaborador(hc.getColaborador());
				md.setData(marHoje.getData());
				md.setHora(marHoje.getHora());
				md.setOriginal(true);
				;
				marcacaoDetalhes.salvaMarcacaoDetalhe(md);
				continue;

			}

			if ((maiorMarcacaoOntem == null) && (achouJornadaOntem == true)
					&& (achouJornadaHoje == true) && (achouFeriado == true)) {
				System.out
						.println("13 ontem false, jornada hoje false,  feriado false");
				if (fechamento != null) {
					if (marHoje.getHora() < fechamento) {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(subtractDay(marHoje.getData()));
						md.setHora(marHoje.getHora() + 1440);
						md.setOriginal(true);

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;
					} else {
						// gravar no dia anterior
						System.out.println("gravo no dia anterior "
								+ marHoje.getHora());
						MarcacaoDetalhe md = new MarcacaoDetalhe();

						md.setColaborador(hc.getColaborador());
						md.setData(marHoje.getData());
						md.setHora(marHoje.getHora());
						md.setOriginal(true);

						marcacaoDetalhes.salvaMarcacaoDetalhe(md);
						continue;
					}
				}

			}

			if ((maiorMarcacaoOntem == null) && (achouJornadaOntem == true)
					&& (achouJornadaHoje == false) && (achouFeriado == true)) {
				System.out
						.println("14 ontem false, jornada hoje false,  feriado false");
			}

			if ((maiorMarcacaoOntem == null) && (achouJornadaOntem == false)
					&& (achouJornadaHoje == true) && (achouFeriado == true)) {
				System.out
						.println("15 ontem false, jornada hoje false,  feriado false");
			}

			if ((maiorMarcacaoOntem == null) && (achouJornadaOntem == false)
					&& (achouJornadaHoje == false) && (achouFeriado == true)) {
				System.out
						.println("16 ontem false, jornada hoje false,  feriado false");
			}

		}// for

	} // diaParaImportacao

}
