<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>My Todo List</title>
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
   <style>
       .form-group {
           display: flex;
           align-items: center;
           margin-bottom: 1rem;
       }
       .form-group label {
           width: 100px;
           margin-right: 1rem;
       }
       .container {
           max-width: 600px;
       }
       .completed {
           text-decoration: line-through;
           color: gray;
       }
   </style>
</head>
<body class="d-flex justify-content-center align-items-center vh-100 bg-light">
   <div class="container text-center">
       <h1 class="mb-4 text-primary">My Todo List</h1>
       <!-- 新增表單 -->
       <form method="post" action="/JavaWebTodoList/todolist/add" class="p-4 bg-white rounded shadow mb-4">
           <fieldset>
               <legend class="text-primary mb-3">新增工作項目</legend>
               <div class="form-group">
                   <label for="text" class="form-label">項目:</label>
                   <input type="text" id="text" name="text" class="form-control" required placeholder="請輸入工作項目" />
               </div>
               <button type="submit" class="btn btn-primary w-100">新增</button>
           </fieldset>
       </form>
       <!-- Todo 列表 -->
       <ul class="list-group">
           <c:forEach var="todo" items="${todos}">
               <li class="list-group-item d-flex justify-content-between align-items-center ${todo.completed ? 'completed' : ''}">
                   <div>
                       <div class="form-group mb-0">
                           <span class="fw-bold">${todo.id}</span> -
                           <input class="form-control" type="text"
                                  value="${fn:escapeXml(todo.text)}"
                                  ondblclick="this.readOnly = !this.readOnly"
                                  onkeydown="updateText(event, ${todo.id}, this)"
                                  readonly title="按我兩下可以編輯，Enter 儲存" />
                       </div>
                       <small class="text-muted">（雙擊可編輯，按 Enter 儲存）</small>
                   </div>
                   <div>
                       <input type="checkbox" ${todo.completed ? "checked" : ""}
                              onchange="location.href='/JavaWebTodoList/todolist/update?id=${todo.id}&checked=' + this.checked;" />
                       已完成
                       <a href="/JavaWebTodoList/todolist/delete?id=${todo.id}"
                          style="display: ${todo.completed ? 'none' : 'inline'}"
                          class="btn btn-danger btn-sm ms-2">刪除</a>
                   </div>
               </li>
           </c:forEach>
       </ul>
   </div>
   <script type="text/javascript">
       const updateText = (event, id, input) => {
           if (event.key === 'Enter') {
               input.readOnly = true;
               const url = '/JavaWebTodoList/todolist/update?id=' + id + '&text=' + encodeURIComponent(input.value);
               location.href = url;
           }
       };
   </script>
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>