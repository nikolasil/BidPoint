import React from 'react';
import { Container, Box, Typography, Button, Tooltip } from '@mui/material';
import { useDispatch } from 'react-redux';
import { createRecommendations } from '../../../actions/auth';

const Home = () => {
  const dispatch = useDispatch();


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
          Admin Panel
        </Typography>
        <Tooltip title={'Force create new reccmmendation table'}>
          <Button
            onClick={() => {
              dispatch(createRecommendations());
            }}
          >
            Create Recommendations
          </Button>
        </Tooltip>
      </Box>
    </Container>
  );
};

export default Home;
