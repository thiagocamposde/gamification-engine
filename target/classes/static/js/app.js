var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
        .when('/users',{
            templateUrl: '/views/users.html',
            controller: 'usersController'
        })
        .when('/badges',{
            templateUrl: '/views/badges.html',
            controller: 'badgesController'
        })
        .when('/attributes',{
            templateUrl: '/views/attributes.html',
            controller: 'attributesController'
        })
        .when('/rules',{
            templateUrl: '/views/rules.html',
            controller: 'rulesController'
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
} ]);


app.service('BadgeService', [ '$http', function($http) {
	 
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
} ]);


app.service('AttributeService', [ '$http', function($http) {
	 
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
} ]);