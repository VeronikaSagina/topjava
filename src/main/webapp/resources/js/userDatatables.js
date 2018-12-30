var ajaxUrl = 'ajax/admin/users/';
var datatableApi;


/*$(document).ready(function() {
    // Обработчик для .ready()
    // эквивалентно записи ниже
});*/


$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            }, {
                "data": "email"
            }, {
                "data": "roles"
            }, {
                "data": "enabled"
            }, {
                "data": "registered"
            }, {
                "defaultContent": "Edit",
                "orderable": false
            }, {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});