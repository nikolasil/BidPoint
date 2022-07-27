const initialState = {
  isLoading: false,
  isAuthenticated: !!localStorage.getItem('accessToken'),
  hasSignedUp: false,
  firstname: '',
  lastname: '',
  username: '',
  roles: [],
};
export default initialState;
