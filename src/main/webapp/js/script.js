$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
});

let tableBody = document.querySelector("tbody");
// console.log(tableBody);
fetch ("http://localhost:8080/demorest/api/users/v1")
	.then (response => response.json())
	.then(data => {
		data.forEach(user => {
			console.log(user.id)
		})
	})
	.catch(error => console.log(error));