import React from 'react';
import { Routes, Route } from 'react-router-dom';
import UnderConstruction from './components/pages/UnderConstruction/UnderConstruction';

const Admin = () => {
  return (
    <>
      <Routes>
        <Route exact path="/" element={<UnderConstruction />} />
      </Routes>
    </>
  );
};

export default Admin;
