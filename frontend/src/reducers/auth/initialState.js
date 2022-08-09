const initialState = {
  isLoading: false,
  isAuthenticated: !!localStorage.getItem('accessToken'),
  user: null,
};
export default initialState;
