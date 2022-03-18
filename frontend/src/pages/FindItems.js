import { Button, Form, Input, Table } from "antd";
import Layout, { Content, Header } from "antd/lib/layout/layout";
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { GoBack, LogOut } from "../App";

function FindItems(props) {
    document.title = "Otsi esemeid";

    const navigate = useNavigate();

    useEffect(
        () => {
            if(props.getUserData === undefined) {
                props.setUserData();
                LogOut(navigate);
            }
        }
    )

    const [tableData, setTableData] = useState([]);

    function openPicture(id) {
        navigate("/image/" + id);
    }

    const onFinish = async (e) => {
        setTableData([]);
        try {
            let searchValue;
            // if the searchbox was empty, it will be replaced with an empty string so the query would be able to find all items
            // else, the same string is pushed to the API
            if(e.nameSearch === undefined) {
                searchValue = "";
            } else {
                searchValue = e.nameSearch;
            }
            fetch('http://localhost:8080/api/item/find/search?addedByID=' + props.getUserData.id + "&namesearch=" + searchValue, {
                method: 'GET'
            }).then(
                response => response.json()
            ).then(
                data => {
                    // each table row is crafted into an object and then pushed to the state which displays them all
                    data.forEach(element => {
                        let rowValues = {};
                        rowValues.key = element.itemID;
                        rowValues.name = element.name;
                        rowValues.parentID = element.storageName;
                        rowValues.serialNo = element.serialNo;
                        rowValues.category = element.category;
                        rowValues.productionYear = element.manufactureYear;
                        rowValues.ownerName = element.ownerName;
                        // since there can be no picture, this statement checks whether or not the button is necessary
                        if(element.pictureID === null) {
                            rowValues.picture = "";
                        } else {
                            rowValues.picture = <Button onClick={() => openPicture(element.pictureID)}>Ava pilt</Button>;
                        }
                        setTableData(oldArray => [...oldArray, rowValues]);
                    });
                }
            )
        } catch (error) {
            alert(error);
        }
    }

    // declaring table headers

    const columns = [
        {
            title: "Nimi",
            dataIndex: "name",
            key: "name"
        },
        {
            title: "Koht, mille alla kuulub",
            dataIndex: "parentID",
            key: "parentID"
        },
        {
            title: "Seerianumber",
            dataIndex: "serialNo",
            key: "serialNo"
        },
        {
            title: "Kategooria",
            dataIndex: "category",
            key: "category"
        },
        {
            title: "Tootmise aasta",
            dataIndex: "productionYear",
            key: "productionYear"
        },
        {
            title: "Vastutaja/omaniku nimi",
            dataIndex: "ownerName",
            key: "ownerName"
        },
        {
            title: "Pilt",
            dataIndex: "picture",
            key: "picture"
        }
    ]

    return (
        <Layout>
            <Header>
                <Button type="danger" id="logoutButton" onClick={() => {props.setUserData(); LogOut(navigate)}}>Logi välja</Button>
                <Button type="primary" onClick={() => GoBack(navigate)}>Tagasi</Button>
            </Header>
            <Content id="content" style={{ marginBottom: "5%" }}>
                <Form
                    labelCol={{ span: 8 }}
                    wrapperCol={{ span: 8 }}
                    name="basic"
                    onFinish={(values) => onFinish(values)}
                >
                    <Form.Item
                        label="Nimi või osa nimest"
                        name="nameSearch"
                        required
                    >
                        <Input
                            type="text"
                            placeholder="Kõikide esemete nägemiseks jäta otsing tühjaks"
                        />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" className="formbutton">Otsi</Button>
                    </Form.Item>
                </Form>
                <Table columns={columns} dataSource={tableData} style={{ marginTop: "8%", marginBottom: "2%" }} />
            </Content>
        </Layout>
    )
}

export default FindItems;