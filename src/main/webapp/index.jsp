<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="./styles/style.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Title</title>
</head>
<body>

<div class="container">
    <div class="container-fluid">
        <h1>ToDo List</h1>
        <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.email}"/>
        </a>
        <a class="nav-link" href="LogoutServlet">
            Log out
        </a>
    </div>
    <form action="<%=request.getContextPath()%>/tasks" method="post">
        <div class="form-group">
            <label for="task">Name:</label>
            <input type="text" class="form-control" id="task">
            <div class="valid-inform  hidden" id="nameInvalid">Поле заполнено неверно</div>
        </div>
        <button type="submit" class="py-2 btn btn-default" id="submit">Submit</button>
    </form>
    <div class="switch-complete">
        <label class="switch">
            <input type="checkbox" id="toggle-complete">
            <span class="slider round"></span>
        </label>
        Show not completed.
    </div>

    <table class="table table-striped" id="usersTable">
        <thead>
        <tr>
            <th>Date</th>
            <th>Task</th>
            <th>Completed</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
</div>
<template id="template">
    <td>10.06.2020</td>
    <td>Прополоть грядку</td>
    <td>
        <input type="checkbox" class="task-checkbox">
        <button type="button" class="delBtn btn  ml-3">
            <i class="fa fa-times"></i>
        </button>
    </td>
