import { useState } from "react";
import AuthService from "../../services/AuthService";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { useNavigate } from "react-router-dom";
import { Card } from "primereact/card";
import './ForgotPassword.css'

export default function RecoverPassword() {
  const authService = new AuthService();
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const response = await authService.forgotPassword({ email });
      console.log("Resposta do backend:", response.data);

      navigate("/change-password");
    } catch (err) {
      console.error("Erro ao recuperar senha:", err);
      setError("Não foi possível enviar o e-mail de recuperação.");
    } finally {
      setLoading(false);
    }
  };

  const header = (
    <div className="recover-password-header">
      <h2 className="recover-password-title">Insira seu Email</h2>
      <p className="recover-password-subtile">Um código de recuperação será enviado</p>
    </div>
  );

  return (
    <div className="recover-password-container">
      <Card header={header} className="recover-password-card">
        <form onSubmit={handleSubmit}>
          <div className="recover-password-input-group">
            <InputText
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="recover-password-input"
              required
            />
            <label htmlFor="email" className="recover-password-label">
              E-mail
            </label>
          </div>

          {error && <p className="recover-password-error">{error}</p>}

          <div className="recover-password-footer">
            <Button
              label={loading ? "Enviando..." : "Recuperar"}
              icon="pi pi-lock"
              type="submit"
              className="recover-password-button recover-password-button-primary"
              disabled={loading}
            />
            <Button
              label="Cancelar"
              icon="pi pi-arrow-circle-left"
              onClick={() => navigate(-1)}
              className="recover-password-button recover-password-button-secondary"
              disabled={loading}
            />
          </div>
        </form>
      </Card>
    </div>
  );
}