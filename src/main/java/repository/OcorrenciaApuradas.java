package repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import model.Acerto;
import model.AcertoAbono;
import model.Colaborador;
import model.MotivoAbono;
import model.Ocorrencia;
import model.OcorrenciaApurada;
import model.TipoDia;
import model.TipoOcorrencia;

@Stateless
public class OcorrenciaApuradas implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "safiraPU")
	private Session session;

	@Inject
	private Colaboradores colaboradores;

	@Inject
	private Ocorrencias ocorrencias;

	public OcorrenciaApurada achaOcorrenciaApuradaDataColaboradorOcorrencia2(Long oco, AcertoAbono acerto) {
		String jpql = "select * from OcorrenciaApurada o where data = :dia and colaborador_id =:cola and ocorrencia_id = :ocorr ";

		Object[] resultado = (Object[]) session.createSQLQuery(jpql).setParameter("dia", acerto.getData())
				.setParameter("cola", acerto.getColaborador_id()).setParameter("ocorr", oco).setMaxResults(1)
				.uniqueResult();

		OcorrenciaApurada oa = new OcorrenciaApurada();
		if (resultado != null) {

			oa.setId((Integer) resultado[0]); // id
			oa.setDataAbono((Date) resultado[1]);
			oa.setData((Date) resultado[2]);
			oa.setHoras((Integer) resultado[3]);
			oa.setHorasAbonadas((Integer) resultado[4]);

			if (String.valueOf(resultado[5]).equals("NORMAL")) {
				oa.setTipoDia(TipoDia.NORMAL);
			}

			if (String.valueOf(resultado[5]).equals("DEMITIDO")) {
				oa.setTipoDia(TipoDia.DEMITIDO);
			}

			if (String.valueOf(resultado[5]).equals("AFASTADO")) {
				oa.setTipoDia(TipoDia.AFASTADO);
			}
			if (String.valueOf(resultado[5]).equals("FALTA")) {
				oa.setTipoDia(TipoDia.FALTA);
			}

			// ocorrencia
			if (String.valueOf(resultado[6]).equals("FALTA")) {
				oa.setTipoOcorrencia(TipoOcorrencia.FALTA);
			}

			if (String.valueOf(resultado[6]).equals("ATRASO")) {
				oa.setTipoOcorrencia(TipoOcorrencia.ATRASO);
			}
			if (String.valueOf(resultado[6]).equals("SAIDA_ANTECIPADA")) {
				oa.setTipoOcorrencia(TipoOcorrencia.SAIDA_ANTECIPADA);
			}
			if (String.valueOf(resultado[6]).equals("HORA_NORMAL")) {
				oa.setTipoOcorrencia(TipoOcorrencia.HORA_NORMAL);
			}

			oa.setColaborador((Colaborador) colaboradores.porId(convertToLong(resultado[7])));
			oa.setMotivoAbono((MotivoAbono) resultado[8]);
			oa.setOcorrencia((Ocorrencia) ocorrencias.porId((int) resultado[9]));
		}
		return (OcorrenciaApurada) oa;

	}

	public OcorrenciaApurada achaOcorrenciaApuradaDataColaboradorOcorrencia(Acerto acerto) {

		// String jpql =
		// "select * from OcorrenciaApurada o where data = :dia and colaborador_id
		// =:cola and ocorrencia_id = :ocorr ";
		// OcorrenciaApurada oa = (OcorrenciaApurada) session
		// .createSQLQuery(jpql)
		// .setParameter("dia", acerto.getData())
		// .setParameter("cola", acerto.getColaborador_id())
		// .setParameter("ocorr", acerto.getOcorrencia_id())
		// .setMaxResults(1)
		// .list();
		//

		// String jpql =
		// "select * from OcorrenciaApurada o where data = :dia and colaborador_id
		// =:cola and ocorrencia_id = :ocorr ";

		// Object[] resultado = (Object[]) session.createSQLQuery(jpql)
		// .setParameter("dia", acerto.getData())
		// .setParameter("cola", acerto.getColaborador_id())
		// .setParameter("ocorr", acerto.getOcorrencia_id()).setMaxResults(1)
		// .uniqueResult();

		String jpql = "select * from OcorrenciaApurada o where data = :dia and colaborador_id =:cola and ocorrencia_id = :ocorr ";

		Object[] resultado = (Object[]) session.createSQLQuery(jpql).setParameter("dia", acerto.getData())
				.setParameter("cola", acerto.getColaborador_id()).setParameter("ocorr", acerto.getOcorrencia_id())
				.setMaxResults(1).uniqueResult();

		OcorrenciaApurada oa = new OcorrenciaApurada();

		if (resultado != null) {
			System.out.println(resultado[0]);

			// System.out.println("achei OcorrenciaApurada ");
			// System.out.println(resultado[0]);
			// System.out.println(resultado[1]);
			// System.out.println(resultado[2]);
			// System.out.println(resultado[3]);
			// System.out.println(resultado[4]);
			// System.out.println(resultado[5]);
			// System.out.println(resultado[6]);
			// System.out.println(resultado[7]);
			// System.out.println(resultado[8]);
			// System.out.println(resultado[9]);

			// achar colaborador

			// achar ocorrencia

			oa.setId((Integer) resultado[0]); // id
			oa.setDataAbono((Date) resultado[1]);
			oa.setData((Date) resultado[2]);
			oa.setHoras((Integer) resultado[3]);
			oa.setHorasAbonadas((Integer) resultado[4]);

			if (String.valueOf(resultado[5]).equals("NORMAL")) {
				oa.setTipoDia(TipoDia.NORMAL);
			}

			if (String.valueOf(resultado[5]).equals("DEMITIDO")) {
				oa.setTipoDia(TipoDia.DEMITIDO);
			}

			if (String.valueOf(resultado[5]).equals("AFASTADO")) {
				oa.setTipoDia(TipoDia.AFASTADO);
			}
			if (String.valueOf(resultado[5]).equals("FALTA")) {
				oa.setTipoDia(TipoDia.FALTA);
			}

			// ocorrencia
			if (String.valueOf(resultado[6]).equals("FALTA")) {
				oa.setTipoOcorrencia(TipoOcorrencia.FALTA);
			}

			if (String.valueOf(resultado[6]).equals("ATRASO")) {
				oa.setTipoOcorrencia(TipoOcorrencia.ATRASO);
			}
			if (String.valueOf(resultado[6]).equals("SAIDA_ANTECIPADA")) {
				oa.setTipoOcorrencia(TipoOcorrencia.SAIDA_ANTECIPADA);
			}
			if (String.valueOf(resultado[6]).equals("HORA_NORMAL")) {
				oa.setTipoOcorrencia(TipoOcorrencia.HORA_NORMAL);
			}

			oa.setColaborador((Colaborador) colaboradores.porId(convertToLong(resultado[7])));
			oa.setMotivoAbono((MotivoAbono) resultado[8]);
			oa.setOcorrencia((Ocorrencia) ocorrencias.porId((int) resultado[9]));
		}

		return (OcorrenciaApurada) oa;
	}

	public void salvaOcorrenciaApuradas(OcorrenciaApurada ocorrenciaApurada) {

		// session.getTransaction().begin();
		session.merge(ocorrenciaApurada);
		// session.getTransaction().commit();

	}

	public void apagaOcorrenciaDia(Date di, Date df) {

		// session.getTransaction().begin();
		session.createQuery("DELETE FROM OcorrenciaApurada where data >=:di and data <= :df").setParameter("di", di)
				.setParameter("df", df).executeUpdate();
		// session.getTransaction().commit();
		// session.flush();

	}

	public void apagaOcorrenciaDiaColaborador(Date di, Date df, Colaborador cola) {

		// session.getTransaction().begin();
		session.createQuery(
				"DELETE FROM OcorrenciaApurada where data >=:di and data <= :df and colaborador_id = :cola_id")
				.setParameter("di", di).setParameter("df", df).setParameter("cola_id", cola.getId()).executeUpdate();
		// session.getTransaction().commit();
		// session.flush();

	}

	public void salvaTipoDia(Colaborador c, Date dia, TipoDia td) {
		System.out.println("salvaTipoDia em apura ocorrencia" + td);

		String jpql = "select m from OcorrenciaApurada m where colaborador_id = :cola and data=:dia";
		OcorrenciaApurada oa = (OcorrenciaApurada) session.createQuery(jpql).setParameter("cola", c.getId())
				.setParameter("dia", dia).setMaxResults(1).list();

		oa.setTipoDia(td);

		// session.getTransaction().begin();
		session.merge(oa);
		// session.getTransaction().commit();
		// session.flush();
	}

	public List<Object[]> achaOcorrenciasApuradasDataColaboradorDia(Date dia, Colaborador cola) {

		String jpql = "select   ocorrenciaapurada.horas, ocorrencia.ocorrencia "
				+ " FROM  ocorrenciaapurada,  ocorrencia " + " WHERE  ocorrenciaapurada.ocorrencia_id = ocorrencia.id "
				+ " and data = :dia and colaborador_id =:cola ";

		@SuppressWarnings("unchecked")
		List<Object[]> resultado = session.createSQLQuery(jpql).setParameter("dia", dia).setParameter("cola", cola)
				.list();

		return resultado;
	}

	public static Long convertToLong(Object o) {
		String stringToConvert = String.valueOf(o);
		Long convertedLong = Long.parseLong(stringToConvert);
		return convertedLong;

	}

}
