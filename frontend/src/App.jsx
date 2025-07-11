import { Routes, Route, Navigate } from 'react-router-dom'

import Login from './pages/login/login.jsx'
import Register from './pages/register/register.jsx'
import ChangePassword from './pages/change-password/ChangePassword.jsx'
import RecoverPassword from './pages/recover-password/RecoverPassword.jsx'

function App() {
  return (
    <div className="app-container">
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/recover-password" element={<RecoverPassword />} />
        <Route path="/change-password" element={<ChangePassword />} />
        <Route path="*" element={<h2>Página não encontrada</h2>} />
      </Routes>
    </div>
  )
}

export default App
