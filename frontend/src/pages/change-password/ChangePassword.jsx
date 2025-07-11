import { useState } from "react";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { useNavigate } from "react-router-dom";
import "./ChangePassword.css";

export default function ChangePassword() {
  const [email, setEmail] = useState("");
  const [code, setCode] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const validatePassword = (pwd) => {
    const rules = {
      length: pwd.length >= 6,
      uppercase: /[A-Z]/.test(pwd),
      lowercase: /[a-z]/.test(pwd),
      number: /\d/.test(pwd),
      special: /[!@#$%^&*(),.?":{}|<>]/.test(pwd),
    };
    return rules;
  };

  const handlePasswordChange = (value) => {
    setNewPassword(value);
    const rules = validatePassword(value);
    setErrors((prev) => ({
      ...prev,
      password: rules,
      confirm: value !== confirmPassword ? "As senhas não coincidem" : "",
    }));
  };

  const handleConfirmPasswordChange = (value) => {
    setConfirmPassword(value);
    setErrors((prev) => ({
      ...prev,
      confirm: value !== newPassword ? "As senhas não coincidem" : "",
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Alterar Senha:", { email, code, newPassword });
    // aqui você pode enviar os dados para a API
  };

  const header = (
    <div className="change-password-header">
      <h2 className="change-password-title">Alterar Senha</h2>
      <p className="change-password-subtitle">
        Defina uma nova senha para sua conta
      </p>
    </div>
  );

  const footer = (
    <div className="change-password-footer">
      <Button
        label="Alterar Senha"
        icon="pi pi-lock"
        type="submit"
        className="change-password-button change-password-button-primary"
      />
      <Button
        label="Cancelar"
        icon="pi pi-arrow-circle-left"
        onClick={() => navigate(-1)}
        className="change-password-button change-password-button-secondary"
      />
    </div>
  );

  return (
    <div className="change-password-container">
      <Card header={header} footer={footer} className="change-password-card">
        <form onSubmit={handleSubmit} className="change-password-form">
          <div className="change-password-input-group">
            <InputText
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="change-password-input"
            />
            <label htmlFor="email" className="change-password-label">
              E-mail
            </label>
          </div>

          <div className="change-password-input-group">
            <InputText
              id="code"
              value={code}
              onChange={(e) => setCode(e.target.value)}
              className="change-password-input"
            />
            <label htmlFor="code" className="change-password-label">
              Código
            </label>
          </div>

          <div className="change-password-input-group">
            <Password
              id="newPassword"
              value={newPassword}
              onChange={(e) => handlePasswordChange(e.target.value)}
              feedback={false}
              className="change-password-input"
            />
            <label htmlFor="newPassword" className="change-password-label">
              Nova Senha
            </label>
          </div>

          <div className="change-password-input-group">
            <Password
              id="confirmPassword"
              value={confirmPassword}
              onChange={(e) => handleConfirmPasswordChange(e.target.value)}
              feedback={false}
              className="change-password-input"
            />
            <label htmlFor="confirmPassword" className="change-password-label">
              Confirmar Senha
            </label>
            {errors.confirm && (
              <small className="error-message">{errors.confirm}</small>
            )}
          </div>

          <ul className="password-requirements">
            <li className={errors.password?.length ? "valid" : ""}>
              <i className={`pi ${errors.password?.length ? "pi-check-circle" : "pi-times-circle"}`} />
              <span>Mínimo 6 caracteres</span>
            </li>
            <li className={errors.password?.uppercase ? "valid" : ""}>
              <i className={`pi ${errors.password?.uppercase ? "pi-check-circle" : "pi-times-circle"}`} />
              <span>1 letra maiúscula</span>
            </li>
            <li className={errors.password?.lowercase ? "valid" : ""}>
              <i className={`pi ${errors.password?.lowercase ? "pi-check-circle" : "pi-times-circle"}`} />
              <span>1 letra minúscula</span>
            </li>
            <li className={errors.password?.number ? "valid" : ""}>
              <i className={`pi ${errors.password?.number ? "pi-check-circle" : "pi-times-circle"}`} />
              <span>1 número</span>
            </li>
            <li className={errors.password?.special ? "valid" : ""}>
              <i className={`pi ${errors.password?.special ? "pi-check-circle" : "pi-times-circle"}`} />
              <span>1 caractere especial</span>
            </li>
          </ul>
        </form>
      </Card>
    </div>
  );
}
