import * as types from '../../types';
import initialState from './initialState';

const itemReducer = (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case types.GET_ITEM_REQUEST:
      console.log('itemReducer: GET_ITEM_REQUEST');
      return {
        ...state,
        isLoading: true,
        isFetched: false,
      };
    case types.GET_ITEM_SUCCESS:
      console.log('itemReducer: GET_ITEM_SUCCESS');
      payload?.images?.sort((a, b) => a.id - b.id);
      return {
        ...state,
        isLoading: false,
        isFetched: true,
        item: payload,
      };
    case types.GET_ITEM_FAILURE:
      console.log('itemReducer: GET_ITEM_FAILURE');
      return {
        ...state,
        isLoading: false,
        isFetched: false,
        item: {},
      };
    case types.POST_ITEM_REQUEST:
      console.log('itemReducer: POST_ITEM_REQUEST');
      return {
        ...state,
        isLoading: true,
        isCreated: false,
      };
    case types.POST_ITEM_SUCCESS:
      console.log('itemReducer: POST_ITEM_SUCCESS');
      return {
        ...state,
        isLoading: false,
        isCreated: true,
        item: payload,
      };
    case types.POST_ITEM_FAILURE:
      console.log('itemReducer: POST_ITEM_FAILURE');
      return {
        ...state,
        isLoading: false,
        isCreated: false,
      };
    case types.CREATE_BID_ITEM_REQUEST:
      console.log('itemReducer: CREATE_BID_ITEM_REQUEST');
      return {
        ...state,
        isLoading: true,
      };
    case types.CREATE_BID_ITEM_SUCCESS:
      console.log('itemReducer: CREATE_BID_ITEM_SUCCESS');
      console.log('payload: ', payload);
      return {
        ...state,
        isLoading: false,
        bids: payload,
        createBidError: null,
      };
    case types.CREATE_BID_ITEM_FAILURE:
      console.log('itemReducer: CREATE_BID_ITEM_FAILURE');
      return {
        ...state,
        isLoading: false,
        createBidError: payload,
      };
    case types.GET_BIDS_ITEM_REQUEST:
      console.log('itemReducer: GET_BIDS_ITEM_REQUEST');
      return {
        ...state,
        isLoading: true,
      };

    case types.GET_BIDS_ITEM_SUCCESS:
      console.log('itemReducer: GET_BIDS_ITEM_SUCCESS');
      return {
        ...state,
        isLoading: false,
        bids: payload,
      };
    case types.GET_BIDS_ITEM_FAILURE:
      console.log('itemReducer: GET_BIDS_ITEM_FAILURE');
      return {
        ...state,
        isLoading: false,
      };

    default:
      return state;
  }
};
export default itemReducer;
