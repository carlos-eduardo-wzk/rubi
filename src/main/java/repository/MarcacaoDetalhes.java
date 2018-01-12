package repository;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.hibernate.Session;

import model.MarcacaoDetalhe;
import model.MarcacaoOriginal;

@Stateless
public class MarcacaoDetalhes implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "safiraPU")
	private Session session;

	public void salvaMarcacaoDetalhe(MarcacaoDetalhe marcacaoDetalhe) {
		
		//session.getTransaction().begin();
		session.saveOrUpdate(marcacaoDetalhe);
		
		//session.getTransaction().commit();
		//session.flush();
		
	}

	public void apagarMarcacaoDetalheNoPeriodo(Date di, Date df) {
		
		//session.getTransaction().begin();
		session.createQuery(
				"DELETE FROM MarcacaoDetalhe where data >= :di and data <= :df")
				.setParameter("di", di).setParameter("df", df).executeUpdate();
		//session.getTransaction().commit();
		//session.flush();
	}

	public MarcacaoDetalhe achMarcacaoDetalheDoColaboradorNoDiaPis(Date dia,
			String pis, int hora) {
		String jpql = "select m from MarcacaoDetalhe m inner join fetch m.colaborador where  m.colaborador.pis = :pis and data = :dia and hora = :hora";
		MarcacaoDetalhe oa = (MarcacaoDetalhe) session.createQuery(jpql)
				.setParameter("dia", dia).setParameter("pis", pis)
				.setParameter("hora", hora).setMaxResults(1).uniqueResult();

		return (MarcacaoDetalhe) oa;
	}

	public MarcacaoOriginal MarcacoesDoColaboradorOriginalNoDiaPorPis(
			Date dia, String pis) {

		String jpql = "select 0 as Id, m.* from vw_marcdiacolaboradororiginal m left join colaborador co "
			       + " on (m.colaborador_id = co.id) where data =:dia and co.pis = :pis";
				
		try{
			MarcacaoOriginal resultado = (MarcacaoOriginal) session
					.createSQLQuery(jpql)
					.addEntity(MarcacaoOriginal.class)
					.setParameter("dia", dia).setParameter("pis", pis)
					.setMaxResults(1)
					.uniqueResult();
				return  resultado;
			
		}
		catch( PersistenceException e){
			System.out.println(e.getMessage());
			return  null;
		}
		

		
	}

}
