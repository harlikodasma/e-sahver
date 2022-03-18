import { Button, Table } from "antd";
import Layout, { Content, Header } from "antd/lib/layout/layout";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { GoBack, GoHome, LogOut } from "../../App";

function OverLimit(props) {
    document.title = "Piiranguid ületavad kliendid";

    const navigate = useNavigate();

    const [restrictionTitle, setRestrictionTitle] = useState("Esemete arvu piirangut ei ole määratud või puuduvad piiranguid ületavad ärikliendid!");
    const [tableData, setTableData] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(
        () => {
            if(props.getUserData.admin === false && props.getUserData !== undefined) {
                GoHome(navigate);
            } else if(props.getUserData === undefined) {
                props.setUserData();
                LogOut(navigate);
            }
            try {
                // first fetch takes the current restriction and displays it
                // if there is no restriction, nothing more happens
                fetch('http://localhost:8080/api/restriction/get', {
                    method: 'GET'
                }).then(
                    response => response.json()
                ).then(
                    data => {
                        // if there is a limit, things go on
                        if(data.length !== 0) {
                            const limitVariable = data[0].itemLimit;
                            setRestrictionTitle("Ärikliendi tasuta esemete arv on piiratud " + limitVariable + " esemeni.");
                            try {
                                // now it will fetch every business client item ...
                                fetch('http://localhost:8080/api/item/find/businessclient?businessClientAccount=true', {
                                    method: 'GET'
                                }).then(
                                    response => response.json()
                                ).then(
                                        data => {
                                            let customerValues = {};
                                            // ... and sums each client's items to check them later
                                            data.forEach(element => {
                                                let addedByID = element.addedByID;
                                                if(addedByID in customerValues) {
                                                    customerValues[addedByID] = customerValues[addedByID] + 1;
                                                } else {
                                                    customerValues[addedByID] = 1;
                                                }
                                            });

                                            var loopNumber = 0;
                                            // for every client, this loop checks if they have gone over the limit
                                            // if yes, an object is made that makes up a row in the table
                                            // this object is then pushed to the table data state to be rendered
                                            for (const [key, value] of Object.entries(customerValues)) {
                                                if(value > limitVariable) {
                                                    try {
                                                        fetch('http://localhost:8080/api/user/find/byid?id=' + key, {
                                                            method: 'GET'
                                                        }).then(
                                                            response => response.json()
                                                        ).then(
                                                            data => {
                                                                let tempTableData = {};
                                                                tempTableData.key = key;
                                                                tempTableData.email = data["email"];
                                                                tempTableData.itemCount = value;
                                                                tempTableData.paymentAmount = ((value - limitVariable) / 100) + " €";
                                                                setTableData(oldArray => [...oldArray, tempTableData]);
                                                                if(Object.keys(customerValues).length === loopNumber) {
                                                                    setIsLoaded(true);
                                                                }
                                                            }
                                                        )
                                                    } catch (error) {
                                                        alert(error);
                                                    }
                                                }
                                                loopNumber++;
                                            }
                                        }
                                    )
                            } catch (error) {
                                alert(error);
                            }
                        }
                    }
                );
            } catch (error) {
                alert(error);
            }
        },
        [navigate]
    )

    // declaring table headers

    const columns = [
        {
            title: "Kliendi e-mail",
            dataIndex: "email",
            key: "email"
        },
        {
            title: "Toodete arv",
            dataIndex: "itemCount",
            key: "itemCount"
        },
        {
            title: "Maksustatav summa",
            dataIndex: "paymentAmount",
            key: "paymentAmount"
        }
    ];

    // until fetching is not done or if no data can be found, this will return

    if(isLoaded === false) {
        return (
            <Layout>
                <Header>
                    <Button type="danger" id="logoutButton" onClick={() => {props.setUserData(); LogOut(navigate)}}>Logi välja</Button>
                    <Button type="primary" id="backButton" onClick={() => GoBack(navigate)}>Tagasi</Button>
                </Header>
                <Content id="content" style={{ fontSize: "25px", marginBottom: "5%" }}>
                    Esemete arvu piirangut ei ole määratud või puuduvad piiranguid ületavad ärikliendid!
                </Content>
            </Layout>
        )
    }

    return (
        <Layout>
            <Header>
                <Button type="danger" id="logoutButton" onClick={() => {props.setUserData(); LogOut(navigate)}}>Logi välja</Button>
                <Button type="primary" onClick={() => GoBack(navigate)}>Tagasi</Button>
            </Header>
            <Content id="content" style={{ marginBottom: "5%" }}>
                <div style={{ fontSize: "25px" }}>
                    {restrictionTitle}
                </div>
                <Table dataSource={tableData} columns={columns} style={{ marginTop: "8%", marginBottom: "2%" }} />
            </Content>
        </Layout>
    )
}

export default OverLimit;