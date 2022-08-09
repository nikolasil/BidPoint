import React, { useEffect, useState } from 'react';

import axios from 'axios';
import AdminPrivateRoute from './privateRoutes/AdminPrivateRoute';
import { useDispatch } from 'react-redux';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import setDefaultAuthHeader from './utils/setDefaultAuthHeader';
import Admin from './Admin';
import Customer from './Customer';
import { loadUser } from './actions/auth';

function App() {
  axios.defaults.baseURL = 'http://localhost:8002/api/';
  setDefaultAuthHeader();
  const dispatch = useDispatch();
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
              <AdminPrivateRoute>
                <Admin />
              </AdminPrivateRoute>
            }
          />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
