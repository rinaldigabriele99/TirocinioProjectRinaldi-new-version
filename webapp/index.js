$(document).ready(function(){

	$("#form-hide-show").show()
	$("#file-hide-show").show()
	$("#file-hide-show").hide()
	$("#gbm_pojo").show()
	$("#gbm_mojo").show()
	$("#glm_pojo").show()
	$("#glm_mojo").show()
	$("#dfr_pojo").show()
	$("#dfr_mojo").show()
	$("#glm_pojo").hide()
	$("#glm_mojo").hide()
	$("#dfr_pojo").hide()
	$("#dfr_mojo").hide()

	$("#gbm_pojo1").show()
	$("#gbm_mojo1").show()
	$("#glm_pojo1").show()
	$("#glm_mojo1").show()
	$("#dfr_pojo1").show()
	$("#dfr_mojo1").show()
	$("#gbm_pojo1").hide()
	$("#gbm_mojo1").hide()
	$("#glm_pojo1").hide()
	$("#glm_mojo1").hide()
	$("#dfr_pojo1").hide()
	$("#dfr_mojo1").hide()



	$( "#algoritmo" ).on( "change", function() {
		console.log($(this).val())
		$("#gbm_pojo").show()
		$("#gbm_mojo").show()
		$("#glm_pojo").show()
		$("#glm_mojo").show()
		$("#dfr_pojo").show()
		$("#dfr_mojo").show()


		if ($('#algoritmo option:selected').text() == 'Gradient Boosting Machine'){
				$("#glm_pojo").hide()
				$("#glm_mojo").hide()
				$("#dfr_pojo").hide()
				$("#dfr_mojo").hide()
		} 
		if ($('#algoritmo option:selected').text() == 'Generalized Linear Model'){
				$("#gbm_pojo").hide()
				$("#gbm_mojo").hide()
				$("#dfr_pojo").hide()
				$("#dfr_mojo").hide()
		}
		if ($('#algoritmo option:selected').text() == 'Distributed Random Forest'){
				$("#gbm_pojo").hide()
				$("#gbm_mojo").hide()
				$("#glm_pojo").hide()
				$("#glm_mojo").hide()
		}
		if ($('#algoritmo option:selected').text() == 'Models Comparison'){
				$("#gbm_pojo").hide()
				$("#gbm_mojo").hide()
				$("#glm_pojo").hide()
				$("#glm_mojo").hide()
				$("#dfr_pojo").hide()
				$("#dfr_mojo").hide()
		}
	})


	$("#data-input").on("change", function(){
		console.log('funzionaaaaaaaaaaaaaaaaaaaa')
		$("#form-hide-show").show()
		$("#file-hide-show").show()
		if ($('#data-input option:selected').text() == 'Fill out the form'){
			$("#file-hide-show").hide()
		}
		if ($('#data-input option:selected').text() == 'Import new data from a csv file'){
			$("#form-hide-show").hide()

			$("#gbm_pojo1").show()
			$("#gbm_mojo1").show()
			$("#glm_pojo1").show()
			$("#glm_mojo1").show()
			$("#dfr_pojo1").show()
			$("#dfr_mojo1").show()


			if ($('#algoritmo option:selected').text() == 'Gradient Boosting Machine'){
					$("#glm_pojo1").hide()
					$("#glm_mojo1").hide()
					$("#dfr_pojo1").hide()
					$("#dfr_mojo1").hide()
			} 
			if ($('#algoritmo option:selected').text() == 'Generalized Linear Model'){
					$("#gbm_pojo1").hide()
					$("#gbm_mojo1").hide()
					$("#dfr_pojo1").hide()
					$("#dfr_mojo1").hide()
			}
			if ($('#algoritmo option:selected').text() == 'Distributed Random Forest'){
					$("#gbm_pojo1").hide()
					$("#gbm_mojo1").hide()
					$("#glm_pojo1").hide()
					$("#glm_mojo1").hide()
			}
			if ($('#algoritmo option:selected').text() == 'Models Comparison'){
					$("#gbm_pojo1").hide()
					$("#gbm_mojo1").hide()
					$("#glm_pojo1").hide()
					$("#glm_mojo1").hide()
					$("#dfr_pojo1").hide()
					$("#dfr_mojo1").hide()
			}

			$( "#algoritmo" ).on( "change", function() {
				console.log($(this).val())
				$("#gbm_pojo1").show()
				$("#gbm_mojo1").show()
				$("#glm_pojo1").show()
				$("#glm_mojo1").show()
				$("#dfr_pojo1").show()
				$("#dfr_mojo1").show()


				if ($('#algoritmo option:selected').text() == 'Gradient Boosting Machine'){
						$("#glm_pojo1").hide()
						$("#glm_mojo1").hide()
						$("#dfr_pojo1").hide()
						$("#dfr_mojo1").hide()
				} 
				if ($('#algoritmo option:selected').text() == 'Generalized Linear Model'){
						$("#gbm_pojo1").hide()
						$("#gbm_mojo1").hide()
						$("#dfr_pojo1").hide()
						$("#dfr_mojo1").hide()
				}
				if ($('#algoritmo option:selected').text() == 'Distributed Random Forest'){
						$("#gbm_pojo1").hide()
						$("#gbm_mojo1").hide()
						$("#glm_pojo1").hide()
						$("#glm_mojo1").hide()
				}
				if ($('#algoritmo option:selected').text() == 'Models Comparison'){
						$("#gbm_pojo1").hide()
						$("#gbm_mojo1").hide()
						$("#glm_pojo1").hide()
						$("#glm_mojo1").hide()
						$("#dfr_pojo1").hide()
						$("#dfr_mojo1").hide()
				}
			})
		}
	})

	/*$("#idForm").submit(function(e) {

    e.preventDefault(); // avoid to execute the actual submit of the form.

    var form = $(this);
    var url = form.attr('action');
    console.log(url)
    console.log(form.serialize())
    alert($("#formFile").val());
    var fd = new FormData();
    fd.append('file', this.files[0]);
    
    $.ajax({
           type: "POST",
           url: url,
           dataType: 'json',
    	   processData: false,
    	   contentType: false,
           data: fd, // serializes the form's elements.
           success: function(data)
           {
               alert(data); // show response from the php script.
           }
         });
    
	});*/

	$("#idForm").submit(function(e) {
    e.preventDefault(); // avoid to execute the actual submit of the form.
	var form = $(this);
    var url = form.attr('action');
	var formData = new FormData();
	formData.append('files', $('#formFile')[0].files[0]);

	$.ajax({
       url : url,
       type : 'POST',
       data : formData,
       processData: false,  // tell jQuery not to process the data
       contentType: false,  // tell jQuery not to set contentType
       success : function(risposta) {
           console.log(risposta);
           listaPredict(risposta)
       }
	});
	});

	$( "#buttonPrediction" ).on( "click", function() {
		$("#idContainerPrediction").html(" ")
  
		var acidityf = $("#FixedAcidity").val();
		var acidityv = $("#VolatileAcidity").val();
		var acid = $("#CitricAcid").val();
		var sugar = $("#ResidualSugar").val();
		var chlorides = $("#Chlorides").val();
		var free_sulfur = $("#FreeSulfurDioxide").val();
		var total_sulfur = $("#TotalSulfurDioxide").val();
		var density = $("#Density").val();
		var ph = $("#pH").val();
		var sulphates = $("#Sulphates").val();
		var alchol = $("#Alchol").val();
		//console.log(alchol)

		var par = {  'fixed_acidity':  			acidityf
					,'volatile_acidity':  		acidityv
					,'citric_acid':             acid
					,'residual_sugar':          sugar
					,'chlorides':               chlorides
					,'free sulfur_dioxide':     free_sulfur
					,'total_sulfur dioxide':    total_sulfur
					,'density':                 density
					,'pH':                      ph
					,'sulphates':               sulphates
					,'alchol':                  alchol
				}


		var url = "http://localhost:8080/predict"

		
		$.ajax({
		  // definisco il tipo della chiamata
		  type: "GET",
		  // specifico la URL della risorsa da contattare
		  url: url,
		  // passo dei dati alla risorsa remota
		  data: par,
		  // definisco il formato della risposta
		  dataType: "json",
		  // imposto un'azione per il caso di successo
		  success: function(risposta){
		  	definisciEventi(risposta)
		  },
		  // ed una per il caso di fallimento
		  error: function(){
		    alert("Chiamata fallita!!!");
		    console.log('chiamata fallita!!');
		  }
		})
	})


	definisciEventi = function(risposta){
		// seleziona il testo
		console.log($('#algoritmo option:selected').text())
		if ($('#algoritmo option:selected').text() == 'Gradient Boosting Machine'){
			console.log($('#algoritmo option:selected').text())
			gbmEvento(risposta)
		} 
		if ($('#algoritmo option:selected').text() == 'Generalized Linear Model'){
			console.log($('#algoritmo option:selected').text())
			glmEvento(risposta)
		}
		if ($('#algoritmo option:selected').text() == 'Distributed Random Forest'){
			console.log($('#algoritmo option:selected').text())
			drfEvento(risposta)
		}
		if ($('#algoritmo option:selected').text() == 'Models Comparison'){
			console.log($('#algoritmo option:selected').text())
			modelComparisonEvento(risposta)
		}
	} 

	gbmEvento = function(risposta){
		$("#idContainerPrediction").html("")
		var titolo = $('<div>').attr({'id': 'titolo'})
		$("#idContainerPrediction").append(titolo)
		$('#titolo').html('Prediction with Gradient Boosting Machine algorithm')
		//creare html con apposite predizioni in base al json
		var paragrafo1 = $("<span>").html("Wine quality  ")
	    var paragrafo2 = $("<span>").html(risposta.prediction)
	    var paragrafo3 = $("<span>").html("  gbm")
	    var paragrafo4 = $("<p>").html(" ")
	    
	    var riga = $("<hr>")

	    $("#idContainerPrediction").append(paragrafo1)
	    $("#idContainerPrediction").append(paragrafo2)
	    $("#idContainerPrediction").append(paragrafo3)
	    $("#idContainerPrediction").append(riga)
	}


	/*gbmEvento = function(risposta){
		var pannello = $("#idContainerPrediction").dialog({
							modal: 			true
							,width: 		'auto'
							,height: 		'auto'
							,draggable: 	true
							,title: 		'Risultato GBM'
						})
		pannello.append($('<div>').attr({'id': 'idContainerPredictionPanel'}))

		$("#idContainerPredictionPanel").html("")
		var titolo = $('<div>').attr({'id': 'titolo'})
		$("#idContainerPredictionPanel").append(titolo)
		$('#titolo').html('Prediction with Gradient Boosting Machine algorithm')
		//creare html con apposite predizioni in base al json
		var paragrafo1 = $("<span>").html("Wine quality  ")
	    var paragrafo2 = $("<span>").html(risposta.prediction)
	    var paragrafo3 = $("<span>").html("  gbm")
	    var paragrafo4 = $("<p>").html(" ")
	    
	    var riga = $("<hr>")

	    $("#idContainerPredictionPanel").append(paragrafo1)
	    $("#idContainerPredictionPanel").append(paragrafo2)
	    $("#idContainerPredictionPanel").append(paragrafo3)
	    $("#idContainerPredictionPanel").append(riga)
	}*/


	glmEvento = function(risposta){
		$("#idContainerPrediction").html("")
		var titolo = $('<div>').attr({'id': 'titolo'})
		$("#idContainerPrediction").append(titolo)
		$('#titolo').html('Prediction with Generalized Linear Model algorithm')
		//creare html con apposite predizioni in base al json
		var paragrafo1 = $("<span>").html("Wine quality  ")
	    var paragrafo2 = $("<span>").html(risposta.prediction1)
	    var paragrafo3 = $("<span>").html("  glm")
	    var paragrafo4 = $("<p>").html(" ")
	    
	    var riga = $("<hr>")

	    $("#idContainerPrediction").append(paragrafo1)
	    $("#idContainerPrediction").append(paragrafo2)
	    $("#idContainerPrediction").append(paragrafo3)
	    $("#idContainerPrediction").append(riga)
	}

	drfEvento = function(risposta){
		$("#idContainerPrediction").html("")
		var titolo = $('<div>').attr({'id': 'titolo'})
		$("#idContainerPrediction").append(titolo)
		$('#titolo').html('Prediction with Distributed Random Fores algorithm')
		//creare html con apposite predizioni in base al json
		var paragrafo1 = $("<span>").html("Wine quality  ")
	    var paragrafo2 = $("<span>").html(risposta.prediction2)
	    var paragrafo3 = $("<span>").html("  drf")
	    var paragrafo4 = $("<p>").html(" ")
	    
	    var riga = $("<hr>")

	    $("#idContainerPrediction").append(paragrafo1)
	    $("#idContainerPrediction").append(paragrafo2)
	    $("#idContainerPrediction").append(paragrafo3)
	    $("#idContainerPrediction").append(riga)
	}

	listaPredict = function(risposta){
		$("#idContainerPrediction").html("")
		var titolo = $('<div>').attr({'id': 'titolo'})
		$("#idContainerPrediction").append(titolo)
		$('#titolo').html('File predictions')
		var riga = $('<div>').attr({'class': 'row'})
		riga.append($('<div>').attr({'class': 'col-4', 'id':'gbm'}))
		riga.append($('<div>').attr({'class': 'col-4', 'id':'glm'}))
		riga.append($('<div>').attr({'class': 'col-4', 'id':'drf'}))
		$("#idContainerPrediction").append(riga)
		lista_esterna = []
		lista_interna = []
		//console.log(risposta.size())
		var json = $.parseJSON(risposta)

		const tabella = $('<table class="table table-striped table-bordered display compact" style="width:100%"></table>').attr({'id':"idTabellaPredictions"})
	    tabella.append($("<thead>"))
	    tabella.append($("<tbody>"))
	    tabella.append($("<tfoot>"))
	    $('#idContainerPrediction').append(tabella)

		$("#idTabellaPredictions").DataTable({
			data: 			json
			,"paging":   false
        	,"ordering": false
        	,"info":     false
        	,"search":     false
			//,pageLength:	4
			,autoFill:		true
			,columns: 	[
							{title: "GBM", data: "gbm", name: "gbm"},
							{title: "GLM", data: "glm", name: "glm"},
							{title: "DRF", data: "drf", name: "drf"}
						]
		})

	}

	modelComparisonEvento = function(risposta){
		console.log('funzione ok')
		$("#idContainerPrediction").html("")
		var titolo = $('<div>').attr({'id': 'titolo'})
		$("#idContainerPrediction").append(titolo)
		$('#titolo').html('Model Comparison')
		var riga = $('<div>').attr({'class': 'row'})
		riga.append($('<div>').attr({'class': 'col-4', 'id':'gbm'}))
		riga.append($('<div>').attr({'class': 'col-4', 'id':'glm'}))
		riga.append($('<div>').attr({'class': 'col-4', 'id':'drf'}))
		$("#idContainerPrediction").append(riga)
		$("#gbm").html(risposta.prediction)
		$("#glm").html(risposta.prediction1)
		$("#drf").html(risposta.prediction2)


		//creare html con apposite predizioni in base al json

		var grafico = $('<div>').attr({'id': 'grafico'})
		$("#idContainerPrediction").append(grafico)
		console.log('dentro al div grafico ci sono')
		google.charts.load('current', {packages: ['bar']});
	  	google.charts.setOnLoadCallback(drawChart);

		function drawChart() {
			console.log('dentro alla function drawbasic ci sono')
			console.log(risposta.modello)
			console.log(risposta.prediction)
	      var data = google.visualization.arrayToDataTable([
	      	['Model', 'Prediction'],
	      	[risposta.modello, risposta.prediction],
	      	[risposta.modello1, risposta.prediction1],
	      	[risposta.modello2, risposta.prediction2]
  		  ]);
	      var options = {
	        title: 'Predictions of different models compared',
	        hAxis: {
	          title: 'Prediction'
	        },
	        vAxis: {
	          title: 'Model'
	        },
	        bars: 'horizontal'
	      };
	      var chart = new google.charts.Bar(document.getElementById('grafico'));

	      chart.draw(data, google.charts.Bar.convertOptions(options));
	    }
	    var riga = $("<hr>")
 		$("#idContainerPrediction").append(riga)	
	}
})














