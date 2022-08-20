import React, { useEffect } from 'react';
import { Container, Box, Typography } from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';
// import { getAllItems } from '../../../actions/admin';
import ItemsTable from './ItemsTable';

const Items = () => {
  const dispatch = useDispatch();
  const items = useSelector((state) => state.items);

  useEffect(() => {
    // dispatch(getAllItems());
  }, [dispatch]);

  return (
    <Container component="main" maxWidth="sm">
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
        <Typography component="h1" variant="h5">
          Items
        </Typography>
        {!items.isLoading && items.isFetched && (
          <ItemsTable rows={items.list} />
        )}
      </Box>
    </Container>
  );
};

export default Items;
