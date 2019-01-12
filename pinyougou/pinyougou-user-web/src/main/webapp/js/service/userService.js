app.service("userService",function($http){

    this.register = function (entity, smsCode) {
        return $http.post("user/add.do?smsCode=" + smsCode, entity);

    };

    this.sendSmsCode = function (phone) {
        return $http.get("user/sendSmsCode.do?phone=" +phone+"&r=" + Math.random());
    };

    this.getUsername = function () {
        return $http.get("user/getUsername.do?r=" + Math.random());

    };

    this.findAllOrder = function () {
        return $http.get("user/findAllOrder.do?t=" + Math.random());
    };

    this.findAllSecKillOrder = function () {
        return $http.get("user/findAllSecKillOrder.do?t=" + Math.random());

    };
});