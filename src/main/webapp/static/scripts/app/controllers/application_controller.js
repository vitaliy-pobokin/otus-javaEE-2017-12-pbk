'use strict';

angular.module('hw4App')

    .controller('ApplicationController', ['AuthService', '$rootScope', '$scope', '$uibModal', 'USER_ROLES', 'AUTH_EVENTS', function (AuthService, $rootScope, $scope, $uibModal, USER_ROLES, AUTH_EVENTS) {
        $scope.userRoles = USER_ROLES;
        $scope.isAuthorized = AuthService.isAuthorized;

        $rootScope.setCurrentUser = function (user) {
            console.log(user);
            $rootScope.currentUser = user;
        };

        $rootScope.$on(AUTH_EVENTS.loginSuccess, function () {
            console.log('login success');
        });

        $rootScope.$on(AUTH_EVENTS.notAuthorized, function () {
            warningModal('You are not authorized to perform this action.');
        });

        $rootScope.$on(AUTH_EVENTS.notAuthenticated, function () {
            loginModal();
        });

        function warningModal(message) {
            $uibModal.open({
                templateUrl: 'static/views/modal/warning_modal.html',
                windowTemplateUrl: 'static/views/modal/modal_window.html',
                controller: ['$uibModalInstance', 'message', WarningModalController],
                controllerAs: 'ctrl',
                resolve: {
                    message: function () {
                        return message;
                    }
                }
            });
        }

        function loginModal() {
            $uibModal.open({
                templateUrl: 'static/views/modal/login_modal.html',
                windowTemplateUrl: 'static/views/modal/modal_window.html',
                controller: ['$uibModalInstance', '$rootScope', 'AuthService', 'AUTH_EVENTS', LoginModalController],
                controllerAs: 'ctrl'
            });
        }
    }]);

function WarningModalController($uibModalInstance, message) {
    var self = this;
    self.message = message;
    self.close = close;

    function close() {
        $uibModalInstance.close();
    }
}

function LoginModalController($uibModalInstance, $rootScope, AuthService, AUTH_EVENTS) {
    var self = this;
    self.credentials = {
        username: '',
        password: ''
    };
    self.login = login;
    self.close = close;

    function login(credentials) {
        AuthService.login(credentials).then(function (data) {
            $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
            $rootScope.setCurrentUser(data);
            $uibModalInstance.close();
        }, function () {
            $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        });
    };

    function close() {
        $uibModalInstance.close();
    }
}