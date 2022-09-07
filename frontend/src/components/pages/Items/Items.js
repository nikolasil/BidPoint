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
  getAllItemsByActive,
  getAllItemsSearchByActive,
} from '../../../actions/items';
import ItemsTable from './ItemsTable';
import CachedIcon from '@mui/icons-material/Cached';

const Items = () => {
  const dispatch = useDispatch();
  const items = useSelector((state) => state.items);
  const [searchState, setSearchState] = React.useState({
    ...items.searchState,
  });

  var isEqualsJson = (obj1, obj2) => {
    const keys1 = Object.keys(obj1);
    const keys2 = Object.keys(obj2);

    //return true when the two json has same length and all the properties has same value key by key
    return (
      keys1.length === keys2.length &&
      Object.keys(obj1).every((key) => obj1[key] == obj2[key])
    );
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
    if (searchState.searchTerm === '') {
      dispatch(
        getAllItemsByActive(
          'TRUE',
          searchState.isEnded,
          searchState.pageNumber,
          searchState.itemCount,
          searchState.sortField,
          searchState.sortDirection
        )
      );
    } else {
      dispatch(
        getAllItemsSearchByActive(
          'TRUE',
          searchState.isEnded,
          searchState.searchTerm,
          searchState.pageNumber,
          searchState.itemCount,
          searchState.sortField,
          searchState.sortDirection
        )
      );
    }
  };

  useEffect(() => {
    if (!items.isFetched || !isEqualsJson(searchState, items.searchState))
      fetchItems();
    else console.log('no need to fetch');
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
          <Tooltip title="Refresh Items">
            <CachedIcon
              sx={{
                '&:hover': { color: 'blue', cursor: 'pointer' },
              }}
              onClick={() => {
                fetchItems();
              }}
            />
          </Tooltip>

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
        />
      </Box>
    </Container>
  );
};

export default Items;
