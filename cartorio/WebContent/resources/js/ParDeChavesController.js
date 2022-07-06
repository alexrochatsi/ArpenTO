angular.module('certisignonApp').controller('ParDeChavesController', function ($scope, $location, $modal, $log, $http, $loading, $interval, $route) {

	this.chave = {};
	$scope.chaves = {};

	$scope.itemsPerPage = 5;
	$scope.currentPage = 1;

	$scope.maxSize = 5;
	$scope.bigTotalItems = 200;
	$scope.bigCurrentPage = 1;

	this.init = function () {
		$scope.erro = {};
		$scope.sucesso = {};
		$scope.data = 'nada';

		$loading.start('loading');

		$http.get('/CertisignLogin/gestor/getUser.json').success(function(message) {

			$scope.usuario = message;
			//$scope.chaves = $scope.usuario.controlesCriptograficos;

			idUsuario = $scope.usuario.id;

			$http.get('/CertisignLogin/chave-criptografica/lista/'+ idUsuario).success(function(chaveList){
				$scope.chaves = chaveList;

				$scope.totalItems = $scope.chaves.length;

				$scope.pageCount = function () {
					return Math.ceil($scope.chaves.length / $scope.itemsPerPage);
				};

				$scope.$watch('currentPage + itemsPerPage', function() {
					var begin = (($scope.currentPage - 1) * $scope.itemsPerPage), end = begin + $scope.itemsPerPage;

					$scope.filteredChaves = $scope.chaves.slice(begin, end);
				});

				$loading.finish('loading');

			});
		}).error(function(message) {
			dialogs.error('Erro', JSON.stringify(message));
		});

		// PMAC TESTE
		/*$scope.chaves = [{
                id: 1,
                nome: "CHAVE 1",
                descricao: "Descricao Teste"
            },
            {
                id: 2,
                nome: "CHAVE 2",
                descricao: "Descricao Teste 2"
            }];*/
	};

	this.listar = function() {
		$location.path('/par-de-chaves');
	};

	this.gerarChave = function () {

		var chavelocal = this.chave;

		// abrir modal
		var modalInstance = $modal.open({
			templateUrl: '/CertisignLogin/gestor/js/certisignon/modalChave.html',
			controller: ModalInstanceCtrl,
			backdrop: 'static',
			resolve: {
				chave: function () {
					return chavelocal;
				}
			}
		});

		modalInstance.result.then(function (selectedItem) {
			$scope.selected = selectedItem;
		}, function () {
		});
	};

	this.editar = function (id) {
	};

	this.download = function (id, nome) {
		var parametros = {
				params: {
					/*
					 * source : link, category_id : category
					 */
				}
		};

		$http.get('/CertisignLogin/chave-criptografica/download/' + id + '/privada/'+ nome, parametros).success(function (data, status, headers, config) {

			/*var element = angular.element('<a/>');
            element.attr({
                href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
                target: '_blank',
                download: 'filename.cer'
            })[0].click();*/

			var hiddenElement = document.createElement('a');

			hiddenElement.href = 'data:attachment/csv;charset=utf-8,' + encodeURI(data);
			hiddenElement.target = '_blank';
			hiddenElement.download = id + '.pk';
			hiddenElement.click();

		}).error(function (data, status, headers, config) {
			dialogs.error('Erro', JSON.stringify(data));
		});

	};

	this.remover = function (id) {
	};

	this.gerarNovaChave = function (id) {
		$scope.prepararChave();
		$location.path('par-de-chaves/nova');
	};

	this.fecharModal = function (id) {
	};

	/*this.add = function () {
		alert('aadd ok!');
		var f = document.getElementById('file').files[0],
		r = new FileReader();
		alert('f');
		r.onloadend = function (e) {
			alert('ok!');
			$scope.data = e.target.result;
		};
		alert('er');
		r.readAsBinaryString(f);
	};*/

	this.gerarChavePendente = function (chavePendente) {

		var chavelocal = chavePendente;

		// abrir modal
		var modalInstance = $modal.open({
			templateUrl: '/CertisignLogin/gestor/js/certisignon/modalChave.html',
			controller: ModalPendenteInstanceCtrl,
			backdrop: 'static',
			resolve: {
				chave: function () {
					return chavelocal;
				}
			}
		});
		modalInstance.result.then(function (selectedItem) {
			$scope.selected = selectedItem;
		}, function () {
		});

		$scope.prepararChave();
	};

	$scope.prepararChave = function () {
		var timer = undefined;

		timer = $interval(function() {
			$.ajax({
				url: '/CertisignLogin/chave-criptografica/statusChave',
				success: function(data) {
					if(data != "" && data.chaveGerada) {
						$route.reload();
						$interval.cancel(timer);
						timer = undefined;
					}
				}
			});
		}, 2000);

	};


});

//Please note that $modalInstance represents a modal window (instance)
//dependency.
//It is not the same as the $modal service used above.

var ModalInstanceCtrl = function ($scope, $modalInstance, chave, $location, $http, $log) {

	this.chave = chave;

	$scope.init = function () {

		this.chave = chave;

		// chamar controller para criar
		$http.post('/CertisignLogin/chave-criptografica/nova', this.chave).success(function (data) {

			$modalInstance.dismiss('cancel');
			$location.path('par-de-chaves');

		});
	};

	$scope.ok = function () {
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
		$location.path('par-de-chaves');
	};

	$scope.init();
};

var ModalPendenteInstanceCtrl = function ($scope, $modalInstance, chave, $location, $http, $log) {

	this.chave = chave;

	$scope.init = function () {

		this.chave = chave;

		// chamar controller para criar
		$http.get('/CertisignLogin/chave-criptografica/gerarChave/' + this.chave.id).success(function (data) {

			$modalInstance.dismiss('cancel');
			$location.path('par-de-chaves');

		});
	};

	$scope.init();
};