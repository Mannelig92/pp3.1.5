const url = "/api/admin" //обращаемся к url адреса админа

async function adminPage() { //async гарантирует что функция вернёт промис
    /*
    Использование промисов гарантирует, что данные, полученные из API,
    не будут обрабатываться/форматироваться до тех пор, пока вызов API не будет успешно выполнен
    */
    let page = await fetch(url) //fetch позволяет JavaScript обмениваться данными с сервером с помощью HTTP-запросов
    if (page.ok) { //был ли ответ успешный, это диапазон ответа 200-299
        let listUsers = await page.json(); //await заставляет ждать пока промис не выполнится
        table(listUsers)
    } else {
        //спецсимвол обратных кавычек ` позволяет вставлять выражения типа ${...}
        alert(`Error, ${page.status}`) //иначе вывод ошибки
    }
}

function table(listUsers) {
    const table_body = document.getElementById("table_body") //соединяем с html-файлом таблицу
    let HTMLData = ""
    for (let user of listUsers) { //перебор по юзерам
        let roles = []
        for (let role of user.roles) { //по ролям юзера
            roles.push(" " + role.role.toString().substring(5))
        }
        //тут снова спецсимвол
        HTMLData += `<tr> 
                <td>${user.id}</td>
                <td>${user.userName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${roles.join(" ")}</td>
                <td>
                    <button class="btn btn-primary" id="table-cell-edit-button-1" data-toggle="modal"
                           onClick="editModalWindow(${user.id})" data-target="#modalEdit">Edit</button>
                </td>
                <td>
                    <button class="btn btn-primary" id="table-cell-edit-button-2" data-toggle="modal"
                            onClick="deleteModalWindow(${user.id})" data-target="#modalDelete">Delete</button>
                </td>
            </tr>`
    }
    table_body.innerHTML = HTMLData
}
adminPage()