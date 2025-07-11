import { useState } from "react";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";

export default function RecoverPassword() {
  const [email, setEmail] = useState("");

  return (
    <div className="card">
      <h2>Recuperar Senha</h2>

      <InputText placeholder="E-mail" value={email} onChange={(e) => setEmail(e.target.value)} />

      <div className="p-mt-3">
        <Button label="Cancelar" severity="secondary" className="p-mr-2" />
        <Button label="Recuperar Senha" />
      </div>
    </div>
  );
}
