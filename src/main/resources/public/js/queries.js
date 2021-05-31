sendNewMessage = (roomId, userId) => {
    if (document.getElementById("write_msg").value !== "") {
        message.text = document.getElementById("write_msg").value;
        message.user_id = userId;
        message.room_id = roomId;
        message.date = (new Date()).toISOString().split('T')[0];
        createNewMessage(message);
    } else {
        alert("Введите текст сообщения!!!")
    }
}

submitNewUser = (elem) => {
    let data = elem.parentNode.parentNode;
    user.login = data.querySelector("#login").value;
    user.password = data.querySelector("#password").value;
    user.role = data.querySelector("#role").value;
    console.log(user);
    createNewUser(user);
}

const command = {
    command: null
}

const message = {
    message_id: null,
    text: null,
    date: null,
    user_id: null,
    room_id: null
}

const room = {
    room_id: null,
    name: null,
    private_message: null,
    user_id: false,
    users: []
}

const user = {
    user_id: null,
    login: null,
    password: null,
    role: null
}


sendRequest = (method, url, body) => {
    const headers = {
        'Content-Type': 'application/json'
    }
    if (body !== null) {
        return fetch(url, {
            method: method,
            body: JSON.stringify(body),
            headers: headers
        });
    } else {
        return fetch(url, {
            method: method,
            headers: headers
        });
    }
}

sendRequestWithFile = (method, url, body) => {
    const headers = {}
    console.log(body);
    if (body !== null) {
        return fetch(url, {
            method: method,
            body: body,
            headers: headers
        });
    } else {
        return fetch(url, {
            method: method,
            headers: headers
        });
    }
}

getUsers = (user_id) => {
    return sendRequest('GET', '/api/users/' + user_id, null).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            console.log(response);
        }
    });
}

createNewMessage = (message) => {
    sendRequest('POST', '/api/message', message).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.href = "http://localhost:8080/main";
        } else {
            console.log(response);
        }
    });
}

createNewUser = (user) => {
    sendRequest('POST', '/api/registrations', user).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

updateUserById = (user_id, user) => {
    sendRequest('PUT', '/api/users/' + user_id, user).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

deleteUserById = (user_id) => {
    sendRequest('DELETE', '/api/users/' + user_id).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

loadingRoomById = (roomId) => {
    if (roomId != null) {
        document.location.href = "http://localhost:8080/main/rooms/" + roomId;
    } else {
        document.location.href = "http://localhost:8080/main";
    }

}
