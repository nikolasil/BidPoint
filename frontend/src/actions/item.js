import * as types from '../types';
import axios from 'axios';

export const postItem = (formData) => async (dispatch) => {
  dispatch({ type: types.POST_ITEM_REQUEST });
  try {
    const config = {
      headers: {
        'Content-Type': 'application/json',
      },
    };
    const res = await axios.post('item', formData, config);
    dispatch({ type: types.POST_ITEM_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({ type: types.POST_ITEM_FAILURE, payload: error.response.data });
  }
};

export const getItem = (id) => async (dispatch) => {
  dispatch({ type: types.GET_ITEM_REQUEST });
  try {
    const res = await axios.get(`item?itemId=${id}`);
    dispatch({ type: types.GET_ITEM_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({ type: types.GET_ITEM_FAILURE, payload: error.response.data });
  }
};
