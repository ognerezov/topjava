var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;
var exceed;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

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
                "render": function (date, type, row) {
                    if (type == 'display') {
                        if(row.exceed) return '<span class="exceeded">'+date.substring(0, 10)+ '</span>';
                        return '<span class="normal">'+date.substring(0, 10)+ '</span>';
                    }
                    return date;
                }
            },
            {
                "data": "description",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        if(row.exceed) return '<span class="exceeded">'+data+ '</span>';
                        return '<span class="normal">'+data+ '</span>';
                    }
                    return data;
                }
            },
            {
                "data": "calories",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        if(row.exceed) return '<span class="exceeded">'+data+ '</span>';
                        return '<span class="normal">'+data+ '</span>';
                    }
                    return data;
                }
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
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
        "createdRow": function (row, data, dataIndex) {
            if (!data.exceed) {
               // $(row).css("opacity", 0.3);
            }
        },
        "initComplete": makeEditable
    });
});