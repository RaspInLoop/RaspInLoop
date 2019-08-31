	
	
$(document).ready(function() {
	
	const urlParams = new URLSearchParams(window.location.search);
	const packageId = urlParams.get('package');
	
    $.ajax({
        url: ("http://localhost:8080/api/modelica/package/" + packageId)
    }).then(function(data) {
       $('.package-id').append(packageId);
       $('.package-description').append(data.description);
       
       var ico = data.svgIcon.replace('<!--?xml version="1.0" ?-->','');
       ico = '<svg height="200" width="200" viewBox="-10 -10 20 20">' + ico + "</svg>";
       $('.package-icon').append(ico);
    });
});

