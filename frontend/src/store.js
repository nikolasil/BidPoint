import auth from './reducers/auth/auth';
import applications from './reducers/applications/applications';
import admin from './reducers/admin/admin';
import { configureStore } from '@reduxjs/toolkit';

export default configureStore({
  reducer: {
    auth,
    // applications,
    // admin,
  },
});
