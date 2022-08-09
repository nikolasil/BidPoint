import React, { useEffect } from 'react';
import { Container, Box, Typography } from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';

const Items = () => {
  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);

  useEffect(() => {
    // dispatch
  }, []);

  return (
    <Container component="main" maxWidth="xs">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography component="h1" variant="h5">
          Items
        </Typography>
      </Box>
    </Container>
  );
};

export default Items;
