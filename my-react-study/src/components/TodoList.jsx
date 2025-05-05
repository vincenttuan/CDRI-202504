function TodoList({todos, toggleCompletion, handleTodoDelete}) {
    return(
        <ul>
            {
                todos.map((todo) => (
                    <li key={todo.id} style={{textDecoration: todo.completed ? 'line-through': 'none'}}>
                        {todo.id}

                        <input type="checkbox" 
                                onChange={() => toggleCompletion(todo.id)} 
                                checked={todo.completed} />

                        {todo.text}

                        <button onClick={() => handleTodoDelete(todo.id)}>X</button>
                    </li>
                ))
            }
        </ul>
    )
}

export default TodoList;