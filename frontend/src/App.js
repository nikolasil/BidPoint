import React from 'react';

import axios from 'axios';
import PrivateRoute from './privateRoutes/PrivateRoute';
import AdminPrivateRoute from './privateRoutes/AdminPrivateRoute';
import SignIn from './components/pages/Landing/SignIn';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import setDefaultAuthHeader from './utils/setDefaultAuthHeader';
import Admin from './Admin';
import Customer from './Customer';
import SignUp from './components/pages/Landing/SignUp';

function App() {
  axios.defaults.baseURL = 'http://localhost:8002/api/';
  setDefaultAuthHeader();

  return (
    <div className="App">
      <Router>
        <Routes>
          <Route exact path="/" element={<SignIn />} />
          <Route exact path="/signup" element={<SignUp />} />
          <Route
            exact
            path="*"
            element={
              <PrivateRoute>
                <Customer />
              </PrivateRoute>
            }
          />
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
