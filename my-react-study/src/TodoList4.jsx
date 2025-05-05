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
        setTodos([...todos, todo]);
        setTodo(''); // 清空欄位資料
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
                        <li key={todo.id}>
                            {todo.id}
                            <input type="checkbox" checked={todo.completed} />
                            {todo.text}
                        </li>
                    ))
                }
            </ul>
        </>
    )
}

export default App

