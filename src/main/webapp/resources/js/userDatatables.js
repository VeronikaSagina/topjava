var ajaxUrl = 'ajax/admin/users/';
var datatableApi;


/*$(document).ready(function() {
    // Обработчик для .ready()
    // эквивалентно записи ниже
});*/


$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            }, {
                "data": "email",
                "render": function (data, type) {
                    if (type === 'display') {
                        return '<a href="mailto:' + data + '">' + data + '</a>';
                    }
                    return data;
                }
            }, {
                "data": "roles",
                searchable: false
            }, {
                "data": "enabled",
                "render": function (data, type, row) {
                    if (type === 'display') {
                        return '<input type="checkbox" ' + (data ? 'checked' : '') + ' onclick="enableOrDisable($(this),' + row.id + ');"/>';
                    }
                    return data;
                }
            }, {
                "data": "registered",
                "render": function (date, type) {
                    if (type === 'display') {
                        return '<span>' + date.substring(0, 10) + '</span>'
                    }
                    return date;
                }
            }, {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            }, {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
        "createdRow": function (row, data) {
            if (!data.enabled) {
                $(row).addClass("disabled");
            }
        },
        "initComplete": makeEditable
    });
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
            successNoty(enabled ? 'common.enabled' : 'common.disabled');
        },
        error: function () {
            $(element).prop("checked", !enabled);
        }
    });
}
