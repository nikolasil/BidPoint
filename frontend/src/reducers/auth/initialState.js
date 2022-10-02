const initialState = {
  isLoading: false,
  isAuthenticated: !!localStorage.getItem('accessToken'),
  user: null,
  recommendations: [],
};
export default initialState;
