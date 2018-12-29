var ajaxUrl = 'ajax/admin/users/';
var datatableApi;

$(function () {
    datatableApi = $('#datatable').DataTable({
        "pagihg": false,
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