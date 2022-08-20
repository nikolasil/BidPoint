import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { loadUser } from '../actions/auth';
import { Alert, Snackbar } from '@mui/material';
import { Box, Container } from '@mui/system';

const PrivateRoute = ({ children }) => {
  const [open, setOpen] = React.useState(false);
  const navigate = useNavigate();

  const handleClose = () => {
    setOpen(false);
    navigate('/');
  };

  const auth = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const isLoading = useSelector((state) => state.auth.isLoading);
  useEffect(() => {
    if (!isLoading && isAuthenticated) {
      console.log('You are authenticated');
      if (auth.user.username === '') {
        console.log('Has tokens but redux is empty (RELOAD DETECTED)');
        dispatch(loadUser());
      }
    } else if (!isLoading && !isAuthenticated) {
      console.log('You are NOT authenticated');
      setOpen(true);
    }
  }, [auth]);

  return (
    !isLoading &&
    (isAuthenticated ? (
      children
    ) : (
      <>
        <Snackbar open={open} autoHideDuration={3000} onClose={handleClose}>
          <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
            You are not authenticated. Please login!
          </Alert>
        </Snackbar>
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
            <h1>You will be redirected to Homepage in 3 seconds...</h1>
          </Box>
        </Container>
      </>
    ))
  );
};

export default PrivateRoute;
