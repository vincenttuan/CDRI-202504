import './App.css'
import React, { useEffect, useState } from 'react';

const API_URL = 'http://localhost:8080/book';

function App() {

  const [books, setBooks] = useState([]);
  const [form, setForm] = useState({ id: null, name: '', price: '', amount: '', pub: false });
  const [editing, setEditing] = useState(false);

  // 讀取書籍資料
  const fetchBooks = async () => {
    try {
      const res = await fetch(API_URL);
      const result = await res.json();
      setBooks(result.data || []);
    } catch (error) {
      console.error('讀取書籍錯誤:', error);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  // 處理表單變更
  /*
  [name]: type === 'checkbox' ? checked : value
  [name]	動態欄位名稱，會變成 name: xxx 或 price: xxx 等。
  type === 'checkbox'	判斷這個 <input> 是否是 checkbox
  ? checked : value	是 checkbox 時使用 .checked 布林值，否則使用 .value 輸入字串或數字
  */
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  // 新增或更新
  const handleSubmit = async (e) => {
    e.preventDefault(); // 停止預設行為
    try {
      const method = editing ? 'PUT' : 'POST';
      const url = editing ? `${API_URL}/${form.id}` : API_URL;
      const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });
      const result = await res.json();
      if (res.ok) {
        await fetchBooks();
        setForm({ id: null, name: '', price: '', amount: '', pub: false });
        setEditing(false);
      } else {
        alert(result.message || '操作失敗');
      }
    } catch (err) {
      console.error('提交錯誤:', err);
    }
  };

  // 編輯功能
  const handleEdit = (book) => {
    setForm(book);
    setEditing(true);
  };

  // 刪除功能
  const handleDelete = async (id) => {
    if (!window.confirm('確定要刪除這本書嗎？')) return;
    try {
      const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
      const result = await res.json();
      if (res.ok) {
        fetchBooks();
      } else {
        alert(result.message || '刪除失敗');
      }
    } catch (err) {
      console.error('刪除錯誤:', err);
    }
  };

  return (
    <div>
      <h2>📚 書籍管理系統(使用 fetch)</h2>
      <form onSubmit={handleSubmit} style={{ marginBottom: 20 }}>
        書名：<input name="name" value={form.name} onChange={handleChange} required /><p />
        價格：<input type="number" name="price" value={form.price} onChange={handleChange} required /><p />
        數量：<input type="number" name="amount" value={form.amount} onChange={handleChange} required /><p />
        出刊：<input type="checkbox" name="pub" checked={form.pub} onChange={handleChange} /><p />
        <button type="submit">{editing ? '更新' : '新增'} 書籍</button>
        {
          editing && (
            <button type="button" onClick={() => {
              setEditing(false);
              setForm({ id: null, name: '', price: '', amount: '', pub: false });
            }}>取消</button>
          )
        }
      </form>
      <h2>📖 書籍列表</h2>
      <table border="1" cellPadding="4">
        <thead>
          <tr>
          <th>ID</th>
          <th>書名</th>
          <th>價格</th>
          <th>數量</th>
          <th>出刊</th>
          <th>操作</th>
          </tr>
        </thead>
        <tbody>
          {
            books.map((book) => (
              <tr key={book.id}>
                <td>{book.id}</td>
                <td>{book.name}</td>
                <td>{book.price}</td>
                <td>{book.amount}</td>
                <td>{book.pub ? '是' : '否'}</td>
                <td>
                  <button onClick={() => handleEdit(book)}>編輯</button>
                  <button onClick={() => handleDelete(book.id)}>刪除</button>
                </td>
              </tr>
            ))
          }
        </tbody>
      </table>
    </div>
  )
}

export default App
