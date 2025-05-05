// TodoList 練習
import './App.css'

function App() {
    return (
        <>
            <h1>My TodoList</h1>
            <div>
                <input />
                <button>加入</button>
            </div>
            <ul>
                <li style={{textDecoration: 'line-through'}}><input type="checkbox" checked />吃早餐</li>
                <li><input type="checkbox" />做體操</li>
                <li><input type="checkbox" />寫程式</li>
            </ul>
        </>
    )
}

export default App

