(function() {
	var app = angular.module('certisignonApp', ['ngRoute', 'ui.bootstrap', 'ui.utils', 'certisignonControllers', 'darthwade.dwLoading', 'ngCpfCnpj', 'ui.mask', 'dialogs.main', 'pascalprecht.translate', 'dialogs.default-translations']);

	app.config(['$routeProvider', function($routeProvider) {

		$routeProvider
		// usuario----------------------------------------------------------------------------------------------------
		.when('/usuario', {
			templateUrl : '/CertisignLogin/gestor/partials/gestor/usuario.html',
			controller : 'UsuarioController'
		})

		// chaves----------------------------------------------------------------------------------------------------
		.when('/par-de-chaves', {
			templateUrl : '/CertisignLogin/gestor/partials/gestor/par-de-chaves.html',
			controller : 'ParDeChavesController'
		}).when('/par-de-chaves/nova', {
			templateUrl : '/CertisignLogin/gestor/partials/gestor/par-de-chaves-nova.html',
			controller : 'ParDeChavesController'
		})
		// sistemas----------------------------------------------------------------------------------------------------
		.when('/sistemas', {
			templateUrl : '/CertisignLogin/gestor/partials/gestor/sistemas.html',
			controller : 'ListarSistemasController'
		}).when('/sistemas/novo', {
			templateUrl : '/CertisignLogin/gestor/partials/gestor/sistemas-novo.html',
			controller : 'DetalheSistemasController'
		}).when('/sistemas/editar/:id', {
            templateUrl: '/CertisignLogin/gestor/partials/gestor/sistemas-novo.html',
            controller: 'DetalheSistemasController'
        })
		// documentacao----------------------------------------------------------------------------------------------------
		.when('/documentacao', {
			templateUrl : '/CertisignLogin/gestor/partials/documentacao/index.html',
			controller : 'SistemasController'
		}).when('/documentacao/plataforma', {
			templateUrl : '/CertisignLogin/gestor/partials/documentacao/plataforma.html',
			controller : 'SistemasController'
		}).when('/documentacao/plataforma-gestor', {
			templateUrl : '/CertisignLogin/gestor/partials/documentacao/plataforma_gestor.html',
			controller : 'SistemasController'
		}).when('/documentacao/plataforma/adm/get_gestores', {
			templateUrl : '/CertisignLogin/partials/documentacao/plataforma/adm/get_gestores.html',
			controller : 'SistemasController'
		}).when('/documentacao/plataforma/adm/sistemas/get_gestores_id_sistemas', {
			templateUrl : '/CertisignLogin/gestor/partials/documentacao/plataforma/adm/sistemas/get_gestores_id_sistemas.html',
			controller : 'SistemasController'
		}).when('/documentacao/implementacao/java', {
			templateUrl : '/CertisignLogin/gestor/partials/documentacao/implementacao/java.html',
			controller : 'SistemasController'
		})
		// --------------------------------------------------------------------------------------------------------------
		.when('/alteracoes', {
			templateUrl : '/CertisignLogin/gestor/partials/documentacao/alteracoes.html',
			controller : 'SistemasController'
		})

		// relatorios----------------------------------------------------------------------------------------------------
		.when('/relatorios', {
			templateUrl : '/CertisignLogin/gestor/partials/gestor/relatorios.html',
			controller : 'ParDeChavesController'
		})
		// gestor----------------------------------------------------------------------------------------------------
		.when('/novo', {
			templateUrl : '/CertisignLogin/gestor/partials/gestor/novo.html',
			controller : 'GestorController'
		}).when('/novo-escolha-certificado', {
			templateUrl : '/CertisignLogin/gestor/partials/gestor/novo-escolha-certificado.html',
			controller : 'GestorController'
		}).when('/email', {
			templateUrl : '/CertisignLogin/gestor/partials/gestor/esqueci-a-senha.html',
			controller : 'GestorController'
		})
		// --------------------------------------------------------------------------------------------------------------
		.otherwise({
			redirectTo : '/par-de-chaves'
		});

	} ]);

	/*
	 * factory
	 */
	app.factory('appFactory', function() {

		// define factory
		var factory = {};

		// other variables
		var ret = {};

		// a factory returns a factory
		return factory;
	});

	app.directive('ngConfirmClick', [ function() {
		return {
			link : function(scope, element, attr) {
				var msg = attr.ngConfirmClick || "Confirma operação?";
				var clickAction = attr.confirmedClick;
				element.bind('click', function(event) {
					if (window.confirm(msg)) {
						scope.$eval(clickAction);
					}
				});
			}
		};
	} ]);

	app.directive('match', function () {
        return {
            require: 'ngModel',
            restrict: 'A',
            scope: {
                match: '='
            },
            link: function(scope, elem, attrs, ctrl) {
                scope.$watch(function() {
                    return (ctrl.$pristine && angular.isUndefined(ctrl.$modelValue)) || scope.match === ctrl.$modelValue;
                }, function(currentValue) {
                    ctrl.$setValidity('match', currentValue);
                });
            }
        };
    });

})();



/*
 * //TODO: implementar load automatico
 *
 * var loadScript = function(src, callbackfn) { var newScript =
 * document.createElement("script"); newScript.type = "text/javascript";
 * newScript.setAttribute("async", "true"); newScript.setAttribute("src", src);
 *
 * if(newScript.readyState) { newScript.onreadystatechange = function() {
 * if(/loaded|complete/.test(newScript.readyState)) callbackfn(); } } else {
 * newScript.addEventListener("load", callbackfn, false); }
 *
 * document.documentElement.firstChild.appendChild(newScript); };
 *
 * loadScript("<s:property value='apiGoogleMapsSrc'/>", function() { });
 */