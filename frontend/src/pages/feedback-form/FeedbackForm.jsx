import React, { useRef, useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import FeedbackService from "../../services/FeedbackService";
import UserService from "../../services/UserService";
import { Toast } from "primereact/toast";
import { Card } from "primereact/card";
import { InputText } from "primereact/inputtext";
import { AutoComplete } from "primereact/autocomplete";
import { Rating } from "primereact/rating";
import { Button } from "primereact/button";
import "./FeedbackForm.css";

export default function FeedbackForm() {
    const userService = new UserService();
    const feedbackService = new FeedbackService();
    const [grade, setGrade] = useState(1);
    const [comment, setComment] = useState("");
    const [loading, setLoading] = useState(false);
    const [editing, setEditing] = useState(false);
    const [recipient, setRecipient] = useState(null);
    const [allUsers, setAllUsers] = useState([]);
    const [filteredUsers, setFilteredUsers] = useState([]);
    const navigate = useNavigate();
    const { id } = useParams();
    const toast = useRef(null);

    useEffect(() => {
        const loadUsers = async () => {
            try {
                const response = await userService.findAll();
                setAllUsers(response.data.content);
            } catch (error) {
                console.error("Erro ao carregar usuários:", error);
            }
        };

        loadUsers();

        if (id) {
            setEditing(true);
            setLoading(true);
            feedbackService.findById(id)
                .then(response => {
                    const feedback = response.data;
                    setGrade(feedback.grade);
                    setComment(feedback.comment);
                    setRecipient(feedback.recipient);
                })
                .catch(error => {
                    console.error("Erro ao carregar feedback:", error);
                    toast.current.show({
                        severity: 'error',
                        summary: 'Erro',
                        detail: 'Não foi possível carregar os dados do feedback.',
                        life: 3000
                    });
                })
                .finally(() => {
                    setLoading(false);
                });
        }
    }, [id]);

    const handleSearch = (event) => {
        const query = event.query.toLowerCase();
        const filtered = allUsers.filter(user =>
            user.name.toLowerCase().includes(query)
        );
        setFilteredUsers(filtered);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        const feedbackData = {
            grade,
            comment,
            recipientId: recipient?.id,
        };

        try {
            if (editing) {
                await feedbackService.update(id, feedbackData);
                toast.current.show({
                    severity: 'success',
                    summary: 'Sucesso',
                    detail: 'Feedback atualizado com sucesso!',
                    life: 3000
                });
            } else {
                await feedbackService.insert(feedbackData);
                toast.current.show({
                    severity: 'success',
                    summary: 'Sucesso',
                    detail: 'Feedback enviado com sucesso!',
                    life: 3000
                });
            }
            navigate("/home/feedbacks", { state: { refresh: true } });
        } catch (error) {
            console.error("Erro ao salvar feedback:", error);
            const message = error.response?.data?.message || 'Erro ao salvar o feedback.';
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

    const header = () => (
        <div className="feedbackform-header">
            <h2 className="feedbackform-title">
                {editing ? "Editar Feedback" : "Enviar Feedback"}
            </h2>
            <p className="feedbackform-subtitle">
                {editing ? "Altere seu feedback" : "Preencha os dados do feedback"}
            </p>
        </div>
    );

    const handleBack = () => {
        navigate("/home/feedbacks");
    }

    return (
        <main className="feedbackform-container">
            <Toast ref={toast} />
            <Card header={header} className="feedbackform-card">
                <form onSubmit={handleSubmit} className="feedback-form">
                    <div className="feedback-input-group">
                        <AutoComplete
                            dropdown
                            value={recipient}
                            suggestions={filteredUsers}
                            completeMethod={handleSearch}
                            field="name"
                            onChange={(e) => setRecipient(e.value)}
                            className="feedback-input"
                            placeholder="Selecione o Destinatário"
                            required
                            disabled={loading || editing}
                        />
                        <label htmlFor="recipient" className="feedback-label">
                            Destinatário
                        </label>
                    </div>
                    <div className="feedback-input-group">
                        <InputText
                            id="comment"
                            value={comment}
                            onChange={(e) => setComment(e.target.value)}
                            className="feedback-input"
                            required
                            disabled={loading}
                        />
                        <label htmlFor="comment">Comentário</label>
                    </div>
                    <div className="feedback-input-group rating-group">
                        <label className="feedback-label">Nota</label>
                        <Rating
                            value={grade}
                            onChange={(e) => setGrade(e.value)}
                            cancel={false}
                            disabled={loading}
                        />
                    </div>
                    <Button
                        label={editing ? "Salvar" : "Enviar"}
                        icon={editing ? "pi pi-save" : "pi pi-send"}
                        type="submit"
                        className="feedbackform-button feedbackform-button-primary"
                        disabled={loading || !recipient || !comment}
                    />
                    <Button
                        label="Voltar"
                        icon="pi pi-arrow-circle-left"
                        type="button"
                        onClick={handleBack}
                        className="feedbackform-button feedbackform-button-secondary"
                        disabled={loading}
                    />
                </form>
            </Card>
        </main>
    );
}