//定义处理器
app.controller("orderController", function ($scope,$location, $http, $controller, orderService) {

    //继承一个controller
    $controller("baseController", {$scope:$scope});

    //查询所有
    $scope.findAll = function () {
        orderService.findAll().success(function (response) {
            $scope.list = response;

        }).error(function () {
            alert("加载数据失败！");
        });

    };


    //根据分页信息查询
    $scope.findPage = function (page, rows) {
        orderService.findPage(page, rows).success(function (response) {
            //response 分页结果对象total,rows
            $scope.list = response.rows;
            //总记录数
            $scope.paginationConf.totalItems = response.total;

        });

    };

    //保存方法
    $scope.save = function () {

        var obj;
        if($scope.entity.id != null){
            //修改
            obj = orderService.update($scope.entity);
        } else {
            obj = orderService.add($scope.entity);
        }

        obj.success(function (response) {
            if(response.success){
                //刷新列表
                $scope.reloadList();
            } else {
                alert(response.message);
            }

        });

    };

    //根据主键查询
    $scope.findOne = function () {
        $scope.id=$location.search()["id"];
        //alert($scope.id);
        orderService.findOne($scope.id).success(function (response) {
            $scope.entity = response;
        });
    };

    //删除
    $scope.delete = function () {
        if ($scope.selectedIds.length == 0) {
            alert("请先选择要删除的记录");
            return;
        }
        if(confirm("确定要删除选择了那些记录吗？")){
            //如果点击了 确定 则返回true
            orderService.delete($scope.selectedIds).success(function (response) {
                if (response.success) {
                    $scope.reloadList();
                } else {
                    alert(response.message);
                }

            });
        }

    };

    $scope.searchEntity = {};

    //查询方法
    $scope.search = function (page, rows) {

        orderService.search(page, rows, $scope.searchEntity).success(function (response) {
            //response 分页结果对象total,rows
            $scope.list = response.rows;
            //总记录数
            $scope.paginationConf.totalItems = response.total;
        });
    };

});
