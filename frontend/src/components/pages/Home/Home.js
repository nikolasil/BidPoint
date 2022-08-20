import React from 'react';
import { Container, Box, Typography } from '@mui/material';

const Home = () => {
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
          Home
        </Typography>
      </Box>
    </Container>
  );
};

export default Home;
