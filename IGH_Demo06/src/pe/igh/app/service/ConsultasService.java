package pe.igh.app.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;
import pe.igh.app.db.AccesoDB;

/**
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/c/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 */
public class ConsultasService {

	public List<Map<String, ?>> traerMovimientoCuenta(String cuenta) {
		List<Map<String, ?>> lista;
		Connection cn = null;
		try {
			cn = AccesoDB.getConnection();
			String sql = "select " + "    mo.chr_cuencodigo cuenta," + "    mo.int_movinumero nromov,"
					+ "    to_char(mo.dtt_movifecha,'DD/MM/YYYY') fecha," + "    tm.vch_tipodescripcion tipo,"
					+ "    mo.dec_moviimporte importe " + "from tipomovimiento tm "
					+ "join movimiento mo on tm.chr_tipocodigo = mo.chr_tipocodigo " + "where  mo.chr_cuencodigo = ? "
					+ "order by mo.int_movinumero ";
			PreparedStatement pstm = cn.prepareStatement(sql);
			pstm.setString(1, cuenta);
			ResultSet rs = pstm.executeQuery();
			lista = JdbcUtil.rsToList(rs);
			rs.close();
			pstm.close();
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
		return lista;
	}

	public List<Map<String, ?>> traerMovimientoCuenta2(String cuenta) {
		List<Map<String, ?>> lista;
		Connection cn = null;
		try {
			cn = AccesoDB.getConnection();
			String sql = "{call usp_egcc_movimientos(?,?)}";
			CallableStatement cstm = cn.prepareCall(sql);
			cstm.setString(1, cuenta);
			cstm.registerOutParameter(2, OracleTypes.CURSOR);
			cstm.executeUpdate();
			ResultSet rs = (ResultSet) cstm.getObject(2);
			lista = JdbcUtil.rsToList(rs);
			rs.close();
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
		return lista;
	}

}
