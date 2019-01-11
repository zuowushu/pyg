app.controller("indexController", function ($scope, loginService) {
    //获取登录用户名
    $scope.getUsername = function () {
        loginService.getUsername().success(function (response) {
            $scope.username = response.username;
        })
    };

    $scope.oldPassword="";
    $scope.newPassword="";
    $scope.againpassword="";
    //修改用户密码
    $scope.updatePassword=function () {

        if($scope.oldPassword ==""){
            alert("请输入原密码");
            return ;
        }
        if($scope.newPassword ==""){
            alert("请输入新密码");
            return ;
        }
        if($scope.newPassword !=$scope.againpassword){
            alert("确认密码不对，请输入正确的密码");
            return ;
        }
        loginService.updatePassword($scope.newPassword,$scope.oldPassword).success(function (response) {
            if(response.success){
               alert(response.message);
               //跳转到登录页面
                parent.location.href="../shoplogin.html";
            }else{
                alert(response.message);
            }
        })
    }
});