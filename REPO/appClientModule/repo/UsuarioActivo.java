package repo;

import java.io.Serializable;



/**
 *
 * @author Usuario
 
 *debe implementar serializable
 *
 *
 *
 */
public class UsuarioActivo implements Serializable{

   
    
    private String apodo;
    private String categoria;
    private String estado;
    private String sessionSocket;
    private String jSessionID;
    private boolean enOperacion = false; //mientras se hace el proceso de creacion de salasy usr no se puede tocar este usuario

    public UsuarioActivo(String apodo, String categoria) {
        this.apodo = apodo;
    	this.categoria = categoria;
    }
    
    
    
    
    
    public String getjSessionID() {
		return jSessionID;
	}





	public void setjSessionID(String jSessionID) {
		this.jSessionID = jSessionID;
	}





	public String getSessionSocket() {
		return sessionSocket;
	}





	public void setSessionSocket(String sessionSocket) {
		this.sessionSocket = sessionSocket;
	}





	public boolean isEnOperacion() {
		return enOperacion;
	}





	public void setEnOperacion(boolean enOperacion) {
		this.enOperacion = enOperacion;
	}





	public String getCategoria() {
		return categoria;
	}





	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}





	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}
    
    
    
}
