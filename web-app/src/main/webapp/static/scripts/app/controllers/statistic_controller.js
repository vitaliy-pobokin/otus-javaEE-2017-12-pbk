'use strict';

angular.module('hw4App')

    .controller('StatisticController', ['StatService', '$scope', function (StatService, $scope) {

        var self = this;
        StatService.getStatCollectionStatus().then(function (statCollectionStatus) {
            $scope.statCollectionStatus = statCollectionStatus;
        });
        $scope.alterStatCollection = alterStatCollection;

        self.day_visits = {
            labels: [],
            series: [],
            data: [],
            datasetOverride: []
        };

        self.browser = {
            labels: [],
            data: [],
            options: []
        };

        self.pages = {
            labels: [],
            data: [],
            options: []
        };

        self.platform = {
            labels: [],
            data: [],
            options: []
        };

        self.day_visits.datasetOverride = [{ yAxisID: 'y-axis-1' }];
        self.day_visits.options = {
            scales: {
                yAxes: [
                    {
                        id: 'y-axis-1',
                        type: 'linear',
                        display: true,
                        position: 'left'
                    }
                ]
            }
        };

        function alterStatCollection() {
            StatService.alterStatCollection();
            self.statCollectionStatus = !self.statCollectionStatus;
        }

        function getBrowserUsage() {
            StatService.getBrowserUsage().then(function (data) {
                var markers = data;
                for (var i = 0; i < markers.length; i++) {
                    self.browser.labels.push(markers[i].browser);
                    self.browser.data.push(markers[i].count);
                }
            })
        }

        function getPlatformUsage() {
            StatService.getPlatformUsage().then(function (data) {
                var markers = data;
                for (var i = 0; i < markers.length; i++) {
                    self.platform.labels.push(markers[i].platform);
                    self.platform.data.push(markers[i].count);
                }
            })
        }

        function getPageViews() {
            StatService.getPageViews().then(function (data) {
                var markers = data;
                for (var i = 0; i < markers.length; i++) {
                    self.pages.labels.push(markers[i].page);
                    self.pages.data.push(markers[i].count);
                }
            })
        }

        function getVisitsPerDay() {
            StatService.getVisitsPerDay().then(function (data) {
                var markers = data;
                for (var i = 0; i < markers.length; i++) {
                    self.day_visits.labels.push(markers[i].dayOfWeek);
                    self.day_visits.data.push(markers[i].count);
                }
            })
        }

        getBrowserUsage();
        getPlatformUsage();
        getPageViews();
        getVisitsPerDay();
    }])

    .factory('AuthInterceptor', ['$rootScope', '$q', 'AUTH_EVENTS', function ($rootScope, $q, AUTH_EVENTS) {
        return {
            responseError: function (response) {
                console.log('Error: ' + response.status);
                $rootScope.$broadcast({
                    401: AUTH_EVENTS.notAuthenticated,
                    403: AUTH_EVENTS.notAuthorized,
                    419: AUTH_EVENTS.sessionTimeout,
                    440: AUTH_EVENTS.sessionTimeout
                }[response.status], response);
                return $q.reject(response);
            }
        };
    }]);




