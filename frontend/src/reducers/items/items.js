import * as types from '../../types';
import initialState from './initialState';

const itemsReducer = (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case types.GET_ALL_ITEMS_REQUEST:
    case types.GET_ITEMS_SEARCH_REQUEST:
      console.log(
        'itemsReducer: GET_ALL_ITEMS_REQUEST | GET_ITEMS_SEARCH_REQUEST'
      );
      return {
        ...state,
        isLoading: true,
        isFetched: false,
      };
    case types.GET_ALL_ITEMS_SUCCESS:
    case types.GET_ITEMS_SEARCH_SUCCESS:
      console.log(
        'itemsReducer: GET_ALL_ITEMS_SUCCESS | GET_ITEMS_SEARCH_SUCCESS'
      );
      return {
        ...state,
        isLoading: false,
        isFetched: true,
        list: payload,
      };
    case types.GET_ALL_ITEMS_FAILURE:
    case types.GET_ITEMS_SEARCH_FAILURE:
      console.log(
        'itemsReducer: GET_ALL_ITEMS_FAILURE | GET_ITEMS_SEARCH_FAILURE'
      );
      return {
        ...state,
        isLoading: false,
        isFetched: false,
        list: [],
      };
    case types.GET_ITEMS_COUNT_REQUEST:
      console.log('itemsReducer: GET_ITEMS_COUNT_REQUEST');
      return {
        ...state,
        itemsCount: 0,
      };
    case types.GET_ITEMS_COUNT_SUCCESS:
      console.log('itemsReducer: GET_ITEMS_COUNT_SUCCESS');
      return {
        ...state,
        itemsCount: payload,
      };
    case types.GET_ITEMS_COUNT_FAILURE:
      console.log('itemsReducer: GET_ITEMS_COUNT_FAILURE');
      return {
        ...state,
        itemsCount: 0,
      };
    default:
      return state;
  }
};
export default itemsReducer;
