import React, { useEffect, useState } from "react";
import CategoryService from "../../services/CategoryService";
import { useNavigate } from "react-router-dom";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import "./CategoryList.css";

export default function CategoryList() {
    const categoryService = new CategoryService();
    const [categories, setCategories] = new useState([]);
    const navigate = new useNavigate();

    useEffect(() => {
        const loadCategories = async () => {
            try {
                const response = await categoryService.findAll();
                setCategories(response.data.content);
            } catch (error) {
                console.error("Erro no carregamento de categorias:", error)
            }
        };
        loadCategories();
    }, []);

    const handleCreate = () => {
        navigate("/home/categories/category-form");
    }

    const handleEdit = (id) => {
        navigate(`/home/categories/category-form/${id}`);
    }

    const handleDelete = async (id) => {
        try {
            await categoryService.delete(id);
            setCategories(categories.filter(category => category.id !== id));
        } catch (error) {
            console.error("Erro ao deletar uma categoria:", error);
        }
    }

    const footer = (category) => {
        return(
            <div className="category-footer">
                <div className="category-info">
                    <p>{category.author.name}</p>
                </div>
                <span className="action-container">
                    <Button
                        icon="pi pi-pencil"
                        className="p-button-outlined p-button-secondary"
                        onClick={() => handleEdit(category.id)}
                    />
                    <Button
                        icon="pi pi-trash"
                        className="p-button-outlined p-button-danger"
                        onClick={() => handleDelete(category.id)}
                        style={{ marginLeft: '.5em' }}
                    />
                </span>
            </div>
        );
    }

    return(
        <main className="categorylist-container">
            <div className="categorylist-header">
                <h1>Lista de Categorias</h1>
                <Button
                    label="Criar Categoria"
                    icon="pi pi-plus"
                    onClick={() => handleCreate()}
                />
            </div>
            <div className="cardcategory-container">
                {categories.length > 0 ? (
                    categories.map(category => (
                        <Card 
                            key={category.id}
                            title={category.name}
                            subTitle={category.note}
                            footer={footer(category)}
                            className="p-card-category"
                        />
                    ))
                ) : (
                    <div className="nocategories">
                        <p>No tasks were found</p>
                    </div>
                )}
            </div>
        </main>
    );
}