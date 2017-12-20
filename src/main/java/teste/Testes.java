package teste;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class Testes implements Serializable {

	private static final long serialVersionUID = -2502125276729484756L;

	@PersistenceContext(unitName = "safiraPU")
	private  EntityManager em;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("teste ...");

		try {
			DateFormat formatter = new SimpleDateFormat("ddMMyyyy");

			Date date = (Date) formatter.parse("01012017");
			System.out.println(date);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

//		Session session = em.unwrap(Session.class);
//
//		try {
//
//			Usuario usuario = (Usuario) session.createQuery("from Usuario where lower(email) = :email")
//					.setParameter("email", "paula@gmail.com").uniqueResult();
//		} catch (NoResultException e) {
//			// nenhum encontado com email
//		}

		//
		// try {
		//
		// emp = (Usuario) session.createQuery("from Usuario where lower(email) =
		// :email")
		// .setParameter("email", "paula@gmail.com").uniqueResult();
		// System.out.println("emp2 " + emp.getNome());
		// } catch (NoResultException e) {
		// // nenhum encontado com email
		// }

	}

}
