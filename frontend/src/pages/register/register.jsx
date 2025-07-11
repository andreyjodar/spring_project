import { useState } from "react";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";

export default function Register() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");

  return (
    <div className="card">
      <h2>Novo Cadastro</h2>

      <div className="p-fluid">
        <InputText placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} />
        <InputText placeholder="E-mail" value={email} onChange={(e) => setEmail(e.target.value)} />
      </div>

      <div className="p-mt-3">
        <Button label="Cancelar" severity="secondary" className="p-mr-2" />
        <Button label="Cadastrar-se" />
      </div>
    </div>
  );
}
