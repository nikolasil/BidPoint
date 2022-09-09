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
      const response = await axios.get(
        `item/all?categories=${categories.join(
          '&categories='
        )}&searchTerm=${searchTerm}&active=${active}&isEnded=${isEnded}&username=${username}&pageNumber=${pageNumber}&itemCount=${itemCount}&sortField=${sortField}&sortDirection=${sortDirection}`
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
