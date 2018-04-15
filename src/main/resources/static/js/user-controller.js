app.controller('usersController', [ '$scope', '$rootScope', '$timeout', 'UserService','NgTableParams', function($scope, $rootScope, $timeout, UserService, NgTableParams) {
	
	$scope.tableParams = new NgTableParams({}, {
		getData: function(params) {
		
			return UserService.getAllUsers()
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