import React, { useEffect, useRef } from 'react';
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
  Button,
  DialogActions,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
} from '@mui/material';
import { LoadingButton } from '@mui/lab';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { getItem, bidItem, getBidsOfItem } from '../../../actions/item';
import Carousel from '../../ui/Carousel';
import BidsTable from '../../ui/BidsTable';
import { useFormik } from 'formik';
import * as yup from 'yup';
import Countdown from 'react-countdown';
import moment from 'moment';
import CachedIcon from '@mui/icons-material/Cached';
import RefreshButton from '../../ui/RefreshButton';
import authReducer from '../../../reducers/auth/auth';
import { MapContainer, TileLayer } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';

const Item = (props) => {
  const item = useSelector((state) => state.item);
  const auth = useSelector((state) => state.auth);
  const createBidError = useSelector((state) => state.item.createBidError);
  const bidsState = useSelector((state) => state.item.bids);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  let { id } = useParams();
  const [dialogOpen, setDialogOpen] = React.useState(false);

  const [pageNumber, setPageNumber] = React.useState(0);
  const [itemCount, setItemCount] = React.useState(5);
  const [bids, setBids] = React.useState([]);
  const [isEnded, setIsEnded] = React.useState({
    value: false,
    message: 'Item is not ended yet.',
  });

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
        setIsEnded({ value: true, message: "Auction's end date passed" });
        return;
      }
    }
    if (
      item.item.buyPrice > 0 &&
      item.item.currentPrice >= item.item.buyPrice
    ) {
      setIsEnded({ value: true, message: 'BuyPrice was activated' });
      return;
    }
    setIsEnded({
      value: false,
      message: 'Item is not ended yet.',
    });
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
      setDialogOpen(true);
    },
  });
  const mapRef = useRef();

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
                if (completed || isEnded.value) {
                  // Render a completed state
                  return (
                    <Typography variant="h5">
                      Auction ended({isEnded.message})
                    </Typography>
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
              <Stack
                direction="column"
                // divider={<Divider orientation="horizontal" flexItem />}
                spacing={2}
              >
                <Carousel
                  height={'320px'}
                  width={'320px'}
                  images={item.item.images}
                  isBase64={true}
                />
                <div>
                  <MapContainer
                    className="map"
                    center={[37.9838, 23.7275]}
                    zoom={4}
                    maxZoom={18}
                    minZoom={2}
                    style={{
                      marginLeft: '24px',
                      width: '320px',
                      height: '320px',
                    }}
                    // ref={mapRef}
                  >
                    <TileLayer
                      url={'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'}
                      attribution={
                        '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                      }
                    ></TileLayer>
                  </MapContainer>
                </div>
              </Stack>
            </Grid>
            <Grid item xs={12} md={8}>
              <Stack
                direction="column"
                // divider={<Divider orientation="horizontal" flexItem />}
                spacing={2}
              >
                <Typography variant="h5">
                  Seller: {item.item.username}
                </Typography>
                <Typography variant="h5">
                  Starting Price: {item.item.startingPrice}$
                </Typography>
                <Typography variant="h5">
                  Current Price: {item.item.currentPrice}$
                </Typography>
                <Typography variant="h5">
                  {item.item.buyPrice != 0
                    ? 'Buy Price: ' + item.item.buyPrice + '$'
                    : 'Buy Price is not available for this item'}
                </Typography>
                <Typography variant="h5">
                  Categories:
                  {item.item &&
                    item.item.categories.map((category) => (
                      <Chip key={category} label={category} />
                    ))}
                </Typography>
                {!isEnded.value &&
                  auth.isAuthenticated &&
                  item.item.username != auth.user.username && (
                    <form onSubmit={formik.handleSubmit}>
                      <TextField
                        margin="normal"
                        required
                        id="amount"
                        label="Amount"
                        name="amount"
                        autoComplete="amount"
                        disabled={isEnded.value}
                        value={formik.values.amount}
                        onChange={formik.handleChange}
                        error={
                          formik.touched.amount && Boolean(formik.errors.amount)
                        }
                        helperText={
                          formik.touched.amount && formik.errors.amount
                        }
                      />

                      <Dialog
                        open={dialogOpen}
                        onClose={() => setDialogOpen(false)}
                        aria-labelledby="alert-dialog-title"
                        aria-describedby="alert-dialog-description"
                      >
                        <DialogTitle id="alert-dialog-title">
                          {"Once you place the bid, you can't undo it."}
                        </DialogTitle>
                        <DialogContent>
                          <DialogContentText id="alert-dialog-description">
                            Do you agree to place the bid?
                          </DialogContentText>
                        </DialogContent>
                        <DialogActions>
                          <Button
                            onClick={() => {
                              dispatch(bidItem(id, formik.values.amount));
                              setDialogOpen(false);
                            }}
                          >
                            Place Bid
                          </Button>
                          <Button
                            onClick={() => {
                              setDialogOpen(false);
                            }}
                            autoFocus
                          >
                            Don't place the Bid
                          </Button>
                        </DialogActions>
                      </Dialog>
                      <LoadingButton
                        disabled={isEnded.value}
                        type="submit"
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                        loading={item.isLoading}
                        loadingIndicator="Loading…"
                      >
                        {!isEnded.value ? 'Place Bid' : 'Cannot Place Bid'}
                      </LoadingButton>
                      {createBidError && createBidError.message}
                    </form>
                  )}
                {!isEnded.value &&
                  auth.isAuthenticated &&
                  item.item.username == auth.user.username && (
                    <Button
                      onClick={() => {
                        navigate('/account/items/edit/' + item.item.id);
                      }}
                    >
                      Edit Item
                    </Button>
                  )}
                {!isEnded.value && !auth.isAuthenticated && (
                  <Button
                    onClick={() => {
                      navigate('/signin');
                    }}
                  >
                    Log In to Place Bid
                  </Button>
                )}

                {isEnded.value && bidsState.length > 0 && (
                  <Typography variant="h4">
                    {bids.length == 0
                      ? 'No Winner'
                      : ' Winner:' +
                        bidsState[0]?.username +
                        ' -> ' +
                        bidsState[0]?.amount +
                        '$'}
                  </Typography>
                )}

                {isEnded.value &&
                  bidsState.length > 0 &&
                  bidsState[0]?.username == auth?.user?.username && (
                    <Button
                      onClick={() => {
                        navigate('/chat/' + item.item.username);
                      }}
                    >
                      Communicate with the seller
                    </Button>
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
                <Typography variant="h6">{item.item.description}</Typography>
              </Grid>
            </Grid>
          </Grid>
        </Paper>
      </Box>
    </Container>
  );
};

export default Item;
