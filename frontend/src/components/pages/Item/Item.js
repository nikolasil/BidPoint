import React, { useEffect } from 'react';
import { Container, Box, Typography, Grid, Divider } from '@mui/material';
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

  const list = [
    { title: 'Name', value: item.item.name },
    { title: 'Description', value: item.item.description },
  ];
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
          Item: {id}
        </Typography>
        {item.item.images && <Carousel images={item.item.images} />}
        <Grid container m={4}>
          {list.map((item) => (
            <Grid container m={1}>
              <Grid item xs={12} sm={6}>
                <Typography component="h1" variant="h5">
                  {item.title}:
                </Typography>
              </Grid>
              <Grid item xs={12} sm={6}>
                <Typography component="h1" variant="h6">
                  {item.value}
                </Typography>
              </Grid>
              <Grid item xs={12}>
                <Divider />
              </Grid>
            </Grid>
          ))}
        </Grid>
      </Box>
    </Container>
  );
};

export default Item;