</template>
<script>
    let json;

    /**
     * Form validation.
     * @param task
     */

    let validate = (task) => {
        document.querySelectorAll('.valid-inform').forEach(el => el.classList.add('hidden'));
        let isValid = true;
        if (task.trim() === '') {
            isValid = false;
            let error = document.querySelector('#nameInvalid');
            error.classList.remove('hidden');
        }
        return isValid;
    }

    /**
     * Add new task.
     * @type {HTMLFormElement}
     */
    let form = document.querySelector('form');
    form.addEventListener('submit', function (e) {
        e.preventDefault()
        let task = document.querySelector('#task').value;
        if (validate(task)) {
            fetch('http://localhost:8081/job4j_todo/tasks.do',
                {
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    body: JSON.stringify({name: task})
                }
            ).then(response => response.json())
                .then(data => {
                    if (isToggle) {
                        renderNotCompleted(data)
                    } else {
                        renderTasks(data);
                    }
                    let checkboxes = document.querySelectorAll('.task-checkbox');
                    let deleteBtns = document.querySelectorAll('.delBtn');
                    checkBoxHandle(checkboxes);
                    delBtnsHandle(deleteBtns);
                }).catch(error => console.error(error))

        }
        task = "";
    });


    /**
     * Add handler to all checkboxes style them cheched/unchecked
     * @type {NodeListOf<Element>}
     */
    const checkboxes = document.querySelectorAll('.task-checkbox')
    let checkBoxHandle = function (checkboxes) {
        for (const checkbox of checkboxes) {
            checkbox.addEventListener('change', (event) => {
                    if (event.target.checked) {
                        event.target.parentNode.parentNode.classList.add("task--checked");

                    } else {
                        event.target.parentNode.parentNode.classList.remove("task--checked");
                    }
                    fetch('http://localhost:8081/job4j_todo/tasks.do?ID=' + event.target.value + "&completed=" + event.target.checked,
                        {
                            method: 'GET'
                        }
                    ).then(response => {
                        console.log(response.text)
                    })
                        .catch(error => console.error(error))
                    if (isToggle) {
                        renderNotCompleted(data)
                    }
                    let checkboxes = document.querySelectorAll('.task-checkbox');
                    const deleteBtns = document.querySelectorAll('.delBtn');
                    checkBoxHandle(checkboxes);
                    delBtnsHandle(deleteBtns);
                }
            )
        }
    }

    /**
     * Add handler on Delete buttons.
     * */
    const deleteBtns = document.querySelectorAll('.delBtn');
    let delBtnsHandle = function (deleteBtns) {
        for (const btn of deleteBtns) {
            btn.addEventListener('click', (event) => {
                let id;
                let row;
                if (event.currentTarget.nodeName == 'BUTTON') {
                    id = event.currentTarget.id;
                    row = event.currentTarget.parentNode.parentNode;
                } else {
                    id = event.target.parentNode.id;
                    row = event.target.parentNode.parentNode.parentNode;
                }
                row.parentNode.removeChild(row);
                fetch('http://localhost:8081/job4j_todo/delete.do?ID=' + id,
                    {
                        method: 'GET'
                    }
                ).then(response => {
                    console.log(response.text);
                })
                    .catch(error => console.error(error))
            })
        }
    }

    window.onload = function () {
        // checkBoxHandle(checkboxes);

        /**
         * Upload all tasks.
         */
        fetch('http://localhost:8081/job4j_todo/tasks.do',
            {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json; charset=utf-8'
                }
            }
        ).then(response => response.json())
            .then(data => {
                if (isToggle) {
                    renderNotCompleted(data)
                } else {
                    renderTasks(data);
                }
                let checkboxes = document.querySelectorAll('.task-checkbox');
                const deleteBtns = document.querySelectorAll('.delBtn');
                checkBoxHandle(checkboxes);
                delBtnsHandle(deleteBtns);
            }).catch(error => console.error(error))
    }

    /**
     * Draw tasks line by line.
     */
    let renderTasks = (data) => {
        document.querySelector('#usersTable tbody').innerHTML = "";
        for (let task in data) {
            let table = document.querySelector('#usersTable');
            let newRow = document.createElement('tr');
            newRow.append(template.content.cloneNode(true));
            let cells = newRow.querySelectorAll('td');
            let date = new Date(data[task].date);
            let checkBox = newRow.querySelector('.task-checkbox');
            checkBox.value = data[task].id;
            checkBox.checked = data[task].completed;
            if (checkBox.checked) {
                newRow.classList.add("task--checked");
            } else {
                newRow.classList.remove("task--checked");
            }
            cells[0].textContent = date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();
            cells[1].textContent = data[task].name;
            let delBtn = newRow.querySelector('.delBtn');
            delBtn.setAttribute("ID", data[task].id);
            let tBody = table.tBodies[0];
            tBody.insertBefore(newRow, tBody.firstElementChild);

        }
    }

    let renderNotCompleted = (data) => {
        document.querySelector('#usersTable tbody').innerHTML = "";
        for (let task in data) {
            if (!data[task].completed) {
                let table = document.querySelector('#usersTable');
                let newRow = document.createElement('tr');
                newRow.append(template.content.cloneNode(true));
                let cells = newRow.querySelectorAll('td');
                let date = new Date(data[task].date);
                let checkBox = newRow.querySelector('.task-checkbox');
                checkBox.value = data[task].id;
                checkBox.checked = data[task].completed;
                if (checkBox.checked) {
                    newRow.classList.add("task--checked");
                } else {
                    newRow.classList.remove("task--checked");
                }
                cells[0].textContent = date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();
                cells[1].textContent = data[task].name;
                let delBtn = newRow.querySelector('.delBtn');
                delBtn.setAttribute("ID", data[task].id);
                let tBody = table.tBodies[0];
                tBody.insertBefore(newRow, tBody.firstElementChild);
            }
        }
    }

    /**
     * Toggle showing Completed/ Not Completed tasks
     * @type {Element}
     */
    let toggler = document.querySelector('#toggle-complete');
    let isToggle = false;
    toggler.addEventListener('click', (e) => {
        isToggle = !isToggle;
        document.querySelector('#usersTable tbody').innerHTML = "";
        fetch('http://localhost:8081/job4j_todo/tasks.do',
            {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json; charset=utf-8'
                }
            }
        ).then(response => response.json())
            .then(data => {
                json = data;
                if (isToggle) {
                    renderNotCompleted(data)
                } else {
                    renderTasks(data);
                }
                let checkboxes = document.querySelectorAll('.task-checkbox');
                const deleteBtns = document.querySelectorAll('.delBtn');
                checkBoxHandle(checkboxes);
                delBtnsHandle(deleteBtns);
            }).catch(error => console.error(error))
    });

</script>
</body>
</html>
