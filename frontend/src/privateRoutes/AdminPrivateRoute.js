import { Typography } from '@mui/material';
import React, { useEffect, useRef } from 'react';
import { useSelector } from 'react-redux';
import { Route, Navigate } from 'react-router-dom';

const AdminPrivateRoute = ({ props, children }) => {
  const auth = useSelector((state) => state.auth);
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const isLoading = useSelector((state) => state.auth.isLoading);
  useEffect(() => {
    console.log(auth);
    if (!isLoading && isAuthenticated && auth.user?.roles?.includes('admin')) {
      console.log('You are a authenticated admin');
    } else {
      console.log('You are NOT a authenticated admin');
    }
  }, [auth]);

  if (!isLoading && isAuthenticated && auth.user?.roles?.includes('admin')) {
    return children;
  }
  return (
    <>
      <Typography>You are NOT a authenticated admin</Typography>
    </>
  );
};

export default AdminPrivateRoute;
