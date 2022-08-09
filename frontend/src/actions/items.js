import * as types from '../types';
import axios from 'axios';

export const getAllItems = () => async (dispatch) => {
  try {
    dispatch({ type: types.GET_ALL_ITEMS_REQUEST });
    const response = await axios.get('/applications');
    dispatch({
      type: types.GET_ALL_ITEMS_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: types.GET_ALL_ITEMS_FAILURE,
      payload: error.response.data,
    });
  }
};

export const getItem = (id) => async (dispatch) => {
  try {
    dispatch({ type: types.FETCH_ITEM_REQUEST });
    const response = await axios.get(`/applications/${id}`);
    dispatch({ type: types.FETCH_ITEM_SUCCESS, payload: response.data });
  } catch (error) {
    dispatch({
      type: types.FETCH_ITEM_FAILURE,
      payload: error.response.data,
    });
  }
};
