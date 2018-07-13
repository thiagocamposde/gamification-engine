app.controller('usersController', [ '$scope', '$rootScope', '$timeout', 'UserService','NgTableParams', function($scope, $rootScope, $timeout, UserService, NgTableParams) {
	
	$scope.usersTableParams = new NgTableParams({}, {
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
	
	
	$scope.deleteUser = function(userId){
		UserService.deleteUser(userId)
		.then (function success(response) {
			$rootScope.alert('Usuário excluído com sucesso!');
		},
		function error(response) {
			$rootScope.alert('Error adding user!');
		});		
	}
}]);

app.controller('newUserController', [ '$scope', '$rootScope', '$timeout', 'UserService', function($scope, $rootScope, $timeout, UserService,) {
    $scope.user = {level:1, current_xp:0, xp:0, active:true};
   
    $scope.saveUser = function () {
    	UserService.addUser($scope.user)
        .then (function success(response) {
        	$rootScope.alert('Usuário adicionado com sucesso!');
        },
        function error(response) {
        	$rootScope.alert('Error adding user!');
      });
    }
}]);

app.controller('editUserController', [ '$scope', '$rootScope', '$timeout', '$routeParams', 'UserService', function($scope, $rootScope, $timeout, $routeParams, UserService) {
	$scope.user = {};
	
    UserService.getUser($routeParams.idUser)
    .then (function success(response) {
    	$scope.user = response.data;
    },
    function error(response) {
    	$rootScope.alert('Error adding user!');
    });
   
    $scope.saveUser = function () {
    	UserService.addUser($scope.user)
        .then (function success(response) {
        	$rootScope.alert('Usuário atualizado com sucesso!');
        },
        function error(response) {
        	$rootScope.alert('Error adding user!');
      });
    }
}]);