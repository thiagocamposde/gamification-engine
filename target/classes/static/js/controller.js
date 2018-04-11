app.controller('usersController', [ '$scope', '$rootScope', '$timeout', 'UserService', function($scope, $rootScope, $timeout, UserService,) {
    $scope.user = {level:0, current_xp:0, xp:0, active:true};
   
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


app.controller('badgesController', [ '$scope', '$rootScope', '$timeout', 'BadgeService', function($scope, $rootScope, $timeout, BadgeService) {
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

app.controller('attributesController', [ '$scope', '$rootScope', '$timeout', 'AttributeService', function($scope, $rootScope, $timeout, AttributeService) {
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

app.controller('rulesController', [ '$scope', '$rootScope', '$timeout', 'RuleService', 'AttributeService', function($scope, $rootScope, $timeout, RuleService, AttributeService) {
    $scope.rule = {finished:false, active:true, repeatable:false};
    $scope.ruleAttributes = [{}];
    $scope.attributes = [];
    
     AttributeService.findAll()
     .then (function success(response) {
    	 $scope.attributes = response.data;
     },
     function error(response) {
     	$rootScope.alert('Error!');
     });

    $scope.saveRule = function () {
    	RuleService.addRule($scope.rule)
    	.then (function success(response) {        	
        	console.log(response);
        	
        	$rootScope.alert('Regra adicionada com sucesso!');
        	
        	$scope.ruleAttributes.forEach(function(ruleAttribute){
        		ruleAttribute.rule = response.data;
        		
        		RuleService.addRuleAttribute(ruleAttribute)
            	.then(function success(response){
            		$rootScope.alert('Regra de atributo adicionada com sucesso!');
            	});
        	});
        },
        function error(response) {
        	$rootScope.alert('Error adding rule!');
      });
    }
    
    $scope.addAttribute = function () {
    	$scope.ruleAttributes.push({});
    }
}]);