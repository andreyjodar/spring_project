import React from "react";
import PrivateNavigation from "../navigation/PrivateNavigation";
import "./Header.css";
import { useNavigate } from "react-router-dom";
import AuthService from "../../services/AuthService";
import { Button } from "primereact/button";

const Header = () => {
    const authService = new AuthService();
    const navigate = useNavigate();

    const handleLogoutClick = () => {
        authService.logout();
        navigate("/")
    }

    return(
        <header className="header-container">
            <div className="brand-container">
                <Button onClick={() => navigate("/home")} link >
                    <p>PlayPay</p>
                </Button>
            </div>
            {<PrivateNavigation/>}
            <div className="auth-actions">
                <Button icon="pi pi-sign-out" onClick={handleLogoutClick}/>
            </div>
        </header>
    );
}

export default Header;