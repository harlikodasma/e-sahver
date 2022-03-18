import { Button, Input, Form } from "antd";
import Layout, { Content, Header } from "antd/lib/layout/layout";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { GoBack, GoHome, LogOut } from "../../App";

function SetLimit(props) {
    document.title = "Muuda piirangut";

    const navigate = useNavigate();

    const [restrictionRender, setRestrictionRender] = useState("Esemete arvu piirangut ei ole määratud!");

    // when the "form" is submitted, a fetch will update the database with the new restriction value
    const onFinish = async (e) => {
        if(e.maxItems === undefined || e.maxItems < 1) {
            alert("Arv ei tohi olla väiksem kui 1!");
        } else {
            try {
                fetch("http://localhost:8080/api/restriction/set", {
                    method: 'POST',
                    body: JSON.stringify({itemLimit: e.maxItems}),
                    headers: { 'Content-Type': 'application/json' }
                }).then(() => {
                    alert("Piirang muudetud!");
                    GoBack(navigate);
                })
            } catch (error) {
                alert(error);
            }
        }
    }

    useEffect(
        () => {
            if(props.getUserData.admin === false && props.getUserData !== undefined) {
                GoHome(navigate);
            } else if(props.getUserData === undefined) {
                props.setUserData();
                LogOut(navigate);
            }
            // this fetch gets the current restriction and displays it
            try {
                fetch('http://localhost:8080/api/restriction/get', {
                    method: 'GET'
                }).then(
                    response => response.json()
                ).then(
                    data => {
                        if(data.length !== 0) {
                            setRestrictionRender("Ärikliendi tasuta esemete arv on piiratud " + data[0].itemLimit + " esemeni.");
                        }
                    }
                );
            } catch (error) {
                alert(error);
            }
        },
        [navigate]
    )

    return (
        <Layout>
            <Header>
                <Button type="danger" id="logoutButton" onClick={() => {props.setUserData(); LogOut(navigate)}}>Logi välja</Button>
                <Button type="primary" onClick={() => GoBack(navigate)}>Tagasi</Button>
            </Header>
            <Content id="content" style={{ marginBottom: "5%" }}>
                <div style={{ fontSize: "25px" }}>
                    {restrictionRender}
                </div>
                <div style={{ marginTop: "8%", marginBottom: "2%", fontSize: "20px" }}>
                    Määra uus piirang:
                </div>
                <Form
                    labelCol={{ span: 8 }}
                    wrapperCol={{ span: 8 }}
                    name="basic"
                    onFinish={(values) => onFinish(values)}
                >
                    <Form.Item
                        label="Max tasuta esemete arv"
                        name="maxItems"
                        required
                    >
                        <Input
                            type="number"
                            required
                        />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" className="formbutton">Salvesta</Button>
                    </Form.Item>
                </Form>
            </Content>
        </Layout>
    )
}

export default SetLimit;