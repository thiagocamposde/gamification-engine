app.controller('attributesController', [ '$scope', '$rootScope', '$timeout', 'AttributeService','NgTableParams', function($scope, $rootScope, $timeout, AttributeService, NgTableParams) {
	
	$scope.attributesTableParams = new NgTableParams({}, {
		getData: function(params) {
		
			return AttributeService.findAll()
			.then (function success(response) {
				$scope.listUsers = response.data;
				params.total(response.data.length);
				return response.data;
				
			},
			function error(response) {
				$rootScope.alert('Error adding user!');
			});			
		}
	});
	
	$scope.deleteAttribute = function(attributeId){
		AttributeService.deleteAttribute(attributeId)
		.then (function success(response) {
			$rootScope.alert('Atributo excluído com sucesso!');
		},
		function error(response) {
			$rootScope.alert('Error adding user!');
		});
	}
	
}]);

app.controller('newAttributeController', [ '$scope', '$rootScope', '$timeout', 'AttributeService', function($scope, $rootScope, $timeout, AttributeService) {
    $scope.attribute = {};
   
    $scope.saveAttribute = function () {
    	AttributeService.addAttribute($scope.attribute)    	
        .then (function success(response) {
        	$rootScope.alert('Atributo adicionado com sucesso!');
        },
        function error(response) {
        	$rootScope.alert('Error adding attribute!');
      });
    }
}]);