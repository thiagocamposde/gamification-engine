app.controller('ruleAttributeController', [ '$scope', '$rootScope', '$timeout', 'RuleService','NgTableParams', function($scope, $rootScope, $timeout, RuleService, NgTableParams) {
	$scope.ruleAttributeTableParams = new NgTableParams({}, {
		getData: function(params) {
			return RuleService.getAllAttributeRules()
			.then (function success(response) {
				params.total(response.data.length);
				return response.data;
			},
			function error(response) {
				$rootScope.alert('Error adding user!');
			});			
		}
	});
	
	$scope.deleteRuleAttribute = function(ruleAttributeId){
		RuleService.deleteRuleAttribute(ruleAttributeId)
		.then (function success(response) {
			$rootScope.alert('Regra de atributo excluída com sucesso!');
		},
		function error(response) {
			$rootScope.alert('Error adding user!');
		});		
	}
	
}]);

app.controller('newRuleAttributeController', [ '$scope', '$rootScope', '$timeout', 'RuleService', 'AttributeService', '$routeParams', function($scope, $rootScope, $timeout, RuleService, AttributeService, $routeParams) {
    $scope.rule = {type:'attribute', finished:false, active:true, repeatable:false};
    $scope.ruleAttributes = [{}];
    $scope.attributes = [];
    
    AttributeService.findAll()
    .then (function success(response) {
    	$scope.attributes = response.data;
    },
    function error(response) {
     	$rootScope.alert('Error!');
    });
    
    
    if(typeof $routeParams.idRule != 'undefined') {
    	
    	console.log('111111');
    	RuleService.getRuleAttributesByRule($routeParams.idRule)
        .then (function success(response) {
        	console.log(response);
        	console.log('222222');
        	
        	//todos possuem a mesma rule, por isso pegar a de [0]
        	$scope.rule = response.data[0].rule;
        	$scope.ruleAttributes = response.data;
//        	
        },
        function error(response) {
         	$rootScope.alert('Error!');
        });
    	
    }

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