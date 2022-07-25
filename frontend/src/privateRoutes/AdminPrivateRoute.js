import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Route, Navigate } from 'react-router-dom';

const AdminPrivateRoute = ({ children }) => {
  const auth = useSelector((state) => state.auth);
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const isLoading = useSelector((state) => state.auth.isLoading);
  const isAdmin = useSelector((state) => state.auth.isAdmin);

  useEffect(() => {
    if (!isLoading && !isAuthenticated && !isAdmin) {
      console.log('You are NOT a authenticated admin');
    } else {
      console.log('You are a authenticated admin');
    }
  }, [auth]);

  return !isLoading && (isAuthenticated ? children : <Navigate to="/" />);
};

export default AdminPrivateRoute;
