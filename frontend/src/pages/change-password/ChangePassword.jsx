import { useState } from "react";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { Button } from "primereact/button";

export default function ChangePassword() {
  const [email, setEmail] = useState("");
  const [codigo, setCodigo] = useState("");
  const [novaSenha, setNovaSenha] = useState("");
  const [confirmarSenha, setConfirmarSenha] = useState("");

  const validarSenha = (senha) => {
    const erros = [];
    if (senha.length < 6) erros.push("Mínimo 6 caracteres");
    if (!/[A-Z]/.test(senha)) erros.push("1 letra maiúscula");
    if (!/[a-z]/.test(senha)) erros.push("1 letra minúscula");
    if (!/[0-9]/.test(senha)) erros.push("1 número");
    if (!/[!@#$%^&*]/.test(senha)) erros.push("1 caractere especial");
    return erros;
  };

  const errosSenha = validarSenha(novaSenha);

  return (
    <div className="card">
      <h2>Alterar Senha</h2>

      <InputText placeholder="E-mail" value={email} onChange={(e) => setEmail(e.target.value)} />
      <InputText placeholder="Código" value={codigo} onChange={(e) => setCodigo(e.target.value)} />
      <Password placeholder="Nova Senha" feedback={false} toggleMask value={novaSenha} onChange={(e) => setNovaSenha(e.target.value)} />
      <Password placeholder="Confirmar Senha" feedback={false} toggleMask value={confirmarSenha} onChange={(e) => setConfirmarSenha(e.target.value)} />

      <div className="p-error">
        {errosSenha.map((erro, i) => (
          <div key={i}>{erro}</div>
        ))}
        {novaSenha !== confirmarSenha && <div>As senhas não coincidem</div>}
      </div>

      <div className="p-mt-3">
        <Button label="Cancelar" severity="secondary" className="p-mr-2" />
        <Button label="Alterar Senha" disabled={errosSenha.length > 0 || novaSenha !== confirmarSenha} />
      </div>
    </div>
  );
}
