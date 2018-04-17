var app = angular.module('app', ['ngRoute','ngResource','ngTable']);
app.config(function($routeProvider){
    $routeProvider
        .when('/usuarios',{
            templateUrl: '/views/user/users.html',
            controller: 'usersController'
        })
        .when('/usuarios/novo',{
	        templateUrl: '/views/user/new-user.html',
	        controller: 'newUserController'
	    })
        .when('/insignias',{
            templateUrl: '/views/badge/badges.html',
            controller: 'badgesController'
        })
        .when('/insignias/novo',{
            templateUrl: '/views/badge/new-badge.html',
            controller: 'newBadgeController'
        })
        .when('/atributos',{
            templateUrl: '/views/attribute/attributes.html',
            controller: 'attributesController'
        })
        .when('/atributos/novo',{
            templateUrl: '/views/attribute/new-attribute.html',
            controller: 'newAttributeController'
        })
        .when('/regras-atributo',{
            templateUrl: '/views/ruleAttribute/rules-attribute.html',
            controller: 'ruleAttributeController'
        })
        .when('/regras-atributo/novo',{
            templateUrl: '/views/ruleAttribute/new-rule-attribute.html',
            controller: 'newRuleAttributeController'
        })
        .when('/regras-insignia',{
            templateUrl: '/views/ruleBadge/rules-badge.html',
            controller: 'ruleBadgeController'
        })
        .when('/regras-insignia/novo',{
            templateUrl: '/views/ruleBadge/new-rule-badge.html',
            controller: 'newRuleBadgeController'
        })
        .when('/regras-nivel',{
            templateUrl: '/views/rules-level.html',
            controller: 'ruleLevelController'
        })
        .otherwise(
            { redirectTo: '/'}
        );
});

app.run(['$rootScope','$timeout' , function ($rootScope, $timeout) {
	$rootScope.showAlert = false;
	$rootScope.alertMessage = "";
	
	$rootScope.alert = function (msg) {
		
		$rootScope.showAlert = true;
		$rootScope.alertMessage = msg;
		
		$timeout(function() {
			$rootScope.showAlert = false;
	    }, 3000);
	}
}]);

app.service('UserService', [ '$http', function($http) {
	 
    this.getUser = function getUser(userId) {
        return $http({
            method : 'GET',
            url : 'api/users/' + userId
        });
    }
    
    this.addUser = function addUser(user) {
        return $http({
            method : 'POST',
            url : 'api/users/',
            data : user
        });
    }
    
    this.getAllUsers = function getAllUsers() {
        return $http({
            method : 'GET',
            url : 'api/users/'
        });
    }
    
    
} ]);


app.service('BadgeService', [ '$http', function($http) {
	 
	this.getAllBadges = function getAllBadge() {
        return $http({
            method : 'GET',
            url : 'api/badges/'
        });
    }
	
    this.getBadge = function getBadge(badgeId) {
        return $http({
            method : 'GET',
            url : 'api/badges/' + badgeId
        });
    }
    
    this.addBadge = function addBadge(badge) {
        return $http({
            method : 'POST',
            url : 'api/badges/',
            data : badge
        });
    }    
    
	this.findAll = function findAll() {
        return $http({
            method : 'GET',
            url : 'api/badges/'
        });
    }
	
} ]);


app.service('AttributeService', [ '$http', function($http) {
	
	this.findAll = function findAll() {
        return $http({
            method : 'GET',
            url : 'api/attributes/'
        });
    }
	
    this.getAttribute = function getAttribute(attributeId) {
        return $http({
            method : 'GET',
            url : 'api/attributes/' + attributeId
        });
    }
    
    this.addAttribute = function addAttribute(attribute) {
        return $http({
            method : 'POST',
            url : 'api/attributes/',
            data : attribute
        });
    }    
} ]);

app.service('RuleService', [ '$http', function($http) {
	 
    this.getRule = function getRule(ruleId) {
        return $http({
            method : 'GET',
            url : 'api/rules/' + ruleId
        });
    }
    this.addRule = function addRule(rule) {
        return $http({
            method : 'POST',
            url : 'api/rules/',
            data : rule
        });
    }
    this.addRuleAttribute = function addRuleAttribute(ruleAttribute) {
    	return $http({
    		method : 'POST',
            url : 'api/attributes/rules/',
            data : ruleAttribute
        });
    }
    this.addRuleLevel = function addRuleLevel(ruleLevel) {
    	return $http({
    		method : 'POST',
            url : 'api/level/rules/',
            data : ruleLevel
        });
    }
    
    this.addRuleBadge = function addRuleBadge(ruleBadge) {
    	return $http({
    		method : 'POST',
            url : 'api/badges/rules/',
            data : ruleBadge
        });
    }
    
    this.addLevelReward = function addLevelReward(levelReward) {
    	return $http({
    		method : 'POST',
            url : 'api/level/rewards/',
            data : levelReward
        });
    }
    
    this.addRuleBadgeAttribute = function addRuleBadgeAttribute(ruleBadgeAttribute) {
    	return $http({
    		method : 'POST',
            url : 'api/badges/attributes/rewards/',
            data : ruleBadgeAttribute
        });
    }
    
    this.getAllAttributeRules = function getAllAttributeRules() {
        return $http({
            method : 'GET',
            url : 'api/attributes/rules/'
        });
    }
    
    this.getAllBadgeRules = function getAllBadgeRules() {
        return $http({
            method : 'GET',
            url : 'api/badges/rules/'
        });
    }
    
    
} ]);