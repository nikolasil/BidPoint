import React, { useEffect, useState } from 'react';
import {
  Grid,
  TextField,
  Typography,
  Autocomplete,
  Switch,
  FormControlLabel,
  Button,
  Stack,
  CircularProgress,
} from '@mui/material';
import { Box, Container } from '@mui/system';
import { useSelector, useDispatch } from 'react-redux';
import { useFormik } from 'formik';
import * as yup from 'yup';
import { LoadingButton } from '@mui/lab';
import { MobileDateTimePicker } from '@mui/x-date-pickers';
import { useNavigate, useParams } from 'react-router-dom';
import { getItem, postItem } from '../../../../actions/item';
import { getAllCategories } from '../../../../actions/categories';
import moment from 'moment';
import Carousel from '../../../ui/Carousel';

const EditItem = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const categories = useSelector((state) => state.categories);
  const item = useSelector((state) => state.item);
  const [hasSubmitted, setHasSubmitted] = useState(false);
  const [wantsBuyPrice, setWantsBuyPrice] = useState(false);

  let { id } = useParams();

  useEffect(() => {
    dispatch(getItem(id));
    dispatch(getAllCategories());
  }, []);

  useEffect(() => {
    if (!item.isLoading && item.isFetched) {
      formik.setFieldValue('name', item.item.name);
      formik.setFieldValue('description', item.item.description);
      formik.setFieldValue('startingPrice', item.item.startingPrice);
      formik.setFieldValue('buyPrice', item.item.buyPrice);
      item.item.buyPrice > 0 ? setWantsBuyPrice(true) : setWantsBuyPrice(false);
      formik.setFieldValue('categories', item.item.categories);
      formik.setFieldValue('isActive', item.item.active);
      formik.setFieldValue('dateEnds', item.item.dateEnds);
      formik.setFieldValue('images', item.item.images);
    }
  }, [item]);

  const formik = useFormik({
    initialValues: {
      name: '',
      description: '',
      startingPrice: 0,
      buyPrice: 0,
      categories: [],
      isActive: true,
      dateEnds: '',
      images: [],
    },
    validationSchema: yup.object().shape({
      name: yup.string().required('Name is required').nullable(),
      description: yup.string().required('Description is required').nullable(),
      startingPrice: yup
        .number()
        .required('Starting Price is required')
        .positive('Starting Price must be a positive number')
        .nullable(),
      buyPrice: yup.number().nullable(),
      categories: yup
        .array()
        .of(yup.string())
        .required('Please provide at least one category'),
      isActive: yup.boolean().required('Is Active is required').nullable(),
      dateEnds: yup.string().required().nullable(),
    }),
    onSubmit: (values) => {
      console.log('onSubmit');
      // var images = [];
      // Object.assign(images, values.images);
      // var itemData = values;
      // delete itemData.images;

      // console.log(itemData);
      // console.log(images);
      // dispatch(postItem(images, JSON.stringify(itemData)));
      // setHasSubmitted(true);
    },
  });
  console.log(id, item.item);
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
    <Container component="main" maxWidth="md">
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
          Edit Item
        </Typography>
        <form onSubmit={formik.handleSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <Grid item xs={12}>
                <Typography></Typography>
              </Grid>
              <TextField
                margin="normal"
                required
                fullWidth
                id="name"
                label="name"
                name="name"
                autoComplete="name"
                autoFocus
                value={formik.values.name}
                onChange={formik.handleChange}
                error={formik.touched.name && Boolean(formik.errors.name)}
                helperText={formik.touched.name && formik.errors.name}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                margin="normal"
                required
                // multiline
                // minRows={2}
                // maxRows={10}
                fullWidth
                id="description"
                label="description"
                name="description"
                autoComplete="description"
                value={formik.values.description}
                onChange={formik.handleChange}
                error={
                  formik.touched.description &&
                  Boolean(formik.errors.description)
                }
                helperText={
                  formik.touched.description && formik.errors.description
                }
              />
            </Grid>
            <Grid item xs={12}>
              <FormControlLabel
                control={
                  <Switch
                    name="wantBuyPrice"
                    checked={wantsBuyPrice}
                    onClick={() =>
                      setWantsBuyPrice((val) => {
                        formik.values.buyPrice = 0;
                        return !val;
                      })
                    }
                  />
                }
                label="Enable Buy Price feature"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="startingPrice"
                label="Starting Price"
                name="startingPrice"
                autoComplete="startingPrice"
                value={formik.values.startingPrice}
                onChange={formik.handleChange}
                error={
                  formik.touched.startingPrice &&
                  Boolean(formik.errors.startingPrice)
                }
                helperText={
                  formik.touched.startingPrice && formik.errors.startingPrice
                }
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                margin="normal"
                required
                fullWidth
                disabled={!wantsBuyPrice}
                id="buyPrice"
                label="Buy Price"
                name="buyPrice"
                autoComplete="buyPrice"
                value={formik.values.buyPrice}
                onChange={formik.handleChange}
                error={
                  formik.touched.buyPrice && Boolean(formik.errors.buyPrice)
                }
                helperText={formik.touched.buyPrice && formik.errors.buyPrice}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <Autocomplete
                freeSolo
                multiple
                id="categories"
                name="categories"
                onChange={(event, value) => {
                  console.log(value);
                  formik.setFieldValue('categories', value);
                }}
                defaultValue={item.item.categories}
                getOptionLabel={(option) => option}
                options={categories.list}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    label="Categories"
                    required
                    inputProps={{
                      ...params.inputProps,
                      required: formik.values.categories.length === 0,
                    }}
                    value={formik.values.categories}
                    onChange={formik.handleChange}
                    error={
                      formik.touched.categories &&
                      Boolean(formik.errors.categories)
                    }
                    helperText={
                      formik.touched.categories && formik.errors.categories
                    }
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <MobileDateTimePicker
                id="dateEnds"
                disablePast
                required
                onChange={(value) => {
                  formik.setFieldValue('dateEnds', value.toISOString());
                }}
                value={formik.values.dateEnds}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    value={formik.values.dateEnds}
                    label="Date Ends"
                    name="dateEnds"
                    error={
                      formik.touched.dateEnds && Boolean(formik.errors.dateEnds)
                    }
                    helperText={
                      formik.touched.dateEnds && formik.errors.dateEnds
                    }
                    fullWidth
                  />
                )}
              />
            </Grid>
            <Grid item xs={12}>
              <FormControlLabel
                control={
                  <Switch
                    name="isActive"
                    checked={formik.values.isActive}
                    onChange={formik.handleChange}
                  />
                }
                label="Activate item on creation"
              />
            </Grid>
            <Grid item xs={12}>
              <Stack
                direction="column"
                spacing={2}
                justifyContent="center"
                alignItems="flex-start"
              >
                <Button variant="contained" component="label">
                  Upload Images
                  <input
                    hidden
                    accept="image/*"
                    name="images"
                    id="images"
                    multiple
                    type="file"
                    onChange={(event) => {
                      console.log(event.target.files);
                      formik.setFieldValue('images', event.currentTarget.files);
                    }}
                  />
                </Button>
                <Carousel
                  images={formik.values.images}
                  isBase64={true}
                  height={'200px'}
                  width={'200px'}
                />
              </Stack>
            </Grid>

            <LoadingButton
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              loading={item.isLoading}
              loadingIndicator="Loading…"
            >
              Submit
            </LoadingButton>
          </Grid>
        </form>
      </Box>
    </Container>
  );
};

export default EditItem;
