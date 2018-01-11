package teste;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Stateful;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

@Stateful
public class Testes implements Serializable {

	private static final long serialVersionUID = -2502125276729484756L;

	 @PersistenceContext(unitName = "safiraPU")
	 private static Session session;

	

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



		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			df.setLenient(false);
			Date dt = df.parse("01/01/2018");

			session.createQuery("DELETE FROM Marcacao where data >=:di and data <= :df").setParameter("di", dt)
					.setParameter("df", dt).executeUpdate();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// session.createQuery(
		// "DELETE FROM Marcacao where data >=:di and data <= :df")
		// .setParameter("di", di).setParameter("df", df).executeUpdate();

	}

}
