import React, { useEffect } from 'react';
import { Form, Input, Button, Layout } from 'antd';
import "../styles/Register.css"
import { useNavigate } from 'react-router-dom';
import Checkbox from 'antd/lib/checkbox/Checkbox';
import { GoHome } from '../App';

function Register(props) {
    document.title = "Registreeri";

    const navigate = useNavigate();

    useEffect(
        () => {
            if(props.getUserData !== undefined) {
                props.setUserData();
                GoHome(navigate);
            }
        }
    )

    const onFinish = async (e) => {
        // a new object with values from the form are created
        const newUser = {
            email: e.email,
            passwordHash: e.password,
            confirmPassword: e.confirmpassword,
            businessClientAccount: e.businessClientAccount,
            admin: false
        }

        // then, these values are checked before sending to API
        if(!e.email || !e.password || !e.confirmpassword) {
            alert('Kõik väljad peavad olema täidetud!');
        } else if(e.password !== e.confirmpassword) {
            alert('Paroolid ei kattu!');
        } else {
            try {
                fetch('http://localhost:8080/api/user/register', {
                    method: 'POST',
                    body: JSON.stringify(newUser),
                    headers: { 'Content-Type': 'application/json' }
                }).then(
                    data => {
                        // finally, a readable answer is displayed whether the registration was a success
                        // or something went wrong (and what exactly)
                        if(data.status === 200) {
                            alert("Kasutaja edukalt loodud!");
                            navigate("/");
                        } else if(data.status === 411) {
                            data.text()
                                .then(responseText => {
                                    alert(responseText);
                                })
                        } else if(data.status === 208) {
                            alert("Sellise e-mailiga kasutaja on juba olemas!");
                        }
                    }
                )
            } catch (error) {
                alert(error);
            }
        }
    }

    function goBack() {
        navigate("/");
    }


    return (
        <Layout>
            <h1>Loo uus kasutaja</h1>
            <Form
                labelCol={{ span: 8 }}
                wrapperCol={{ span: 8 }}
                name="basic"
                onFinish={(values) => onFinish(values)}
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
                    name="password"
                    required
                >
                    <Input
                        type="password"
                        required
                    />
                </Form.Item>
                <Form.Item
                    label="Kinnita parool"
                    name="confirmpassword"
                    required
                >
                    <Input
                        type="password"
                        required
                    />
                </Form.Item>
                <Form.Item
                    label="Äriklient"
                    name="businessClientAccount"
                    valuePropName='checked'
                >
                    <Checkbox />
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" className="formbutton">Registreeri</Button>
                </Form.Item>
                <Form.Item>
                    <Button type="danger" className="formbutton"><span onClick={goBack}>Tagasi</span></Button>
                </Form.Item>
            </Form>
        </Layout>
    );
}

export default Register;