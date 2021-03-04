package pe.igh.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pe.igh.app.service.ConsultasService;

/**
 * Servlet implementation class CuentaController
 */
@WebServlet({ "/CuentaController", "/hhh" })
public class CuentaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CuentaController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();

		switch (path) {
		case "/CuentaController":
			consultarCuenta(request, response);
			break;
		}
		
	}
	
	private void consultarCuenta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Captura de datos
		String numCuenta = request.getParameter("cuenta"); //Captura el parametro, del pedido que le hizo el cliente
		// Proceso
		ConsultasService consultasService = new ConsultasService();
		List<Map<String, ?>> listaMovimientos = consultasService.traerMovimientoCuenta(numCuenta);
		// JSON
		Gson gson = new Gson();
		String jsonRpta = gson.toJson(listaMovimientos);
		System.out.println("->"+jsonRpta); //Convierte en JSON {"clave" : "valor"}
		// Reporte
		ServletUtil.jsonResponse(response, jsonRpta);
		
		/*En esta petición, el servidor le está respondiendo al cliente
		con un array de JSON*/
		
		//Si es GET: el cliente envía el valor del form por el URL, igual lo captura con lin 49
		//Si es POST: el cliente envía el valor del form por el body, igual lo captura con lin 49
	}

}
