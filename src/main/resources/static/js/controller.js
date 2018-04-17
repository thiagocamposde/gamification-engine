
app.controller('ruleLevelController', [ '$scope', '$rootScope', '$timeout', 'RuleService','AttributeService', function($scope, $rootScope, $timeout, RuleService, AttributeService) {
	$scope.rule = {type:'level', timesToComplete:0, finished:false, active:true, repeatable:false, xp:0};
	$scope.ruleLevels = [{}];
	$scope.levelRewards = [];
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
            		$scope.levelRewards.forEach(function(levelReward)
            		{
            			levelReward.ruleLevel = response.data;
            			RuleService.addLevelReward(levelReward)
            			.then(function success(response)
            			{
            				
            				$rootScope.alert('Regra de nível adicionada com sucesso!');
            			},
            			function error(response)
            			{
            				$rootScope.alert('Error adding rule!');
            			});
            		});
            	});
        	});
        },
        function error(response) 
        {
        	$rootScope.alert('Error adding rule!');
      });
    }
    
    $scope.addLevelReward = function () {
    	$scope.levelRewards.push({});
    }
}]);

//app.controller('ruleLevelRewardController', [ '$scope', '$rootScope', '$timeout', 'RuleService', 'AttributeService', function($scope, $rootScope, $timeout, RuleService, AttributeService) {
//	$scope.rule = {type:'level', timesToComplete:0, finished:false, active:true, repeatable:false, xp:0};
//	$scope.levelRewards = [{}];
//	
//    $scope.saveRule = function () {
//    	RuleService.addRule($scope.rule)
//    	.then (function success(response) {        	
//        	console.log(response);
//        	
//        	$rootScope.alert('Regra adicionada com sucesso!');
//        	
//        	$scope.ruleLevels.forEach(function(ruleLevel){
//        		ruleLevel.rule = response.data;
//        		
//        		RuleService.addRuleLevel(ruleLevel)
//            	.then(function success(response){
//            		$rootScope.alert('Regra de atributo adicionada com sucesso!');
//            	});
//        	});
//        },
//        function error(response) {
//        	$rootScope.alert('Error adding rule!');
//      });
//    }
//    
//    $scope.addAttribute = function () {
//    	$scope.ruleAttributes.push({});
//    }
//}]);


