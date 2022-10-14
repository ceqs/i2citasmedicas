$(document).ready(function () {

    $("#btn_guardar_roles").click(function () {
        $.post("rol", $("#form_roles").serialize(), function (response) {
            $('#modal_roles_moved').modal('hide');
            getDataListadoRoles();
        });

    });

    setTypeDataTableMaestraRoles();
    getDataListadoRoles();
});

function limpiar() {
    $('#txtcodigo_roles').val("");
    $('#txtnombre_roles').val("");
}

function nuevo() {
    checkModal();
    $('#opc_roles').val("1");
    limpiar();
    $('#modal_roles_moved').modal('show');
}

function editar(_id) {
    checkModal();
    $('#opc_roles').val("2");
    limpiar();
    getData(_id);
    $('#modal_roles_moved').modal('show');
}

function checkModal() {
    if($('#modal_roles_moved').length) {
        // destruyo el nuevo y me quedo con el antiguo.
        $( "#modal_roles" ).remove();
    }
    else {
        // lo muevo lo muevo al body y ke asigno el nuevo id.
        $('#modal_roles').appendTo("body");
        $("#modal_roles").attr("id", "modal_roles_moved");
    }
}

function borrar(_id) {
    delData(_id);
}

function getData(_id) {
    let opc = "3";
    $.post("rol", {opc, _id}, function (response) {
        var odata = $.parseJSON(response);
        $('#txtcodigo_roles').val(odata.idRol);
        $('#txtnombre_roles').val(odata.nomRol);
    });
}
function delData(_id) {
    let opc = "4";
    $.post("rol", {opc, _id}, function (response) {
        getDataListadoRoles();
    });
}


function getDataListadoRoles() {
    //JSON.parse(response
    opc = "0";
    $.post("rol", {opc}, function (response) {
        var lista = $.parseJSON(response);
        var resultado = "";
        
        if ( $.fn.DataTable.isDataTable('#tbl_maestra_roles') ) {
             $('#tbl_maestra_roles').DataTable().destroy();
        }
        
        $("#tbl_maestra_body_roles").html("");
        for (var i = 0; i < lista.length; i++) {
            resultado = "";
            resultado += "<tr>";
            resultado += "    <td>" + i + "</td>";
            resultado += "    <td>" + lista[i].idRol + "</td>";
            resultado += "    <td>" + lista[i].nomRol + "</td> ";
            resultado += "    <td><a href='#' onclick='editar(" + lista[i].idRol + ")'><img src='botones/Edit.gif'/></a></td>";
            resultado += "    <td><a href='#' onclick='borrar(" + lista[i].idRol + ")'><img src='botones/eliminar.png'/></a></td>";
            resultado += "    </td>";
            resultado += "</tr>";

            $("#tbl_maestra_body_roles").append(resultado);
        }
        setTypeDataTableMaestraRoles();
    });
}

function setTypeDataTableMaestraRoles() {
    $('#tbl_maestra_roles').DataTable({
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