import React, { useEffect } from "react";
import "antd/dist/antd.min.css";
import '../styles/Login.css';
import { Button, Input, Layout, Form } from "antd";
import { useNavigate } from "react-router-dom";
import { GoHome } from "../App";

function Login(props) {
    document.title = "Logi sisse";

    const navigate = useNavigate();

    useEffect(
        () => {
            if(props.getUserData !== undefined) {
                GoHome(navigate);
            }
        }
    )

    function goToRegister() {
        navigate("/register");
    }

    // after pressing the login button, the request is sent
    // if it comes back without the 200 OK code, email/password must be wrong and error is displayed
    // else, userdata state is set and the user is logged in

    const onFinish = (values) => {
        try {
            fetch('http://localhost:8080/api/user/login', {
                method: 'POST',
                body: JSON.stringify(values),
                headers: { 'Content-Type': 'application/json' }
            }).then(
                    data => {
                        if (data.status !== 200) {
                            alert("Vale e-mail või parool!");
                        } else {
                            data.text()
                                .then(responseText => {
                                    responseText = JSON.parse(responseText);
                                    props.setUserData(responseText);
                                    navigate("/home");
                                })
                        }
                    });
        } catch (error) {
            alert(error);
        }
    };

    return (
        <>
            <h1>e-Sahver</h1>
            <Layout>
                <Form
                    labelCol={{ span: 8 }}
                    wrapperCol={{ span: 8 }}
                    name="basic"
                    initialValues={{ remember: true }}
                    onFinish={onFinish}
                    autoComplete="off"
                >
                    <Form.Item
                        label="E-maili aadress"
                        name="email"
                        required
                    >
                        <Input
                            placeholder="email@domain.com"
                            type="email"
                            required
                        />
                    </Form.Item>
                    <Form.Item
                        label="Parool"
                        name="passwordHash"
                        required
                    >
                        <Input.Password
                            type="password"
                            required
                        />
                    </Form.Item>
                    <Form.Item>
                        <Button type="danger primary" htmlType="submit" id="login_button">
                            Logi sisse
                        </Button>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" id="newAccount"><span onClick={goToRegister}>Loo uus kasutaja</span></Button>
                    </Form.Item>
                </Form>
            </Layout>
        </>
    )
}

export default Login;