<%@page isErrorPage="true"%>

<%			
java.io.StringWriter stringWriter = new java.io.StringWriter();
java.io.PrintWriter writer = new java.io.PrintWriter(stringWriter);

exception.printStackTrace(writer);
exception.printStackTrace();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Erro</title>
<meta name="collection" content="reference" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/resources/styles/stylesheet.css" />
<script>


	function toogleDetail(){
		element = document.getElementById("detail");

		if(element.style.visibility == "hidden"){
			element.style.visibility = "visible";
			wasVisible = false;
			
		} else {
			element.style.visibility = "hidden";
			wasVisible = true;
		}
		
		element = document.getElementById("link");
		if(wasVisible){
			element.innerHTML = "Mostrar Mensagem"
			
		} else {
			element.innerHTML = "Esconder Mensagem"
			
		}
	}
</script>
</head>

<body>
	<a href="portal.jsf" style="left: 910px; top: 5px; position: absolute;"><img
		border="1"
		src="<%= request.getContextPath() %>/resources/images/transparent.gif"
		width="70" height="20" /></a>

	<table style="left: 440px; top: 100px; position: absolute">
		<tr>
			<td><a href="#" id="link" onclick="toogleDetail()">Mostrar
					Mensagem</a></td>
		</tr>
	</table>
	<textarea cols="1000" rows="24" id="detail" readonly="readonly"
		style="visibility: hidden; border: 1px solid green; left: 100px; top: 120px; width: 835px; position: absolute;"><%=stringWriter.getBuffer().toString()%></textarea>

</body>
</html>

