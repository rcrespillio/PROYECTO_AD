package websockets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import repo.LogeadoDTO;
import utils.Json;
import controlador.Controlador;

import org.json.simple.JSONObject;   
import org.json.simple.parser.ParseException;


@ServerEndpoint("/connect")
public class WebSocket {

	
	/**tener en cuenta que cada vez que se conecta un usuario (open session), esta clase crea un thread aparte,
	 * asi que entre sessiones manejan diferentes variables, el arraylist compartido de sessiones esta en SessionHandler.
	 * **/
	 
	
	boolean ya_iniciado = false;
	
	
    @OnOpen
        public void open(Session session) {
   
    	 System.out.println(session.getId() + " has opened a connection"); 
         try {
             
        	 
        	 session.getBasicRemote().sendText("Connection Established");
        	 SessionHandler.getInstance().addSession(session);
        	 
        	 
         } catch (IOException ex) {
             ex.printStackTrace();
         }
    }

    @OnClose
        public void close(Session session) {
    	 System.out.println("Session " +session.getId()+" has ended");
    	 SessionHandler.getInstance().removeSession(session);
    }

    @OnError
        public void onError(Throwable error) {
    }

    @OnMessage
        public void handleMessage(String message, Session session) throws IOException, ParseException {
    	 System.out.println("Message from " + session.getId() + ": " + message);
    	
    	
    	 //siempre y cuando exista session
    	 if(session!=null){
    	
    	 //parseo json y obtengo la accion a realizar
         JSONObject json = (JSONObject) JSONValue.parseWithException(message);
   		 System.out.println(json.toJSONString());
   		String action = (String) json.get("action");
   		 
   		switch(action){
   		
   		case "vincular":
   			System.out.println("entre en el vincular...");
       	     boolean resultadoVincular = Controlador.getControlador().vincularSessionUsuario(session.getId(),json.get("apodo").toString());
          	 if(resultadoVincular){
          	 session.getBasicRemote().sendText("usuariovinculado");
          	 }else{
          		 JSONObject jsonError = Json.crearJSONError("error vinculando session con usuario");
   		  		session.getBasicRemote().sendText(jsonError.toJSONString());
          	  }
   			  break;
   		
   		case "libreindividual":
   			
   			String sessionSocket = session.getId();
		  	String JSessionID = (String) json.get("id");
		  	String apodo = (String) json.get("apodo");
		  	System.out.println("Websocket/message:  entre a libreindividual, llamando controlador ");
		  	System.out.println("parametros recuperados: apodo"+apodo+"jsess: "+JSessionID+" sessionSock: "+sessionSocket);
		  	boolean validarUsuario =  Controlador.getControlador().validarUsuarioActivo(apodo,JSessionID,sessionSocket);
		  	if(validarUsuario){
		  	boolean resultadoIndividual = Controlador.getControlador().crearLibreIndividual(apodo,JSessionID,sessionSocket);
		  	if(resultadoIndividual){
		  	JSONObject jsonListaEspera = Json.crearJSONListaEspera();
	  		session.getBasicRemote().sendText(jsonListaEspera.toJSONString());
		  	}else{
		  		System.out.println("Websocket/message: json error to send");
		  		JSONObject jsonError = Json.crearJSONError("error en el proceso de creacion  libre individual");
		  		session.getBasicRemote().sendText(jsonError.toJSONString());
		  	}
		  	}else{
		  		System.out.println("Websocket/message: json error to send");
		  		JSONObject jsonError = Json.crearJSONError("error validando el usuario, por favor vuelva a ingresar");
		  		session.getBasicRemote().sendText(jsonError.toJSONString());
		  	}
   			
   			break;
   		
   		case "usuariosconectados":
   			
   			
   		 try {
	    		Set<Session> sessions = SessionHandler.getInstance().getSessions();
	    		 for(Session s: sessions){
	    		LogeadoDTO logeado = Controlador.getControlador().obtenerUsuarioLogeado(s.getId());
	    		if(logeado!=null){
	    		
	    		JSONObject json1 = Json.crearJSON(logeado);
	    		if(json!=null){
	    		System.out.println("json parceado:"+json1.toJSONString());
	    		 
	    		for(Session ss: sessions){ 
	                
	    			System.out.println("ssgetid: "+ss.getId()+"this session id: "+session.getId()+" ya iniciado: "+ya_iniciado);
	    			if((ss.getId()==session.getId()) && (this.ya_iniciado==true)){
	    				System.out.println("salgo del bucle");
	    				continue;
	                	
	                }
	    			ss.getBasicRemote().sendText(json1.toJSONString());
	    			System.out.println("send message json correctly to: "+session.getId());
	    		}
	               
	               
	        
	    		}else{//json null
	    		 System.out.println("json null o mal parseado");
	    	     }
	    		
	    		
	    		}else{
	    			System.out.println("websocket/handlemessage: no encontre el usuario logeado en el servidor");	
	    		}
	    		 
	    		 
	    		 }//fin for
	    		 this.ya_iniciado = true; 
	    		} catch (Exception ex) {
	             ex.printStackTrace();
	         }
   			
   			break;
   		
   		}//fin switch
   		
   		
   		
   		
   		
    		
    		
       	 
   		 
    		
    		
    		
    		  	
    	
    	
    	
    	
    	}
    	
    	
    }
    	
}    