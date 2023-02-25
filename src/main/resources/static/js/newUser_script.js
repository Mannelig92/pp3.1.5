//document - объект представл€ющий дом-дерево
const newUser = document.getElementById("formNewUser") //получаем ссылку на объект по id
//получаем ссылку по селектору и преобразовываем в массив с помощью selectedOptions
const newRole = document.document.querySelector("#tp2-sel").selectedOptions

async function addNewUser(){
    newUser.addEventListener("submit", save) //действие после срабатывани€ событи€

    async function save(addEvent){
        addEvent.preventDefault()
        const url = "/api/admin"
        let rolesList = []
        for (let i = 0; i < newRole.length; i++) {
            rolesList.push("ROLE_" + newRole[i].value)
        }
        let method = {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: newUser.name.value,
                lastName: newUser.lastName.value,
                age: newUser.age.value,
                email: newUser.email.value,
                password: newUser.password.value,
                roles: rolesList
            })
        }
        await fetch(url, method).then(() =>{
            newUser.reset()
            adminPage()
        })
    }
}