function App() {
    let count = 0;

    function handleClick() {
        count++; // 更新 count => 相當於 setCount
        document.getElementById('count').textContent = count; // 資料重新渲染
    }

    return(<>
        <div id='count'>0</div>
        <button onClick={handleClick}>按我一下 count 可以 + 1</button>
    </>)

}

export default App;