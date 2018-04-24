app.controller('ruleBadgeController', [ '$scope', '$rootScope', '$timeout', 'RuleService','NgTableParams', function($scope, $rootScope, $timeout, RuleService, NgTableParams) {
	$scope.ruleBadgeTableParams = new NgTableParams({}, {
		getData: function(params) {
			return RuleService.getAllBadgeRules()
			.then (function success(response) {
				params.total(response.data.length);
				return response.data;
			},
			function error(response) {
				$rootScope.alert('Error adding user!');
			});			
		}
	});
	
	$scope.deleteRule = function(ruleId){
		RuleService.deleteRule(ruleId)
		.then (function success(response) {
			$rootScope.alert('Regra de atributo excluída com sucesso!');
		},
		function error(response) {
			$rootScope.alert('Error adding user!');
		});		
	}
}]);


app.controller('newRuleBadgeController', [ '$scope', '$rootScope', '$timeout', 'RuleService', 'BadgeService','AttributeService','$routeParams', function($scope, $rootScope, $timeout, RuleService, BadgeService, AttributeService, $routeParams) {
	$scope.rule = {type:"badge", finished:false, active:true, repeatable:false, timesToComplete:1};
	$scope.ruleBadges = [{}];
	$scope.listBadges = [];
	$scope.listAttributes = [];
	$scope.selectedAttribute = {};
    
    AttributeService.findAll()
    .then (function success(response) {
    	$scope.listAttributes = response.data;
    },
    function error(response) {
     	$rootScope.alert('Error!');
    });
    
    BadgeService.findAll()
    .then (function success(response) {
    	$scope.listBadges = response.data;
    },
    function error(response) {
     	$rootScope.alert('Error!');
    });
	
    //EDIÇÃO	
    if($routeParams.idRule != 'undefined') {
    		
    	RuleService.getRuleBadge($routeParams.idRule)
        .then (function success(response) {
        	console.log(response);
        	console.log('asdasda');
        	
        	
        	$scope.rule = response.data.rule;
        	$scope.ruleBadges[0].attribute = response.data.attribute
        	$scope.ruleBadges[0].badge = response.data.badge;
        	$scope.ruleBadges[0].goalValue = response.data.goalValue;
        	$scope.ruleBadges[0].id = response.data.id;
        },
        function error(response) {
         	$rootScope.alert('Error!');
        });
    	
    }
    
    $scope.saveRule = function () {
    	RuleService.addRule($scope.rule)
    	.then (function success(response) 
    	{        	
        	$scope.ruleBadges.forEach(function(ruleBadge)
        	{
        		ruleBadge.rule = response.data;
    			RuleService.addRuleBadge(ruleBadge)
            	.then(function success(response)
            	{
            		$rootScope.alert('Regra de insígnia adicionada com sucesso!');
            	});
        	});
        },
        function error(response) {
        	$rootScope.alert('Error adding rule!');
      });
    }
}]);