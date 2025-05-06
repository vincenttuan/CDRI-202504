import './App.css'
import React, { useState } from "react";

function Cart() {

  // 預設商品名稱與價格
  const products = [
    {name: "蘋果", price: 50},{name: "香蕉", price: 30},{name: "芒果", price: 60},
    {name: "葡萄", price: 70},{name: "西瓜", price: 90},{name: "柳丁", price: 80},
  ];

  const [name, setName] = useState(products[0].name);
  const [price, setPrice] = useState(products[0].price);
  const [items, setItems] = useState([]);

  const handleAdd = () => {
    const newItem = { name, price: Number(price) };
    //const newItem = { name, price: price*1 };
    setItems([...items, newItem]);
    setName("");
    setPrice("");
  };

  const handleDelete = (index) => {
    // 利用 filter 來過濾不需要的資料
    // i 是指 index, 除非 Item 裡面有另外設計 id (請參考 TodoList 練習)
    setItems(items.filter((item, i) => i !== index));
  }

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

      <h3>總金額: {total}</h3>
    </div>
  );
}

export default Cart;
