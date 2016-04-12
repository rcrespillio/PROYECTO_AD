package dao;

import hibernate.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import repo.LogeadoDTO;
import repo.UsuarioDTO;
import entities.Entity_Usuario;




public class UsuarioDAO {
	private static UsuarioDAO instancia = null;
	private static SessionFactory sf = null;
	private UsuarioDAO(){
		sf = HibernateUtil.getSessionFactory();
	}
	
	public static UsuarioDAO getInstancia(){
		if(instancia == null)
			instancia = new UsuarioDAO();
		return instancia;
	}
	
	public boolean agregarUsuario(Entity_Usuario usuario){
		
		try{
			Session session = sf.openSession();
			session.beginTransaction();
			session.save(usuario);
			session.flush();
			session.getTransaction().commit();
			session.close();
			return true;
		}catch(Exception e){
			System.out.println(e);
			System.out.println("ErrorDAO: Usuario");
		}
		
		
		return false;
		
		
		
		/** ESTA MANERA NO FUNCIONO **/
		/*
		Transaction tx = null;
		
		try {
			Session s = sf.getCurrentSession();
			tx = s.beginTransaction();
			s.save(usuario);
            
            s.close();
            
			return true;
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
			System.out.println("ErrorDAO: Usuario");
			
		}
	
	*/
		
	}

	public boolean verificarApodoEnUso(String apodo) {
		
		Session s = null;
		Entity_Usuario result = null;
		try{
			s = sf.getCurrentSession();
			Transaction t = s.beginTransaction();
			
			Query q = s.createQuery("from Entity_Usuario c where c.apodo = :identificador");
			q.setParameter("identificador", apodo);
			result = (Entity_Usuario) q.uniqueResult();
			t.commit();
			
			System.out.println("verificando apodo, transaccion hecha, result == "+result);
			if(result!=null){
				
				return true;
			}
		}catch(Exception e){
			System.out.println(e);
			System.out.println("ErrorDAO: usuarioDAO.verificarApodoEnUso");
		}
		
		
		return false;
	}

	public boolean existeUsuario(String email, String pwd) {
		Session s = null;
		Entity_Usuario result = null;
		try{
			s = sf.getCurrentSession();
			Transaction t = s.beginTransaction();
			
			Query q = s.createQuery("from Entity_Usuario c where c.email = :identificador1 and c.pass = :identificador2");
			q.setParameter("identificador1", email);
			q.setParameter("identificador2", pwd);
			result = (Entity_Usuario) q.uniqueResult();
			t.commit();
			
			System.out.println("verificando si existe usuario , transaccion hecha, result == "+result);
			if(result!=null){
				
				return true;
			}
		}catch(Exception e){
			System.out.println(e);
			System.out.println("ErrorDAO: usuarioDAO.existeUsuario");
		}
		
		
		return false;
	}

	public UsuarioDTO validarYobtenerUsuario(String user, String pwd) {
		Session s = null;
		Entity_Usuario result = null;
		try{
			s = sf.getCurrentSession();
			Transaction t = s.beginTransaction();
			
			Query q = s.createQuery("from Entity_Usuario c where c.email = :identificador1 and c.pass = :identificador2");
			q.setParameter("identificador1", user);
			q.setParameter("identificador2", pwd);
			result = (Entity_Usuario) q.uniqueResult();
			t.commit();
			
			System.out.println("verificando si existe usuario , transaccion hecha, result == "+result);
			if(result!=null){
				
				UsuarioDTO usuario = result.getUsuarioDTO();
				return usuario;
			}
		}catch(Exception e){
			System.out.println(e);
			System.out.println("ErrorDAO: usuarioDAO.existeUsuario");
			return null;
		}
		
		return null;
	}
}


