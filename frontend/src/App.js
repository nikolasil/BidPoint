import React, { useEffect, useState } from 'react';

import axios from 'axios';
import AdminPrivateRoute from './privateRoutes/AdminPrivateRoute';
import { useDispatch } from 'react-redux';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  useNavigate,
} from 'react-router-dom';
import Admin from './Admin';
import Customer from './Customer';
import NotFound from './components/NotFound/NotFound';
import { loadUser, logoutUser } from './actions/auth';

function App() {
  const dispatch = useDispatch();

  axios.defaults.baseURL = 'http://localhost:8002/api/';
  // Request interceptor for API calls
  axios.interceptors.request.use(
    async (request) => {
      if (request.url != 'auth/refresh-token') {
        if (localStorage.getItem('accessToken')) {
          request.headers = {
            Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
          };
        } else {
          delete request.headers.Authorization;
        }
      }
      return request;
    },
    (error) => {
      Promise.reject(error);
    }
  );
  // Response interceptor for API calls
  axios.interceptors.response.use(
    (response) => {
      return response;
    },
    async function (error) {
      const originalRequest = error.config;
      if (originalRequest.url === 'auth/refresh-token') {
        dispatch(logoutUser());
        return Promise.reject(error);
      }
      if (error.response.status === 403 && originalRequest.url !== 'auth') {
        originalRequest._retry = true;

        if (localStorage.getItem('refreshToken') !== null)
          axios.defaults.headers.common['Authorization'] =
            'Bearer ' + localStorage.getItem('refreshToken');
        const res = await axios.get('auth/refresh-token');
        console.log('REFRESH_TOKEN', res);
        localStorage.setItem('accessToken', res.data['access_token']);
        localStorage.setItem('refreshToken', res.data['refresh_token']);

        if (res.data['access_token'] !== null) {
          axios.defaults.headers.common['Authorization'] =
            'Bearer ' + res.data['access_token'];

          originalRequest.headers['Authorization'] =
            'Bearer ' + res.data['access_token'];
        }
        return axios(originalRequest);
      }
      return Promise.reject(error);
    }
  );

  // in first initial render, check for user in local storage
  useEffect(() => {
    console.log('Check for user in local storage');
    dispatch(loadUser());
  }, []);
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route exact path="*" element={<Customer />} />
          <Route
            exact
            path="/admin/*"
            element={
              // <AdminPrivateRoute>
              <Admin />
              // </AdminPrivateRoute>
            }
          />
          <Route path="*" exact element={<NotFound />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
