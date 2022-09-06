import * as types from '../types';
import axios from 'axios';

export const getAllItemsByActive =
  (active, pageNumber, itemCount, sortField, sortDirection) =>
  async (dispatch) => {
    try {
      dispatch({ type: types.GET_ALL_ITEMS_REQUEST });
      const response = await axios.get(
        `item/all?active=${active}&pageNumber=${pageNumber}&itemCount=${itemCount}&sortField=${sortField}&sortDirection=${sortDirection}`
      );
      dispatch({
        type: types.GET_ALL_ITEMS_SUCCESS,
        payload: response.data,
      });
    } catch (error) {
      dispatch({
        type: types.GET_ALL_ITEMS_FAILURE,
        payload: error.response,
      });
    }
  };

export const getAllItemsCountByActive = (active) => async (dispatch) => {
  try {
    dispatch({ type: types.GET_ITEMS_COUNT_REQUEST });
    const response = await axios.get(`item/count?active=${active}`);
    dispatch({
      type: types.GET_ITEMS_COUNT_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: types.GET_ITEMS_COUNT_FAILURE,
      payload: error.response,
    });
  }
};
export const getItemsSearch =
  (searchTerm, pageNumber, itemCount, sortField, sortDirection) =>
  async (dispatch) => {
    try {
      dispatch({ type: types.GET_ITEMS_SEARCH_REQUEST });
      const response = await axios.get(
        `item/search?searchTerm=${searchTerm}&pageNumber=${pageNumber}&itemCount=${itemCount}&sortField=${sortField}&sortDirection=${sortDirection}`
      );
      dispatch({
        type: types.GET_ITEMS_SEARCH_SUCCESS,
        payload: response.data,
      });
    } catch (error) {
      dispatch({
        type: types.GET_ITEMS_SEARCH_FAILURE,
        payload: error.response,
      });
    }
  };
