package repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import filter.AcertoFilter;

@Stateless
public class AcertosAbonos implements Serializable {

	private static final long serialVersionUID = 1L;


	@PersistenceContext(unitName = "safiraPU")
	private Session session;
	
	public List<model.AcertoAbono> carregarAcertos(AcertoFilter acertoFilter,
			String[] selectedTipos) {
		System.out.println("**********************" + acertoFilter.getTipo());
		System.out.println("--- " + acertoFilter.getDepto());

		String auxStr = "\'HT\'";
		String str = "";
		String auxStrDiv = "";

		// if (acertoFilter.getTipo().equals("ATRASO")) {
		// auxStr = "ATRASO";
		// }
		// if (acertoFilter.getTipo().equals("SAIDA")) {
		// auxStr = "SAIDA";
		// }
		//
		// if (acertoFilter.getTipo().equals("HE")) {
		// auxStr = "HE";
		// }
		//
		// if (acertoFilter.getTipo().equals("HT")) {
		// auxStr = "HT";
		// }
		//
		// if (acertoFilter.getTipo().equals("FALTA")) {
		// auxStr = "FALTA";
		// }
		//
		// if (acertoFilter.getTipo().equals("Divergencia")) {
		// auxStr = "MARCACAOINVALIDA";
		// }

		for (String a : selectedTipos) {
			System.out.println(" AAAAAA " + a);

			if (a.equals("ATRASO")) {
				if (auxStr.length() > 0) {
					auxStr += ",\'AT\'";
				} else {
					auxStr = "\'AT\'";
				}// if
			}// if

			if (a.equals("SAIDA")) {
				if (auxStr.length() > 0) {
					auxStr += ",\'SA\'";
				} else {
					auxStr = "\'SA\'";
				}// if
			}// if

			if (a.equals("HE")) {
				if (auxStr.length() > 0) {
					auxStr += ",\'HE\'";
				} else {
					auxStr = "\'HE\'";
				}// if
			}// if

			if (a.equals("HT")) {
				if (auxStr.length() > 0) {
					auxStr += ",\'HT\'";
				} else {
					auxStr = "\'HT\'";
				}// if
			}// if

			if (a.equals("Divergencia")) {
				auxStrDiv += "MARCACAOINVALIDA";
			}// if

			if (a.equals("FALTA")) {
				auxStr = "FALTA";
			}

			if (a.equals("AF")) {
				auxStr = "AFASTAMENTO";
			}

			if (a.equals("FO")) {
				auxStr = "FOLGA";
			}

		}// for

		String jpql = "";

		try {

			str = formataStringCSV(acertoFilter.getMatricula());

			jpql = "select  mp.id, "
					+ " matricula, "
					+ "nome, depto_id, "
					+ " mp.data,h1,h2,h3,h4,h5,h6,h7,h8,horas, mp.colaborador_id, empresa_id, ocorrencia_id, filial_id,pis,  "
					+ "							    CASE  "
					+ "							    WHEN oc.ocorrencia <> '' THEN oc.ocorrencia  "
					+ "							    ELSE mp.tipodia   "
					+ "							    END   "
					+ "							    AS ocorrencia  "
					+ "							    , agrupamento"
					+ "							  from marcacaoprocessada mp  "
					+ "							  left join vw_marcdiacolaborador md "
					+ "							  on (mp.data=md.data and mp.colaborador_id=md.colaborador_id) "
					+ "							  left join ocorrenciaapurada oa   "
					+ "							  on (  oa.colaborador_id=mp.colaborador_id and oa.data=mp.data  ) "
					+ "							  left join motivoabono ma  "
					+ "							  on(ma.id= oa.motivoabono_id) "
					+ "							  left join ocorrencia oc  "
					+ "							  on(oc.id= oa.ocorrencia_id) "
					+ "							  left join colaborador co  "
					+ "							  on ( oa.colaborador_id = co.Id ) "
					+ "                           where md.data >= :diaini and md.data <= :diafim and dataabono is null ";

			if (!auxStr.isEmpty()) {
				if (auxStr.equals("FALTA")) {
					jpql = "    select   mp.id, matricula, nome,depto_id,mp.data, "
							+ " 0 as h1,0 as h2,0 as h3,0 as h4,0 as h5,0 as h6,0 as h7,0 as h8, "
							+ " horas, mp.colaborador_id, empresa_id,ocorrencia_id,filial_id,pis, "
							+ " CASE   "
							+ " WHEN oc.ocorrencia <> '' THEN oc.ocorrencia "
							+ " ELSE mp.tipodia     "
							+ " END              AS ocorrencia   ,"
							+ " agrupamento          "
							+ " from "
							+ " marcacaoprocessada mp "
							+ " left join "
							+ " ocorrenciaapurada oa  "
							+ " on ( "
							+ "   oa.colaborador_id=mp.colaborador_id "
							+ "   and oa.data=mp.data  "
							+ " ) "
							+ " left join "
							+ "    motivoabono ma  "
							+ "        on( "
							+ "            ma.id= oa.motivoabono_id "
							+ "        )   "
							+ " left join "
							+ "    ocorrencia oc "
							+ "        on (  "
							+ "            oc.id= oa.ocorrencia_id "
							+ "        )     "
							+ " left join "
							+ "    colaborador co "
							+ "        on ( "
							+ "            mp.colaborador_id = co.Id "
							+ "        ) "
							+ "  where "
							+ "    mp.data >= :diaini "
							+ "    and mp.data <= :diafim  "
							+ "    and agrupamento = 'FA' and dataabono is null";
				} // if falta

				if (auxStr.equals("AFASTAMENTO")) {
					jpql = "    select   mp.id, matricula, nome,depto_id,mp.data, "
							+ " 0 as h1,0 as h2,0 as h3,0 as h4,0 as h5,0 as h6,0 as h7,0 as h8, "
							+ " horas, mp.colaborador_id, empresa_id,ocorrencia_id,filial_id,pis, "
							+ " CASE   "
							+ " WHEN oc.ocorrencia <> '' THEN oc.ocorrencia "
							+ " ELSE mp.tipodia     "
							+ " END              AS ocorrencia   ,"
							+ " agrupamento          "
							+ " from "
							+ " marcacaoprocessada mp "
							+ " left join "
							+ " ocorrenciaapurada oa  "
							+ " on ( "
							+ "   oa.colaborador_id=mp.colaborador_id "
							+ "   and oa.data=mp.data  "
							+ " ) "
							+ " left join "
							+ "    motivoabono ma  "
							+ "        on( "
							+ "            ma.id= oa.motivoabono_id "
							+ "        )   "
							+ " left join "
							+ "    ocorrencia oc "
							+ "        on (  "
							+ "            oc.id= oa.ocorrencia_id "
							+ "        )     "
							+ " left join "
							+ "    colaborador co "
							+ "        on ( "
							+ "            mp.colaborador_id = co.Id "
							+ "        ) "
							+ "  where "
							+ "    mp.data >= :diaini "
							+ "    and mp.data <= :diafim  "
							+ "    and agrupamento = 'AF' and dataabono is null";
				} // if afastamento

				if (auxStr.equals("FOLGA")) {
					jpql = "    select   mp.id, matricula, nome,depto_id,mp.data, "
							+ " 0 as h1,0 as h2,0 as h3,0 as h4,0 as h5,0 as h6,0 as h7,0 as h8, "
							+ " horas, mp.colaborador_id, empresa_id,ocorrencia_id,filial_id,pis, "
							+ " CASE   "
							+ " WHEN oc.ocorrencia <> '' THEN oc.ocorrencia "
							+ " ELSE mp.tipodia     "
							+ " END              AS ocorrencia   ,"
							+ " agrupamento          "
							+ " from "
							+ " marcacaoprocessada mp "
							+ " left join "
							+ " ocorrenciaapurada oa  "
							+ " on ( "
							+ "   oa.colaborador_id=mp.colaborador_id "
							+ "   and oa.data=mp.data  "
							+ " ) "
							+ " left join "
							+ "    motivoabono ma  "
							+ "        on( "
							+ "            ma.id= oa.motivoabono_id "
							+ "        )   "
							+ " left join "
							+ "    ocorrencia oc "
							+ "        on (  "
							+ "            oc.id= oa.ocorrencia_id "
							+ "        )     "
							+ " left join "
							+ "    colaborador co "
							+ "        on ( "
							+ "            mp.colaborador_id = co.Id "
							+ "        ) "
							+ "  where "
							+ "    mp.data >= :diaini "
							+ "    and mp.data <= :diafim  "
							+ "    and agrupamento = 'FO' and dataabono is null";
				} // if folga

			}// str nao vazia

			// if (acertoFilter.getTipo().equals("TODOS")) {
			// jpql = jpql
			// + " where md.data >= :diaini and md.data <= :diafim ";
			//

			System.out.println("|||||| empresa selecionada "
					+ acertoFilter.getEmpresa());

			if (acertoFilter.getEmpresa() != 0) {
				jpql = jpql + " and empresa_id = " + acertoFilter.getEmpresa();
			}

			if (acertoFilter.getFilial() != null) {
				if (acertoFilter.getFilial() != 0) {
					jpql = jpql + " and filial_id = "
							+ acertoFilter.getFilial();
				}
			}

			if (acertoFilter.getDepto() != null) {
				if (acertoFilter.getDepto() != 0) {
					jpql = jpql + " and depto_id = " + acertoFilter.getDepto();
				}
			}

			if (acertoFilter.getNome() != null) {
				System.out.println("Entrei no filtro nome "
						+ acertoFilter.getNome());
				if (acertoFilter.getNome().isEmpty() == false) {
					jpql = jpql + " and nome like " + "'%"
							+ acertoFilter.getNome().trim() + "%'";
				}
			}

			if (acertoFilter.getMatricula().isEmpty() == false) {
				System.out.println(" matricula---------->" + str);
				jpql = jpql + " and  co.matricula in (" + str + ")";
				System.out.println("jpsl " + jpql);
			}

			if ((selectedTipos.length > 0) && (auxStr != "FALTA")
					&& (auxStr != "AFASTAMENTO") && (auxStr != "FOLGA") && (!auxStr.isEmpty())) {

				jpql = jpql + " and agrupamento in (" + auxStr + ")";

			}//

			if (auxStrDiv.equals("MARCACAOINVALIDA")) {

				if (auxStr.isEmpty())
					jpql = jpql
							+ " and   ((h1<>0 and h2 is null) or ((h1<>0) and (h2<>0) and (h3<>0) and (h4 is null))) ";
				else
					jpql = jpql
							+ " or  ((h1<>0 and h2 is null) or ((h1<>0) and (h2<>0) and (h3<>0) and (h4 is null))) ";
			}

			// if (!auxStr.isEmpty()) {
			// if (auxStr.equals("ATRASO")) {
			// jpql = jpql + " and agrupamento = " + "'AT'";
			// }
			// }
			//
			// if (!auxStr.isEmpty()) {
			// if (auxStr.equals("SAIDA")) {
			// jpql = jpql + " and agrupamento = " + "'SA'";
			// }
			// }
			//
			// if (!auxStr.isEmpty()) {
			// if (auxStr.equals("HE")) {
			// jpql = jpql + " and agrupamento = " + "'HE'";
			// }
			// }
			//
			// if (!auxStr.isEmpty()) {
			// if (auxStr.equals("HT")) {
			// jpql = jpql + " and agrupamento = " + "'HT'";
			// }
			// }
			//
			// if (!auxStr.isEmpty()) {
			// if (auxStr.equals("MARCACAOINVALIDA")) {
			// jpql = jpql
			// +
			// " and ((h1<>0 and h2 is null) or ((h1<>0) and (h2<>0) and (h3<>0) and (h4 is null))) ";
			// }
			// }

			jpql = jpql + "	order by id ";

			System.out.println(jpql);
			// + " order by oa.data, oa.colaborador_id, h1,h2,h3,h4,h5 ";

			//
			List<model.AcertoAbono> la = new ArrayList<model.AcertoAbono>();

			Long idAnterio = 0L;
			Date dataAnterior = null;
			model.AcertoAbono ac1 = new model.AcertoAbono();

			System.out.println("data dataini " + acertoFilter.getDataini());

			@SuppressWarnings("unchecked")
			List<Object[]> resultado = session.createSQLQuery(jpql)
					.setParameter("diaini", acertoFilter.getDataini())
					.setParameter("diafim", acertoFilter.getDatafim()).list();

			for (Object[] a : resultado) {

				// testar se mesmo dia, mesmo colaborador
				if ((idAnterio.equals(((BigInteger) a[0]).longValue()))
						&& (dataAnterior.equals((java.util.Date) a[4]))) {

					if (a[20] != null) {
						if (a[20].toString().equals("AT")) {
							if (ac1.getAtraso() == null) {
								ac1.setAtraso((Integer) a[13]); // horas
							} else {
								ac1.setAtraso((Integer) a[13] + ac1.getAtraso()); // horas
							}

							ac1.setOcorrenciaAtraso_id(((Integer) a[16])
									.longValue());
						}
					}

					if (a[20] != null) {						
						if (a[20].toString().equals("HE")) {
							if(ac1.getHe() == null){
							ac1.setHe((Integer) a[13]); // horas
							}
							else{
								ac1.setHe((Integer) a[13] + ac1.getHe()); // horas
							}
						}
					}


					if (a[20] != null) {
						if (a[20].toString().equals("BC")) {														  
							if (ac1.getBancohoras() == null) {
								ac1.setBancohoras((Integer) a[13]); // horas
							} else {
								ac1.setBancohoras((Integer) a[13] + (ac1.getBancohoras() )); // horas
							}
						}
					}
					
					if (a[20] != null) {
						if (a[20].toString().equals("BD")) {														  
							if (ac1.getBancohoras() == null) {
								ac1.setBancohoras((Integer) a[13] * -1); // horas
							} else {
								ac1.setBancohoras(((Integer) a[13] * -1)  +  (ac1.getBancohoras())); // horas
							}
						}
					}
		
					
					
					if (a[20] != null) {
						if (a[20].toString().equals("FA")) {

							ac1.setFalta((Integer) a[13]); // horas

							ac1.setOcorrenciaFalta_id(((Integer) a[16])
									.longValue());
						}
					}

					if (a[20] != null) {
						if (a[20].toString().equals("HT")) {
							ac1.setHt((Integer) a[13]); // horas
						}
					}

					if (a[20] != null) {
						if (a[20].toString().equals("SA")) {
							if (ac1.getSaida() == null) {
								ac1.setSaida((Integer) a[13]); // saida
							} else {
								ac1.setSaida((Integer) a[13] + ac1.getSaida()); // saida
							}

							ac1.setOcorrenciaSaida_id(((Integer) a[16])
									.longValue());
						}
					}

				}// if
				else {

					try {
						System.out.println((long) ((BigInteger) a[0])
								.longValue());// ID
						System.out.println((String) a[1]); // MATRI
						System.out.println((String) a[2]);// / NOME
						System.out.println((int) ((BigInteger) a[3])
								.longValue()); // dept
						System.out.println((java.util.Date) a[4]);// data
						System.out.println((Integer) a[5]);// h1
						System.out.println((Integer) a[6]);// h1
						System.out.println((Integer) a[7]);// h1
						System.out.println((Integer) a[8]);// h1
						System.out.println((Integer) a[9]);// h1
						System.out.println((Integer) a[10]);// h1
						System.out.println((Integer) a[11]);// h1
						System.out.println((Integer) a[12]);// h8
						System.out.println(((BigInteger) a[14]).longValue()); // colaborador
						System.out.println("empresa id "
								+ ((BigInteger) a[15]).longValue());// empresa
						System.out.println("ocorrencia id "
								+ ((Integer) a[16]).longValue()); // ocorrencia
						// id
						System.out.println(((BigInteger) a[17]).longValue()); // filia
																				// id
						System.out.println((String) a[18]);// pis
						System.out.println((String) a[19]);// ocorrencia
						System.out.println((String) a[20]); // agrupamento

						// System.out.println(a[21]); //ocorrencia id

					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}

					ac1 = new model.AcertoAbono();
					ac1.setId((long) ((BigInteger) a[0]).longValue());
					ac1.setMatricula((String) a[1]);
					ac1.setNome((String) a[2]);

					if (a[3] != null) {
						ac1.setDepto_id(((BigInteger) a[3]).intValue());
					}

					ac1.setData((java.util.Date) a[4]);
					ac1.setH1((Integer) a[5]);
					ac1.setH2((Integer) a[6]);
					ac1.setH3((Integer) a[7]);
					ac1.setH4((Integer) a[8]);
					ac1.setH5((Integer) a[9]);
					ac1.setH6((Integer) a[10]);
					ac1.setH7((Integer) a[11]);
					ac1.setH8((Integer) a[12]);

					if (a[14] != null) {
						ac1.setColaborador_id(((BigInteger) a[14]).longValue());
					}

					if (a[15] != null) {
						ac1.setEmpresa_id(((BigInteger) a[15]).intValue());
					}

					if (a[17] != null) {
						ac1.setFilial_id(((BigInteger) a[17]).intValue());
					}

					ac1.setPis((String) a[18]);

					// ac1.setPis((String) a[19]); // ocorre
					ac1.setAgrupamento((String) a[20]);

					if ((a[20] != null)) {
						if ((a[20].toString().equals("AT"))) {
							ac1.setAtraso((Integer) a[13]); // horas
							ac1.setOcorrenciaAtraso_id(((Integer) a[16])
									.longValue());
						}
					}
					
					if (a[20] != null) {
						if (a[20].toString().equals("HE")) {
							ac1.setHe((Integer) a[13]); // horas
						}
					}
					
					if (a[20] != null) {
						if (a[20].toString().equals("BD")) {
							ac1.setBancohoras((Integer) a[13] * -1); // banco horas
						}
					}
					
					if (a[20] != null) {
						if (a[20].toString().equals("BC")) {
							ac1.setBancohoras((Integer) a[13]); // banco horas
						}
					}
										

					if (a[20] != null) {
						if (a[20].toString().equals("FA")) {
							ac1.setFalta((Integer) a[13]); // horas
							ac1.setOcorrenciaFalta_id(((Integer) a[16])
									.longValue());
						}
					}

					if (a[20] != null) {
						if (a[20].toString().equals("HT")) {
							ac1.setHt((Integer) a[13]); // horas
						}
					}

					if (a[20] != null) {
						if (a[20].toString().equals("SA")) {
							ac1.setSaida((Integer) a[13]); // horas
							ac1.setOcorrenciaSaida_id(((Integer) a[16])
									.longValue());
						}
					}

					la.add(ac1);

					idAnterio = ((BigInteger) a[0]).longValue();
					dataAnterior = (java.util.Date) a[4];

				}// if

			}// for

			for (model.AcertoAbono aa : la) {
				System.out.println(aa);
			}

			return la;

			// }

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// return null;
	}

	public String formataStringCSV(String st) {
		if (st == null) {
			return "";
		}

		String str = st;
		String[] country = str.split(",");
		str = "";
		for (String x : country) {
			str = str + "'" + x + "'" + ",";
		}
		str = str.substring(0, str.length() - 1);
		return str;
	}
}
