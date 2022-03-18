import { Button } from "antd";
import Layout, { Content, Header } from "antd/lib/layout/layout";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { GoBack, LogOut } from "../App";

function ViewImage(props) {
    document.title = "Pildi eelvaade";

    const navigate = useNavigate();
    const [imageData, setImageData] = useState();

    // image id is taken directly from the url, i.e. /image/15 or /image/18 etc
    let { id } = useParams();

    useEffect(
        () => {
            if(props.getUserData === undefined) {
                props.setUserData();
                LogOut(navigate);
            }
            // this id is then sent to the API which responds with a base64 byte string and image mimetype
            // which are both used to create and display said image
            try {
                fetch('http://localhost:8080/api/image/find/byid?id=' + id, {
                    method: 'GET'
                }).then(
                    response => response.json()
                ).then(data => {
                    setImageData(<img alt="pilt" src={`data:${data.type};base64,${data.picture}`} style={{ display: "block", marginLeft: "auto", marginRight: "auto", marginTop: "5%", marginBottom: "5%", width: "50%" }} />);
                })
            } catch (error) {
                alert(error);
            }
        },
        [id, navigate]
    )

    return (
        <Layout>
            <Header>
                <Button type="danger" id="logoutButton" onClick={() => {props.setUserData(); LogOut(navigate)}}>Logi välja</Button>
                <Button type="primary" onClick={() => GoBack(navigate)}>Tagasi</Button>
            </Header>
            <Content>
                {imageData}
            </Content>
        </Layout>
    )
}

export default ViewImage;