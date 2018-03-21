'use strict';

angular.module('hw4App')
    .controller('DepartmentController', ['DepartmentService', '$scope', '$uibModal', function(DepartmentService, $scope, $uibModal) {

        var self = this;
        self.department = {};
        self.departments = DepartmentService.loadAllDepartments();
        self.sortType = 'id';
        self.sortReverse = false;
        self.search = '';

        self.getAllDepartments = getAllDepartments;
        self.editModal = editModal;
        self.deleteModal = deleteModal;
        self.addModal = addModal;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        function editModal(department) {
            $uibModal.open({
                templateUrl: 'static/views/modal/department_update_modal.html',
                windowTemplateUrl: 'static/views/modal/modal_window.html',
                controller: ['$uibModalInstance', 'department', 'DepartmentService', EditDepartmentModalController],
                controllerAs: 'ctrl',
                resolve: {
                    department: function () {
                        return department;
                    }
                }
            });
        }

        function deleteModal(department) {
            $uibModal.open({
                templateUrl: 'static/views/modal/department_delete_modal.html',
                windowTemplateUrl: 'static/views/modal/modal_window.html',
                controller: ['$uibModalInstance', 'department', 'DepartmentService', DeleteDepartmentModalController],
                controllerAs: 'ctrl',
                resolve: {
                    department: function () {
                        return department;
                    }
                }
            });
        }

        function addModal() {
            $uibModal.open({
                templateUrl: 'static/views/modal/department_create_modal.html',
                windowTemplateUrl: 'static/views/modal/modal_window.html',
                controller: ['$uibModalInstance', 'DepartmentService', AddDepartmentModalController],
                controllerAs: 'ctrl'
            });
        }

        function getAllDepartments(){
            return DepartmentService.getAllDepartments();
        }
    }]);

function EditDepartmentModalController($uibModalInstance, department, DepartmentService) {
    var self = this;
    self.department = Object.assign({}, department);
    self.updateDepartment = updateDepartment;
    self.close = close;

    function updateDepartment(department) {
        console.log('About to update Department');
        DepartmentService.updateDepartment(department)
            .then(
                function (response){
                    console.log('Department updated successfully');
                    self.successMessage='Department updated successfully';
                    self.errorMessage='';
                    self.done = true;
                },
                function(errResponse){
                    console.error('Error while updating Department');
                    self.errorMessage='Error while updating Department '+errResponse.data;
                    self.successMessage='';
                }
            );
        $uibModalInstance.close();
    }
    function close() {
        $uibModalInstance.close();
    }
}

function AddDepartmentModalController($uibModalInstance, DepartmentService) {
    var self = this;
    self.department = {
        name: '',
        city: ''
    };
    self.createDepartment = createDepartment;
    self.close = close;

    function createDepartment(department) {
        console.log('About to create Department: ' + JSON.stringify(department));
        DepartmentService.createDepartment(JSON.stringify(department))
            .then(
                function (response){
                    console.log('Department created successfully');
                    self.successMessage='Department created successfully';
                    self.errorMessage='';
                    self.done = true;
                },
                function(errResponse){
                    console.error('Error while creating Department');
                    self.errorMessage='Error while creating Department '+errResponse.data;
                    self.successMessage='';
                }
            );
        $uibModalInstance.close();
    }
    function close() {
        $uibModalInstance.close();
    }
}

function DeleteDepartmentModalController($uibModalInstance, department, DepartmentService) {
    var self = this;
    self.department = department;
    self.deleteDepartment = deleteDepartment;
    self.close = close;

    function deleteDepartment(department) {
        var id = department.id;
        console.log('About to remove Department with id '+id);
        DepartmentService.removeDepartment(id)
            .then(
                function(){
                    console.log('Department '+id + ' removed successfully');
                },
                function(errResponse){
                    console.error('Error while removing Department '+id +', Error :'+errResponse.data);
                }
            );
        $uibModalInstance.close();
    }
    function close() {
        $uibModalInstance.close();
    }
}