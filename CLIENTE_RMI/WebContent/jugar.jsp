<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="controlador.Controlador" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <script src="js/jquery-1.12.3.min.js"></script>

</head>

<% 
       
      
        String id = (String) session.getAttribute("id");
        String apodo = (String) session.getAttribute("apodo");
        if(apodo!=null && id!=null ){
        	
         
        
       boolean confirmarAcceso = Controlador.getControlador().confirmarUsuarioEnEsperaIndividual(id,apodo);
       if(!confirmarAcceso){
    	   response.sendRedirect("errorAccesoJugar.html");
       }
       
       
        }else{
        	response.sendRedirect("error.html");
        }
      
        %>
        
       
       
       <script>
       $(document).ready(function() {
	   
    	   
    	   webSocket = new WebSocket("ws://localhost:8080/CLIENTE_RMI/partidaIndividual");
           alert("onopensocket");
           /**
            * Binds functions to the listeners for the websocket.
            */
           webSocket.onopen = function(event){
               // For reasons I can't determine, onopen gets called twice
               // and the first time event.data is undefined.
               // Leave a comment if you know the answer.
               if(event.data === undefined){
                   return;
               }
                  
              
               
               writeResponse(event.data);
              
               
               
               
           };

           webSocket.onmessage = function(event){
               writeResponse(event.data);
               
              
              
               
                 
       };

           webSocket.onclose = function(event){
               writeResponse("Connecon closed");
           };
       
       
       
       
    	  
	    
	
});
      
       
       function openSocket(){
           // Ensures only one connection is open at a time
           if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
              writeResponse("WebSocket is already opened.");
               return;
           }
           // Create a new instance of the websocket
           webSocket = new WebSocket("ws://localhost:8080/CLIENTE_RMI/partidaIndividual");
           alert("onopensocket");
           /**
            * Binds functions to the listeners for the websocket.
            */
           webSocket.onopen = function(event){
               // For reasons I can't determine, onopen gets called twice
               // and the first time event.data is undefined.
               // Leave a comment if you know the answer.
               if(event.data === undefined){
                   return;
               }
                  
              
               
               writeResponse(event.data);
              
               
               
               
           };

           webSocket.onmessage = function(event){
               writeResponse(event.data);
               
              
              
               
                 
       };

           webSocket.onclose = function(event){
               writeResponse("Connecon closed");
           };
       
       }
       
       
       
       
       </script>

<body>
<h6> PAGINA DE PARTIDAS !!! </h6>
</body>




</html>




