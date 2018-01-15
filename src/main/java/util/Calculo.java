package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import model.Colaborador;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.jsf.JsfExceptionHandler;

public class Calculo implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(JsfExceptionHandler.class);

	public void calculoMarcacaoDia(Object[] o, ArrayList<Integer> marc,
			Colaborador c, Date dia, Integer[] resultados) {
		System.out.println("Calculo Marcaco dia ");

		if (o == null) { // nao tem horario, rertorna
			return;
		}

		Object[] j = o;

		System.out.println(">>>>> ");
		System.out.println(" cod. horario " + o[0]); // cod. horario
		System.out.println(" nome horario " + o[1]); // nome horario
		System.out.println(" data base " + o[2]); // data base
		System.out.println(" jornada " + o[3]); // jornada
		System.out.println(" regime " + o[4]); // regime
		System.out.println(" h1 " + o[5]); // h1
		System.out.println(" h2 " + o[6]); // h2
		System.out.println(" h3 " + o[7]); // h3
		System.out.println(" h4 " + o[8]); // h4
		System.out.println(" seq" + o[9]); // seq
		System.out.println(" fechamento " + o[10]); // fechamento
		System.out.println(" colaboraodr " + o[11]); // colaboraodr
		System.out.println(" tole1ant " + o[12]); // tol
		System.out.println(" tole1dep " + o[13]); // tol
		System.out.println(" tole2ant " + o[14]); // tol
		System.out.println(" tole2dep " + o[15]); // tol
		System.out.println(" tole3ant " + o[16]); // tol
		System.out.println(" tole3dep " + o[17]); // tol
		System.out.println(" tole4ant " + o[18]); // tol
		System.out.println(" tole4dep " + o[19]); // tol
		System.out.println(" tole5ant " + o[20]); // tol
		System.out.println(" tole5dep " + o[21]); // tol
		System.out.println(" tole6ant " + o[22]); // tol
		System.out.println(" tole6dep " + o[23]); // tol
		System.out.println(" tole7ant " + o[24]); // tol
		System.out.println(" tole7dep " + o[25]); // tol
		System.out.println(" tole8ant " + o[26]); // tol
		System.out.println(" tole8dep " + o[27]); // tol
		System.out.println(" pre e2 " + o[28]); // tol
		System.out.println(" pre s1 " + o[29]); // tol
		System.out.println(" afastamento" + o[30]); // afastamento
		System.out.println(" folga" + o[31]); // folga
		System.out.println(" feriado " + o[32]); // feriado
		System.out.println("----");

		System.out.println(" h1 " + o[5]);
		System.out.println(" h2 " + o[6]);
		System.out.println(" h3 " + o[7]);
		System.out.println(" h4 " + o[8]);
		System.out.println(" pre " + o[28]);
		System.out.println(" pre " + o[29]);

		System.out.println("j1 " + j[0]);
		System.out.println("j2 " + j[1]);
		System.out.println("j3 " + j[2]);
		System.out.println("j4 " + j[3]);
		System.out.println("j5 " + j[4]);
		System.out.println("j6 " + j[5]);
		System.out.println("j7 " + j[6]);
		System.out.println("j8 " + j[7]);
		System.out.println("j9 " + j[8]);
		System.out.println("j10 " + j[9]);
		System.out.println("j11 " + j[10]);
		System.out.println("j12 " + j[11]);
		System.out.println("j13 " + j[12]);
		System.out.println("j14 " + j[13]);
		System.out.println("j15 " + j[14]);

		System.out.println("---*---");

		System.out.println("o28 " + j[28]);
		System.out.println("o29 " + j[26]);

		System.out.println("j6 " + j[6]);
		System.out.println("j28 " + j[28]);
		System.out.println("j29 " + j[29]);

		// se tem pre-assinalado - deslocar na jornafa
		if (((Integer) o[28] > 0) && ((Integer) o[29] > 0)) {
			j[8] = j[6];
			j[7] = j[28];
			j[6] = j[29];
			System.out.println("Horario apos pre " + j[5]);
			System.out.println("Horario apos pre " + j[6]);
			System.out.println("Horario apos pre " + j[7]);
			System.out.println("Horario apos pre " + j[8]);

		}

		System.out.println("Tamanho do array marc em calculoMarcacaoDia "
				+ marc.size());

		if (marc.size() <= 0) { // nao tem marcacao
			return;
		}

		if ((marc.size() == 1) || (marc.size() == 3) || (marc.size() == 5)
				|| (marc.size() == 7)) { // nao tem marcacao
			// log.error("Erro de sistema numero de  marcacoes impar  :" +
			// marc.size() + " pis"
			// + c.getPis());
			return;
		}

		// acrescenta para nao dar erro de index out bound
		if (marc.size() == 2) {
			marc.add(0);
			marc.add(0);
		}

		System.out.println("marc 1" + ((marc.get(0) == 0) ? 0 : marc.get(0)));
		System.out.println("marc 2" + ((marc.get(1) == 0) ? 0 : marc.get(1)));
		System.out.println("marc 3" + ((marc.get(2) == 0) ? 0 : marc.get(2)));
		System.out.println("marc 4" + ((marc.get(3) == 0) ? 0 : marc.get(3)));

		System.out.println("Maracacao " + marc.get(0));
		System.out.println("Maracacao " + marc.get(1));
		System.out.println("Maracacao " + marc.get(2));
		System.out.println("Maracacao " + marc.get(3));

		System.out.println(" <<<<<< ");

		// System.out.println(o);

		if (marc.size() <= 4) {
			CalculaDados(dia, resultados, ((marc.get(0) != null) ? marc.get(0)
					: 0), ((marc.get(1) != null) ? marc.get(1) : 0),
					((marc.get(2) != null) ? marc.get(2) : 0),
					((marc.get(3) != null) ? marc.get(3) : 0), (Integer) j[12],
					(Integer) j[13], (Integer) j[14], (Integer) j[15],
					(Integer) j[16], (Integer) j[17], (Integer) j[18],
					(Integer) j[19], (Integer) j[5], (Integer) j[6],
					(Integer) j[7], (Integer) j[8], 0, // Integer TolInterAnt,
					0, // Integer TolInterDep,
					0, // Integer nucleo_ini,
					0, // Integer nucleo_fim,
					0, // Integer Afast_Ini,
					0, // Integer Afast_Fim,
					0, // Integer Usaquatromarc,
					0); // Integer tiporegime,

		} else {
			System.out.println("Numero de Marcacoes para calculo --->  "
					+ marc.size());
			// calcula para 6 0u 8 marcacoes
			CalculaDados6_8();
		}

	} // calculoMarcacaoDia

	/**
	 * 
	 * Calcula marcacoes 6 0u 8
	 */
	public int CalculaDados6_8() {
		return 0;

	}// CalculaDados6_8()

	// retorno no array
	// ref Integer hnto,
	// ref Integer HN,
	// ref Integer HNN,
	// ref Integer HED,
	// ref Integer HEN,
	// ref Integer AN,
	// ref Integer atraso1,
	// ref Integer atraso2,
	// ref Integer atrasoNaodescontado,
	// ref Integer saidaAnt1,
	// ref Integer saidaAnt2,
	// ref Integer saidaAntNaodescontada,

	public int CalculaDados(Date Dia, Integer resultado[], Integer E1,
			Integer S1, Integer E2, Integer S2, Integer TolE1ANT,
			Integer TolE1DEP, Integer TolS1ANT, Integer TolS1DEP,
			Integer TolE2ANT, Integer TolE2DEP, Integer TolS2ANT,
			Integer TolS2DEP, Integer HE1, Integer HS1, Integer HE2,
			Integer HS2, Integer TolInterAnt, Integer TolInterDep,
			Integer nucleo_ini, Integer nucleo_fim, Integer Afast_Ini,
			Integer Afast_Fim, Integer Usaquatromarc, Integer tiporegime) {

		Integer[] afastado = { 0 };
		String geraafasthn = "";
		Integer[] descontainterv = { 0 };
		String recebeadicionalNoturno = "";
		Integer pagaheintervalo;
		Integer feriado;
		Integer[] descontaatrasodahe = { 0 };

		@SuppressWarnings("unused")
		Boolean Hora1440;
		@SuppressWarnings("unused")
		int QtdHsRefeicao, AfastCred, DifHora, QtdHsDia, QtdHsHorario, H_Extras, AdNoturno, Credito, Debito, CredAutorizado, DebAutorizado, Diverge, AdNoturno_Ini, AdNoturno_fim, auxE1, auxS1, auxE2, auxS2, auxHE1, auxHS1, auxHE2, auxHS2, auxatraso, hntole;

		auxE1 = E1;
		auxS1 = S1;
		auxE2 = E2;
		auxS2 = S2;

		auxHE1 = HE1;
		auxHS1 = HS1;
		auxHE2 = HE2;
		auxHS2 = HS2;

		// zera variaveis
		int saidaAntNaodescontada = 0;

		Integer hnto[] = { 0 };
		Integer atraso1[] = { 0 };
		Integer atraso2[] = { 0 };
		Integer atrasoNaodescontado[] = { 0 };
		Integer saidaAnt1[] = { 0 };
		Integer saidaAnt2[] = { 0 };
		Integer H_Normais[] = { 0 };
		Integer HN[] = { 0 };
		Integer HNN[] = { 0 };
		Integer HED[] = { 0 };
		Integer HEN[] = { 0 };
		Integer AN[] = { 0 };

		HNTOL(E1, S1, E2, S2, TolE1ANT, TolE1DEP, TolS1ANT, TolS1DEP, TolE2ANT,
				TolE2DEP, TolS2ANT, TolS2DEP, HE1, HS1, HE2, HS2, hnto);

		System.out.println(" hnto----------------------------------- "
				+ hnto[0]);

		resultado[0] = hnto[0];

		Hora1440 = false;
		// calcula total trabalhado , verific&&o se ultrapassou para outro dia
		if (HE1 > HS1) {
			QtdHsDia = HS1 + 1440 - HE1;
		} else {
			QtdHsDia = HS1 - HE1;
		}

		if (HE2 > HS2) {
			QtdHsDia = QtdHsDia + HS2 + 1440 - HE2;
		} else {
			QtdHsDia = QtdHsDia + HS2 - HE2;
		}

		if ((E1 == 0) && (E2 == 0) && (S1 == 0) && (S2 == 0)) {
			return -1;
		}

		// ajusta para calculo o horario
		if (HE1 > HS1) // 23:00 e 04:00 , passa a ser 23:00 e 28:00
		{
			Hora1440 = true;
			HS1 = HS1 + 1440;
			if (HE2 != 0 && HS2 != 0) // idem para as marcacoes seguintes
			{
				HE2 = HE2 + 1440;
				HS2 = HS2 + 1440;
			}
		} else {
			if (HS1 > HE2 && (HE2 != 0 && HS2 != 0)) {
				Hora1440 = true;
				HE2 = HE2 + 1440;
				HS2 = HS2 + 1440;
			}
		}

		if (HE2 > HS2) // ex.: 23:00 e 04:00 , passa a ser 23:00 e 28:00
		{
			Hora1440 = true;
			HS2 = HS2 + 1440;
		}

		// ajusta para calculo as marcacoes
		if (E1 > S1) {
			S1 = S1 + 1440;
			if (E2 != 0 && S2 != 0) {
				E2 = E2 + 1440;
				S2 = S2 + 1440;
			}
		} else if (S1 > E2 && (E2 != 0 && S2 != 0)) {
			E2 = E2 + 1440;
			S2 = S2 + 1440;
		}

		if (E2 > S2) {
			S2 = S2 + 1440;
		}

		H_Extras = 0;
		AdNoturno = 0;
		Credito = 0;
		Debito = 0;
		CredAutorizado = 0;
		DebAutorizado = 0;
		Diverge = 0;
		QtdHsHorario = 0;

		// faz os calculos lev&&o consideracao as mudancas diferente QtdHsDia
		if (HE1 > HS1) {
			QtdHsHorario = HE1 - HS1;
		} else {
			QtdHsHorario = HS1 - HE1;
		}

		if (HE2 > HS2) {
			QtdHsHorario = QtdHsHorario + HE2 - HS2;
		} else {
			QtdHsHorario = QtdHsHorario + HS2 - HE2;
		}

		// indep}ente se usa 2 ou quatro marcacoes.
		QtdHsRefeicao = HE2 - HS1;

		// Se n�o � feriado e � dia de Trabalho}
		if (HE1 != 0 && HS1 != 0) {
			// Acerta Toler�ncia
			if (E1 > HE1 - TolE1ANT - 1 && E1 < HE1 + TolE1DEP + 1) {
				// guarda atraso nao descontado na tol.
				if (E1 > HE1) {
					atrasoNaodescontado[0] = E1 - HE1;
				}
				E1 = HE1;
			}

			if (E2 > HE2 - TolE2ANT - 1 && E2 < HE2 + TolE2DEP + 1) {
				if (E2 > HE2) {
					atrasoNaodescontado[0] = E2 - HE2;
				}
				E2 = HE2;
			}

			// if (E2>HE1-TolE1-1) && (E2<HE1+TolE1+1) E2 = HE1; // ???????????

			if (S1 > HS1 && S1 < HS1 + TolS1DEP + 1) {
				S1 = HS1;
			}
			if (S2 > HS2 && S2 < HS2 + TolS2DEP + 1 && S2 > 0) {
				S2 = HS2;
			}
			if (S2 > HS1 && S2 < HS1 + TolS1DEP + 1 && S2 > 0) {
				S2 = HS1;
			}
			if (S1 > HS2 && S1 < HS2 + TolS2DEP + 1 && S1 > 0) {
				S1 = HS2;
			}

			// GUARDA SAIDA ANTECIPADA NAO DESCONTADA NA TOL.
			if (S1 > HS1 - TolS1DEP + 1 && S1 <= HS1) {
				saidaAntNaodescontada = HS1 - S1;
			}

			if (tiporegime == 2) // Se Hor�rio Flex�vel
			{
				// deslocamento do horario.
				DifHora = HE1 - E1;
				HE1 = HE1 - DifHora;
				HS1 = HS1 - DifHora;
				if (HE2 != 0 && HS2 != 0) {
					HE2 = HE2 - DifHora;
					HS2 = HS2 - DifHora;
				}
			}

		}//

		// -------- se esta afatado e gera hn no afastamento
		if (geraafasthn.equals('1') && (afastado[0] == 1)) {
			// Acerta Hor�rio de Afastamento
			if (Afast_Ini == 0 && Afast_Fim == 0) // Se houve afastamento total
			{ // Afastamento total
				if (Afast_Ini < HE1) {
					Afast_Ini = HE1;
				}

				// ajusta o fim do afastamento
				if (HE2 == 0 && HS2 == 0) {
					if (Afast_Fim > HS1) {
						Afast_Fim = HS1;
					}
				} else {
					if (Afast_Fim > HS2) {
						Afast_Fim = HS2;
					}
				}

				if (E1 >= Afast_Ini && E1 <= Afast_Fim) {
					E1 = Afast_Ini;
				}

				if (HE2 == 0 && HS2 == 0) {
					if (S1 >= Afast_Ini && S1 <= Afast_Fim) {
						S1 = HS1;
					}
				} else {
					if (S2 >= Afast_Ini && S2 <= Afast_Fim) {
						S2 = HS2;
					}
				}
			} // Afastamento total
			else { // Afastamento parcial
				if (Afast_Ini < HE1 && Afast_Fim > HE1) {
					Afast_Ini = HE1;
				}

				if (HE2 == 0 && HS2 == 0) {
					if (Afast_Fim > HS1) {
						Afast_Fim = HS1;
					}
				} else {
					if (Afast_Fim > HS2) {
						Afast_Fim = HS2;
					}
				}

				if ((E1 >= Afast_Ini && E1 <= Afast_Fim && E1 > HE1)) {
					E1 = HE1;
				}

				if (HE2 == 0 && HS2 == 0) {
					if (S1 >= Afast_Ini && S1 <= Afast_Fim && S1 > HE1) {
						S1 = HS1;
					}
				} else {
					if (S2 >= Afast_Ini && S2 <= Afast_Fim && S2 > HE1) {
						S2 = HS2;
					}
				}
				// Afastamento parcial
			} // Se Afastado e Gera afastamento como HN/cr�dito no dia
		} // se nao � feriado � dia de trabalho

		// parametros da empresa

		AdNoturno_Ini = 1320;
		AdNoturno_fim = 300;

		switch (tiporegime) {
		case 0: // FIXO

			// Calcula_AtrasoFX(auxE1, S1, E2, S2, TolE1ANT, TolE1DEP, TolS1ANT,
			// TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP, HE1, HS1,
			// HE2, HS2, TolInterAnt, TolInterDep, atraso1, atraso2,
			// atrasoNaodescontado, Usaquatromarc, descontainterv[0]);
			// resultado[1] = atraso1[0];
			// resultado[2] = atraso2[0];

			Calcula_AtrasoSaidaAntFX(auxE1, S1, E2, S2, TolE1ANT, TolE1DEP,
					TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP,
					HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep, atraso1,
					atraso2, saidaAnt1, saidaAnt2, Usaquatromarc,
					descontainterv[0]);
			resultado[1] = atraso1[0];
			resultado[2] = atraso2[0];
			resultado[3] = saidaAnt1[0];
			resultado[4] = saidaAnt2[0];

			// Calcula_SaidaAntFX(E1, auxS1, auxE2, S2, TolE1ANT, TolE1DEP,
			// TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP,
			// HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep, saidaAnt1,
			// saidaAnt2, Usaquatromarc, descontainterv[0]);
			// resultado[3] = saidaAnt1[0];
			// resultado[4] = saidaAnt2[0];

			HorasNormaisFX(auxE1, S1, E2, S2, TolE1ANT, TolE1DEP, TolS1ANT,
					TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP,
					TolInterAnt, TolInterDep, HE1, HS1, HE2, HS2,
					AdNoturno_Ini, AdNoturno_fim, HN, HNN, descontainterv[0]);

			resultado[5] = HN[0];
			resultado[6] = HNN[0];

			// ---------------- C�lculo de Horas Extras ---------------}
			HEFixo(E1, auxS1, auxE2, S2, TolE1ANT, TolE1DEP, TolS1ANT,
					TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP,
					TolInterAnt, TolInterDep, HE1, HS1, HE2, HS2,
					AdNoturno_Ini, AdNoturno_fim, Usaquatromarc, HED, HEN);

			resultado[7] = HED[0];
			resultado[8] = HEN[0];

			// ******* se recebe adicional noturno, calcular
			if (recebeadicionalNoturno.equals('1')) // auxS1 - marcacao original
				Calcula_AdicionalNoturno(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
						TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT,
						TolS2DEP, HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep,
						AdNoturno_Ini, AdNoturno_fim, AN);

			resultado[9] = AN[0];

			// fixo
			break;

		case 2: // FLEXIVEL
			System.out.println("flexivel");
			if (descontaatrasodahe.equals('1')) {

				System.out.println("dentro " + descontaatrasodahe);
				auxatraso = 0;
				if (auxHE1 <= auxHS1) // mesmo dia 2 marcacoes
				{
					if (auxS1 < auxE1)
						auxS1 = 1440 - auxS1;

					// se fez menos horas
					if (((auxS1 - auxE1) + (auxS2 - auxE2)) < ((HS1 - HE1) + (HS2 - HE2))) {

						if (HS1 != 0 && HE1 != 0) // nao � dia de descanso
							atraso1[0] = (HS1 - HE1) + (HS2 - HE2)
									- ((auxS1 - auxE1) + (auxS2 - auxE2));
						else
							atraso1[0] = 0;

						auxatraso = atraso1[0];

						if (atraso1[0] <= TolE1DEP) {
							atraso1[0] = 0;
						}

						HN[0] = ((S1 - E1) + (S2 - E2)) - atraso1[0];

						// se trabalhou suficiente para almoco desconta
						if (((S1 - E1) + (S2 - E2)) > ((HS1 - HE1) + (HE2 - HS1)))
							if (descontainterv[0] > 0) {
								HN[0] = HN[0] - descontainterv[0];
							}
					}

					// se fez mais horas
					if (auxS2 - auxE2 < 0) {
						auxS2 = auxS2 + 1440;
					}

					if ((auxS1 - auxE1) + (auxS2 - auxE2) > ((HS1 - HE1)
							+ (HS2 - HE2) + TolE1ANT)) {
						HN[0] = ((HS1 - HE1) + (HS2 - HE2));
						if (descontainterv[0] > 0) {
							HN[0] = HN[0] - descontainterv[0];
						}
						HED[0] = ((auxS1 - auxE1) + (auxS2 - auxE2))
								- ((HS1 - HE1) + (HS2 - HE2));
					}

					// se fez dentro da tolerancia
					if ((((auxS1 - auxE1) + (auxS2 - auxE2)) >= ((HS1 - HE1) + (HS2 - HE2)))
							&& (((auxS1 - auxE1) + (auxS2 - auxE2)) <= ((HS1 - HE1)
									+ (HS2 - HE2) + TolE1ANT))) {
						HN[0] = ((HS1 - HE1) + (HS2 - HE2));
						if (descontainterv[0] > 0) {
							HN[0] = HN[0] - descontainterv[0];
						}
					}

				} else {
					// se fez menos horas
					if (((auxS1 + (1440 - auxE1) + (auxS2 - auxE2)) < (HS1
							+ (1440 - HE1) + (HS2 - HE2)))) {

						atraso1[0] = ((HS1 - HE1) + (HS2 - HE2))
								- (auxS1 + (1440 - auxE1) + (auxS2 - auxE2));
						if (atraso1[0] < 0)
							auxatraso = 0; // troquei o sinal -+
						else
							auxatraso = atraso1[0];

						// if atraso1 < TolE1DEP atraso1 = 0;
						if (auxatraso <= TolE1DEP)
							atraso1[0] = 0;
						else
							atraso1[0] = auxatraso;

						HN[0] = ((HS1 - HE1) + (HS2 - HE2)) - atraso1[0];
						// se trabalhou suficiente para almoco desconta
						if (((S1 - E1) + (S2 - E2)) > ((HS1 - HE1) + (HE2 - HS1)))
							if (descontainterv[0] > 0)
								HN[0] = HN[0] - descontainterv[0];
					}

					// se fez mais horas
					if ((auxS1 + (1440 - auxE1) + (auxS2 - auxE2)) > ((HS1 - HE1)
							+ (HS2 - HE2) + TolE1ANT)) {
						HN[0] = ((HS1 - HE1) + (HS2 - HE2));
						if (descontainterv[0] > 0)
							HN[0] = HN[0] - descontainterv[0];
						HED[0] = (auxS1 + (1440 - auxE1) + (auxS2 - auxE2))
								- ((HS1 - HE1) + (HS2 - HE2));
					}

				}

				if (recebeadicionalNoturno.equals('1')) { // auxS1 - marcacao
															// original

					Calcula_AdicionalNoturno(E1, S1, E2, S2, TolE1ANT,
							TolE1DEP, TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP,
							TolS2ANT, TolS2DEP, HE1, HS1, HE2, HS2,
							TolInterAnt, TolInterDep, AdNoturno_Ini,
							AdNoturno_fim, AN);

				} else {

					Calcula_AtrasoFlex(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
							TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT,
							TolS2DEP, TolInterAnt, TolInterDep, HE1, HS1, HE2,
							HS2, descontainterv[0], atraso1);

					if (atraso1[0] < TolE1DEP) {
						atrasoNaodescontado = atraso1;
						atraso1[0] = 0;
					}

					// testar para saber se atraso ou saida antecipada
					if ((HE2 == 0) && (HS2 == 0)) {

						if ((E1 - HE1 > HS1 - S1)
								&& (atraso1[0] < saidaAnt1[0])) {
							atraso1[0] = saidaAnt1[0];
							saidaAnt1[0] = 0;
						} else {
							saidaAnt1[0] = atraso1[0];
							atraso1[0] = 0;
						}

					}

					// deslocamento do horario.
					E1 = auxE1;
					S1 = auxS1;
					E2 = auxE2;
					S2 = auxS2;

					HE1 = auxHE1;
					HS1 = auxHS1;
					HE2 = auxHE2;
					HS2 = auxHS2;

					if (HS1 < HE1) {
						HS1 = HS1 + 1440;
					}
					if (HE2 < HS1) {
						HE2 = HE2 + 1440;
					}
					if (HS2 < HE2) {
						HS2 = HS2 + 1440;
					}

					if ((S1 < E1) && (S1 != 0)) {
						S1 = S1 + 1440;
					}
					if ((E2 < S1) && (E2 != 0)) {
						E2 = E2 + 1440;
					}
					if ((S2 < E2) && (S2 != 0)) {
						S2 = S2 + 1440;
					}

					DifHora = HE1 - E1;

					if (HE1 < 0) {
						HE1 = 1440 + HE1;
					}
					HE1 = HE1 - DifHora;
					if (HS1 < 0) {
						HS1 = 1440 + HS1;
					}
					HS1 = HS1 - DifHora;

					if (HE2 != 0 && HS2 != 0) {
						HE2 = HE2 - DifHora;
						if (HE2 < 0) {
							HE2 = 1440 + HE2;
						}

						HS2 = HS2 - DifHora;
						if (HS2 < 0) {
							HS2 = 1440 + HS2;
						}
					}

					if ((auxHE1 == 0) && (auxHS1 == 0) && (auxHE2 == 0)
							&& (auxHS2 == 0)) {
						HE1 = 0;
						HS1 = 0;
						HE2 = 0;
						HS2 = 0;
					}

					if (HE2 == HS2) {
						HE2 = 0;
						HS2 = 0;
					}

					// Calcula_HN_HE_Flex( E1 , S1, E2, S2 ,
					// TolE1ANT,TolE1DEP,TolS1ANT,TolS1DEP,TolE2ANT,TolE2DEP,TolS2ANT,TolS2DEP,
					// HE1, HS1, HE2, HS2 , TolInterAnt,TolInterDep,
					// 0,AdNoturno_Ini,AdNoturno_fim,
					// HN,HNN,
					// HED,HEN,descontainterv,pagaheintervalo,feriado);

					if (recebeadicionalNoturno.equals('1'))

						Calcula_AdicionalNoturno(E1, S1, E2, S2, TolE1ANT,
								TolE1DEP, TolS1ANT, TolS1DEP, TolE2ANT,
								TolE2DEP, TolS2ANT, TolS2DEP, HE1, HS1, HE2,
								HS2, TolInterAnt, TolInterDep, AdNoturno_Ini,
								AdNoturno_fim, AN);

					// adicopnal noturno ajuste
					// trabalha ate as 22 entao an normal
					// tranalha entre 22 e 5 , adn somente apos o fim do horario

					if (AN[0] > 0) {
						HN[0] = HN[0] - AN[0];
					}

					HNN[0] = AN[0] + HEN[0];
					if ((atraso1[0] > HED[0] + HEN[0]) && (HED[0] + HEN[0] > 0)
							&& (atraso1[0] > 0)) {
						atraso1[0] = atraso1[0] - (HED[0] + HEN[0]);
						HED[0] = 0;
						HEN[0] = 0;

					}

					if ((atraso1[0] < HED[0] + HEN[0]) && (HED[0] + HEN[0] > 0)
							&& (atraso1[0] > 0)) {
						if (HED[0] >= atraso1[0])
							HED[0] = HED[0] - atraso1[0];
						else {
							HED[0] = HED[0] + (atraso1[0] - HED[0]);
							atraso1[0] = 0;
							if (HED[0] > HEN[0]) {
								HED[0] = HED[0] - HEN[0];
								HEN[0] = 0;
							} else {
								HEN[0] = HEN[0] - HED[0];
								HED[0] = 0;
							}
						}
						atraso1[0] = 0;
					}

					if ((atraso1[0] == HED[0] + HEN[0])
							&& (HED[0] + HEN[0] > 0) && (atraso1[0] > 0)) {
						HED[0] = 0;
						HEN[0] = 0;
						atraso1[0] = 0;
					}

					// resultados
					resultado[0] = hnto[0];
					resultado[1] = atraso1[0];
					resultado[2] = atraso2[0];
					resultado[3] = saidaAnt1[0];
					resultado[4] = saidaAnt2[0];
					resultado[5] = HN[0];
					resultado[6] = HNN[0];
					resultado[7] = HED[0];
					resultado[8] = HEN[0];
					resultado[9] = AN[0];

				}

				break; // flexivel

			}// switch
		}
		return 1;
	} // fim calcula dados

	// ******************
	// HNTOL
	// ******************
	private void HNTOL(int E1, int S1, int E2, int S2, int TolE1ANT,
			int TolE1DEP, int TolS1ANT, int TolS1DEP, int TolE2ANT,
			int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1, int HS1,
			int HE2, int HS2, Integer[] HNTO) {

		int Carga, trabalhado;

		HNTO[0] = 0;
		// calcula total a trabalhar , verific&&o se ultrapassou para outro dia
		if (HE1 > HS1) {
			Carga = HS1 + 1440 - HE1;
		} else {
			Carga = HS1 - HE1;
		}

		if (HE2 > HS2) {
			Carga = Carga + HS2 + 1440 - HE2;
		} else {
			Carga = Carga + HS2 - HE2;
		}

		// calcula total trabalhado , verific&&o se ultrapassou para outro dia
		if (E1 > S1) {
			trabalhado = S1 + 1440 - E1;
		} else {
			trabalhado = S1 - E1;
		}

		if (E2 > S2) {
			trabalhado = trabalhado + S2 + 1440 - E2;
		} else {
			trabalhado = trabalhado + S2 - E2;
		}

		if ((trabalhado > Carga) && (trabalhado <= (Carga + TolS2DEP))) {
			HNTO[0] = trabalhado - Carga;
		}

	}// HNTOL

	// ----------------------------------------------------------//
	// -------------------- Calcula_AtrasoFX ------------------//
	// ----------------------------------------------------------//
	public void Calcula_AtrasoFX(int E1, int S1, int E2, int S2, int TolE1ANT,
			int TolE1DEP, int TolS1ANT, int TolS1DEP, int TolE2ANT,
			int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1, int HS1,
			int HE2, int HS2, int TolInterAnt, int TolInterDep,
			Integer[] atraso1, Integer[] atraso2,
			Integer[] atrasoNaodescontado, int Usaquatromarc, int descontainterv) {

		int QtdHsRefeicao;
		int tothnRealizado, tothn;

		if ((HE1 == 0 && HS1 == 0) && (HE2 == 0 && HS2 == 0)) // feriado
			return;

		atraso1[0] = 0;
		atraso2[0] = 0;

		// marc 4 e so espera duas
		if ((HE2 == 0 && HS2 == 0) && (E1 != 0) && (S1 != 0) && (E2 != 0)
				&& (S2 != 0)) {

			if (((HS1 - HE1 - descontainterv) > (E1 - S1) + (S2 - E2))
					&& (S2 < 1440)) {

				TolS1ANT = 0; // se marcou 4 e so esperada duas entao nao tem
								// tolerancia nda tolS1ant
				if ((E2 - S1) > descontainterv + TolS1ANT)
					atraso1[0] = (E2 - S1) - descontainterv;
			}
		}

		// espera 4 e marca 2
		if ((HE1 != 0 && HS1 != 0) && (HE2 == 0 && HS2 == 0) && (E1 != 0)
				&& (S1 != 0) && (E2 == 0) && (S2 == 0)) {
			if ((HS1 - HE1) + TolS1ANT > (S1 - E1)) {
				atraso1[0] = (HS1 - HE1) - (S1 - E1);
			}
			if ((HS1 - HE1) >= 360) {
				atraso1[0] = atraso1[0] - 60;
			}
		}

		// espera 4 e marca 2
		if ((HE2 != 0 && HS2 != 0) && (E1 != 0) && (S1 != 0) && (E2 == 0)
				&& (S2 == 0)) {

			tothn = ((HS1 - HE1) + (HS2 - HE2)) - descontainterv;
			if (E1 < (HE1 - TolE1ANT)) {
				tothnRealizado = (S1 - HE1) + (S2 - E2);
			} else {
				tothnRealizado = (S1 - E1) + (S2 - E2);
			}

			if (tothn - tothnRealizado > 0) {
				atraso1[0] = tothn - tothnRealizado;
			}
		}

		if ((HE2 != 0 && HS2 != 0) || (Usaquatromarc == 1)) // Se Exite hor�rio
															// ap�s intervalo
		{
			if ((TolInterAnt > 0) || (TolInterDep > 0))
				if (((S1 > HS1 - TolInterAnt) && (S1 < HE2 - TolInterDep)) // se
																			// marcacao
																			// esta
																			// entre
																			// HS1
																			// e
																			// HE2
						&& ((E2 - S1) < (HE2 - HS1 + TolE2DEP))) // se gastou
																	// menos
																	// ajusta
					E2 = S1 + (HE2 - HS1);

			QtdHsRefeicao = HE2 - HS1;

			if (E1 <= HS1) // Se E1 antes do intervalo
			{
				if (E1 > TolE1DEP + HE1) {
					atraso1[0] = atraso1[0] + (E1 - HE1);
				}
			} else if ((E1 >= HE1) && (E1 <= HS2)) // {Se E1 ap�s o intervalo}
			{
				atraso1[0] = (HS1 - HE1);
				atraso2[0] = (E1 - HE2);
			}

			if (E2 > HE2) // {Se E2 ap�s o fim do intervalo}
			{
				if (E2 > TolE2DEP + HE2) {
					if ((TolInterAnt > 0) || (TolInterDep > 0)) // {Possui
																// toler�ncia de
																// refei��o}
					{
						// if (E2 > S1 + QtdHsRefeicao + TolE2DEP)
						// { atraso2 = atraso2; } // + (E2-S1-QtdHsRefeicao);
					} else {
						atraso2[0] = atraso2[0] + (E2 - HE2);
					}
				}
			}
		} // Se Existe hor�rio ap�s intervalo}
		else if (E1 > TolE1DEP + HE1) // {+Nucleo_Ini}
		{
			atraso1[0] = atraso1[0] + (E1 - HE1);
		} // Se n�o existe hor�rio ap�s intervalo
	} // fim Calcula_Atraso

	// ----------------------------------------------------------//
	// -------------------- Calcula_SaidaAntFX ----------------//
	// ----------------------------------------------------------//
	private void Calcula_SaidaAntFX(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1,
			int HS1, int HE2, int HS2, int TolInterAnt, int TolInterDep,
			Integer[] SaidaAnt1, Integer[] SaidaAnt2, int Usaquatromarc,
			int descontainterv) {
		int QtdHsRefeicao;

		System.out.println("Entrei em SaidaAntFX linha 872 - calculo.java");
		System.out.println("Marcacao");
		System.out.println(E1);
		System.out.println(S1);
		System.out.println(E2);
		System.out.println(S2);

		System.out.println("HORARIO");
		System.out.println(HE1);
		System.out.println(HS1);
		System.out.println(HE2);
		System.out.println(HS2);
		System.out.println("maracacoes que tem na saidaant");

		// caso tenha duas marcaoes e esta esper&&o 4 marcacoes entao nao
		// calcula , pois ja esta s}o
		// calculado no atraso
		if ((HE1 != 0) && (HS1 != 0) && (HE2 != 0) && (HS2 != 0) && (E1 != 0)
				&& (S1 != 0) && (E2 == 0) && (S2 == 0)) {
			return;
		}

		System.out.println("saida ant ponto 1 ");

		if ((HE2 != 0 && HS2 != 0) || (Usaquatromarc == 1)) // Se Exite hor�rio
															// ap�s intervalo
		{

			System.out.println("saida ant ponto 2 ");

			QtdHsRefeicao = HE2 - HS1;
			SaidaAnt1[0] = 0;

			if (S1 < HS1) // Se S1 antes do intervalo
			{
				if ((S1 < HS1 - TolS1ANT - TolInterAnt) && (S1 >= HE2))
					SaidaAnt1[0] = SaidaAnt1[0] + (HS2 - S1) - TolInterAnt;
				else
				// else if (S1< HS1-TolS1ANT-TolInterAnt) && (S1<=HE2)
				// SaidaAnt1 = SaidaAnt1 + (HS1-S1)-TolInterAnt
				if ((S1 < HS1 - TolS1ANT - TolInterAnt) && (S1 > HE1))
					SaidaAnt1[0] = SaidaAnt1[0] + (HS1 - S1) - TolInterAnt;
			}

			System.out.println("saida ant ponto 3 ");

			if (S2 <= HE2 && S2 > HS1) // Se s2 entre o intervalo
			{
				SaidaAnt1[0] = SaidaAnt1[0] + (HS2 - S2);
			}

			System.out.println("saida ant ponto 3 ");

			if (S2 > HE2) // Se s2 ap�s o fim do intervalo
			{
				if (S2 < HS2 - TolS2ANT) // saiu antes de hse-tol.
				{
					if ((S2 > HE2 + TolInterDep + TolE2DEP))
						SaidaAnt2[0] = SaidaAnt2[0] + (HS2 - S2);
					else if (S2 < HE2) // se so tem duas marcacoes se saiu
										// depois HE2
						SaidaAnt2[0] = SaidaAnt2[0] + (QtdHsRefeicao - S2)
								+ (HS2 - HE2);
					else if ((S2 > HE2) && (S2 < HS2))
						SaidaAnt2[0] = SaidaAnt2[0] + (HS2 - S2);

				}
			}

			if (((S1 >= HS1) && (S1 < HS2) && (S2 == 0) && (E2 == 0))
					|| ((S2 == 0) && (E2 == 0))) {
				if (S1 - HE2 > 0)
					SaidaAnt2[0] = (HS2 - S1 - TolS2ANT);
				else if ((S1 > HS1) && (S1 <= HE2))
					SaidaAnt2[0] = (HS2 - HE2); // nao amputar almoco com saida
												// ant (HE2-S1)
				else if ((S1 < HS1) && (S1 >= HE1))
					SaidaAnt2[0] = (HS2 - HE2) + (HS1 - S1);

			}

		} // {}if Se Existe hor�rio ap�s intervalo}
		else if ((S2 == 0) && (E2 == 0)) {

			if (S1 < E1)
				S1 = S1 + 1440;

			if (S1 < HS1 - TolS1ANT) // se 2 marcacoes no horarios
				SaidaAnt1[0] = (HS1 - S1); // SaidaAnt1 + (E1-HE1) {Se n�o
											// existe hor�rio ap�s intervalo}
			else if ((S1 > HE2 + TolE2DEP) && (HE2 != 0))
				SaidaAnt1[0] = SaidaAnt1[0] + (HS2 - S1);
		} else {
			if (S1 < HS1 - TolS1ANT) // se 2 marcacoes no horarios
			{
				if ((HE2 != 0) && (HS2 != 0))
					SaidaAnt1[0] = (HS1 - S1); // SaidaAnt1 + (E1-HE1) {Se n�o
												// existe hor�rio ap�s
												// intervalo}
			}

			if (((E2 - S1) < (descontainterv - TolS1ANT))
					&& (S1 < HS1 - TolS1ANT))
				SaidaAnt1[0] = descontainterv - (E2 - S1);

		}

		// coloquei para o caso de 10 as 13 com almoco entre 12 e 13 e flex. 120
		if ((HE2 != 0 && HS2 != 0) && ((E2 - S1) > (HE2 - HS1 + TolS1ANT))) {
			SaidaAnt1[0] = SaidaAnt1[0] + ((E2 - S1) - (HE2 - HS1));
		}

		if (SaidaAnt1[0] > 240)
			SaidaAnt1[0] = SaidaAnt1[0] - descontainterv;

	}// fim Calcula_SaidaAnt}

	// ----------------------------------------------------------//
	// -------------------- HorasNormais-------------------------//
	// ----------------------------------------------------------//
	public void HorasNormaisFX(int E1, int S1, int E2, int S2, int TolE1ANT,
			int TolE1DEP, int TolS1ANT, int TolS1DEP, int TolE2ANT,
			int TolE2DEP, int TolS2ANT, int TolS2DEP, int TolInterAnt,
			int TolInterDep, int HE1, int HS1, int HE2, int HS2,
			int AdNoturno_ini, int AdNoturno_fim, Integer[] H_Normais,
			Integer[] H_NormaisNoturna, int descontainterv) {
		int QtdHsRefeicao;

		if (HS1 != 0 && HE2 != 0)
			QtdHsRefeicao = HE2 - HS1;
		else
			QtdHsRefeicao = 0;

		// 4 marcacoes
		if (E2 != 0 && S2 != 0) {
			if ((TolInterAnt > 0) || (TolInterDep > 0)) {
				HNFixo4Bat_comTolIntervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
						TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT,
						TolS2DEP, TolInterAnt, TolInterDep, HE1, HS1, HE2, HS2,
						H_Normais);
			} // TolIntervalo >0
			else {
				HN_4bat_sem_Tolintervalo(E1, S1, E2, S2, HE1, HS1, HE2, HS2,
						H_Normais);
			} // se nao tem TolIntervalo

			// CALCULA horas normais noturnas com 4 marcacoes
			HNN4_com_Tolintervalo(E1, S1, E2, S2, HE1, HS1, HE2, HS2, TolE1ANT,
					TolE1DEP, TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT,
					TolS2DEP, AdNoturno_ini, AdNoturno_fim, H_NormaisNoturna,
					descontainterv);

		} // se existem 4 batidas
		else // Se existe somente duas batidas e marca Intervalo
		{

			if ((TolE1ANT > 0) || (TolS1DEP > 0)) {

				HN_com_Tolintervalo(E1, S1, E2, S2, HE1, HS1, HE2, HS2,
						TolE1ANT, TolE1DEP, TolS1ANT, TolS1DEP, TolE2ANT,
						TolE2DEP, TolS2ANT, TolS2DEP, H_Normais, descontainterv);

				// CALCULA horas normais noturnas com 2 marcacoes
				HNN2_com_Tolintervalo(E1, S1, E2, S2, HE1, HS1, HE2, HS2,
						TolE1ANT, TolE1DEP, TolS1ANT, TolS1DEP, TolE2ANT,
						TolE2DEP, TolS2ANT, TolS2DEP, AdNoturno_ini,
						AdNoturno_fim, H_NormaisNoturna, descontainterv);

			} else {

				HN_sem_Tolintervalo(E1, S1, E2, S2, HE1, HS1, HE2, HS2,
						H_Normais, descontainterv);

				// CALCULA horas normais noturnas com 2 marcacoes
				HNN2_sem_Tolintervalo(E1, S1, E2, S2, HE1, HS1, HE2, HS2,
						TolE1ANT, TolE1DEP, TolS1ANT, TolS1DEP, TolE2ANT,
						TolE2DEP, TolS2ANT, TolS2DEP, AdNoturno_ini,
						AdNoturno_fim, H_NormaisNoturna, descontainterv);
			}

		} // }if existem 2 batidas
	} // HorasNormaisFX

	// ----------------------------------------------------------
	// HE FIXO
	// ----------------------------------------------------------
	public void HEFixo(int E1, int S1, int E2, int S2, int TolE1ANT,
			int TolE1DEP, int TolS1ANT, int TolS1DEP, int TolE2ANT,
			int TolE2DEP, int TolS2ANT, int TolS2DEP, int TolInterAnt,
			int TolInterDep, int HE1, int HS1, int HE2, int HS2,
			int AdNoturno_Ini, int AdNoturno_fim, int Usaquatromarc,
			Integer[] HED, Integer[] HEN) {

		if (HE2 != 0 && HS2 != 0) // Se Marca intervalo, 4 marcacoes previstas
			HEMarca_Intervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP, TolS1ANT,
					TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP,
					TolInterAnt, TolInterDep, HE1, HS1, HE2, HS2,
					AdNoturno_Ini, AdNoturno_fim, HED, HEN);
		else
			HENaoMarca_Intervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP, TolS1ANT,
					TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP,
					TolInterAnt, TolInterDep, HE1, HS1, HE2, HS2,
					AdNoturno_Ini, AdNoturno_fim, HED, HEN);

	} // HEFixo

	// ----------------------------------------------------------
	// Calcula_AdicionalNoturno
	// ----------------------------------------------------------

	private void Calcula_AdicionalNoturno(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1,
			int HS1, int HE2, int HS2, int TolInterAnt, int TolInterDep,
			int AdNoturno_Ini, int AdNoturno_fim, Integer[] AdNoturno) {

		int AN_INI = AdNoturno_Ini;
		int AN_FIM = AdNoturno_fim;
		if (AN_INI > AN_FIM)
			AN_FIM = AN_FIM + 1440;

		// Calculo_do_AdicionalNoturno2(E1 , S1, E2, S2,
		// TolE1ANT,TolE1DEP,TolS1ANT
		// ,TolS1DEP,TolE2ANT,TolE2DEP,TolS2ANT,TolS2DEP, HE1, HS1, HE2, HS2,
		// TolInterAnt,TolInterDep,AdNoturno_Ini,AdNoturno_fim , AdNoturno);

	}

	// ----------------------------------------------------------//
	// -------------------- Calcula_AtrasoFlex ------------------//
	// ----------------------------------------------------------//
	private void Calcula_AtrasoFlex(int E1, int S1, int e2, int s2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP,
			int TolInterAnt, int TolInterDep, int HE1, int HS1, int he2,
			int hs2, int descontainterv, Integer[] atraso) {
		// Boolean Possui_Intervalo ;
		int Hs_Realizada, Hs_Intervalo, Tot_Horario;

		int auxE1, auxS1, auxe2, auxs2;
		int auxHE1, auxHS1, auxhe2, auxhs2;

		Hs_Intervalo = 0;
		Hs_Realizada = 0;
		Tot_Horario = 0;

		auxE1 = 1;
		auxS1 = S1;
		auxe2 = e2;
		auxs2 = s2;

		auxHE1 = HE1;
		auxHS1 = HS1;
		auxhe2 = he2;
		auxhs2 = hs2;

		if ((auxHS1 < auxHE1) && (auxHS1 != 0)) {
			auxHS1 = auxHS1 + 1440;
		}
		if ((auxhe2 < auxHS1) && (auxhe2 != 0)) {
			auxhe2 = auxhe2 + 1440;
		}

		if ((auxS1 < auxE1) && (auxS1 != 0)) {
			auxS1 = auxS1 + 1440;
		}
		if ((auxe2 < auxS1) && (auxe2 != 0)) {
			auxe2 = auxe2 + 1440;
		}
		if ((auxs2 < auxe2) && (auxs2 != 0)) {
			auxs2 = auxs2 + 1440;
		}

		// if (Possui_Intervalo) { Hs_Intervalo = he2-HS1;}
		if (descontainterv > 0) {
			Hs_Intervalo = descontainterv;
		}

		if ((Hs_Intervalo > 0) && (e2 != 0 && s2 != 0)) {
			if (((e2 - S1) - TolE2DEP <= Hs_Intervalo)
					&& (e2 - S1 < Hs_Intervalo + TolE2DEP)) {
				e2 = S1 + Hs_Intervalo;
			}
		}

		Hs_Realizada = (s2 - e2) + (S1 - E1);
		if ((s2 == 0 && e2 == 0)) // {Se tem intervalo e se n�o existe marca��o
									// ap�s intervalo } (Possui_Intervalo) &&
		{
			if (S1 > he2) {
				Hs_Realizada = Hs_Realizada - Hs_Intervalo;
			}
		}

		if ((descontainterv > 0) && (he2 == 0 && hs2 == 0)) {
			Tot_Horario = (HS1 - HE1);
		} else {
			Tot_Horario = (hs2 - he2) + (HS1 - HE1);
		}

		atraso[0] = Tot_Horario - (Hs_Realizada);

		if ((he2 != 0) && (hs2 != 0) && (e2 != 0) && (s2 != 0)
				&& (atraso[0] <= 0)) {
			if ((e2 - S1) > (he2 - HS1)) {
				atraso[0] = (e2 - S1) - (he2 - HS1);
			}
		}

		if (atraso[0] <= TolS1DEP) {
			atraso[0] = 0;
		}
		if (atraso[0] < 0) {
			atraso[0] = 0;
		}

	}// Calcula_AtrasoFlex

	// ----------------------------------------------------------//
	// -------------------- Calcula_HN_HE_Flex ------------------//
	// ----------------------------------------------------------//
	private void Calcula_HN_HE_Flex(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1,
			int HS1, int HE2, int HS2, int TolInterAnt, int TolInterDep,
			int Period_Noturno, int AdNoturno_Ini, int AdNoturno_fim,
			Integer[] H_Normais, int HNN, int H_Extras, int HEN,
			int descontainterv, int pagaheintervalo, Boolean feriado) {
		Boolean Possui_Intervalo;
		int Hs_Intervalo, Hs_Realizada, DifHora, Tot_Horario;

		H_Normais[0] = 0;
		H_Extras = 0;
		Hs_Intervalo = 0;
		if (HE2 == 0 && HS2 == 0) {
			Possui_Intervalo = false;
		} else {
			Possui_Intervalo = true;
		}

		Integer[] vHEN = { 0 };
		Integer[] vH_Extras = { 0 };

		// se tem quatro marcacoes e batei duas entao nao tem intervalo
		// coloquei por causa da di santini 5/4/12
		if ((HE1 != 0) && (HS1 != 0) && (HE2 != 0) && (HS2 != 0) && (E2 == 0)
				&& (S2 == 0)) {
			Possui_Intervalo = false;
		}

		if (Possui_Intervalo) {
			Hs_Intervalo = HE2 - HS1;
		}

		Tot_Horario = (HS2 - HE2) + (HS1 - HE1);
		if (E2 > S2) {
			Hs_Realizada = ((1440 + S2) - E2) + (S1 - E1);
		} else {
			Hs_Realizada = (S2 - E2) + (S1 - E1);
		}

		if ((Possui_Intervalo) && (S2 == 0 && E2 == 0)) // {Se tem intervalo e
														// se n�o existe
														// marca��o ap�s
														// intervalo}
		{
			if (S1 > HE2) {
				Hs_Realizada = Hs_Realizada - Hs_Intervalo;
			}
		}

		// desconta intervalo para que bate somente 2 batidas
		if ((descontainterv > 0) && (HE2 == 0 && HS2 == 0)) // se desconta
															// intervalo para
															// duas marc. > 0
		{
			if (S2 != 0 && E2 != 0) {
				Hs_Realizada = Hs_Realizada;
			} else {
				Hs_Realizada = Hs_Realizada - descontainterv;
			}

			if ((feriado = false) || (HE1 != 0 && HS1 != 0)) {
				Tot_Horario = Tot_Horario - descontainterv;
			} else if (((feriado = true) || (HE1 == 0 && HS1 == 0))
					&& (pagaheintervalo == 1)) {
				Tot_Horario = Tot_Horario - descontainterv;
			}
		}

		if ((Hs_Realizada > Tot_Horario)
				&& (Hs_Realizada < Tot_Horario + TolS2DEP)) {
			Hs_Realizada = Tot_Horario;
		}

		if (Tot_Horario > Hs_Realizada) {
			H_Normais[0] = Hs_Realizada;
		} else {
			H_Normais[0] = Tot_Horario;
		}

		if (HE2 != 0 && HS2 != 0) // {Se Marca intervalo, 4 marcacoes previstas
									// }
		{
			HEFlexMarca_Intervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP, TolS1ANT,
					TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP,
					TolInterAnt, TolInterDep, HE1, HS1, HE2, HS2,
					AdNoturno_Ini, AdNoturno_fim, vH_Extras, vHEN);
		} else {
			HENaoMarca_Intervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP, TolS1ANT,
					TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP,
					TolInterAnt, TolInterDep, HE1, HS1, HE2, HS2,
					AdNoturno_Ini, AdNoturno_fim, vH_Extras, vHEN);
		}

	} // Calcula_HN_HE_Flex

	private void HEFlexMarca_Intervalo(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP,
			int TolInterAnt, int TolInterDep, int HE1, int HS1, int HE2,
			int HS2, int AdNoturno_Ini, int AdNoturno_fim, Integer[] HED,
			Integer[] HEN) {
		int QtdHsRefeicao;
		// C�lculo de Horas Extras com hor�rio de intervalo
		QtdHsRefeicao = HE2 - HS1;
		// Se dentro da faixa de toler�ncia ou menor que o Hor�rio de Entrada

		if (E2 != 0 && S2 != 0) // Se existe entrada ap�s intervalo
		{ // 4 batidas

			HEFlex_4Bat_marcaIntervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
					TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP,
					HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep,
					AdNoturno_Ini, AdNoturno_fim, HED, HEN);

			// progamacao defensiva
			if (HED[0] > 1440) {
				HED[0] = HED[0] - 1440;
			}
			if (HEN[0] > 1440) {
				HEN[0] = HEN[0] - 1440;
			}

		} // se existem 4 batidas}
		else // Se existe somente duas batidas
		{
			// se bate 4 e so bateu 2
			if ((E2 == 0 && S2 == 0) && (HE2 != 0 && HS2 != 0)) {
				// **** fazer esta situacao, 2 marc. mas horario tem 4 marc.
				HEFixo_2Bat_semIntervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
						TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT,
						TolS2DEP, HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep,
						AdNoturno_Ini, AdNoturno_fim, HED, HEN);

			} else {
				HEFixo_2Bat_marcaIntervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
						TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT,
						TolS2DEP, HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep,
						AdNoturno_Ini, AdNoturno_fim, HED, HEN);
			}
		} // }if existem 2 batidas}

	} // HEMarca_Intervalo

	private void HEFixo_2Bat_semIntervalo(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolEint1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1,
			int HS1, int HE2, int HS2, int TolInterAnt, int TolInterDep,
			int AdNoturno_Ini, int AdNoturno_fim, Integer[] HED, Integer[] HEN) {

		// HE1 HS1 HE2 HS2
		// ----------|--------========-----------|-----
		// E1 S1
		//

		// verificar se ultrapassou o faixa da hora extra, ou seja , passou para
		// o segundo periodo

		if ((S1 > HS1) && (S1 > HS2) && (E2 == 0) && (E2 == 0)) {
			HED[0] = (S1 - HS2) + (HE2 - HS1);
		}
		if (S1 > HS2) {
			HED[0] = (S1 - HS2) + (HE2 - HS1);
		}
		if (E1 < (HE1 - TolE1ANT)) {
			HED[0] = HED[0] + (HE1 - E1);
		}

		if (S1 >= 1440) {
			if ((S1 - E1) < 300) // 6 horas
				HED[0] = 0;
		}
	}

	private void HEFixo_2Bat_marcaIntervalo(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1,
			int HS1, int HE2, int HS2, int TolInterAnt, int TolInterDep,
			int AdNoturno_Ini, int AdNoturno_fim, Integer[] HED, Integer[] HEN)

	{
		int IPN, FPN, H_Extras;

		IPN = 0; // inicio periodo noturno
		FPN = 0; // fim periodo noturno
		HED[0] = 0;
		HEN[0] = 0;
		H_Extras = 0;

		IPN = AdNoturno_Ini;
		if (S1 > 1440) {
			FPN = AdNoturno_fim + 1440;
		} else {
			FPN = AdNoturno_fim;
		}

		if (((E1 < HE1 - TolE1ANT) && (S1 < HE1)) || ((E1 > HS2) && (S1 < HS2))) {
			H_Extras = S1 - E1;
		} else {
			if (E1 < (HE1 - TolE1ANT)) {
				H_Extras = H_Extras + HE1 - E1;
				HEN[0] = Calcula_HoraExtraNoturna(HE1, E1, IPN, FPN);
				if (HEN[0] > 0) {
					H_Extras = H_Extras - HEN[0];
				}
			}
			if (S1 > HS2 + TolS2DEP) {
				H_Extras = H_Extras + S1 - HS2;
				HEN[0] = Calcula_HoraExtraNoturna(S1, HS2, IPN, FPN);
				if (HEN[0] > 0) {
					H_Extras = H_Extras - HEN[0];
				}
			}

			if (E1 > HS1 && E1 < HE2) {
				if (S1 > HE2) {
					H_Extras = H_Extras + (HE2 - E1);
					HEN[0] = Calcula_HoraExtraNoturna(HE2, E1, IPN, FPN);
					if (HEN[0] > 0) {
						H_Extras = H_Extras - HEN[0];
					}
				} else {
					H_Extras = H_Extras + (S1 - E1);
					HEN[0] = Calcula_HoraExtraNoturna(S1, E1, IPN, FPN);
					if (HEN[0] > 0) {
						H_Extras = H_Extras - HEN[0];
					}

				}
			} else if (S1 > HS1 && S1 < HE2) {
				if (E1 < HS1) {
					H_Extras = H_Extras + (S1 - HS1);
					HEN[0] = Calcula_HoraExtraNoturna(S1, HS1, IPN, FPN);
					if (HEN[0] > 0) {
						H_Extras = H_Extras - HEN[0];
					}
				} else {
					H_Extras = H_Extras + (S1 - E1);
					HEN[0] = Calcula_HoraExtraNoturna(S1, E1, IPN, FPN);
					if (HEN[0] > 0) {
						H_Extras = H_Extras - HEN[0];
					}
				}
			} else if ((S1 > HE2) && (S1 < HS2) && (E1 <= HS1)) {
				H_Extras = H_Extras + (HE2 - HS1);
				HEN[0] = Calcula_HoraExtraNoturna(HE2, HS1, IPN, FPN);
				if (HEN[0] > 0) {
					H_Extras = H_Extras - HEN[0];
				}
			}
		}

		HED[0] = H_Extras;
	} // HEFixo_2Bat_marcaIntervalo

	private int Calcula_HoraExtraNoturna(int E1, int S1, int IPN, int FPN) {
		if ((E1 > IPN) && (S1 > IPN) && (E1 > FPN) && (S1 < FPN)) {
			if ((E1 - FPN) > (E1 - S1))
				return (E1 - S1) - (E1 - FPN);
		}

		if (E1 < IPN && S1 < IPN) {
			return 0;
		}

		if ((E1 >= IPN) && (E1 <= FPN) && (S1 <= IPN))
			if ((E1 - FPN) > (E1 - IPN))
				return (E1 - S1) - (E1 - IPN);

		return 0;
	} // Calcula_HoraExtraNoturna

	private void HENaoMarca_Intervalo(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP,
			int TolInterAnt, int TolInterDep, int HE1, int HS1, int HE2,
			int HS2, int AdNoturno_Ini, int AdNoturno_fim, Integer[] HED,
			Integer[] HEN) {
		if (E2 != 0 && S2 != 0) // Se existe entrada ap�s intervalo
		{ // {4 batidas}

			HEFixo_4Bat_naomarcaIntervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
					TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP,
					HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep,
					AdNoturno_Ini, AdNoturno_fim, HED, HEN);
		} else { // Se Existem 2 Batidas
			HEFixo_2Bat_naomarcaIntervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
					TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT, TolS2DEP,
					HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep,
					AdNoturno_Ini, AdNoturno_fim, HED, HEN);

		}

	} // HENaoMarca_Intervalo

	private void HEFixo_4Bat_naomarcaIntervalo(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1,
			int HS1, int HE2, int HS2, int TolInterAnt, int TolInterDep,
			int AdNoturno_Ini, int AdNoturno_fim, Integer[] HED, Integer[] HEN) {

		int AUXHEN, AUXHEDS, IPN, FPN, H_Extras;

		IPN = 0; // inicio periodo noturno
		FPN = 0; // fim periodo noturno
		AUXHEN = 0;
		AUXHEDS = 0;
		H_Extras = 0;

		if (S1 > 1440) {
			IPN = AdNoturno_Ini + 1440;
			FPN = AdNoturno_fim + 1440;
		} else {
			IPN = AdNoturno_Ini;
			if (S2 > 1440) {
				FPN = AdNoturno_fim + 1440;
			} else {
				FPN = AdNoturno_fim;
			}
		}

		if (E1 < HE1) {
			HED[0] = HED[0] + (HE1 - E1);
		}
		if (E1 > HS1) {
			if (S2 <= 1440) {
				HED[0] = HED[0] + S1 - E1 + S2 - E2;
			} else {
				HED[0] = HED[0] + S1 - E1 + (1440 - E2) + (S2 - 1440);
			}
		} // {E1>HS1}
		else if (S1 > HS1 + TolS1DEP) {
			if ((S1 >= IPN) && (HS1 <= IPN)) {
				AUXHEN = AUXHEN + (S1 - IPN);
				AUXHEDS = AUXHEDS + IPN - HS1;
				if ((E2 > IPN) && (S2 < FPN)) {
					AUXHEN = AUXHEN + (S2 - E2);
				}
				// H_Extras = AUXHEN + AUXHEDS;
				HEN[0] = AUXHEN;
				HED[0] = AUXHEDS;

			}

			if ((S1 < IPN) && (HS1 < IPN)) {
				AUXHEDS = AUXHEDS + (S1 - HS1);
				if ((E2 > IPN) && (S2 < FPN)) {
					AUXHEN = AUXHEN + (S2 - E2);
					HEN[0] = AUXHEN;
				} else {
					if ((E2 < IPN) && (S2 > IPN)) {
						AUXHEN = AUXHEN + (S2 - IPN);
						AUXHEDS = AUXHEDS + IPN - E2;
					}
				}

				HEN[0] = AUXHEN;
				HED[0] = AUXHEDS;

			} // {(S1 < IPN) && (HS1<IPN)}
		} // {S1>HS1}
		else if (E2 > HS1) {
			if (HS1 > IPN) {
				HEN[0] = (S2 - E2);
			} else {
				if ((E2 <= IPN) && (S2 <= IPN)) {
					if (S2 < 1440)
						HED[0] = (S2 - E2);
					else
						HED[0] = (S2 - 1440);
				} else // coloquei para at}er he apos 22:00 em 04/05/03 rivaldo
				{
					// E2 > IPN

					if (E2 > 1440) {
						HEN[0] = (S2 - E2);
					} else {
						if (S2 > 1440) {
							if ((E2 <= IPN) && (S2 > IPN)) {
								HED[0] = (IPN - E2);
								HEN[0] = (1440 - IPN) + (S2 - 1440);
							}

							else {
								HEN[0] = (1440 - E2) + (S2 - 1440);
							}
						} else if ((E2 <= IPN) && (S2 > IPN)) {
							HEN[0] = (S2 - IPN);
							HED[0] = (IPN - E2);
						} else {
						}
					}
				}
			}
			// H_Extras = AUXHEN + AUXHEDS;
			// HED = H_Extras; // 11/08/2008 - NAO ESTAVA CALCUL&&O
		} // E2>HS1
		else if (S2 > HS1) {
			if (E2 > HS1) {
				if (E2 > IPN)
					AUXHEN = AUXHEN + (S2 - E2);
				else {
					HEN[0] = AUXHEN + (S2 - IPN);
					HED[0] = AUXHEDS + (IPN - E2);
				}
			} else // S2>HS1
			{
				if (HS1 > IPN) {
					AUXHEN = AUXHEN + (S2 - HS1);
				} else // HS1>IPN
				{
					if ((HS1 < IPN) && (S2 > IPN)) {
						AUXHEN = AUXHEN + (S2 - IPN);
						AUXHEDS = AUXHEDS + (IPN - HS1);
						HED[0] = AUXHEDS;
						HEN[0] = AUXHEN;
					} else {
						if ((HS1 < IPN) && (S2 < IPN)
								&& ((S2 - HS1) > TolS2DEP))
							HED[0] = HED[0] + (S2 - HS1);
					}
				}
			}
		} // S2>HS1

	}// HEFixo_4Bat_naomarcaIntervalo

	private void HEFixo_2Bat_naomarcaIntervalo(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1,
			int HS1, int HE2, int HS2, int TolInterAnt, int TolInterDep,
			int AdNoturno_Ini, int AdNoturno_fim, Integer[] HED, Integer[] HEN) {

		int AUXHES, AUXHEDS, AUXHEN;

		int IPN, FPN, H_Extras;

		IPN = 0; // inicio periodo noturno
		FPN = 0; // fim periodo noturno
		AUXHEN = 0;
		AUXHEDS = 0;
		H_Extras = 0;

		if (S1 < E1) {
			S1 = S1 + 1440;
		}

		if (S1 > 1440) {
			IPN = AdNoturno_Ini;
			FPN = AdNoturno_fim + 1440;
		} else {
			IPN = AdNoturno_Ini;
			if (S2 > 1440) {
				FPN = AdNoturno_fim + 1440;
			} else {
				FPN = AdNoturno_fim;
			}
		}

		// caso 1
		if ((HE1 < IPN) && (HE1 < FPN + 1440) && (HS1 < IPN)
				&& (HS1 < FPN + 1440)) {
			if ((E1 < (HE1 - TolE1ANT)) && (S1 <= HE1)) {
				AUXHEDS = S1 - E1;
			} else if ((E1 < (HE1 - TolE1ANT)) && (S1 > HE1) && (S1 < HS1))
				AUXHEDS = HE1 - E1;
			else if ((E1 <= (HE1 - TolE1ANT)) && (S1 >= HS1))
				AUXHEDS = (HE1 - E1) + (S1 - HS1);
			else if ((E1 >= HE1) && (E1 <= HS1) && (S1 > (HS1 + TolS1DEP)))
				AUXHEDS = S1 - HS1;
			else if ((E1 >= HS1) && (S1 > (HS1 + TolS1DEP)))
				AUXHEDS = S1 - E1;
		} // caso 1

		// caso 2
		if ((HE1 < IPN) && (HE1 < FPN) && (HS1 >= IPN) && (HS1 <= FPN)) {
			if ((E1 < (HE1 - TolE1ANT)) && (E1 < IPN) && (E1 < HS1)
					&& (E1 < FPN) && (S1 < HE1) && (S1 < IPN) && (S1 < HS1)
					&& (S1 < FPN))
				AUXHEDS = S1 - E1;
			else if ((E1 < (HE1 - TolE1ANT)) && (E1 < IPN) && (E1 < HS1)
					&& (E1 < FPN) && (S1 >= HE1) && (S1 < IPN) && (S1 < HS1)
					&& (S1 < FPN))
				AUXHEDS = HE1 - E1;
			else if ((E1 < (HE1 - TolE1ANT)) && (E1 < IPN) && (E1 < HS1)
					&& (E1 < FPN) && (S1 >= HE1) && (S1 >= IPN) && (S1 <= HS1)
					&& (S1 < FPN)) {
				AUXHEDS = HE1 - E1;
			} else if ((E1 < (HE1 - TolE1ANT)) && (E1 < IPN) && (E1 < HS1)
					&& (E1 < FPN) && (S1 > HE1) && (S1 > IPN) && (S1 > HS1)
					&& (S1 <= FPN)) {
				AUXHEDS = (HE1 - E1);
				AUXHEN = S1 - HS1;
			} else if ((E1 < (HE1 - TolE1ANT)) && (E1 <= IPN) && (E1 < HS1)
					&& (E1 < FPN) && (S1 > HE1) && (S1 > IPN) && (S1 > HS1)
					&& (S1 > FPN)) {
				AUXHEDS = (HE1 - E1) + (S1 - FPN);
				AUXHEN = (FPN - HS1);
			} else if ((E1 > (HE1 - TolE1ANT)) && (E1 < IPN) && (E1 < HS1)
					&& (S1 > HE1) && (S1 > IPN) && (S1 > HS1 + TolS1DEP)
					&& (S1 <= FPN))
				AUXHEN = S1 - HS1;
			else if ((E1 >= HE1) && (E1 <= IPN) && (E1 < HS1) && (E1 < FPN)
					&& // 5
					(S1 > HE1) && (S1 > IPN) && (S1 > HS1 + TolS1DEP)
					&& (S1 > FPN)) {
				AUXHEDS = (HE1 - E1) + (S1 - FPN);
				AUXHEN = FPN - HS1;
			} else if ((E1 > HE1) && (E1 < IPN) && (E1 < HS1) && (E1 < FPN) && // 6
					(S1 > HE1) && (S1 > IPN) && (S1 >= HS1) && (S1 <= FPN))
				AUXHEN = S1 - HS1;
			else if ((E1 > HS1) && (E1 < IPN) && (E1 < HS1) && (E1 < FPN) && // 7
					(S1 > HE1) && (S1 > IPN) && (S1 > HS1) && (S1 > FPN)) {
				AUXHEDS = S1 - FPN;
				AUXHEN = FPN - HS1;
			} else if ((E1 > HE1) && (E1 > IPN) && (E1 < HS1) && (E1 < FPN) && // 8
					(S1 > HE1) && (S1 > IPN) && (S1 > HS1) && (S1 < FPN))
				AUXHEN = S1 - HS1;
			else if ((E1 > HE1) && (E1 > IPN) && (E1 <= HS1) && (E1 < FPN) && // 9
					(S1 > HE1) && (S1 > IPN) && (S1 > HS1) && (S1 > FPN)) {
				AUXHEN = FPN - HS1;
				AUXHEDS = S1 - FPN;
			} else if ((E1 > HE1) && (E1 > IPN) && (E1 >= HS1) && (E1 < FPN) && // 10
					(S1 > HE1) && (S1 > IPN) && (S1 > HS1) && (S1 < FPN))
				AUXHEDS = S1 - E1;
			else if ((E1 > HE1) && (E1 > IPN) && (E1 >= HS1) && (E1 < FPN) && // 11
					(S1 > HE1) && (S1 > IPN) && (S1 > HS1) && (S1 > FPN)) {
				AUXHEDS = S1 - FPN;
				AUXHEN = FPN - E1;
			} else if ((E1 > HE1) && (E1 > IPN) && (E1 >= HS1) && (E1 > FPN) && // 12
					(S1 > HE1) && (S1 > IPN) && (S1 > HS1) && (S1 > FPN))
				AUXHEDS = S1 - E1;
		} // caso 2

		// caso 3
		if ((HE1 > IPN) && (HE1 < FPN) && (HS1 > IPN) && (HS1 < FPN)) {
			if (E1 < IPN && S1 <= IPN)
				AUXHEDS = S1 - E1;
			else if ((E1 < IPN) && (E1 < HE1 - TolE1ANT) && (E1 < HS1)
					&& (E1 < FPN) && (S1 >= IPN) && (S1 <= HE1) && (S1 < HS1)
					&& (S1 < FPN)) {
				AUXHEN = S1 - IPN;
				AUXHEDS = IPN - E1;
			} else if ((E1 < IPN) && (E1 < HE1 - TolE1ANT) && (E1 < HS1)
					&& (E1 < FPN) && (S1 > IPN) && (S1 > HE1) && (S1 <= HS1)
					&& (S1 < FPN)) {
				AUXHEN = HE1 - IPN;
				AUXHEDS = IPN - E1;
			} else if ((E1 < IPN) && (E1 < HE1) && (E1 < HS1) && (E1 < FPN)
					&& (S1 > IPN) && (S1 > HE1) && (S1 > HS1) && (S1 < FPN)) {
				AUXHEN = HE1 - IPN;
				AUXHEDS = (IPN - E1) + (S1 - HS1);
			} else if ((E1 < IPN) && (E1 < HE1) && (E1 < HS1) && (E1 < FPN)
					&& (S1 > IPN) && (S1 > HE1) && (S1 > HS1) && (S1 > FPN)) {
				AUXHEN = (HE1 - IPN) + (S1 - FPN);
				AUXHEDS = (IPN - E1) + (S1 - HS1);
			} else if ((E1 >= IPN) && (E1 <= HE1) && (E1 < HS1) && (E1 < FPN)
					&& (S1 >= IPN) && (S1 <= HE1) && (S1 < HS1) && (S1 < FPN))
				AUXHEN = S1 - E1;
			else if ((E1 >= IPN) && (E1 <= HE1) && (E1 < HS1) && (E1 < FPN)
					&& (S1 >= IPN) && (S1 > HE1) && (S1 < HS1) && (S1 < FPN))
				AUXHEN = HE1 - E1;
			else if ((E1 >= IPN) && (E1 <= HE1) && (E1 < HS1) && (E1 < FPN)
					&& (S1 >= IPN) && (S1 > HE1) && (S1 > HS1) && (S1 < FPN))
				AUXHEN = (HE1 - E1) + (S1 - HS1);
			else if ((E1 >= IPN) && (E1 <= HE1) && (E1 < HS1) && (E1 < FPN)
					&& (S1 >= IPN) && (S1 > HE1) && (S1 > HS1) && (S1 > FPN)) {
				AUXHEN = (HE1 - E1) + (FPN - HS1);
				AUXHEDS = (S1 - FPN);
			} else if ((E1 > IPN) && (E1 > HE1) && (E1 < HS1) && (E1 < FPN)
					&& (S1 > IPN) && (S1 > HE1) && (S1 > HS1) && (S1 < FPN))
				AUXHEN = S1 - HS1;
			else if ((E1 > IPN) && (E1 > HE1) && (E1 < HS1) && (E1 < FPN)
					&& (S1 > IPN) && (S1 > HE1) && (S1 > HS1) && (S1 > FPN)) {
				AUXHEN = (FPN - HS1);
				AUXHEDS = (S1 - FPN);
			} else if ((E1 > IPN) && (E1 > HE1) && (E1 >= HS1) && (E1 < FPN)
					&& (S1 > IPN) && (S1 > HE1) && (S1 >= HS1) && (S1 < FPN))
				AUXHEN = (S1 - E1);
			else if ((E1 > IPN) && (E1 > HE1) && (E1 >= HS1) && (E1 < FPN)
					&& (S1 > IPN) && (S1 > HE1) && (S1 >= HS1) && (S1 > FPN)) {
				AUXHEN = FPN - E1;
				AUXHEDS = S1 - FPN;
			} else if ((E1 > IPN) && (E1 > HE1) && (E1 >= HS1) && (E1 > FPN)
					&& (S1 > IPN) && (S1 > HE1) && (S1 >= HS1) && (S1 > FPN))
				AUXHEDS = S1 - E1;

		} // caso 3

		// caso 4
		if ((HE1 >= IPN) && (HE1 < FPN) && (HS1 > IPN) && (HS1 > FPN)) {
			if ((E1 < IPN) && (E1 < HS1) && (E1 < FPN) && (E1 < HS1) && // 1
					(S1 < IPN) && (S1 < HS1) && (S1 < FPN) && (S1 < HS1))
				AUXHEDS = S1 - E1;
			else if ((E1 < IPN) && (E1 < HS1) && (E1 < FPN) && (E1 < HS1) && // 2
					(S1 > IPN) && (S1 < HE1) && (S1 < FPN) && (S1 < HS1)) {
				AUXHEN = (S1 - IPN);
				AUXHEDS = (IPN - E1);
			} else if ((E1 < IPN) && (E1 < HS1) && (E1 < FPN) && (E1 < HS1) && // 3
					(S1 > IPN) && (S1 > HE1) && (S1 < FPN) && (S1 < HS1)) {
				AUXHEN = (HE1 - IPN);
				AUXHEDS = (IPN - E1);
			} else if ((E1 < IPN) && (E1 < HS1) && (E1 < FPN) && (E1 < HS1) && // 4
					(S1 > IPN) && (S1 > HE1) && (S1 > FPN) && (S1 < HS1)) {
				AUXHEN = HE1 - IPN;
				AUXHEDS = IPN - E1;
			} else if ((E1 <= IPN) && (E1 < HS1) && (E1 < FPN) && (E1 < HS1) && // 5
					(S1 > IPN) && (S1 > HE1) && (S1 > FPN) && (S1 > HS1)) {
				AUXHEDS = (IPN - E1) + (S1 - HS1);
			} else if ((E1 > IPN) && (E1 < HS1) && (E1 < FPN) && (E1 < HS1) && // 6
					(S1 > IPN) && (S1 < HE1) && (S1 < FPN) && (S1 < HS1))
				AUXHEN = S1 - E1;
			else if ((E1 > IPN) && (E1 < HS1) && (E1 < FPN) && (E1 < HS1) && // 7
					(S1 > IPN) && (S1 > HE1) && (S1 < FPN) && (S1 < HS1))
				AUXHEN = HE1 - E1;
			else if ((E1 > IPN) && (E1 < HS1) && (E1 < FPN) && (E1 < HS1) && // 8
					(S1 > IPN) && (S1 > HE1) && (S1 > FPN) && (S1 < HS1)) {
				AUXHEN = HE1 - E1;
			} else if ((E1 > IPN) && (E1 < HS1) && (E1 < FPN) && (E1 < HS1) && // 9
					(S1 > IPN) && (S1 > HE1) && (S1 > FPN) && (S1 > HS1)) {
				AUXHEN = HE1 - E1;
				AUXHEDS = S1 - HS1;
			} else if ((E1 > IPN) && (E1 >= HS1) && (E1 < FPN) && (E1 < HS1) && // 10
					(S1 > IPN) && (S1 > HE1) && (S1 > FPN) && (S1 > HS1))
				AUXHEDS = S1 - HS1;
			else if ((E1 > IPN) && (E1 >= HS1) && (E1 > FPN) && (E1 <= HS1) && // 11
					(S1 > IPN) && (S1 > HE1) && (S1 > FPN) && (S1 > HS1))
				AUXHEDS = S1 - HS1;
			else if ((E1 > IPN) && (E1 > HS1) && (E1 > FPN) && (E1 > HS1) && // 12
					(S1 > IPN) && (S1 > HE1) && (S1 > FPN) && (S1 > HS1))
				AUXHEDS = S1 - E1;
		}// caso 4

		// caso 5
		if ((HE1 > IPN) && (HE1 > FPN) && (HS1 > IPN) && (HS1 > FPN)) {
			if ((E1 < IPN) && (E1 < FPN) && (E1 < HE1) && (E1 < HS1) && // 1
					(S1 < IPN) && (S1 < FPN) && (S1 < HE1) && (S1 < HS1))
				AUXHEDS = S1 - E1;
			else if ((E1 < IPN) && (E1 < FPN) && (E1 < HE1) && (E1 < HS1) && // 2
					(S1 > IPN) && (S1 < FPN) && (S1 < HE1) && (S1 < HS1)) {
				AUXHEDS = IPN - E1;
				AUXHEN = S1 - IPN;
			} else if ((E1 < IPN) && (E1 < FPN) && (E1 < HE1) && (E1 < HS1) && // 3
					(S1 > IPN) && (S1 > FPN) && (S1 < HE1) && (S1 < HS1)) {
				AUXHEDS = (IPN - E1) + (S1 - FPN);
				AUXHEN = FPN - IPN;
			} else if ((E1 < IPN) && (E1 < FPN) && (E1 < HE1) && (E1 < HS1) && // 4
					(S1 > IPN) && (S1 > FPN) && (S1 >= HE1) && (S1 < HS1)) {
				AUXHEDS = (IPN - E1) + (HE1 - FPN);
				AUXHEN = FPN - IPN;
			} else if ((E1 < IPN) && (E1 < FPN) && (E1 < HE1) && (E1 < HS1) && // 5
					(S1 > IPN) && (S1 > FPN) && (S1 > HE1) && (S1 > HS1)) {
				AUXHEDS = (IPN - E1) + (HE1 - FPN) + (S1 - HS1);
				AUXHEN = FPN - IPN;
			} else if ((E1 > IPN) && (E1 < FPN) && (E1 < HE1) && (E1 < HS1) && // 6
					(S1 > IPN) && (S1 < FPN) && (S1 < HE1) && (S1 < HS1))
				AUXHEN = S1 - E1;
			else if ((E1 > IPN) && (E1 < FPN) && (E1 < HE1) && (E1 < HS1) && // 7
					(S1 > IPN) && (S1 > FPN) && (S1 <= HE1) && (S1 < HS1)) {
				AUXHEDS = S1 - FPN;
				AUXHEN = FPN - E1;
			} else if ((E1 > IPN) && (E1 < FPN) && (E1 < HE1) && (E1 < HS1) && // 8
					(S1 > IPN) && (S1 > FPN) && (S1 > HE1) && (S1 < HS1)) {
				AUXHEDS = HE1 - FPN;
				AUXHEN = FPN - E1;
			} else if ((E1 > IPN) && (E1 < FPN) && (E1 < HE1) && (E1 < HS1) && // 9
					(S1 > IPN) && (S1 > FPN) && (S1 > HE1) && (S1 > HS1)) {
				AUXHEN = FPN - E1;
				AUXHEDS = (HE1 - FPN) + (S1 - HS1);
			} else if ((E1 > IPN) && (E1 > FPN) && (E1 < HE1) && (E1 < HS1) && // 10
					(S1 > IPN) && (S1 > FPN) && (S1 < HE1) && (S1 < HS1))
				AUXHEDS = S1 - E1;
			else if ((E1 > IPN) && (E1 > FPN) && (E1 < HE1) && (E1 < HS1) && // 11
					(S1 > IPN) && (S1 > FPN) && (S1 >= HE1) && (S1 <= HS1))
				AUXHEDS = HE1 - E1;
			else if ((E1 > IPN) && (E1 > FPN) && (E1 < HE1) && (E1 < HS1) && // 12
					(S1 > IPN) && (S1 > FPN) && (S1 > HE1) && (S1 > HS1))
				AUXHEDS = (HE1 - E1) + (S1 - HS1);
			else if ((E1 > IPN) && (E1 > FPN) && (E1 >= HE1) && (E1 < HS1)
					&& (S1 > IPN) && (S1 > FPN) && (S1 > HE1) && (S1 > HS1))
				AUXHEDS = S1 - HS1;
			else if ((E1 > IPN) && (E1 > FPN) && (E1 > HE1) && (E1 > HS1)
					&& (S1 > IPN) && (S1 > FPN) && (S1 > HE1) && (S1 > HS1))
				AUXHEDS = S1 - E1;

		} // caso 5

		// caso 6
		if ((HE1 <= IPN) && (HE1 < FPN) && (HS1 > IPN) && (HS1 > FPN)) {
			if ((E1 < HE1) && (E1 < IPN) && (E1 < FPN) && (E1 < HS1) && // 1
					(S1 < IPN) && (S1 < FPN) && (S1 < HE1) && (S1 < HS1))
				AUXHEDS = S1 - E1;
			else if ((E1 < HE1) && (E1 < IPN) && (E1 < FPN) && (E1 < HS1) && // 2
					(S1 > IPN) && (S1 < FPN) && (S1 < HE1) && (S1 < HS1))
				AUXHEDS = HE1 - E1;
			else if ((E1 < HE1) && (E1 < IPN) && (E1 < FPN) && (E1 < HS1) && // 3
					(S1 > IPN) && (S1 > FPN) && (S1 < HE1) && (S1 < HS1))
				AUXHEDS = HE1 - E1;
			else if ((E1 < HE1) && (E1 < IPN) && (E1 < FPN) && (E1 < HS1) && // 4
					(S1 > IPN) && (S1 > FPN) && (S1 > HE1) && (S1 < HS1))
				AUXHEDS = HE1 - E1;
			else if ((E1 < HE1) && (E1 < IPN) && (E1 < FPN) && (E1 < HS1)
					&& // 5
					(S1 > IPN) && (S1 > FPN) && (S1 > HE1)
					&& (S1 > HS1 + TolS1DEP))
				AUXHEDS = (HE1 - E1) + (S1 - HS1);
			else if ((E1 >= HE1) && (E1 < IPN) && (E1 < FPN) && (E1 < HS1)
					&& // 6
					(S1 > HE1) && (S1 > IPN) && (S1 > FPN)
					&& (S1 > HS1 + TolS1DEP))
				AUXHEDS = S1 - HS1;
			else if ((E1 >= HE1) && (E1 > IPN) && (E1 < FPN) && (E1 < HS1) && // 7
					(S1 > HE1) && (S1 > IPN) && (S1 > FPN) && (S1 > HS1))
				AUXHEDS = S1 - HS1;
			else if ((E1 >= HE1) && (E1 > IPN) && (E1 > FPN) && (E1 < HS1) && // 8
					(S1 > HE1) && (S1 > IPN) && (S1 > FPN) && (S1 > HS1))
				AUXHEDS = S1 - HS1;
			else if ((E1 >= HE1) && (E1 > IPN) && (E1 > FPN) && (E1 > HS1) && // 9
					(S1 > HE1) && (S1 > IPN) && (S1 > FPN) && (S1 > HS1))
				AUXHEDS = S1 - E1;

		} // caso 6

		HED[0] = AUXHEDS;
		HEN[0] = AUXHEN;

	} // HEFixo_2Bat_naomarcaIntervalo

	private void HEFlex_4Bat_marcaIntervalo(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1,
			int HS1, int HE2, int HS2, int TolInterAnt, int TolInterDep,
			int AdNoturno_Ini, int AdNoturno_fim, Integer[] HED, Integer[] HEN) {
		int AUXHEN, AUXHEDS, IPN, FPN, H_Extras;
		int auxHE1, auxhe2, auxHS1, auxhs2;
		int auxE1, auxe2, auxS1, auxs2;

		IPN = 0; // inicio periodo noturno
		FPN = 0; // fim periodo noturno
		HED[0] = 0;
		HEN[0] = 0;

		// if S1<E1 S1 = S1+ 1440;

		if (S1 > 1440) {

			if (E1 >= 1440) {
				IPN = AdNoturno_Ini + 1440;
			} else {
				IPN = AdNoturno_Ini;
			}

			if ((E1 >= 1440) && (S1 >= 1440)) {
				IPN = AdNoturno_Ini + 1440;
			} else {
				IPN = AdNoturno_Ini;
			}

			if ((E1 >= 1440) && (S1 >= 1440) && (E2 >= 1440)) {
				IPN = AdNoturno_Ini + 1440;
			} else {
				IPN = AdNoturno_Ini;
			}

			FPN = AdNoturno_fim + 1440;
		} else {
			IPN = AdNoturno_Ini;
			if (S2 > 1440) {
				FPN = AdNoturno_fim + 1440;
			} else {
				FPN = AdNoturno_fim + 1440;
			}
		}

		// ajusta o horario de acordo as batidas. puxa o horario para fazer o
		// calculo

		// HE1 HS1 HE2 HS2
		// ------=====|--------|--------|---------|-----
		// E1 S1
		// IPN FPN (fim do periodo noturno
		// -----------------------------------|------------|-------

		// chegou antes da hora
		if (HE1 > E1 && HE1 < IPN) {
			HED[0] = HE1 - E1;
		}

		// situacao HE1 > E1 com IPN
		if (HE1 > E1) {
			if ((IPN < E1) && (FPN < HE1) && (FPN > E1)) {
				HEN[0] = FPN - E1;
				HED[0] = HE1 - FPN;
			} else if ((IPN > E1) && (FPN < HE1) && (E1 < FPN)) {
				HEN[0] = IPN - E1;
				HEN[0] = FPN - E1;
			} else if ((E1 < IPN) && (FPN > HE1) && (IPN < HE1)) {
				HED[0] = IPN - E1;
				HEN[0] = HE1 - IPN;
			} else if ((IPN < E1) && (FPN < E1)) {
				HED[0] = HE1 - E1;
			} else if ((IPN > HE1) && (FPN > HE1)) {
				HED[0] = HE1 - E1;
			}

		} // HE1>E1

		// HE1 HS1 HE2 HS2
		// ----------|--------|====----|---------|-----
		// S1 E2
		//

		if ((S1 > HS1) && (S1 < HE2) && (E2 >= HE2)) {
			if ((IPN < HS1) && (FPN < S1) && (FPN > HS1)) {
				HEN[0] = FPN - HS1;
			} else if ((IPN > HS1) && (FPN > HS1) && (FPN < S1) && (S1 < HE2)) {
				HED[0] = IPN - HS1;
				HEN[0] = FPN - IPN;
				HED[0] = HED[0] + (S1 - FPN);
			} else if ((IPN > HE1) && (IPN < S1) && (S1 < HE2) && (FPN > S1)
					&& (FPN < HE2)) {
				HED[0] = IPN - HS1;
				HEN[0] = S1 - IPN;
			} else if ((IPN < HS1) && (S1 > HS1) && (S1 <= HE2) && (FPN > S1)) {
				HEN[0] = S1 - HS1;
			} else if ((HE1 < IPN) && (FPN > HE2) && (S1 < HE2) && (S1 > HS1)
					&& ((E2 - S1) < (HE2 - HS1 - TolS1ANT))
					&& ((TolInterAnt == 0) && (TolInterDep == 0)))
			// coloquei aqui para bao calcular qdo for flex o almoco
			{
				HED[0] = HED[0] + (S1 - HS1);
			} // ((HE2-HS1)- (E2-S1));

		} // {(S1>HS1) && (S1<HE2) && (E2>HE2)}

		// HE1 HS1 HE2 HS2
		// ----------|--------|------===|---------|-----
		// E2
		//
		// neste caso podemos ter o S1 q saiu atrasado ou seja S1>HS1, entao
		// temos que somar
		// HE anteriores calculadas
		if ((E2 > HS1) && (E2 <= HE2)) {
			if ((IPN < HS1) && (FPN < S1) && (FPN > HS1)
					&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))) {
				HEN[0] = HEN[0] + (FPN - E2);
				HED[0] = HED[0] + (HE2 - FPN);
			} else if ((IPN < E2) && (FPN > HE2)
					&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))) {
				HEN[0] = HEN[0] + ((HE2 - HS1 - TolE2ANT) - (E2 - S1));
			} else if ((IPN > E2) && (IPN < HE2) && (FPN < HE2)
					&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))) {
				HED[0] = HED[0] + (IPN - E2);
				HEN[0] = HEN[0] + (HE2 - IPN);
			} else if ((IPN > HE2) && (FPN > HE2)
					&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))
					&& ((E2 < HE2 - TolE2ANT) || (S1 > HS1 + TolS1DEP))
					&& (E2 > HS1)) {
				if ((E2 - S1) < ((HE2 - HS1) - TolE2DEP)) {
					HED[0] = HED[0] + (((HE2 - HS1) - TolE2DEP) - (E2 - S1));
				} else if ((IPN < E2) && (FPN < E2)
						&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))) {
					HED[0] = HED[0] + (HE2 - E2);
				} else if ((IPN > S2) && (FPN < E1)
						&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))) {
					if ((E2 - S1) < (HE2 - HS1)) {
						HED[0] = HED[0] + (HE2 - HS1) - (E2 - S1);
					}
				} else if ((S1 < IPN) && (S2 > FPN) && (HS2 < FPN)) {
					HED[0] = HED[0] + (S2 - HS2);
					if (HED[0] >= 1440) {
						HED[0] = HED[0] - 1440;
					}
				}
			} // {(E2>HS1) && (E2<HE2)}
			else {
				// flexibilidade intervalo
				// if (S1>HS1-TolInterAnt)&&(S1<HE2+TolInterAnt)
				// &&((E2-S1)<(HE2-HS1-TolE2ANT))

				auxHE1 = HE1;
				auxHS1 = HS1;
				auxhe2 = HE2;
				auxhs2 = HS2;

				auxE1 = E1;
				auxS1 = S1;
				auxe2 = E2;
				auxs2 = S2;

				if (auxHS1 < auxHE1) {
					auxHS1 = auxHS1 + 1440;
				}
				if (auxhe2 < auxHS1) {
					auxhe2 = auxhe2 + 1440;
				}
				if (auxhs2 < auxhe2) {
					auxhs2 = auxhs2 + 1440;
				}

				if (auxS1 < auxE1) {
					auxS1 = auxS1 + 1440;
				}
				if (auxe2 < auxS1) {
					auxe2 = auxe2 + 1440;
				}
				if (auxs2 < auxe2) {
					auxs2 = auxs2 + 1440;
				}

				if ((auxe2 - auxS1) < (auxhe2 - auxHS1 - TolE2ANT)) {
					if (((auxhe2 - auxHS1) - (auxe2 - auxS1)) > (TolE2ANT + TolS1ANT)) // coloquei
																						// aqui
																						// di
																						// ssantini
					{
						HED[0] = HED[0] + ((auxhe2 - auxHS1) - (auxe2 - auxS1));
					}
				}
			}

			// HE1 HS1 HE2 HS2
			// ----------|--------|--------|---------|-----
			// S2
			//
			if (S2 > HS2) {
				if ((IPN < HS2) && (FPN < HS2)) {
					HEN[0] = HEN[0] + (S2 - HS2);
				} else if ((IPN < HS2) && (FPN < HS2) && (FPN < S2)) {
					HEN[0] = HEN[0] + (FPN - HS2);
					HED[0] = HED[0] + (S2 - FPN);
				} else if ((IPN > HS2) && (FPN > HS2) && (S2 > FPN)) {
					HEN[0] = HEN[0] + (S2 - IPN);
					HED[0] = HED[0] + (IPN - HS2);
				} else if ((IPN > HS2) && (S2 > HS2 + TolS2DEP) && (FPN > S2)
						&& (IPN < S2)) {
					HED[0] = HED[0] + (IPN - HS2);
					HEN[0] = HEN[0] + (S2 - IPN);

				} else if ((IPN > HS2) && (S2 > HS2 + TolS2DEP)) {
					// if s2 >= 1440 FPN = FPN-1440;
					if ((S2 <= FPN) && (S2 <= IPN)) {
						HED[0] = HED[0] + (S2 - HS2);
					} else {
						HEN[0] = HEN[0] + (S2 - HS2);
					}

				} else if ((S2 > IPN) && (S2 < FPN) && (E2 < IPN)) {
					HEN[0] = S2 - HS2;
				} else if (S2 > (HS2 + TolS2DEP)) {
					HEN[0] = S2 - HS2;
				}

			} // {(S2>HS2)}

		}
	}// HEFlex_4Bat_marcaIntervalo

	private void HN_com_Tolintervalo(int E1, int S1, int E2, int S2, int HE1,
			int HS1, int HE2, int HS2, int TolE1ANT, int TolE1DEP,
			int TolS1ANT, int TolS1DEP, int TolE2ANT, int TolE2DEP,
			int TolS2ANT, int TolS2DEP, Integer[] H_Normais, int descontainterv) {
		int QtdHsRefeicao;

		// HE1 HS1 HE2 HS2
		// ----------|--------|--------|---------|-----
		// E1 S1
		//

		QtdHsRefeicao = 0;

		if ((S1 > HS2) && ((HE2 != 0) && (HS2 != 0)))
			S1 = HS2;
		if (E1 > HS1 && E1 < HE2)
			E1 = HE2; // se esta dentro intervalo
		if ((S1 > HS1) && (S1 < HE2) && (S2 != 0) && (E2 != 0))
			S1 = HS1; // se esta dentro intervalo
		if ((S1 > HS1) && ((HE2 == 0) && (HS2 == 0)))
			S1 = HS1; //

		if ((E1 < HE1 - TolE1ANT) && ((HE2 == 0) && (HS2 == 0)))
			E1 = HE1; // VAI PAGAR HE DO RESTASNTE

		if ((((E1 <= HS1) && (S1 >= HS2)) || ((S1 >= HE2) && (E1 < HS1)) || ((E1 <= HS1) && (S1 >= HE2)))
				&& ((HE2 != 0) && (HS2 != 0))) // somente se nao for 4 bat
			QtdHsRefeicao = HE2 - HS1;
		// else QtdHsRefeicao =0 ;

		if ((descontainterv > 0) && ((HE2 == 0) && (HS2 == 0))
				&& ((S1 - E1) > 300)) // se desconta intervalo para duas marc. >
										// 0
			QtdHsRefeicao = descontainterv;

		H_Normais[0] = H_Normais[0] + S1 - E1 - QtdHsRefeicao;
	} // HN_com_Tolintervalo

	private void HNFixo4Bat_comTolIntervalo(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP,
			int TolInterAnt, int TolInterDep, int HE1, int HS1, int HE2,
			int HS2, Integer[] H_Normais) {
		int QtdHsRefeicao;

		// Verifica se S1 dentro da toler�ncia de Intervalo
		QtdHsRefeicao = HE2 - HS1;

		// trata E1
		if ((S1 > (HS1 - TolInterAnt - 1)) && (S1 < (HE2 + TolInterDep + 1))) {
			if (E1 < HE1 - TolE1ANT - 1)
				H_Normais[0] = H_Normais[0] + S1 - HE1;
			else
				H_Normais[0] = H_Normais[0] + S1 - E1;
		} else {
			if (S1 < (HS1 - TolInterAnt))
				H_Normais[0] = H_Normais[0] + S1 - E1; // Se menor que faixa de
														// toler�ncia
			else if (S1 > HE2 && S1 < HS2)
				H_Normais[0] = H_Normais[0] + S1 - E1;
			else
				H_Normais[0] = H_Normais[0] + HS1 - E1; // Se maior que faixa de
														// toler�ncia

		}

		// trata E2
		if ((E2 > (HS1 - TolInterAnt - 1)) && (E2 < (HE2 + TolInterDep + 1))) {
			if ((S1 > (HS1 - TolInterAnt - 1))
					&& (S1 < (HE2 + TolInterDep + 1))) {
				if ((E2 < S1 + QtdHsRefeicao - TolE2ANT))
					E2 = S1 + QtdHsRefeicao;

				if (S2 >= HS2) // se maior que a saida normal
					H_Normais[0] = H_Normais[0] + HS2 - E2;
				else
					H_Normais[0] = H_Normais[0] + S2 - E2;

			} else
				H_Normais[0] = H_Normais[0] + S2 - E2;
		} else {
			if (E2 < (HE2 - TolE1ANT)) // E2<(HE2-TolInterAnt)
			{
				if ((S1 > (HS1 - TolE1ANT - 1)) && (S1 < (HE2 + TolE1DEP + 1)))
					H_Normais[0] = H_Normais[0] + S2 - S1 + QtdHsRefeicao; // Se
																			// menor
																			// que
																			// faixa
																			// de
																			// toler�ncia
				else
					H_Normais[0] = H_Normais[0] + S2 - HE2 - TolInterAnt; // ????
																			// TolInterAnt
			} else {
				if ((S2 > (HS2 + TolE2DEP + 1)) && (E2 > (HS2 + TolE2DEP + 1)))
					H_Normais[0] = H_Normais[0] + HS2 - HE2;
				else
					H_Normais[0] = H_Normais[0] + S2 - E2;
			}
		}
	} // fim HNFixo4Bat_comTolIntervalo

	// calculo das hn com 4 bat e sem tolerancia intervalo.
	private void HN_4bat_sem_Tolintervalo(int E1, int S1, int E2, int S2,
			int HE1, int HS1, int HE2, int HS2, Integer[] H_Normais) {
		if (HE2 != 0 && HS2 != 0) {
			// trata as duas primeiras marcacoes
			if (E1 <= HS1) // E1 < que In�cio do Intervalo
			{ // C�lculo em HE1
				if ((S1 > HS1) && (S1 < HE2))
					S1 = HS1;
				if (S1 >= HE2) {
					// HN = Horas antes do intervalo
					H_Normais[0] = H_Normais[0] + HS1 - E1;
					// E1 tem seu in�cio em Hor�rio ap�s intervalo
					E1 = HE2;
					H_Normais[0] = H_Normais[0] + (S1 - E1);
				} else {
					if (E1 >= HE1)
						H_Normais[0] = H_Normais[0] + (S1 - E1);
					else
						H_Normais[0] = H_Normais[0] + (S1 - HE1);
				} // } se E1 < que In�cio do Intervalo
			} else // Se E1> In�cio do Intervalo
			{
				if (S1 >= HE2) {
					// E1 tem seu in�cio em Hor�rio ap�s intervalo
					E1 = HE2;
					H_Normais[0] = H_Normais[0] + (S1 - E1);
				}
			} // Se E1> In�cio do Intervalo

			// trata as duas marcacoes seguintes
			if (E2 <= HS1) // E2 < que In�cio do Intervalo
			{ // C�lculo em HE2
				if ((S2 > HS1) && (S2 < HE2))
					S2 = HS1;
				if (S2 >= HE2) {
					// HN = Horas antes do intervalo
					H_Normais[0] = H_Normais[0] + HS1 - E2;
					// E2 tem seu in�cio em Hor�rio ap�s intervalo
					E2 = HE2;
					H_Normais[0] = H_Normais[0] + (S2 - E2);
				} else
					H_Normais[0] = H_Normais[0] + (S2 - E2);
			} // se E2 < que In�cio do Intervalo
			else // E2> In�cio do Intervalo
			{
				if (E2 < HE2)
					E2 = HE2;
				if (S2 > HS2) {
					// E2 tem seu in�cio em Hor�rio ap�s intervalo
					S2 = HS2;
					H_Normais[0] = H_Normais[0] + (S2 - E2);
				} else
					H_Normais[0] = H_Normais[0] + (S2 - E2);
			} // E2> In�cio do Intervalo
		} else if (HE2 == 0 && HS2 == 0) {
			if ((HS1 - HE1) > ((S1 - E1) + (S2 - E2)))
				H_Normais[0] = (S1 - E1) + (S2 - E2);
			else
				H_Normais[0] = (HS1 - HE1);
		}

	} // HN_4bat_sem_Tolintervalo

	private void HNN4_com_Tolintervalo(int E1, int S1, int E2, int S2, int HE1,
			int HS1, int HE2, int HS2, int TolE1ANT, int TolE1DEP,
			int TolS1ANT, int TolS1DEP, int TolE2ANT, int TolE2DEP,
			int TolS2ANT, int TolS2DEP, int AdNoturno_Ini, int AdNoturno_fim,
			Integer[] H_NormaisNoturnas, int descontainterv) {
		int IPN, FPN;

		// HE1 HS1 HE2 HS2
		// ----------|------|------|---------|-------------------
		// | |
		// IPN FPN

		IPN = 0; // inicio periodo noturno
		FPN = 0; // fim periodo noturno

		if (S1 > 1440) {
			IPN = AdNoturno_Ini;
			FPN = AdNoturno_fim + 1440;
		} else {
			IPN = AdNoturno_Ini;
			if (S2 > 1440)
				FPN = AdNoturno_fim + 1440;
			else
				FPN = AdNoturno_fim + 1440;
		}

		H_NormaisNoturnas[0] = 0;

		// E1 S1 E2 S2
		// ----------|------|------|---------|-------------------
		// | |
		// IPN FPN
		if ((E1 < IPN) && (S1 < IPN) && (E2 < IPN) && (S2 > IPN) && (S2 <= FPN)) {
			if (S2 > HS2) {
				if (HS2 < IPN)
					H_NormaisNoturnas[0] = S2 - IPN;
				if (HS2 > IPN && S2 > HS2)
					H_NormaisNoturnas[0] = HS2 - IPN;
			} else
				H_NormaisNoturnas[0] = S2 - IPN;

		}

		// E1 S1 E2 S2
		// ----------|------|------|---------|-------------------
		// | |
		// IPN FPN
		if ((E1 < IPN) && (S1 <= IPN) && (E2 > IPN) && (E2 < FPN) && (S2 > IPN)
				&& (S2 <= FPN)) {
			if ((S2 <= HS2) || (HS2 == 0))
				H_NormaisNoturnas[0] = S2 - E2;
			else
				H_NormaisNoturnas[0] = HS2 - E2;

		}

		// E1 S1 E2 S2
		// ----------|------|------|---------|-------------------
		// | |
		// IPN FPN
		if ((E1 < IPN) && (S1 > IPN) && ((S1 < FPN)) && (E2 > IPN)
				&& (E2 < FPN) && (S2 > IPN) && (S2 <= FPN)) {
			if (S2 <= HS2)
				H_NormaisNoturnas[0] = (S2 - E2) + (S1 - IPN);
			else
				H_NormaisNoturnas[0] = (HS2 - E2) + (S1 - IPN);

		}

		// E1 S1 E2 S2
		// ----------|------|------|---------|-------------------
		// | |
		// IPN FPN
		if ((E1 < IPN) && (S1 > IPN) && ((S1 < FPN)) && (E2 > IPN)
				&& (E2 < FPN) && (S2 > FPN))
			H_NormaisNoturnas[0] = (FPN - E2) + (S1 - IPN);

		// E1 S1 E2 S2
		// ----------|------|------|---------|-------------------
		// | |
		// IPN FPN
		if ((E1 < IPN) && (S1 > IPN) && (S1 < FPN) && (E2 > IPN) && (E2 > FPN)
				&& (E2 < FPN) && (S2 > IPN) && (S2 > FPN))
			H_NormaisNoturnas[0] = (S1 - IPN);

		// E1 S1 E2 S2
		// ----------|------|------|---------|-------------------
		// | |
		// IPN FPN
		if ((E1 >= IPN) && (E1 < FPN) && (S1 > IPN) && (S1 < FPN) && (E2 > IPN)
				&& (E2 < FPN) && (S2 > IPN) && (S2 < FPN)) {
			if (HS2 > S2)
				H_NormaisNoturnas[0] = (S2 - E2) + (S1 - E2);
			else
				H_NormaisNoturnas[0] = (HS2 - E2) + (S1 - E2);

		}

		// E1 S1 E2 S2
		// ----------|------|------|---------|-------------------
		// | |
		// IPN FPN
		if ((E1 > IPN) && (E1 < FPN) && (S1 > IPN) && (S1 < FPN) && (E2 > IPN)
				&& (E2 < FPN) && (S2 > FPN))
			H_NormaisNoturnas[0] = (FPN - E2) + (S1 - E1);

		// E1 S1 E2 S2
		// ----------|------|------|---------|-------------------
		// | |
		// IPN FPN
		if ((E1 > IPN) && (E1 < FPN) && (S1 > IPN) && (S1 < FPN) && (E2 > FPN)
				&& (S2 > FPN))
			H_NormaisNoturnas[0] = (S1 - E1);

		// E1 S1 E2 S2
		// ----------|------|------|---------|-------------------
		// | |
		// IPN FPN
		if ((E1 > IPN) && (E1 < FPN) && (S1 > IPN) && (S1 > FPN) && (E2 > FPN)
				&& (S2 > FPN))
			H_NormaisNoturnas[0] = (FPN - E1);

		if ((E1 < IPN) && (E1 < FPN) && (S1 > IPN) && (S1 <= FPN) && (E2 > IPN)
				&& (E2 < FPN) && (S2 <= FPN))
			H_NormaisNoturnas[0] = (S2 - IPN) - (E2 - S1);

	} // * HNN4_com_Tolintervalo

	private void HNN2_com_Tolintervalo(int E1, int S1, int E2, int S2, int HE1,
			int HS1, int HE2, int HS2, int TolE1ANT, int TolE1DEP,
			int TolS1ANT, int TolS1DEP, int TolE2ANT, int TolE2DEP,
			int TolS2ANT, int TolS2DEP, int AdNoturno_Ini, int AdNoturno_fim,
			Integer[] H_NormaisNoturnas, int descontainterv) {
		int IPN, FPN;

		// E1 S2
		// ----------|--------------------|-------------------
		// | |
		// IPN FPN

		IPN = AdNoturno_Ini;
		FPN = AdNoturno_fim;

		if (E1 < S1) // ESTAO NO MESMO DIA
		{

			// E1 S1 0
			// -----|--------------|--||-----------------
			// | |
			// Ipn FPN
			if ((S1 > IPN) && (E1 < IPN))
				H_NormaisNoturnas[0] = S1 - IPN;

			// E1 S1 0
			// -----|--------------|--||-----------------
			// | |
			// ipn FPN
			if (S1 > IPN && E1 >= IPN)
				H_NormaisNoturnas[0] = S1 - E1;
		} // NO MESMO DIA

		if (E1 > S1) // ESTAO NO MESMO DIA
		{
			// E1 0 S1
			// -----|----------------||-|----------------
			// | |
			// IPN FPN
			if (E1 < IPN && S1 < FPN)
				H_NormaisNoturnas[0] = S1 + (1440 - IPN);

			// E1 0 S1
			// --------|----------------||-|----------------
			// | |
			// IPN FPN
			if (E1 >= IPN && S1 < FPN)
				H_NormaisNoturnas[0] = S1 + (1440 - E1);

		}// NO MESMO DIA diferentes
	} // HNN2_com_Tolintervalo

	// calculo horas normais sem tolerancia de intervalo.
	private void HN_sem_Tolintervalo(int E1, int S1, int E2, int S2, int HE1,
			int HS1, int HE2, int HS2, Integer[] H_Normais, int descontainterv) {
		int QtdHsRefeicao;

		// HE1 HS1 HE2 HS2
		// ----------|--------|--------|---------|-----
		// E1 S1
		//

		QtdHsRefeicao = 0;

		if ((S1 > HS2) && ((HE2 != 0) && (HS2 != 0)))
			S1 = HS2;
		if (E1 > HS1 && E1 < HE2)
			E1 = HE2; // se esta dentro intervalo
		if (S1 > HS1 && S1 < HE2)
			S1 = HS1; // se esta dentro intervalo
		if ((S1 > HS1) && ((HE2 == 0) && (HS2 == 0)))
			S1 = HS1; //

		if ((((E1 <= HS1) && (S1 >= HS2)) || ((S1 >= HE2) && (E1 < HS1)) || ((E1 <= HS1) && (S1 >= HE2)))
				&& ((HE2 != 0) && (HS2 != 0))) // somente se nao for 4 bat
			QtdHsRefeicao = HE2 - HS1;
		// else QtdHsRefeicao =0 ;

		if ((descontainterv > 0) && ((HE2 == 0) && (HS2 == 0))) // se desconta
																// intervalo
																// para duas
																// marc. > 0
			QtdHsRefeicao = descontainterv;
		H_Normais[0] = H_Normais[0] + S1 - E1 - QtdHsRefeicao;

	} // HN_sem_Tolintervalo

	private void HNN2_sem_Tolintervalo(int E1, int S1, int E2, int S2, int HE1,
			int HS1, int HE2, int HS2, int TolE1ANT, int TolE1DEP,
			int TolS1ANT, int TolS1DEP, int TolE2ANT, int TolE2DEP,
			int TolS2ANT, int TolS2DEP, int AdNoturno_Ini, int AdNoturno_fim,
			Integer[] H_NormaisNoturnas, int descontainterv) {
		int IPN, FPN;

		// E1 S2
		// ----------|--------------------|-------------------
		// | |
		// IPN FPN

		IPN = 0; // inicio periodo noturno
		FPN = 0; // fim periodo noturno

		IPN = AdNoturno_Ini;
		FPN = AdNoturno_fim + 1440;

		H_NormaisNoturnas[0] = 0;

		// E1 S2
		// ----------|--------------------|-------------------
		// | |
		// IPN FPN
		if (E1 < IPN && S1 < IPN)
			H_NormaisNoturnas[0] = 0;

		// E1 S2
		// ----------|--------------------|-------------------
		// | |
		// IPN FPN
		if ((E1 >= IPN) && (E1 <= FPN) && (S2 > FPN))
			H_NormaisNoturnas[0] = FPN - E1;

		// E1 S2
		// -----|--------------|-------------------
		// | |
		// IPN FPN
		if ((E1 >= IPN) && (E1 <= FPN) && (S1 > FPN))
			H_NormaisNoturnas[0] = S1 - E1;

		// E1 S1
		// -----|--------------|-------------------
		// | |
		// IPN FPN
		if ((E1 < IPN) && (S1 <= FPN) && (S1 >= IPN) && (S1 > HS1))
			H_NormaisNoturnas[0] = S1 - IPN;

		// E1 S2
		// -----|--------------|-------------------
		// | |
		// IPN FPN
		if (E1 <= IPN && S1 >= FPN)
			H_NormaisNoturnas[0] = FPN - IPN;

		// E1 S1 HS1
		// -----|-----------|-------|-------
		// | |
		// IPN FPN
		if ((E1 < IPN) && (S1 <= FPN) && (S1 >= IPN) && (S1 < HS1))
			H_NormaisNoturnas[0] = S1 - IPN;

	} // HNN2_com_Tolintervalo

	private void HEMarca_Intervalo(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP,
			int TolInterAnt, int TolInterDep, int HE1, int HS1, int HE2,
			int HS2, int AdNoturno_Ini, int AdNoturno_fim, Integer[] HED,
			Integer[] HEN) {
		int QtdHsRefeicao;

		// C�lculo de Horas Extras com hor�rio de intervalo}
		QtdHsRefeicao = HE2 - HS1;
		// Se dentro da faixa de toler�ncia ou menor que o Hor�rio de Entrada
		if (E2 != 0 && S2 != 0) // Se existe entrada ap�s intervalo
		{ // 4 batidas
			if (TolInterAnt > 0 || TolInterDep > 0) {

				HEFixo_4Bat_marcaIntervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
						TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT,
						TolS2DEP, HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep,
						AdNoturno_Ini, AdNoturno_fim, HED, HEN);
			} // se TolIntervalo >0
			else { // Se n�o existe TolIntervalo

				HEFixo_4Bat_marcaIntervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
						TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT,
						TolS2DEP, HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep,
						AdNoturno_Ini, AdNoturno_fim, HED, HEN);

			} // }IF Se n�o existe TolIntervalo
		} // } se existem 4 batidas
		else // Se existe somente duas batidas
		{
			// se bate 4 e so bateu 2
			if ((((E2 == 0) && (S2 == 0))) && (((HE2 != 0) && (HS2 != 0)))) {
				// **** fazer esta situacao, 2 marc. mas horario tem 4 marc.
				HEFixo_2Bat_semIntervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
						TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT,
						TolS2DEP, HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep,
						AdNoturno_Ini, AdNoturno_fim, HED, HEN);

			} else {
				HEFixo_2Bat_marcaIntervalo(E1, S1, E2, S2, TolE1ANT, TolE1DEP,
						TolS1ANT, TolS1DEP, TolE2ANT, TolE2DEP, TolS2ANT,
						TolS2DEP, HE1, HS1, HE2, HS2, TolInterAnt, TolInterDep,
						AdNoturno_Ini, AdNoturno_fim, HED, HEN);
			}
		} // }IF existem 2 batidas

	} // HEMarca_Intervalo

	private void HEFixo_4Bat_marcaIntervalooLD(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1,
			int HS1, int HE2, int HS2, int TolInterAnt, int TolInterDep,
			int AdNoturno_Ini, int AdNoturno_fim, Integer[] HED, Integer[] HEN) {
		int AUXHEN, AUXHEDS, IPN, FPN, H_Extras;
		int auxhe1, auxhe2, auxhs1, auxhs2;
		int auxe1, auxe2, auxs1, auxs2;

		IPN = 0; // inicio periodo noturno
		FPN = 0; // fim periodo noturno
		HED[0] = 0;
		HEN[0] = 0;

		// if s1<e1 s1 = s1+ 1440;

		if (S1 > 1440) {
			IPN = AdNoturno_Ini + 1440;
			FPN = AdNoturno_fim + 1440;
		} else {
			IPN = AdNoturno_Ini;
			if (S2 > 1440)
				FPN = AdNoturno_fim + 1440;
			else
				FPN = AdNoturno_fim + 1440;
		}

		// ajusta o horario de acordo as batidas. puxa o horario para fazer o
		// calculo

		// HE1 HS1 HE2 HS2
		// ------=====|--------|--------|---------|-----
		// E1 S1
		// IPN FPN (fim do periodo noturno
		// -----------------------------------|------------|-------

		// chegou antes da hora
		if ((HE1 > E1) && (HE1 < IPN))
			HED[0] = HE1 - E1;

		// situacao HE1 > E1 com IPN
		if (HE1 > E1) {
			//
			if ((IPN < E1) && (FPN < HE1) && (FPN > E1)) {
				HEN[0] = FPN - E1;
				HED[0] = HE1 - FPN;
			} else if ((IPN > E1) && (FPN < HE1) && (E1 < FPN)) {
				HEN[0] = IPN - E1;
				HEN[0] = FPN - E1;
			} else if ((E1 < IPN) && (FPN > HE1) && (IPN < HE1)) {
				HED[0] = IPN - E1;
				HEN[0] = HE1 - IPN;
			} else if ((IPN < E1) && (FPN < E1)) {
				HED[0] = HE1 - E1;
			} else if ((IPN > HE1) && (FPN > HE1))
				HED[0] = HE1 - E1;

		} // HE1>E1

		// HE1 HS1 HE2 HS2
		// ----------|--------|====----|---------|-----
		// S1 E2
		//

		if ((S1 > HS1) && (S1 < HE2) && (E2 >= HE2)) {
			if ((IPN < HS1) && (FPN < S1) && (FPN > HS1)) {
				HEN[0] = FPN - HS1;
			} else if ((IPN > HS1) && (FPN > HS1) && (FPN < S1) && (S1 < HE2)) {
				HED[0] = IPN - HS1;
				HEN[0] = FPN - IPN;
				HED[0] = HED[0] + (S1 - FPN);
			} else if ((IPN > HE1) && (IPN < S1) && (S1 < HE2) && (FPN > S1)
					&& (FPN < HE2)) {
				HED[0] = IPN - HS1;
				HEN[0] = S1 - IPN;
			} else if ((IPN < HS1) && (S1 > HS1) && (S1 <= HE2) && (FPN > S1)) {
				HEN[0] = S1 - HS1;
			} else if ((HE1 < IPN) && (FPN > HE2) && (S1 < HE2) && (S1 > HS1)
					&& ((E2 - S1) < (HE2 - HS1 - TolS1ANT))
					&& ((TolInterAnt == 0) && (TolInterDep == 0)))
				// coloquei aqui para bao calcular qdo for flex o almoco
				HED[0] = HED[0] + (S1 - HS1); // ((HE2-HS1)- (E2-S1));

		} // (S1>HS1) && (S1<HE2) && (E2>HE2)
		else {
		}

		// HE1 HS1 HE2 HS2
		// ----------|--------|------===|---------|-----
		// E2
		//
		// neste caso podemos ter o S1 q saiu atrasado ou seja S1>HS1, entao
		// temos que somar
		// HE anteriores calculadas
		if ((E2 > HS1) && (E2 <= HE2)) {
			if ((IPN < HS1) && (FPN < S1) && (FPN > HS1)
					&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))) {
				HEN[0] = HEN[0] + (FPN - E2);
				HED[0] = HED[0] + (HE2 - FPN);
			} else if ((IPN < E2) && (FPN > HE2)
					&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))) {
				HEN[0] = HEN[0] + (HE2 - E2);
			} else if ((IPN > E2) && (IPN < HE2) && (FPN < HE2)
					&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))) {
				HED[0] = HED[0] + (IPN - E2);
				HEN[0] = HEN[0] + (HE2 - IPN);
			} else if ((IPN > HE2) && (FPN > HE2)
					&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))
					&& ((E2 < HE2 - TolE2ANT) || (S1 > HS1 + TolS1DEP))
					&& (E2 > HS1)) {

				if (E2 < HE2 - TolE2ANT - TolS1ANT) // coloquei aqui di santini
					HED[0] = HED[0] + (HE2 - E2);

				if (S1 > HS1 + TolS1DEP)
					HED[0] = HED[0] + (S1 - HS1); // (S1-HS1) + (HE2-E2);

			} else if ((IPN < E2) && (FPN < E2)
					&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))) {
				HED[0] = HED[0] + (HE2 - E2);
			}
			// coloquei em 17/12
			else if ((IPN > S2) && (FPN < E1)
					&& ((E2 - S1) < (HE2 - HS1 - TolE2ANT))) {
				if ((E2 - S1) < (HE2 - HS1))
					HED[0] = HED[0] + (HE2 - HS1) - (E2 - S1);
			} else if ((S1 < IPN) && (S2 > FPN) && (HS2 < FPN) && (S2 > HS2)
					&& (S2 < 1440)) {
				HED[0] = HED[0] + (S2 - HS2);
				if (HED[0] >= 1440)
					HED[0] = HED[0] - 1440;
			}

		} // (E2>HS1) && (E2<HE2)
		else
		// flexibilidade intervalo
		// if (S1>HS1-TolInterAnt)&&(S1<HE2+TolInterAnt)
		// &&((E2-S1)<(HE2-HS1-TolE2ANT))
		{

			auxhe1 = HE1;
			auxhs1 = HS1;
			auxhe2 = HE2;
			auxhs2 = HS2;

			auxe1 = E1;
			auxs1 = S1;
			auxe2 = E2;
			auxs2 = S2;

			if (auxhs1 < auxhe1)
				auxhs1 = auxhs1 + 1440;
			if (auxhe2 < auxhs1)
				auxhe2 = auxhe2 + 1440;
			if (auxhs2 < auxhe2)
				auxhs2 = auxhs2 + 1440;

			if (auxs1 < auxe1)
				auxs1 = auxs1 + 1440;
			if (auxe2 < auxs1)
				auxe2 = auxe2 + 1440;
			if (auxs2 < auxe2)
				auxs2 = auxs2 + 1440;

			if ((auxe2 - auxs1) < (auxhe2 - auxhs1 - TolE2ANT)) {
				if (((auxhe2 - auxhs1) - (auxe2 - auxs1)) > (TolE2ANT + TolS1ANT)) // coloquei
																					// aqui
																					// di
																					// ssantini
					HED[0] = HED[0] + (auxhe2 - auxhs1) - (auxe2 - auxs1);
			}
		}

		// HE1 HS1 HE2 HS2
		// ----------|--------|--------|---------|-----
		// S2
		//
		if (S2 > HS2) {
			if ((IPN < HS2) && (FPN < HS2)) {
				HEN[0] = HEN[0] + (S2 - HS2);

			} else if ((IPN < HS2) && (FPN < HS2) && (FPN < S2)) {
				HEN[0] = HEN[0] + (FPN - HS2);
				HED[0] = HED[0] + (S2 - FPN);
			} else if ((IPN > HS2) && (FPN > HS2) && (S2 > FPN)) {
				HEN[0] = HEN[0] + (S2 - IPN);
				HED[0] = HED[0] + (IPN - HS2);

			} else if ((IPN > HS2) && (S2 > HS2 + TolS2DEP) && (FPN > S2)
					&& (IPN < S2)) {
				HED[0] = HED[0] + (IPN - HS2);
				HEN[0] = HEN[0] + (S2 - IPN);
			} else if ((IPN > HS2) && (S2 > HS2 + TolS2DEP)) {
				if (S2 >= 1440)
					FPN = FPN - 1440;
				if (S2 <= FPN && S2 <= IPN)
					HED[0] = HED[0] + (S2 - HS2);
				else
					HEN[0] = HEN[0] + (S2 - HS2);

			} else if ((S2 > IPN) && (S2 < FPN) && (E2 < IPN))
				HEN[0] = S2 - HS2;
			else if (S2 > (HS2 + TolS2DEP))
				HEN[0] = S2 - HS2;

		} // (S2>HS2)

		// IPN FPN
		// ----------------------------------|-----------|-----
		// HE1 HS1 HE2 HS2
		// ----------|--------|--------|---------|--------------
		// --------|-----------------------------------------|--
		// S1 S2

	}// HEFixo_4Bat_marcaIntervalo

	private void HEFixo_4Bat_marcaIntervalo(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1,
			int HS1, int HE2, int HS2, int TolInterAnt, int TolInterDep,
			int AdNoturno_Ini, int AdNoturno_fim, Integer[] HED, Integer[] HEN) {
		int AUXHEN, AUXHEDS, IPN, FPN, H_Extras;
		int auxHE1, auxhe2, auxHS1, auxhs2;
		int auxE1, auxe2, auxS1, auxs2;

		IPN = 0; // inicio periodo noturno
		FPN = 0; // fim periodo noturno
		HED[0] = 0;
		HEN[0] = 0;

		if (S1 > 1440) {

			if (E1 >= 1440) {
				IPN = AdNoturno_Ini + 1440;
			} else {
				IPN = AdNoturno_Ini;
			}

			if ((E1 >= 1440) && (S1 >= 1440)) {
				IPN = AdNoturno_Ini + 1440;
			} else {
				IPN = AdNoturno_Ini;
			}

			if ((E1 >= 1440) && (S1 >= 1440) && (E2 >= 1440)) {
				IPN = AdNoturno_Ini + 1440;
			} else {
				IPN = AdNoturno_Ini;
			}

			FPN = AdNoturno_fim + 1440;
		} else {
			IPN = AdNoturno_Ini;
			if (S2 > 1440) {
				FPN = AdNoturno_fim + 1440;
			} else {
				FPN = AdNoturno_fim + 1440;
			}
		}

		Integer HEDiurna[] = { 0 }, HENoturna[] = { 0 };

		// Condição 1
		if ((E1 < HE1) && (S1 < HE1) && (E2 < HE2) && (S2 <= (HE1 - TolE1ANT))) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];

			if ((E2 < HE1) && (S2 < HE1) && (E2 <= HE2)
					&& (S2 <= (HE1 - TolE1ANT))) {
				calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
				HED[0] += HEDiurna[0];
				HEN[0] += HENoturna[0];
			}
			return;
		}// 1

		// Condição 2
		if ((E1 < HE1) && (S1 < HE1) && (E2 < HE1) && (S2 <= (HE1 - TolE1ANT))) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];

			if ((E2 < HE1) && (S2 < HE1) && (E2 <= HE1) && (S2 > (HE1))) {
				calculaHEDHNN(IPN, FPN, E2, HE1, HEDiurna, HENoturna);
				HED[0] += HEDiurna[0];
				HEN[0] += HENoturna[0];
			}
			return;
		}// 2

		// Condição 3
		if ((E1 < HE1) && (S1 < HE1) && (E2 < HE1) && (S2 <= (HE1 - TolE1ANT))) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];

			if ((E2 < HE1) && (S2 > HE1) && (S2 > HS1) && (S2 <= HE2)) {
				calculaHEDHNN(IPN, FPN, E2, HE1, HEDiurna, HENoturna);
				HED[0] += HEDiurna[0];
				HEN[0] += HENoturna[0];
				calculaHEDHNN(IPN, FPN, HS1, S2, HEDiurna, HENoturna);
				HED[0] += HEDiurna[0];
				HEN[0] += HENoturna[0];
			}
			return;
		}// 3

		// Condição 4
		if ((E1 < HE1) && (S1 < HE1) && (E2 < HE2) && (S2 > (HE1 - TolE1ANT))) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}

		// Condição 5
		if ((E1 < HE1) && (S1 < HE1) && (E2 <= HE1) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, HE1, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;

		}// 5

		// Condição 6
		if ((E1 < HE1) && (S1 <= HE1) && (E2 >= HE1) && (E2 < HS1)
				&& (S2 > HE1) && (S2 <= HS1)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 6

		// Condição 7
		if ((E1 < HE1) && (S1 <= HE1) && (E2 >= HE1) && (E2 <= HS1)
				&& (S2 >= HS1) && (S2 <= HE2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 7

		// Condição 8
		if ((E1 < HE1) && (S1 <= HE1) && (E2 >= HE1) && (E2 <= HS1)
				&& (S2 >= HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 8

		// Condição 9
		if ((E1 < HE1) && (S1 <= HE1) && (E2 >= HE1) && (E2 <= HS1)
				&& (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 9

		// Condição 10
		if ((E1 < HE1) && (S1 <= HE1) && (E2 >= HS1) && (E2 <= HE2)
				&& (S2 >= HS1) && (S2 <= HE2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 10

		// Condição 11
		if ((E1 < HE1) && (S1 <= HE1) && (E2 >= HS1) && (E2 <= HE2)
				&& (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 11

		// Condição 12
		if ((E1 < HE1) && (S1 <= HE1) && (E2 >= HS1) && (E2 <= HE2)
				&& (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 12

		// Condição 13
		if ((E1 < HE1) && (S1 <= HE1) && (E2 >= HE2) && (E2 <= HS2)
				&& (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 13

		// Condição 14
		if ((E1 < HE1) && (S1 <= HE1) && (E2 >= HE2) && (E2 <= HS2)
				&& (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 14

		// Condição 15
		if ((E1 < HE1) && (S1 <= HE1) && (E2 >= HE2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 15

		// Condição 16
		if ((E1 < HE1) && (S1 >= HE1) && (S1 < HS1) && (E2 > HE1) && (E2 < HS1)
				&& (S2 > HE1) && (S2 <= HS1)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 16

		// Condição 17
		if ((E1 < HE1) && (S1 >= HE1) && (S1 < HS1) && (E2 > HE1) && (E2 < HS1)
				&& (S2 > HS1) && (S2 <= HE2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 17

		// Condição 18
		if ((E1 < HE1) && (S1 >= HE1) && (S1 < HS1) && (E2 > HE1) && (E2 < HS1)
				&& (S2 >= HE2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 18

		// Condição 19
		if ((E1 < HE1) && (S1 >= HE1) && (S1 < HS1) && (E2 > HE1) && (E2 < HS1)
				&& (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 19

		// Condição 20
		if ((E1 < HE1) && (S1 >= HE1) && (S1 < HS1) && (E2 > HS1) && (E2 < HE2)
				&& (S2 > HS1) && (S2 <= HE2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 20

		// Condição 21
		if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HS1)
				&& (E2 <= HE2) && (S2 >= HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 21

		// Condição 22
		if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HS1)
				&& (E2 <= HE2) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 22

		// Condição 23
		if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HE2)
				&& (E2 <= HS2) && (S2 >= HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 23

		// Condição 24
		if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HE2)
				&& (E2 <= HS2) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 24

		// Condição 25
		if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HS2)
				&& (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 25

		// Condição 26
		if ((E1 < HE1) && (S1 >= HS1) && (S1 <= HE2) && (E2 > HS1)
				&& (E2 < HE2) && (S2 > HS1) && (S2 <= HE2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 26

		// Condição 27
		if ((E1 <= HE1) && (S1 >= HS1) && (S1 <= HE2) && (E2 > HS1)
				&& (E2 <= HE2) && (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E1, E2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 27

		// Condição 28
		if ((E1 <= HE1) && (S1 > HS1) && (S1 <= HE2) && (E2 > HS1)
				&& (E2 <= HE2) && (S2 > HE2) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, S1, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 28

		// Condição 29
		if ((E1 <= HE1) && (S1 > HS1) && (S1 <= HE2) && (E2 > HE2)
				&& (E2 <= HS2) && (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, S1, E2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 29

		// Condição 30
		if ((E1 <= HE1) && (S1 > HS1) && (S1 <= HE2) && (E2 > HE2)
				&& (E2 < HS2) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, S1, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];

			return;
		}// 30

		// Condição 31
		if ((E1 <= HE1) && (S1 > HS1) && (S1 <= HE2) && (E2 >= HS2)
				&& (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, S1, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 31

		// Condição 32
		if ((E1 <= HE1) && (S1 >= HE2) && (S1 <= HS2) && (E2 > HE2)
				&& (E2 < HS2) && (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 32

		// Condição 33
		if ((E1 <= HE1) && (S1 >= HE2) && (S1 <= HS2) && (E2 > HE2)
				&& (E2 < HS2) && (S2 >= HE2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, E2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 33

		// Condição 34
		if ((E1 <= HE1) && (S1 >= HE2) && (S1 <= HS2) && (E2 >= HS2)
				&& (S2 > HE2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 34

		// Condição 35
		if ((E1 <= HE1) && (S1 >= HS2) && (E2 > HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S1, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
		}// 35

		// Condição 36
		if ((E1 >= HE1) && (E1 <= HS1) && (S1 > HE1) && (S1 < HS1)
				&& (E2 > HE1) && (E2 < HS1) && (S2 > HE1) && (S2 <= HS1)) {
			return;
		}// 36

		// Condição 37
		if ((E1 >= HE1) && (E1 <= HS1) && (S1 > HE1) && (S1 < HS1)
				&& (E2 > HE1) && (E2 < HS1) && (S2 >= HE1) && (S2 <= HE2)) {
			calculaHEDHNN(IPN, FPN, HS1, S2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 37

		// Condição 38
		if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1) && (E2 > HE1)
				&& (E2 <= HS1) && (S2 >= HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 38

		// Condição 39
		if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1) && (E2 > HE1)
				&& (E2 <= HS1) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 39

		// Condição 40
		if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
				&& (E2 >= HS1) && (E2 < HE2) && (S2 > HS1) && (S2 <= HE2)) {
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 40

		// Condição 41
		if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 <= HS1)
				&& (E2 >= HS1) && (E2 <= HE2) && (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E2, HE2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 41

		// Condição 42
		if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
				&& (E2 >= HS1) && (E2 <= HE2) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, E2, HE2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 42

		// Condição 43
		if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
				&& (E2 >= HE2) && (E2 < HS2) && (S2 > HE2) && (S2 <= HS2)) {
			return;
		}// 43

		// Condição 44
		if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
				&& (E2 >= HE2) && (E2 <= HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 44

		// Condição 45
		if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
				&& (E2 >= HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 45

		// Condição 46
		if ((E1 >= HE1) && (E1 < HS1) && (S1 > HS1) && (S1 < HE2) && (E2 > HS1)
				&& (E2 < HE2) && (S2 > HS1) && (S2 <= HE2)) {
			calculaHEDHNN(IPN, FPN, HS1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 46

		// Condição 47
		if ((E1 >= HE1) && (E1 < HS1) && (S1 > HS1) && (S1 < HE2) && (E2 > HS1)
				&& (E2 <= HE2) && (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E2, HE2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 47

		// Condição 48
		if ((E1 >= HE1) && (E1 < HS1) && (S1 >= HS1) && (S1 < HE2)
				&& (E2 > HS1) && (E2 <= HE2) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, HS1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 48

		// Condição 49
		if ((E1 >= HE1) && (E1 < HS1) && (S1 >= HS1) && (S1 <= HE2)
				&& (E2 > HE2) && (E2 < HS2) && (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, HS1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 49

		// Condição 50
		if ((E1 >= HE1) && (E1 < HS1) && (S1 >= HS1) && (S1 <= HE2)
				&& (E2 > HE2) && (E2 < HS2) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, HS1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 50

		// Condição 51
		if ((E1 >= HE1) && (E1 < HS1) && (S1 >= HS1) && (S1 <= HE2)
				&& (E2 >= HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, HS1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 51

		// Condição 52
		if ((E1 >= HE1) && (E1 < HS1) && (S1 >= HE2) && (S1 < HS2)
				&& (E2 > HE2) && (E2 < HS2) && (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 52

		// Condição 53
		if ((E1 >= HE1) && (E1 <= HS1) && (S1 >= HE2) && (S1 < HS2)
				&& (E2 > HE2) && (E2 < HS2) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 53

		// Condição 54
		if ((E1 >= HE1) && (E1 <= HS1) && (S1 >= HE2) && (S1 < HS2)
				&& (E2 >= HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 54

		// Condição 55
		if ((E1 >= HE1) && (E1 <= HS1) && (S1 >= HS2) && (E2 > HS2)
				&& (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, HS1, HE2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S1, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 55

		// Condição 57
		if ((E1 >= HS1) && (E1 <= HE2) && (S1 > HS1) && (S1 < HE2)
				&& (E2 > HS1) && (E2 < HE2) && (S2 > HS1) && (S2 <= HE2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 57

		// Condição 58
		if ((E1 >= HS1) && (E1 < HE2) && (S1 > HS1) && (S1 < HE2) && (E2 > HS1)
				&& (E2 <= HE2) && (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 58

		// Condição 59
		if ((E1 >= HS1) && (E1 < HE2) && (S1 > HS1) && (S1 < HE2) && (E2 > HS1)
				&& (E2 <= HE2) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, HE2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 59

		// Condição 60
		if ((E1 >= HS1) && (E1 < HE2) && (S1 > HS1) && (S1 < HE2) && (E2 > HE2)
				&& (E2 <= HS2) && (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 60

		// Condição 61
		if ((E1 >= HS1) && (E1 < HE2) && (S1 > HS1) && (S1 <= HE2)
				&& (E2 > HE2) && (E2 <= HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 61

		// Condição 62
		if ((E1 >= HS1) && (E1 < HE2) && (S1 > HS1) && (S1 <= HE2)
				&& (E2 >= HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 62

		// Condição 63
		if ((E1 >= HS1) && (E1 < HE2) && (S1 >= HE2) && (S1 < HS2)
				&& (E2 > HE2) && (E2 < HS2) && (S2 > HE2) && (S2 <= HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 63

		// Condição 64
		if ((E1 >= HS1) && (E1 <= HE2) && (S1 > HE2) && (S1 < HS2)
				&& (E2 > HE2) && (E2 <= HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HS2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 64

		// Condição 65
		if ((E1 >= HS1) && (E1 <= HE2) && (S1 > HE2) && (S1 < HS2)
				&& (E2 >= HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 65

		// Condição 66
		if ((E1 >= HS1) && (S1 <= HE2) && (S1 >= HS2) && (S1 < HS2)
				&& (E2 > HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, HE2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, HS2, S1, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 66

		// Condição 67
		if ((E1 >= HE2) && (E1 <= HS2) && (S1 > HE2) && (S1 < HS2)
				&& (E2 > HE2) && (E2 < HS2) && (S2 > HE2) && (S2 <= HS2)) {
			return;
		}// 67

		// Condição 68
		if ((E1 >= HE2) && (E1 <= HS2) && (S1 > HE2) && (S1 < HS2)
				&& (E2 > HE2) && (E2 < HS2) && (S2 >= HS2)) {
			calculaHEDHNN(IPN, FPN, HS2, S2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 68

		// Condição 69
		if ((E1 >= HE2) && (E1 <= HS2) && (S1 > HE2) && (S1 < HS2)
				&& (E2 >= HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			return;
		}// 69

		// Condição 70
		if ((E1 >= HE2) && (E1 <= HS2) && (S1 > HS2) && (E2 > HS2)
				&& (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, HS2, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 70

		// Condição 71
		if ((E1 >= HS2) && (S1 > HS2) && (E2 > HS2) && (S2 > HS2)) {
			calculaHEDHNN(IPN, FPN, E1, S1, HEDiurna, HENoturna);
			HED[0] = HEDiurna[0];
			HEN[0] = HENoturna[0];
			calculaHEDHNN(IPN, FPN, E2, S2, HEDiurna, HENoturna);
			HED[0] += HEDiurna[0];
			HEN[0] += HENoturna[0];
			return;
		}// 71

	} // HEFIXO_4Bat_marcaIntervalo

	public void calculaHEDHNN(Integer IPN, Integer FPN, Integer E, Integer S,
			Integer[] HED, Integer[] HEN) {

		if ((E < IPN) && (S <= FPN)) {
			HED[0] = S - E;
		}
		if ((E <= IPN) && (S > IPN) && (S <= FPN)) {
			HED[0] = IPN - E;
			HEN[0] = S - IPN;
		}
		if ((E < IPN) && (S >= FPN)) {
			HED[0] = IPN - E;
			HEN[0] = FPN - IPN;
			HED[0] += S - FPN;
		}
		if ((E >= IPN) && (E < FPN) && (S < FPN) && (S > IPN)) {
			HEN[0] = S - E;
		}
		if ((E > IPN) && (E < FPN) && (S >= FPN)) {
			HEN[0] = FPN - E;
			HED[0] = S - FPN;
		}
		if ((E >= FPN) && (S > FPN)) {
			HED[0] = S - E;
		}

	}// calculaHEDHNN

	private void Calcula_AtrasoSaidaAntFX(int E1, int S1, int E2, int S2,
			int TolE1ANT, int TolE1DEP, int TolS1ANT, int TolS1DEP,
			int TolE2ANT, int TolE2DEP, int TolS2ANT, int TolS2DEP, int HE1,
			int HS1, int HE2, int HS2, int TolInterAnt, int TolInterDep,
			Integer[] Atraso1, Integer[] Atraso2, Integer[] SaidaAnt1,
			Integer[] SaidaAnt2, int Usaquatromarc, int descontainterv) {
		int QtdHsRefeicao;

		// 4 marcacoes 4 horario
		if ((HE1 != 0) && (HS1 != 0) && (HE2 != 0) && (HS2 != 0) && (E1 != 0)
				&& (S1 != 0) && (E2 != 0) && (S2 != 0)) {

			// 1
			if ((E1 < HE1) && (S1 < HE1) && (E2 < HE1) && (S2 <= HE1)) {
				SaidaAnt1[0] = HS1 - HE1;
				SaidaAnt2[0] = HS2 - HE2;
			}
			// 2
			if ((E1 < HE1) && (S1 < HE1) && (E2 < HE1) && (S2 > HE1)
					&& (S2 <= HS1)) {
				SaidaAnt1[0] = HS1 - HE1;
				SaidaAnt2[0] = HS2 - HE2;
			}
			// 3
			if ((E1 < HE1) && (S1 < HE1) && (E2 < HE1) && (S2 > HS1)
					&& (S2 <= HE2)) {
				SaidaAnt1[0] = HS1 - HE2;
				SaidaAnt2[0] = HS2 - HE2;
			}
			// 4
			if ((E1 < HE1) && (S1 < HE1) && (E2 < HE1) && (S2 > HE2)
					&& (S2 < HS2)) {
				SaidaAnt1[0] = HS1 - HE2;
				SaidaAnt2[0] = HS2 - S2;
			}
			// 5
			if ((E1 < HE1) && (S1 < HE1) && (E2 < HE1) && (S2 >= HS2)) {
				SaidaAnt1[0] = HS1 - HE2;
			}
			// 6
			if ((E1 < HE1) && (S1 < HE1) && (E2 >= HE1) && (S2 <= HS1)) {
				SaidaAnt1[0] = HS1 - HE2;
				SaidaAnt2[0] = HS2 - HE2;
			}
			// 7
			if ((E1 < HE1) && (S1 < HE1) && (E2 >= HE1) && (E2 <= HS1)
					&& (S2 >= HS1) && (S2 < HE2)) {
				SaidaAnt1[0] = HS1 - HE2;
				SaidaAnt2[0] = HS2 - HE2;
			}
			// 8
			if ((E1 < HE1) && (S1 < HE1) && (E2 >= HE1) && (E2 <= HS1)
					&& (S2 >= HE1) && (S2 <= HE2)) {
				SaidaAnt1[0] = HS1 - HE2;
				SaidaAnt2[0] = HS2 - S2;
			}
			// 9
			if ((E1 < HE1) && (S1 < HE1) && (E2 >= HE1) && (E2 <= HS1)
					&& (S2 >= HE2)) {
				SaidaAnt1[0] = HS1 - HE2;
			}
			// 11
			if ((E1 < HE1) && (S1 < HE1) && (E2 >= HS1) && (E2 <= HE2)
					&& (S2 >= HS1) && (S2 <= HE2)) {
				SaidaAnt1[0] = HS1 - HE2;
				SaidaAnt2[0] = HS2 - HS2;
			}
			// 12
			if ((E1 < HE1) && (S1 < HE1) && (E2 >= HS1) && (E2 <= HE2)
					&& (S2 >= HE2) && (S2 <= HS2)) {
				SaidaAnt1[0] = HS1 - HE2;
				SaidaAnt2[0] = HS2 - S2;
			}
			// 13
			if ((E1 < HE1) && (S1 < HE1) && (E2 >= HS1) && (E2 <= HE2)
					&& (S2 >= HS2)) {
				SaidaAnt1[0] = HS1 - HE2;
			}
			// 14
			if ((E1 < HE1) && (S1 < HE1) && (E2 >= HE2) && (E2 < HS2)
					&& (S2 >= HE2) && (S2 <= HS2)) {
				SaidaAnt1[0] = HS1 - HE2;
				SaidaAnt2[0] = HS2 - S2;

				Atraso2[0] = E2 - HE2;
			}
			// 15
			if ((E1 < HE1) && (S1 < HE1) && (E2 >= HE2) && (E2 < HS2)
					&& (S2 > HS2)) {
				SaidaAnt1[0] = HS1 - HE2;
				Atraso2[0] = E2 - HE2;
			}
			// 16
			if ((E1 < HE1) && (S1 < HE1) && (E2 >= HS2) && (S2 > HS2)) {
				SaidaAnt1[0] = HS1 - HE2;
				Atraso2[0] = HS2 - HE2;
			}
			// 17
			if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HE1)
					&& (E2 < HS1) && (S2 >= HE1) && (S2 <= HS1)) {
				SaidaAnt1[0] = HS1 - S1;
				SaidaAnt2[0] = HS2 - HS2;
			}
			// 18
			if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HE1)
					&& (E2 < HS1) && (S2 >= HS1) && (S2 <= HE2)) {
				SaidaAnt1[0] = HS1 - S1;
				SaidaAnt2[0] = HS2 - HS2;
			}
			// 19
			if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HE1)
					&& (E2 < HS1) && (S2 >= HE2) && (S2 <= HS2)) {
				SaidaAnt1[0] = HS1 - S1;
				SaidaAnt2[0] = HS2 - S2;
			}
			// 20
			if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HE1)
					&& (E2 < HS1) && (S2 >= HS2)) {
				SaidaAnt1[0] = HS1 - S1;
			}
			// 21
			if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HS1)
					&& (E2 < HE2) && (S2 >= HS2)) {
				SaidaAnt1[0] = HS1 - S1;
			}
			// 22
			if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HE2)
					&& (E2 < HS2) && (S2 >= HS2)) {
				SaidaAnt1[0] = HS1 - S1;
				Atraso2[0] = E2 - HE2;
			}
			// 22
			if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HE2)
					&& (E2 < HS2) && (S2 >= HS2)) {
				SaidaAnt1[0] = HS1 - S1;
				Atraso2[0] = HS2 - HE2;
			}
			// 23
			if ((E1 < HE1) && (S1 >= HE1) && (S1 <= HS1) && (E2 >= HS2)
					&& (S2 > HS2)) {
				SaidaAnt1[0] = HS1 - S1;
			}
			// 24
			if ((E1 < HE1) && (S1 >= HS1) && (S1 <= HE2) && (E2 > HS1)
					&& (E1 < HE2) && (S2 > HS1) && (S2 <= HS2)) {
				SaidaAnt2[0] = HS2 - HE2;
			}
			// 25
			if ((E1 < HE1) && (S1 >= HS1) && (S1 <= HE2) && (E2 > HS1)
					&& (E1 < HE2) && (S2 >= HE2) && (S2 <= HS2)) {
				SaidaAnt2[0] = HS2 - S2;
			}
			// 26
			if ((E1 < HE1) && (S1 >= HS1) && (S1 <= HE2) && (E2 > HS1)
					&& (E1 < HE2) && (S2 > HS2)) {
			}
			// 27
			if ((E1 < HE1) && (S1 >= HS1) && (S1 <= HE2) && (E2 > HE2)
					&& (E1 < HS2) && (S2 > HS2) && (S2 <= HS2)) {
				SaidaAnt2[0] = HS2 - S2;
				Atraso2[0] = E2 - HE2;
			}
			// 28
			if ((E1 < HE1) && (S1 >= HS1) && (S1 <= HE2) && (E2 >= HE2)
					&& (E2 <= HS2) && (S2 > HS2)) {
				Atraso2[0] = E2 - HE2;
			}
			// 29
			if ((E1 < HE1) && (S1 >= HS1) && (S1 <= HE2) && (E2 >= HS2)
					&& (S2 > HS2)) {
				Atraso2[0] = HE2 - HS2;
			}
			// 30
			if ((E1 < HE1) && (S1 >= HE2) && (S1 <= HS2) && (E2 >= HE2)
					&& (E2 <= HS2) && (S2 > HE2) && (S2 <= HS2)) {
				SaidaAnt2[0] = HS2 - S2;
				Atraso2[0] = E2 - HE2;
			}
			// 31
			if ((E1 < HE1) && (S1 >= HE2) && (S1 <= HS2) && (E2 >= HE2)
					&& (E2 <= HS2) && (S2 > HS2)) {
				Atraso2[0] = E2 - HE2;
			}
			// 32
			if ((E1 < HE1) && (S1 >= HE2) && (S1 <= HS2) && (E2 >= HS2)
					&& (S2 > HS2)) {
				Atraso2[0] = HS2 - HE2;
			}

			// 33
			if ((E1 < HE1) && (S1 >= HS2) && (E2 > HS2) && (S2 > HS2)) {
				Atraso2[0] = HS2 - HE2;
			}

			// 34
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
					&& (E2 >= HE1) && (E2 < HS1) && (S2 > HE1) && (S2 <= HS1)) {
				SaidaAnt1[0] = HS1 - S1;
				SaidaAnt2[0] = HS2 - HE2;
				Atraso1[0] = E1 - HE1;
			}
			// 35
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
					&& (E2 >= HE1) && (E2 < HS1) && (S2 > HE1) && (S2 >= HS1)
					&& (S2 <= HE2)) {
				SaidaAnt1[0] = HS1 - S1;
				SaidaAnt2[0] = HS2 - HE2;
				Atraso1[0] = E1 - HE1;
			}
			// 36
			if ((E1 >= HE1) && (E1 < HE1) && (S1 > HE1) && (S1 < HS1)
					&& (E2 >= HE1) && (E2 < HS1) && (S2 > HE1) && (S2 >= HE2)
					&& (S2 <= HS2)) {
				SaidaAnt1[0] = HS1 - S1;
				SaidaAnt2[0] = HS2 - S2;
				Atraso1[0] = E1 - HE1;
			}
			// 37
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
					&& (E2 >= HE1) && (E2 < HS1) && (S2 > HE1) && (S2 >= HS2)) {
				SaidaAnt1[0] = HS1 - S1;
				Atraso1[0] = E1 - HE1;
			}
			// 38
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
					&& (E2 >= HS1) && (E2 < HE2) && (S2 > HS1) && (S2 <= HE2)) {
				SaidaAnt1[0] = HS1 - S1;
				SaidaAnt2[0] = HS2 - HE2;
				Atraso1[0] = E1 - HE1;
			}
			// 39
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
					&& (E2 >= HS1) && (E2 < HE2) && (S2 > HE2) && (S2 <= HS2)) {
				SaidaAnt1[0] = HS1 - S1;
				SaidaAnt2[0] = HS2 - S2;
				Atraso1[0] = E1 - HE1;
			}
			// 40
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
					&& (E2 >= HS1) && (E2 < HE2) && (S2 >= HS2)) {
				SaidaAnt1[0] = HS1 - S1;
				Atraso1[0] = E1 - HE1;
			}
			// 41
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE1) && (S1 < HS1)
					&& (E2 >= HE2) && (E2 <= HS2) && (S2 >= HS2)) {
				SaidaAnt1[0] = HS1 - S1;
				Atraso1[0] = E1 - HE1;
				Atraso2[0] = E2 - HE2;
			}
			// 42
			if ((E1 >= HE1) && (E1 < HS1) && (S1 >= HE1) && (S1 < HS1)
					&& (E2 >= HS2) && (S2 > HS2)) {
				SaidaAnt1[0] = HS1 - S1;
				Atraso1[0] = E1 - HE1;
				Atraso2[0] = HS2 - HE2;
			}
			// 43
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HS1) && (S1 <= HE2)
					&& (E2 >= HS1) && (E2 < HS2) && (S2 > HS1) && (S2 <= HE2)) {
				SaidaAnt2[0] = HE2 - HS2;
				Atraso1[0] = E1 - HE1;
			}
			// 44
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HS1) && (S1 <= HE2)
					&& (E2 >= HS1) && (E2 <= HE2) && (S2 >= HE2) && (S2 <= HS2)) {
				SaidaAnt2[0] = HS2 - S2;
				Atraso1[0] = E1 - HE1;
			}
			// 45
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HS1) && (S1 < HE2)
					&& (E2 >= HS1) && (E2 <= HE2) && (S2 >= HS2)) {
				Atraso1[0] = E1 - HE1;
			}
			// 46
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HS1) && (S1 < HE2)
					&& (E2 >= HE2) && (E2 <= HS2) && (S2 >= HS2)) {
				Atraso1[0] = E1 - HE1;
				Atraso2[0] = E2 - HE2;
			}
			// 47
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HS1) && (S1 < HE2)
					&& (E2 >= HS2) && (S2 > HS2)) {
				Atraso1[0] = E1 - HE1;
				Atraso2[0] = HS2 - HE2;
			}
			// 48
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE2) && (S1 < HS2)
					&& (E2 >= HE2) && (E2 < HS2) && (S2 > HE2) && (S2 <= HS2)) {
				SaidaAnt2[0] = HS2 - S2;
				Atraso1[0] = E1 - HE1;
				Atraso2[0] = E2 - HE2;
			}
			// 49
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE2) && (S1 < HS2)
					&& (E2 >= HE2) && (E2 <= HS2) && (S2 >= HS2)) {
				Atraso1[0] = E1 - HE1;
				Atraso2[0] = E2 - HE2;
			}
			// 50
			if ((E1 >= HE1) && (E1 < HS1) && (S1 > HE2) && (S1 < HS2)
					&& (E2 >= HS2) && (S2 > HS2)) {
				Atraso1[0] = E1 - HE1;
				Atraso2[0] = HS2 - HE2;
			}
			// 51
			if ((E1 >= HE1) && (E1 < HS1) && (S1 >= HS2) && (E2 > HS2)
					&& (S2 > HS2)) {
				Atraso1[0] = E1 - HE1;
				Atraso2[0] = HS2 - HE2;
			}
			// 52
			if ((E1 >= HS1) && (E1 < HE2) && (S1 > HS1) && (S1 < HE2)
					&& (E2 > HS1) && (E2 < HE2) && (S2 > HS1) && (S2 <= HE2)) {
				SaidaAnt2[0] = HS2 - HE2;
				Atraso1[0] = HS1 - HE1;
			}
			// 53
			if ((E1 >= HS1) && (E1 < HE2) && (S1 > HS1) && (S1 < HE2)
					&& (E2 > HS1) && (E2 < HE2) && (S2 > HE2) && (S2 <= HS2)) {
				SaidaAnt2[0] = HS2 - S2;
				Atraso1[0] = HS1 - HE1;
			}
			// 54
			if ((E1 >= HS1) && (E1 < HE2) && (S1 > HS1) && (S1 < HE2)
					&& (E2 > HS1) && (E2 < HE2) && (S2 > HS2)) {
				Atraso1[0] = HS1 - HE1;
			}
			// 55
			if ((E1 >= HS1) && (E1 < HE2) && (S1 > HS1) && (S1 < HE2)
					&& (E2 > HE2) && (E2 <= HS2) && (S2 >= HS2)) {
				Atraso1[0] = HS1 - HE1;
				Atraso2[0] = E2 - HE2;
			}
			// 56
			if ((E1 >= HS1) && (E1 < HE2) && (S1 > HS1) && (S1 < HE2)
					&& (E2 >= HS2) && (S2 > HS2)) {
				Atraso1[0] = HS1 - HE1;
				Atraso2[0] = HS2 - HE2;
			}
			// 57
			if ((E1 >= HS1) && (E1 < HE2) && (S1 > HE2) && (S1 < HS2)
					&& (E2 >= HE2) && (E2 < HS2) && (S2 > HE2) && (S2 <= HS2)) {
				SaidaAnt2[0] = HS2 - S2;
				Atraso1[0] = HS1 - HE1;
				Atraso2[0] = E2 - HE2;
			}
			// 58
			if ((E1 >= HS1) && (E1 < HE2) && (S1 > HE2) && (S1 < HS2)
					&& (E2 >= HE2) && (E2 <= HS2) && (S2 >= HS2)) {
				Atraso1[0] = HS1 - HE1;
				Atraso2[0] = E2 - HE2;
			}
			// 59
			if ((E1 >= HS1) && (E1 < HE2) && (S1 > HE2) && (S1 < HS2)
					&& (E2 >= HS2) && (S2 > HS2)) {
				Atraso1[0] = HS1 - HE1;
				Atraso2[0] = HS2 - HE2;
			}
			// 60
			if ((E1 >= HS1) && (E1 < HE2) && (S1 >= HS2) && (E2 > HS2)
					&& (S2 > HS2)) {
				Atraso1[0] = HS1 - HE1;
				Atraso2[0] = E2 - HE2;
			}
			// 61
			if ((E1 >= HE2) && (E1 < HS2) && (S1 > HE2) && (S1 < HS2)
					&& (E2 > HE2) && (E2 < HS2) && (S2 > HE2) && (S2 <= HS2)) {
				SaidaAnt2[0] = HS2 - S2;
				Atraso1[0] = HS1 - HE1;
				Atraso2[0] = E2 - HE2;
			}
			// 62
			if ((E1 >= HE2) && (E1 < HS2) && (S1 > HE2) && (S1 < HS2)
					&& (E2 > HE2) && (E2 <= HS2) && (S2 > HS2)) {
				Atraso1[0] = HS1 - HE1;
				Atraso2[0] = E2 - HE2;
			}
			// 63
			if ((E1 >= HE2) && (E1 < HS2) && (S1 > HE2) && (S1 <= HS2)
					&& (E2 > HS2) && (S2 > HS2)) {
				Atraso1[0] = HS1 - HE1;
				Atraso2[0] = HS2 - HE2;
			}
			// 64
			if ((E1 >= HE2) && (E1 < HS2) && (S1 >= HS2) && (E2 > HS2)
					&& (S2 > HS2)) {
				Atraso1[0] = HS1 - HE1;
				Atraso2[0] = HS2 - HE2;
			}
			// 65
			if ((E1 >= HS2) && (S1 > HS2) && (E2 > HS2) && (S2 > HS2)) {
				Atraso1[0] = HS1 - HE1;
				Atraso2[0] = HS2 - HE2;
			}
			
			// 66
			if ((E1>HE1) && (E1<=HS1) && (S1>=HS1) && (S1<=HE2) && (E2>=HE2) && (E2<=HS2) && (S2>=HE2) && (S2<=HS2)) {
				Atraso1[0] = E1 - HE1;
				Atraso2[0] = E2 - HE2;
				SaidaAnt2[0] = HS2 - S2;
			}
			
			

		} // 4 bat 4 hora

	}// Calcula_SaidaAntFX2

}// fim
