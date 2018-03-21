'use strict';

angular.module('hw4App')



    .controller('SideBarController', ['NewsService', 'CurrencyService', '$scope', '$window', function(NewsService, CurrencyService, $scope, $window) {

        var self = this;
        self.news = NewsService.loadAllNews();
        self.currencies = CurrencyService.loadAllCurrencies();
        self.selectedCurrencies = ['USD','EUR','GBP'];

        self.filterByCurrency = filterByCurrency;

        self.getAllNews = getAllNews;
        self.getAllCurrencies = getAllCurrencies;
        self.openLink = openLink;

        function getAllNews(){
            return NewsService.getAllNews();
        }

        function getAllCurrencies(){
            return CurrencyService.getAllCurrencies();
        }

        function filterByCurrency(currency) {
            return (self.selectedCurrencies.indexOf(currency.charCode) !== -1);
        }

        function openLink(link) {
            $window.open(link, "_blank");
        }
    }
    ]);
