package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controlador.Controlador;

/**
 * Servlet implementation class checkApodoServlet
 */
@WebServlet("/checkApodoServlet")
public class RegistracionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<String> ar;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistracionServlet() {
        super();
      
        ar = new ArrayList<String>();     
        ar.add("PEPE");
        }
        // TODO Auto-generated constructor stub
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String apodo = request.getParameter("id");
        System.out.println(apodo);
        
        Controlador con = Controlador.getControlador();
        
        if (!con.verificarApodoEnUso(apodo)) {  //apodo no en uso.....
        	
        	 response.setContentType("text/xml");
             response.setHeader("Cache-Control", "no-cache");
             response.getWriter().write("false");
        	
        } else {
          
        	 response.setContentType("text/xml");
             response.setHeader("Cache-Control", "no-cache");
             response.getWriter().write("true");
            
           
        }
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean resultado = false;
		
		String apodo = request.getParameter("apodo");
		String email = request.getParameter("email");    
	    String pwd = request.getParameter("password");
	    
	   if(apodo!=null && email!=null & pwd!=null){
		   
		   
		   Controlador con = Controlador.getControlador();
           
		   resultado = con.registrarUsuario(apodo, email, pwd);
           
		   if(resultado==true){
        	  
			 
			 //obtengo session, como no hay porque recien se registro se crea automaticamente > cookie (JSESSIONID)
			   HttpSession session = request.getSession(true);
			   //le pongo el apodo que creo en una cookie
			    session.setAttribute("apodo",apodo);
			  
			 
			   
			   //sendredirect manda a una nueva pagina, no confundir con requestdispacher (ver diferencias) 
			   response.sendRedirect("exito.html");  
			   
           }else{
        	   response.sendRedirect("error.html");
           }
	   
	   }else{
		   response.sendRedirect("error.html");  
	   }
		   
	   
		
	}
}
