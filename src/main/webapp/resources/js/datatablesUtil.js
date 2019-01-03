var form;

function makeEditable() {
    form = $('#detailsForm');

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });
    $.ajaxSetup({cache: false});
}

function add() {
    form.find(":input").val("");
    $('#editRow').modal();
    /*  $('#datetimepicker').datetimepicker();*/
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            //$.get(ajaxUrl, updateTableByData);
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

function editRow(id) {
    $.get(ajaxUrl + id, function (data) {
        console.log("data", data);
        form.find(":input[id='id']").val(data.id);
        form.find(":input[id='dateTime']").val(data.dateTime);
        form.find(":input[id='calories']").val(data.calories);
        form.find(":input[id='description']").val(data.description);
        $('#editRow').modal();
    });
}

function updateTableByData(data) {
    var rows = datatableApi.clear().rows.add(data).draw();
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            successNoty('Saved');
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

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: 1500
    });
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = noty({
        text: 'Error status: ' + jqXHR.status,
        type: 'error',
        layout: 'bottomRight'
    });
}
