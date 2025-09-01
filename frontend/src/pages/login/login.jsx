import { useState } from "react";
import AuthService from "../../services/AuthService";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { useNavigate } from "react-router-dom";
import "./login.css";

export default function Login() {
  const authService = new AuthService();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setErrorMessage(null);

    console.log("Base URL:", process.env.REACT_APP_API_BASE_URL);
    console.log("AuthService endpoint:", authService.endPoint);
    console.log("URL completa seria:", `${process.env.REACT_APP_API_BASE_URL}/auth/login`);

    try {
      const response = await authService.login({ email, password });
      
      if (response && response.data?.accessToken) {
        navigate("/home"); 
      }
    } catch (error) {
      console.error("Erro no login:", error);
      setErrorMessage(
        error.response?.data?.message || "Não foi possível fazer login."
      );
    } finally {
      setLoading(false);
    }
  };

  const header = (
    <div className="login-header">
      <h2 className="login-title">Login</h2>
      <p className="login-subtitle">Entre na sua conta</p>
    </div>
  );

  const footer = (
    <div className="login-footer">
      <Button
        label="Recuperar Senha"
        icon="pi pi-question-circle"
        type="button"
        onClick={() => navigate("/forgot-password")}
        className="login-button login-button-help"
      />
    </div>
  );

  return (
    <div className="login-container">
      <Card header={header} footer={footer} className="login-card">
        <form onSubmit={handleSubmit} className="login-form">
          <div className="login-input-group">
            <InputText
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="login-input"
              required
            />
            <label htmlFor="email" className="login-label">
              E-mail
            </label>
          </div>

          <div className="login-input-group">
            <Password
              id="senha"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              feedback={false}
              className="login-input"
              required
            />
            <label htmlFor="senha" className="login-label">
              Senha
            </label>
          </div>

          {errorMessage && (
            <small className="p-error login-error">{errorMessage}</small>
          )}
          <Button
            label={loading ? "Entrando..." : "Acessar"}
            icon="pi pi-sign-in"
            type="submit"
            className="login-button login-button-primary"
            disabled={loading}
          />
          <Button
            label="Cadastrar-se"
            icon="pi pi-user-plus"
            type="button"
            onClick={() => navigate("/register")}
            className="login-button login-button-secondary"
          />
        </form>
      </Card>
    </div>
  );
}