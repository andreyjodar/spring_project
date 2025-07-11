import { useState } from "react";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { useNavigate } from "react-router-dom";
import { Card } from "primereact/card";
import './RecoverPassword.css'

export default function RecoverPassword() {
  const [email, setEmail] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Tentativa de Recuperar Senha:", { email });
  };

  const header = (
    <div className="recover-password-header">
      <h2 className="recover-password-title">Recuperar Senha</h2>
      <p className="recover-password-subtile">Recupere sua senha</p>
    </div>
  );

  const footer = (
    <div className="recover-password-footer">
      <Button
        label="Recuperar"
        icon="pi pi-lock"
        type="submit"
        className="recover-password-button recover-password-button-primary"
      />
      <Button
        label="Cancelar"
        icon="pi pi-arrow-circle-left"
        onClick={() => navigate(-1)}
        className="recover-password-button recover-password-button-secondary"
      />
    </div>
  );

  return (
    <div className="recover-password-container">
      <Card header={header} footer={footer} className="recover-password-card">
        <form onSubmit={handleSubmit}>
          <div className="recover-password-input-group">
            <InputText
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="recover-password-input"
            />
            <label htmlFor="email" className="recover-password-label">
              E-mail
            </label>
          </div>
        </form>
      </Card>
    </div>
  );
}
