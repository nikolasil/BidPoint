import * as types from '../../types';
import initialState from './initialState';

const itemsReducer = (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case types.GET_ALL_ITEMS_REQUEST:
      return {
        ...state,
      };
    case types.GET_ALL_ITEMS_SUCCESS:
      return {
        ...state,
      };
    case types.GET_ALL_ITEMS_FAILURE:
      return {
        ...state,
      };
    default:
      return state;
  }
};
export default itemsReducer;
