import TodoItem from "./TodoItem";

function TodoList({todos, toggleCompletion, handleTodoDelete}) {
    return(
        <ul>
            {
                todos.map((todo) => (
                    <TodoItem todo={todo} 
                              toggleCompletion={toggleCompletion} 
                              handleTodoDelete={handleTodoDelete} />
                ))
            }
        </ul>
    )
}

export default TodoList;