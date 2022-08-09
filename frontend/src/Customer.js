import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Account from './components/pages/Account/Account';
import Home from './components/pages/Home/Home';
import Logout from './components/pages/Logout/Logout';
import NavBar from './components/ui/NavBar';
import PrivateRoute from './privateRoutes/PrivateRoute';
import SignIn from './components/pages/Landing/SignIn';
import SignUp from './components/pages/Landing/SignUp';
import { Box } from '@mui/material';
import Items from './components/pages/Items/Items';

const Customer = () => {
  return (
    <Box>
      <NavBar />
      <Routes>
        <Route exact path="/" element={<Home />} />
        <Route exact path="/signin" element={<SignIn />} />
        <Route exact path="/signup" element={<SignUp />} />
        <Route
          exact
          path="/logout"
          element={
            <PrivateRoute>
              <Logout />
            </PrivateRoute>
          }
        />
        <Route
          exact
          path="/items"
          element={
            <PrivateRoute>
              <Items />
            </PrivateRoute>
          }
        />
        <Route
          exact
          path="/account"
          element={
            <PrivateRoute>
              <Account />
            </PrivateRoute>
          }
        />
      </Routes>
    </Box>
  );
};

export default Customer;
