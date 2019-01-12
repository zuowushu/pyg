//定义业务对象
app.service("orderService", function ($http) {

    //查询所有 this 表示当前对象orderService
    this.findAll = function () {
        return $http.get("../order/findAll.do");
    };

    //根据分页信息查询
    this.findPage = function (page, rows) {
        return $http.get("../order/findPage.do?page=" + page + "&rows=" + rows);

    };

    //新增
    this.add = function (entity) {
        return $http.post("../order/add.do", entity);

    };

    //更新
    this.update = function (entity) {
        return $http.post("../order/update.do", entity);

    };

    //根据主键查询
    this.findOne = function (id) {
        return $http.get("../order/findOne.do?id=" + id);
    };


    //删除
    this.delete = function (selectedIds) {
        return $http.get("../order/delete.do?ids=" + selectedIds);

    };


    //查询方法
    this.search = function (page, rows, searchEntity) {
        return $http.post("../order/search.do?page=" + page + "&rows="+ rows, searchEntity);
    };

    //查询品牌数据
    this.selectOptionList = function () {
        return $http.get("../order/selectOptionList.do");

    };

});
