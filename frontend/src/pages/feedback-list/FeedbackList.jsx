import React, { useState, useEffect, useRef } from "react";
import FeedbackService from "../../services/FeedbackService";
import { useNavigate, useLocation } from "react-router-dom";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { Toast } from "primereact/toast";
import "./FeedbackList.css";

export default function FeedbackList() {
    const feedbackService = new FeedbackService();
    const [receivedFeedbacks, setReceivedFeedbacks] = useState([]);
    const [givenFeedbacks, setGivenFeedbacks] = useState([]);
    const navigate = useNavigate();
    const location = useLocation();
    const toast = useRef(null);

    const loadFeedbacks = async () => {
        try {
            const givenResponse = await feedbackService.findByAuthor();
            const receivedResponse = await feedbackService.findByRecipient();
            setReceivedFeedbacks(receivedResponse.data.content);
            setGivenFeedbacks(givenResponse.data.content);
        } catch (error) {
            console.error("Erro no carregamento dos feedbacks:", error);
            toast.current.show({
                severity: 'error',
                summary: 'Erro',
                detail: 'Não foi possível carregar os feedbacks.',
                life: 3000
            });
        }
    };

    useEffect(() => {
        loadFeedbacks();
    }, [location.state]);

    const handleDelete = async (id) => {
        try {
            await feedbackService.delete(id);
            setGivenFeedbacks(givenFeedbacks.filter(feedback => feedback.id !== id));
            toast.current.show({
                severity: 'success',
                summary: 'Sucesso',
                detail: 'Feedback excluído com sucesso!',
                life: 3000
            });
        } catch (error) {
            console.error("Erro ao deletar feedback:", error);
            const message = error.response?.data?.message || 'Erro ao deletar o feedback.';
            toast.current.show({
                severity: 'error',
                summary: 'Erro',
                detail: message,
                life: 5000
            });
        }
    }

    const handleCreate = () => {
        navigate("/home/feedbacks/feedback-form")
    }

    const handleEdit = (id) => {
        navigate(`/home/feedbacks/feedback-form/${id}`)
    }

    const renderStars = (grade) => {
        const stars = [];
        for (let i = 1; i <= 5; i++) {
            if (i <= grade) {
                stars.push(<i key={i} className="pi pi-star-fill" style={{ color: '#FFC107' }}></i>);
            } else {
                stars.push(<i key={i} className="pi pi-star" style={{ color: '#BDBDBD' }}></i>);
            }
        }
        return stars;
    };

    const givenFooter = (feedback) => {
        return (
            <div className="feedback-footer">
                <div className="feedback-info">
                    {renderStars(feedback.grade)}
                </div>
                <span className="action-container">
                    <Button
                        icon="pi pi-pencil"
                        className="p-button-outlined p-button-secondary"
                        onClick={() => handleEdit(feedback.id)}
                        tooltip="Editar Feedback"
                        tooltipOptions={{ position: 'bottom' }}
                    />
                    <Button
                        icon="pi pi-trash"
                        className="p-button-outlined p-button-danger"
                        onClick={() => handleDelete(feedback.id)}
                        tooltip="Excluir Feedback"
                        tooltipOptions={{ position: 'bottom' }}
                    />
                </span>
            </div>
        );
    }

    const receivedFooter = (feedback) => {
        return (
            <div className="feedback-footer">
                <div className="feedback-info">
                    {renderStars(feedback.grade)}
                </div>
            </div>
        );
    }

    return (
        <main className="feedbacklist-container">
            <Toast ref={toast} />
            <div className="feedbacklist-header">
                <h1>Feedbacks Enviados</h1>
                <Button
                    label="Criar Feedback"
                    icon="pi pi-plus"
                    onClick={() => handleCreate()}
                />
            </div>
            <div className="cardfeedback-container">
                {givenFeedbacks.length > 0 ? (
                    givenFeedbacks.map(feedback => (
                        <Card
                            key={feedback.id}
                            title={feedback.recipient.name}
                            subTitle={feedback.comment}
                            footer={givenFooter(feedback)}
                            className="p-card-feedback"
                        />
                    ))
                ) : (
                    <div className="nofeedbacks">
                        <p>No given feedbacks were found</p>
                    </div>
                )}
            </div>
            <div className="feedbacklist-header">
                <h1>Feedbacks Recebidos</h1>
            </div>
            <div className="cardfeedback-container">
                {receivedFeedbacks.length > 0 ? (
                    receivedFeedbacks.map(feedback => (
                        <Card
                            key={feedback.id}
                            title={feedback.author.name}
                            subTitle={feedback.comment}
                            footer={receivedFooter(feedback)}
                            className="p-card-feedback"
                        />
                    ))
                ) : (
                    <div className="nofeedbacks">
                        <p>No received feedbacks were found</p>
                    </div>
                )}
            </div>
        </main>
    );
}