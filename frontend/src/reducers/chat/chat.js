import * as types from '../../types';
import initialState from './initialState';

const chatReducer = (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case types.GET_ALL_USERS_REQUEST:
      console.log('chatReducer: GET_ALL_USERS_REQUEST');
      return {
        ...state,
        users: {
          ...state.users,
          isLoading: true,
          isFetched: false,
        },
      };
    case types.GET_ALL_USERS_SUCCESS:
      console.log('chatReducer: GET_ALL_USERS_SUCCESS');
      return {
        ...state,
        users: {
          ...state.users,
          isLoading: false,
          isFetched: true,
          list: payload,
        },
      };
    case types.GET_ALL_USERS_FAILURE:
      console.log('chatReducer: GET_ALL_USERS_FAILURE');
      return {
        ...state,
        users: {
          ...state.users,
          isLoading: false,
          isFetched: false,
          list: [],
        },
      };
    case types.GET_MESSAGES_REQUEST:
      console.log('chatReducer: GET_MESSAGES_REQUEST');
      return {
        ...state,
        messages: {
          ...state.messages,
          isLoading: true,
          isFetched: false,
        },
      };
    case types.GET_MESSAGES_SUCCESS:
      console.log('chatReducer: GET_MESSAGES_SUCCESS');
      return {
        ...state,
        messages: {
          ...state.messages,
          isLoading: false,
          isFetched: true,
          list: payload,
        },
      };
    case types.GET_MESSAGES_FAILURE:
      console.log('chatReducer: GET_MESSAGES_FAILURE');
      return {
        ...state,
        messages: {
          ...state.messages,
          isLoading: false,
          isFetched: false,
          list: [],
        },
      };

    default:
      return state;
  }
};
export default chatReducer;
