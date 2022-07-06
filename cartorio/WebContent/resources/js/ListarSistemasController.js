angular.module('certisignonApp').controller('ListarSistemasController', function($scope, $location, $http, $loading) {

	this.init = function() {
		$scope.erro = {};
		$scope.sucesso = {};
		$scope.usuario = {};
		$scope.sistemas = {};

		$scope.listar();
	};

	$scope.listar = function() {

		$loading.start('loading');

		$http.get('/CertisignLogin/sistema/listarPorGestor').success(function(data) {

			$scope.sistemas = data;

			$loading.finish('loading');

		}).error(function(message) {
			dialogs.error('Erro', JSON.stringify(message));
		});
	};

	this.novo = function() {
		$location.path('/sistemas/novo');
	};

	this.editar = function(id) {
		$location.path('sistemas/editar/' + id);
	};

	this.remover = function(id) {
		$http['delete']("/CertisignLogin/sistema/remover/" + id).success(function(){
			$scope.listar();
        });
	};

	$scope.startLoading = function (name) {
		$loading.start(name);
	};

	$scope.finishLoading = function (name) {
		$loading.finish(name);
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