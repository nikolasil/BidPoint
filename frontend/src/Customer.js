import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './components/pages/Home/Home';
import NavBar from './components/ui/NavBar';

const Customer = () => {
  return (
    <>
      <NavBar />
      <Routes>
        <Route exact path="/home" element={<Home />} />
      </Routes>
    </>
  );
};

export default Customer;
