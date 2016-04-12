package utils;





import org.json.simple.JSONObject;

import repo.LogeadoDTO;

public class Json {

	
	
	

	public static JSONObject crearJSON(Object object){
		 


		
		System.out.println("ENTRE EN EL METODO JSON");
		try{
		//cosa rara,,, el instanceof no funciona....
			System.out.println(object.getClass().toString());
			System.out.println(LogeadoDTO.class.toString());
			System.out.println(LogeadoDTO.class.toString());
			//cosa rara QUE FUNCIONA,,, el instanceof no funciona....
			if(object.getClass()==LogeadoDTO.class){

					LogeadoDTO logeado = (LogeadoDTO) object;
					
					
					
					
				System.out.println("pase el equals");
			JSONObject obj = (JSONObject) new JSONObject();
			obj.put("action", "usuariosConectados");
			obj.put("apodo", logeado.getApodo());
			obj.put("estado", logeado.getEstado());
			
				
		    System.out.println("obj json pendiente retorno: "+obj.toJSONString());
			return obj;
		}
	
		 System.out.println("class json: return null");
	
	}catch(Exception e){
		e.printStackTrace();
	}
		 
		 
		 return null;

	
	
	
	}

	public static JSONObject crearJSONError(String error) {
		JSONObject obj = (JSONObject) new JSONObject();
		
		obj.put("action", error);
		return obj;
	}

	public static JSONObject crearJSONListaEspera() {
		JSONObject obj = (JSONObject) new JSONObject();
		obj.put("action", "enlista");
		return obj;
	}

	public static JSONObject crearJSONJugarIndividual() {
		JSONObject obj = (JSONObject) new JSONObject();
		obj.put("action", "individualcreada");
		return obj;
	}

	public static JSONObject crearJSONRedireccionIndividual() {
		JSONObject obj = (JSONObject) new JSONObject();
		obj.put("action", "redireccionindividual");
		
		return obj;
	}
		

}