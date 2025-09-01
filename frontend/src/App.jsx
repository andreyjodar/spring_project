import { Routes, Route, Navigate } from 'react-router-dom'

import Login from './pages/login/login.jsx'
import Register from './pages/register/Register.jsx'
import ChangePassword from './pages/change-password/ChangePassword.jsx'
import ForgotPassword from './pages/forgot-password/ForgotPassword.jsx'
import LayoutPattern from './components/layout/LayoutPattern.jsx'

import 'primereact/resources/themes/lara-light-indigo/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import PrivateRouteLayout from './components/layout/PrivateRouteLayout.jsx'
import Home from './pages/home/home.jsx'
import CategoryForm from './pages/category-form/CategoryForm.jsx'
import CategoryList from './pages/category-list/CategoryList.jsx'
import FeedbackList from './pages/feedback-list/FeedbackList.jsx'
import FeedbackForm from './pages/feedback-form/FeedbackForm.jsx'

function App() {
  return (
    <div className="app-container">
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/change-password" element={<ChangePassword />} />
        <Route element={<PrivateRouteLayout />}>
          <Route path="/home" element={<LayoutPattern>
            <Home />
          </LayoutPattern>} />
          <Route path="/home/categories" element={<LayoutPattern>
            <CategoryList />
          </LayoutPattern>} />    
          <Route path="/home/categories/category-form" element={<LayoutPattern>
            <CategoryForm />
          </LayoutPattern>} />
          <Route path="/home/categories/category-form/:id" element={<LayoutPattern>
            <CategoryForm />
          </LayoutPattern>} />      
          <Route path="/home/feedbacks" element={<LayoutPattern>
            <FeedbackList />
          </LayoutPattern>} />
          <Route path="/home/feedbacks/feedback-form" element={<LayoutPattern>
            <FeedbackForm />
          </LayoutPattern>} />
          <Route path="/home/feedbacks/feedback-form/:id" element={<LayoutPattern>
            <FeedbackForm />
          </LayoutPattern>} />
        </Route>
      </Routes>
    </div>
  )
}

export default App;
