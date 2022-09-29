import * as types from '../types';
import axios from 'axios';

export const getMessagesOfSenderAndReceiver = (sender) => async (dispatch) => {
  dispatch({ type: types.GET_MESSAGES_REQUEST });
  try {
    const res = await axios.get(`chat/${sender}`);
    dispatch({ type: types.GET_MESSAGES_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({ type: types.GET_MESSAGES_FAILURE, payload: error.response });
  }
};

export const getAllUsers = () => async (dispatch) => {
  dispatch({ type: types.GET_ALL_USERS_REQUEST });
  try {
    const res = await axios.get(`chat/users/all`);
    dispatch({ type: types.GET_ALL_USERS_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({ type: types.GET_ALL_USERS_FAILURE, payload: error.response });
  }
};
