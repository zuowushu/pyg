app.service("addressService", function ($http) {

    this.findAddressList = function () {
        return $http.get("../address/findAddressList.do?t=" + Math.random());
    };

    this.saveAddress = function (address) {
        return $http.post("address/add.do",address);
    };
    this.findProvincesList = function(){
        return $http.get("../address/findProvincesList.do");
    };
    this.findByProvincesId = function (provinceid) {
        return $http.get("../address/findByCityList.do?provinceid=" + provinceid);
    };
    this.findByCityId = function (cityid) {
        return $http.get("../address/findByCityId.do?cityid=" + cityid);
    };
});
