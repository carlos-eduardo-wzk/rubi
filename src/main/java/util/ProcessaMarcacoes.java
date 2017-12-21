package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.result.ResultSetOutput;

import model.AcertoAbono;
import model.Colaborador;
import model.NormaColaborador;
import model.OcorrenciaApurada;
import model.TipoDia;
import model.TipoOcorrencia;
import repository.Colaboradores;
import repository.MaracacaoProcessadas;
import repository.OcorrenciaApuradas;
import repository.Ocorrencias;
import util.jsf.JsfExceptionHandler;


public class ProcessaMarcacoes implements Serializable {

	private static final long serialVersionUID = 1L;

	// @Inject
	// private EntityManager manager;
	
	@PersistenceContext(unitName = "safiraPU")
	private Session session;

	@Inject
	private Colaboradores colaboradores;

	@Inject
	private Calculo calculo;

	@Inject
	private MaracacaoProcessadas maracacaoProcessadas;

	@Inject
	private OcorrenciaApuradas ocorrenciaApuradas;

	@Inject
	private Ocorrencias ocorrencias;

	public ProcessaMarcacoes() {
//		ocorrencias = new  Ocorrencias();
//		ocorrenciaApuradas = new OcorrenciaApuradas();
//		maracacaoProcessadas = new MaracacaoProcessadas();
//		calculo = new Calculo();
//		colaboradores = new Colaboradores();
//		session ; 		
	
		System.out.println("contrutor processa marcacoes");
	}
	
	private static Log log = LogFactory.getLog(JsfExceptionHandler.class);

