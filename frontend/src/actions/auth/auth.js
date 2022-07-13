import axios from 'axios';
import * as types from '../types';
import setAuthHeader from '../../utils/setAuthHeader';

export const signupUser = (formData) => async (dispatch) => {
  dispatch({ type: types.SIGNUP_USER_REQUEST });
  try {
    const config = {
      headers: {
        'Content-Type': 'application/json',
      },
    };
    console.log(axios.baseURL);
    const res = await axios.post('auth/signup', formData, config);
    dispatch({ type: types.SIGNUP_USER_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({ type: types.SIGNUP_USER_FAILURE, payload: error.response.data });
  }
};

export const loginUser = (formData) => async (dispatch) => {
  dispatch({ type: types.LOGIN_USER_REQUEST });
  try {
    const config = {
      headers: {
        'Content-Type': 'application/json',
      },
    };
    const res = await axios.post('auth', formData, config);
    dispatch({ type: types.LOGIN_USER_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({ type: types.LOGIN_USER_FAILURE, payload: error.response.data });
  }
};

export const logoutUser = () => async (dispatch) => {
  dispatch({ type: types.LOGOUT });
};

export const loadUser = () => async (dispatch) => {
  setAuthHeader();
  dispatch({ type: types.LOAD_USER_REQUEST });
  try {
    const res = await axios.get('auth/me');
    dispatch({ type: types.LOAD_USER_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({ type: types.LOAD_USER_FAILURE, payload: error.response.data });
  }
};
