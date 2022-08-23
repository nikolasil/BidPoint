import * as types from '../../types';
import initialState from './initialState';

const categoriesReducer = (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case types.GET_ALL_CATEGORIES_REQUEST:
      console.log('categoriesReducer: GET_ALL_CATEGORIES_REQUEST');
      return {
        ...state,
        isLoading: true,
        isFetched: false,
      };
    case types.GET_ALL_CATEGORIES_SUCCESS:
      console.log('categoriesReducer: GET_ALL_CATEGORIES_SUCCESS');
      return {
        ...state,
        isLoading: false,
        isFetched: true,
        list: payload,
      };
    case types.GET_ALL_CATEGORIES_FAILURE:
      console.log('categoriesReducer: GET_ALL_CATEGORIES_FAILURE');
      return {
        ...state,
        isLoading: false,
        isFetched: false,
        list: [],
      };

    default:
      return state;
  }
};
export default categoriesReducer;
