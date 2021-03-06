var form;

function makeEditable() {
    form = $('#detailsForm');
    $(document).ajaxError(function (event, jqXHR) {
        failNoty(jqXHR);
    });
    $.ajaxSetup({cache: false});
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });
}

function extendsOpts(opts) {
    $.extend(true, opts,
        {
            "ajax": {
                "url": ajaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "language": {
                "search": i18n["common.search"]
            },
            "initComplete": makeEditable
        }
    );
    return opts;
}

function add() {
    $("#headerAdd").removeAttr("hidden");
    $("#headerEdit").attr("hidden", true);
    form.find(":input").val("");
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('common.deleted');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

function editRow(id) {
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            form.find(":input[name='" + key + "']").val(value);
        });
        $("#headerEdit").removeAttr("hidden");
        $("#headerAdd").attr("hidden", true);
        $('#editRow').modal();
    });
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function save() {
    // var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            successNoty('common.saved');
            sleep(0).then(updateTable);
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    noty({
        text: i18n[key],
        type: 'success',
        layout: 'bottomRight',
        timeout: 1500
    });
}

function failNoty(jqXHR) {
    closeNoty();
    let errorInfo = $.parseJSON(jqXHR.responseText);
    failedNote = noty({
        text: i18n['common.errorStatus'] + ': ' + jqXHR.status + '<br>' + errorInfo.cause + '<br>' + errorInfo.details.join("<br>"),
        type: 'error',
        layout: 'bottomRight'
    });
}

function renderEditBtn(data, type, row) {
    if (type === 'display') {
        return '<span onclick="editRow(' + row.id + ');">' +
            '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>'
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === 'display') {
        return '<span onclick="deleteRow(' + row.id + ');">' +
            '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>'
    }
}
