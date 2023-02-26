//document - объект представл€ющий дом-дерево
const newUser = document.getElementById("formNewUser") //получаем ссылку на объект по id
//получаем ссылку по селектору и преобразовываем в массив с помощью selectedOptions
const newRole = document.querySelector("#roleNew").selectedOptions

async function addNewUser() {
    newUser.addEventListener("submit", save) //действие после срабатывани€ событи€

    async function save(addEvent) {
        addEvent.preventDefault()
        const url = "/api/admin"
        let rolesList = []
        for (let i = 0; i < newRole.length; i++) {
            rolesList.push({
                id: newRole[i].value,
                name: "ROLE_" + newRole[i].text
            })
        }
        let method = {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            //JSON.stringify преобразует объект в строку
            body: JSON.stringify({
                //с помощью value получаем данные из вводимого пол€
                userName: newUser.userName.value,
                lastName: newUser.lastName.value,
                age: newUser.age.value,
                email: newUser.email.value,
                password: newUser.password.value,
                roles: rolesList
            })
        }
        //после выполнени€ возвращаемс€ на страницу админа
        await fetch(url, method).then(() => {
            newUser.reset() //сбрасываем данные по юзеру
            adminPage() //обновл€ем дл€ по€влени€ нового юзера
            $("#tab-link-1").click()
        })
    }
}