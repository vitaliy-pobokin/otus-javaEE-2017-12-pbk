'use strict';

var app = angular.module('hw4App', ['ui.router', 'ui.router.state.events', 'ngResource', 'ngStorage', 'ui.bootstrap', 'chart.js']);

app.constant('urls', {
    BASE: 'http://localhost:3000' + contextPath,
    EMPLOYEE_SERVICE_API: 'http://localhost:3000' + contextPath + '/api/employee/',
    DEPARTMENT_SERVICE_API: 'http://localhost:3000' + contextPath + '/api/department/',
    ACCOUNT_SERVICE_API: 'http://localhost:3000' + contextPath + '/api/account/',
    LOGIN_SERVICE: 'http://localhost:3000' + contextPath + '/api/login/',
    NEWS_SERVICE_API: 'http://localhost:3000' + contextPath + '/api/news/',
    CURRENCY_SERVICE_API: 'http://localhost:3000' + contextPath + '/api/currency/',
    STATISTIC_BROWSER_USAGE_API: 'http://localhost:3000' + contextPath + '/api/statistic/browser_usage/',
    STATISTIC_PLATFORM_USAGE_API: 'http://localhost:3000' + contextPath + '/api/statistic/platform_usage/',
    STATISTIC_PAGE_VIEWS_API: 'http://localhost:3000' + contextPath + '/api/statistic/page_views/',
    STATISTIC_VISITS_PER_DAY_API: 'http://localhost:3000' + contextPath + '/api/statistic/visits_per_day/',
    STAT_SERVICE_API: 'http://localhost:3000' + contextPath + '/api/statistic/stat/',
    STAT_COLLECTION_STATUS: 'http://localhost:3000' + contextPath + '/api/statistic/stat_collection_status/',
    ALTER_STAT_COLLECTION: 'http://localhost:3000' + contextPath + '/api/statistic/alter_stat_collection/'});

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
                    'content': {
                        templateUrl : 'static/views/home.html',
                        controller  : 'SideBarController',
                        controllerAs: 'ctrl',
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
                        templateUrl : 'static/views/login.html',
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
                        templateUrl : 'static/views/news.html',
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
                        templateUrl : 'static/views/our_employees.html',
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
                        templateUrl : 'static/views/about.html',
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
                        templateUrl : 'static/views/scripts.html',
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
                        templateUrl : 'static/views/employees.html',
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
                        templateUrl : 'static/views/departments.html',
                        controller  : 'DepartmentController',
                        controllerAs: 'ctrl',
                    }
                },
                data: {
                    pageType: 'private',
                    authorizedRoles: [USER_ROLES.ceo, USER_ROLES.acm, USER_ROLES.hrm, USER_ROLES.usr]
                }
            })

            .state('app.statistic', {
                url: 'statistic',
                views: {
                    'content@': {
                        templateUrl : 'static/views/statistic.html',
                        controller  : 'StatisticController',
                        controllerAs: 'ctrl',
                    }
                },
                data: {
                    pageType: 'private',
                    authorizedRoles: [USER_ROLES.ceo]
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

app.run(['$rootScope', '$localStorage', '$window',  'AUTH_EVENTS', 'AuthService', 'StatService', function ($rootScope, $localStorage, $window, AUTH_EVENTS, AuthService, StatService) {
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
    $rootScope.$on('$stateChangeSuccess', function (event, next) {
        var stats = {
            user: '',
            language: '',
            path: '',
            date: ''
        };
        stats.user = $rootScope.currentUser.username;
        stats.language = $window.navigator.language;
        stats.path = next.url;
        stats.date = new Date().toISOString();
        StatService.sendStats(stats);
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
