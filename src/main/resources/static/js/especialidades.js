$(document).ready(function () {

    $("#btnGuardar_esp").click(function () {
        $.post("especialidad", $("#form_esp").serialize(), function (response) {
            $('#modalInfo_esp_moved').modal('hide');
            getDataListado();
        });

    });

    setTypeDataTableMaestra();
    getDataListado();
});

function limpiar() {
    $('#txtcodigo_esp').val("");
    $('#txtnombre_esp').val("");
}

function nuevo() {
    checkModal();
    $('#opc_esp').val("1");
    limpiar();
    $('#modalInfo_esp_moved').modal('show');
}

function editar(_id) {
    checkModal();
    $('#opc_esp').val("2");
    limpiar();
    getData(_id);
    $('#modalInfo_esp_moved').modal('show');
}

function checkModal() {
    //$('#modalInfo_esp').appendTo("body").modal('show');
    if($('#modalInfo_esp_moved').length) {
        // destruyo el nuevo y me quedo con el antiguo.
        $( "#modalInfo_esp" ).remove();
    }
    else {
        // lo muevo lo muevo al body y ke asigno el nuevo id.
        $('#modalInfo_esp').appendTo("body");
        $("#modalInfo_esp").attr("id", "modalInfo_esp_moved");
    }
}

function borrar(_id) {
    delData(_id);
}

function getData(_id) {
    let opc = "3";
    $.post("especialidad", {opc, _id}, function (response) {
        var odata = $.parseJSON(response);
        $('#txtcodigo_esp').val(odata.idEspecialidad);
        $('#txtnombre_esp').val(odata.nomEspecialidad);
    });
}
function delData(_id) {
    let opc = "4";
    $.post("especialidad", {opc, _id}, function (response) {
        getDataListado();
    });
}


function getDataListado() {
    //JSON.parse(response
    opc = "0";
    $.post("especialidad", {opc}, function (response) {
        var lista = $.parseJSON(response);

        var resultado = "";
        tablaMaestra.destroy();
        $("#tblMaestraBody_esp").html("");
        for (var i = 0; i < lista.length; i++) {

            resultado = "";
            resultado += "<tr>";
            resultado += "    <td>" + i + "</td>";
            resultado += "    <td>" + lista[i].idEspecialidad + "</td>";
            resultado += "    <td>" + lista[i].nomEspecialidad + "</td> ";
            resultado += "    <td><a href='#' onclick='editar(" + lista[i].idEspecialidad + ")'><img src='botones/Edit.gif'/></a></td>";
            resultado += "    <td><a href='#' onclick='borrar(" + lista[i].idEspecialidad + ")'><img src='botones/eliminar.png'/></a></td>";
            resultado += "    </td>";
            resultado += "</tr>";

            $("#tblMaestraBody_esp").append(resultado);
        }
        setTypeDataTableMaestra();
    });
}

function setTypeDataTableMaestra() {
    tablaMaestra = $('#tablaMaestra_esp').DataTable(
            {
                "language": {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    }
                },
                "pageLength": 50,
                deferRender: true,
                scrollY: 400,
                scrollCollapse: true,
                scroller: true
            });
}