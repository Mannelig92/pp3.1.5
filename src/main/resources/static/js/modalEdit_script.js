const editMod = document.getElementById("formEdit")
const edit_id = document.getElementById("modal1-textInput")
const edit_name = document.getElementById("modal1-textInput-2")
const edit_lastName = document.getElementById("modal1-textInput-3")
const edit_age = document.getElementById("modal1-numInp")
const edit_email = document.getElementById("modal1-email")
const edit_password = document.getElementById("modal1-password")

async function editModalWindow(id) {
    $("#modalEdit").modal("show")
    const url = "api/admin/" + id
    let edit = await fetch(url)
    if (edit.ok) {
        let editPar = await edit.json().then(user => {
            edit_id.value = `${user.id}`
            edit_name.value = `${user.userName}`
            edit_lastName.value = `${user.lastName}`
            edit_age.value = `${user.age}`
            edit_email.value = `${user.email}`
            edit_password.value = `${user.password}`
        })
    } else {
        alert(`Error, ${edit.status}`)
    }
}

async function edit() {
    let urlEd = "api/admin/" + edit_id.value //url с id пользователя
    let roles = []
    for (let i = 0; i < editMod.modalRoleEd.options.length; i++) { //перебираем возможные роли
        if (editMod.modalRoleEd.options[i].selected) {
            roles.push("ROLE_" + editMod.modalRoleEd.options[i].value) //тот же костыль, что и в newUser
        }
    }
    let method = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: edit_name.userName.value,
            lastname: edit_lastName.lastName.value,
            age: edit_age.age.value,
            email: edit_email.email.value,
            password: edit_password.password.value,
            roles: roles
        })
    }
    await fetch(urlEd, method).then(() => {
        $("#editClose").click
        adminPage()
    })
}

