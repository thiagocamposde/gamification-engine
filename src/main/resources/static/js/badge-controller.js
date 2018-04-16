app.controller('badgesController', [ '$scope', '$rootScope', '$timeout', 'BadgeService','NgTableParams', function($scope, $rootScope, $timeout, BadgeService, NgTableParams) {
	$scope.teste = "string";
	$scope.badgesTableParams = new NgTableParams({}, {
		getData: function(params) {
		
			return BadgeService.getAllBadges()
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
}]);

app.controller('newBadgeController', [ '$scope', '$rootScope', '$timeout', 'BadgeService', function($scope, $rootScope, $timeout, BadgeService) {
    $scope.badge = {};
   
    $scope.saveBadge = function () {
    	BadgeService.addBadge($scope.badge)    	
        .then (function success(response) {
        	$rootScope.alert('Emblema adicionado com sucesso!');
        },
        function error(response) {
        	$rootScope.alert('Error adding user!');
      });
    }
}]);