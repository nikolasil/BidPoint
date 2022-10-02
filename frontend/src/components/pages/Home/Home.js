import React, { useEffect } from 'react';
import {
  Container,
  Box,
  Typography,
  Button,
  Paper,
  Tooltip,
  Chip,
} from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { getRecommendations } from '../../../actions/auth';
import { Stack } from '@mui/system';
import { useNavigate } from 'react-router-dom';

const Home = () => {
  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);
  const navigate = useNavigate();

  useEffect(() => {
    dispatch(getRecommendations());
  }, [dispatch]);

  return (
    <Container
      component="main"
      maxWidth={false}
      sx={{
        width: '100%',
        height: '100vh',
        background:
          'linear-gradient(to top, rgba(2,0,36,0.65), rgba(9,9,121,0.65)), url(/homepage.jpg) no-repeat center center fixed',
        '-webkit-background-size': 'cover',
        '-moz-background-size': 'cover',
        '-o-background-size': 'cover',
        'background-size': 'cover',
      }}
    >
      <Box
        sx={{
          paddingTop: 1,
          marginBottom: 2,
          width: '100%',
          height: 'calc(80%)',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          // paddingLeft: '16px',
        }}
      >
        <Typography
          component="h1"
          variant="h1"
          color="white"
          sx={{
            mr: 2,
            display: { xs: 'none', md: 'flex' },
            fontFamily: 'alkalami',
            fontWeight: 700,
            letterSpacing: '.3rem',
            textDecoration: 'none',
          }}
        >
          Let The Bid Begin!
        </Typography>
        <Stack
          marginTop={2}
          minWidth={'90%'}
          direction="row"
          justifyContent="center"
          alignItems="center"
          spacing={2}
        >
          <Button
            variant="contained"
            onClick={() => {
              navigate('items');
            }}
          >
            <Typography
              variant="h5"
              padding={1}
              color="white"
              sx={{
                display: { xs: 'none', md: 'flex' },
                fontFamily: 'alkalami',
                fontWeight: 700,
                letterSpacing: '.2rem',
              }}
            >
              Discover
            </Typography>
          </Button>
          <Button
            variant="contained"
            color="success"
            onClick={() => {
              if (!auth.isAuthenticated) navigate('signin');
              else navigate('account/items/create');
            }}
          >
            <Typography
              variant="h5"
              padding={1}
              color="white"
              sx={{
                display: { xs: 'none', md: 'flex' },
                fontFamily: 'alkalami',
                fontWeight: 700,
                letterSpacing: '.2rem',
              }}
            >
              Sell
            </Typography>
          </Button>
        </Stack>
        {auth.isAuthenticated && auth?.recommendations?.length > 0 && (
          <Typography
            variant="h5"
            color="white"
            sx={{
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'alkalami',
              fontWeight: 700,
              letterSpacing: '.2rem',
            }}
            marginTop={2}
            marginBottom={2}
          >
            Recommendations
          </Typography>
        )}
        <Stack
          minWidth={'90%'}
          direction="row"
          justifyContent="center"
          alignItems="center"
          spacing={2}
        >
          {auth.isAuthenticated &&
            auth?.recommendations?.map((recommendation) => {
              return (
                <Paper
                  elevation={5}
                  style={{ padding: 2 }}
                  sx={{
                    'max-height': '300px',
                    '&:hover': {
                      background: '#EBEBEB',
                      cursor: 'pointer',
                    },
                  }}
                  onClick={() => {
                    navigate(`items/${recommendation.id}`);
                  }}
                >
                  <Typography
                    variant="h5"
                    padding={2}
                    sx={{
                      display: { xs: 'none', md: 'flex' },
                      fontFamily: 'alkalami',
                      fontWeight: 700,
                      letterSpacing: '.2rem',
                    }}
                  >
                    {recommendation.name}
                  </Typography>
                  {recommendation.categories.map((category) => (
                    <Chip key={category} label={category} />
                  ))}
                  <Typography
                    variant="h5"
                    padding={2}
                    sx={{
                      display: { xs: 'none', md: 'flex' },
                      fontFamily: 'alkalami',
                      fontWeight: 700,
                      letterSpacing: '.2rem',
                    }}
                  >
                    {recommendation.currentPrice}$
                  </Typography>
                </Paper>
              );
            })}
        </Stack>
      </Box>
    </Container>
  );
};

export default Home;