/*


definisciEventi = function(){
	// seleziona il testo
	console.log($('#algoritmo option:selected').text())
	if ($('#algoritmo option:selected').text() == 'Gradient Boosting Machine'){
		console.log($('#algoritmo option:selected').text())
		var bgm = $.get("/predict-servlet", function(jsonResponse){
			gbmEvento(jsonResponse)
		})
	}  
}*/
/*
gbmEvento = function(jsonResponse){
	$("#idContainerPrediction").html("")
	var titolo = $('<div>').attr({'id': 'titolo'})
	$("#idContainerPrediction").append(titolo)
	$('#titolo').html('Prediction with Gradient Boosting Machine algorithm')
	//creare html con apposite predizioni in base al json
	for (el in jsonResponse){}
}

glmEvento = function(jsonResponse){
	$("#idContainerPrediction").html("")
	$('#titolo').html('Prediction with Generalized Linear Model algorithm')
	//creare html con apposite predizioni in base al json
	for (el in jsonResponse){}
}

naiveBayesEvento = function(jsonResponse){
	$("#idContainerPrediction").html("")
	$('#titolo').html('Prediction with Naïve Bayes algorithm')
	//creare html con apposite predizioni in base al json
	for (el in jsonResponse){}
}

modelComparisonEvento = function(jsonResponse){
	$("#idContainerPrediction").html("")
	$('#titolo').html('Model Comparison')
	var riga = $('<div>').attr({'class': 'row'})
	riga.append($('<div>').attr({'class': 'col-4', 'id':'gbm'}))
	riga.append($('<div>').attr({'class': 'col-4', 'id':'glm'}))
	riga.append($('<div>').attr({'class': 'col-4', 'id':'naiveBayes'}))
	$("#idContainerPrediction").append(riga)
	//creare html con apposite predizioni in base al json
	for (el in jsonResponse){}
}*/