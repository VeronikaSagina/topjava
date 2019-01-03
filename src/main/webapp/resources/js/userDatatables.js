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

function enableOrDisable(element, id) {
    // var userId = element.closest("tr[id]").attr("id");
    var enabled = element.is(":checked");
    //element.parent().parent().css("text-decoration", enabled ? "none" : "line-through");
    $.ajax({
        url: ajaxUrl + id,
        type: 'POST',
        data: 'enabled=' + enabled,
        success: function () {
            element.closest('tr').toggleClass('disabled');
            successNoty(enabled ? 'enabled' : 'Disabled');
        },
        error:function () {
            $(element).prop("checked", !enabled);
        }
    });
}
