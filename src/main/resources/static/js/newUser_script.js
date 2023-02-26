//document - ������ �������������� ���-������
const newUser = document.getElementById("formNewUser") //�������� ������ �� ������ �� id
//�������� ������ �� ��������� � ��������������� � ������ � ������� selectedOptions
const newRole = document.querySelector("#roleNew").selectedOptions

async function addNewUser() {
    newUser.addEventListener("submit", save) //�������� ����� ������������ �������

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
            //JSON.stringify ����������� ������ � ������
            body: JSON.stringify({
                //� ������� value �������� ������ �� ��������� ����
                userName: newUser.userName.value,
                lastName: newUser.lastName.value,
                age: newUser.age.value,
                email: newUser.email.value,
                password: newUser.password.value,
                roles: rolesList
            })
        }
        //����� ���������� ������������ �� �������� ������
        await fetch(url, method).then(() => {
            newUser.reset() //���������� ������ �� �����
            adminPage() //��������� ��� ��������� ������ �����
            $("#tab-link-1").click()
        })
    }
}