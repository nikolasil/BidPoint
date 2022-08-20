import React from 'react';
import { Container, Box, Typography, Grid, Divider } from '@mui/material';
import { useSelector } from 'react-redux';
const Account = () => {
  const auth = useSelector((state) => state.auth);
  const list = [
    { title: 'Username', value: auth.user.username },
    { title: 'Firstname', value: auth.user.firstname },
    { title: 'Lastname', value: auth.user.lastname },
    { title: 'Roles', value: auth.user.roles },
    { title: 'Mail', value: auth.user.mail },
    { title: 'Phone', value: auth.user.phone },
    { title: 'Address', value: auth.user.address },
    { title: 'afm', value: auth.user.afm },
    { title: 'Approved', value: auth.user.approved.toString() },
  ];
  return (
    <Container component="main" maxWidth="sm">
      <Box
        sx={{
          marginTop: 1,
          marginBottom: 2,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          // paddingLeft: '16px',
        }}
      >
        <Typography component="h1" variant="h5">
          Account
        </Typography>
        <Grid container m={4}>
          {list.map((item) => (
            <Grid container m={1}>
              <Grid item xs={12} sm={6}>
                <Typography component="h1" variant="h5">
                  {item.title}:
                </Typography>
              </Grid>
              <Grid item xs={12} sm={6}>
                <Typography component="h1" variant="h6">
                  {item.title === 'Roles'
                    ? item.value
                        .map((value) => ' ' + value)
                        .toString()
                        .trim()
                    : item.value}
                </Typography>
              </Grid>
              <Grid item xs={12}>
                <Divider />
              </Grid>
            </Grid>
          ))}
        </Grid>
      </Box>
    </Container>
  );
};

export default Account;
