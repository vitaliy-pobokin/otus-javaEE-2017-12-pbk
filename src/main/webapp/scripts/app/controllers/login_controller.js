'use strict';

angular.module('hw4App')

    .controller('LoginController', ['AuthService', '$scope', '$rootScope', 'AUTH_EVENTS', function (AuthService, $scope, $rootScope, AUTH_EVENTS) {

        var self = this;
        self.credentials = {
            username: '',
            password: ''
        };

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




