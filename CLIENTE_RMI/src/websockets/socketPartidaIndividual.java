package websockets;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.parser.ParseException;


@ServerEndpoint("/partidaIndividual")
public class socketPartidaIndividual {

	
	 @OnOpen
     public void open(Session session) {

 	 System.out.println(session.getId() + " has opened a connection from socketPartidaIndividual"); 
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
 }
	
	
	
}
