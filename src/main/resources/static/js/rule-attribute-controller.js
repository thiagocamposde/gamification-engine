app.controller('ruleAttributeController', [ '$scope', '$rootScope', '$timeout', 'RuleService','NgTableParams', function($scope, $rootScope, $timeout, RuleService, NgTableParams) {
	console.log('asdasdasd');
	
	$scope.ruleAttributeTableParams = new NgTableParams({}, {
		getData: function(params) {
			console.log('aqui 1');
			return RuleService.getAllAttributeRules()
			.then (function success(response) {
				console.log('aqui 2');
				console.log(response);
				
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

app.controller('newRuleAttributeController', [ '$scope', '$rootScope', '$timeout', 'RuleService', 'AttributeService', function($scope, $rootScope, $timeout, RuleService, AttributeService) {
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