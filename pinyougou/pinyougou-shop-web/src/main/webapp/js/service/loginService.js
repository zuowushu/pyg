app.service("loginService", function ($http) {
    this.getUsername = function () {
        return $http.get("../login/getUsername.do");
    };


    this.updatePassword=function (newPassword,oldPassword) {
        return $http.get("../login/updatePassword.do?newPassword="+newPassword+"&oldPassword="+oldPassword);
    }
});