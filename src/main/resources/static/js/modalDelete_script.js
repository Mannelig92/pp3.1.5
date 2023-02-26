const deleteMod = document.getElementById("formDelete")
const delete_id = document.getElementById("modal2-textInp")
const delete_name = document.getElementById("modal2-textInp2")
const delete_lastName = document.getElementById("modal2-textInp3")
const delete_age = document.getElementById("modal2-numInp")
const delete_email = document.getElementById("modal2-email")

async function deleteModalWindow(id) {
    jQuery.noConflict();
    $("#modalDelete").modal("show") //открытие модального окна
    const url = "api/admin/" + id
    let del = await fetch(url)
    if (del.ok) {
        let delPar = await del.json().then(user => {
            delete_id.value = `${user.id}`;
            delete_name.value = `${user.userName}`;
            delete_lastName.value = `${user.lastName}`;
            delete_age.value = `${user.age}`;
            delete_email.value = `${user.email}`;
        })
    } else {
        alert(`Error, ${del.status}`)
    }
}

async function userDelete() {
    deleteMod.addEventListener("submit", remove)

    function remove(addEvent) {
        addEvent.preventDefault() // ”бирает действие браузера по-умолчанию. Ќе до конца пон€л, разобратьс€
        let urlDel = "api/admin/" + delete_id.value
        let method = {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        }
        fetch(urlDel, method).then(() => {
            $("#deleteClose").click()
            adminPage()
        })
    }
}