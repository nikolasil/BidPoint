import axios from 'axios';
import * as types from '../types';

export const singUpUser = (formData) => async (dispatch) => {
  dispatch({ type: types.SIGNUP_USER_REQUEST });
  try {
    const config = {
      headers: {
        'Content-Type': 'application/json',
      },
    };
    const res = await axios.post('user', formData, config);
    dispatch({ type: types.SIGNUP_USER_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({ type: types.SIGNUP_USER_FAILURE, payload: error.response });
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
    dispatch({ type: types.LOGIN_USER_FAILURE, payload: error.response });
  }
};

export const logoutUser = () => async (dispatch) => {
  dispatch({ type: types.LOGOUT });
};

export const loadUser = () => async (dispatch) => {
  dispatch({ type: types.LOAD_USER_REQUEST });
  try {
    const res = await axios.get('user/me');
    dispatch({ type: types.LOAD_USER_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({ type: types.LOAD_USER_FAILURE, payload: error.response });
  }
};

export const getRecommendations = () => async (dispatch) => {
  dispatch({ type: types.GET_RECOMMENDATIONS_REQUEST });
  try {
    const res = await axios.get('recommendation');
    dispatch({ type: types.GET_RECOMMENDATIONS_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({
      type: types.GET_RECOMMENDATIONS_FAILURE,
      payload: error.response,
    });
  }
};

export const createRecommendations = () => async (dispatch) => {
  dispatch({ type: types.CREATE_RECOMMENDATIONS_REQUEST });
  try {
    const res = await axios.post('recommendation');
    dispatch({ type: types.CREATE_RECOMMENDATIONS_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({
      type: types.CREATE_RECOMMENDATIONS_FAILURE,
      payload: error.response,
    });
  }
};
