'use strict';

angular.module('hw4App')

    .controller('StatisticController', ['AuthService' , '$scope', '$rootScope', 'AUTH_EVENTS', function (AuthService, $scope, $rootScope, AUTH_EVENTS) {

        var self = this;
        self.credentials = {
            username: '',
            password: ''
        };

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

        self.day_visits.labels = ["January", "February", "March", "April", "May", "June", "July"];
        self.day_visits.series = ['Series A', 'Series B'];
        self.day_visits.data = [
            [65, 59, 80, 81, 56, 55, 40],
            [28, 48, 40, 19, 86, 27, 90]
        ];
        self.day_visits.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
        self.day_visits.options = {
            scales: {
                yAxes: [
                    {
                        id: 'y-axis-1',
                        type: 'linear',
                        display: true,
                        position: 'left'
                    },
                    {
                        id: 'y-axis-2',
                        type: 'linear',
                        display: true,
                        position: 'right'
                    }
                ]
            }
        };

        self.browser.labels = ["Download Sales", "In-Store Sales", "Mail-Order Sales", "Tele Sales", "Corporate Sales"];
        self.browser.data = [300, 500, 100, 40, 120];

        self.pages.labels =["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"];

        self.pages.data = [
            [65, 59, 90, 81, 56, 55, 40],
            [28, 48, 40, 19, 96, 27, 100]
        ];

        self.platform.labels = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
        self.platform.data = [300, 500, 100];


        self.login = login;
        self.logout = logout;

        function login(credentials) {
            AuthService.login(credentials).then(function (data) {
                $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
                $scope.setCurrentUser(data);
            }, function () {
                $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
            });
        };

        function logout() {
            AuthService.logout();
            $scope.setCurrentUser(null);
            $rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);
        }
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




