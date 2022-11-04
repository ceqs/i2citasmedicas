 let myChart=null;

$(document).ready(function () {

    $("#btnBuscar").click(function () {
        //valida
        let txtI = form_grafico.txtanio.value;

        if (txtI == null || txtI == "") {
            alert("Debe especificar Anio");
            return;
        }
        //alert("OK: " + txtI + '|' + txtF + '|' + cboE + '|' + cboM);

        document.getElementById("myChartX").style.display = "none";
        document.getElementById("divMessage").style.display = "none";

        $.getJSON("/v2/graficos", { anio: txtI },function (response) {
            getDataListado(response);
        });

    });

});


function getDataListado(response) {
    //JSON.parse(response
 console.log(response);
    var lista = response;
    console.log(lista);
    if (lista.length > 0){
        document.getElementById("myChartX").style.display = "block";
        setTypeChartControl(lista);
    }else{
        document.getElementById("divMessage").style.display = "block";
    }
}

function setTypeChartControl(lista){
    let ntype = form_grafico.tipo.value;
    let ctx = document.getElementById('myChartX').getContext('2d');
    if (myChart) {
        myChart.destroy();
    }
    myChart = new Chart(ctx, {
    type: ntype,
    data: {
        datasets: [{
            label: 'Total Atenciones',
            backgroundColor: [
                '#FAEBD7',
                '#DCDCDC',
                '#E9967A',
                '#F5DEB3',
                '#9ACD32',
                '#E9DAC6'
            ],
            borderColor: ['black'],
            borderWidth: 1
        }]
    },
    options: {
        "responsive": false,
        "maintainAspectRatio": true,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

    //carga data
    for (var i = 0; i < lista.length; i++) {
        myChart.data['labels'].push(lista[i].nameM);
        myChart.data['datasets'][0].data.push(lista[i].total);
    }
    myChart.update();
}
