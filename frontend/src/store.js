import auth from './reducers/auth/auth';
import items from './reducers/items/items';
import admin from './reducers/admin/admin';
import { configureStore } from '@reduxjs/toolkit';

export default configureStore({
  reducer: {
    auth,
    items,
    admin,
  },
});
