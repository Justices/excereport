angular.module('FlyerReport', ['ngRoute'])
    .controller('HomeController',function($scope,$route) {$scope.$route = $route})
    .controller('AverageController',function($scope,$route){$scope.$route = $route})
    .config(
    function($routeProvider) {
       $routeProvider
        .when('/',
              {templateUrl:'app/template/sourceData.html',
                controller : 'HomeController'
              })
        .when('/average',{templateUrl: 'app/template/average.html',
                            controller: 'AverageController'
                         })
        .when('/Charts',{templateUrl: 'app/template/charts.html'})
        .otherwise({redirectTo:'/'});
    });
