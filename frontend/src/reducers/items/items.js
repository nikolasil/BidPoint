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
      localStorage.setItem('searchState', JSON.stringify(payload.searchState));
      return {
        ...state,
        isLoading: false,
        isFetched: true,
        searchState: payload.searchState,
        list: payload.items,
        itemsCount: payload.totalItems,
      };
    case types.GET_ALL_ITEMS_FAILURE:
    case types.GET_ITEMS_SEARCH_FAILURE:
      console.log(
        'itemsReducer: GET_ALL_ITEMS_FAILURE | GET_ITEMS_SEARCH_FAILURE'
      );
      return {
        ...state,
        isLoading: false,
        searchState: {
          pageNumber: 0,
          itemCount: 10,
          sortField: 'dateUpdated',
          sortDirection: 'desc',
          searchTerm: '',
          active: 'TRUE',
          isEnded: 'NONE',
          username: '',
        },
        isFetched: false,
        list: [],
      };
    default:
      return state;
  }
};
export default itemsReducer;
