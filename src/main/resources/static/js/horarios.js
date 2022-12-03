$(document).ready(function () {
    var initialLocaleCode = 'es';
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
          initialDate: '2022-11-01',
          initialView: 'timeGridWeek',
          locale: initialLocaleCode,
          nowIndicator: true,
          slotDuration: '00:15:00',
          lotEventOverlap: false,
          snapDuration: '00:15:00',
          slotLabelFormat: {
            hour: 'numeric',
            minute: '2-digit',
            omitZeroMinute: false
          },
          headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'timeGridWeek,timeGridDay'
          },
          select: function(arg) {
            if (confirm('Desea programar esos horarios?')) {
                console.log(arg);
                var horario = {
                    medico: {
                        id: $('#cboMedico').val()
                    },
                    start: arg.startStr,
                    end: arg.endStr
                };
                $.ajax({
                      url:"/v1/horarios/group",
                      type:"POST",
                      data:JSON.stringify(horario),
                      contentType:"application/json; charset=utf-8",
                      dataType:"json",
                      success: function(response){
                          $('#btnBuscar').trigger('click');
                      }
                });
            }

          },
          eventClick: function(arg) {
            if(arg.event.title == "RESERVADO") {
                if (confirm('Desea anular la cita?')) {
                    $.ajax({
                      url:"/v1/reservas/" + arg.event.extendedProps.idCita,
                      type:"DELETE",
                      contentType:"application/json; charset=utf-8",
                      dataType:"json",
                      success: function(response){
                          arg.event.title = "LIBERADO";
                          arg.event.backgroundColor = "#32cd32";
                          alert("Cita con ticket " + arg.event.extendedProps.idCita + " fue anulada.");
                          $('#btnBuscar').trigger('click');
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
          events: [
          {
                    title: 'Meeting',
                    start: '2022-11-26T12:15:00',
                    end: '2022-11-26T12:30:00'
                  },
                {
                    title: 'Meeting2',
                    start: '2022-11-26T12:30:00',
                    end: '2022-11-26T12:45:00'
                  }
          ]
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
