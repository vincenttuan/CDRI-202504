import './App.css'
import React, { useEffect, useState } from 'react';

const API_BASE = 'http://localhost:8081/rest/room'

function App() {
  const [rooms, setRooms] = useState([]);

  useEffect(() => {
    fetchRooms();
  }, [])

  // 查詢所有房間
  const fetchRooms = async () => {
    try {
      // credentials: 'include': 允許將憑證資料上傳, 例如: session id
      const res = await fetch(API_BASE, {credentials: 'include'});
      const data = await res.json();
      console.log('data:', data);
      setRooms(data.data);
    } catch(err) {
      console.log('取得房間資料失敗:', err.message);
      alert('取得房間資料失敗:' + err.message);
    }

  };

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial' }}>
      <h1>房間管理系統</h1>

      <form style={{ marginBottom: '30px' }}>
        <fieldset>
          <legend>新增房間</legend>
          <div>
            <label>房號：</label>
            <input
              type="number"
              name="roomId"
              required
            />
          </div>
          <div>
            <label>名稱：</label>
            <input
              type="text"
              name="roomName"
            />
          </div>
          <div>
            <label>人數：</label>
            <input
              type="number"
              name="roomSize"
              required
            />
          </div>
          <button type="submit">新增房間</button>
          
        </fieldset>
      </form>

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>房號</th>
            <th>名稱</th>
            <th>人數</th>
            <th>編輯</th>
            <th>刪除</th>
          </tr>
        </thead>
        <tbody>
          
        </tbody>
      </table>
    </div>
  );
}

export default App;
