import * as types from '../types';
import axios from 'axios';

export const getAllCategories = () => async (dispatch) => {
  try {
    dispatch({ type: types.GET_ALL_CATEGORIES_REQUEST });
    const response = await axios.get('category/all');
    dispatch({
      type: types.GET_ALL_CATEGORIES_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: types.GET_ALL_CATEGORIES_FAILURE,
      payload: error.response.data,
    });
  }
};
