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
	
	$scope.deleteBadge = function(badgeId){
		BadgeService.deleteBadge(badgeId)
		.then (function success(response) {
			$rootScope.alert('Insígnia excluída com sucesso!');
		},
		function error(response) {
			$rootScope.alert('Error adding user!');
		});		
	}
	
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


app.controller('editBadgeController', [ '$scope', '$rootScope', '$timeout', 'BadgeService', '$routeParams', function($scope, $rootScope, $timeout, BadgeService, $routeParams) {
    $scope.badge = {};
    BadgeService.getBadge($routeParams.idBadge)    	
    .then (function success(response) {
    	$scope.badge = response.data;
    },
    function error(response) {
    	$rootScope.alert('Error adding user!');
    });
    
    
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