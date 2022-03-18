import React, { useState } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Layout } from 'antd';
import "antd/dist/antd.min.css";
import { Content } from "antd/lib/layout/layout";
import Login from './pages/Login';
import Register from "./pages/Register";
import Home from "./pages/Home";
import AddStorage from "./pages/AddStorage";
import AddItem from "./pages/AddItem";
import Admin from "./pages/admin/Admin";
import SetLimit from "./pages/admin/SetLimit";
import OverLimit from "./pages/admin/OverLimit";
import UserStats from "./pages/admin/UserStats";
import FindItems from "./pages/FindItems";
import ViewImage from "./pages/ViewImage";

// declaring functions that are used in other components to not rewrite same code

function LogOut(navigate) {
  navigate("/");
}

function GoBack(navigate) {
  navigate(-1);
}

function GoHome(navigate) {
  navigate("/home")
}

function App() {
  const [userData, setUserData] = useState();

  return (
    <BrowserRouter>
      <Layout>
        <Content>
          <Routes>
            <Route exact path="/" element={<Login setUserData={p => {setUserData(p)}} getUserData={userData} />} />
            <Route exact path="/register" element={<Register getUserData={userData} />} />
            <Route exact path="/home" element={<Home setUserData={p => {setUserData(p)}} getUserData={userData} />} />
            <Route exact path="/addstorage" element={<AddStorage setUserData={p => {setUserData(p)}} getUserData={userData} />} />
            <Route exact path="/additem" element={<AddItem setUserData={p => {setUserData(p)}} getUserData={userData} />} />
            <Route exact path="/finditems" element={<FindItems setUserData={p => {setUserData(p)}} getUserData={userData} />} />
            <Route exact path="/admin" element={<Admin setUserData={p => {setUserData(p)}} getUserData={userData} />} />
            <Route exact path="/admin/setlimit" element={<SetLimit setUserData={p => {setUserData(p)}} getUserData={userData} />} />
            <Route exact path="/admin/overlimit" element={<OverLimit setUserData={p => {setUserData(p)}} getUserData={userData} />} />
            <Route exact path="/admin/userstats" element={<UserStats setUserData={p => {setUserData(p)}} getUserData={userData} />} />
            <Route exact path="/image/:id" element={<ViewImage setUserData={p => {setUserData(p)}} getUserData={userData} />} />
          </Routes>
        </Content>
      </Layout>
    </BrowserRouter>
  );
}

export {App, LogOut, GoBack, GoHome};
