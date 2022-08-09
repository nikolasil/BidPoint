// import * as types from '../../types';
// import initialState from './initialState';

// const adminReducer = (state = initialState, action) => {
//   const { type, payload } = action;

//   switch (type) {
//     case types.LOAD_ADMIN_USER_REQUEST:
//     case types.LOGIN_ADMIN_USER_REQUEST: {
//       return {
//         ...state,
//         isLoading: true,
//         error: null,
//       };
//     }

//     case types.LOGIN_ADMIN_USER_SUCCESS: {
//       localStorage.setItem('admin-token', payload.token);
//       return {
//         ...state,
//         isLoading: false,
//         isAuthenticated: true,
//         user: payload.user,
//         token: payload.token,
//         error: null,
//       };
//     }
//     case types.LOGIN_ADMIN_USER_FAILURE: {
//       return {
//         ...state,
//         isLoading: false,
//         error: payload,
//       };
//     }
//     case types.LOAD_ADMIN_USER_FAILURE:
//     case types.LOGOUT_ADMIN: {
//       localStorage.removeItem('admin-token');
//       return {
//         ...state,
//         isAuthenticated: false,
//         isLoading: false,
//         token: null,
//         user: {},
//       };
//     }
//     case types.LOAD_ADMIN_USER_SUCCESS: {
//       return {
//         ...state,
//         isLoading: false,
//         isAuthenticated: true,
//         user: payload.user,
//         error: null,
//       };
//     }
//     case types.GET_ALL_ADMIN_APPLICATIONS_REQUEST: {
//       return {
//         ...state,
//         applications: {
//           ...state.applications,
//           isLoading: true,
//           error: null,
//         },
//       };
//     }
//     case types.GET_ALL_ADMIN_APPLICATIONS_SUCCESS: {
//       return {
//         ...state,
//         applications: {
//           ...state.applications,
//           isLoading: false,
//           applications: payload.applications,
//           isFetched: true,
//           error: null,
//         },
//       };
//     }
//     case types.GET_ALL_ADMIN_APPLICATIONS_FAILURE: {
//       return {
//         ...state,
//         applications: {
//           ...state.applications,
//           isLoading: false,
//           isFetched: false,
//           error: payload,
//         },
//       };
//     }
//     case types.APPROVE_APPLICATION_REQUEST: {
//       return {
//         ...state,
//         approve: {
//           ...state.approve,
//           isLoading: true,
//           error: null,
//         },
//       };
//     }
//     case types.APPROVE_APPLICATION_SUCCESS: {
//       return {
//         ...state,
//         approve: {
//           ...state.approve,
//           isLoading: false,
//           isApproved: true,
//           error: null,
//         },
//         applications: {
//           ...state.applications,
//           applications: state.applications.applications.map((application) => {
//             console.log(payload.application, application);
//             if (application._id === payload.application._id) {
//               return payload.application;
//             }
//             return application;
//           }),
//         },
//         application: {
//           ...state.application,
//           application: payload.application,
//         },
//       };
//     }
//     case types.APPROVE_APPLICATION_FAILURE: {
//       return {
//         ...state,
//         approve: {
//           ...state.approve,
//           isLoading: false,
//           isApproved: false,
//           error: payload,
//         },
//       };
//     }
//     case types.REJECT_APPLICATION_REQUEST: {
//       return {
//         ...state,
//         reject: {
//           ...state.reject,
//           isLoading: true,
//           error: null,
//         },
//       };
//     }

//     case types.REJECT_APPLICATION_SUCCESS: {
//       return {
//         ...state,
//         reject: {
//           ...state.reject,
//           isLoading: false,
//           isRejected: true,
//           error: null,
//         },
//         applications: {
//           ...state.applications,
//           applications: state.applications.applications.map((application) => {
//             console.log(payload.application, application);
//             if (application._id === payload.application._id) {
//               return payload.application;
//             }
//             return application;
//           }),
//         },
//         application: {
//           ...state.application,
//           application: payload.application,
//         },
//       };
//     }
//     case types.REJECT_APPLICATION_FAILURE: {
//       return {
//         ...state,
//         reject: {
//           ...state.reject,
//           isLoading: false,

//           isRejected: false,
//           error: payload,
//         },
//       };
//     }
//     case types.GET_ADMIN_APPLICATION_REQUEST: {
//       return {
//         ...state,
//         application: {
//           isLoading: false,
//           error: null,
//         },
//       };
//     }
//     case types.GET_ADMIN_APPLICATION_SUCCESS: {
//       return {
//         ...state,
//         application: {
//           ...state.application,
//           isLoading: false,
//           error: null,
//           message: payload.message,
//           application: payload.application,
//           isFetched: true,
//         },
//       };
//     }
//     case types.GET_ADMIN_APPLICATION_FAILURE:
//       return {
//         ...state,
//         application: {
//           ...state.application,
//           isLoading: false,
//           error: payload,
//           message: null,
//           application: null,
//           isFetched: false,
//         },
//       };
//     case types.COMMENT_APPLICATION_REQUEST: {
//       return {
//         ...state,
//         comments: {
//           ...state.comments,
//           isLoading: true,
//           error: null,
//           isCommentAdded: false,
//         },
//       };
//     }
//     case types.COMMENT_APPLICATION_SUCCESS: {
//       return {
//         ...state,
//         comments: {
//           ...state.comments,
//           isLoading: false,
//           error: null,
//           message: payload.message,

//           isCommentAdded: true,
//         },
//         application: {
//           ...state.application,
//           application: {
//             ...state.application.application,
//             comments: payload.comments,
//           },
//         },
//       };
//     }
//     case types.COMMENT_APPLICATION_FAILURE: {
//       return {
//         ...state,
//         comments: {
//           ...state.comments,
//           isLoading: false,
//           isCommentAdded: false,
//           error: payload,
//           message: null,
//         },
//       };
//     }
//     default: {
//       return state;
//     }
//   }
// };
// export default adminReducer;
