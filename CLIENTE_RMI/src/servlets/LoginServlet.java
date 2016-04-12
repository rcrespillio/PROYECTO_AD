package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




import org.json.simple.JSONObject;

import repo.LogeadoDTO;
import repo.UsuarioActivo;
import controlador.Controlador;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("email");    
	    String pwd = request.getParameter("password");
	    
	   boolean resultado = false;
	   
	   System.out.println("usuario ingresado: "+user+"password: "+pwd);
	   
		
			
	   
	   
	   Controlador con = Controlador.getControlador();
	  
	  // resultado = con.existeUsuario(user, pwd);
	   
	   /**antes se devolvia un booleano si el usuario existia,
	   ahora se realiza el siguiente procedimiento:
	   se traen los datos unicamente que interesan para el usuario logeado
	   se traen el LogeadoDTO con el apodo y el estado.
	   y el servidor se encarga de agregarlo a la lista de activos
	   se trae el apodo ya que se guarda en una cookie.
	   
	   
	   **/
	  
	   LogeadoDTO usuario = con.validarYobtenerUsuario(user,pwd);
	   
	   if(usuario!=null){
		   HttpSession session = request.getSession(true);
		   System.out.println("valida el usuario, logeadoDTO apodo: "+usuario.getApodo());
		   session.setAttribute("apodo",usuario.getApodo());
		   
		   response.sendRedirect("success.jsp"); 
		   //request.getRequestDispatcher("success.jsp").forward(request, response);
	    } else {
	    	 response.sendRedirect("error.html");
	    }
		
	}

}
