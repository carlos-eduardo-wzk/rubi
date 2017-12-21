package repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import model.HorarioColaborador;
import model.Marcacao;
import model.MarcacaoProcessada;

public class Marcacoes implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "safiraPU")
	private Session session;

	public void salvaMarcacao(Marcacao marcacao) {

		session.getTransaction().begin();
		session.merge(marcacao);
		session.getTransaction().commit();

	}

	public void salvaListaMarcacao(List<Marcacao> lstmarcacao) {

		session.getTransaction().begin();
		for (int i = 0; i < lstmarcacao.size(); i++) {
			session.merge(lstmarcacao.get(i));
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}

		}// for

		session.getTransaction().commit();
	}

	public void apagarMarcacaoNoPeriodo(Date di, Date df) {

		session.getTransaction().begin();

		session.createQuery(
				"DELETE FROM Marcacao where data >=:di and data <= :df")
				.setParameter("di", di).setParameter("df", df).executeUpdate();
		session.getTransaction().commit();
		session.flush();

	}

	public Long diaJaFoiImportado(Date dia) {
		Criteria crit = session.createCriteria(MarcacaoProcessada.class);
		crit.add(Restrictions.eq("data", dia));
		crit.setProjection(Projections.rowCount());
		Long count = (Long) crit.uniqueResult();
		return count;

	}

	public void criarRegistroMarcacaoProcessada(Date dia) {
		session.getTransaction().begin();

		session.createQuery(
				"insert into MarcacaoProcessada (data, colaborador_id) select :dia , Id from Colaborador where dataDemissao is null")
				.setParameter("dia", dia).executeUpdate();
		session.getTransaction().commit();
	}

	public void apagarMarcacaoProcessadaNoDia(Date dia) {

		session.getTransaction().begin();

		session.createQuery("DELETE FROM MarcacaoProcessada where data = :dia")
				.setParameter("dia", dia).executeUpdate();
		session.getTransaction().commit();

		session.getTransaction().begin();
		session.createQuery("DELETE FROM MarcacaoDetalhe where data = :dia")
				.setParameter("dia", dia).executeUpdate();	
		session.getTransaction().commit();
		
		session.flush();
		
		
	}

	@SuppressWarnings("unchecked")
	public List<Marcacao> marcacaoDoDia(Date dia) {
		return session
				.createQuery(
						"from Marcacao where data = :dia order by pis, data , hora")
				.setParameter("dia", dia).list();
	}

	public Integer maiorMarcacaoDoDiaColaborador(Date dia, String pis) {
		Integer resultado = (Integer) session
				.createQuery(
						"select max(hora) from Marcacao m where data=:dia and pis=:pis ")
				.setParameter("dia", dia).setParameter("pis", pis)
				.uniqueResult();
		return resultado;
	}

	public Boolean achaJornadaDia(Date dia, String pis) {
		Boolean achou = false;

		// acho o horario programado para o dia
		HorarioColaborador hc = horarioColaboradorDia(dia, pis);

		String jpql = "select h2.horario_id, h.horario, data_base, h2.jornada_id, tipoRegime, e1, s1, e2, s2, seq  from horario  as h "
				+ " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id "
				+ " left join Jornada AS j   ON h2.Jornada_id = j.Jornada_id "
				+ " where (1=1) and (h.horario_id = :horario) and "
				+ " (h2.seq =  ( SELECT "
				+ " 1 + (SELECT DATEDIFF(:dia ,data_base) from Horario where horario_id=:horario) % vw_contaSeqHorario.Expr1 AS sequencia "
				+ " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
				+ " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id "
				+ " WHERE        (vw_contaSeqHorario.horario_id = :horario) ) ) ";
		
		
		jpql = " select h2.horario_id, h.horario, data_base, h2.jornada_id, tipoRegime, e1, s1, e2, s2, seq  from horario  as h "
				+ " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id "
		        + " left join Jornada AS j   ON h2.Jornada_id = j.Jornada_id "
		        + " where (1=1) and (h.horario_id = :horario) and "
		        + " (h2.seq =  ( SELECT "
		        + " 1 +   mod(   (SELECT  cast (  date_part('day',age(:dia, data_base)) as int) from horario) , (select cast( vw_contaSeqHorario.Expr1 as int) "
	            + " from vw_contaSeqHorario))    AS sequencia "
	            + " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
		        + " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id "
		        + " WHERE   (vw_contaSeqHorario.horario_id = :horario) ) ) ";
		
				
		
		jpql = "  select h2.horario_id, h.horario, data_base, h2.jornada_id, tipoRegime, e1, s1, e2, s2, seq  from horario  as h "
				+ " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id "
	            + " left join Jornada AS j   ON h2.Jornada_id = j.Jornada_id "
	            + " where (1=1) and (h.horario_id = :horario) and "
	            + " (h2.seq =  ( SELECT "
			    + " 1 +  mod( (SELECT  :dia - cast( data_base as date) from Horario where horario_id= h2.horario_id  )  , CAST( vw_contaseqhorario.Expr1 AS BIGINT) ) "
			    + " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
			    + " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id"
			    + " WHERE        (vw_contaSeqHorario.horario_id = h2.horario_id ) ) )";
		
		

		try {
			@SuppressWarnings("unchecked")
			List<Object[]> resultado = session.createSQLQuery(jpql)
					.setParameter("dia", dia)
					.setParameter("horario", hc.getHorario()).list();
			

			for (Object[] colunas : resultado) {
				if (((Integer) colunas[5] == 0) && ((Integer) colunas[6] == 0)
						&& ((Integer) colunas[7] == 0)
						&& ((Integer) colunas[8] == 0)) {
					achou = false;
				} else {
					achou = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return achou;
	}

	public void mudarDataMarcacaoDiaAnterior(Marcacao marcacao, Date dia) {
		session.getTransaction().begin();
		marcacao.setData(dia);
		session.merge(marcacao);
		session.getTransaction().commit();

	}

	public HorarioColaborador horarioColaboradorDia(Date dia, String pis) {

		try {
			String jpql = "select h from HorarioColaborador h inner join fetch h.colaborador  where data_Inicio <= :dia and h.colaborador.pis=:pis  order by  data_Inicio desc";
			HorarioColaborador hc = (HorarioColaborador) session
					.createQuery(jpql)
					.setParameter("dia", dia).setParameter("pis", pis)
					.setMaxResults(1).uniqueResult();
			return hc;
		} catch (Exception e) {
			return null;
		}
	}

	public Integer achaJornadaDiaFechamento(Date dia, String pis) {
		Integer fechamento = 0;

		// acho o horario programado para o dia
		HorarioColaborador hc = horarioColaboradorDia(dia, pis);

		String jpql = " select h2.horario_id, h.horario, data_base, h2.jornada_id, tipoRegime, e1, s1, e2, s2, seq, fechamento  from horario  as h "
				+ " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id "
				+ " left join Jornada AS j   ON h2.Jornada_id = j.Jornada_id "
				+ " where (1=1) and (h.horario_id = :horario) and "
				+ " (h2.seq =  ( SELECT "
				+ " 1 + (SELECT DATEDIFF(:dia ,data_base) from Horario where horario_id=:horario) % vw_contaSeqHorario.Expr1 AS sequencia "
				+ " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
				+ " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id "
				+ " WHERE        (vw_contaSeqHorario.horario_id = :horario) ) ) ";
		
		
		
		jpql = " select h2.horario_id, h.horario, data_base, h2.jornada_id, tipoRegime, e1, s1, e2, s2, seq, fechamento  from horario  as h "
				+ " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id "
				+ " left join Jornada AS j   ON h2.Jornada_id = j.Jornada_id "
				+ " where (1=1) and (h.horario_id = :horario ) and "
				+ " (h2.seq =  ( SELECT "
				+ "     1 +   mod(   (SELECT  cast (  date_part('day',age(:dia, data_base)) as int) from horario) , (select cast( vw_contaSeqHorario.Expr1 as int) "
				+ "     from vw_contaSeqHorario))    AS sequencia "
				+ " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
				+ " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id "
				+ " WHERE        (vw_contaSeqHorario.horario_id = :horario) ) ) ";
		
		
		
		jpql = " select h2.horario_id, h.horario, data_base, h2.jornada_id, tipoRegime, e1, s1, e2, s2, seq, fechamento  from horario  as h "
				+ " inner join horario_jornada AS h2  ON h.horario_id = h2.horario_id "
		 + " left join Jornada AS j   ON h2.Jornada_id = j.Jornada_id "
		 + " where (1=1) and (h.horario_id = :horario) and "
         + "                (h2.seq =  ( SELECT "
		 + " 1 +  mod( (SELECT  :dia - cast( data_base as date) from Horario where horario_id= h2.horario_id  )  , CAST( vw_contaseqhorario.Expr1 AS BIGINT) )"
		 + " FROM    vw_contaSeqHorario INNER JOIN vw_totdiasDtBaseHohe "
		 + " ON vw_contaseqhorario.horario_id = vw_totdiasDtBaseHohe.horario_id"
		 + " WHERE        (vw_contaSeqHorario.horario_id = h2.horario_id ) ) )";
		
		
		

		try {
			@SuppressWarnings("unchecked")
			List<Object[]> resultado = session.createSQLQuery(jpql)
					.setParameter("dia", dia)
					.setParameter("horario", hc.getHorario()).list();

			for (Object[] colunas : resultado) {
				if (((Integer) colunas[5] == 0) && ((Integer) colunas[6] == 0)
						&& ((Integer) colunas[7] == 0)
						&& ((Integer) colunas[8] == 0)) {
					fechamento = (Integer) colunas[10];
				} else {
					fechamento = (Integer) colunas[10];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fechamento;
	}

}
