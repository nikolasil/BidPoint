import * as types from '../actions/types';
import setAuthHeader from '../utils/setAuthHeader';

setAuthHeader();
const initialState = {
  user: null,
  isLoading: true,
  error: null,
  token: localStorage.getItem('token'),
  isAuthenticated: !!localStorage.getItem('token'),
};

export default (state = initialState, action) => {
  const { type, payload } = action;

  switch (type) {
    case types.LOAD_USER_REQUEST:
    case types.LOGIN_USER_REQUEST:
    case types.SIGNUP_USER_REQUEST: {
      console.log(
        'SIGNUP_USER_REQUEST | LOGIN_USER_REQUEST | LOAD_USER_REQUEST'
      );
      return {
        ...state,
        isLoading: true,
        error: null,
      };
    }

    case types.LOGIN_USER_SUCCESS:
    case types.SIGNUP_USER_SUCCESS: {
      console.log('SIGNUP_USER_SUCCESS | LOGIN_USER_SUCCESS');
      localStorage.setItem('token', payload.token);
      setAuthHeader();
      return {
        ...state,
        isLoading: false,
        isAuthenticated: true,
        user: payload.user,
        token: payload.token,
        error: null,
      };
    }
    case types.LOGIN_USER_FAILURE:
    case types.SIGNUP_USER_FAILURE: {
      console.log('SIGNUP_USER_FAILURE | LOGIN_USER_FAILURE');
      return {
        ...state,
        isLoading: false,
        error: payload,
      };
    }
    case types.LOAD_USER_FAILURE:
    case types.LOGOUT: {
      console.log('LOAD_USER_FAILURE | LOGOUT');
      localStorage.removeItem('token');
      return {
        ...state,
        isAuthenticated: false,
        isLoading: false,
        token: null,
        user: {},
      };
    }
    case types.LOAD_USER_SUCCESS: {
      console.log('LOAD_USER_SUCCESS');
      return {
        ...state,
        isLoading: false,
        isAuthenticated: true,
        user: payload.user,
        error: null,
      };
    }
    default: {
      return state;
    }
  }
};
