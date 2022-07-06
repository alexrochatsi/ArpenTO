angular.module('certisignonApp').controller('GestorController', function($scope, $location, $http, $log, appFactory, $window, $loading, dialogs) {

	$scope.usuario = {};
	$scope.submitted = false;
	this.tipoAutenticacaoSenha = false;
	this.ret = {};

	this.login = {};

	this.init = function() {

		$scope.erro = {};
		$scope.sucesso = {};

	};

	this.getCertificadoSelecionado = function() {
		this.ret = appFactory.ret;
	};

	this.certificadoRead = function() {

		$loading.start('loading');
		$http.post('/CertisignLogin/certificado/get').success(function(message) {

			if (message.code == 200) {

				appFactory.ret = message;
				$location.path('novo-escolha-certificado');


			} else {
				dialogs.error('Erro', message.message);
			}
			$loading.finish('loading');

		}).error(function(data, status, headers, config) {
			dialogs.error('Erro', '<p>Erro ao obter Certificado. </p> <b>Motivos Prov&aacute;veis</b> <ul>'
					+ '<li>N&atilde;o existem certificados instalados no reposit&oacute;rio.</li> '
					+ '<li>Certificado expirado.</li> '
					+ '<li>Algum certificado da cadeia est&aacute; expirado.</li> '
					+ '<li>Algum certificado na cadeia possui assinatura inv&aacute;lida.</li> '
					+ '<li>A raiz da cadeia do certificado n&atilde;o &eacute; confi&aacute;vel.</li> '
					+ '</ul>');
			//alert("Erro ao tentar obter certificado." + JSON.stringify(data) + JSON.stringify(status) + JSON.stringify(headers) + JSON.stringify(config));
			$loading.finish('loading');
		});

	};

	this.entrar = function() {

		$loading.start('loading');
        $http.post('/CertisignLogin/gestor/login/certificado')
        .success(function(data, status, headers, config) {
        	if (data.JSONMessage.code == 0){
            	$window.location.href = '/CertisignLogin/index.jsp';
        	}
        	if (data.JSONMessage.code == 500){
        		dialogs.error('Erro', '<p>Erro ao obter Certificado. </p> <b>Motivos Prov&aacute;veis</b> <ul>'
    					+ '<li>N&atilde;o existem certificados instalados no reposit&oacute;rio.</li> '
    					+ '<li>Certificado expirado.</li> '
    					+ '<li>Algum certificado da cadeia est&aacute; expirado.</li> '
    					+ '<li>Algum certificado na cadeia possui assinatura inv&aacute;lida.</li> '
    					+ '<li>A raiz da cadeia do certificado n&atilde;o &eacute; confi&aacute;vel.</li> '
    					+ '</ul>');

        	}
        	if (data.JSONMessage.code == 505){
        		dialogs.notify('Atenção', data.JSONMessage.message);
        		$window.location.href = '/CertisignLogin/gestor/cadastro#/novo';
        	}
        	$loading.finish('loading');
	  	}).error(function() {
	  		//alert('Não foi possível integrar o certificado.');
	  		dialogs.error('Erro', 'Não foi possível integrar o certificado.');
	  		$loading.finish('loading');
	  	});
	};


	this.entrarComUsuarioSenha = function() {

		$loading.start('loading');
		$scope.resetError();

        $http.post('/CertisignLogin/gestor/loginSenha', this.login)
        .success(function(data, status, headers, config) {
        	if (data.JSONMessage.code == 200)
            	$window.location.href = '/CertisignLogin/index.jsp';
        	if (data.JSONMessage.code == 500)
        		dialogs.notify('Atenção', data.JSONMessage.message);
	  	}).error(function() {
	  		//alert('Erro de acesso.');
	  		dialogs.error('Erro', 'Erro de acesso com o usuário.');
	  	});
        $loading.finish('loading');
	};

	this.esqueceuASenha = function(login) {
		$scope.resetError();
		$scope.submitted = true;
		if ($scope.form.$valid){
			$loading.start('loading');
	        $http.post('/CertisignLogin/gestor/login/esqueci-a-senha', this.login)
	        .success(function(data, status, headers, config) {
	        	if (data.JSONMessage.code == 200)
	        		dialogs.notify('Atenção', 'OK');
	        	if (data.JSONMessage.code == 505)
	        		dialogs.notify('Atenção', data.JSONMessage.message);
		  	}).error(function() {
		  		dialogs.error('Erro', 'Erro de acesso com o usuário.');
		  	});

	        $loading.finish('loading');
		}
	};

	this.tipoAutenticacao = function(tipo) {

		if (tipo == 'S') {
			this.tipoAutenticacaoSenha = true;

		} else {
			this.tipoAutenticacaoSenha = false;
			this.certificadoRead();
		}
	};

    this.salvar = function(usuario) {

        $scope.resetError();
        $scope.submitted = true;
        if ($scope.form.$valid){
	        $loading.start('loading');
	        $http.post('/CertisignLogin/gestor/salvar/email/senha', usuario).success(function(data, status, headers, config) {

	        	if (data.JSONMessage.code == 200)
	            	$window.location.href = '/CertisignLogin/index.jsp';
	        	if (data.JSONMessage.code == 500)
	        		dialogs.error('Erro', data.JSONMessage.message);

	        }).error(function(data, status, headers, config) {
				dialogs.error('Erro', 'Erro ao tentar salvar usuário');
			});
	        $loading.finish('loading');
        }
    };

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    };

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    };


});