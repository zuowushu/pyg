app.service("userService",function($http){

    this.register = function (entity, smsCode) {
        return $http.post("user/add.do?smsCode=" + smsCode, entity);
    };
    this.isTure = function (entity, smsCode) {
        return $http.post("user/isTure.do?smsCode=" + smsCode, entity);
    };
    this.updatePhone = function (entity, smsCode) {
        return $http.post("user/updatePhone.do?smsCode=" + smsCode, entity);
    };

    this.sendSmsCode = function (phone) {
        return $http.get("user/sendSmsCode.do?phone=" +phone+"&r=" + Math.random());
    };

    this.getUsername = function () {
        return $http.get("user/getUsername.do?r=" + Math.random());
    };

    this.setPassword = function (entity) {
        debugger;
        return $http.post("user/setPassword.do",entity);
    };


    //初始化加载所有订单列表
    this.getOrderList = function (searchMap) {
        return $http.post("user/getOrderList.do",searchMap);

    };
    this.getUser=function () {
        return $http.get("user/getUser.do?t="+Math.random())
    };
    //上传图片
    this.uploadFile = function () {
        var formData = new FormData();
        formData.append("up_img_WU_FILE_0", up_img_WU_FILE_0.files[0]);
        return $http({
            url:"../upload.do",
            method:"post",
            data:formData,
            headers:{"Content-Type": undefined},
            transformRequest: angular.identity
        });
    };
    //修改个人信息
    this.addUser=function (entity) {
        return $http.post("user/addUser.do?t="+Math.random(),entity);
    };

});