const url = "/api/user"

async function getUserPage() {
    let page = await fetch(url);

    if (page.ok) {
        let user = await page.json();
        getInformationAboutUser(user);
    } else {
        alert(`Error, ${page.status}`)
    }
}

function getInformationAboutUser(user) {
    const table_body = document.getElementById("userBody") //соединяем с html-файлом таблицу
    let HTMLData = ""
    let roles = [];
    for (let role of user.roles) {
        roles.push(" " + role.role.toString().substring(5))
    }
    HTMLData +=
        `<tr>
    <td>${user.id}</td>
    <td>${user.userName}</td>
    <td>${user.lastName}</td>
    <td>${user.age}</td>
    <td>${user.email}</td>
    <td>${roles.join(" ")}</td>   
</tr>`

    table_body.innerHTML = HTMLData
}

getUserPage();