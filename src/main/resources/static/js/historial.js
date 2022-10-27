$(document).ready(function () {
    
    $("#btnBuscar").click(function () {
        //valida
        let txtI = form_historial.txtfechaI.value;
        let txtF = form_historial.txtfechaF.value;
        let cboE = form_historial.cboEspecial.value;
        let cboM = form_historial.cboMedico.value;

        if (txtI == null || txtI == "") {
            alert("Debe especificar fecha Inicio");
            return;
        }
        if (txtF == null || txtF == "") {
            alert("Debe especificar fecha Fin");
            return;
        }
        //alert("OK: " + txtI + '|' + txtF + '|' + cboE + '|' + cboM);

        $.post("HistorialController", $("#form_historial").serialize(), function (response) {         
            getDataListado(response);
        });
    });
    
    
    $("#cboEspecial").change(function () {        
        let _id = form_historial.cboEspecial.value;        
        validaCboEspecial(_id);
    });
    
    validaCboEspecial("0"); 
    setTypeDataTableMaestra();
});


function validaCboEspecial(_id){
    let opc = "1";
    $.post("HistorialController", {opc, _id}, function (response) {
        getListaMedxEsp(response);
    });
}

function getListaMedxEsp(response){
    var lista = $.parseJSON(response);     
    var resultado = "";  
    resultado += "<option value=0 selected='selected'>(TODOS)</option>";
    for (var i = 0; i < lista.length; i++) {
        resultado += "<option  value=" + lista[i].idMedico + ">" + lista[i].apellidos + "</option>";
    }
    $("#cboMedico").html(resultado);    
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
                scroller: true,
                
                 //para usar los botones   
                responsive: "true",
                dom: 'Bfrtilp',       
                buttons:[ 
			{
				extend:    'excelHtml5',
				text:      '<i class="fas fa-file-excel"></i> ',
				titleAttr: 'Exportar a Excel',
				className: 'btn btn-success'
			},
			{
				extend:    'pdfHtml5',
				text:      '<i class="fas fa-file-pdf"></i> ',
				titleAttr: 'Exportar a PDF',
				className: 'btn btn-danger'
			},
			{
				extend:    'print',
				text:      '<i class="fa fa-print"></i> ',
				titleAttr: 'Imprimir',
				className: 'btn btn-info'
			}
		]
            });
}