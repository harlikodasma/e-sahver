import { Button, Input, Form, Select } from "antd";
import Layout, { Content, Header } from "antd/lib/layout/layout";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { GoBack, LogOut } from "../App";
import "../styles/AddStorage.css"

function AddStorage(props) {
    document.title = "Uus hoiustamiskoht";

    const navigate = useNavigate();
    const [searchResults, setSearchResults] = useState([]);

    // when the form is submitted, an object is made and sent to the API
    const onFinish = async (e) => {
        const newStorage = {
            name: e.name,
            creatorID: props.getUserData.id,
            parentID: e.parentID
        }
        
        try {
            fetch("http://localhost:8080/api/storage/create", {
                method: 'POST',
                body: JSON.stringify(newStorage),
                headers: { 'Content-Type': 'application/json' }
            }).then(() => {
                alert("Uus hoiustamiskoht loodud!");
                GoBack(navigate);
            })
        } catch (error) {
            alert(error);
        }
    }

    useEffect(
        () => {
            if(props.getUserData === undefined) {
                props.setUserData();
                LogOut(navigate);
            }
            // all previously made storages are fetched for the dropdown (to select a super storage for a new one)
            try {
                fetch('http://localhost:8080/api/storage/find/bycreator?id=' + props.getUserData.id, {
                    method: 'GET'
                }).then(
                    response => response.json())
                .then(
                    data => {
                        setSearchResults(data);
                    }
                )
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
            <Content id="content">
                <Form
                    labelCol={{ span: 8 }}
                    wrapperCol={{ span: 8 }}
                    name="basic"
                    onFinish={(values) => onFinish(values)}
                >
                    <Form.Item
                        label="Koha nimetus"
                        name="name"
                        required
                    >
                        <Input
                            type="text"
                            placeholder="Tuba-1"
                            required
                        />
                    </Form.Item>
                    <Form.Item
                        label="Koht, mille alla kuulub"
                        name="parentID"
                    >
                        <Select>
                            {/* dropdown is created from the fetch in useeffect */}
                            {searchResults.map((e) => {
                                return <Select.Option value={e.id} key={e.id}>{e.name}</Select.Option>
                            })}
                        </Select>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" className="formbutton">Salvesta</Button>
                    </Form.Item>
                </Form>
            </Content>
        </Layout>
    )
}

export default AddStorage;