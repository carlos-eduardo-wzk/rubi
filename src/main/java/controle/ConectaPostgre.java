package controle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConectaPostgre {
 // private static final String URL ="jdbc:postgresql://ec2-54-228-246-19.eu-west-1.compute.amazonaws.com:5432/dbi8qgecsrot6n?sslmode=require&user=vkszfpszigcsjo&password=Xcz_2Ajab_0_7cPM1q0LjJglle";
   private static final String URL ="jdbc:postgresql://127.0.0.1:5432/postgres?user=postgres&password=postgres";
	
	
	//private static final String USER ="urkvtogicoyclx";
	//private static final String SENHA ="";
	
	public static final Connection obtemConexao() throws SQLException{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("erro ao tentar conectar");
			System.out.println(e.getMessage());
		}		
		//return DriverManager.getConnection(URL,user,senha);
		return DriverManager.getConnection(URL);				
	}	
	
	
}
