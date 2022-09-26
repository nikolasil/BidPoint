import { Box, Grid, Typography } from '@mui/material';
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './components/adminPages/Home/Home';
import UnderConstruction from './components/UnderConstruction/UnderConstruction';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import { useSelector } from 'react-redux';
import NavBar from './components/ui/NavBar';
import Users from './components/adminPages/Users/Users';
import User from './components/adminPages/Users/User';
import PageContainer from './components/ui/PageContainer';
import Account from './components/pages/Account/Account';
import AdminPrivateRoute from './privateRoutes/AdminPrivateRoute';
import Items from './components/adminPages/Items/Items';

const Admin = () => {
  const auth = useSelector((state) => state.auth);
  return (
    <Box>
      <NavBar
        title="AdminPanel"
        pages={[
          { name: 'Website', path: '/' },
          { name: 'Users', path: '/admin/users' },
          { name: 'Items', path: '/admin/items' },
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
            path: '/admin/account',
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
        homePagePath={'/admin'}
      />
      <Routes>
        <Route
          exact
          path="/"
          element={
            <AdminPrivateRoute>
              <PageContainer page={<Home />} />
            </AdminPrivateRoute>
          }
        />
        <Route
          exact
          path="/account"
          element={
            <AdminPrivateRoute>
              <PageContainer page={<Account />} />
            </AdminPrivateRoute>
          }
        />
        <Route
          exact
          path="/users"
          element={
            <AdminPrivateRoute>
              <PageContainer page={<Users />} />
            </AdminPrivateRoute>
          }
        />
        <Route
          exact
          path="/users/:username"
          element={
            <AdminPrivateRoute>
              <PageContainer page={<User />} />
            </AdminPrivateRoute>
          }
        />
        <Route
          exact
          path="/items"
          element={
            <AdminPrivateRoute>
              <PageContainer page={<Items />} />
            </AdminPrivateRoute>
          }
        />
      </Routes>
    </Box>
  );
};

export default Admin;
