import auth from './reducers/auth/auth';
import admin from './reducers/admin/admin';
import items from './reducers/items/items';
import item from './reducers/item/item';
import categories from './reducers/categories/categories';
import chat from './reducers/chat/chat';
import { configureStore } from '@reduxjs/toolkit';

export default configureStore({
  reducer: {
    auth,
    admin,
    items,
    item,
    chat,
    categories,
  },
});
