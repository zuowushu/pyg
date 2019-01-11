app.service("searchService", function ($http) {


    this.search = function (searchMap) {
        return $http.post("itemSearch/search.do", searchMap);

    };
    this.findSellerList = function (sellerId) {
        return $http.get("itemSearch/findItemList.do?sellerId="+sellerId)
    };



});