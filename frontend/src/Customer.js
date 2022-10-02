import React, { useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import Account from './components/pages/Account/Account';
import Home from './components/pages/Home/Home';
import Logout from './components/pages/Auth/Logout';
import NavBar from './components/ui/NavBar';
import PrivateRoute from './privateRoutes/PrivateRoute';
import SignIn from './components/pages/Auth/SignIn';
import SignUp from './components/pages/Auth/SignUp';
import { Box, Grid, Typography } from '@mui/material';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import { useSelector } from 'react-redux';

import PageContainer from './components/ui/PageContainer';
import CreateItem from './components/pages/Account/Items/CreateItem';
import AccountItems from './components/pages/Account/Items/AccountItems';
import EditItem from './components/pages/Account/Items/EditItem';
import Items from './components/pages/Items/Items';
import Item from './components/pages/Items/Item';
import ManageItems from './components/pages/Account/Items/ManageItems';
import Chat from './components/pages/Chat/Chat';
import { useSocket } from './customHooks/useSocket';

const Customer = () => {
  const auth = useSelector((state) => state.auth);

  return (
    <Box>
      <NavBar
        title="BidPoint"
        pages={[
          { name: 'Search Items', path: '/items' },
          { name: 'Manage Items', path: '/account/manage' },
          { name: 'Chat', path: '/chat' },
        ]}
        settings={[
          {
            id: 'setting_1',
            name: (
              <Grid container direction="row" alignItems="center">
                <AccountCircleOutlinedIcon />
                <Typography>
                  My Account | {auth.user && auth.user.username}
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
        <Route
          exact
          path="/signin"
          element={<PageContainer page={<SignIn />} />}
        />
        <Route
          exact
          path="/signup"
          element={<PageContainer page={<SignUp />} />}
        />
        <Route
          exact
          path="/logout"
          element={<PageContainer page={<Logout />} />}
        />
        <Route
          exact
          path="/account"
          element={
            <PrivateRoute>
              <PageContainer page={<Account />} />
            </PrivateRoute>
          }
        />
        <Route
          exact
          path="/items"
          element={<PageContainer page={<Items />} />}
        />
        <Route
          exact
          path="/items/:id"
          element={<PageContainer page={<Item />} />}
        />
        <Route
          exact
          path="/chat"
          element={
            <PrivateRoute>
              <PageContainer page={<Chat />} />
            </PrivateRoute>
          }
        />
        <Route
          exact
          path="/chat/:selectUser"
          element={
            <PrivateRoute>
              <PageContainer page={<Chat />} />
            </PrivateRoute>
          }
        />
        <Route
          exact
          path="/account/manage"
          element={
            <PrivateRoute>
              <PageContainer page={<ManageItems />} />
            </PrivateRoute>
          }
        />
        <Route
          exact
          path="/account/items"
          element={
            <PrivateRoute>
              <PageContainer page={<AccountItems />} />
            </PrivateRoute>
          }
        />
        <Route
          exact
          path="/account/items/create"
          element={
            <PrivateRoute>
              <PageContainer page={<CreateItem />} />
            </PrivateRoute>
          }
        />
        <Route
          exact
          path="/account/items/edit/:id"
          element={
            <PrivateRoute>
              <PageContainer page={<EditItem />} />
            </PrivateRoute>
          }
        />
      </Routes>
    </Box>
  );
};

export default Customer;
