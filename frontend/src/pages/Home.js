import { Button } from "antd";
import Layout, { Content, Header } from "antd/lib/layout/layout";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { LogOut } from "../App";
import "../styles/Home.css"

function Home(props) {
    document.title = "Avaleht";

    const navigate = useNavigate();

    const [adminRender, setAdminRender] = useState("");

    function addStorage() {
        navigate("/addstorage");
    }

    function addItem() {
        navigate("/additem");
    }

    function goToAdmin() {
        navigate("/admin");
    }

    function findItems() {
        navigate("/finditems");
    }

    useEffect(
        () => {
            if(props.getUserData === undefined) {
                props.setUserData();
                LogOut(navigate);
            // only if you're logged in with an admin account, the admin panel button is displayed
            } else if(props.getUserData.admin === true) {
                setAdminRender(<Button type="primary" onClick={goToAdmin}>Admin paneel</Button>);
            }
        },
        [navigate]
    )

    return (
        <Layout>
            <Header>
                {adminRender}
                <Button type="danger" id="logoutButton" onClick={() => {props.setUserData(); LogOut(navigate)}}>Logi välja</Button>
            </Header>
            <Content>
                <Button type="primary" id="viewItems" style={{ background: "green", borderColor: "green" }} onClick={findItems}>Vaata ja otsi esemeid</Button>
                <Button type="primary" id="addStorage" onClick={addStorage}>Lisa uus hoiustamiskoht</Button>
                <Button type="primary" id="addItems" onClick={addItem}>Lisa uus ese</Button>
            </Content>
        </Layout>
    )
}

export default Home;