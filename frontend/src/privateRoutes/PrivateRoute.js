import React, { useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { loadUser } from '../actions/auth';
const PrivateRoute = ({ children }) => {
  const auth = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const isLoading = useSelector((state) => state.auth.isLoading);
  useEffect(() => {
    if (!isLoading && isAuthenticated) {
      console.log('You are authenticated');
      if (auth.username === '') {
        console.log('Has tokens but redux is empty (RELOAD DETECTED)');
        dispatch(loadUser());
      }
    } else if (!isLoading && !isAuthenticated) {
      console.log('You are NOT authenticated');
    }
  }, [auth]);

  return !isLoading && (isAuthenticated ? children : <Navigate to="/" />);
};

export default PrivateRoute;
