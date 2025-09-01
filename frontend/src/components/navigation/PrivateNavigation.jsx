import React from "react";
import { Button } from "primereact/button";
import { useNavigate } from "react-router-dom";

const PrivateNavigation = () => {
    const navigate = useNavigate();

    const handleFeedbackClick = () => {
        navigate("/home/feedbacks")
    }

    const handleCategoryClick = () => {
        navigate("/home/categories")
    }

    return(
        <nav className="navigation-container">
            <Button icon="pi pi-megaphone" onClick={() => handleFeedbackClick()} link />
            <Button icon="pi pi-tags" onClick={() => handleCategoryClick()} link />
        </nav>
    );
}

export default PrivateNavigation;