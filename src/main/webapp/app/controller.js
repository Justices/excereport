function sourceDataController($scope, $route,$http) {
    $scope.$route = $route;
    
    $http.post('/report/sourceData/flyer_20170704')
        .then(function successCallback($response){
         $scope.sourceData = $response.data;
    });
}

function homeController($scope,$route){
    $scope.$route = $route;
}
