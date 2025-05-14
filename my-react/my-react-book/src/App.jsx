import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  return (
    <div>
      <h2>書籍管理系統(使用 fetch)</h2>
      <form>
        id: <input name="id" readOnly /><p />
        書名: <input name="name" required /><p />
        價格: <input name="price" required /><p />
        數量: <input name="amount" required /><p />
        出刊: <input name="pub" type="checkbox" /><p />
        <button type="submit">新增</button>
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
          <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>
              <button type='button'>編輯</button>
              <button type='button'>刪除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

  )
  
}

export default App
