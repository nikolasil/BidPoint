import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Route, Navigate } from 'react-router-dom';

const PrivateRoute = ({ children }) => {
  const auth = useSelector((state) => state.auth);
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const isLoading = useSelector((state) => state.auth.isLoading);

  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      console.log('You are NOT authenticated');
    } else {
      console.log('You are authenticated');
    }
  }, [auth]);

  return !isLoading && (isAuthenticated ? children : <Navigate to="/" />);
};

export default PrivateRoute;
