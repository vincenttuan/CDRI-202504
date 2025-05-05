// useState 與事件處理 + 展開運算子...
import { useState } from "react"

function App() {
    const [inputMessage, setInputMessage] = useState('');
    const [messages, setMessages] = useState([]);
    
    const handleInputChange = (e) => {
        // e.target.value 是 input 欄位的內容
        console.log(e.target.value)
        // 若使用者輸入的是 enter 則自動呼叫 handleAddMessage()
        
        setInputMessage(e.target.value);

    }

    const handleAddMessage = () => {
        //setMessages(messages.concat(inputMessage));
        setMessages([...messages, inputMessage]);
    }

    return(<>
        <input type='text' value={inputMessage} onChange={handleInputChange} />
        <button onClick={handleAddMessage}>Send</button><p />
        {messages}
        <p />
        <ul>
            {
                messages.map((message, index) => (
                    <li key={index}>{index}: {message}</li>
                ))
            }
        </ul>
    </>)

}

export default App;
