'use strict';

angular.module('hw4App')



    .controller('EmployeeController', ['EmployeeService', 'DepartmentService', '$scope', '$uibModal', function(EmployeeService, DepartmentService, $scope, $uibModal) {

        var self = this;
        self.employee = {};
        self.employees = EmployeeService.loadAllEmployees();
        self.sortType = 'id';
        self.sortReverse = false;
        self.search = '';

        self.getAllEmployees = getAllEmployees;
        self.editModal = editModal;
        self.deleteModal = deleteModal;
        self.addModal = addModal;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        function editModal(employee) {
            $uibModal.open({
                templateUrl: 'views/modal/employee_update_modal.html',
                windowTemplateUrl: 'views/modal/modal_window.html',
                controller: ['$uibModalInstance', 'employee', 'departments', 'EmployeeService', EditEmployeeModalController],
                controllerAs: 'ctrl',
                resolve: {
                    employee: function () {
                        return employee;
                    },
                    departments: function (DepartmentService) {
                        return DepartmentService.loadAllDepartments()
                            .then(function (response) {
                                return response.data;
                            });
                    }
                }
            });
        }

        function deleteModal(employee) {
            $uibModal.open({
                templateUrl: 'views/modal/employee_delete_modal.html',
                windowTemplateUrl: 'views/modal/modal_window.html',
                controller: ['$uibModalInstance', 'employee', 'EmployeeService', DeleteEmployeeModalController],
                controllerAs: 'ctrl',
                resolve: {
                    employee: function () {
                        return employee;
                    }
                }
            });
        }

        function addModal() {
            $uibModal.open({
                templateUrl: 'views/modal/employee_create_modal.html',
                windowTemplateUrl: 'views/modal/modal_window.html',
                controller: ['$uibModalInstance', 'departments', 'EmployeeService', AddEmployeeModalController],
                controllerAs: 'ctrl',
                resolve: {
                    departments: function (DepartmentService) {
                        return DepartmentService.loadAllDepartments()
                            .then(function (response) {
                                return response.data;
                            });
                    }
                }
            });
        }

        function getAllEmployees(){
            return EmployeeService.getAllEmployees();
        }
    }
    ]);

function EditEmployeeModalController($uibModalInstance, employee, departments, EmployeeService) {
    var self = this;
    self.employee = Object.assign({}, employee); //employee;
    self.departments = departments;
    self.updateEmployee = updateEmployee;
    self.close = close;

    function updateEmployee(employee) {
        console.log('About to update Employee');
        EmployeeService.updateEmployee(employee)
            .then(
                function (response){
                    console.log('Employee updated successfully');
                    self.successMessage='Employee updated successfully';
                    self.errorMessage='';
                    self.done = true;
                },
                function(errResponse){
                    console.error('Error while updating Employee');
                    self.errorMessage='Error while updating Employee '+errResponse.data;
                    self.successMessage='';
                }
            );
        $uibModalInstance.close();
    }
    function close() {
        $uibModalInstance.close();
    }
}

function AddEmployeeModalController($uibModalInstance, departments, EmployeeService) {
    var self = this;
    self.employee = {
        name: '',
        email: '',
        phone: '',
        hireDate: '',
        salary: 0,
        job: '',
        account: null,
        department: null
    };
    self.departments = departments;
    self.createEmployee = createEmployee;
    self.close = close;

    function createEmployee(employee) {
        console.log('About to create Employee: ' + JSON.stringify(employee));
        EmployeeService.createEmployee(JSON.stringify(employee))
            .then(
                function (response){
                    console.log('Employee created successfully');
                    self.successMessage='Employee created successfully';
                    self.errorMessage='';
                    self.done = true;
                },
                function(errResponse){
                    console.error('Error while creating Employee');
                    self.errorMessage='Error while creating Employee '+errResponse.data;
                    self.successMessage='';
                }
            );
        $uibModalInstance.close();
    }
    function close() {
        $uibModalInstance.close();
    }
}

function DeleteEmployeeModalController($uibModalInstance, employee, EmployeeService) {
    var self = this;
    self.employee = employee;
    self.deleteEmployee = deleteEmployee;
    self.close = close;

    function deleteEmployee(employee) {
        var id = employee.id;
        console.log('About to remove Employee with id '+id);
        EmployeeService.removeEmployee(id)
            .then(
                function(){
                    console.log('Employee '+id + ' removed successfully');
                },
                function(errResponse){
                    console.error('Error while removing Employee '+id +', Error :'+errResponse.data);
                }
            );
        $uibModalInstance.close();
    }
    function close() {
        $uibModalInstance.close();
    }
}
