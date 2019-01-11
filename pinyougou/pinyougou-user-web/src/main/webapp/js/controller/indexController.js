app.controller("indexController", function ($scope, userService) {

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
});
