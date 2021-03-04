package pe.igh.app.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtil {
	
	private ServletUtil() {
	}

	static void forward(
			HttpServletRequest request, 
			HttpServletResponse response, 
			String destino) throws ServletException, IOException{
		
		RequestDispatcher rd = request.getRequestDispatcher(destino);
		rd.forward(request, response);
	}
	
	static void jsonResponse(
			HttpServletResponse response, 
			String stringJson) throws IOException{
		//Envio al browser(cliente)
		PrintWriter out = response.getWriter();
		response.setContentType("application/json"); //Le indica al navegador qué tipo de dato quiere que muestre
		response.setCharacterEncoding("UTF-8");
		out.print(stringJson);
		out.flush();
	}
}
