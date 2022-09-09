import React, { useEffect } from 'react';
import {
  Container,
  Box,
  Typography,
  Grid,
  Divider,
  Stack,
  Paper,
  Chip,
  TextField,
  Tooltip,
  CircularProgress,
  LinearProgress,
} from '@mui/material';
import { LoadingButton } from '@mui/lab';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { getItem, bidItem, getBidsOfItem } from '../../../actions/item';
import Carousel from '../../ui/Carousel';
import BidsTable from './BidsTable';
import { useFormik } from 'formik';
import * as yup from 'yup';
import Countdown from 'react-countdown';
import moment from 'moment';
import CachedIcon from '@mui/icons-material/Cached';
import RefreshButton from '../../ui/RefreshButton';

const Item = (props) => {
  const item = useSelector((state) => state.item);
  const createBidError = useSelector((state) => state.item.createBidError);
  const bidsState = useSelector((state) => state.item.bids);

  const dispatch = useDispatch();
  let { id } = useParams();

  const [pageNumber, setPageNumber] = React.useState(0);
  const [itemCount, setItemCount] = React.useState(5);
  const [bids, setBids] = React.useState([]);
  const [isEnded, setIsEnded] = React.useState(false);

  const handleFetchBids = () => {
    dispatch(getBidsOfItem(id));
  };

  const handleEnded = () => {
    console.log('is ended');
    dispatch(getBidsOfItem(id));
  };

  const handleChangePageNumber = (event, newPage) => {
    setPageNumber(newPage);
  };

  const handleChangeItemCount = (event) => {
    setItemCount(parseInt(event.target.value, 10));
    setPageNumber(0);
  };

  useEffect(() => {
    console.log('First Bids');
    dispatch(getBidsOfItem(id));
  }, []);

  useEffect(() => {
    if (item.item.dateEnds) {
      if (moment(item.item.dateEnds).utc().isBefore(moment().utc())) {
        console.log('is ended');
        setIsEnded(true);
        return;
      }
    }
    setIsEnded(false);
  }, [item.item]);

  useEffect(() => {
    console.log('Item');
    dispatch(getItem(id));
  }, [bidsState]);

  useEffect(() => {
    console.log('Bids');
    if (createBidError != null) dispatch(getBidsOfItem(id));
  }, [createBidError]);

  const handleChangeBids = (totalBids, pageNumber, itemCount) => {
    console.log('Bids');
    if (!totalBids) return [];
    return totalBids.slice(
      pageNumber * itemCount,
      (pageNumber + 1) * itemCount
    );
  };

  useEffect(() => {
    setBids(handleChangeBids(bidsState, pageNumber, itemCount));
  }, [bidsState, pageNumber, itemCount]);

  const formik = useFormik({
    initialValues: {
      amount: 0,
    },
    validationSchema: yup.object().shape({
      amount: yup
        .number()
        .required('Amount is required')
        .positive('Amount must be a positive number')
        .min(
          item.item.currentPrice + 0.01,
          'Amount must be greater than current price'
        )
        .nullable(),
    }),
    onSubmit: (values) => {
      console.log('onSubmit');

      dispatch(bidItem(id, values.amount));
    },
  });
  
  if (id != item.item.id) {
    console.log('id != item.item.id');
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
          <CircularProgress />
        </Box>
      </Container>
    );
  }

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
        {item.item.id ? (
          <>
            <Typography variant="h4">{item.item.name}</Typography>
            <Paper
              elevation={5}
              style={{
                padding: 8,
              }}
              sx={{ width: '100%' }}
            >
              <Stack
                direction="row"
                justifyContent="flex-end"
                alignItems="center"
                spacing={0}
                fullWidth
              >
                <div></div>
                <Countdown
                  date={moment.utc(item.item.dateEnds).toString()}
                  intervalDelay={0}
                  precision={1}
                  onComplete={handleEnded}
                  renderer={({
                    days,
                    hours,
                    minutes,
                    seconds,
                    milliseconds,
                    completed,
                  }) => {
                    if (completed) {
                      // Render a completed state
                      return (
                        <Typography variant="h5">Auction ended</Typography>
                      );
                    } else {
                      // Render a countdown
                      return (
                        <>
                          <Typography variant="h7" paddingRight={1}>
                            Auction ends in:
                          </Typography>
                          <Typography variant="h7">
                            {String(days).padStart(3, '0') +
                              'd ' +
                              String(hours).padStart(2, '0') +
                              'h ' +
                              String(minutes).padStart(2, '0') +
                              'm ' +
                              String(seconds).padStart(2, '0') +
                              '.' +
                              milliseconds / 100 +
                              's'}
                          </Typography>
                        </>
                      );
                    }
                  }}
                />
              </Stack>
              <Grid container>
                <Grid item xs={12} md={4}>
                  {item.item.images && (
                    <Carousel
                      height={'320px'}
                      width={'320px'}
                      images={item.item.images}
                      isBase64={true}
                    />
                  )}
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
                    <Typography variant="h5">
                      {item.item.buyPrice != 0
                        ? 'Buy Price: ' + item.item.buyPrice
                        : 'Buy Price is not available for this item'}
                    </Typography>
                    <Typography variant="h5">
                      Categories:
                      {item.item &&
                        item.item.categories.map((category) => (
                          <Chip key={category} label={category} />
                        ))}
                    </Typography>
                    <form onSubmit={formik.handleSubmit}>
                      <TextField
                        margin="normal"
                        required
                        id="amount"
                        label="Amount"
                        name="amount"
                        autoComplete="amount"
                        value={formik.values.amount}
                        onChange={formik.handleChange}
                        error={
                          formik.touched.amount && Boolean(formik.errors.amount)
                        }
                        helperText={
                          formik.touched.amount && formik.errors.amount
                        }
                      />
                      <LoadingButton
                        disabled={isEnded}
                        type="submit"
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                        loading={item.isLoading}
                        loadingIndicator="Loading…"
                      >
                        {!isEnded ? 'Submit Bid' : 'Cannot Submit Bid'}
                      </LoadingButton>
                      {createBidError && createBidError.message}
                    </form>

                    {isEnded && (
                      <Typography variant="h4">
                        {bids.length == 0
                          ? 'No Winner'
                          : 'Winner:' +
                            bids[0].username +
                            ' -> ' +
                            bids[0].amount +
                            '$'}
                      </Typography>
                    )}

                    <RefreshButton
                      tooltip={'Refresh Bids'}
                      fetch={handleFetchBids}
                    />

                    <BidsTable
                      bids={bids}
                      count={bidsState.length}
                      pageNumber={pageNumber}
                      itemCount={itemCount}
                      handleChangePageNumber={handleChangePageNumber}
                      handleChangeItemCount={handleChangeItemCount}
                    />
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
