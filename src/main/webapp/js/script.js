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
						<a href="#editUserModal" class="settings" title="Settings" data-toggle="modal"><i class="material-icons" data-toggle="tooltip">&#xE8B8;</i></a>
						<a href="#deleteUserModal" class="delete" title="Delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip">&#xE5C9;</i></a>
                     </td>
                </tr>
			`
            })
            // console.log(tableInfo)
            tableBody.innerHTML = tableInfo
        })
        .catch(error => console.log(error))
})

