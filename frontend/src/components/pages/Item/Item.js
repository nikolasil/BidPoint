import React, { useEffect } from 'react';
import {
  Container,
  Box,
  Typography,
  Grid,
  Divider,
  Stack,
  Paper,
} from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { getItem } from '../../../actions/item';
import Carousel from './Carousel';

const Item = (props) => {
  const item = useSelector((state) => state.item);
  const dispatch = useDispatch();
  let { id } = useParams();

  useEffect(() => {
    console.log('Item');
    dispatch(getItem(id));
  }, []);

  return (
    <Container component="main">
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
        {item.item.name ? (
          <>
            <Typography variant="h4">{item.item.name}</Typography>
            <Paper
              elevation={5}
              style={{
                padding: 8,
              }}
              sx={{ width: '100%' }}
            >
              <Grid container>
                <Grid item xs={12} md={4}>
                  {item.item.images && <Carousel images={item.item.images} />}
                </Grid>
                <Grid item xs={12} md={8}>
                  <Stack
                    direction="column"
                    // divider={<Divider orientation="horizontal" flexItem />}
                    spacing={2}
                  >
                    <Typography variant="h5">
                      Starting Price: {item.item.startingPrice}
                    </Typography>
                    <Typography variant="h5">
                      Current Price: {item.item.currentPrice}
                    </Typography>
                    {item.item.buyPrice != 0 && (
                      <Typography variant="h5">
                        Buy Price: {item.item.buyPrice}
                      </Typography>
                    )}
                    <Typography variant="h5">
                      Categories: {item.item.categories.join(', ')}
                    </Typography>
                  </Stack>
                </Grid>
              </Grid>
              <Grid container m={4}>
                <Grid container m={1}>
                  <Grid item xs={12}>
                    <Typography variant="h5">Description</Typography>
                  </Grid>
                  <Grid item xs={12}>
                    <Divider />
                  </Grid>
                  <Grid item xs={12}>
                    <Typography variant="h6">
                      {item.item.description}
                    </Typography>
                  </Grid>
                </Grid>
              </Grid>
            </Paper>
          </>
        ) : (
          'Loading'
        )}
      </Box>
    </Container>
  );
};

export default Item;
