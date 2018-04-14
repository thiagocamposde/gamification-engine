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

app.controller('ruleAttributeController', [ '$scope', '$rootScope', '$timeout', 'RuleService', 'AttributeService', function($scope, $rootScope, $timeout, RuleService, AttributeService) {
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


app.controller('ruleBadgeController', [ '$scope', '$rootScope', '$timeout', 'RuleService', 'BadgeService','AttributeService', function($scope, $rootScope, $timeout, RuleService, BadgeService, AttributeService) {
	$scope.rule = {type:'badge', finished:false, active:true, repeatable:false, timesToComplete:1};
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
        		
        		if($scope.type == 'event') 
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


