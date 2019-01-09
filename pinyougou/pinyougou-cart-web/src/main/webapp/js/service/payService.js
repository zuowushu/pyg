app.service("payService", function ($http) {

    this.createNative = function (outTradeNo) {

        return $http.get("pay/createNative.do?outTradeNo=" + outTradeNo + "&t=" + Math.random());

    };
    this.queryPayStatus = function (outTradeNo) {

        return $http.get("pay/queryPayStatus.do?outTradeNo=" + outTradeNo + "&t=" + Math.random());

    };

});