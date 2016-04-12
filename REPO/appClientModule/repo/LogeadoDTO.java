package repo;

import java.io.Serializable;

public class LogeadoDTO implements Serializable{

	private String apodo;
	private String estado;
	private String idsocket;
	
	
	
	
	public LogeadoDTO(String apodo, String estado,String idSocket) {
		super();
		this.apodo = apodo;
		this.estado = estado;
		this.idsocket = idSocket;
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
	public String getIdsocket() {
		return idsocket;
	}
	public void setIdsocket(String idsocket) {
		this.idsocket = idsocket;
	}
	
	
	
	
}
