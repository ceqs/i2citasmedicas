$(document).ready(function () {
    var URLactual = window.location.host;

    $("#btnBuscar").click(function () {
        //valida
        let txtI = form_historial.txtfechaI.value;
        //let txtF = form_historial.txtfechaF.value;
        let cboE = form_historial.cboEspecial.value;
        let cboM = form_historial.cboMedico.value;
        let cboH = form_historial.cboHorario.value;

        form_historial.des_

        if (txtI == null || txtI == "") {
            alert("Debe especificar fecha Inicio");
            return;
        }
        if (cboE == 0) {
            alert("Debe seleccionar una Especialidad");
            return;
        }
        if (cboM == 0) {
            alert("Debe seleccionar un Medico");
            return;
        }
        if (cboH == 0) {
            alert("Debe seleccionar un Horario");
            return;
        }

        /*
        var combo = document.getElementById("cboMedico");
        var selected = combo.options[combo.selectedIndex].text;
        form_historial.des_medico.value = selected;

        var combo = document.getElementById("cboHorario");
        var selected = combo.options[combo.selectedIndex].text;
        form_historial.des_horario.value = selected;
        */

        var reserva = {
            paciente: {
                id: $('#cboPaciente').val()
            },
            medico: {
                id: $('#cboMedico').val()
            },
            fechaCita: $("#txtfechaI").val(),
        };

        $.ajax({
          url:"/v1/reservas",
          type:"POST",
          data:JSON.stringify(reserva),
          contentType:"application/json; charset=utf-8",
          dataType:"json",
          success: function(response){
              alert("Cita registrada con ticket:" + response.id);
          }
        });
    });

    $("#cboEspecial").change(function () {
        let _id = form_historial.cboEspecial.value;
        validaCboEspecial(_id);
    });

    $("#cboMedico").change(function () {
        let _id = form_historial.cboMedico.value;
        let _txtI = form_historial.txtfechaI.value;
        //alert("OK: " + _id + '|' + _txtI);
        validaCboMedico(_id, _txtI);
    });

    validaCboEspecial("0");
    validaCboMedico("0","0");
    //setTypeDataTableMaestra();
});

function getAbsolutePath() {
    var loc = window.location;
    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
    return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}


function validaCboEspecial(_id){
    let opc = "1";
    $.post("CitaController", {opc, _id}, function (response) {
        getListaMedxEsp(response);
    });
}

function validaCboMedico(_id, _fechaI){
    let opc = "3";
    $.post("CitaController", {opc, _id, _fechaI}, function (response) {
        getListaHorxMed(response);
    });
}

//function grabarCita(){
//    let opc = "4";
//    $.post("CitaController", {opc}, function (response) {
//    });
//    console.log("grabando datos");
//}

function getListaMedxEsp(response){
    var lista = $.parseJSON(response);
    var resultado = "";
    resultado += "<option value=0 selected='selected'>(TODOS)</option>";
    for (var i = 0; i < lista.length; i++) {
        resultado += "<option  value=" + lista[i].idMedico + ">" + lista[i].apellidos + "</option>";
    }
    $("#cboMedico").html(resultado);
}

function getListaHorxMed(response){
    var lista = $.parseJSON(response);
    var resultado = "";
    resultado += "<option value=0 selected='selected'>(TODOS)</option>";
    for (var i = 0; i < lista.length; i++) {
        resultado += "<option  value=" + lista[i].idHorario + ">" + lista[i].nombre + "</option>";
    }
    $("#cboHorario").html(resultado);
}

function getDataListado(response) {
    //JSON.parse(response

    var lista = $.parseJSON(response);

    var resultado = "";
    tablaMaestra.destroy();
    $("#tblMaestraBody").html("");
    for (var i = 0; i < lista.length; i++) {

        resultado = "";
        resultado += "<tr>";
        resultado += "    <td>" + lista[i].str_fecha + "</td>";
        resultado += "    <td>" + lista[i].horario + "</td>";
        resultado += "    <td>" + lista[i].medico + "</td>";
        resultado += "    <td>" + lista[i].especialidad + "</td>";
        resultado += "    <td>" + lista[i].paciente + "</td>";
        resultado += "</tr>";

        $("#tblMaestraBody").append(resultado);
    }
    setTypeDataTableMaestra();

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