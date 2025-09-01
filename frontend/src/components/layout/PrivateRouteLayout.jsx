import React from "react";
import { Navigate, Outlet } from "react-router-dom";

const PrivateRouteLayout = () => {
    const accessToken = localStorage.getItem("accessToken")?true:false;

    return(
        accessToken?<Outlet/>:<Navigate to={"/login"} replace/>
    );
}

export default PrivateRouteLayout;