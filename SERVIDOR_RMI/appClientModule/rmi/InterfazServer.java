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
import repo.UsuarioActivo;         // del repo
import rmi_interface.Interface; //importada del repo



public class InterfazServer extends UnicastRemoteObject implements Interface {
	
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private static InterfazServer interfazServer;
    static Logger logger;

    

    private InterfazServer() throws RemoteException{
    	 logger = Logger.getLogger(getClass().getName());
         logger.log(Level.INFO, "Se ha instanciado la clase de Implementacion del Servidor");
    	
    }
    
    public static InterfazServer getInstance() throws RemoteException{
    	if(interfazServer==null){
    		interfazServer = new InterfazServer();
    		return interfazServer;
    		
    	}else{
    		return interfazServer;
    	}
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

	@Override
	public boolean crearLibreIndividual(String apodo, String jSessionID,
			String sessionSocket) throws RemoteException {
		System.out.println("CLIENTE CONTROLADOR, CREAR INDV: sessionid:"+jSessionID);
		boolean enLista = AdministradorDeUsuarios.getInstance().crearLibreIndividual(apodo,jSessionID,sessionSocket);
		if(enLista){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean validarUsuarioActivo(String apodo, String jSessionID,
			String sessionSocket) throws RemoteException {
		boolean logeado = AdministradorDeUsuarios.getInstance().validarUsuarioActivo(apodo,jSessionID,sessionSocket);
		if(logeado){
			return true;
		}else{
			return false;
		}
	}

	

	@Override
	public ArrayList<LogeadoDTO> hayParaIndividual() throws RemoteException {
		
		
		return AdministradorDeUsuarios.getInstance().hayParaIndividual();
	}

	@Override
	public boolean confirmarUsuarioEnEsperaIndividual(String jSessionID, String apodo)
			throws RemoteException {

         return AdministradorDeUsuarios.getInstance().confirmarUsuarioEnEsperaIndividual(jSessionID,apodo);
	}

	@Override
	public boolean vincularJSessionIDconUsuario(String jsessionID, String apodo)
			throws RemoteException {

           return AdministradorDeUsuarios.getInstance().vincularJSessionIDconUsuario(jsessionID,apodo);
	}
    
}
