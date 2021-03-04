package pe.igh.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/c/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 */
public class AccesoDB {

	// Para que no se pueda crear instancias de esta clase
	private AccesoDB() {
	}

	// Retorna un objeto de tipo Connection
	public static Connection getConnection() throws SQLException {
		Connection cn = null;
		try {
			// Paso 1: Cargar el driver a memoria
			Class.forName("oracle.jdbc.OracleDriver").getDeclaredConstructor().newInstance();
			// Paso 2: Obtener el objeto Connection
			String url = "jdbc:oracle:thin:@localhost:1521/orcl";
			cn = DriverManager.getConnection(url, "eureka", "admin");
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw new SQLException("No se encontró el driver de la base de datos.");
		} catch (Exception e) {
			throw new SQLException("No se puede establecer la conexión con la BD.");
		}
		return cn;
	}

}
