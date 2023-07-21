$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip()

    $(document).on('click', '#edit-btn', (e) => {
        // console.log("Hello")
        let userID = $(e.currentTarget).attr('data-id') // .data('id')
        console.log(userID)
        $('#edit-modal').attr('data-id', userID) // Add id to data-id value of button in modal
    })
    $(document).on('click', '#delete-btn', (e) => {
        let userID = $(e.currentTarget).attr('data-id')
        $('#delete-modal').attr('data-id', userID)
    })

    readUsers()
    $('#add-modal').on('click', createUser)
    $('#edit-modal').on('click', updateUser)
    $('#delete-modal').on('click', deleteUser)

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
						<a href="#editUserModal" class="settings" title="Settings" data-toggle="modal"><i id="edit-btn" class="material-icons" data-id="${user.id}" data-toggle="tooltip">&#xE8B8;</i></a>
						<a href="#deleteUserModal" class="delete" title="Delete" data-toggle="modal"><i id="delete-btn" class="material-icons" data-id="${user.id}" data-toggle="tooltip">&#xE5C9;</i></a>
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
        let userGender = $('.add_employee #gender_input').val()
        let userStatus = $('.add_employee #status_input').val()
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
                // console.log(data)
                $('#addUserModal').modal('hide')
                readUsers()
                alert('Add user successfully!')
            }
        })
    }

    function updateUser() {
        let userID = $('#edit-modal').attr('data-id')
        let userName = $('.edit_employee #name_edit').val()
        let userGender = $('.edit_employee #gender_edit').val()
        let userStatus = $('.edit_employee #status_edit').val()
        // console.log(userID)
        // console.log(userName)
        // console.log(userGender)
        // console.log(userStatus)

        $.ajax({
            url: 'http://localhost:8080/demorest/api/users/v1/' + userID,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                name: userName,
                gender: userGender,
                status: userStatus
            }),
            success: (data) => {
                //let response = JSON.parse(data)
                console.log(data)
                $('#editUserModal').modal('hide')
                readUsers()
                alert('Edit user successfully!')
            }
        })
    }

    function deleteUser() {
        let userID = $('#delete-modal').attr('data-id')
        // console.log(userID)

        $.ajax({
            url: 'http://localhost:8080/demorest/api/users/v1/' + userID,
            method: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify({
                id: userID
            }),
            success: () => {
                $('#delete-modal').hide()
                readUsers()
                alert('Delete user successfully!')
            }
        })
    }
})



