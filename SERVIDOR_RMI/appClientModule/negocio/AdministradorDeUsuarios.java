package negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dao.UsuarioDAO;
import repo.LogeadoDTO;
import repo.UsuarioActivo;
import repo.UsuarioDTO;
import rmi.InterfazServer;
import rmi_interface.Interface;

public class AdministradorDeUsuarios {

	private ArrayList<UsuarioActivo> listaDeEspera = new ArrayList<UsuarioActivo>();
	private ArrayList<UsuarioActivo> usuariosLogeados = new ArrayList<UsuarioActivo>();
	private static AdministradorDeUsuarios administradorDeUsuarios;
	
	private AdministradorDeUsuarios() {
		
	HiloCrearIndividual thread = new HiloCrearIndividual();
	thread.start();
		
	};
	
	
	

	public static AdministradorDeUsuarios getInstance(){
		if(administradorDeUsuarios!=null){
			return administradorDeUsuarios;
		}else{
			administradorDeUsuarios = new AdministradorDeUsuarios();
			return administradorDeUsuarios;
			
		}
		
	}

	public int getCantidadEnListaDeEspera(){
		return listaDeEspera.size();
	}
	
	
	
	public LogeadoDTO validarYobtenerUsuario(String user, String pwd) {
		
		UsuarioDTO usuario = UsuarioDAO.getInstancia().validarYobtenerUsuario(user, pwd);
		
		if(usuario!=null){
		
		UsuarioActivo usuarioActivo = new UsuarioActivo(usuario.getApodo(),usuario.getCategoria());
		
		
			
			usuarioActivo.setEstado("ACTIVO");
			usuariosLogeados.add(usuarioActivo);
			return new LogeadoDTO(usuario.getApodo(),usuarioActivo.getEstado(),usuarioActivo.getSessionSocket());
		}
		
		return null;
	}

	

	public boolean vincularSessionUsuario(String id, String apodo) {
		
		for(UsuarioActivo usr :  usuariosLogeados){
			if(usr.getApodo().equalsIgnoreCase(apodo)){
				usr.setSessionSocket(id);
				System.out.println("encontre usuario en la lista de usuarios logeados, agrege session");
				return true;
			}
			
		}
		
		return false;
	}

	public LogeadoDTO obtenerUsuarioLogeado(String idSession) {
		for(UsuarioActivo usr :  usuariosLogeados){
			if(usr.getSessionSocket()!=null){
			if(usr.getSessionSocket().equalsIgnoreCase(idSession)){
				
				System.out.println("encontre usuario logeado con el id de session en el servidor");
				return new LogeadoDTO(usr.getApodo(),usr.getEstado(),usr.getSessionSocket());
			}
			}
		}
		
		return null;
	}

	public boolean validarUsuarioActivo(String apodo, String jSessionID,
			String sessionSocket) {
		System.out.println("validando el usuario activo con todos los params");
		
		for(UsuarioActivo usr :  usuariosLogeados){
			System.out.println("coparando params pasados: Jsess"+jSessionID+"usr jses: "+usr.getjSessionID()+" apodo: "+apodo+" "+sessionSocket+" "+"usuario en logeado: "+usr.getApodo()+" "+usr.getSessionSocket());
			if(usr.getApodo().equalsIgnoreCase(apodo) && usr.getSessionSocket().equalsIgnoreCase(sessionSocket) && usr.getjSessionID().equals(jSessionID)){
				System.out.println("encontre usuario activo retorno true");
				return true;
			}
			
		}
	return false;
	
	}

	public boolean crearLibreIndividual(String apodo, String jSessionID,
			String sessionSocket) {
		UsuarioActivo usuario = null;
		System.out.println("SERVER/cREARINDIVIDUAL le paso: jSession"+jSessionID);
		// TODO FALTA HACER QUE RECORRA LA LISTA BUSCANDO USUARIOS DE LA MISMA CATEGORIA, SI NO, SE AGREGA A LA LISTA DE ESPERA
		//EN ESTA FASE SOLO SE OBTIENE EL USUARIO SE LE CAMBIA EL ESTADO Y SE AGREGA A LA LISTA DE ESPERA.
		for(UsuarioActivo usr :  usuariosLogeados){
			if(usr.getApodo().equalsIgnoreCase(apodo) && usr.getSessionSocket().equalsIgnoreCase(sessionSocket) &&  usr.getjSessionID().equals(jSessionID)){
				usr.setEstado("EN ESPERA");
				listaDeEspera.add(usr);
			    usuario = usr;
			    System.out.println("AU/crearlIBRE: encontre usuario, lo cambio a espera, pendiente retorno true");
			}
			
		}
	if(usuario!=null){
		//usuariosLogeados.remove(usuario);    si lo quiero enviar igual o sacarlo de la lista de activos
		return true;
	}
		
		
		return false;
	}




	




	public ArrayList<LogeadoDTO> hayParaIndividual() {
		System.out.println("AU/hayparaindividual");
		ArrayList<LogeadoDTO> hayparaindividual = null;
		  for(UsuarioActivo usr :  listaDeEspera){
			
			if(usr.getEstado().equals("EN ESPERA") && usr.isEnOperacion()==false){
			LogeadoDTO usuario  = new LogeadoDTO(usr.getApodo(),usr.getEstado(),usr.getSessionSocket());
			if(hayparaindividual==null){
				hayparaindividual = new ArrayList<LogeadoDTO>();
			}
			hayparaindividual.add(usuario);
			 if(hayparaindividual.size()==2){
				 System.out.println("AU/hayparaindividual regreso que tiene tamaño 2");
				 
				 for(LogeadoDTO l : hayparaindividual){
					 String s = l.getApodo();
					 for(UsuarioActivo usu :  listaDeEspera){
						 if(usu.getApodo().equals(s)){
							 System.out.println("usuario: "+usu.getApodo()+" fue puesto en estado en operacion");
							 usu.setEnOperacion(true);
						 }
					 }
				 }
				 
				 
				 //DEBERIA CREAR LA SALA Y MANDAR LOS LOGEADOSDTO PARA REDIRECCIONARLOS A LA SECCION JUGAR INDIVIDUAL
				 return hayparaindividual;
			 }
			}
			
		}
		
		 
		
		return null;
	}




	public boolean confirmarUsuarioEnEsperaIndividual(String jSessionID, String apodo) {
		 System.out.println("estoy en AU/confirmarUsuarioEsperaIndividual  en el servidor");
		for(UsuarioActivo usr : listaDeEspera){
			System.out.println("COMPARANDO usr.apodo= "+usr.getApodo()+"con "+apodo+" y usr.jsession= "+usr.getjSessionID()+" con param pasado: "+jSessionID+"esta en operacion: "+usr.isEnOperacion());
			if(usr.getApodo().equals(apodo) && usr.getjSessionID().equals(jSessionID) && usr.isEnOperacion()){
				 System.out.println("encontre usuario en confimarparaindividual en el servidor, esta en operacion true");
				 return true;
			 }
	
		
		}
		return false;

	}




	public boolean vincularJSessionIDconUsuario(String jsessionID, String apodo) {
		for(UsuarioActivo usr :  usuariosLogeados){
			if(usr.getApodo().equalsIgnoreCase(apodo)){
				usr.setjSessionID(jsessionID);
				System.out.println("encontre usuario en la lista de usuarios logeados, agrege JSESSIONID");
				return true;
			}
			
		}
		
		return false;
	}
	
	
}
