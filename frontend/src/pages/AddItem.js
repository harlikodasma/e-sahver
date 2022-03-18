import { Button, Input, Select, Form, Upload } from "antd";
import Layout, { Content, Header } from "antd/lib/layout/layout";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { GoBack, LogOut } from "../App";

function AddItem(props) {
    document.title = "Uus ese";

    const navigate = useNavigate();
    const [searchResults, setSearchResults] = useState([]);

    const onFinish = async (e) => {
        // a quick check if the user somehow didn't select a storage it belongs to
        if(e.parentID === undefined) {
            alert("Vali koht, mille alla ese kuulub!");
        } else {
            // if there is a picture attached, a seperate fetch will upload it to the database
            // and returns with its ID so it could later be bound to the item itself
            if(e.pictureID !== undefined) {
                try {
                    const formData = new FormData();
                    formData.append("file", e.pictureID.file);

                    fetch("http://localhost:8080/api/image/upload", {
                        method: 'POST',
                        body: formData,
                    }).then(
                        response => response.json()
                    ).then(data => {
                        // an object with all the necessary information for the API is made and sent away
                        const newItem = {
                            name: e.name,
                            parentID: e.parentID,
                            pictureID: data,
                            serialNo: e.serialNo,
                            category: e.category,
                            manufactureYear: e.manufactureYear,
                            ownerName: e.ownerName,
                            addedByID: props.getUserData.id,
                            businessClientItem: props.getUserData.businessClientAccount
                        }

                        try {
                            fetch("http://localhost:8080/api/item/create", {
                                method: 'POST',
                                body: JSON.stringify(newItem),
                                headers: { 'Content-Type': 'application/json' }
                            }).then(() => {
                                alert("Ese on lisatud!");
                                GoBack(navigate);
                            })
                        } catch (error) {
                            alert(error);
                        }
                    })
                } catch (error) {
                    alert(error);
                }
            } else {
                // if there is no picture, a slightly different object will be made for the API and the image API doesn't need to get involved
                const newItem = {
                    name: e.name,
                    parentID: e.parentID,
                    pictureID: null,
                    serialNo: e.serialNo,
                    category: e.category,
                    manufactureYear: e.manufactureYear,
                    ownerName: e.ownerName,
                    addedByID: props.getUserData.id,
                    businessClientItem: props.getUserData.businessClientAccount
                }
                try {
                    fetch("http://localhost:8080/api/item/create", {
                        method: 'POST',
                        body: JSON.stringify(newItem),
                        headers: { 'Content-Type': 'application/json', }
                    }).then( () => {
                        alert("Ese on lisatud!");
                        GoBack(navigate);
                    })
                } catch (error) {
                    alert(error);
                }
            }
        }
    }

    useEffect(
        () => {
            if(props.getUserData === undefined) {
                props.setUserData();
                LogOut(navigate);
            }
            // this will fetch all storages for the user to be displayed in the dropdown selection
            try {
                fetch('http://localhost:8080/api/storage/find/bycreator?id=' + props.getUserData.id, {
                    method: 'GET'
                }).then(
                    response => response.json()
                ).then(
                    data => {
                        setSearchResults(data);
                    }
                );
            } catch (error) {
                alert(error);
            }
        },
        [navigate]
    )

    if(searchResults.length === 0) {
        return <Layout>
            <Header>
                <Button type="danger" id="logoutButton" onClick={() => {props.setUserData(); LogOut(navigate)}}>Logi välja</Button>
                <Button type="primary" id="backButton" onClick={() => GoBack(navigate)}>Tagasi</Button>
            </Header>
            <Content id="content" style={{ fontSize: "25px", marginBottom: "5%" }}>
                Esemeid ei saa ilma ühegi hoiustuskohata lisada!
            </Content>
        </Layout>
    }

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
                        label="Pilt"
                        name="pictureID"
                    >
                        <Upload
                            listType="pictureID"
                            maxCount={1}
                            beforeUpload={() => false}
                            accept=".png,.jpg,.jpeg,.bmp"
                        >
                            <Button style={{ width: "100%" }}>
                                Vali fail
                            </Button>
                        </Upload>
                    </Form.Item>
                    <Form.Item
                        label="Nimi"
                        name="name"
                        required
                    >
                        <Input
                            type="text"
                            required
                        />
                    </Form.Item>
                    <Form.Item
                        label="Koht, mille alla kuulub"
                        name="parentID"
                        required
                    >
                        {/* here, the aforementioned dropdown with all previously created storages will be made */}
                        <Select required>
                            {searchResults.map((e) => {
                                return <Select.Option value={e.id} key={e.id}>{e.name}</Select.Option>
                            })}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        label="Seerianumber"
                        name="serialNo"
                    >
                        <Input
                            type="text"
                        />
                    </Form.Item>
                    <Form.Item
                        label="Kategooria"
                        name="category"
                    >
                        <Input
                            type="text"
                        />
                    </Form.Item>
                    <Form.Item
                        label="Tootmise aasta"
                        name="manufactureYear"
                    >
                        <Input
                            type="number"
                        />
                    </Form.Item>
                    <Form.Item
                        label="Vastutaja/omaniku nimi"
                        name="ownerName"
                    >
                        <Input
                            type="text"
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

export default AddItem;