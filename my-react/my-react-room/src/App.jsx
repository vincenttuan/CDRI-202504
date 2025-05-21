import './App.css'
import React, { useEffect, useState } from 'react';

const API_BASE = 'http://localhost:8081/rest/room'

function App() {
  const [rooms, setRooms] = useState([]);
  const [form, setForm] = useState({roomId:'', roomName:'', roomSize:''});
  const [isEditing, setIsEditing] = useState(false); // 用來判斷編輯模式
  
  useEffect(() => {
    console.log('檢查是否已經登入');
    checkLoginStatus();
    console.log('查詢所有房間');
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

  const handleChange = (e) => {
    const {name, value} = e.target;
    setForm(prev => ({...prev, [name]: value}));
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const method = isEditing ? 'PUT' : 'POST';
      const url = isEditing ? `${API_BASE}/${form.roomId}` : API_BASE;

      const res = await fetch(url, {
        method,
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(form)
      })

      if(!res.ok) throw new Error(res);

      const resData = await res.json();
      console.log(resData);
      console.log(resData.status);
      console.log(resData.message);
      if(resData.status == 200) {
        setForm({roomId:'', roomName:'', roomSize:''})
        fetchRooms();
        setIsEditing(false); // 解除編輯模式
      } else {
        alert('表單傳送失敗:' + resData.message);
      }
      
    } catch(err) {
      console.log('表單傳送失敗:', err.message);
      alert('表單傳送失敗:' + err.message);
    }
  }

  // 刪除
  const handleDelete = async (roomId) => {
    if(!window.confirm('確定要刪除嗎?')) return;
    try {
      const method = 'DELETE';
      const url = `${API_BASE}/${roomId}`;
      const res = await fetch(url, {
        method,
        credentials: 'include'
      });

      if(!res.ok) throw new Error(res);

      const resData = await res.json();
      if(resData.status == 200) {
        fetchRooms();
      } else {
        alert('刪除失敗:' + resData.message);
      }

    } catch(err) {
      alert('刪除失敗:' + err.message);
    }
  }

  // 編輯修改
  const handleEdit = (room) => {
    setForm(room);
    setIsEditing(true);
  };

  // -- 登入用 ----------------------------------------------------------------------
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [loginForm, setLoginForm] = useState({ username: '', password: '' });

  // 檢查登入狀態(是否已經登入過?)
  const checkLoginStatus = async () => {
    try {
      const res = await fetch('http://localhost:8081/rest/check-login', {
        method: 'GET',
        credentials: 'include'
      });
      const resData = await res.json();
      setIsLoggedIn(resData.data);
    } catch (err) {
      setIsLoggedIn(false);
    }
  };

  // 登入表單資料狀態改變
  const handleLoginChange = (e) => {
    const { name, value } = e.target;
    // 只變更該欄位資料, 其他欄位仍保持原資料狀態
    setLoginForm(prev => ({ ...prev, [name]: value }));
  };
  
  // 登入
  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch('http://localhost:8081/rest/login', {
        method: 'POST',
        credentials: 'include',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams(loginForm) // 轉換為 username=xxx&password=xxx
      });

      const resData = await res.json();
      if (res.ok && resData.status === 200) {
        alert('登入成功');
        setIsLoggedIn(true);
      } else {
        alert('登入失敗：' + resData.message);
      }
    } catch (err) {
      alert('登入錯誤: ' + err.message);
    }
  };

  // 登出
  const handleLogoutSubmit = async () => {
    try {
      const res = await fetch('http://localhost:8081/rest/logout', {
        method: 'GET',
        credentials: 'include'
      });

      const resData = await res.json();
      if (res.ok && resData.status === 200) {
        alert(resData.message);
        setIsLoggedIn(false);
      } else {
        alert('登出失敗：' + resData.message);
      }
    } catch (err) {
      alert('登出錯誤: ' + err.message);
    }
  };

  // ------------------------------------------------------------------

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial' }}>
      <h1>房間管理系統</h1>
      {!isLoggedIn && (
        <form onSubmit={handleLoginSubmit} style={{ marginBottom: '20px' }}>
          <fieldset>
            <legend>登入系統</legend>
            <div>
              <label>帳號：</label>
              <input
                type="text"
                name="username"
                value={loginForm.username}
                onChange={handleLoginChange}
                required
              />
            </div>
            <div>
              <label>密碼：</label>
              <input
                type="password"
                name="password"
                value={loginForm.password}
                onChange={handleLoginChange}
                required
              />
            </div>
            <button type="submit">登入</button>
          </fieldset>
        </form>
      )}

      {isLoggedIn && (
        <form onSubmit={handleSubmit} style={{ marginBottom: '30px' }}>
          <fieldset>
            <legend>{isEditing ? '編輯房間' : '新增房間'}</legend>
            <div>
              <label>房號：</label>
              <input
                type="number"
                name="roomId"
                value={form.roomId}
                onChange={handleChange}
                required
              />
            </div>
            <div>
              <label>房名：</label>
              <input
                type="text"
                name="roomName"
                value={form.roomName}
                onChange={handleChange}
                required
              />
            </div>
            <div>
              <label>人數：</label>
              <input
                type="number"
                name="roomSize"
                value={form.roomSize}
                onChange={handleChange}
                required
              />
            </div>
            <button type="submit">{isEditing ? '更新房間' : '新增房間'}</button>
            {isEditing && (
              <button
                type="button"
                onClick={() => {
                  setForm({ roomId: '', roomName: '', roomSize: '' });
                  setIsEditing(false);
                }}
              >
                取消編輯
              </button>
            )}
            <button type="button" onClick={handleLogoutSubmit}>登出</button>
          </fieldset>
        </form>
      )}

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>房號</th>
            <th>房名</th>
            <th>人數</th>
            <th>編輯</th>
            <th>刪除</th>
          </tr>
        </thead>
        <tbody>
          {
            rooms.map(room => {
              return (
                <tr key={room.roomId}>
                  <td>{room.roomId}</td>
                  <td>{room.roomName}</td>
                  <td>{room.roomSize}</td>
                  <td>
                    {isLoggedIn && (
                      <button onClick={() => handleEdit(room)}>編輯</button>
                    )}
                  </td>
                  <td>
                    {isLoggedIn && (
                      <button onClick={() => {handleDelete(room.roomId)}}>刪除</button>
                    )}
                  </td>
                </tr>
              )
            })
          }

          {
            rooms.length === 0 && (
              <tr>
                <td colSpan="5">無資料</td>
              </tr>
            )
          }
        </tbody>
      </table>
    </div>
  );
}

export default App;
