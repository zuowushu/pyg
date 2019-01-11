app.service("contentService1", function ($http) {
    this.findContentListByCategoryId = function (categoryId) {
        return $http.get("content/findContentListByCategoryId.do?categoryId="+categoryId);
    };

});