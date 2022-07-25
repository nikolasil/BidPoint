const initialState = {
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
export default initialState;
