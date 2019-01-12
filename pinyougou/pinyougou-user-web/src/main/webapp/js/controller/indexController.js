app.controller("indexController", function ($scope,$location,userService) {

    $scope.getUsername = function () {
        userService.getUsername().success(function (response) {
            $scope.username = response.username;
        });
    };


    $scope.findAllOrderItem = function () {
        userService.findAllOrderItem().success(function (response) {
            $scope.orderItemList = response;

        })
    };


    $scope.findAllOrder = function () {
        userService.findAllOrder().success(function (response) {
            $scope.orderList = response;
        });
    };

    $scope.findAllSecKillOrder = function () {
        userService.findAllSecKillOrder().success(function (response) {
            $scope.secKillOrderList = response;
        });
    };

});