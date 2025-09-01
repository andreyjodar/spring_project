import { useState, useRef } from "react";
import AuthService from "../../services/AuthService";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { useNavigate } from "react-router-dom";
import { Password } from "primereact/password";
import { Dropdown } from "primereact/dropdown";
import { Toast } from "primereact/toast";
import "./Register.css";

export default function Register() {
  const authService = new AuthService();
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [selectedRole, setSelectedRole] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const toast = useRef(null);

  const roleMapping = {
    BUYER: ["BUYER"],
    SELLER: ["BUYER", "SELLER"],
    ADMIN: ["BUYER", "SELLER", "ADMIN"],
  };

  const roleOptions = [
    { label: "Comprador", value: "BUYER" },
    { label: "Vendedor", value: "SELLER" },
    { label: "Administrador", value: "ADMIN" },
  ];

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const rolesToSend = roleMapping[selectedRole];
      if (!rolesToSend) {
        throw new Error("Selecione uma role válida.");
      }

      const registerRequest = {
        name: name,
        email: email,
        password: password,
        roles: rolesToSend,
      };

      const response = await authService.register(registerRequest);

      if (response && response.data.accessToken) {
        toast.current.show({
          severity: "success",
          summary: "Sucesso",
          detail: "Usuário cadastrado com sucesso! Redirecionando...",
          life: 3000,
        });

        setTimeout(() => {
          navigate("/home");
        }, 2000);
      } else {
        throw new Error("Resposta inesperada do servidor após o registro.");
      }
    } catch (error) {
      console.error("Erro no cadastro:", error);
      const errorMessage =
        error.response?.data?.message ||
        "Não foi possível cadastrar o usuário. Tente novamente.";
      toast.current.show({
        severity: "error",
        summary: "Erro",
        detail: errorMessage,
        life: 5000,
      });
    } finally {
      setLoading(false);
    }
  };

  const header = (
    <div className="register-header">
      <h2 className="register-title">Cadastro</h2>
      <p className="register-subtile">Crie sua nova conta</p>
    </div>
  );

  return (
    <div className="register-container">
      <Toast ref={toast} />
      <Card header={header} className="register-card">
        <form onSubmit={handleSubmit} className="register-form">
          <div className="register-input-group">
            <InputText
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              className="register-input"
              required
            />
            <label htmlFor="name" className="register-label">
              Nome
            </label>
          </div>
          <div className="register-input-group">
            <InputText
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="register-input"
              required
            />
            <label htmlFor="email" className="register-label">
              E-mail
            </label>
          </div>
          <div className="register-input-group">
            <Password
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="register-input"
              required
            />
            <label htmlFor="password" className="register-label">
              Senha
            </label>
          </div>
          <div className="register-input-group">
            <Dropdown
              id="role"
              value={selectedRole}
              onChange={(e) => setSelectedRole(e.value)}
              options={roleOptions}
              placeholder="Selecione uma Role"
              className="register-input"
              required
            />
            <label htmlFor="role" className="register-label">
              Função
            </label>
          </div>
          <div className="register-footer">
            <Button
              label={loading ? "Cadastrando..." : "Cadastrar"}
              icon={loading ? "pi pi-spin pi-spinner" : "pi pi-user-plus"}
              type="submit"
              className="register-button register-button-primary"
              disabled={loading || !selectedRole}
            />
            <Button
              label="Cancelar"
              icon="pi pi-arrow-circle-left"
              onClick={() => navigate(-1)}
              className="register-button register-button-secondary"
              disabled={loading}
            />
          </div>
        </form>
      </Card>
    </div>
  );
}