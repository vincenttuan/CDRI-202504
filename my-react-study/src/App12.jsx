// React 組件間參數傳遞
// 子組件
function CircleArea({r}) {
    const pi = 3.1415926;
    const area = r * r * pi;
    return (<div>{area}</div>)
}

// 子組件
const CircleArea2 = ({r}) => {
    const pi = 3.1415926;
    const area = r * r * pi;
    return (<div>{area}</div>)
}

// 父組件
function App() {
    return(
        <>
            <CircleArea r="10" />
            <CircleArea2 r="10" />
        </>
    )
}

export default App;
