import React from 'react';
import { Container, Box, Typography } from '@mui/material';

const Home = () => {
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
          Admin Home
        </Typography>
      </Box>
    </Container>
  );
};

export default Home;
