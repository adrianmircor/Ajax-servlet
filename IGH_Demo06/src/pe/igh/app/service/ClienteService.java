package pe.igh.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pe.igh.app.db.AccesoDB;
import pe.igh.app.model.Cliente;


/**
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/c/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 */
public class ClienteService implements RowMapper<Cliente> {


	public Cliente traer(String idCliente) {
		Cliente cliente = null;
		Connection cn = null;
		try {
			cn = AccesoDB.getConnection();
			String sql = "select chr_cliecodigo, vch_cliepaterno, vch_cliematerno, "
							+ "vch_clienombre, chr_cliedni, vch_clieciudad, "
							+ "vch_cliedireccion, vch_clietelefono, vch_clieemail "
							+ "from cliente "
							+ "where chr_cliecodigo = ?";
			PreparedStatement pstm = cn.prepareStatement(sql);
			pstm.setString(1, idCliente);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				cliente = mapRow(rs);
			}
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
		return cliente;

	} // Fin de traer


	public List<Cliente> traer(String paterno, String materno, String nombre) {
		List<Cliente> lista = new ArrayList<>();
		Connection cn = null;
		paterno = "%" + paterno + "%";
		materno = "%" + materno + "%";
		nombre = "%" + nombre + "%";
		try {
			cn = AccesoDB.getConnection();
			String sql = "select chr_cliecodigo, vch_cliepaterno, vch_cliematerno, "
							+ "vch_clienombre, chr_cliedni, vch_clieciudad, "
							+ "vch_cliedireccion, vch_clietelefono, vch_clieemail "
							+ "from cliente "
							+ "where lower(vch_cliepaterno) like lower(?) "
							+ "and lower(vch_cliematerno) like lower(?) "
							+ "and lower(vch_clienombre) like lower(?) ";
			PreparedStatement pstm = cn.prepareStatement(sql);
			pstm.setString(1, paterno);
			pstm.setString(2, materno);
			pstm.setString(3, nombre);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Cliente cliente = mapRow(rs);
				lista.add(cliente);
			}
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


	public void inertar(Cliente cliente) {
		Connection cn = null;
		try {
			// Variables
			String sql, codigo;
			ResultSet rs;
			PreparedStatement pstm;
			Statement stm;
			int cont, longitud;
			// Inicio de tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false); // Inicia la Tx
			// Obtener contador de sucursal
			sql = "select int_contitem, int_contlongitud from contador "
							+ "where vch_conttabla = 'Cliente' for update";
			stm = cn.createStatement();
			rs = stm.executeQuery(sql);
			rs.next();
			cont = rs.getInt("int_contitem") + 1;
			longitud = rs.getInt("int_contlongitud");
			rs.close();
			// Crear codigo
			String formato = "";
			for (int i = 1; i <= longitud; i++) {
				formato += "0";
			}
			DecimalFormat df = new DecimalFormat(formato);
			codigo = df.format(cont);
			// Insertar cliente
			sql = "insert into cliente(chr_cliecodigo,vch_cliepaterno,vch_cliematerno,"
							+ "vch_clienombre,chr_cliedni,vch_clieciudad,vch_cliedireccion,"
							+ "vch_clietelefono,vch_clieemail) values(?,?,?,?,?,?,?,?,?)";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, codigo);
			pstm.setString(2, cliente.getPaterno());
			pstm.setString(3, cliente.getMaterno());
			pstm.setString(4, cliente.getNombre());
			pstm.setString(5, cliente.getDni());
			pstm.setString(6, cliente.getCiudad());
			pstm.setString(7, cliente.getDireccion());
			pstm.setString(8, cliente.getTelefono());
			pstm.setString(9, cliente.getEmail());
			pstm.executeUpdate();
			pstm.close();
			// Actualizar contador
			sql = "update contador set int_contitem = int_contitem + 1 "
							+ "where vch_conttabla='Cliente'";
			stm.executeUpdate(sql);
			stm.close();
			cn.commit(); // Confirma Tx
			cliente.setCodigo(codigo);
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (Exception e1) {
			}
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			try {
				cn.rollback();
			} catch (Exception e1) {
			}
			throw new RuntimeException("Error en proceso de creación de cuenta.");
		} finally {
			try {
				cn.close();
			} catch (Exception e) {
			}
		}

	}


	public void actualizar(Cliente cliente) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	public void eliminar(String idCliente) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	public Cliente mapRow(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setCodigo(rs.getString("chr_cliecodigo"));
		cliente.setPaterno(rs.getString("vch_cliepaterno"));
		cliente.setMaterno(rs.getString("vch_cliematerno"));
		cliente.setNombre(rs.getString("vch_clienombre"));
		cliente.setDni(rs.getString("chr_cliedni"));
		cliente.setCiudad(rs.getString("vch_clieciudad"));
		cliente.setDireccion(rs.getString("vch_cliedireccion"));
		cliente.setTelefono(rs.getString("vch_clietelefono"));
		cliente.setEmail(rs.getString("vch_clieemail"));
		return cliente;
	}

}
