$(document).ready(function () {

    $("#btnGuardar").click(function () {
        $.post("medico", $("#form_medico").serialize(), function (response) {
            $('#modalInfoMed_moved').modal('hide');
            getDataListado();
        });

    });

    setTypeDataTableMaestra();
    getDataListado();
});

function limpiar() {
    $('#txtcodigo').val("0");
    $('#txtapellido').val("");
    $('#txtnombre').val("");

    document.getElementById("rdgeneroM").checked = true;
    document.getElementById("rdgeneroF").checked = false;

    $('#txtdate').val("");
    $('#txtDNI').val("");
    $('#txtphone').val("");
    $('#txtemail').val("");
    $('#cboespecialidad').val("");
}

function nuevo() {
    checkModal();
    $('#opc').val("1");
    limpiar();
    $('#modalInfoMed_moved').modal('show');
}
function editar(_id) {
    checkModal();
    $('#opc').val("2");
    limpiar();
    getData(_id);
    $('#modalInfoMed_moved').modal('show');
}

function checkModal() {
    //$('#modalInfo_esp').appendTo("body").modal('show');
    if($('#modalInfoMed_moved').length) {
        // destruyo el nuevo y me quedo con el antiguo.
        $( "#modalInfoMed" ).remove();
    }
    else {
        // lo muevo lo muevo al body y ke asigno el nuevo id.
        $('#modalInfoMed').appendTo("body");
        $("#modalInfoMed").attr("id", "modalInfoMed_moved");
    }
}

function borrar(_id) {
    delData(_id);
}

function getData(_id) {
    let opc = "3";
    $.post("medico", {opc, _id}, function (response) {
        var odata = $.parseJSON(response);
        $('#txtcodigo').val(odata.idMedico);       
        $('#txtapellido').val(odata.apellidos);
        $('#txtnombre').val(odata.nombres);
        let bgenero = (odata.genero == "M") ? true : false;

        document.getElementById("rdgeneroM").checked = bgenero;
        document.getElementById("rdgeneroF").checked = !bgenero;

        $('#txtdate').val(odata.str_fecNac);        
        
        $('#txtDNI').val(odata.DNI);
        $('#txtphone').val(odata.telefono);
        $('#txtemail').val(odata.email);
        $('#cboespecialidad').val(odata.idEspecialidad);

    });
}
function delData(_id) {
    let opc = "4";
    $.post("medico", {opc, _id}, function (response) {
        getDataListado();
    });
}


function getDataListado() {
    //JSON.parse(response
    opc = "0";
    $.post("medico", {opc}, function (response) {
        var lista = $.parseJSON(response);
        
        var resultado = "";
        tablaMaestra.destroy();
        $("#tblMaestraBody").html("");
        for (var i = 0; i < lista.length; i++) {

            resultado = "";
            resultado += "<tr>";
            resultado += "    <td>" + lista[i].idMedico + "</td>";
            resultado += "    <td>" + lista[i].apellidos + " " + lista[i].nombres + "</td>";
            resultado += "    <td>" + lista[i].genero + "</td>";
            resultado += "    <td>" + lista[i].str_fecNac + "</td>";
            resultado += "    <td>" + lista[i].DNI + "</td>";
            resultado += "    <td>" + lista[i].telefono + "</td>";
            resultado += "    <td>" + lista[i].email + "</td>";
            resultado += "    <td>" + lista[i].nomEspecialidad + "</td> ";
            resultado += "    <td><a href='#' onclick='editar(" + lista[i].idMedico + ")'><img src='botones/Edit.gif'/></a></td>";
            resultado += "    <td><a href='#' onclick='borrar(" + lista[i].idMedico + ")'><img src='botones/eliminar.png'/></a></td>";
            resultado += "    </td>";
            resultado += "</tr>";

            $("#tblMaestraBody").append(resultado);
        }
        setTypeDataTableMaestra();
    });
}

function setTypeDataTableMaestra() {
    tablaMaestra = $('#tablaMaestra').DataTable(
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