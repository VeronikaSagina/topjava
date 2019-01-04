var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

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
                "data": "dateTime",
                "render": function (data, type, row) {
                    if (type === 'display') {
                        return moment(data).format('MM-DD-YYYY hh:mm');
                    }
                    return data;
                }
            }, {
                "data": "description"
            }, {
                "data": "calories"
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
        "initComplete": makeEditable
    });
});

/*function filter() {
    var form = $('#filter');
    var params = "?" + form.serialize();
  /!*  console.log(params);*!/
    $.get(ajaxUrl + "filter" + params, function (data) {
        datatableApi.clear();
        $.each(data, function (key, item) {
            datatableApi.row.add(item);
        });
        datatableApi.draw();
    });
}*/
function filter() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: updateTableByData
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, function (data) {
        datatableApi.clear();
        $.each(data, function (key, item) {
            datatableApi.row.add(item);
        });
        datatableApi.draw();
    });
}

