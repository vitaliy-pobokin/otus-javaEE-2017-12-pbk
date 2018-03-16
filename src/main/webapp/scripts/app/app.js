'use strict';

var app = angular.module('hw4App', ['ui.router', 'ui.router.state.events', 'ngResource', 'ngStorage', 'ui.bootstrap']);

app.constant('urls', {
    BASE: 'http://localhost:3000' + contextPath,
    EMPLOYEE_SERVICE_API: 'http://localhost:3000' + contextPath + '/api/employee/',
    DEPARTMENT_SERVICE_API: 'http://localhost:3000' + contextPath + '/api/department/',
    ACCOUNT_SERVICE_API: 'http://localhost:3000' + contextPath + '/api/account/',
    LOGIN_SERVICE: 'http://localhost:3000' + contextPath + '/api/login/',
    NEWS_SERVICE_API: 'http://localhost:3000' + contextPath + '/api/news/',
    CURRENCY_SERVICE_API: 'http://localhost:3000' + contextPath + '/api/currency/'});

app.constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    sessionTimeout: 'auth-session-timeout',
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized'});

app.constant('USER_ROLES', {
    ceo: 'CEO',
    acm: 'ACM',
    hrm: 'HRM',
    usr: 'USR'});

app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', 'USER_ROLES',
    function($stateProvider, $urlRouterProvider, $locationProvider, USER_ROLES) {
        $stateProvider

            // route for the home page
            .state('app', {
                url:'/',
                views: {
                    'header': {
                        templateUrl : 'views/header.html',
                    },
                    'nav': {
                        templateUrl : 'views/nav.html',
                    },
                    'content': {
                        templateUrl : 'views/home.html',
                        controller  : 'SideBarController',
                        controllerAs: 'ctrl',
                    },
                    'footer': {
                        templateUrl : 'views/footer.html',
                    }
                },
                data: {
                    pageType: 'public'
                }
            })

            .state('app.login', {
                url:'login',
                views: {
                    'content@': {
                        templateUrl : 'views/login.html',
                    }
                },
                data: {
                    pageType: 'public'
                }
            })

            .state('app.news', {
                url:'news',
                views: {
                    'content@': {
                        templateUrl : 'views/news.html',
                        controller  : 'SideBarController',
                        controllerAs: 'ctrl',
                    }
                },
                data: {
                    pageType: 'public'
                }
            })

            .state('app.our_employees', {
                url:'our_employees',
                views: {
                    'content@': {
                        templateUrl : 'views/our_employees.html',
                        controller  : 'SideBarController',
                        controllerAs: 'ctrl',
                    }
                },
                data: {
                    pageType: 'public'
                }
            })

            .state('app.about', {
                url:'about',
                views: {
                    'content@': {
                        templateUrl : 'views/about.html',
                        controller  : 'SideBarController',
                        controllerAs: 'ctrl',
                    }
                },
                data: {
                    pageType: 'public'
                }
            })

            .state('app.scripts', {
                url:'scripts',
                views: {
                    'content@': {
                        templateUrl : 'views/scripts.html',
                    }
                },
                data: {
                    pageType: 'private',
                    authorizedRoles: [USER_ROLES.ceo]
                }
            })

            .state('app.employees', {
                url: 'employees',
                views: {
                    'content@': {
                        templateUrl : 'views/employees.html',
                        controller  : 'EmployeeController',
                        controllerAs: 'ctrl',
                    }
                },
                data: {
                    pageType: 'private',
                    authorizedRoles: [USER_ROLES.ceo, USER_ROLES.acm, USER_ROLES.hrm, USER_ROLES.usr]
                }
            })

            .state('app.departments', {
                url: 'departments',
                views: {
                    'content@': {
                        templateUrl : 'views/departments.html',
                        controller  : 'DepartmentController',
                        controllerAs: 'ctrl',
                    }
                },
                data: {
                    pageType: 'private',
                    authorizedRoles: [USER_ROLES.ceo, USER_ROLES.acm, USER_ROLES.hrm, USER_ROLES.usr]
                }
            });

            $locationProvider.html5Mode({
                enabled: true,
                requireBase: true
            });
        $urlRouterProvider.otherwise('/');
    }]);

app.config(function ($httpProvider) {
    $httpProvider.interceptors.push([
        '$injector',
        function ($injector) {
            return $injector.get('AuthInterceptor');
        }
    ]);
});

app.run(['$rootScope', '$localStorage', 'AUTH_EVENTS', 'AuthService', function ($rootScope, $localStorage, AUTH_EVENTS, AuthService) {
    $rootScope.$on('$stateChangeStart', function (event, next) {
        if (next.data.pageType !== 'public') {
            var authorizedRoles = next.data.authorizedRoles;
            if (!AuthService.isAuthorized(authorizedRoles)) {
                event.preventDefault();
                if (AuthService.isAuthenticated()) {
                    // user is not allowed
                    console.log('not authorized');
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
                } else {
                    // user is not logged in
                    console.log('not autenticated');
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
                }
            }
        }
    });
    function init() {
        console.log('init');
        $rootScope.currentUser = null;
        var session = $localStorage.session;
        if (session) {
            AuthService.loadUserSession();
            $rootScope.currentUser = session.user;
        }
    }
    init();
}]);
