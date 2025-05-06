function CartList({items, handleDelete}) {

    return(
        <>
            <h3>購物車內容:</h3>
            <ul>
                {
                    items.length === 0 ? 
                    (
                        <li>無商品</li>
                    )
                    : 
                    (
                        items.map((item, index) => (
                            <li key={index}>
                                {index + 1}. {item.name} - ${item.price}
                                <button onClick={() => handleDelete(index)}>X</button>
                            </li>
                        ))
                    )
                }
            </ul>
        </>
    )

}

export default CartList;