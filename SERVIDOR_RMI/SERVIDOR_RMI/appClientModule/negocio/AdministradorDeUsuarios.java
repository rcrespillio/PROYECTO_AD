package negocio;

import java.util.ArrayList;

import dao.UsuarioDAO;
import repo.LogeadoDTO;
import repo.UsuarioDTO;

public class AdministradorDeUsuarios {

	private ArrayList<UsuarioDTO> usuariosLogeados = new ArrayList<UsuarioDTO>();
	private static AdministradorDeUsuarios administradorDeUsuarios;
	private AdministradorDeUsuarios(){};
	
	public static AdministradorDeUsuarios getInstance(){
		if(administradorDeUsuarios!=null){
			return administradorDeUsuarios;
		}else{
			administradorDeUsuarios = new AdministradorDeUsuarios();
			return administradorDeUsuarios;
			
		}
		
	}

	public LogeadoDTO validarYobtenerUsuario(String user, String pwd) {
		
		UsuarioDTO usuario = UsuarioDAO.getInstancia().validarYobtenerUsuario(user, pwd);
		
		if(usuario!=null){
			
			usuario.setEstado("ACTIVO");
			usuariosLogeados.add(usuario);
			return new LogeadoDTO(usuario.getApodo(),usuario.getEstado());
		}
		
		return null;
	}

	

	public boolean vincularSessionUsuario(String id, String apodo) {
		
		for(UsuarioDTO usr :  usuariosLogeados){
			if(usr.getApodo().equalsIgnoreCase(apodo)){
				usr.setSessionSocket(id);
				System.out.println("encontre usuario en la lista de usuarios logeados, agrege session");
				return true;
			}
			
		}
		
		return false;
	}

	public LogeadoDTO obtenerUsuarioLogeado(String idSession) {
		for(UsuarioDTO usr :  usuariosLogeados){
			if(usr.getSessionSocket()!=null){
			if(usr.getSessionSocket().equalsIgnoreCase(idSession)){
				
				System.out.println("encontre usuario logeado con el id de session en el servidor");
				return new LogeadoDTO(usr.getApodo(),usr.getEstado());
			}
			}
		}
		
		return null;
	}
	
	
}
