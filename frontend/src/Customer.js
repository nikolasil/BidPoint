import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Account from './components/pages/Account/Account';
import Home from './components/pages/Home/Home';
import Logout from './components/pages/Logout/Logout';
import NavBar from './components/ui/NavBar';

const Customer = () => {
  return (
    <>
      <NavBar />
      <Routes>
        <Route exact path="/home" element={<Home />} />
        <Route exact path="/logout" element={<Logout />} />
        <Route exact path="/account" element={<Account />} />
      </Routes>
    </>
  );
};

export default Customer;
