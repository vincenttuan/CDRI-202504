// TodoList 練習
import './App.css'
import { useState } from 'react';

function App() {

    const [todos, setTodos] = useState([
        {id:1, text:'吃早餐', completed:false}, 
        {id:2, text:'做體操', completed:true}, 
        {id:3, text:'寫程式', completed:false},
    ])
    const [todo, setTodo] = useState('');

    const handleTodoChange = (e) => {
        setTodo(e.target.value);
    }

    const handleTodoAdd = () => {
        const newId = todos.length > 0 ? Math.max(...todos.map((t)=>t.id)) + 1 : 1;
        const newTodo = {id:newId, text:todo, completed:false};
        setTodos([...todos, newTodo]);
        setTodo(''); // 清空欄位資料
    }

    const toggleCompletion = (id) => {
        setTodos(
            todos.map((todo) => todo.id === id ? {...todo, completed: !todo.completed} : todo)
        )
    }

    const handleTodoDelete = (id) => {
        // 利用 filter 來過濾不需要的資料
        setTodos(todos.filter((todo) => todo.id !== id));
    }

    return (
        <>
            <h1>My TodoList</h1>
            <div>
                <input type='text' value={todo} onChange={handleTodoChange} />
                <button onClick={handleTodoAdd}>加入</button>
            </div>
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
        </>
    )
}

export default App

