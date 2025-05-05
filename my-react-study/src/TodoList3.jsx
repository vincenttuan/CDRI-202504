// TodoList 練習
import './App.css'

function App() {

    const todos = ['吃早餐', '做體操', '寫程式'];

    return (
        <>
            <h1>My TodoList</h1>
            <div>
                <input />
                <button>加入</button>
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

