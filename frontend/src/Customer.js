import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Account from './components/pages/Account/Account';
import Home from './components/pages/Home/Home';
import Logout from './components/pages/Logout/Logout';
import NavBar from './components/ui/NavBar';
import PrivateRoute from './privateRoutes/PrivateRoute';
import SignIn from './components/pages/Landing/SignIn';
import SignUp from './components/pages/Landing/SignUp';
import { Box, Grid, Typography } from '@mui/material';
import Items from './components/pages/Items/Items';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import { useSelector } from 'react-redux';

const Customer = () => {
  const auth = useSelector((state) => state.auth);
  return (
    <Box>
      <NavBar
        pages={[
          { name: 'Items', path: '/items' },
          { name: 'Chat', path: '/chat' },
        ]}
        settings={[
          {
            id: 'setting_1',
            name: (
              <Grid container direction="row" alignItems="center">
                <AccountCircleOutlinedIcon />
                <Typography>
                  Your Account{auth.user && ': ' + auth.user.username}
                </Typography>
              </Grid>
            ),
            path: '/account',
            divider: true,
          },
          {
            id: 'setting_2',
            name: (
              <Grid container direction="row" alignItems="center">
                <LogoutOutlinedIcon />
                <Typography>Logout</Typography>
              </Grid>
            ),
            path: '/logout',
            divider: false,
          },
        ]}
        homePagePath={'/'}
      />
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
