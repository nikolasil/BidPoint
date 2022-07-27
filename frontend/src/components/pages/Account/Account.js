import React from 'react';
import { Container, Box, Typography } from '@mui/material';

const Account = () => {
  return (
    <>
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
            Account
          </Typography>
        </Box>
      </Container>
    </>
  );
};

export default Account;
