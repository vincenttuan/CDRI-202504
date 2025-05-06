import './App.css'
import React, { useState, useEffect } from "react";

function Cart() {

  // 商品名稱與價格
  const [products, setProducts] = useState([]);
  const [name, setName] = useState(products.name);
  const [price, setPrice] = useState(products.price);
  const [items, setItems] = useState([]);

  // []: 代表只執行這段 effect 一次
  // [name]: 代表當name改變時就會重新執行這段 effect
  // useEffect(() => {}, [])
  useEffect(() => {
    fetch("http://localhost:3000/products")
      .then(res => res.json())
      .then(data => {
        setProducts(data);
        setName(data[0].name);
        setPrice(data[0].price);
      })
      .catch(err => {
        console.log("載入失敗", err);
      });
  }, [])

  
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

  const handleSelectChange = (e) => {
    const selectedName = e.target.value;
    const selectedProduct = products.find(p => p.name === selectedName);
    setName(selectedProduct.name);
    setPrice(selectedProduct.price);
  }

  const total = items.reduce((sum, item) => sum + item.price, 0);

  const handleCheckout = () => {
    if(items.length === 0) {
      alert('購物車是空的');
      return;
    }

    fetch("http://localhost:3000/orders", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        items,
        total,
        createdAt: new Date().toDateString()
      })
    })
    .then(res => res.json())
    .then(data => {
      alert("結帳成功");
      setItems([]); // 清空購物車
    })
    .catch(err => {
      console.log("結帳失敗:". err);
      alert("結帳失敗");
    });
  }

  return (
    <div>
      <h2>簡易購物車</h2>
      <select onChange={handleSelectChange}>
        {
          products.map((p) => (
            <option key={p.id} value={p.name}>
              {p.name} ${p.price}
            </option>
          ))    
        }
      </select>
      <br />
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

      <button onClick={handleCheckout}>結帳</button>

    </div>
  );
}

export default Cart;
