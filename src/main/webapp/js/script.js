$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip()

    let tableBody = document.querySelector("#user-data")
    // console.log(tableBody);
    fetch("http://localhost:8080/demorest/api/users/v1")
        .then(response => response.json())
        .then(data => {
            console.log(data)
            let tableInfo = ""
            data.data.forEach(user => {
                // console.log(user.gender)
                tableInfo += `
				<tr>
					<td>${user.id}</td>
					<td>${user.name}</td>
					<td>${user.gender}</td>
					<td>${user.status}</td>
					 <td>
						<a href="#" class="settings" title="Settings" data-toggle="tooltip"><i class="material-icons">&#xE8B8;</i></a>
						<a href="#" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE5C9;</i></a>
                     </td>
                </tr>
			`
            })
            // console.log(tableInfo)
            tableBody.innerHTML = tableInfo
        })
        .catch(error => console.log(error))
})

