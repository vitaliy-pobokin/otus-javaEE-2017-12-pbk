'use strict';
 
angular.module('hw4App')
    .factory('EmployeeService', ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {
 
            var factory = {
                loadAllEmployees: loadAllEmployees,
                getAllEmployees: getAllEmployees,
                getEmployee: getEmployee,
                createEmployee: createEmployee,
                updateEmployee: updateEmployee,
                removeEmployee: removeEmployee
            };
 
            return factory;
 
            function loadAllEmployees() {
                console.log('Fetching all Employees');
                var deferred = $q.defer();
                $http.get(urls.EMPLOYEE_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all Employees');
                            console.log(response.data);//-------------------------
                            $localStorage.employees = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading Employees');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
 
            function getAllEmployees(){
                return $localStorage.employees;
            }
 
            function getEmployee(id) {
                console.log('Fetching Employee with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.EMPLOYEE_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Employee with id :'+id);
                            console.log(response.data);//-------------------------
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Employee with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
 
            function createEmployee(employee) {
                console.log('Creating Employee');
                var deferred = $q.defer();
                $http.post(urls.EMPLOYEE_SERVICE_API, employee)
                    .then(
                        function (response) {
                            loadAllEmployees();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating Employee : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
 
            function updateEmployee(employee) {
                console.log('Updating Employee with id ' + employee.id);
                var deferred = $q.defer();
                $http.put(urls.EMPLOYEE_SERVICE_API, employee)
                    .then(
                        function (response) {
                            loadAllEmployees();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Employee with id :' + employee.id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
 
            function removeEmployee(id) {
                console.log('Removing Employee with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.EMPLOYEE_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllEmployees();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Employee with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
 
        }
    ])

    .factory('DepartmentService', ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllDepartments: loadAllDepartments,
                getAllDepartments: getAllDepartments,
                getDepartment: getDepartment,
                createDepartment: createDepartment,
                updateDepartment: updateDepartment,
                removeDepartment: removeDepartment
            };

            return factory;

            function loadAllDepartments() {
                console.log('Fetching all Departments');
                var deferred = $q.defer();
                $http.get(urls.DEPARTMENT_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all Departments');
                            console.log(response.data);//-------------------------
                            $localStorage.departments = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading Departments');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllDepartments(){
                return $localStorage.departments;
            }

            function getDepartment(id) {
                console.log('Fetching Department with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.DEPARTMENT_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Department with id :'+id);
                            console.log(response.data);//-------------------------
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Department with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createDepartment(department) {
                console.log('Creating Department');
                var deferred = $q.defer();
                $http.post(urls.DEPARTMENT_SERVICE_API, department)
                    .then(
                        function (response) {
                            loadAllDepartments();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating Department : '+errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateDepartment(department) {
                console.log('Updating Department with id ' + department.id);
                var deferred = $q.defer();
                $http.put(urls.DEPARTMENT_SERVICE_API, department)
                    .then(
                        function (response) {
                            loadAllDepartments();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Department with id :' + department.id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeDepartment(id) {
                console.log('Removing Department with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.DEPARTMENT_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllDepartments();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Department with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ])

    .factory('AuthService', ['$localStorage', '$http', '$q', 'Session', 'urls', function ($localStorage, $http, $q, Session, urls) {
        var authService = {
            login: login,
            logout: logout,
            loadUserSession: loadUserSession
        };
        var AUTHORIZATION_PROPERTY = 'Authorization';

        function login(credentials) {
            var deferred = $q.defer();
            $http
                .post(urls.LOGIN_SERVICE, credentials)
                .then(function (res) {
                    var authHeader = res.headers('Authorization');
                    console.log(authHeader);
                    var session = Session.create(authHeader, res.data);
                    console.log(res);
                    storeUserSession(session);
                    deferred.resolve(res.data);
                });
            return deferred.promise;
        }

        function logout() {
            $http.defaults.headers.common[AUTHORIZATION_PROPERTY] = undefined;
            $localStorage.session = undefined;
            Session.destroy();
        }

        function loadUserSession() {
            var session = $localStorage.session;
            if (session) {
                useSession(session);
            }
        }

        function storeUserSession(Session) {
            $localStorage.session = Session;
            useSession(Session);
        }

        function useSession(session) {
            // Set the token as header for your requests!
            Session.create(session.authHeader, session.user);
            $http.defaults.headers.common[AUTHORIZATION_PROPERTY] = Session.authHeader;
        }

        authService.isAuthenticated = function () {
            if (Session) {
                return !!Session.user;
            }
            return false;
        };

        authService.isAuthorized = function (authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                authorizedRoles = [authorizedRoles];
            }
            return (authService.isAuthenticated() &&
                authorizedRoles.indexOf(Session.user.role) !== -1);
        };

        //loadUserSession();

        return authService;
    }])

    .service('Session', function () {
        this.create = function (authHeader, user) {
            this.authHeader = authHeader;
            this.user = user;
            return this;
        };
        this.destroy = function () {
            this.authHeader = null;
            this.user = null;
        };
    })

    .factory('NewsService', ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllNews: loadAllNews,
                getAllNews: getAllNews
            };

            return factory;

            function loadAllNews() {
                console.log('Fetching all News');
                var deferred = $q.defer();
                $http.get(urls.NEWS_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all News');
                            console.log(response.data.news);
                            $localStorage.news = response.data.news;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading News');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllNews(){
                return $localStorage.news;
            }
        }
    ])

    .factory('CurrencyService', ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllCurrencies: loadAllCurrencies,
                getAllCurrencies: getAllCurrencies
            };

            return factory;

            function loadAllCurrencies() {
                console.log('Fetching all Currencies');
                var deferred = $q.defer();
                $http.get(urls.CURRENCY_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all Currencies');
                            console.log(response.data.currencies);
                            $localStorage.currencies = response.data.currencies;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading Currencies');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllCurrencies(){
                return $localStorage.currencies;
            }
        }
    ]);