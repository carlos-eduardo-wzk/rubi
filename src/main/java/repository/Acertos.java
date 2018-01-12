package repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Named;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import filter.AcertoFilter;
import model.Acerto;

@Named
@Stateful
public class Acertos implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "safiraPU")
	private Session session;

	public List<Acerto> carregarAcertos(AcertoFilter acertoFilter) {

		String auxStr = "TODOS";
		String str = "";

		if (acertoFilter.getTipo().equals("ATRASO")) {
			auxStr = "ATRASO";
		}
		if (acertoFilter.getTipo().equals("SAIDA")) {
			auxStr = "SAIDA";
		}
		if (acertoFilter.getTipo().equals("HE")) {
			auxStr = "HE";
		}

		if (acertoFilter.getTipo().equals("FALTA")) {
			auxStr = "FALTA";
		}

		if (acertoFilter.getTipo().equals("Divergencia")) {
			auxStr = "MARCACAOINVALIDA";
		}

		String jpql = "";

		try {

			if (acertoFilter.getTipo().equals("Divergencia")) {
				str = formataStringCSV(acertoFilter.getMatricula());
				jpql = " SELECT mp.Id, DataAbono, mp.data ,oa.horas,horasAbonadas,motivoabono,oa.colaborador_id,h1,h2,h3,h4,h5,h6,h7,h8,nome,ocorrencia_id, 'Divergencia' as  ocorrencia, empresa_id, filial_id, depto_id, matricula,pis "
						+ " from marcacaoprocessada mp" + " left join vw_marcdiacolaborador md"
						+ " on (mp.data=md.data and mp.colaborador_id=md.colaborador_id)" + " left join colaborador co"
						+ " on ( md.colaborador_id = co.Id )" + " left join ocorrenciaapurada oa"
						+ " on (oa.data=md.data and oa.colaborador_id=md.colaborador_id) "
						+ " left join motivoabono ma " + " on ( ma.id = oa.motivoabono_Id ) ";

				jpql = jpql + " where md.data >= :diaini and md.data <= :diafim and mp.tipoDia=:td ";

				if (acertoFilter.getEmpresa() != 0) {
					jpql = jpql + " and empresa_id = " + acertoFilter.getEmpresa();
				}

				if (acertoFilter.getFilial() != 0) {
					jpql = jpql + " and filial_id = " + acertoFilter.getFilial();
				}

				if (acertoFilter.getDepto() != 0) {
					jpql = jpql + " and depto_id = " + acertoFilter.getDepto();
				}

				if (acertoFilter.getNome().isEmpty() == false) {
					jpql = jpql + " and nome like " + "'%" + acertoFilter.getNome().trim() + "%'";
				}

				if (acertoFilter.getMatricula().isEmpty() == false) {
					jpql = jpql + " and  co.matricula in (" + str + ")";
				}

				@SuppressWarnings("unchecked")
				List<Acerto> resultado = session.createSQLQuery(jpql).addEntity(Acerto.class)
						.setParameter("diaini", acertoFilter.getDataini())
						.setParameter("diafim", acertoFilter.getDatafim()).setParameter("td", auxStr).list();

				return resultado;

			} else {
				if (acertoFilter.getTipo().equals("FALTA")) {

					str = formataStringCSV(acertoFilter.getMatricula());

					jpql = "  SELECT  oa.Id, DataAbono, oa.data ,  oa.horas, null as  horasAbonadas, '' as motivoabono, "
							+ "oa.colaborador_id, 0 as h1, 0 as h2, 0 as h3, 0 as h4, 0 as h5, 0 as h6, 0 as h7, 0 as h8, "
							+ " nome, oa.ocorrencia_id, 'Falta' as ocorrencia, empresa_id, filial_id, depto_id, "
							+ " matricula, pis " + " FROM ocorrenciaapurada  oa " + " left join " + " colaborador co "
							+ "  on (" + "      oa.colaborador_id = co.Id  " + "      ) ";

					jpql = jpql
							+ " where data >= :diaini and data <= :diafim and tipoDia='FALTA' and dataAbono is null  ";

					if (acertoFilter.getEmpresa() != 0) {
						// jpql = jpql + " and empresa_id = " +
						// acertoFilter.getEmpresa();
					}

					if (acertoFilter.getFilial() != 0) {
						jpql = jpql + " and filial_id = " + acertoFilter.getFilial();
					}

					if (acertoFilter.getDepto() != 0) {
						jpql = jpql + " and depto_id = " + acertoFilter.getDepto();
					}

					if (acertoFilter.getNome().isEmpty() == false) {
						jpql = jpql + " and nome like " + "'%" + acertoFilter.getNome().trim() + "%'";
					}

					if (acertoFilter.getMatricula().isEmpty() == false) {
						jpql = jpql + " and  co.matricula in (" + str + ")";
					}

					jpql = jpql + " order by data ";

					@SuppressWarnings("unchecked")
					List<Acerto> resultado = session.createSQLQuery(jpql).addEntity(Acerto.class)
							.setParameter("diaini", acertoFilter.getDataini())
							.setParameter("diafim", acertoFilter.getDatafim()).list();

					return resultado;
				} else

				if (acertoFilter.getTipo().equals("AFASTADO")) {

					str = formataStringCSV(acertoFilter.getMatricula());

					jpql = "  SELECT  oa.Id, DataAbono, oa.data ,  oa.horas, null as  horasAbonadas, '' as motivoabono, "
							+ "oa.colaborador_id, 0 as h1, 0 as h2, 0 as h3, 0 as h4, 0 as h5, 0 as h6, 0 as h7, 0 as h8, "
							+ " nome, oa.ocorrencia_id, 'Afastado' as ocorrencia, empresa_id, filial_id, depto_id, "
							+ " matricula, pis " + " FROM ocorrenciaapurada  oa " + " left join " + " colaborador co "
							+ "  on (" + "      oa.colaborador_id = co.Id  " + "      ) ";

					jpql = jpql
							+ " where data >= :diaini and data <= :diafim and tipoDia='AFASTADO' and dataAbono is null  ";

					if (acertoFilter.getEmpresa() != 0) {
						// jpql = jpql + " and empresa_id = " +
						// acertoFilter.getEmpresa();
					}

					if (acertoFilter.getFilial() != 0) {
						jpql = jpql + " and filial_id = " + acertoFilter.getFilial();
					}

					if (acertoFilter.getDepto() != 0) {
						jpql = jpql + " and depto_id = " + acertoFilter.getDepto();
					}

					if (acertoFilter.getNome().isEmpty() == false) {
						jpql = jpql + " and nome like " + "'%" + acertoFilter.getNome().trim() + "%'";
					}

					if (acertoFilter.getMatricula().isEmpty() == false) {
						jpql = jpql + " and  co.matricula in (" + str + ")";
					}

					jpql = jpql + " order by data ";

					@SuppressWarnings("unchecked")
					List<Acerto> resultado = session.createSQLQuery(jpql).addEntity(Acerto.class)
							.setParameter("diaini", acertoFilter.getDataini())
							.setParameter("diafim", acertoFilter.getDatafim()).list();

					return resultado;
				} else

				if (acertoFilter.getTipo().equals("FOLGA")) {

					str = formataStringCSV(acertoFilter.getMatricula());

					jpql = "  SELECT  oa.Id, DataAbono, oa.data ,  oa.horas, null as  horasAbonadas, '' as motivoabono, "
							+ "oa.colaborador_id, 0 as h1, 0 as h2, 0 as h3, 0 as h4, 0 as h5, 0 as h6, 0 as h7, 0 as h8, "
							+ " nome, oa.ocorrencia_id, 'Folga' as ocorrencia, empresa_id, filial_id, depto_id, "
							+ " matricula, pis " + " FROM ocorrenciaapurada  oa " + " left join " + " colaborador co "
							+ "  on (" + "      oa.colaborador_id = co.Id  " + "      ) ";

					jpql = jpql
							+ " where data >= :diaini and data <= :diafim and tipoDia='FOLGA' and dataAbono is null  ";

					if (acertoFilter.getEmpresa() != 0) {
						// jpql = jpql + " and empresa_id = " +
						// acertoFilter.getEmpresa();
					}

					if (acertoFilter.getFilial() != 0) {
						jpql = jpql + " and filial_id = " + acertoFilter.getFilial();
					}

					if (acertoFilter.getDepto() != 0) {
						jpql = jpql + " and depto_id = " + acertoFilter.getDepto();
					}

					if (acertoFilter.getNome().isEmpty() == false) {
						jpql = jpql + " and nome like " + "'%" + acertoFilter.getNome().trim() + "%'";
					}

					if (acertoFilter.getMatricula().isEmpty() == false) {
						jpql = jpql + " and  co.matricula in (" + str + ")";
					}

					jpql = jpql + " order by data ";

					@SuppressWarnings("unchecked")
					List<Acerto> resultado = session.createSQLQuery(jpql).addEntity(Acerto.class)
							.setParameter("diaini", acertoFilter.getDataini())
							.setParameter("diafim", acertoFilter.getDatafim()).list();

					return resultado;
				}

				else {

					str = formataStringCSV(acertoFilter.getMatricula());

					jpql = " select  mp.id,mp.colaborador_id, mp.data,dataprocessamento,mp.tipodia, h1,h2,h3,h4,h5,h6,h7,h8 ,dataabono "
							+ ",oa.horas,horasabonadas , motivoAbono, nome, ocorrencia_id,empresa_id,filial_id,depto_id,matricula,pis, "
							+ "   CASE " + "   WHEN oc.ocorrencia <> '' THEN oc.ocorrencia " + "   ELSE mp.tipodia  "
							+ "   END  " + "   AS ocorrencia " + " from marcacaoprocessada mp "
							+ " left join vw_marcdiacolaborador md "
							+ " on (mp.data=md.data and mp.colaborador_id=md.colaborador_id) "
							+ " left join ocorrenciaapurada oa  "
							+ " on (  oa.colaborador_id=mp.colaborador_id and oa.data=mp.data  ) "
							+ " left join motivoabono ma " + " on(ma.id= oa.motivoabono_id) "
							+ " left join ocorrencia oc " + " on(oc.id= oa.ocorrencia_id) "
							+ " left join colaborador co " + " on ( md.colaborador_id = co.Id ) ";

					if (acertoFilter.getTipo().equals("TODOS")) {
						jpql = jpql + " where md.data >= :diaini and md.data <= :diafim ";

						if (acertoFilter.getEmpresa() != 0) {
							jpql = jpql + " and empresa_id = " + acertoFilter.getEmpresa();
						}

						if (acertoFilter.getFilial() != 0) {
							jpql = jpql + " and filial_id = " + acertoFilter.getFilial();
						}

						if (acertoFilter.getDepto() != 0) {
							jpql = jpql + " and depto_id = " + acertoFilter.getDepto();
						}

						if (acertoFilter.getNome().isEmpty() == false) {
							jpql = jpql + " and nome like " + "'%" + acertoFilter.getNome().trim() + "%'";
						}

						if (acertoFilter.getMatricula().isEmpty() == false) {
							System.out.println(" matricula---------->" + str);
							jpql = jpql + " and  co.matricula in (" + str + ")";
							System.out.println("jpsl " + jpql);
						}

						jpql = jpql + " order by oa.data, oa.colaborador_id, h1,h2,h3,h4,h5 ";

						List<Acerto> la = new ArrayList<Acerto>();

						@SuppressWarnings("unchecked")
						List<Object[]> resultado = session.createSQLQuery(jpql)
								.setParameter("diaini", acertoFilter.getDataini())
								.setParameter("diafim", acertoFilter.getDatafim()).list();

						for (Object[] a : resultado) {

							Acerto ac1 = new Acerto();

							ac1.setId((long) ((BigInteger) a[0]).longValue());
							ac1.setColaborador_id(((BigInteger) a[1]).longValue());
							ac1.setData((java.util.Date) a[2]);

							ac1.setH1((Integer) a[5]);
							ac1.setH2((Integer) a[6]);
							ac1.setH3((Integer) a[7]);
							ac1.setH4((Integer) a[8]);
							ac1.setH5((Integer) a[9]);
							ac1.setH6((Integer) a[10]);
							ac1.setH7((Integer) a[11]);
							ac1.setH8((Integer) a[12]);
							ac1.setDataAbono((java.util.Date) a[13]);
							ac1.setHoras((Integer) a[14]);
							ac1.setHorasAbonadas((Integer) a[15]);
							ac1.setMotivoAbono((String) a[16]);
							ac1.setNome((String) a[17]);
							ac1.setOcorrencia_id((Integer) a[18]);
							ac1.setPis((String) a[23]);
							ac1.setMatricula((String) a[22]);
							ac1.setDepto_id((int) ((BigInteger) a[21]).longValue());
							ac1.setFilial_id((int) ((BigInteger) a[20]).longValue());
							ac1.setEmpresa_id((int) ((BigInteger) a[19]).longValue());
							ac1.setOcorrencia((String) a[24]);

							la.add(ac1);
						}

						return la;

					} else {

						if (acertoFilter.getTipo().equals("ATRASO")) {

							jpql = jpql
									+ " where md.data >= :diaini and md.data <= :diafim and trim(ocorrencia) = 'ATRASO' and oa.dataAbono is null";

							if (acertoFilter.getEmpresa() != 0) {
								jpql = jpql + " and empresa_id = " + acertoFilter.getEmpresa();
							}

							if (acertoFilter.getFilial() != 0) {
								jpql = jpql + " and filial_id = " + acertoFilter.getFilial();
							}

							if (acertoFilter.getDepto() != 0) {
								jpql = jpql + " and depto_id = " + acertoFilter.getDepto();
							}

							if (acertoFilter.getNome().isEmpty() == false) {
								jpql = jpql + " and nome like " + "'%" + acertoFilter.getNome().trim() + "%'";
							}

							if (acertoFilter.getMatricula().isEmpty() == false) {
								jpql = jpql + " and  co.matricula in (" + str + ")";
							}
						}

						if (acertoFilter.getTipo().equals("SAIDA_ANTECIPADA")) {
							jpql = jpql
									+ " where md.data >= :diaini and md.data <= :diafim and ((ocorrencia = 'SAIDA 2') or (ocorrencia = 'SAIDA'))  and oa.dataAbono is null";

							if (acertoFilter.getEmpresa() != 0) {
								jpql = jpql + " and empresa_id = " + acertoFilter.getEmpresa();
							}

							if (acertoFilter.getFilial() != 0) {
								jpql = jpql + " and filial_id = " + acertoFilter.getFilial();
							}

							if (acertoFilter.getDepto() != 0) {
								jpql = jpql + " and depto_id = " + acertoFilter.getDepto();
							}

							if (acertoFilter.getNome().isEmpty() == false) {
								jpql = jpql + " and nome like " + "'%" + acertoFilter.getNome().trim() + "%'";
							}

							if (acertoFilter.getMatricula().isEmpty() == false) {
								jpql = jpql + " and  co.matricula in (" + str + ")";
							}
						}

						if (acertoFilter.getTipo().equals("HE")) {
							jpql = jpql
									+ " where md.data >= :diaini and md.data <= :diafim  and ocorrencia like '%HE%' ";

							if (acertoFilter.getEmpresa() != 0) {
								jpql = jpql + " and empresa_id = " + acertoFilter.getEmpresa();
							}

							if (acertoFilter.getFilial() != 0) {
								jpql = jpql + " and filial_id = " + acertoFilter.getFilial();
							}

							if (acertoFilter.getDepto() != 0) {
								jpql = jpql + " and depto_id = " + acertoFilter.getDepto();
							}

							if (acertoFilter.getNome().isEmpty() == false) {
								jpql = jpql + " and nome like " + "'%" + acertoFilter.getNome().trim() + "%'";
							}

							if (acertoFilter.getMatricula().isEmpty() == false) {
								jpql = jpql + " and  co.matricula in (" + str + ")";
							}
						}

						@SuppressWarnings("unchecked")
						List<Object[]> resultado = session.createSQLQuery(jpql)
								.setParameter("diaini", acertoFilter.getDataini())
								.setParameter("diafim", acertoFilter.getDatafim()).list();

						List<Acerto> la = new ArrayList<Acerto>();

						for (Object[] a : resultado) {

							Acerto ac1 = new Acerto();

							ac1.setId((long) ((BigInteger) a[0]).longValue());
							ac1.setColaborador_id(((BigInteger) a[1]).longValue());
							ac1.setData((java.util.Date) a[2]);

							ac1.setH1((Integer) a[5]);
							ac1.setH2((Integer) a[6]);
							ac1.setH3((Integer) a[7]);
							ac1.setH4((Integer) a[8]);
							ac1.setH5((Integer) a[9]);
							ac1.setH6((Integer) a[10]);
							ac1.setH7((Integer) a[11]);
							ac1.setH8((Integer) a[12]);
							ac1.setDataAbono((java.util.Date) a[13]);
							ac1.setHoras((Integer) a[14]);
							ac1.setHorasAbonadas((Integer) a[15]);
							ac1.setMotivoAbono((String) a[16]);
							ac1.setNome((String) a[17]);
							ac1.setOcorrencia_id((Integer) a[18]);
							ac1.setPis((String) a[23]);
							ac1.setMatricula((String) a[22]);
							ac1.setDepto_id((int) ((BigInteger) a[21]).longValue());
							ac1.setFilial_id((int) ((BigInteger) a[20]).longValue());
							ac1.setEmpresa_id((int) ((BigInteger) a[19]).longValue());
							ac1.setOcorrencia((String) a[24]);

							la.add(ac1);
						}
						return la;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String formataStringCSV(String st) {
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
