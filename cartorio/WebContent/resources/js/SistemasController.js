angular.module('certisignonApp').controller('SistemasController', function ($scope, $location, $http, $log) {

    $scope.usuario = {};

    this.init = function () {

        $scope.sistemas = {};

        $http.get('/CertisignLogin/gestor/getUser.json').success(function(message) {

			$scope.usuario = message;

			$scope.sistemas = $scope.usuario.sistemas;

		}).error(function(message) {
			dialogs.error('Erro', JSON.stringify(message));
		});

    };

    this.editar = function (id) {
    };

    this.cadastrar = function () {
        $location.path('sistemas/novo');
    };

    this.salvar = function () {
        $location.path('sistemas');
    };

    this.remover = function (id) {
    };

});