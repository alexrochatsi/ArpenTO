angular.module('certisignonApp').controller('DetalheSistemasController', function($scope, $location, $http, $routeParams) {

	this.init = function() {
		$scope.erro = {};
		$scope.sucesso = {};
		$scope.sistema = {};

		var idUsuario = null;

		$http.get('/CertisignLogin/gestor/getUser.json').success(function(message) {

			$scope.usuario = message;
			idUsuario = $scope.usuario.id;

			$http.get('/CertisignLogin/chave-criptografica/lista/'+ idUsuario).success(function(chaveList){
	            $scope.chaves = chaveList;
	        });

		}).error(function(message) {
			dialogs.error('Erro', JSON.stringify(message));
		});

	    var id = $routeParams.id;
	    if (id) {
	    	$http.get('/CertisignLogin/sistema/buscar/' + id).success(function(model){
	    		$scope.sistema = model;
	    	});
	    }
	};

	this.salvar = function(sistema) {
        $scope.resetError();
        $http.post('/CertisignLogin/sistema/salvar', sistema).success(function() {
        	$location.path('/sistemas');
        }).error(function() {
            $scope.setError('Não foi possível salvar o sistema.');
        });
    };

    this.atualizar = function(sistema) {
        $scope.resetError();
        $http.post('/CertisignLogin/sistema/atualizar', sistema).success(function() {
        	$location.path('/sistemas');
        }).error(function() {
            $scope.setError('Não foi possível atualizar o sistema.');
        });
    };

    this.listar = function() {
    	$location.path('/sistemas');
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