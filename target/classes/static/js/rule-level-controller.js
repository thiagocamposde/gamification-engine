app.controller('ruleLevelController', [ '$scope', '$rootScope', '$timeout', 'RuleService','NgTableParams', function($scope, $rootScope, $timeout, RuleService, NgTableParams) {
	
	$scope.ruleLevelTableParams = new NgTableParams({}, {
		getData: function(params) {
			return RuleService.getAllLevelRules()
			.then (function success(response) {
				console.log(response);
				
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
			$rootScope.alert('Regra excluída com sucesso!');
		},
		function error(response) {
			$rootScope.alert('Error adding user!');
		});		
	}
	
}]);


app.controller('newRuleLevelController', [ '$scope', '$rootScope', '$timeout', 'RuleService','AttributeService', '$routeParams', function($scope, $rootScope, $timeout, RuleService, AttributeService, $routeParams) {
	$scope.rule = {type:'level', timesToComplete:0, finished:false, active:true, repeatable:false, xp:0};
	$scope.ruleLevels = [{}];
	$scope.levelRewards = [{}];
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
    	.then (function success(response) 
    	{        	
        	$scope.ruleLevels.forEach(function(ruleLevel)
        	{
        		ruleLevel.rule = response.data;
        		RuleService.addRuleLevel(ruleLevel)
            	.then(function success(response)
            	{
            		alert('1');
            		console.log('AAAAAAAAAAAAAAAAAAAAA');
            		console.log($scope.levelRewards);
            		$scope.levelRewards.forEach(function(levelReward)
            		{
            			console.log('bbbbbbbbbbbbbbbbbbbbbb');
            			levelReward.ruleLevel = response.data;
            			RuleService.addLevelReward(levelReward);            			
            		});
            	});
        	});
        	
        	$rootScope.alert('Regra de nível adicionada com sucesso!');
        },
        function error(response) 
        {
        	$rootScope.alert('Error adding rule!');
      });
    }
    
    $scope.addLevelReward = function () {
    	$scope.levelRewards.push({});
    }  
   
    //EDIÇÃO	
    if(typeof $routeParams.idRuleLevel != 'undefined') {
    	
    	console.log('asdasda');
    	
    	//get rule level
    	RuleService.getRuleLevel($routeParams.idRuleLevel)
        .then (function success(response) {
        	console.log(response);
        	console.log('aaaaaaaaaaaaaaaaaaaaaaaaaaa');
        	$scope.rule = response.data.rule;
        	$scope.ruleLevels[0] = response.data;
        },
        function error(response) {
         	$rootScope.alert('Error!');
        });
    	
    	//get rule level rewards
    	RuleService.getRuleLevelRewardsByRuleLevel($routeParams.idRuleLevel)
        .then (function success(response) {
        	console.log(response);
        	console.log('bbbbbbbbbbbbbbbbbbbbb');
        	if(response.data)
        		$scope.levelRewards = response.data;
        },
        function error(response) {
         	$rootScope.alert('Error!');
        });
    }
    
    
}]);