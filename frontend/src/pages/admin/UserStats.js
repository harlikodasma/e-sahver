import { Button, Table } from "antd";
import Layout, { Content, Header } from "antd/lib/layout/layout";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { GoBack, GoHome, LogOut } from "../../App";

function UserStats(props) {
    document.title = "Kasutajate statistika";

    const navigate = useNavigate();

    const [tableData, setTableData] = useState([]);

    useEffect(
        () => {
            if(props.getUserData.admin === false && props.getUserData !== undefined) {
                GoHome(navigate);
            } else if(props.getUserData === undefined) {
                props.setUserData();
                LogOut(navigate);
            }
            // for each user, this fetch will get their appropriate stats and display them in a table
            try {
                fetch('http://localhost:8080/api/user/stats', {
                    method: 'GET'
                }).then(
                    response => response.json()
                ).then(
                    data => {
                        data.forEach(element => {
                            let rowValues = {};
                            rowValues.key = element.userID;
                            rowValues.email = element.email;
                            rowValues.itemCount = element.itemCount;
                            rowValues.pictureCount = element.imageCount;
                            rowValues.storageCount = element.storageCount;
                            rowValues.lowerStorageCount = element.lowerStorageCount;
                            setTableData(oldArray => [...oldArray, rowValues]);
                        });
                    }
                )
            } catch (error) {
                alert(error);
            }
        },
        [navigate]
    )

    // declaring table headers

    const columns = [
        {
            title: "Kasutaja e-mail",
            dataIndex: "email",
            key: "email"
        },
        {
            title: "Esemete arv",
            dataIndex: "itemCount",
            key: "itemCount"
        },
        {
            title: "Piltide arv",
            dataIndex: "pictureCount",
            key: "pictureCount"
        },
        {
            title: "Hoiustuskohtade arv kokku",
            dataIndex: "storageCount",
            key: "storageCount"
        },
        {
            title: "Alamhoiustuskohad (kuuluvad teiste kohtade alla)",
            dataIndex: "lowerStorageCount",
            key: "lowerStorageCount"
        }
    ]

    return (
        <Layout>
            <Header>
                <Button type="danger" id="logoutButton" onClick={() => {props.setUserData(); LogOut(navigate)}}>Logi välja</Button>
                <Button type="primary" onClick={() => GoBack(navigate)}>Tagasi</Button>
            </Header>
            <Content>
            <Table columns={columns} dataSource={tableData} style={{ marginTop: "5%", marginBottom: "5%" }} />
            </Content>
        </Layout>
    )
}

export default UserStats;