// 變數應用
function App() {
    const text = 'Hello React';
    return (
        <>
            <h1>{text}</h1>
            <h1>{text} {text}</h1>
            <h1>{text.toUpperCase()}</h1>
            <h1 style={{backgroundColor: 'red'}}>{text}</h1>
            <form>
                Text: <input type="text" value={text} />
            </form>
        </>
    )
}

export default App