var ie = angular.module("conf-inicial",['ui.bootstrap']);
ie.controller('ieCtrl', ['$uibModal','$log', function($uibModal,$log){
	var self = this;
	self.newIE = function(){
		var modalInstante = $uibModal.open({
			animation: true,
			templateUrl:'formIE.html',
			controller:'newIEController',
			resolve:{
				mensaje:function(){
					return 'se registrar una nueva IE';
				}
			}
		});

		var okResult = function(result){
                        console.log('resultado',result);
		};
		var badResult = function(){
			$log.info('Modal cancel');
		};
		modalInstante.result.then(okResult,badResult);
	};
}]);
ie.controller('newIEController', ['$scope','$uibModalInstance','mensaje', function($scope,$uibModalInstance,mensaje){
	var ie = {};
	$scope.save = function(){
		ie.nombre = $scope.nameie;
		ie.address = $scope.addressie;
		ie.departament = $scope.departamentie;
		ie.province = $scope.provinceie;
		ie.district = $scope.districtie;
		ie.type = $scope.typeie;
		ie.gender = $scope.genderie;
		ie.ugel = $scope.ugelie;
                console.log('datos de la IE',ie);
                $uibModalInstance.close(ie);
	};
}]);