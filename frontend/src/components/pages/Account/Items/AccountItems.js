import React, { useEffect } from 'react';
import {
  Container,
  Box,
  Typography,
  Autocomplete,
  TextField,
  InputAdornment,
  OutlinedInput,
  Stack,
  Toolbar,
  Tooltip,
  Switch,
  FormControl,
  Select,
  InputLabel,
  MenuItem,
} from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';
import {
  getItemsSearchPageableSortingFiltering,
  getAllItemsSearchByActive,
} from '../../../../actions/items';
import ItemsTable from '../../../ui/ItemsTable';
import RefreshButton from '../../../ui/RefreshButton';
import { getAllCategories } from '../../../../actions/categories';

const AccountItems = () => {
  const dispatch = useDispatch();
  const items = useSelector((state) => state.items);
  const auth = useSelector((state) => state.auth);
  const categories = useSelector((state) => state.categories);
  const [searchState, setSearchState] = React.useState({
    pageNumber: 0,
    itemCount: 10,
    sortField: 'dateUpdated',
    sortDirection: 'desc',
    searchTerm: '',
    active: 'NONE',
    isEnded: 'NONE',
    username: auth.user.username,
    categories: [],
  });

  const handleCategoryChipClick = (event, value) => {
    event.stopPropagation();
    console.log(value);
    let cat = searchState.categories;
    const index = cat.indexOf(value);

    if (index > -1) cat.splice(index, 1);
    else cat.push(value);

    return setSearchState((old) => ({
      ...old,
      categories: cat,
    }));
  };
  const handleChangePageNumber = (event, newPage) => {
    setSearchState((old) => ({ ...old, pageNumber: newPage }));
  };

  const handleChangeItemCount = (event) => {
    setSearchState((old) => ({
      ...old,
      itemCount: parseInt(event.target.value, 10),
      pageNumber: 0,
    }));
  };

  const handleChangeSort = (sortField) => {
    setSearchState((old) => {
      if (old.sortField === sortField) {
        return {
          ...old,
          sortDirection: old.sortDirection === 'asc' ? 'desc' : 'asc',
          pageNumber: 0,
        };
      }
      return {
        ...old,
        sortField,
        sortDirection: 'asc',
        pageNumber: 0,
      };
    });
  };

  const fetchItems = () => {
    dispatch(
      getItemsSearchPageableSortingFiltering(
        searchState.categories,
        searchState.active,
        searchState.isEnded,
        auth.user.username,
        searchState.searchTerm,
        searchState.pageNumber,
        searchState.itemCount,
        searchState.sortField,
        searchState.sortDirection
      )
    );
  };

  useEffect(() => {
    dispatch(getAllCategories());
  }, []);

  useEffect(() => {
    if (
      searchState.searchTerm === items.searchState.searchTerm &&
      searchState.username === items.searchState.username
    ) {
      fetchItems();
    } else {
      const delayDebounceFn = setTimeout(() => {
        fetchItems();
      }, 600);
      return () => clearTimeout(delayDebounceFn);
    }
  }, [searchState]);

  return (
    <Container component="main" maxWidth="xl">
      <Box
        sx={{
          marginTop: 1,
          marginBottom: 2,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          // paddingLeft: '16px',
        }}
      >
        <Stack
          direction="column"
          justifyContent="center"
          alignItems="center"
          spacing={2}
        >
          <Stack
            minWidth={'90%'}
            direction="row"
            justifyContent="center"
            alignItems="center"
            spacing={2}
          >
            <FormControl fullWidth>
              <InputLabel id="isEnded">Time Left</InputLabel>
              <Select
                labelId="Time Left"
                id="isEnded"
                value={searchState.isEnded}
                label="Time Left"
                onChange={(event) => {
                  setSearchState((old) => ({
                    ...old,
                    isEnded: event.target.value,
                    pageNumber: 0,
                  }));
                }}
              >
                <MenuItem value={'TRUE'}>Has Ended</MenuItem>
                <MenuItem value={'FALSE'}>Hasn't Ended</MenuItem>
                <MenuItem value={'NONE'}>Both</MenuItem>
              </Select>
            </FormControl>

            <FormControl fullWidth>
              <InputLabel id="active">Status</InputLabel>
              <Select
                labelId="Status"
                id="active"
                value={searchState.active}
                label="Status"
                onChange={(event) => {
                  setSearchState((old) => ({
                    ...old,
                    active: event.target.value,
                    pageNumber: 0,
                  }));
                }}
              >
                <MenuItem value={'TRUE'}>Active</MenuItem>
                <MenuItem value={'FALSE'}>Disabled</MenuItem>
                <MenuItem value={'NONE'}>Both</MenuItem>
              </Select>
            </FormControl>

            <TextField
              id="search-items"
              fullWidth
              value={searchState.searchTerm}
              onChange={(event) => {
                setSearchState((old) => ({
                  ...old,
                  searchTerm: event.target.value,
                  pageNumber: 0,
                  sortField: 'id',
                  sortDirection: 'asc',
                }));
              }}
              label="Search on Name, Description"
            />

            <RefreshButton tooltip={'Refresh Items'} fetch={fetchItems} />
          </Stack>
          <Stack
            minWidth={'90%'}
            direction="row"
            justifyContent="center"
            alignItems="center"
            spacing={2}
          >
            <Autocomplete
              multiple
              id="categories"
              name="categories"
              onChange={(event, value) => {
                setSearchState((old) => ({
                  ...old,
                  categories: value,
                }));
              }}
              value={searchState.categories}
              options={categories.list}
              renderInput={(params) => (
                <TextField {...params} label="Categories" />
              )}
            />
          </Stack>
        </Stack>
        <ItemsTable
          loading={items.isLoading}
          fetched={items.isFetched}
          items={items.list}
          count={items.itemsCount}
          pageNumber={searchState.pageNumber}
          itemCount={searchState.itemCount}
          sortField={searchState.sortField}
          sortDirection={searchState.sortDirection}
          handleChangePageNumber={handleChangePageNumber}
          handleChangeItemCount={handleChangeItemCount}
          handleChangeSort={handleChangeSort}
          onClickCategoryChip={handleCategoryChipClick}
        />
      </Box>
    </Container>
  );
};

export default AccountItems;
