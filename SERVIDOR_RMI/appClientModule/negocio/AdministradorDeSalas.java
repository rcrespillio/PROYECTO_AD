package negocio;

import java.util.ArrayList;

public class AdministradorDeSalas {

	ArrayList<Sala> salas;
	
	
	private static AdministradorDeSalas administradorDeSalas;
	
	private AdministradorDeSalas(){
		
		salas = new ArrayList<Sala>();
	};
	
	public AdministradorDeSalas getInstance(){
	
		
		if(administradorDeSalas!=null){
			return administradorDeSalas;
			
		}else{
			administradorDeSalas = new AdministradorDeSalas();
			
			return administradorDeSalas;
		}
		
	}
	
}
