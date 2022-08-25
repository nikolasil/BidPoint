import React, { useEffect } from 'react';
import { Container, Box, Typography } from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';
import { getAllUsers } from '../../../actions/admin';
import UsersTable from './UsersTable';

const Users = () => {
  const dispatch = useDispatch();
  const users = useSelector((state) => state.admin.users);

  useEffect(() => {
    dispatch(getAllUsers());
  }, [dispatch]);

  return (
    <Container component="main" maxWidth="md">
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
          Admin Users
        </Typography>
        {!users.isLoading && users.isFetched && (
          <UsersTable rows={users.list} />
        )}
      </Box>
    </Container>
  );
};

export default Users;
