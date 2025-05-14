function TodoInput({todo, handleTodoChange, handleTodoAdd}) {

    return (
        <div>
            <input type='text' value={todo} onChange={handleTodoChange} />
            <button onClick={handleTodoAdd}>加入</button>
        </div>
    )

}

export default TodoInput;
