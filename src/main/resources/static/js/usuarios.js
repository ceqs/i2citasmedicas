$(document).ready(function () {

    $("#btn_guardar_usuario").click(function () {
        $.post("usuario", $("#form_usuario").serialize(), function (response) {
            $('#modal_usuario_moved').modal('hide');
            getDataListadoUsuarios();
        });

    });

    setTypeDataTableMaestraUsuarios();
    getDataListadoUsuarios();
});

function limpiar() {
    $('#txtusuario_usuario').val("");
    $('#txtpassword_usuario').val("");
    $('#cborol').val("");
    $('rdenabledsi').checked = true;
    $('rdenabledno').checked = false;
}

function nuevo() {
    checkModal();
    $('#opc_usuario').val("1");
    limpiar();
    $('#modal_usuario_moved').modal('show');
}

function editar(_id) {
    checkModal();
    $('#opc_usuario').val("2");
    limpiar();
    getData(_id);
    $('#modal_usuario_moved').modal('show');
}

function checkModal() {
    if($('#modal_usuario_moved').length) {
        // destruyo el nuevo y me quedo con el antiguo.
        $( "#modal_usuario" ).remove();
    }
    else {
        // lo muevo lo muevo al body y ke asigno el nuevo id.
        $('#modal_usuario').appendTo("body");
        $("#modal_usuario").attr("id", "modal_usuario_moved");
    }
}

function borrar(_usuario) {
    delData(_usuario);
}

function getData(_usuario) {
    let opc = "3";
    $.post("usuario", {opc, _usuario}, function (response) {
        var odata = $.parseJSON(response);
        $('#txtusuario_usuario').val(odata.usuario);
        $('#txtpassword_usuario').val(odata.password);
        $('#cborol').val(odata.idRol);
        
        let benabled = (odata.enabled ? true : false);

        $('rdenabledsi').checked = benabled;
        $('rdenabledno').checked = !benabled;
    });
}

function delData(_usuario) {
    let opc = "4";
    $.post("usuario", {opc, _usuario}, function (response) {
        getDataListadoUsuarios();
    });
}


function getDataListadoUsuarios() {
    //JSON.parse(response
    opc = "0";
    $.post("usuario", {opc}, function (response) {
        var lista = $.parseJSON(response);
        var resultado = "";
        
        if ( $.fn.DataTable.isDataTable('#tbl_maestra_usuario') ) {
             $('#tbl_maestra_usuario').DataTable().destroy();
        }
        
        $("#tbl_maestra_body_usuario").html("");
        for (var i = 0; i < lista.length; i++) {
            resultado = "";
            resultado += "<tr>";
            resultado += "    <td>" + i + "</td>";
            resultado += "    <td>" + lista[i].usuario + "</td>";
            resultado += "    <td>" + lista[i].password.replace(/./g, '*'); + "</td> ";
            resultado += "    <td>" + lista[i].idRol + "</td> ";
            resultado += "    <td>" + lista[i].enabled + "</td> ";
            resultado += "    <td><a href='#' onclick='editar(" + lista[i].usuario + ")'><img src='botones/Edit.gif'/></a></td>";
            resultado += "    <td><a href='#' onclick='borrar(" + lista[i].usuario + ")'><img src='botones/eliminar.png'/></a></td>";
            resultado += "    </td>";
            resultado += "</tr>";

            $("#tbl_maestra_body_usuario").append(resultado);
        }
        setTypeDataTableMaestraUsuarios();
    });
}

function setTypeDataTableMaestraUsuarios() {
    $('#tbl_maestra_usuario').DataTable({
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