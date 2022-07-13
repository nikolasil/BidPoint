import * as types from '../actions/types';

const inititalState = {
	application: {
		isLoading: true,
		error: null,
		application: null,
		message: null,
		isUploaded: false,
		isFetched: false,
		newId: null,
	},
	applications: {
		isLoading: false,
		error: null,
		applications: null,
		message: null,
	},
};

export default (state = inititalState, action) => {
	const { type, payload } = action;
	switch (type) {
		case types.FETCH_APPLICATION_REQUEST:
		case types.SUBMIT_NEW_APPLICATION_REQUEST:
			return {
				...state,
				application: {
					...state.application,
					isLoading: true,
					error: null,
					message: null,
					isUploaded: false,
				},
			};
		case types.SUBMIT_NEW_APPLICATION_SUCCESS:
			return {
				...state,
				application: {
					...state.application,
					isLoading: false,
					error: null,
					message: payload.message,
					newId: payload.application._id,
					isUploaded: true,
				},
			};
		case types.SUBMIT_NEW_APPLICATION_FAILURE:
			return {
				...state,
				application: {
					...state.application,
					isLoading: false,
					error: payload,
					message: null,
					application: null,
					isUploaded: false,
				},
			};
		case types.GET_ALL_APPLICATIONS_REQUEST:
			return {
				...state,
				applications: {
					...state.applications,
					isLoading: true,
					error: null,
					message: null,
					applications: null,
				},
			};
		case types.GET_ALL_APPLICATIONS_SUCCESS:
			return {
				...state,
				applications: {
					...state.applications,
					isLoading: false,
					error: null,
					message: payload.message,
					applications: payload.applications,
				},
			};
		case types.GET_ALL_APPLICATIONS_FAILURE:
			return {
				...state,
				applications: {
					...state.applications,
					isLoading: false,
					error: payload,
					message: null,
					applications: null,
				},
			};
		case types.FETCH_APPLICATION_SUCCESS: {
			console.log('reducer', payload.application.attachments);
			return {
				...state,
				application: {
					...state.application,
					isLoading: false,
					error: null,
					message: payload.message,
					application: payload.application,
					isFetched: true,
				},
			};
		}
		case types.FETCH_APPLICATION_FAILURE:
			return {
				...state,
				application: {
					...state.application,
					isLoading: false,
					error: payload,
					message: null,
					application: null,
					isFetched: false,
				},
			};

		default:
			return state;
	}
};
