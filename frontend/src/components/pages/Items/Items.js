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
} from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';
import {
  getAllItemsByActive,
  getAllItemsSearchByActive,
} from '../../../actions/items';
import ItemsTable from './ItemsTable';

const Items = () => {
  const dispatch = useDispatch();
  const items = useSelector((state) => state.items);
  const [searchState, setSearchState] = React.useState({
    pageNumber: 0,
    itemCount: 10,
    sortField: 'id',
    sortDirection: 'asc',
    searchTerm: '',
  });

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

  const handleChangeSortField = (newSortField) => {
    setSearchState((old) => ({
      ...old,
      sortField: newSortField,
      pageNumber: 0,
    }));
  };

  const handleChangeSortDirection = (newSortDirection) => {
    setSearchState((old) => ({
      ...old,
      sortDirection: newSortDirection,
      pageNumber: 0,
    }));
  };

  useEffect(() => {
    if (searchState.searchTerm === '') {
      dispatch(
        getAllItemsByActive(
          true,
          searchState.pageNumber,
          searchState.itemCount,
          searchState.sortField,
          searchState.sortDirection
        )
      );
    } else {
      dispatch(
        getAllItemsSearchByActive(
          true,
          searchState.searchTerm,
          searchState.pageNumber,
          searchState.itemCount,
          searchState.sortField,
          searchState.sortDirection
        )
      );
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
          direction="row"
          justifyContent="center"
          alignItems="center"
          spacing={2}
        >
          <Typography component="h1" variant="h5">
            Items
          </Typography>
          <TextField
            id="search-items"
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
            label="Search Items"
          />
        </Stack>
        {!items.isLoading && items.isFetched && (
          <ItemsTable
            items={items.list}
            count={items.itemsCount}
            pageNumber={searchState.pageNumber}
            itemCount={searchState.itemCount}
            sortField={searchState.sortField}
            sortDirection={searchState.sortDirection}
            handleChangePageNumber={handleChangePageNumber}
            handleChangeItemCount={handleChangeItemCount}
            handleChangeSortField={handleChangeSortField}
            handleChangeSortDirection={handleChangeSortDirection}
          />
        )}
      </Box>
    </Container>
  );
};

export default Items;
