$(document).ready(function () {
    var initialLocaleCode = 'es';

    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
          initialDate: '2022-11-01',
          initialView: 'timeGridWeek',
          locale: initialLocaleCode,
          nowIndicator: true,
          headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'timeGridWeek,timeGridDay'
          },
          eventClick: function(arg) {
            if(arg.event.title != "RESERVADO") {
                if (confirm('Desea realizar la cita?')) {
                    var reserva = {
                        paciente: {
                            id: $('#cboPaciente').val()
                        },
                        medico: {
                            id: $('#cboMedico').val()
                        },
                        fechaCita: $("#txtfechaI").val(),
                        fhInicio: arg.event.extendedProps.startStr,
                        fhFin: arg.event.extendedProps.endStr
                    };
                    $.ajax({
                      url:"/v1/reservas",
                      type:"POST",
                      data:JSON.stringify(reserva),
                      contentType:"application/json; charset=utf-8",
                      dataType:"json",
                      success: function(response){
                          arg.event.title = "RESERVADO";
                          arg.event.backgroundColor = "#cd5c5c";
                          alert("Cita registrada con ticket:" + response.id);
                      }
                    });
                }
            }
          },
          navLinks: true, // can click day/week names to navigate views
          editable: true,
          selectable: true,
          selectMirror: true,
          dayMaxEvents: true, // allow "more" link when too many events
          events: []
        });

        calendar.render();
        calendar.today();

    $("#btnBuscar").click(function () {
        let txtI = form_historial.txtfechaI.value;
        let txtF = form_historial.txtfechaF.value;

        if (txtI == null || txtI == "") {
            alert("Debe especificar fecha Inicio");
            return;
        }

        if (txtF == null || txtF == "") {
            alert("Debe especificar fecha Fin");
            return;
        }

        var eventSources = calendar.getEventSources();
        var len = eventSources.length;
        for (var i = 0; i < len; i++) {
            eventSources[i].remove();
        }

        $.getJSON('/v1/horarios/search?medico='+$('#cboMedico').val()+'&fechaInicio='+$("#txtfechaI").val()+'&fechaFin='+$("#txtfechaF").val(), function (response) {
            calendar.addEventSource(response);
            calendar.gotoDate($("#txtfechaI").val());
        });
    });

    $("#cboEspecial").change(function () {
        let _id = form_historial.cboEspecial.value;
        //validaCboEspecial(_id);
    });

    $("#cboMedico").change(function () {
        let _id = form_historial.cboMedico.value;
        let _txtI = form_historial.txtfechaI.value;
        //alert("OK: " + _id + '|' + _txtI);
        //validaCboMedico(_id, _txtI);
    });

    //validaCboEspecial("0");
    //validaCboMedico("0","0");
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
