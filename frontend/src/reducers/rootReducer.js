import { combineReducers } from 'redux';
import auth from './auth';
import applications from './applications';
import admin from './admin';
import * as types from '../actions/types';

const appReducer = combineReducers({
  auth,
  admin,
  applications,
});

export default appReducer;
