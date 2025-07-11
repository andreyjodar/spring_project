import { useState } from "react";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { useNavigate } from "react-router-dom";
import "./register.css";

export default function Register() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Tentativa de Cadastro:", { nome, email });
  };

  const header = (
    <div className="register-header">
      <h2 className="register-title">Cadastro</h2>
      <p className="register-subtile">Crie sua nova conta</p>
    </div>
  );

  const footer = (
    <div className="register-footer">
      <Button
        label="Cadastrar"
        icon="pi pi-user-plus"
        type="submit"
        className="register-button register-button-primary"
      />
      <Button
        label="Cancelar"
        icon="pi pi-arrow-circle-left"
        onClick={() => navigate(-1)}
        className="register-button register-button-secondary"
      />
    </div>
  );

  return (
    <div className="register-container">
      <Card header={header} footer={footer} className="register-card">
        <form onSubmit={handleSubmit} className="register-form">
          <div className="register-input-group">
            <InputText
              id="name"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              className="register-input"
            />
            <label htmlFor="email" className="register-label">
              Nome
            </label>
          </div>
          <div className="register-input-group">
            <InputText
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="register-input"
            />
            <label htmlFor="email" className="register-label">
              E-mail
            </label>
          </div>
        </form>
      </Card>
    </div>
  );
}
