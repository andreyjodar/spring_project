import { useState, useMemo } from "react";
import AuthService from "../../services/AuthService";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { useNavigate } from "react-router-dom";
import { Toast } from "primereact/toast";
import { useRef } from "react";
import "./ChangePassword.css";

export default function ChangePassword() {
  const authService = useMemo(() => new AuthService(), []);
  const [email, setEmail] = useState("");
  const [code, setCode] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();
  const toast = useRef(null);

  const validatePassword = (pwd) => {
    const rules = {
      length: pwd.length >= 8,
      uppercase: /[A-Z]/.test(pwd),
      lowercase: /[a-z]/.test(pwd),
      number: /\d/.test(pwd),
      special: /[!@#$%^&*(),.?":{}|<>]/.test(pwd),
    };
    return rules;
  };

  const isPasswordValid = (passwordRules) => {
    return Object.values(passwordRules).every(rule => rule === true);
  };

  const handlePasswordChange = (value) => {
    setNewPassword(value);
    const rules = validatePassword(value);
    setErrors((prev) => ({
      ...prev,
      password: rules,
      confirm: value !== confirmPassword && confirmPassword ? "As senhas não coincidem" : "",
    }));
  };

  const handleConfirmPasswordChange = (value) => {
    setConfirmPassword(value);
    setErrors((prev) => ({
      ...prev,
      confirm: value !== newPassword && newPassword ? "As senhas não coincidem" : "",
    }));
  };

  const handleCodeChange = (value) => {
    const alphanumericValue = value.replace(/[^0-9A-Z]/g, '').toUpperCase().slice(0, 6);
    setCode(alphanumericValue);
  };

  const validateForm = () => {
    const newErrors = {};

    if (!email.trim()) {
      newErrors.email = "Email é obrigatório";
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      newErrors.email = "Email inválido";
    }

    if (!code.trim()) {
      newErrors.code = "Código é obrigatório";
    } else if (code.length !== 6) {
      newErrors.code = "Código deve ter exatamente 6 dígitos";
    }

    if (!newPassword) {
      newErrors.newPassword = "Nova senha é obrigatória";
    } else {
      const passwordRules = validatePassword(newPassword);
      if (!isPasswordValid(passwordRules)) {
        newErrors.newPassword = "A senha não atende aos critérios mínimos";
      }
    }

    if (!confirmPassword) {
      newErrors.confirmPassword = "Confirmação de senha é obrigatória";
    } else if (newPassword !== confirmPassword) {
      newErrors.confirmPassword = "As senhas não coincidem";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    setErrorMessage("");

    if (!validateForm()) {
      console.log("Formulário inválido");
      return;
    }

    setLoading(true);

    try {
      console.log("Tentando alterar senha...");

      const changePasswordRequest = {
        email: email.trim(),
        validityCode: code.trim(),
        newPassword: newPassword
      };

      const response = await authService.changePassword(changePasswordRequest);
      console.log("Senha alterada com sucesso:", response);

      if (toast.current) {
        toast.current.show({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Senha alterada com sucesso! Redirecionando para login...',
          life: 3000
        });
      }

      setTimeout(() => {
        navigate("/login");
      }, 2000);

    } catch (error) {
      console.error("Erro ao alterar senha:", error);

      let message = "Não foi possível alterar a senha. Tente novamente.";

      if (error.response) {
        message = error.response.data.message;
      } else if (error.request) {
        message = "Não foi possível conectar ao servidor. Verifique sua conexão.";
      }

      setErrorMessage(message);

      if (toast.current) {
        toast.current.show({
          severity: 'error',
          summary: 'Erro',
          detail: message,
          life: 5000
        });
      }
    } finally {
      setLoading(false);
    }
  };

  const header = (
    <div className="change-password-header">
      <h2 className="change-password-title">Alterar Senha</h2>
      <p className="change-password-subtitle">
        Defina uma nova senha para sua conta
      </p>
    </div>
  );

  const isFormValid = () => {
    const passwordRules = validatePassword(newPassword);
    return (
      email.trim() &&
      code.length === 6 &&
      isPasswordValid(passwordRules) &&
      newPassword === confirmPassword &&
      !loading
    );
  };

  return (
    <div className="change-password-container">
      <Toast ref={toast} />
      
      <Card header={header} className="change-password-card">
        <form onSubmit={handleSubmit} className="change-password-form">
          <div className="change-password-input-group">
            <InputText
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className={`change-password-input ${errors.email ? 'p-invalid' : ''}`}
              disabled={loading}
              required
            />
            <label htmlFor="email" className="change-password-label">
              E-mail
            </label>
            {errors.email && (
              <small className="p-error">{errors.email}</small>
            )}
          </div>

          <div className="change-password-input-group">
            <InputText
              id="code"
              value={code}
              onChange={(e) => handleCodeChange(e.target.value)}
              className={`change-password-input ${errors.code ? 'p-invalid' : ''}`}
              maxLength={6}
              disabled={loading}
              required
            />
            <label htmlFor="code" className="change-password-label">
              Código (6 dígitos)
            </label>
            {errors.code && (
              <small className="p-error">{errors.code}</small>
            )}
          </div>

          <div className="change-password-input-group">
            <Password
              id="newPassword"
              value={newPassword}
              onChange={(e) => handlePasswordChange(e.target.value)}
              feedback={false}
              className={`change-password-input ${errors.newPassword ? 'p-invalid' : ''}`}
              disabled={loading}
              required
            />
            <label htmlFor="newPassword" className="change-password-label">
              Nova Senha
            </label>
            {errors.newPassword && (
              <small className="p-error">{errors.newPassword}</small>
            )}
          </div>

          <div className="change-password-input-group">
            <Password
              id="confirmPassword"
              value={confirmPassword}
              onChange={(e) => handleConfirmPasswordChange(e.target.value)}
              feedback={false}
              className={`change-password-input ${errors.confirmPassword ? 'p-invalid' : ''}`}
              disabled={loading}
              required
            />
            <label htmlFor="confirmPassword" className="change-password-label">
              Confirmar Senha
            </label>
            {(errors.confirm || errors.confirmPassword) && (
              <small className="p-error">{errors.confirm || errors.confirmPassword}</small>
            )}
          </div>

          <ul className="password-requirements">
            <li className={errors.password?.length ? "valid" : ""}>
              <i className={`pi ${errors.password?.length ? "pi-check-circle" : "pi-times-circle"}`} />
              <span>Mínimo 8 caracteres</span>
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

          {errorMessage && (
            <div className="change-password-error">
              <small className="p-error">{errorMessage}</small>
            </div>
          )}

          <div className="change-password-footer">
            <Button
              label={loading ? "Alterando..." : "Alterar Senha"}
              icon={loading ? "pi pi-spin pi-spinner" : "pi pi-lock"}
              type="submit"
              className="change-password-button change-password-button-primary"
              disabled={!isFormValid()}
              loading={loading}
            />
            <Button
              label="Cancelar"
              icon="pi pi-arrow-circle-left"
              type="button"
              onClick={() => navigate(-1)}
              className="change-password-button change-password-button-secondary"
              disabled={loading}
            />
          </div>
        </form>
      </Card>
    </div>
  );
}