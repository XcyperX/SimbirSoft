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

sendCommand = () => {
    if (document.getElementById("write_msg").value !== "") {
        command.command = document.getElementById("write_msg").value;
        sendCommandToServer(command);
    } else {
        alert("Введите текст сообщения!!!")
    }
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

sendCommandToServer = (command) => {
    sendRequest('POST', '/api/command', command).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
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
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

getProduct = (product_id) => {
    return sendRequest('GET', '/api/products/' + product_id, null).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            console.log(response);
        }
    });
}

createNewOrder = (order) => {
    sendRequest('POST', '/api/new/customer/order', order).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
            alert("Ваш заказ принят! )");
            deleteCookie();
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

createNewProduct = (product) => {
    sendRequestWithFile('POST', '/api/product/image', product).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

addProductsByOrder = (orderId, elem) => {
    let storeId = elem.parentNode.parentNode;
    sendRequestWithFile('POST', '/api/add/products/orders/' + orderId + '/stock/' + storeId.querySelector("#store_id").value).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

updateProductById = (product_id, product) => {
    sendRequestWithFile('PUT', '/api/product/image/' + product_id, product).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

deleteProductById = (product_id) => {
    sendRequest('DELETE', '/api/products/' + product_id).then(response => {
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
            document.location.href = "http://localhost:8080/users";
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

createNewManufacturers = (manufacturers) => {
    sendRequest('POST', '/api/manufacturers', manufacturers).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

createNewSubdivision = (subdivision) => {
    sendRequest('POST', '/api/subdivisions', subdivision).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

createNewCategories = (categories) => {
    sendRequest('POST', '/api/categories', categories).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

createNewPosition = (position) => {
    sendRequest('POST', '/api/positions/names', position).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

createNewStore = (store) => {
    sendRequest('POST', '/api/store', store).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

createNewEmployee = (employee) => {
    sendRequest('POST', '/api/registrations', employee).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

updateEmployeeById = (employee_id, employee) => {
    sendRequest('PUT', '/api/users/' + employee_id, employee).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.reload(true);
        } else {
            console.log(response);
        }
    });
}

updateEmployeeVacationById = (employee_id) => {
    let legacyEmployee = document.getElementById("createEmployee_" + employee_id);
    vacation.vacation_start = legacyEmployee.querySelector("#vacation_start").value;
    vacation.vacation_final = legacyEmployee.querySelector("#vacation_final").value;
    console.log(vacation);
    sendRequest('PUT', '/api/products/vacation/' + employee_id, vacation).then(response => {
        if (response.ok) {
            console.log(response);
            document.location.href = "http://localhost:8080/vacation";
        } else {
            console.log(response);
        }
    });
}

loadingRoomById = (roomId) => {
    document.location.href = "http://localhost:8080/main/rooms/" + roomId;
}
