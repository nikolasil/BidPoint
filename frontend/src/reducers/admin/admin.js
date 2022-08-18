import * as types from '../../types';
import initialState from './initialState';

const adminReducer = (state = initialState, action) => {
  const { type, payload } = action;

  switch (type) {
    case types.ADMIN_FETCH_ALL_USERS_REQUEST: {
      console.log('adminReducer: ADMIN_FETCH_ALL_USERS_REQUEST');
      return {
        ...state,
        users: {
          ...state.users,
          isLoading: true,
          isFetched: false,
        },
      };
    }

    case types.ADMIN_FETCH_ALL_USERS_SUCCESS: {
      console.log('adminReducer: ADMIN_FETCH_ALL_USERS_SUCCESS');
      console.log('adminReducer: payload = ', payload);
      return {
        ...state,
        users: {
          ...state.users,
          isLoading: false,
          isFetched: true,
          list: payload,
        },
      };
    }

    case types.ADMIN_FETCH_ALL_USERS_FAILURE: {
      console.log('adminReducer: ADMIN_FETCH_ALL_USERS_FAILURE');
      return {
        ...state,
        users: {
          ...state.users,
          isLoading: false,
          isFetched: false,
          list: null,
        },
      };
    }

    case types.ADMIN_APPROVE_USER_REQUEST: {
      console.log('adminReducer: ADMIN_APPROVE_USER_REQUEST');
      return {
        ...state,
        users: {
          ...state.users,
          isLoading: true,
          isFetched: true,
        },
      };
    }
    case types.ADMIN_APPROVE_USER_SUCCESS: {
      console.log('adminReducer: ADMIN_APPROVE_USER_SUCCESS');
      console.log('adminReducer: payload = ', payload);

      return {
        ...state,
        users: {
          ...state.users,
          isLoading: false,
          isFetched: true,
          list: state.users.list.map((user) =>
            user.username === payload.username ? payload : user
          ),
        },
      };
    }
    case types.ADMIN_APPROVE_USER_FAILURE: {
      console.log('adminReducer: ADMIN_APPROVE_USER_FAILURE');
      return {
        ...state,
        users: {
          ...state.users,
          isLoading: false,
          isFetched: true,
        },
      };
    }

    default: {
      return state;
    }
  }
};
export default adminReducer;
