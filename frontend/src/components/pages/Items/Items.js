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
  getAllItemsCountByActive,
  getItemsSearch,
} from '../../../actions/items';
import ItemsTable from './ItemsTable';

const Items = () => {
  const dispatch = useDispatch();
  const items = useSelector((state) => state.items);

  const [pageNumber, setPageNumber] = React.useState(0);
  const [itemCount, setItemCount] = React.useState(10);
  const [sortField, setSortField] = React.useState('id');
  const [sortDirection, setSortDirection] = React.useState('asc');
  const [searchTerm, setSearchTerm] = React.useState('');

  useEffect(() => {
    dispatch(getAllItemsCountByActive(true));
  }, []);

  const handleChangePageNumber = (event, newPage) => {
    setPageNumber(newPage);
  };

  const handleChangeItemCount = (event) => {
    setItemCount(parseInt(event.target.value, 10));
    setPageNumber(0);
  };

  const handleChangeSortField = (newSortField) => {
    setSortField(newSortField);
    setPageNumber(0);
  };

  const handleChangeSortDirection = (newSortDirection) => {
    setSortDirection(newSortDirection);
    setPageNumber(0);
  };

  useEffect(() => {
    dispatch(
      getAllItemsByActive(true, pageNumber, itemCount, sortField, sortDirection)
    );
  }, [pageNumber, itemCount, sortField, sortDirection]);

  useEffect(() => {
    if (searchTerm != '') dispatch(getItemsSearch(searchTerm));
    else
      dispatch(
        getAllItemsByActive(
          true,
          pageNumber,
          itemCount,
          sortField,
          sortDirection
        )
      );
  }, [searchTerm]);

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
            value={searchTerm}
            onChange={(event) => {
              setSearchTerm(event.target.value);
              setPageNumber(0);
              setSortField('id');
              setSortDirection('asc');
            }}
            label="Search Items"
          />
        </Stack>
        {!items.isLoading && items.isFetched && (
          <ItemsTable
            items={items.list}
            count={searchTerm != '' ? items.list.length : items.itemsCount}
            pageNumber={pageNumber}
            itemCount={itemCount}
            sortField={sortField}
            sortDirection={sortDirection}
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
