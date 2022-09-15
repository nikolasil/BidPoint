import React, { useEffect } from 'react';
import {
  Container,
  Box,
  Typography,
  MenuItem,
  TextField,
  Select,
  InputLabel,
  Stack,
  FormControl,
} from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';
import { getUsersSearchPageableSorting } from '../../../actions/admin';
import UsersTable from '../../ui/UsersTable';
import RefreshButton from '../../ui/RefreshButton';

const Users = () => {
  const dispatch = useDispatch();
  const users = useSelector((state) => state.admin.users);
  const [searchState, setSearchState] = React.useState({
    pageNumber: 0,
    itemCount: 10,
    sortField: 'username',
    sortDirection: 'desc',
    approved: 'FALSE',
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

  const fetchUsers = () => {
    dispatch(
      getUsersSearchPageableSorting(
        searchState.approved,
        searchState.searchTerm,
        searchState.pageNumber,
        searchState.itemCount,
        searchState.sortField,
        searchState.sortDirection
      )
    );
  };

  useEffect(() => {
    if (searchState.searchTerm === users.searchState.searchTerm) {
      fetchUsers();
    } else {
      const delayDebounceFn = setTimeout(() => {
        fetchUsers();
      }, 600);
      return () => clearTimeout(delayDebounceFn);
    }
  }, [searchState]);

  return (
    <Container component="main" maxWidth="md">
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
          minWidth={'90%'}
          direction="row"
          justifyContent="center"
          alignItems="center"
          spacing={2}
        >
          <FormControl fullWidth>
            <InputLabel id="approved">Approved</InputLabel>
            <Select
              labelId="Approved"
              id="approved"
              value={searchState.approved}
              label="Approved"
              onChange={(event) => {
                setSearchState((old) => ({
                  ...old,
                  approved: event.target.value,
                  pageNumber: 0,
                }));
              }}
            >
              <MenuItem value={'TRUE'}>Already Approved</MenuItem>
              <MenuItem value={'FALSE'}>Not Approved</MenuItem>
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
                sortField: 'username',
                sortDirection: 'asc',
              }));
            }}
            label="Search on Username"
          />

          <RefreshButton tooltip={'Refresh Users'} fetch={fetchUsers} />
        </Stack>

        <UsersTable
          loading={users.isLoading}
          fetched={users.isFetched}
          users={users.list}
          count={users.usersCount}
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

export default Users;
