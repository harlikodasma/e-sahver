import { Button } from "antd";
import Layout, { Content, Header } from "antd/lib/layout/layout";
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { GoBack, LogOut, GoHome } from "../../App";
import "../../styles/Admin.css"

function Admin(props) {
    document.title = "Admin paneel";

    const navigate = useNavigate();

    useEffect(
        () => {
            if(props.getUserData.admin === false && props.getUserData !== undefined) {
                GoHome(navigate);
            } else if(props.getUserData === undefined) {
                props.setUserData();
                LogOut(navigate);
            }
        }
    )

    function checkOverLimit() {
        navigate("/admin/overlimit");
    }

    function setLimit() {
        navigate("/admin/setlimit");
    }

    function userStats() {
        navigate("/admin/userstats");
    }

    return (
        <Layout>
            <Header>
                <Button type="danger" id="logoutButton" onClick={() => {props.setUserData(); LogOut(navigate)}}>Logi välja</Button>
                <Button type="primary" onClick={() => GoBack(navigate)}>Tagasi</Button>
            </Header>
            <Content>
                <Button type="primary" id="setLimit" onClick={setLimit}>Vaata ja muuda ärikliendi piiranguid</Button>
                <Button type="primary" id="overLimit" onClick={checkOverLimit}>Piiranguid ületavad ärikliendid</Button>
                <Button type="primary" id="userStats" style={{ background: "green", borderColor: "green" }} onClick={userStats}>Kasutajate statistika</Button>
            </Content>
        </Layout>
    )
}

export default Admin;