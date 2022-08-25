import * as types from '../types';
import axios from 'axios';

export const postItem = (images, itemData) => async (dispatch) => {
  dispatch({ type: types.POST_ITEM_REQUEST });
  try {
    var formData = new FormData();
    const config = {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    };
    console.log('images', images);
    images.forEach((image) => {
      console.log(image);
      formData.append('images', image);
    });

    formData.append(
      'item',
      new Blob([itemData], {
        type: 'application/json',
      })
    );

    const res = await axios.post('item', formData, config);
    dispatch({ type: types.POST_ITEM_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({ type: types.POST_ITEM_FAILURE, payload: error.response });
  }
};

export const getItem = (id) => async (dispatch) => {
  dispatch({ type: types.GET_ITEM_REQUEST });
  try {
    const res = await axios.get(`item?itemId=${id}`);
    dispatch({ type: types.GET_ITEM_SUCCESS, payload: res.data });
  } catch (error) {
    dispatch({ type: types.GET_ITEM_FAILURE, payload: error.response });
  }
};
