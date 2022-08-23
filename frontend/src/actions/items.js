import * as types from '../types';
import axios from 'axios';

export const getAllItems =
  (pageNumber, itemCount, sortField, sortDirection) => async (dispatch) => {
    try {
      dispatch({ type: types.GET_ALL_ITEMS_REQUEST });
      const response = await axios.get(
        `item/all?pageNumber=${pageNumber}&itemCount=${itemCount}&sortField=${sortField}&sortDirection=${sortDirection}`
      );
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

export const getItemsCount = () => async (dispatch) => {
  try {
    dispatch({ type: types.GET_ITEMS_COUNT_REQUEST });
    const response = await axios.get('item/count');
    dispatch({
      type: types.GET_ITEMS_COUNT_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: types.GET_ITEMS_COUNT_FAILURE,
      payload: error.response.data,
    });
  }
};
export const getItemsSearch = (searchTerm) => async (dispatch) => {
  try {
    dispatch({ type: types.GET_ITEMS_SEARCH_REQUEST });
    const response = await axios.get(`item/search?searchTerm=${searchTerm}`);
    dispatch({
      type: types.GET_ITEMS_SEARCH_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: types.GET_ITEMS_SEARCH_FAILURE,
      payload: error.response.data,
    });
  }
};

