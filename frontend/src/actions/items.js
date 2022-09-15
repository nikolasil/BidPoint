import * as types from '../types';
import axios from 'axios';

export const getItemsSearchPageableSortingFiltering =
  (
    categories,
    active,
    isEnded,
    username,
    searchTerm,
    pageNumber,
    itemCount,
    sortField,
    sortDirection
  ) =>
  async (dispatch) => {
    try {
      dispatch({ type: types.GET_ALL_ITEMS_REQUEST });
      console.log(categories);
      categories = categories.map((element) => {
        return encodeURIComponent(element);
      });
      console.log(categories);
      if (categories.length != 0) categories = categories.join('&categories=');
      else categories = '';
      const response = await axios.get(
        `item/all?categories=${categories}&searchTerm=${encodeURIComponent(
          searchTerm
        )}&active=${active}&isEnded=${isEnded}&username=${encodeURIComponent(
          username
        )}&pageNumber=${pageNumber}&itemCount=${itemCount}&sortField=${sortField}&sortDirection=${sortDirection}`
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
