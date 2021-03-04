package pe.igh.app.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import pe.igh.app.db.AccesoDB;

/**
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/c/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 */
public class CuentaImpl  {


	public void registrarMovimiento(String cuenta, Double importe, String codEmp) {
		Connection cn = null;
		try {
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			String sql = "{call usp_egcc_deposito(?,?,?)}";
			CallableStatement cstm = cn.prepareCall(sql);
      cstm.setString(1, cuenta); 
      cstm.setDouble(2, importe); 
      cstm.setString(3, codEmp); 
      cstm.executeUpdate();
			cstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("Error de acceso a la tabla Cliente.");
		} finally {
			try {
				cn.close();
			} catch (Exception e) {
			}
		}
	}

}
