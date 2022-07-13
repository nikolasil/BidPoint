import axios from 'axios';
import * as types from '../types';
import setAdminHeader from '../../utils/setAdminHeader';

export const loginAdminUser = (formData) => async (dispatch) => {
	setAdminHeader();
	dispatch({ type: types.LOGIN_ADMIN_USER_REQUEST });
	try {
		const config = {
			headers: {
				'Content-Type': 'application/json',
			},
		};
		const res = await axios.post('admin/login', formData, config);
		dispatch({ type: types.LOGIN_ADMIN_USER_SUCCESS, payload: res.data });
	} catch (error) {
		dispatch({ type: types.LOGIN_ADMIN_USER_FAILURE, payload: error.response.data });
	}
};

export const logoutAdminUser = () => async (dispatch) => {
	dispatch({ type: types.LOGOUT_ADMIN });
};

export const loadAdminUser = () => async (dispatch) => {
	setAdminHeader();
	dispatch({ type: types.LOAD_ADMIN_USER_REQUEST });
	try {
		const res = await axios.get('admin/me');
		dispatch({ type: types.LOAD_ADMIN_USER_SUCCESS, payload: res.data });
	} catch (error) {
		dispatch({ type: types.LOAD_ADMIN_USER_FAILURE, payload: error.response.data });
	}
};

export const getAllApplications = () => async (dispatch) => {
	try {
		dispatch({ type: types.GET_ALL_ADMIN_APPLICATIONS_REQUEST });
		const res = await axios.get('admin/applications');
		dispatch({ type: types.GET_ALL_ADMIN_APPLICATIONS_SUCCESS, payload: res.data });
	} catch (error) {
		dispatch({ type: types.GET_ALL_ADMIN_APPLICATIONS_FAILURE, payload: error.response.data });
	}
};

export const approveApplication = (id) => async (dispatch) => {
	try {
		dispatch({ type: types.APPROVE_APPLICATION_REQUEST });
		const res = await axios.get(`admin/applications/${id}/approve`);
		dispatch({ type: types.APPROVE_APPLICATION_SUCCESS, payload: res.data });
	} catch (error) {
		dispatch({ type: types.APPROVE_APPLICATION_FAILURE, payload: error.response.data });
	}
};
export const rejectApplication = (id) => async (dispatch) => {
	try {
		dispatch({ type: types.REJECT_APPLICATION_REQUEST });
		const res = await axios.get(`admin/applications/${id}/reject`);
		dispatch({ type: types.REJECT_APPLICATION_SUCCESS, payload: res.data });
	} catch (error) {
		dispatch({ type: types.REJECT_APPLICATION_FAILURE, payload: error.response.data });
	}
};

export const getApplication = (id) => async (dispatch) => {
	try {
		dispatch({ type: types.GET_ADMIN_APPLICATION_REQUEST });
		const res = await axios.get(`admin/applications/${id}`);
		dispatch({ type: types.GET_ADMIN_APPLICATION_SUCCESS, payload: res.data });
	} catch (error) {
		dispatch({ type: types.GET_ADMIN_APPLICATION_FAILURE, payload: error.response.data });
	}
};

export const commentApplication = (id, comments) => async (dispatch) => {
	try {
		dispatch({ type: types.COMMENT_APPLICATION_REQUEST });
		const config = {
			headers: {
				'Content-Type': 'application/json',
			},
		};
		const res = await axios.post(`admin/applications/${id}/comment`, { comments }, config);
		dispatch({ type: types.COMMENT_APPLICATION_SUCCESS, payload: res.data });
	} catch (error) {
		dispatch({ type: types.COMMENT_APPLICATION_FAILURE, payload: error.response.data });
	}
};
