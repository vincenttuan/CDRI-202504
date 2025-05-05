// TodoList 練習
import './App.css'
import { useState } from 'react';

function App() {

    //const todos = ['吃早餐', '做體操', '寫程式'];
    const [todos, setTodos] = useState(['吃早餐', '做體操', '寫程式'])
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
                    todos.map((todo, index) => (
                        <li key={index}>{todo}</li>
                    ))
                }
            </ul>
        </>
    )
}

export default App

