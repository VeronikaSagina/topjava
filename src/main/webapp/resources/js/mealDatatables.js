var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            }, {
                "data": "description"
            }, {
                "data": "calories"
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

