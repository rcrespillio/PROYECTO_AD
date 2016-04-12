package listeners;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.json.simple.JSONObject;

import repo.LogeadoDTO;
import controlador.Controlador;
import utils.Json;
import websockets.SessionHandler;




	
	public class HiloCrearIndividual extends Thread{
		ArrayList<LogeadoDTO> usuariosParaIndividual = new ArrayList<LogeadoDTO>();
		
		public void run(){
			while(true){
				if(SessionHandler.getInstance().sessionCant()==2){
					System.out.println("la cantidad de sessiones es igual a 2, procedo a preguntar si hay libres");
					 try {
						
						 usuariosParaIndividual = Controlador.getControlador().hayParaIndividual();
						 
						if(usuariosParaIndividual!=null){
						 if(usuariosParaIndividual.size()==2){
							System.out.println("TRHREAD : encontre 2, procedo a intentar redireccionar");
							boolean redireccionOk = redireccionar(usuariosParaIndividual);
							stop();
						 }else{
							 System.out.println("hay 2 sesiones pero ambos no estan EN ESPERA,todo ok"); 
						 }
						 }else{//igual a null
							 System.out.println("es array es nulo, no hay 2 en espera, todo ok"); 
						 }
						 try {
							sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
					 } catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					
					 //stop();
				}else{
					 try {
						sleep(3000);
						 System.out.println("sleep 3 en el else");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
		}
		
	}

		private boolean redireccionar(ArrayList<LogeadoDTO> usuariosParaIndividual) {
			System.out.println("HiloCrearIndividual/redireccionar method");
			Set<Session> sessions = SessionHandler.getInstance().getSessions();
   		 for(Session s: sessions){

           for(LogeadoDTO usuario: usuariosParaIndividual){
        	   if(usuario.getIdsocket().equals(s.getId())){
        		   JSONObject json = Json.crearJSONRedireccionIndividual();
        		   
        			   System.out.println("JSON CREADO: "+json.toJSONString());
        			   System.out.println("redirecciono al usr: "+s.getId());
        			   try {
						s.getBasicRemote().sendText(json.toJSONString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        	   
        	   }
           }
			
           //LOS USUARIOS EN EL SERVIDOR FUERON PUESTOS "EN OPERACION" OSEA QUE NO SON TENIDOS EN CUENTA SI ALGUIEN TRATA DE BUSCARLOS
           //SI NO HUBO ERRORES EN LA REDIRECCION, PROCEDO A ESPERAR QUE SE CONECTEN LOS WEBSOCKETS DE  LA PARTE JUGAR
           
           
          // Controlador.getControlador().crearSalaIndividual(usuariosParaIndividual);
           
		}
	return true;
		}
		}
	
	
	

