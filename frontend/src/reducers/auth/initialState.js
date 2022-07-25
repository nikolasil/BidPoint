const initialState = {
  isLoading: false,
  isAuthenticated: !!localStorage.getItem('accessToken'),
  isAdmin: false,
};
export default initialState;
