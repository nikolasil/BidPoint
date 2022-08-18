import React, { useEffect } from 'react';
import { Container, Box, Typography } from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';
import { getAllItems } from '../../../actions/admin';
import UsersTable from './UsersTable';

const Users = () => {
  const dispatch = useDispatch();
  const users = useSelector((state) => state.admin.users);

  useEffect(() => {
    dispatch(getAllItems());
  }, [dispatch]);

  return (
    <Container component="main" minWidth="sm" maxWidth="lg">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
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
