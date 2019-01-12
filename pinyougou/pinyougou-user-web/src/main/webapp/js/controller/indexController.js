app.controller("indexController", function ($scope,userService) {
    $scope.getUsername = function () {
        userService.getUsername().success(function (response) {
            $scope.username = response.username;
            $scope.entity.username=$scope.username;
        });
    };
    //上传商品图片
    $scope.uploadFile = function () {
        userService.uploadFile().success(function (response) {
            if (response.success) {
                $scope.entity.headPic = response.message;
            } else {
                alert(response.message);
            }
        }).error(function () {
            alert("上传图片失败");
        });
    };

    //初始化加载所有订单列表
    $scope.getOrderList= function () {

        userService.getOrderList($scope.searchMap).success(function (response) {
            debugger
            $scope.resultMap = response;
            //构建页面分页导航条信息
            buildPageInfo();
        });
    };

    //分页条件对象
    $scope.searchMap = {"pageNo":1,"pageSize":2};

    //构建页面分页导航条信息
    buildPageInfo = function () {
        //页面中要显示的页号数组
        $scope.pageNoList= [];
        //在导航条中总显示页号数
        var showPageNoTotal = 5;
        //起始页号
        var startPageNo = 1;
        //结束页号
        var endPageNo = $scope.resultMap.totalPages;
        //如果总页数大于要显示的页号数
        if($scope.resultMap.totalPages > showPageNoTotal) {
            //当前页号的左右间隔
            var interval = Math.floor(showPageNoTotal/2);
            startPageNo = parseInt($scope.searchMap.pageNo) - interval;
            endPageNo = parseInt($scope.searchMap.pageNo) + interval;

            if(startPageNo >= 1) {
                // 如果结束页号是大于总页数的则都设置为总页数，起始页号就为要总页数-要显示的页数并加1
                if(endPageNo > $scope.resultMap.totalPages){
                    startPageNo = $scope.resultMap.totalPages - showPageNoTotal + 1;
                    endPageNo = $scope.resultMap.totalPages;
                }
            } else {
                // 如果起始页号是小于1的则都设置为1，结束页号就为要显示的总页号数
                startPageNo = 1;
                endPageNo = showPageNoTotal;
            }
        }
        // 前面3个点：如果起始页号大于1则存在；
        $scope.frontDot = false;
        if (startPageNo > 1) {
            $scope.frontDot = true;
        }
        // 后面3个点：如果结束页号小于总页数则存在；
        $scope.backDot = false;
        if (endPageNo < $scope.resultMap.totalPages) {
            $scope.backDot = true;
        }
        //获得正确的页号
        for (var i = startPageNo; i <= endPageNo; i++) {
            $scope.pageNoList.push(i);
        }


    };
    $scope .queryByPageNo=function (pageNo) {
        $scope.searchMap.pageNo=pageNo;
        $scope.getOrderList();
    }
    $scope.isCurrentPage = function (pageNo) {
        return $scope.searchMap.pageNo==pageNo;
    };



    $scope.entity = {"username":"", "sex":"", "year":"","month":"","day":"","headPic":"","birthday":""};
    $scope.entity.sex="1";
    $scope.getUser=function () {
        userService.getUser().success(function (respons) {
            if (respons.sex==0){
                respons.sex=1;
            }
            $scope.entity.sex =respons.sex;
            $scope.entity.year=respons.year;
            $scope.entity.month=respons.month;
            $scope.entity.day=respons.day;
            $scope.entity.headPic=respons.headPic;
            // $scope.entity.province=respons.province;
            // $scope.entity.city=respons.city;
            // $scope.entity.town=respons.town;
            /* $("#select_year2").val(parseInt(respons.year));
             $("#select_month2").val(parseInt(respons.month));
             $("#select_day2").val(parseInt(respons.day));
             $("#province1").val("respons.province");
             $("#province1").trigger("change");
             $("#city1").val("respons.city");
             $("#city1").trigger("change");
             $("#district1").val("respons.town");*/

            // $("#select_year2").val(respons.year);
            // $("#select_month2").val( respons.month);
            // $("#select_day2").val( respons.day);
        })
    };
    $scope.addUser=function () {
        /* if (JSON.parse($scope.entity.month)<10){
             $scope.entity.month=JSON.stringify("0"+JSON.parse($scope.entity.month));
         }
         if ($scope.entity.day<10&& $scope.entity.day != 1){
             $scope.entity.day=JSON.stringify("0"+JSON.parse($scope.entity.day));
         }*/
        $scope.entity.birthday =JSON.parse($scope.entity.year)+"-"+JSON.parse($scope.entity.month)+"-"+$scope.entity.day;
        $scope.entity.birthday= Date.parse($scope.entity.birthday);
        userService.addUser($scope.entity).success(function (response) {
            if (response.success) {
                alert(response.message);
            } else {
                alert(response.message);
            }
        });
    };

});