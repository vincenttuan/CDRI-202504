import { useState } from "react";

function App() {
    const [count, setCount] = useState(0);
    
    function handleClick() {
        setCount(count + 1);
    }

    return(<>
        <div>{count}</div>
        <button onClick={handleClick}>按我一下 count 可以 + 1</button>
    </>)

}

export default App;
