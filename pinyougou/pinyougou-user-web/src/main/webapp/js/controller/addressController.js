app.controller("addressController", function ($scope, $controller,addressService) {

    //用户中心-地址管理-获取当前登录用户的收件人地址列表
    $scope.findAddressList = function () {
        addressService.findAddressList().success(function (response) {
            $scope.addressList = response;
            //查询默认地址
            /*for (var i = 0; i < response.length; i++) {
                var address = response[i];
                if("1"==address.isDefault){
                    $scope.address = address;
                    break;
                }
            }*/
        });
    };

    $scope.entity = {"contact":"", "address":"", "mobile":"", "alias":"","provinceId":"","cityId":"","areasId":""};
    //所在地区三级联动
    //读取一级省份分类列表
    $scope.selectProvincesList = function () {
        addressService.findProvincesList().success(function (response) {
            $scope.provincesList = response;
        });
    };

    //读取二级市级分类列表
    $scope.$watch("entity.province.provinceid", function (newValue, oldValue) {
        if (newValue != undefined) {
            addressService.findByProvincesId(newValue).success(function (response) {
                $scope.cityList = response;
            });
        }
    });

    //读取三级县、区分类列表
    $scope.$watch("entity.city.cityid", function (newValue, oldValue) {
        if (newValue != undefined) {
            addressService.findByCityId(newValue).success(function (response) {
                $scope.areasList = response;
            });
        }
    });
    //用户中心-地址管理-新增地址
    $scope.saveAddress =function () {
        if ($scope.entity.contact == "") {
            alert("请输入收货人");
            return;
        }
        if ($scope.entity.address == "") {
            alert("请输入详细地址");
            return;
        }
        if ($scope.entity.mobile == "") {
            alert("请输入联系电话");
            return;
        }
        if ($scope.entity.alias == "") {
            alert("请输入地址别名");
            return;
        }
        addressService.saveAddress($scope.entity).success(function (response) {
            if (response.success) {
                if ("增加成功" == response.message) {
                    alert("新增成功！");
                } else {
                    alert("修改成功！");
                }
                location.href = "home-setting-address.html";
            } else if ("增加失败" == response.message) {
                alert("新增失败!");
            } else {
                alert(response.message);
            }
        });
    };

    //根据地址的id查询地址
    $scope.findOne = function (id) {
        addressService.findOne(id).success(function (response) {
            $scope.entity = response;
        });
    };
    //根据地址id的删除地址
    $scope.deleteAddress = function (id) {
        if (confirm("确定要删除吗？")){
            addressService.deleteAddress(id).success(function (response) {
                if (response.success){
                    location.href = "home-setting-address.html";
                }
            });
        }
    };
    //设置为默认地址
    $scope.updateIsDefault = function (isDefault) {
        addressService.updateIsDefault(isDefault).success(function (response) {
            alert(response.message);
        });
    };
});