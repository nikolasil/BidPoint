import { Container, Box, Typography, Stack, Button } from '@mui/material';
import React from 'react';
import { useNavigate } from 'react-router-dom';

const ManageItems = () => {
  const navigate = useNavigate();
  return (
    <Container component="main" maxWidth="xl">
      <Box
        sx={{
          marginTop: 1,
          marginBottom: 2,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          // paddingLeft: '16px',
        }}
      >
        <Stack
          direction="column"
          justifyContent="center"
          alignItems="center"
          spacing={2}
        >
          <Button
            onClick={() => {
              navigate('/account/items');
            }}
            variant="contained"
            fullWidth
            sx={{ mt: 3, mb: 2 }}
          >
            My Items
          </Button>
          <Button
            onClick={() => {
              navigate('/account/items/create');
            }}
            variant="outlined"
            fullWidth
            sx={{ mt: 3, mb: 2 }}
          >
            Create Item
          </Button>
        </Stack>
      </Box>
    </Container>
  );
};

export default ManageItems;
