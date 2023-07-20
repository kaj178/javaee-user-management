$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip()

    readUsers()
})

function readUsers() {
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
}

function createUser() {
     let userName = $('.add_employee #name_input').val()
     let userGender =  $('.add_employee #gender_input').val()
     let userStatus =  $('.add_employee #status_input').val()
    // console.log(userName)
    // console.log(userGender)
    // console.log(userStatus)

     $.ajax({
         url: 'http://localhost:8080/demorest/api/users/v1',
         method: 'POST',
         contentType: 'application/json',
         data: JSON.stringify({
             name: userName,
             gender: userGender,
             status: userStatus
         }),
         success: (data) => {
             //let response = JSON.parse(data)
             console.log(data)
             $('#addUserModal').modal('hide')
             readUsers()
             alert('Add user successfully!')
         }
     })
}


