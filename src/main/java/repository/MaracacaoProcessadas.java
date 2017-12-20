package repository;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;

import model.Colaborador;
import model.MarcacaoProcessada;
import model.TipoDia;

public class MaracacaoProcessadas implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "safiraPU")
	private Session session;

	public void apagarOcorrenciaApuradaNoDia(Date dia) {

		session.getTransaction().begin();
		session.createQuery("DELETE FROM OcorrenciaApurada where data = :dia").setParameter("dia", dia).executeUpdate();
		session.getTransaction().commit();
		session.flush();
	}

	@Transactional
	public void salvaTipoDia(Colaborador c, Date dia, TipoDia td) {
		System.out.println("salvaTipoDia " + td + "  " + dia);

		// String jpql = "select m from MarcacaoProcessada m where colaborador_id =
		// :cola and data=:dia";
		// MarcacaoProcessada mp = (MarcacaoProcessada) session.createSQLQuery(jpql)
		// .setParameter("cola", c.getId())
		// .setParameter("dia", dia)
		// .setMaxResults(1)
		// .list();

		String jpql = "select m from MarcacaoProcessada m where colaborador_id = :cola and data=:dia";
		MarcacaoProcessada mp = (MarcacaoProcessada) session.createQuery(jpql).setParameter("cola", c.getId())
				.setParameter("dia", dia).uniqueResult();

		mp.setTipoDia(td);

		session.getTransaction().begin();
		session.merge(mp);
		session.getTransaction().commit();
		session.flush();
	}

}
