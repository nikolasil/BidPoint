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
  ];
  return (
    <>
      <Container component="main" maxWidth="sm">
        <Box
          m={8}
          sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
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
    </>
  );
};

export default Account;