	// calcula no periodo marcacoes
	public void processarMarcacao(Date di, Date df, Boolean sobrepor,
			List<Colaborador> listacolab, Boolean repropcessar,
			List<AcertoAbono> listaAcerto) {

		System.out.println("Entrei em processarMarcacao ");
		
		ArrayList<Integer> marc = new ArrayList<Integer>();
		Integer numDias = 0;

		if (sobrepor == true) {
			ocorrenciaApuradas.apagaOcorrenciaDia(di, df);
		}

		// paga as marcacoes somente dos colaborado a fim de recalculo
		if (repropcessar == true) {
			for (Colaborador cola : listacolab) {
				ocorrenciaApuradas.apagaOcorrenciaDiaColaborador(di, df, cola);
			}
		}

		if (listacolab == null) {
			listacolab = colaboradores.findAll();
		}

		if (repropcessar == false) {
			for (Colaborador c : listacolab) {
				System.out.println("=====================");
				System.out.println(c.getId());
				System.out.println("=====================");

				Calendar cal = Calendar.getInstance();
				cal.setTime(di);
				// fazer for di...df
				numDias = daysBetween(di, df);

				for (int i = 0; i <= numDias; i++) {
					processaMarcacaoPeriodo(cal, c, marc);
					// acrescentar 1 dia
					cal.add(Calendar.DATE, 1);
				}// for dias
			} // for colaborador
		} else // se reprocessa
		{
			// se recalculo
			if (listaAcerto != null) {
				for (AcertoAbono ab : listaAcerto) {
					Colaborador cola = colaboradores.porId(ab
							.getColaborador_id());
					if (cola != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(ab.getData());

						processaMarcacaoPeriodo(cal, cola, marc);
					}

				}
			} else {
				// se calculo individual
				for (Colaborador c : listacolab) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(di);
					processaMarcacaoPeriodo(cal, c, marc);
				}

			}

		} // reprocessa

	} // processarMarcacao

	private void processaMarcacaoPeriodo(Calendar cal, Colaborador c,
			ArrayList<Integer> marc) {

		List<Object[]> lo;

		// achar a norma do dia
		NormaColaborador nc = normaColaboradorDia(cal.getTime(), c.getPis());

		if (nc == null) {
			log.error("Erro de sistema na importacao marcacoes, sem norma  "
					+ c.getPis() + "  dia  " + cal.getTime());
			return;
		}

		System.out.println("********** " + c.getPis() + " ***** " + c.getId());

		OcorrenciaApurada oa = new OcorrenciaApurada();

		lo = carregaHorarioAfastamentoFolga(cal.getTime(), c.getId());

		if (lo.size() > 0) {
			for (Object[] o : lo) {
				Integer[] resul = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

				System.out.println("FOLGA 31 " + o[31]);
				System.out.println("Feriado 32 " + o[32]);

				// pega as maracoes se tiver
				marc = carregarMarcacoesDia(cal.getTime(), c.getId());

				System.out.println("Tamanho size " + marc.size());

				// se achou marcacoes
				if (marc.size() != 0) {

					if ((marc.size() == 1) || (marc.size() == 3)
							|| (marc.size() == 5) || (marc.size() == 7)) { // nao
																			// tem
																			// marcacao
						log.error("Erro de sistema numero de  marcacoes impar  :"
								+ marc.size() + " pis" + c.getPis());

						maracacaoProcessadas.salvaTipoDia(c, cal.getTime(),
								TipoDia.MARCACAOINVALIDA);
						continue;

					}

					System.out.println(" CALCULO .......");
					System.out.println("Data " + cal.getTime());
					System.out.println("DAY_OF_WEEK = "
							+ cal.get(Calendar.DAY_OF_WEEK));

					// se tiver afastamento, folga ou feriado deixar
					// como he
					// deixao horario vazio "jornada" para que seja
					// calculado como he

					System.out.println("Feriado " + o[32]);

					// zerar horario para que seja calculado como he
					if (o[32] != null) { // feriado
						o[5] = 0; // h1
						o[6] = 0; // h2
						o[7] = 0; // h3
						o[8] = 0; // h4
						o[28] = 0; // pre
						o[29] = 0; // pre

					}

					// ---------
					// processar
					// ---------
					calculo.calculoMarcacaoDia(o, marc, c, cal.getTime(), resul);

					System.out.println("hnto       calculo " + resul[0]);
					System.out.println("ATRASO 1   calculo " + resul[1]);
					System.out.println("ATRASO 2   calculo " + resul[2]);
					System.out.println("saidaAnt 1 calculo " + resul[3]);
					System.out.println("saidaAnt 2 calculo " + resul[4]);
					System.out.println("HN         calculo " + resul[5]);
					System.out.println("HNN        calculo " + resul[6]);
					System.out.println("HED        calculo " + resul[7]);
					System.out.println("HEN        calculo " + resul[8]);
					System.out.println("AN         calculo " + resul[9]);
					System.out.println("Resposta calculo " + resul[10]);

					maracacaoProcessadas.salvaTipoDia(c, cal.getTime(),
							TipoDia.NORMAL);

					// gravar calculos

					System.out
							.println("começa a gravar ocorrencia apurada....");

					if (resul[5] > 0) { // HN

						System.out.println("HN "
								+ resul[5]
								+ " ocorrencia "
								+ ocorrencias.porId(nc.getNorma()
										.getOcorrenciaHorasNormais()));

						oa.setColaborador(c);
						oa.setData(cal.getTime());
						oa.setHoras(resul[5]);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHorasNormais()));
						oa.setTipoDia(TipoDia.NORMAL);
						oa.setTipoOcorrencia(TipoOcorrencia.HORA_NORMAL);
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

						System.out.println("OA --->>" + oa);
						oa = new OcorrenciaApurada();
						System.out.println(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHorasNormais()));

					}// HN - resul[5]

					if (resul[6] > 0) {// HNN

						System.out.println("HNN "
								+ resul[6]
								+ " ocorrencia"
								+ ocorrencias.porId(nc.getNorma()
										.getOcorrenciaHorasNormaisNoturna()));

						oa.setColaborador(c);
						oa.setData(cal.getTime());
						oa.setHoras(resul[6]);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHorasNormaisNoturna()));
						oa.setTipoDia(TipoDia.NORMAL);
						oa.setTipoOcorrencia(TipoOcorrencia.HE);
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						System.out.println(oa);
						oa = new OcorrenciaApurada();

					}// HNN - resul[6]

					if (resul[9] > 0) { // AN
						System.out.println("AN " + resul[9]);
						oa.setColaborador(c);
						oa.setData(cal.getTime());
						oa.setHoras(resul[9]);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaAdicionalNoturno()));
						oa.setTipoDia(TipoDia.NORMAL);
						oa.setTipoOcorrencia(TipoOcorrencia.AN);
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						oa = new OcorrenciaApurada();
					}// AN - resul[9]

					if (isDiaBancoHoras(cal.get(Calendar.DAY_OF_WEEK), nc) == false) {

						if ((Integer) resul[0] > 0) {

							oa.setColaborador(c);
							oa.setData(cal.getTime());
							oa.setHoras(resul[0]);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaHorasNormaisTolerancia()));
							oa.setTipoDia(TipoDia.NORMAL);
							oa.setTipoOcorrencia(TipoOcorrencia.HORA_NORMAL);

							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
							System.out.println(oa);
							oa = new OcorrenciaApurada();

							System.out
									.println(" NH "
											+ nc.getNorma()
													.getOcorrenciaHorasNormaisTolerancia());

						} // result[0] - hnto

						if (resul[1] > 0) { // ATRASO 1
							oa.setColaborador(c);
							oa.setData(cal.getTime());
							oa.setHoras(resul[1]);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaAtraso()));
							oa.setTipoDia(TipoDia.NORMAL);
							oa.setTipoOcorrencia(TipoOcorrencia.ATRASO);
							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
							System.out.println(oa);
							oa = new OcorrenciaApurada();

						}// ATRASO 1 - esul[1]

						if (resul[2] > 0) {// ATRASO 2
							oa.setColaborador(c);
							oa.setData(cal.getTime());
							oa.setHoras(resul[2]);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaAtraso2()));
							oa.setTipoDia(TipoDia.NORMAL);
							oa.setTipoOcorrencia(TipoOcorrencia.ATRASO);
							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
							System.out.println(oa);
							oa = new OcorrenciaApurada();

						}// ATRASO 2 - resul[2]

						if (resul[3] > 0) { // saidaAnt 1
							oa.setColaborador(c);
							oa.setData(cal.getTime());
							oa.setHoras(resul[3]);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaSaidaAntecipada()));
							oa.setTipoDia(TipoDia.NORMAL);
							oa.setTipoOcorrencia(TipoOcorrencia.SAIDA_ANTECIPADA);
							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

							System.out.println("OA --->>" + oa);
							oa = new OcorrenciaApurada();
							System.out.println("ocorrencia------->>> "
									+ ocorrencias.porId(nc.getNorma()
											.getOcorrenciaSaidaAntecipada()));

						}// saidaAnt 1 - resul[3]

						if (resul[4] > 0) { // saidaAnt 2
							oa.setColaborador(c);
							oa.setData(cal.getTime());
							oa.setHoras(resul[4]);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaSaidaAntecipada2()));
							oa.setTipoDia(TipoDia.NORMAL);
							oa.setTipoOcorrencia(TipoOcorrencia.SAIDA_ANTECIPADA);
							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

							System.out.println("OA --->>" + oa);
							oa = new OcorrenciaApurada();
							System.out.println(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaSaidaAntecipada2()));

						}// saidaAnt 2 - resul[4]

						if (resul[7] > 0) { // HED

							System.out.println("HED "
									+ resul[7]
									+ " ocorrencia "
									+ ocorrencias.porId(nc.getNorma()
											.getOcorrenciaHeSegFaixaDiurno1()));

							oa.setColaborador(c);
							oa.setData(cal.getTime());
							oa.setHoras(resul[7]);
							oa.setTipoDia(TipoDia.NORMAL);
							oa.setTipoOcorrencia(TipoOcorrencia.HE);

							if (cal.get(Calendar.DAY_OF_WEEK) == 1) { // domingo
								heOcorrencia(oa, nc, (Integer) resul[7], 1);

							}
							if (cal.get(Calendar.DAY_OF_WEEK) == 2) { // segunda

								heOcorrencia(oa, nc, (Integer) resul[7], 2);

							}
							if (cal.get(Calendar.DAY_OF_WEEK) == 3) { // terca
								heOcorrencia(oa, nc, (Integer) resul[7], 3);

							}
							if (cal.get(Calendar.DAY_OF_WEEK) == 4) { // qua
								heOcorrencia(oa, nc, (Integer) resul[7], 4);

							}
							if (cal.get(Calendar.DAY_OF_WEEK) == 5) { // qui
								heOcorrencia(oa, nc, (Integer) resul[7], 5);

							}
							if (cal.get(Calendar.DAY_OF_WEEK) == 6) { // sex
								heOcorrencia(oa, nc, (Integer) resul[7], 6);

							}
							if (cal.get(Calendar.DAY_OF_WEEK) == 7) { // sab
								heOcorrencia(oa, nc, (Integer) resul[7], 7);

							}

							oa = new OcorrenciaApurada();
							maracacaoProcessadas.salvaTipoDia(c, cal.getTime(),
									TipoDia.NORMAL);

						}// HED - resul[7]

						if (resul[8] > 0) { // HEN

							System.out.println("HEN "
									+ resul[8]
									+ " OCORRENCIA "
									+ ocorrencias.porId(nc.getNorma()
											.getOcorrenciaHeSegFaixaNot1()));

							oa.setColaborador(c);
							oa.setData(cal.getTime());
							oa.setHoras(resul[8]);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaHeSegFaixaNot1()));
							oa.setTipoDia(TipoDia.NORMAL);
							oa.setTipoOcorrencia(TipoOcorrencia.HE);
							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
							oa = new OcorrenciaApurada();

						}// HEN - resul[8]

					} else // banco de horas
					{
						System.out.println("Banco de horas -----");
						Integer debito = 0;
						Integer credito = 0;

						if (resul[1] > 0) { // ATRASO 1
							debito = resul[1];
						}
						if (resul[2] > 0) {// ATRASO 2
							debito += resul[2];
						}
						if (resul[3] > 0) { // saidaAnt 1
							debito += resul[3];
						}
						if (resul[4] > 0) { // saidaAnt 2
							debito += resul[4];
						}

						if (debito > 0) {
							oa.setColaborador(c);
							oa.setData(cal.getTime());
							oa.setHoras(debito);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaDebito()));
							oa.setTipoDia(TipoDia.NORMAL);
							oa.setTipoOcorrencia(TipoOcorrencia.DEBITO);
							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
							System.out.println(oa);
							oa = new OcorrenciaApurada();
						}// debito

						if (resul[7] > 0) { // HED
							credito = resul[7];
						}
						if (resul[8] > 0) { // HEN
							credito += resul[8];
						}

						if (credito > 0) {

							ScriptEngineManager manager = new ScriptEngineManager();
							ScriptEngine engine = manager.getEngineByName("js");
							engine.put("he", (Integer) credito);
							engine.put("cred", 0.0);
							
							// engine.put("feriado", true);
							try {

								if (cal.get(Calendar.DAY_OF_WEEK) == 1) { // domingo
									engine.eval(nc.getNorma().getScriptDOM());
								} else if (cal.get(Calendar.DAY_OF_WEEK) == 2) { // seg
									engine.eval(nc.getNorma().getScriptSEG());
								} else if (cal.get(Calendar.DAY_OF_WEEK) == 3) { // ter
									engine.eval(nc.getNorma().getScriptTER());
								} else if (cal.get(Calendar.DAY_OF_WEEK) == 4) { // qua
									engine.eval(nc.getNorma().getScriptQUA());
								} else if (cal.get(Calendar.DAY_OF_WEEK) == 5) { // qui
									engine.eval(nc.getNorma().getScriptQUI());
								} else if (cal.get(Calendar.DAY_OF_WEEK) == 6) { // sex
									engine.eval(nc.getNorma().getScriptSEX());
								} else if (cal.get(Calendar.DAY_OF_WEEK) == 7) { // sab
									engine.eval(nc.getNorma().getScriptSAB());
								}

								Double reHe = (Double) engine.get("output");
								Double recredito = (Double) engine.get("cred");

								if (reHe > 0) {

									oa.setColaborador(c);
									oa.setData(cal.getTime());
									oa.setHoras(resul[7]);
									oa.setTipoDia(TipoDia.NORMAL);
									oa.setTipoOcorrencia(TipoOcorrencia.HE);

									if (cal.get(Calendar.DAY_OF_WEEK) == 1) { // domingo
										heOcorrencia(oa, nc, reHe.intValue(), 1);
									} else if (cal.get(Calendar.DAY_OF_WEEK) == 2) { // seg
										heOcorrencia(oa, nc, reHe.intValue(), 2);
									} else if (cal.get(Calendar.DAY_OF_WEEK) == 3) { // ter
										heOcorrencia(oa, nc, reHe.intValue(), 3);
									} else if (cal.get(Calendar.DAY_OF_WEEK) == 4) { // qua
										heOcorrencia(oa, nc, reHe.intValue(), 4);
									} else if (cal.get(Calendar.DAY_OF_WEEK) == 5) { // qui
										heOcorrencia(oa, nc, reHe.intValue(), 5);
									} else if (cal.get(Calendar.DAY_OF_WEEK) == 6) { // sex
										heOcorrencia(oa, nc, reHe.intValue(), 6);
									} else if (cal.get(Calendar.DAY_OF_WEEK) == 7) { // sab
										heOcorrencia(oa, nc, reHe.intValue(), 7);
									}

								}// rehE

								// credito
								oa.setColaborador(c);
								oa.setData(cal.getTime());
								oa.setHoras(recredito.intValue());
								oa.setOcorrencia(ocorrencias.porId(nc
										.getNorma().getOcorrenciaCredito()));
								oa.setTipoDia(TipoDia.NORMAL);
								oa.setTipoOcorrencia(TipoOcorrencia.CREDITO);
								ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
								System.out.println(oa);
								oa = new OcorrenciaApurada();

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}// banco de horas

				} else // sem marcacao
				{

					System.out.println();
					System.out.println("  horario 5 " + (Integer) o[5]);
					System.out.println("  horario 6 " + (Integer) o[6]);
					System.out.println("  horario 7 " + (Integer) o[7]);
					System.out.println("  horario 8 " + (Integer) o[8]);

					System.out.println("Horario id " + o[0]);
					System.out.println("Horario " + o[1]);
					System.out.println("Data base" + o[2]);
					System.out.println("jornada " + o[3]);
					System.out.println("tipo regime " + o[4]);
					System.out.println("Data " + o[9]);
					System.out.println("Data " + o[10]);
					System.out.println("Data " + o[11]);
					System.out.println("Data " + o[12]);
					System.out.println("Data " + o[13]);
					System.out.println("Data " + o[14]);

					// verificar se � dia de DESCANSO
					if (((Integer) o[5] == 0) && ((Integer) o[6] == 0)
							&& ((Integer) o[7] == 0) && ((Integer) o[8] == 0)) {
						// gravar em marcacao processada dia como
						// descanso usar tipodia
						System.out.println("descanso .....");

						maracacaoProcessadas.salvaTipoDia(c, cal.getTime(),
								TipoDia.DESCANSO);

					}

					// verificar se � dia de FALTA nao pode ter
					// afastamento, folga
					if (((Integer) o[5] != 0) && ((Integer) o[6] != 0)
							&& (o[30] == null) && o[31] == null) {

						System.out.println("Falta " + c.getNome() + " matric "
								+ c.getMatricula());

						// gravar em marcacao processada dia como
						// descanso usar tipodia
						maracacaoProcessadas.salvaTipoDia(c, cal.getTime(),
								TipoDia.NORMAL);

						// grava resultados
						oa.setColaborador(c);
						oa.setData(cal.getTime());
						oa.setHoras((Integer) o[33]);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaFaltas()));
						oa.setTipoDia(TipoDia.FALTA);
						oa.setTipoOcorrencia(TipoOcorrencia.FALTA);

						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						oa = new OcorrenciaApurada();
					}

					// System.out.println(nc.getNorma().getOcorrenciaAfastamento());

					// verificar se esta afastado

					System.out.println(c.getPis() + " " + " afastamento "
							+ o[30] + "  " + o[30]);

					System.out.println(" afastamento " + o[33]);

					if (o[30] != null) // afastamento
					{
						// norma para pegar ocorrencia
						// acha ocorrencia

						// achar dsr

						maracacaoProcessadas.salvaTipoDia(c, cal.getTime(),
								TipoDia.AFASTADO);
						oa.setColaborador(c);
						oa.setTipoOcorrencia(TipoOcorrencia.AFASTAMENTO);
						oa.setData(cal.getTime());
						oa.setHoras((Integer) o[33]);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaAfastamento()));
						oa.setTipoDia(TipoDia.AFASTADO);

						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						oa = new OcorrenciaApurada();
					}

					System.out.println(c.getPis() + " " + " folga " + o[31]
							+ "  " + o[31]);

					System.out.println(" horas folha " + o[33]);

					// verificar se esta de FOLGA
					if (o[31] != null) {

						System.out.println("dentro folga para salvar " + o[31]);
						maracacaoProcessadas.salvaTipoDia(c, cal.getTime(),
								TipoDia.FOLGA);
						oa.setTipoOcorrencia(TipoOcorrencia.FOLGA);
						oa.setColaborador(c);
						oa.setData(cal.getTime());
						oa.setHoras((Integer) o[33]);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaFolga()));
						oa.setTipoDia(TipoDia.FOLGA);
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						oa = new OcorrenciaApurada();
					}

					// // verifica se divergencia de marcacooes 1
					// if (((Integer) o[5] != 0) && ((Integer) o[6]
					// == 0))
					// {
					// maracacaoProcessadas.salvaTipoDia(c,
					// cal.getTime(), TipoDia.MARCACAOINVALIDO);
					// }
					//
					// // verifica se divergencia de marcacooes 3
					// if ( ((Integer) o[5] != 0) && ((Integer) o[6]
					// != 0) && ((Integer) o[7] != 0) && ((Integer)
					// o[8] == 0) )
					// {
					// maracacaoProcessadas.salvaTipoDia(c,
					// cal.getTime(), TipoDia.MARCACAOINVALIDO);
					// System.out.println(TipoDia.MARCACAOINVALIDO);
					// }

					// verificar se esta demitido
					System.out.println("Data demissao " + c.getDataDemissao());
					if (c.getDataDemissao() != null) {
						maracacaoProcessadas.salvaTipoDia(c, cal.getTime(),
								TipoDia.DEMITIDO);

					}

				}// sem marcacao
			}// for horario;afastamento Object[] o
		}// se not null - lo.size()
		else {
			log.error("Erro de sistema na importacao marcacoes, sem horario  "
					+ c.getPis());
			System.out.println(" entrei no null");
			// calculo.calculoMarcacaoDia(null, null, c,
			// cal.getTime());
		} // se nao achou lo

	}// ProcessaMarcacaoPeriodo

	@SuppressWarnings("unchecked")
	public List<Object[]> carregaHorarioAfastamentoFolga(Date dia,
			Long colaborador) {

		// mysql
		@SuppressWarnings("unused")
		String jpql = " select h2.horario_id, h.horario, data_base, h2.jornada_id, tipoRegime, e1, s1, e2, s2, seq, fechamento, hc.colaborador_id , "
				+ " tole1ant ,tole1dep, tole2ant ,tole2dep, tole3ant ,tole3dep, tole4ant ,tole4dep, tole5ant ,tole5dep, tole6ant ,tole6dep, tole7ant ,tole7dep, tole8ant ,tole8dep,"
				+ " preassinaladoe2,preassinalados1 ,"
				+ " (select motivoAfastamento_id from afastamento  where :dia between data_afastamento_inicial and data_afastamento_final and colaborador_id= hc.colaborador_id ) as Afastamento,"
				+ " (select 'Folga' from folga  where :dia between data_afastamento_inicial and data_afastamento_final and colaborador_id= hc.colaborador_id) as folga , "
				+ " (select 'Feriado' from feriado where data_feriado = :dia) as feriado, dsr "
				+ " from horario  as h "
				+ " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id"
				+ " left join jornada AS j   ON h2.Jornada_id = j.Jornada_id    "
				+ " inner join  "
				+ " (select * from horario_colaborador AS hc"
				+ " where  cast(data_inicio as date) <= :dia and colaborador_id = :colaborador"
				+ " order by data_inicio desc limit 1) hc"
				+ " where (1=1) and (h.horario_id = hc.horario_id) and"
				+ " (h2.seq =  ( SELECT "
				+ " 1 + (SELECT DATEDIFF(:dia ,data_base) from horario where horario_id=hc.horario_id) % vw_contaSeqHorario.Expr1 AS sequencia"
				+ " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
				+ " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id"
				+ " WHERE        (vw_contaSeqHorario.horario_id = hc.horario_id) ) )  ";

		// // postgres
		// jpql =
		// " Select h2.horario_id, h.horario, data_base, h2.jornada_id, tipoRegime, e1, s1, e2, s2, seq, fechamento, hc.colaborador_id , "
		// +
		// " tole1ant ,tole1dep, tole2ant ,tole2dep, tole3ant ,tole3dep, tole4ant ,tole4dep, tole5ant ,tole5dep, tole6ant ,tole6dep, tole7ant ,tole7dep, tole8ant ,tole8dep,"
		// + " preassinaladoe2,preassinalados1,"
		// +
		// " (select motivoAfastamento_id from afastamento  where :dia between data_afastamento_inicial and data_afastamento_final and colaborador_id= hc.colaborador_id ) as Afastamento, "
		// +
		// " (select cast('Folga' as text) from folga  where :dia  between data_afastamento_inicial and data_afastamento_final and colaborador_id= hc.colaborador_id) as folga ,"
		// +
		// "  (select cast( 'Feriado' as text) from feriado where data_feriado = :dia) as feriado, dsr"
		// + "  from horario  as h 	"
		// +
		// " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id 			"
		// + " left join jornada AS j ON h2.Jornada_id = j.Jornada_id  			"
		// +
		// " inner join  ( select * from horario_colaborador as hc where  cast(data_inicio as date) <= :dia and colaborador_id = :colaborador "
		// +
		// " order by data_inicio desc limit 1) as hc on (1=1 and (h.horario_id = hc.horario_id)) "
		// + " and "
		// + " (h2.seq =  ( SELECT "
		// +
		// " 1 +   mod(   (SELECT  cast (  date_part('day',age(:dia, data_base)) as int) from horario) , (select cast( vw_contaSeqHorario.Expr1 as int) "
		// + "  from vw_contaSeqHorario))    AS sequencia "
		// + "  FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
		// +
		// " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id)) ";

		// OK sem empresa id e sem feriado empresa
		// jpql =
		// " Select h.horario_id as hid , h.horario, data_base, h2.jornada_id, tipoRegime, e1, s1, e2, s2, seq, fechamento, hc.colaborador_id , "
		// +
		// " tole1ant ,tole1dep, tole2ant ,tole2dep, tole3ant ,tole3dep, tole4ant ,tole4dep, tole5ant ,tole5dep, tole6ant ,tole6dep, tole7ant ,tole7dep, tole8ant ,tole8dep, "
		// + " preassinaladoe2,preassinalados1, "
		// +
		// " (select motivoAfastamento_id from afastamento  where :dia between data_afastamento_inicial and data_afastamento_final and colaborador_id= hc.colaborador_id ) as Afastamento, "
		// +
		// " (select cast('Folga' as text) from folga  where :dia  between data_afastamento_inicial and data_afastamento_final and colaborador_id= hc.colaborador_id) as folga , "
		// +
		// " (select cast( 'Feriado' as text) from feriado where data_feriado = :dia ) as feriado, dsr,"
		// + " hc.horario_id	"
		// + " from horario  as h "
		// +
		// " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id "
		// + " left join jornada AS j ON h2.Jornada_id = j.Jornada_id  "
		// +
		// " inner join  ( select * from horario_colaborador as hc where  cast(data_inicio as date) <= :dia and colaborador_id =:colaborador "
		// +
		// " order by data_inicio desc limit 1) as hc on (1=1 and (h.horario_id = hc.horario_id)) "
		// + " and "
		// + " (h2.seq =  ( SELECT "
		// +
		// " 1 +  mod( (SELECT  :dia - cast( data_base as date) from Horario as ho where horario_id= hc.horario_id  )  , CAST( vw_contaseqhorario.Expr1 AS BIGINT) ) "
		// + " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
		// +
		// " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id "
		// +
		// " WHERE        (vw_contaSeqHorario.horario_id = hc.horario_id ) ) ) ";

		jpql = " Select h.horario_id as hid , h.horario, data_base, h2.jornada_id, tipoRegime, e1, s1, e2, s2, seq, fechamento, hc.colaborador_id , "
				+ " tole1ant ,tole1dep, tole2ant ,tole2dep, tole3ant ,tole3dep, tole4ant ,tole4dep, tole5ant ,tole5dep, tole6ant ,tole6dep, tole7ant ,tole7dep, tole8ant ,tole8dep, "
				+ " preassinaladoe2,preassinalados1, "
				+ " (select motivoAfastamento_id from afastamento  where :dia between data_afastamento_inicial and data_afastamento_final and colaborador_id= hc.colaborador_id ) as Afastamento, "
				+ " (select cast('Folga' as text) from folga  where :dia  between data_afastamento_inicial and data_afastamento_final and colaborador_id= hc.colaborador_id) as folga , "
				+ " (select feriado from feriado where data_feriado = :dia and empresa_id=cola.empresa_id ) as feriado, dsr,"
				+ " hc.horario_id, empresa_id, e3, s3, e4, s4	"
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

		// jpql =
		// "select public.fc_horarioafastamentofolga(:dia,:colaborador))";

		System.out.println("dia " + dia);
		System.out.println("cola " + colaborador);

		try {

			// chama function no postgres

			ProcedureCall procedureCall = session
					.createStoredProcedureCall("fc_horarioafastamentofolga");
			procedureCall
					.registerParameter("dia", Date.class, ParameterMode.IN);
			procedureCall.registerParameter("cola", Long.class,
					ParameterMode.IN);
			procedureCall.getParameterRegistration("dia").bindValue(
					new java.sql.Date(dia.getTime()));
			procedureCall.getParameterRegistration("cola").bindValue(95L);
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs
					.getCurrent();
			List<Object[]> resultado = resultSetOutput.getResultList();

			// for(Integer i=0;i<resultado.size();i++) {
			// Object[] objects = (Object[]) resultado.get(i);
			// System.out.println("The benefit is "+objects[1]);
			// }

			// List<Object[]> resultado = session.createSQLQuery(jpql)
			// .setParameter("dia", dia)
			// .setParameter("colaborador", colaborador).list();
			//
			// for (Object[] colunas : resultado) {
			//
			// System.out.println(colunas[0]);
			// System.out.println(colunas[1]);
			//
			// }

			return (List<Object[]>) resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} // carregaHorarioAfastamentoFolga

	public ArrayList<Integer> carregarMarcacoesDia(Date dia, Long colaborador) {

		ArrayList<Integer> marcas = new ArrayList<Integer>();

		// String jpql =
		// " select * from marcacaoDetalhe where colaborador_id = :cola and data = :dia and (tipo <> 'D' or tipo is null)  order by data , hora ";
		// List<MarcacaoDetalhe> resultado = session
		// .createSQLQuery(jpql)
		// .setParameter("dia", dia).setParameter("cola", colaborador)
		// .list();
		//
		// for (MarcacaoDetalhe md : resultado) {
		// // System.out.println("maraccoes " + md.getHora());
		// marcas.add(md.getHora());
		// }
		//
		// return marcas;

		String jpql = " select * from marcacaoDetalhe where colaborador_id = :cola and data = :dia and (tipo <> 'D' or tipo is null)  order by data , hora ";

		@SuppressWarnings("unchecked")
		List<Object[]> lista = session.createSQLQuery(jpql)
				.setParameter("dia", dia).setParameter("cola", colaborador)
				.list();

		for (Object[] colunas : lista) {
			marcas.add((Integer) colunas[2]);
		}

		return marcas;

	} // carregarMarcacoesDia

	// retorna o numero de dias entre duas datas
	public int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	public NormaColaborador normaColaboradorDia(Date dia, String pis) {

		try {
			String jpql = "select h from NormaColaborador h inner join fetch h.colaborador   where data_Inicio <= :dia and h.colaborador.pis=:pis  order by  data_Inicio desc";
			NormaColaborador nc = (NormaColaborador) session.createQuery(jpql)
					.setParameter("dia", dia).setParameter("pis", pis)
					.setMaxResults(1).uniqueResult();
			return nc;
		} catch (Exception e) {
			return null;
		}
	}

	public void heOcorrencia(OcorrenciaApurada oa, NormaColaborador nc,
			Integer resul, Integer diaSemana) {

		Integer horas = resul;

		if (diaSemana == 1) { // domingo
			if (nc.getNorma().getLimDomFaixaDiurno1() <= horas) {
				oa.setHoras(nc.getNorma().getLimDomFaixaDiurno1());
				oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
						.getOcorrenciaHeDomFaixaDiurno1()));
				ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

				// abate horas
				horas = horas - nc.getNorma().getLimDomFaixaDiurno1();
				if (nc.getNorma().getLimDomFaixaDiurno2() <= horas) {

					oa.setHoras(nc.getNorma().getLimDomFaixaDiurno2());
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeDomFaixaDiurno2()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					horas = horas - nc.getNorma().getLimDomFaixaDiurno2();

					if (nc.getNorma().getLimDomFaixaDiurno3() <= horas) {
						oa.setHoras(nc.getNorma().getLimDomFaixaDiurno3());
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeDomFaixaDiurno3()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						horas = horas - nc.getNorma().getLimDomFaixaDiurno3();

					} else {
						oa.setHoras(horas);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeDomFaixaDiurno3()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

					}
				} else {
					if (horas > 0) {
						oa.setHoras(horas);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeDomFaixaDiurno2()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					}
				}
			}//
			else {
				if (horas > 0) {
					oa.setHoras(horas);
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeDomFaixaDiurno1()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
				}
			}// se <= faixa1
		}// domingo 1

		// ------------
		if (diaSemana == 2) { //
			if (nc.getNorma().getLimSegFaixaDiurno1() <= horas) {
				oa.setHoras(nc.getNorma().getLimSegFaixaDiurno1());
				oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
						.getOcorrenciaHeSegFaixaDiurno1()));
				ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

				// abate horas
				horas = horas - nc.getNorma().getLimSegFaixaDiurno1();
				if (nc.getNorma().getLimSegFaixaDiurno2() <= horas) {

					oa.setHoras(nc.getNorma().getLimSegFaixaDiurno2());
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeSegFaixaDiurno2()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					horas = horas - nc.getNorma().getLimSegFaixaDiurno2();

					if (nc.getNorma().getLimSegFaixaDiurno3() <= horas) {
						oa.setHoras(nc.getNorma().getLimSegFaixaDiurno3());
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeSegFaixaDiurno3()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						horas = horas - nc.getNorma().getLimSegFaixaDiurno3();

					} else {
						if (horas > 0) {
							oa.setHoras(horas);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaHeSegFaixaDiurno3()));
							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						}
					}
				} else {
					if (horas > 0) {
						oa.setHoras(horas);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeSegFaixaDiurno2()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					}
				}
			}//
			else {
				if (horas > 0) {
					oa.setHoras(horas);
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeSegFaixaDiurno1()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
				}
			}// se <= faixa1
		}// segunda-feira 2

		// -------------------
		if (diaSemana == 3) { // terça
			if (nc.getNorma().getLimTerFaixaDiurno1() <= horas) {
				oa.setHoras(nc.getNorma().getLimTerFaixaDiurno1());
				oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
						.getOcorrenciaHeTerFaixaDiurno1()));
				ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

				// abate horas
				horas = horas - nc.getNorma().getLimTerFaixaDiurno1();
				if (nc.getNorma().getLimTerFaixaDiurno2() <= horas) {

					oa.setHoras(nc.getNorma().getLimTerFaixaDiurno2());
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeTerFaixaDiurno2()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					horas = horas - nc.getNorma().getLimTerFaixaDiurno2();

					if (nc.getNorma().getLimTerFaixaDiurno3() <= horas) {
						oa.setHoras(nc.getNorma().getLimTerFaixaDiurno3());
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeTerFaixaDiurno3()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						horas = horas - nc.getNorma().getLimTerFaixaDiurno3();

					} else {
						if (horas > 0) {
							oa.setHoras(horas);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaHeTerFaixaDiurno3()));
							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						}

					}
				} else {
					if (horas > 0) {
						oa.setHoras(horas);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeTerFaixaDiurno2()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					}
				}
			}//
			else {
				oa.setHoras(horas);
				oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
						.getOcorrenciaHeTerFaixaDiurno1()));
				ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
			}// se <= faixa1
		}// teça-feira 2

		// -------------------
		if (diaSemana == 4) { // qua
			if (nc.getNorma().getLimQuaFaixaDiurno1() <= horas) {
				oa.setHoras(nc.getNorma().getLimQuaFaixaDiurno1());
				oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
						.getOcorrenciaHeQuaFaixaDiurno1()));
				ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

				// abate horas
				horas = horas - nc.getNorma().getLimQuaFaixaDiurno1();
				if (nc.getNorma().getLimQuaFaixaDiurno2() <= horas) {

					oa.setHoras(nc.getNorma().getLimQuaFaixaDiurno2());
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeQuaFaixaDiurno2()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					horas = horas - nc.getNorma().getLimQuaFaixaDiurno2();

					if (nc.getNorma().getLimQuaFaixaDiurno3() <= horas) {
						oa.setHoras(nc.getNorma().getLimQuaFaixaDiurno3());
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeQuaFaixaDiurno3()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						horas = horas - nc.getNorma().getLimQuaFaixaDiurno3();

					} else {
						if (horas > 0) {
							oa.setHoras(horas);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaHeQuaFaixaDiurno3()));
							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						}
					}
				} else {
					if (horas > 0) {
						oa.setHoras(horas);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeQuaFaixaDiurno2()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					}
				}
			}//
			else {
				if (horas > 0) {

					oa.setHoras(horas);
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeQuaFaixaDiurno1()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
				}
			}// se <= faixa1
		}// quarta-feira 2

		// -------------------
		if (diaSemana == 5) { // qui
			if (nc.getNorma().getLimQuiFaixaDiurno1() <= horas) {
				oa.setHoras(nc.getNorma().getLimQuiFaixaDiurno1());
				oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
						.getOcorrenciaHeQuiFaixaDiurno1()));
				ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

				// abate horas
				horas = horas - nc.getNorma().getLimQuiFaixaDiurno1();
				if (nc.getNorma().getLimQuiFaixaDiurno2() <= horas) {

					oa.setHoras(nc.getNorma().getLimQuiFaixaDiurno2());
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeQuiFaixaDiurno2()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					horas = horas - nc.getNorma().getLimQuiFaixaDiurno2();

					if (nc.getNorma().getLimQuiFaixaDiurno3() <= horas) {
						oa.setHoras(nc.getNorma().getLimQuiFaixaDiurno3());
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeQuiFaixaDiurno3()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						horas = horas - nc.getNorma().getLimQuiFaixaDiurno3();

					} else {
						if (horas > 0) {
							oa.setHoras(horas);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaHeQuiFaixaDiurno3()));
							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						}
					}
				} else {
					if (horas > 0) {
						oa.setHoras(horas);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeQuiFaixaDiurno2()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					}
				}
			}//
			else {
				if (horas > 0) {
					oa.setHoras(horas);
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeQuiFaixaDiurno1()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
				}
			}// se <= faixa1
		}// quarta-feira 2

		// -------------------
		if (diaSemana == 6) { // sex
			if (nc.getNorma().getLimSexFaixaDiurno1() <= horas) {
				oa.setHoras(nc.getNorma().getLimSexFaixaDiurno1());
				oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
						.getOcorrenciaHeSexFaixaDiurno1()));
				ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

				// abate horas
				horas = horas - nc.getNorma().getLimSexFaixaDiurno1();
				if (nc.getNorma().getLimSexFaixaDiurno2() <= horas) {

					oa.setHoras(nc.getNorma().getLimSexFaixaDiurno2());
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeSexFaixaDiurno2()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					horas = horas - nc.getNorma().getLimSexFaixaDiurno2();

					if (nc.getNorma().getLimSexFaixaDiurno3() <= horas) {
						oa.setHoras(nc.getNorma().getLimSexFaixaDiurno3());
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeSexFaixaDiurno3()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						horas = horas - nc.getNorma().getLimSexFaixaDiurno3();

					} else {
						if (horas > 0) {
							oa.setHoras(horas);
							oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
									.getOcorrenciaHeSexFaixaDiurno3()));
							ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						}
					}
				} else {
					if (horas > 0) {
						oa.setHoras(horas);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeSexFaixaDiurno2()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					}
				}
			}//
			else {
				if (horas > 0) {
					oa.setHoras(horas);
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeSexFaixaDiurno1()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
				}
			}// se <= faixa1
		}// sexta-feira 6

		// -------------------
		if (diaSemana == 7) { // sab
			if (nc.getNorma().getLimSabFaixaDiurno1() <= horas) {
				oa.setHoras(nc.getNorma().getLimSabFaixaDiurno1());
				oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
						.getOcorrenciaHeSabFaixaDiurno1()));
				ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

				// abate horas
				horas = horas - nc.getNorma().getLimSabFaixaDiurno1();
				if (nc.getNorma().getLimSabFaixaDiurno2() <= horas) {

					oa.setHoras(nc.getNorma().getLimSabFaixaDiurno2());
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeSabFaixaDiurno2()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					horas = horas - nc.getNorma().getLimSabFaixaDiurno2();

					if (nc.getNorma().getLimSabFaixaDiurno3() <= horas) {
						oa.setHoras(nc.getNorma().getLimSabFaixaDiurno3());
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeSabFaixaDiurno3()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
						horas = horas - nc.getNorma().getLimSabFaixaDiurno3();

					} else {
						oa.setHoras(horas);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeSabFaixaDiurno3()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);

					}
				} else {
					if (horas > 0) {
						oa.setHoras(horas);
						oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
								.getOcorrenciaHeSabFaixaDiurno2()));
						ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
					}
				}
			}//
			else {
				if (horas > 0) {
					oa.setHoras(horas);
					oa.setOcorrencia(ocorrencias.porId(nc.getNorma()
							.getOcorrenciaHeSabFaixaDiurno1()));
					ocorrenciaApuradas.salvaOcorrenciaApuradas(oa);
				}
			}// se <= faixa1
		}// sab-feira 7

	}// heOcorrencia

	public boolean isDiaBancoHoras(Integer i, NormaColaborador nc) {
		Boolean bh = false;
		if (i == 1) {// dom
			if (nc.getNorma().getBancoHorasDom() == true) {
				bh = true;
			} else {
				bh = false;
			}
		}
		if (i == 2) {// seg
			if (nc.getNorma().getBancoHorasSeg() == true) {
				bh = true;
			} else {
				bh = false;
			}
		}
		if (i == 3) {// ter
			if (nc.getNorma().getBancoHorasTer() == true) {
				bh = true;
			} else {
				bh = false;
			}
		}
		if (i == 4) {// qua
			if (nc.getNorma().getBancoHorasQua() == true) {
				bh = true;
			} else {
				bh = false;
			}
		}
		if (i == 5) {// qui
			if (nc.getNorma().getBancoHorasQui() == true) {
				bh = true;
			} else {
				bh = false;
			}
		}
		if (i == 6) {// sex
			if (nc.getNorma().getBancoHorasSex() == true) {
				bh = true;
			} else {
				bh = false;
			}
		}
		if (i == 7) {// sab
			if (nc.getNorma().getBancoHorasSab() == true) {
				bh = true;
			} else {
				bh = false;
			}
		}

		// // folga
		// if(nc.getNorma().getBancoHorasFol()==true){
		// bh = true;
		// }
		//
		// // fer
		// if(nc.getNorma().getBancoHorasFer()==true){
		// bh = true;
		// }

		return bh;
	}
}
