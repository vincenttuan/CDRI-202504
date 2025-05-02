// 陣列

function App() {
    // 陣列
    const items1 = ['Apple', 'Banana', 'Orange']
    // 利用 JSX 來渲染陣列
    // key 是幫助 React 來識別每個元素的唯一性
    const items2 = [
        <li key='1'>Apple</li>,
        <li key='2'>Banana</li>,
        <li key='3'>Orange</li>
    ]

    return (
        <>
            {items1}
            {items2}
        </>
    )
}

export default App