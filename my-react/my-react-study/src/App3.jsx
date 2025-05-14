// 建立組件有二種寫法 
// 1. function
// 2. () => 函數式

// 子組件
function Hello() {
    return (
        <h1>Hello</h1>
    )
}

// 子組件
const World = () => {
    return (
        <h1>World</h1>
    )
}

// 父組件
function App() {
    return (
        <>
            <Hello />
            <World />
        </>
    )
}

export default App
