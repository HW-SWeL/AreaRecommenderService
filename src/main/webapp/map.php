<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

	<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDZg5qfeNjn_F3H1XQfAPj1x6HUuIz6lPI"></script>

	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

	<!-- User defined functions -->
	<script type="text/javascript" src="js/map.js"></script> 
	<script type="text/javascript" src="js/service.js"></script> 
	<script type="text/javascript" src="js/sidebar.js"></script> 

    <title>Area Information App</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/mainStyle.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]--> 
  </head>

  <body>  
	  
	 <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Area Information App</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="#">Home</a></li>     
                   
            <li class="dropdown">
			  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Profile <span class="caret"></span></a>
			  <ul class="dropdown-menu" role="menu">
				<li><a href="#">Profile Page</a></li>
				<li><a href="#">Saved Locations</a></li>
				<li class="divider"></li>
				<li><a href="#">Log out</a></li>
			  </ul>
			</li>
			
			<li><a href="#contact">About</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
        
   <div class="container">

		<div class="btn-group" role="group" aria-label="..." id="topPanel">
			<button type="button" class="btn btn-default">1</button>
			<button type="button" class="btn btn-default">2</button>

			<div class="btn-group" role="group" >
				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
					Dropdown
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">Dropdown link</a></li>
					<li><a href="#">Dropdown link</a></li>
				</ul>
			</div>
		</div>
			
		<div class="well well-lg" id="mainContent">
			
			<div id="mapContainer" class="col-md-8" >
				  <div class="col-lg-6">
					<div class="input-group">
					  <input type="text" class="form-control" placeholder="Search for...">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Go!</button>
					  </span>
					</div><!-- /input-group -->
				  </div><!-- /.col-lg-6 -->
			</div>
			
			<div  class="col-md-4" id="sidebar" >
				
			</div>
			
		</div>

    </div>
    
    <?php
$someVar = 1;
?>
    
    <script type="text/javascript">
    var data = <?php echo json_encode("42"); ?>; //Don't forget the extra semicolon!
</script>
    
	<script>
		var $_POST = <?php echo "TEST"; ?>;
		console.log($_POST);
	
		initMap("mapContainer", 55.953252000000000000, -3.188266999999996000);

		alert(areaNamePost);

		webService.getLocationByName(areaNamePost).done(function(data){
		    var location = JSON.parse(data);
		    console.log(location);
		    
		   	var latlng = location.latlng.split(",")
		   	latlng = { lat:parseFloat(latlng[0]), lng:parseFloat(latlng[1]) };
		   	addMarker(latlng, location.name);

		   	//Add a panel for each piece of information
		   	var sidebar = document.getElementById('sidebar');

		   	var percentStats = "Population: "+location.population+
		   						"</br>% of people working age: "+location.workingPercent+
		   						"</br>% of people pension age: "+location.pensionPercent+
		   						"</br>% of people child age: "+location.childPercent;
		   	sidebar.appendChild(createPanel(percentStats));

		   	var cafes = "Number of cafes: "+location.cafes.length;
		   	for(i = 0; i < location.cafes.length; i++){
				cafes+="</br>"+location.cafes[i].name;
		   	}
			sidebar.appendChild(createPanel(cafes));

			var entertainment = "Number of entertainment: "+location.entertainment.length;
		   	for(i = 0; i < location.entertainment.length; i++){
		   		entertainment+="</br>"+location.entertainment[i].name;
		   	}
			sidebar.appendChild(createPanel(entertainment));

			var groceryShops = "Number of grocery shops: "+location.groceryShops.length;
		   	for(i = 0; i < location.groceryShops.length; i++){
		   		groceryShops+="</br>"+location.groceryShops[i].name;
		   	}
			sidebar.appendChild(createPanel(groceryShops));

			var primarySchools = "Number of primary schools: "+location.primarySchools.length;
		   	for(i = 0; i < location.primarySchools.length; i++){
		   		primarySchools+="</br>"+location.primarySchools[i].name;
		   	}
			sidebar.appendChild(createPanel(primarySchools));

			var secondarySchools = "Number of secondary schools: "+location.secondarySchools.length;
		   	for(i = 0; i < location.secondarySchools.length; i++){
		   		secondarySchools+="</br>"+location.secondarySchools[i].name;
		   	}
			sidebar.appendChild(createPanel(secondarySchools));
		   
		});

	</script>
	
	
</body>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>
