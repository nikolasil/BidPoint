import axios from 'axios';
import * as types from '../types';

export const getUsersSearchPageableSorting =
  (approved, username, pageNumber, itemCount, sortField, sortDirection) =>
  async (dispatch) => {
    try {
      dispatch({ type: types.ADMIN_FETCH_ALL_USERS_REQUEST });
      const res = await axios.get(
        `user/all?approved=${approved}&searchTerm=${username}&pageNumber=${pageNumber}&itemCount=${itemCount}&sortField=${sortField}&sortDirection=${sortDirection}`
      );
      dispatch({
        type: types.ADMIN_FETCH_ALL_USERS_SUCCESS,
        payload: res.data,
      });
    } catch (error) {
      dispatch({
        type: types.ADMIN_FETCH_ALL_USERS_FAILURE,
        payload: error.response,
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

export const getUser = (username) => async (dispatch) => {
  try {
    dispatch({ type: types.ADMIN_GET_USER_REQUEST });
    const res = await axios.get(`user?username=${username}`);
    dispatch({ type: types.ADMIN_GET_USER_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({
      type: types.ADMIN_GET_USER_FAILURE,
      payload: error.response,
    });
  }
};
