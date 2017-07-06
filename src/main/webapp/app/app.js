var reportModel = angular.module('FlyerReport', ['ngRoute']);

function route($locationProvider,$routeProvider) {
    $locationProvider.hashPrefix(''); 
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
}

reportModel.controller('AverageController',sourceDataController);
reportModel.controller('HomeController',homeController);
reportModel.config(route);
