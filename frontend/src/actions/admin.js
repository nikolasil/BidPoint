import axios from 'axios';
import * as types from '../types';

export const getAllUsers = () => async (dispatch) => {
  try {
    dispatch({ type: types.ADMIN_FETCH_ALL_USERS_REQUEST });
    const res = await axios.get('user/all');
    dispatch({
      type: types.ADMIN_FETCH_ALL_USERS_SUCCESS,
      payload: res.data,
    });
  } catch (error) {
    dispatch({
      type: types.ADMIN_FETCH_ALL_USERS_FAILURE,
      payload: error.response.data,
    });
  }
};

export const approveUser = (username) => async (dispatch) => {
  try {
    dispatch({ type: types.ADMIN_APPROVE_USER_REQUEST });
    const res = await axios.put(`user/approve?username=${username}`);
    dispatch({ type: types.ADMIN_APPROVE_USER_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({
      type: types.ADMIN_APPROVE_USER_FAILURE,
      payload: error.response,
    });
  }
};
