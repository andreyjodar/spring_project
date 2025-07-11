import { useState } from "react";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { useNavigate } from "react-router-dom";
import "./login.css";

export default function Login() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Tentativa de Login:", { email, senha });
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
        label="Acessar"
        icon="pi pi-sign-in"
        type="submit"
        className="login-button login-button-primary"
      />
      <Button
        label="Cadastrar-se"
        icon="pi pi-user-plus"
        onClick={() => navigate("/register")}
        className="login-button login-button-secondary"
      />
      <Button
        label="Recuperar Senha"
        icon="pi pi-question-circle"
        onClick={() => navigate("/recover-password")}
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
            />
            <label htmlFor="email" className="login-label">
              E-mail
            </label>
          </div>

          <div className="login-input-group">
            <Password
              id="senha"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              feedback={false}
              toggleMask
              className="login-input"
            />
            <label htmlFor="senha" className="login-label">
              Senha
            </label>
          </div>
        </form>
      </Card>
    </div>
  );
}
