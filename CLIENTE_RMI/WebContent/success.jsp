<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
 <%@ page import="controlador.Controlador" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="cache-control" content="no-cache" />
         
         <script src="js/jquery-1.12.3.min.js"></script>
        <title>TRUCO ONLINE</title>
    </head>
    <body>
       
       
        <% 
       
      //save message in session
        String id = session.getId();
       session.setAttribute("id", id);
        String exito = (String) session.getAttribute("apodo");
        if(exito!=null){%>
        	
         <% boolean resultado = Controlador.getControlador().vincularJSessionIDconUsuario(id,exito); %>
        
      <h1>BIENVENIDO <%=session.getAttribute("apodo") %> !!!!!</h1>
      
      
       <%}else{%>
        
        
       <h1>NO HEMOS PODIDO ENCONTRAR TU APODO (COOKIE), NO PODRAS USAR LAS FUNCIONES TEMPORALMENTE
       RECUERDA PERMITIR LAS COOKIES EN EL NAVEGADOR </h1>
       <% }%>
      
      
      
      
      <script>
       $(document).ready(function() {
	   
    	   
    	   webSocket = new WebSocket("ws://localhost:8080/CLIENTE_RMI/connect");
           alert("onopensocket");
           if(webSocket.readyState === 1){
        	   alert("onopensocket");
        	   vincular();
           }
           
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
               
               //Generate();
              
              var response = JSON.parse(event.data);
              
              if(response.action === "usuariovinculado") {
           	   writeResponse("ha sido vinculado al sistema");
              }
              
              if (response.action === "usuariosConectados") {
           	   
           	   Generate(response);
                   }
              if (response.action === "error") {
           	   
           	   writeResponse("error recibido , usuario no encontrado");
                   }
                 if (response.action === "enlista") {
           	   
           	   writeResponse("enhorabuena! ha sido agregado a la lista de espera, espere eternamente hasta que se conecten 3 jugadores más");
                   }
                 if (response.action === "individualcreada") {
              	   
              	   writeResponse("PENDIENTE PARA TRASLADAR A SALA INDIVIDUAL! ");
                      }
                 if (response.action === "redireccionindividual") {
                 	   
                 	  redirectIndividual();
                         }
               
              
              
               
                 
       };

           webSocket.onclose = function(event){
               writeResponse("Connecon closed");
           };
       
       
       
       
    	  
	    
	
});
   </script>   
      
      
      
      
      
      <div>
            <input type="text" id="messageinput"/>
        </div>
        <div>
            <button type="button" onclick="openSocket();" >Open(primer paso abrir socket)</button>
            <button type="button" onclick="vincular();" >Vincular(segundo paso vincular usuario)</button>
            <button type="button" onclick="mostrarConectados()" >Mostrar Usuarios Conectados</button>
            <button type="button" onclick="closeSocket();" >Close</button>
            <button type="button" onclick="sendPartidaIndividual();" >Partida Individual</button>
             <button type="button" onclick="redirectIndividual();" >Probar Redireccion</button>
            
        </div>
        <!-- Server responses get written here -->
        <div id="messages"></div>
       
        <script>
            /**
             * Sends the value of the text input to the server
             */
             function sendPartidaIndividual(){
            	
            	
            	 var myObject = new Object();
            	 myObject.id =   '${id}';
            	 myObject.apodo = '${apodo}';
            	 myObject.action = "libreindividual";
            	 var ind = JSON.stringify(myObject);
                 webSocket.send(ind);
             }
             
             
            function mostrarConectados(){
            	var myObject = new Object();
           	    myObject.id =   '${id}';
           	    myObject.apodo = '${apodo}';
           	    myObject.action = "usuariosconectados";
           	     var ind = JSON.stringify(myObject);
                webSocket.send(ind);
            	
            }
            
            
           function testRedireccion(){
        	   
        	   
        	   
           }
            
          function redirectIndividual(){
        	  
        	 // window.location="localhost:8080/CLIENTE_RMI/jugar.jsp";
        	  setTimeout("location.href='jugar.jsp'", 1000);
        	  
          }
           
           
            function vincular(){
                 alert("estoy vinculando, todo ok");
                 var myObject = new Object();
             	 myObject.id =   '${id}';
             	 myObject.apodo = '${apodo}';
             	 myObject.action = "vincular";
             	 var ind = JSON.stringify(myObject);
                 
             	 webSocket.send(ind);
                
             }
            
           
            function closeSocket(){
                webSocket.close();
            }
 
            function writeResponse(text){
                messages.innerHTML += "<br/>" + text;
            }
           
            function Generate(response){
            	
            	//var json = [{"User_Name":"John Doe","score":"10","team":"1"},{"User_Name":"Jane Smith","score":"15","team":"2"},{"User_Name":"Chuck Berry","score":"12","team":"2"}];
                     var tr;
                
                	tr = $('<tr/>');
                    tr.append("<td>" + "n" + "</td>");
                    tr.append("<td>" + response.apodo + "</td>");
                    tr.append("<td>" + response.estado + "</td>");
                    
                    $('table').append(tr);
                
            }
               
        </script>
   
  
  
      <table>
    
   <tr>
       <th>ID</th>
       <th>APODO</th>
       <th>ESTADO</th>
   </tr>
 
   </table>
      
      
      

      
      
      
    
      
    </body>
</html>
