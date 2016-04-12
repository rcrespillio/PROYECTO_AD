package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import repo.LogeadoDTO;
import repo.UsuarioDTO;



/**
 *
 * @author Usuario
 esta clase a diferencia de la clase del respositiorio correspondiente
 va a ser la que este anotada con notacion hibernate para luego persistir
 
 aca va a ir el @entitty para persistencia ( es parte de hybernate, las anotaciones se hacen directamente sobre estas clases)
 No va en el respositorio porque , en el respositorio UsuarioDTO ,, la responsablidad nada mas el es definirlo en el repo. 
 como usuarioDTO.
 
 la idea es es devolver objetos al cliente de transferencia DTO. ( serian como los Views) 
 
 *
 */

	
	

@Entity
@Table(name = "Usuario")
public class Entity_Usuario {

   
	    @Id
	    @GeneratedValue(strategy= GenerationType.AUTO)
	    @Column(name = "id")
	    private int id;
	    
	    @Column(name = "apodo")
	    private String apodo;
	    
	    @Column(name = "email")
	    private String email;
	    
	    @Column(name = "password")
	    private String pass;
	
	
	public Entity_Usuario(){
		
	}
	
	
	public Entity_Usuario(String apodo, String email, String pass) {
       this.apodo = apodo;
    	this.email = email;
        this.pass = pass;
    }
    
   

   

   
    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


	


	public LogeadoDTO getLogeadoDTO() {
		// TODO Auto-generated method stub
		return new LogeadoDTO(this.getApodo(),"");
	}


	public UsuarioDTO getUsuarioDTO() {
		
		return new UsuarioDTO(this.getApodo(),this.getEmail(),this.getPass());
	}


	
    
    
    
}
