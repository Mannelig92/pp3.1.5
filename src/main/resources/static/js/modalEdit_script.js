const editMod = document.getElementById("formEdit")
const edit_id = document.getElementById("modal1_textInput")
const edit_name = document.getElementById("modal1-textInput-2")
const edit_lastName = document.getElementById("modal1-textInput-3")
const edit_age = document.getElementById("modal1-numInp")
const edit_email = document.getElementById("modal1-email")

async function editModalWindow(id) {
    jQuery.noConflict();
    $("#modalEdit").modal("show") //открытие модального окна
    const url = "api/admin/" + id
    let edit = await fetch(url)
    if (edit.ok) {
        let editPar = await edit.json().then(user => {
            edit_id.value = `${user.id}`
            edit_name.value = `${user.userName}`
            edit_lastName.value = `${user.lastName}`
            edit_age.value = `${user.age}`
            edit_email.value = `${user.email}`
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
            roles.push("ROLE_" + editMod.modalRoleEd.options[i].value)
        }
    }
    let method = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            userName: editMod.userName.value,
            lastName: editMod.lastName.value,
            age: editMod.age.value,
            email: editMod.email.value,
            password: editMod.password.value,
            roles: roles
        })
    }
    await fetch(urlEd, method).then(() => {
        adminPage()
        $("#editClose").click()
    })
}

