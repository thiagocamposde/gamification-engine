app.controller('ruleXpController', [ '$scope', '$rootScope', '$timeout', 'RuleService','NgTableParams', function($scope, $rootScope, $timeout, RuleService, NgTableParams) {
	
	$scope.rulesTableParams = new NgTableParams({}, {
		getData: function(params) {
			return RuleService.findAllXpRules()
			.then (function success(response) {
				console.log(response);
				params.total(response.data.length);
				return response.data;
				
			},
			function error(response) {
				$rootScope.alert('Error adding attribute!');
			});			
		}
	});
	
	$scope.deleteRule = function(ruleId){
		RuleService.deleteRule(ruleId)
		.then (function success(response) {
			$rootScope.alert('Regra excluída com sucesso!');
		},
		function error(response) {
			$rootScope.alert('Error adding attribute!');
		});
	}
	
}]);

app.controller('newRuleXpController', [ '$scope', '$rootScope', '$timeout', 'RuleService','NgTableParams', '$routeParams', function($scope, $rootScope, $timeout, RuleService, NgTableParams, $routeParams) {

	$scope.rule = {type:'xp', finished:false, active:true, repeatable:false};
	
	if(typeof $routeParams.idRule != 'undefined') {
		
		RuleService.getRule($routeParams.idRule)
	    .then (function success(response) {
	    	console.log(response);
	    	$scope.rule = response.data;
	    },
	    function error(response) {
	     	$rootScope.alert('Error!');
	    });
	}
	
	$scope.saveRule = function () {
		RuleService.addRule($scope.rule)
		.then (function success(response) {        	
	    	$rootScope.alert('Regra adicionada com sucesso!');
	    },
	    function error(response) {
	    	$rootScope.alert('Error adding rule!');
	  });
	}
}]);
