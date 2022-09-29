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
        isAuthenticated: false,
        isCreated: false,
        user: null,
        status: '',
      };
    }

    case types.LOGIN_USER_SUCCESS: {
      console.log('authReducer: LOGIN_USER_SUCCESS');
      console.log('authReducer: payload = ', payload);
      if (payload == 'Not Approved') {
        return {
          ...state,
          isLoading: false,
          isAuthenticated: false,
          user: null,
          status: 'Yout account has not been approved yet!',
        };
      }
      localStorage.setItem('accessToken', payload.access_token);
      localStorage.setItem('refreshToken', payload.refresh_token);
      return {
        ...state,
        isLoading: false,
        isAuthenticated: true,
        user: payload.user,
        status: '',
      };
    }
    case types.SIGNUP_USER_SUCCESS: {
      console.log('authReducer: SIGNUP_USER_SUCCESS');
      console.log('authReducer: payload = ', payload);

      if (payload?.status === 208) {
        return {
          ...state,
          isLoading: false,
          isCreated: false,
          user: null,
          status:
            'Username already exists. Please try again with a different username.',
        };
      }

      return {
        ...state,
        isLoading: false,
        isCreated: true,
        user: null,
        status:
          'User created successfully. You can login to your account when an admin approves your profile.',
      };
    }
    case types.LOGIN_USER_FAILURE: {
      console.log('authReducer: SIGNUP_USER_FAILURE | LOGIN_USER_FAILURE');
      console.log('authReducer: payload = ', payload);

      return {
        ...state,
        isLoading: false,
        isAuthenticated: false,
        isCreated: false,
        user: null,
        status: 'Failed to login. Check your username and password.',
      };
    }
    case types.SIGNUP_USER_FAILURE: {
      console.log('authReducer: SIGNUP_USER_FAILURE | LOGIN_USER_FAILURE');
      console.log('authReducer: payload = ', payload);

      return {
        ...state,
        isLoading: false,
        isAuthenticated: false,
        isCreated: false,
        user: null,
        status: 'Failed to signup.',
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
        isCreated: false,
        isLoading: false,
        user: null,
        status: '',
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
