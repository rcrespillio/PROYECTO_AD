package repo;

public class UsuarioDTO {

	
	private String apodo;
	private String categoria;
	
	
	public UsuarioDTO(String apodo, String categoria){
		this.apodo = apodo;
		this.categoria = categoria;
	}
	
	
	public String getApodo() {
		return apodo;
	}
	
	
	
	public void setApodo(String apodo) {
		this.apodo = apodo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
	
	
}
