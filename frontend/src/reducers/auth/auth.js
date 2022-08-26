import * as types from '../../types';
import initialState from './initialState';
import { SignalWifiStatusbarNullTwoTone } from '@mui/icons-material';

const authReducer = (state = initialState, action) => {
  const { type, payload } = action;

  switch (type) {
    case types.LOAD_USER_REQUEST:
    case types.LOGIN_USER_REQUEST:
    case types.SIGNUP_USER_REQUEST: {
      console.log(
        'authReducer: SIGNUP_USER_REQUEST | LOGIN_USER_REQUEST | LOAD_USER_REQUEST'
      );

      return {
        ...state,
        isLoading: true,
      };
    }

    case types.LOGIN_USER_SUCCESS: {
      console.log('authReducer: LOGIN_USER_SUCCESS');
      console.log('authReducer: payload = ', payload);

      localStorage.setItem('accessToken', payload.access_token);
      localStorage.setItem('refreshToken', payload.refresh_token);
      return {
        ...state,
        isLoading: false,
        isAuthenticated: true,
        user: payload.user,
      };
    }
    case types.SIGNUP_USER_SUCCESS: {
      console.log('authReducer: SIGNUP_USER_SUCCESS');
      console.log('authReducer: payload = ', payload);
      localStorage.setItem('accessToken', payload.access_token);
      localStorage.setItem('refreshToken', payload.refresh_token);

      return {
        ...state,
        isLoading: false,
        isAuthenticated: true,
        user: payload,
      };
    }
    case types.LOGIN_USER_FAILURE:
    case types.SIGNUP_USER_FAILURE: {
      console.log('authReducer: SIGNUP_USER_FAILURE | LOGIN_USER_FAILURE');
      console.log('authReducer: payload = ', payload);

      return {
        ...state,
        isLoading: false,
      };
    }
    case types.LOAD_USER_FAILURE:
    case types.LOGOUT: {
      console.log('authReducer: LOAD_USER_FAILURE | LOGOUT');
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');

      return {
        ...state,
        isAuthenticated: false,
        isLoading: false,
        user: null,
      };
    }
    case types.LOAD_USER_SUCCESS: {
      console.log('authReducer: LOAD_USER_SUCCESS');
      console.log('authReducer: payload = ', payload);
      return {
        ...state,
        isLoading: false,
        isAuthenticated: true,
        user: payload,
      };
    }
    default: {
      return state;
    }
  }
};
export default authReducer;
