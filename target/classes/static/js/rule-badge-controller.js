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


app.controller('newRuleBadgeController', [ '$scope', '$rootScope', '$timeout', 'RuleService', 'BadgeService','AttributeService', function($scope, $rootScope, $timeout, RuleService, BadgeService, AttributeService) {
	$scope.rule = {finished:false, active:true, repeatable:false, timesToComplete:1};
	$scope.ruleBadges = [{}];
	$scope.listBadges = [];
	$scope.listAttributes = [];
	$scope.selectedAttribute = {};
	$scope.type;
    
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
	
    $scope.saveRule = function () {
    	RuleService.addRule($scope.rule)
    	.then (function success(response) 
    	{        	
        	$scope.ruleBadges.forEach(function(ruleBadge)
        	{
        		ruleBadge.rule = response.data;
        		
        		if($scope.rule.type == 'badge') 
        		{
        			RuleService.addRuleBadge(ruleBadge)
                	.then(function success(response)
                	{
                		$rootScope.alert('Regra de insígnia adicionada com sucesso!');
                	});
        		}
        		else
        		{
        			ruleBadge.attribute = $scope.selectedAttribute;
        			RuleService.addRuleBadgeAttribute(ruleBadge)
                	.then(function success(response)
                	{
                		$rootScope.alert('Regra de insígnia adicionada com sucesso!');
                	});
        		}
        		
        	});
        },
        function error(response) {
        	$rootScope.alert('Error adding rule!');
      });
    }
}]);
