/* TodoList 拆分模組練習
src/
├── components/
│   ├── TodoInput.jsx
│   ├── TodoItem.jsx
│   └── TodoList.jsx
├── TodoList5.jsx
├── App.css
*/
import './App.css'
import { useState } from 'react';
import TodoInput from './components/TodoInput';
import TodoList from './components/TodoList';

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
            
            <TodoInput todo={todo} 
                       handleTodoChange={handleTodoChange} 
                       handleTodoAdd={handleTodoAdd} />

            <TodoList todos={todos} 
                      toggleCompletion={toggleCompletion} 
                      handleTodoDelete={handleTodoDelete} />
            
        </>
    )
}

export default App

