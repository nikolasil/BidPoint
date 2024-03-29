const initialState = {
  isLoading: true,
  isFetched: false,
  searchState: {
    pageNumber: 0,
    itemCount: 10,
    sortField: 'dateUpdated',
    sortDirection: 'desc',
    searchTerm: '',
    active: 'TRUE',
    isEnded: 'NONE',
    username: '',
    categories: [],
  },
  list: [],
  itemsCount: 0,
};

export default initialState;
