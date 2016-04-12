package websockets;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;

import repo.LogeadoDTO;


public  class SessionHandler {
    
	private final static Set<Session> sessions = new HashSet<>();
	//private final static Map<Session,LogeadoDTO> sessions = new HashMap();
	private static SessionHandler session;
	private SessionHandler(){};
	
	public static SessionHandler getInstance(){
		
		if(session!=null){
			return session;
		}else{
			session = new SessionHandler();
			return session;
		}
		
	}
	
	
   public Set<Session> getSessions(){
	   
	   return sessions;
   }
	
	public void addSession(Session session) {
        //sessions.put(session, null);
		sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
        
    }
    
   public int sessionCant(){
	   return sessions.size();
   }
    
    
    /**
    
    public boolean vinculateSession(Session session,LogeadoDTO logeado){
		
    	
    	for (Map.Entry<Session, LogeadoDTO> m : sessions.entrySet())
    	{
              if(m.getKey().equals(session) && m.getValue().equals(null)){
            	 System.out.println("sessionhandler , vinculada la session con el logeadodto"); 
    			m.setValue(logeado);
    			return true;
    		}
    	}
    	
    	
    	return false;
    	
    	
    }
    
    */
}