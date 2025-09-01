import { Card } from "primereact/card";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { Toast } from "primereact/toast";
import React, { useState, useEffect, useRef } from "react";
import { useNavigate, useParams } from "react-router-dom";
import CategoryService from "../../services/CategoryService";
import "./CategoryForm.css";

export default function CategoryForm() {
    const categoryService = new CategoryService();
    const [categoryId, setCategoryId] = useState(null);
    const [name, setName] = useState("");
    const [note, setNote] = useState("");
    const [loading, setLoading] = useState(false);
    const [editing, setEditing] = useState(false);
    const navigate = useNavigate();
    const { id } = useParams();
    const toast = useRef(null);

    useEffect(() => {
        if (id) {
            setEditing(true);
            setLoading(true);
            categoryService.findById(id)
                .then(response => {
                    const category = response.data;
                    setCategoryId(category.id);
                    setName(category.name);
                    setNote(category.note);
                })
                .catch(error => {
                    console.error("Erro ao carregar categoria:", error);
                    toast.current.show({
                        severity: 'error',
                        summary: 'Erro',
                        detail: 'Não foi possível carregar os dados da categoria.',
                        life: 3000
                    });
                })
                .finally(() => {
                    setLoading(false);
                });
        }
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        const categoryData = {
            name,
            note,
        };

        try {
            if (editing) {
                await categoryService.update(categoryId, categoryData);
                toast.current.show({
                    severity: 'success',
                    summary: 'Sucesso',
                    detail: 'Categoria atualizada com sucesso!',
                    life: 3000
                });
            } else {
                await categoryService.insert(categoryData);
                toast.current.show({
                    severity: 'success',
                    summary: 'Sucesso',
                    detail: 'Categoria criada com sucesso!',
                    life: 3000
                });
            }
            navigate("/home/categories");
        } catch (error) {
            console.error("Erro ao salvar categoria:", error);
            const message = error.response?.data?.message || 'Erro ao salvar a categoria.';
            toast.current.show({
                severity: 'error',
                summary: 'Erro',
                detail: message,
                life: 5000
            });
        } finally {
            setLoading(false);
        }
    }

    const handleBack = () => {
        navigate("/home/categories");
    }

    const header = () => (
        <div className="cetegoryform-header">
            <h2 className="categoryform-title">
                {editing ? "Editar Categoria" : "Criar Categoria"}
            </h2>
            <p className="categoryform-subtitle">
                {editing ? "Altere os dados da categoria" : "Preencha os dados da nova categoria"}
            </p>
        </div>
    );

    return (
        <main className="categoryform-container">
            <Toast ref={toast} />
            <Card header={header} className="categoryform-card">
                <form onSubmit={handleSubmit} className="category-form">
                    <div className="category-input-group">
                        <InputText
                            id="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            className="category-input"
                            required
                            disabled={loading}
                        />
                        <label htmlFor="name" className="category-label">
                            Nome
                        </label>
                    </div>
                    <div className="category-input-group">
                        <InputText
                            id="note"
                            value={note}
                            onChange={(e) => setNote(e.target.value)}
                            className="category-input"
                            required
                            disabled={loading}
                        />
                        <label htmlFor="note" className="category-label">
                            Descrição
                        </label>
                    </div>
                    <Button
                        label={editing ? "Salvar" : "Criar"}
                        icon={editing ? "pi pi-save" : "pi pi-plus"}
                        type="submit"
                        className="categoryform-button categoryform-button-primary"
                        disabled={loading || !name || !note}
                    />
                    <Button
                        label="Voltar"
                        icon="pi pi-arrow-circle-left"
                        type="button"
                        onClick={handleBack}
                        className="categoryform-button categoryform-button-secondary"
                        disabled={loading}
                    />
                </form>
            </Card>
        </main>
    );
}