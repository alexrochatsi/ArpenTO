<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Retorno</title>


<meta name="viewport" content="width=device-width, initial-scale=1">

<meta http-equiv="X-UA-Compatible" content="IE=edge">

<link rel="stylesheet" href="css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="css/bootstrap.min.css" />

<script src="js/bootstrap.min.js"></script>

</head>
<body>
	<br>
	<br>

	<div class="container">

		<div class="panel panel-primary">

			<div class="panel-heading">Dados do Certificado</div>
			<div class="panel-body">

				<form name="frm" class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-2 control-label">Emissor</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${certificado.emissor}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Nome</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${certificado.nome}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">CPF</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${certificado.cpf}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">CNPJ</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${certificado.cnpj}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">E-mail</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${certificado.email}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Emiss&atilde;o</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${certificado.emissao}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Validade</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${certificado.validade}">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">Certificado Base64</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${certificado.certificadoBase64}">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">Serial</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${certificado.serial}">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">OAB</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${certificado.oab}">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">T&iacute;tulo Eleitor</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${certificado.tituloEleitor}">
						</div>
					</div>

				</form>
			</div>
		</div>

		<pre>retorno certisignon: ${ret}</pre>

		<pre>retorno decriptado: ${decriptado}</pre>

	</div>

</body>
</html>