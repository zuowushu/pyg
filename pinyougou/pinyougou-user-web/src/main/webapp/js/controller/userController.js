app.controller("userController", function ($scope, $controller, userService) {

    /*$scope.getUsername = function () {
        userService.getUsername().success(function (response) {
            $scope.username = response.username;
        })
    }*/
    $controller("indexController", {$scope:$scope});
    $scope.entity = {"username":"", "password":"", "phone":""};
    $scope.register = function () {
        if ($scope.entity.username == "") {
            alert("请输入用户名");
            return;
        }

        if ($scope.entity.password == "") {
            alert("请输入密码");
            return;
        }
        //判断两次密码是否一致
        if($scope.password != $scope.entity.password){
            alert("两次输入的密码不一致;请重新输入");
            return;
        }

        if ($scope.entity.phone == "") {
            alert("请输入手机号");
            return;
        }

        userService.register($scope.entity, $scope.smsCode).success(function (response) {
            alert(response.message);
        });
    };
    $scope.step = function () {
        if ($scope.entity.phone == null) {
            alert("请输入手机号");
            return;
        }
        if ($scope.smsCode == null) {
            alert("请输入验证码");
            return;
        }
        userService.isTure( $scope.entity,$scope.smsCode).success(function (response) {
            if (response.success) {
                alert(response.message);
                window.location.href="http://user.pinyougou.com/home-setting-address-phone.html";
            }else {
                alert(response.message);
            }
        });
    };
    $scope.updatePhone = function () {
        if ($scope.entity.phone == null) {
            alert("请输入手机号");
            return;
        }
        if ($scope.smsCode == null) {
            alert("请输入验证码");
            return;
        }
        userService.updatePhone( $scope.entity,$scope.smsCode).success(function (response) {
            if (response.success) {
                alert(response.message);
                window.location.href="http://user.pinyougou.com/home-setting-address-complete.html";
            }else {
                alert(response.message);
            }
        });
    };
    $scope.entity = {};
    $scope.sendSmsCode = function () {
        if($scope.entity.phone == null || $scope.entity.phone=="") {
            alert("请输入手机号");
            return;
        }

        userService.sendSmsCode($scope.entity.phone).success(function (response) {
            alert(response.message);
        });
    };
//    修改密码
    $scope.save = function () {
        debugger;
        if ($scope.entity.username != null) {
            if ($scope.entity.OldPasswordOne != null &&
                $scope.entity.OldPasswordTwo != null) {
                if ($scope.entity.OldPasswordOne == $scope.entity.OldPasswordTwo) {
                    userService.setPassword($scope.entity).success(function (response) {
                        if (response.success) {
                            alert(response.message);
                            window.location.href="http://user.pinyougou.com/logout/cas";
                        } else {
                            alert(response.message);
                        }
                    })
                } else {
                    alert("密码输入不一致，请重新输入")
                }
            } else {
                alert("请输入两次要修改的密码")
            }
        }else {
            alert("请输入用户名")
        }

    }
});