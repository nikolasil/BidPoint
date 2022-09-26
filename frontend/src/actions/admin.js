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

export const importItems = (file, type) => async (dispatch) => {
  try {
    dispatch({ type: types.ADMIN_IMPORT_ITEMS_REQUEST });
    const config = {
      headers: {
        'Content-Type': 'application/' + type,
      },
    };
    const res = await axios.post(`item/import`, file, config);
    dispatch({ type: types.ADMIN_IMPORT_ITEMS_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({
      type: types.ADMIN_IMPORT_ITEMS_FAILURE,
      payload: error.response,
    });
  }
};

export const exportItems = (type) => async (dispatch) => {
  try {
    dispatch({ type: types.ADMIN_EXPORT_ITEMS_REQUEST });
    const config = {
      responseType: 'blob',
    };
    const res = await axios.get(`item/export/` + type, config);
    const url = window.URL.createObjectURL(res.data);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'items.' + type);
    document.body.appendChild(link);
    link.click();
    dispatch({ type: types.ADMIN_EXPORT_ITEMS_SUCCESS, payload: null });
  } catch (error) {
    dispatch({
      type: types.ADMIN_EXPORT_ITEMS_FAILURE,
      payload: error.response,
    });
  }
};
