import './App.css'
import React, { useState } from "react";

function Cart() {
  const [name, setName] = useState("蘋果");
  const [price, setPrice] = useState("50");
  const [items, setItems] = useState([]);

  const handleAdd = () => {
    const newItem = { name, price: Number(price) };
    setItems([...items, newItem]);
    setName("");
    setPrice("");
  };

  const total = items.reduce((sum, item) => sum + item.price, 0);

  return (
    <div>
      <h2>簡易購物車</h2>
      <input
        value={name}
        onChange={(e) => setName(e.target.value)}
        placeholder="商品名稱"
      />
      <br />
      <input
        type="number"
        value={price}
        onChange={(e) => setPrice(e.target.value)}
        placeholder="價格"
      />
      <p />
      <button onClick={handleAdd}>新增</button>

      <h3>購物車內容:</h3>
      <ul>
        {items.length === 0 ? (
          <li>無商品</li>
        ) : (
          items.map((item, index) => (
            <li key={index}>
              {index + 1}. {item.name} - ${item.price}
            </li>
          ))
        )}
      </ul>

      <h3>總金額: {total}</h3>
    </div>
  );
}

export default Cart;
