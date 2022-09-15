const initialState = {
  users: {
    isLoading: true,
    isFetched: false,
    searchState: {
      pageNumber: 0,
      itemCount: 10,
      sortField: 'username',
      sortDirection: 'desc',
      approved: 'FALSE',
      searchTerm: '',
    },
    list: [],
    usersCount: 0,
  },
  user: {
    isLoading: false,
    isFetched: false,
    user: {},
  },
};

export default initialState;
