var certisignonControllers = angular.module('certisignonControllers', []);

certisignonControllers.controller('SistemasController', function($scope, $routeParams) {
});

certisignonControllers.controller('ParDeChavesController', function($scope, $routeParams) {
});

certisignonControllers.controller('GestorController', function($scope, $routeParams) {
});

certisignonControllers.controller('ListarSistemasController', function($scope, $routeParams) {
});

certisignonControllers.controller('DetalheSistemasController', function($scope, $routeParams) {
});

certisignonControllers.controller('UsuarioController', function($scope, $routeParams) {
});

certisignonControllers.controller('DropdownCtrl', function($scope, $routeParams) {

	  $scope.status = {
	    isopen: false
	  };

	  $scope.toggled = function(open) {

	  };

	  $scope.toggleDropdown = function($event) {
	    $event.preventDefault();
	    $event.stopPropagation();
	    $scope.status.isopen = !$scope.status.isopen;
	  };
});