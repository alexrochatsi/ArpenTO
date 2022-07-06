angular.module('certisignonApp').controller('UsuarioController', function($scope, $location, $http, $loading) {

	this.init = function() {
		$scope.erro = {};
		$scope.sucesso = {};
		$scope.usuario = {};

		$loading.start('loading');

		$http.get('/CertisignLogin/gestor/getUser.json').success(function(message) {

			$scope.usuario = message;
			$loading.finish('loading');

		}).error(function(message) {
			dialogs.error('Erro', JSON.stringify(message));
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