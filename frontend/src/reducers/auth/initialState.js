const initialState = {
  isLoading: false,
  isAuthenticated: !!localStorage.getItem('accessToken'),
  hasSignedUp: false,
  user: null,
};
export default initialState;
