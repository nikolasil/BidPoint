import React, { useEffect } from 'react';
import {
  Container,
  Box,
  Typography,
  Grid,
  Divider,
  CircularProgress,
  Button,
} from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { getUser, approveUser } from '../../../actions/admin';
import { useParams } from 'react-router-dom';
const User = () => {
  const user = useSelector((state) => state.admin.user);

  const dispatch = useDispatch();

  let { username } = useParams();
  let list = [];

  useEffect(() => {
    console.log('User');
    dispatch(getUser(username));
  }, []);

  if (username != user.user.username) {
    console.log('username != user.user.username');
    return (
      <Container component="main">
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
          <CircularProgress />
        </Box>
      </Container>
    );
  }

  list = [
    { title: 'Username', value: user.user.username },
    { title: 'Firstname', value: user.user.firstname },
    { title: 'Lastname', value: user.user.lastname },
    { title: 'Roles', value: user.user.roles },
    { title: 'Mail', value: user.user.mail },
    { title: 'Phone', value: user.user.phone },
    { title: 'Address', value: user.user.address },
    { title: 'afm', value: user.user.afm },
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
          Username: {user.user.username}
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
          <Grid container m={1}>
            <Grid item xs={12}>
              {user.user.approved ? (
                <Button variant="outlined" disabled color="success">
                  Already Approved
                </Button>
              ) : (
                <Button
                  variant="outlined"
                  color="success"
                  onClick={() => {
                    dispatch(approveUser(username));
                  }}
                >
                  Approve User
                </Button>
              )}
            </Grid>
          </Grid>
        </Grid>
      </Box>
    </Container>
  );
};

export default User;
