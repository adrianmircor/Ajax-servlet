package pe.igh.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pe.igh.app.model.TablaItem;
import pe.igh.app.service.TablaService;

@WebServlet({ "/Suma", "/Resta", "/TablaMultiplicar", "/TablaMultiplicar2" })
public class Calculadora extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getServletPath();

		switch (path) {
		case "/Suma":
			suma(request, response);
			break;
		case "/Resta":
			resta(request, response);
			break;
		case "/TablaMultiplicar":
			tablaMultiplicar(request, response);
			break;
		case "/TablaMultiplicar2":
			tablaMultiplicar2(request, response);
			break;
		}

	}
	
	private void tablaMultiplicar2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Captura de datos
		int num = Integer.parseInt(request.getParameter("num"));
		// Proceso
		TablaService tablaService = new TablaService();
		List<TablaItem> lista = tablaService.multiplicar(num);
		// JSON
		Gson gson = new Gson();
		String jsonRpta = gson.toJson(lista);
		// Reporte
		ServletUtil.jsonResponse(response, jsonRpta);
	}

	private void tablaMultiplicar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Captura de datos
		int num = Integer.parseInt(request.getParameter("num"));
		// Proceso
		TablaService tablaService = new TablaService();
		List<TablaItem> lista = tablaService.multiplicar(num);
		// Reporte
		request.setAttribute("lista", lista);
		ServletUtil.forward(request, response, "tabla.jsp");
	}

	private void resta(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Captura de datos
		int n1 = Integer.parseInt(request.getParameter("n1"));
		int n2 = Integer.parseInt(request.getParameter("n2"));
		// Proceso
		int rpta = n1 - n2;
		// Reporte
		request.setAttribute("titulo", "RESTA");
		request.setAttribute("n1", n1);
		request.setAttribute("n2", n2);
		request.setAttribute("rpta", rpta);
		ServletUtil.forward(request, response, "calculadora.jsp");
	}

	private void suma(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Captura de datos
		int n1 = Integer.parseInt(request.getParameter("n1"));
		int n2 = Integer.parseInt(request.getParameter("n2"));
		// Proceso
		int rpta = n1 + n2;
		// Reporte
		request.setAttribute("titulo", "SUMA");
		request.setAttribute("n1", n1);
		request.setAttribute("n2", n2);
		request.setAttribute("rpta", rpta);
		ServletUtil.forward(request, response, "calculadora.jsp");
	}

}
