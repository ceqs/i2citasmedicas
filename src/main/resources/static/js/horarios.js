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
          editable: false,
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
});