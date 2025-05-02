// 陣列

function App() {
    // 陣列
    const items1 = ['Apple', 'Banana', 'Orange']
    // 利用 JSX 來渲染陣列
    // key 是幫助 React 來識別每個元素的唯一性
    const items2 = [
        <li key='0'>0-Apple</li>,
        <li key='1'>1-Banana</li>,
        <li key='2'>2-Orange</li>
    ]
    // 使用 map 來渲染 items1
    const items3 = items1.map((item, index) => (
        <li key={index}>{index}-{item}</li>
    ))

    return (
        <>
            {items1}
            {items2}
            {items3}
        </>
    )
}

export default App