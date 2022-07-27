import React, { useEffect } from 'react';
import { logoutUser } from '../../../actions/auth';
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { Box, Typography, Container } from '@mui/material';

const Logout = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const auth = useSelector((state) => state.auth);
  useEffect(() => {
    console.log('Logout');
    dispatch(logoutUser());
  }, []);

  useEffect(() => {
    if (!auth.isLoading && !auth.isAuthenticated) navigate('/');
  }, [auth]);
  return (
    <Container component="main" maxWidth="xs">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography component="h1" variant="h5">
          Logging out...
        </Typography>
      </Box>
    </Container>
  );
};

export default Logout;
