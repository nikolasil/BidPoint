const initialState = {
  // isLoading: false,
  user: null,
  isLoading: true,
  error: null,
  token: localStorage.getItem('admin-token'),
  isAuthenticated: !!localStorage.getItem('admin-token'),
  applications: {
    isLoading: true,
    error: null,
    applications: [],
    isFetched: false,
  },
  application: {
    isLoading: true,
    error: null,
    message: null,
    application: null,
    isFetched: false,
  },
  approve: {
    isLoading: false,
    error: null,
    isApproved: false,
  },
  reject: {
    isLoading: false,
    error: null,
    isRejected: false,
  },
  comments: {
    isLoading: false,
    error: null,
    isCommentAdded: false,
    message: null,
  },
};

export default initialState;