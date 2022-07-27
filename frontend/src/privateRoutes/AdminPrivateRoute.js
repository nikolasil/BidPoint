import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Route, Navigate } from 'react-router-dom';

const AdminPrivateRoute = ({ children }) => {
  const auth = useSelector((state) => state.auth);
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const isLoading = useSelector((state) => state.auth.isLoading);

  useEffect(() => {
    if (!isLoading && isAuthenticated) {
      console.log('You are a authenticated admin');
    } else if (
      !isLoading &&
      !isAuthenticated &&
      !auth.roles.includes('admin')
    ) {
      console.log('You are NOT a authenticated admin');
    }
  }, [auth]);

  return !isLoading && (isAuthenticated ? children : <Navigate to="/" />);
};

export default AdminPrivateRoute;
