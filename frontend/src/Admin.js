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

const Admin = () => {
  const auth = useSelector((state) => state.auth);
  return (
    <Box>
      <NavBar
        pages={[
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
        <Route exact path="/" element={<Home />} />
        <Route exact path="/account" element={<UnderConstruction />} />
        <Route exact path="/users" element={<Users />} />
        <Route exact path="/items" element={<UnderConstruction />} />
      </Routes>
    </Box>
  );
};

export default Admin;
