package rmi;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;













import negocio.AdministradorDeUsuarios;
import dao.UsuarioDAO;
import entities.Entity_Usuario;
import repo.LogeadoDTO;
import repo.UsuarioDTO;         // del repo
import rmi_interface.Interface; //importada del repo



public class InterfazServer extends UnicastRemoteObject implements Interface {
    
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    
    static Logger logger;

    public InterfazServer() throws RemoteException {
        logger = Logger.getLogger(getClass().getName());
        logger.log(Level.INFO, "Se ha instanciado la clase de Implementacion del Servidor");
       
       
    }

    /*
     * Debo escribir todos los métodos que se encuentran en la interface
     */
    // Por cada metodo se escribe Override que se utiliza para que utilize este metodo en vez del metodo del padre
   
    @Override
    public boolean existeUsuario(String email, String pwd){
      
          if(UsuarioDAO.getInstancia().existeUsuario(email,pwd)){
        	  return true;
          
	      }else{
	    	  return false; 
	      }
        
      
    }

	@Override
	public synchronized boolean verificarApodoEnUso(String apodo) throws RemoteException {
		
		System.out.println("VERIFICANDO EN SERVIDOR EL APODO: "+apodo);
		
		if(UsuarioDAO.getInstancia().verificarApodoEnUso(apodo)){
			System.out.println("verificado apodo y existe...");
			return true;
		}
	        
	     return false;   
	}

	@Override
	public boolean registrarUsuario(String apodo, String email, String pwd) {
		
		System.out.println("estoy en la implr del server registrar usuario... creando entity..");
		Entity_Usuario usr = new Entity_Usuario(apodo,email,pwd);
		if(UsuarioDAO.getInstancia().agregarUsuario(usr)){
			System.out.println("agrego satisfactoriamente...");
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public LogeadoDTO validarYobtenerUsuario(String user, String pwd)
			 {
		
		LogeadoDTO logeado = AdministradorDeUsuarios.getInstance().validarYobtenerUsuario(user,pwd);
		
		if(logeado!=null){
			return logeado;
		}else{
			return  null;
		}
      	  
		
		
	}

	@Override
	public boolean vincularSessionUsuario(String id, String apodo)
			throws RemoteException {
		System.out.println("estoy en la interfaz servidor vinculando sesion con usuario");
		return AdministradorDeUsuarios.getInstance().vincularSessionUsuario(id,apodo);
		
	}

	@Override
	public LogeadoDTO obtenerUsuarioLogeado(String idSession)
			throws RemoteException {
		
		LogeadoDTO logeado = AdministradorDeUsuarios.getInstance().obtenerUsuarioLogeado(idSession);
		return logeado;
	}
    
}
