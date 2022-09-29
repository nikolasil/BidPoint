import React, { useEffect, useState } from 'react';
import {
  Box,
  Container,
  Avatar,
  Typography,
  Grid,
  Link,
  TextField,
  Snackbar,
  Alert,
} from '@mui/material';
import LoadingButton from '@mui/lab/LoadingButton';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import { useSelector, useDispatch } from 'react-redux';
import * as yup from 'yup';
import { loginUser, singUpUser } from '../../../actions/auth';

const SignUp = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const auth = useSelector((state) => state.auth);
  const [hasSubmitted, setHasSubmitted] = useState(false);
  const [open, setOpen] = React.useState(false);

  const handleCloseAlert = () => {
    setOpen(false);
    if (hasSubmitted && !auth.isLoading && auth.isCreated) {
      console.log('Signed up!');
      navigate('/');
    }
  };

  const formik = useFormik({
    initialValues: {
      firstname: '',
      lastname: '',
      username: '',
      password: '',
      confirmPassword: '',
      address: '',
      phone: '',
      mail: '',
      afm: '',
    },
    validationSchema: yup.object().shape({
      firstname: yup.string().required(),
      lastname: yup.string().required(),
      username: yup.string().required(),
      password: yup.string().required(),
      confirmPassword: yup.string().required(),
      address: yup.string().required(),
      phone: yup.string().required(),
      mail: yup.string().required(),
      afm: yup.string().required(),
    }),
    onSubmit: (values) => {
      if (values.password === values.confirmPassword) {
        setHasSubmitted(true);
        console.log('onSubmit');
        console.log(JSON.stringify(values));
        dispatch(singUpUser(JSON.stringify(values)));
      } else {
        formik.setFieldError('confirmPassword', 'Passwords do not match!');
        formik.setFieldError('password', 'Passwords do not match!');
      }
    },
  });

  // check for signup user
  useEffect(() => {
    if (hasSubmitted && !auth.isLoading && auth.user == null) {
      if (auth.status == 'Failed to signup.') {
        formik.setFieldError('username', 'This username is already taken!');
      } else if (
        auth.status ==
        'User created successfully. You can login to your account when an admin approves your profile.'
      ) {
        setOpen(true);
      }
    }
  }, [auth]);

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
        <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <Snackbar
          open={open}
          autoHideDuration={5000}
          onClose={handleCloseAlert}
        >
          <Alert
            onClose={handleCloseAlert}
            severity="success"
            sx={{ width: '100%' }}
          >
            {auth.status}
          </Alert>
        </Snackbar>
        <form onSubmit={formik.handleSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="firstname"
                label="firstname"
                name="firstname"
                autoComplete="firstname"
                autoFocus
                value={formik.values.firstname}
                onChange={formik.handleChange}
                error={
                  formik.touched.firstname && Boolean(formik.errors.firstname)
                }
                helperText={formik.touched.firstname && formik.errors.firstname}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="lastname"
                label="lastname"
                name="lastname"
                autoComplete="lastname"
                value={formik.values.lastname}
                onChange={formik.handleChange}
                error={
                  formik.touched.lastname && Boolean(formik.errors.lastname)
                }
                helperText={formik.touched.lastname && formik.errors.lastname}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="username"
                label="username"
                name="username"
                autoComplete="username"
                value={formik.values.username}
                onChange={formik.handleChange}
                error={
                  formik.touched.username && Boolean(formik.errors.username)
                }
                helperText={formik.touched.username && formik.errors.username}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="password"
                value={formik.values.password}
                onChange={formik.handleChange}
                error={
                  formik.touched.password && Boolean(formik.errors.password)
                }
                helperText={formik.touched.password && formik.errors.password}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                margin="normal"
                required
                fullWidth
                name="confirmPassword"
                label="ConfirmPassword"
                type="password"
                id="confirmPassword"
                autoComplete="confirmPassword"
                value={formik.values.confirmPassword}
                onChange={formik.handleChange}
                error={
                  formik.touched.confirmPassword &&
                  Boolean(formik.errors.confirmPassword)
                }
                helperText={
                  formik.touched.confirmPassword &&
                  formik.errors.confirmPassword
                }
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                margin="normal"
                required
                fullWidth
                name="address"
                label="Address"
                type="address"
                id="address"
                autoComplete="address"
                value={formik.values.address}
                onChange={formik.handleChange}
                error={formik.touched.address && Boolean(formik.errors.address)}
                helperText={formik.touched.address && formik.errors.address}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                margin="normal"
                required
                fullWidth
                name="phone"
                label="Phone"
                type="phone"
                id="phone"
                autoComplete="phone"
                value={formik.values.phone}
                onChange={formik.handleChange}
                error={formik.touched.phone && Boolean(formik.errors.phone)}
                helperText={formik.touched.phone && formik.errors.phone}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                margin="normal"
                required
                fullWidth
                name="mail"
                label="Mail"
                type="mail"
                id="mail"
                autoComplete="mail"
                value={formik.values.mail}
                onChange={formik.handleChange}
                error={formik.touched.mail && Boolean(formik.errors.mail)}
                helperText={formik.touched.mail && formik.errors.mail}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                margin="normal"
                required
                fullWidth
                name="afm"
                label="afm"
                type="afm"
                id="afm"
                autoComplete="afm"
                value={formik.values.afm}
                onChange={formik.handleChange}
                error={formik.touched.afm && Boolean(formik.errors.afm)}
                helperText={formik.touched.afm && formik.errors.afm}
              />
            </Grid>
            <LoadingButton
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              loading={auth.isLoading || open}
              loadingIndicator="Loading…"
            >
              Sign Up
            </LoadingButton>
            <Grid container>
              <Grid item xs />
              <Grid item>
                <Link href="/signin" variant="body2">
                  Already have an account? Sign In
                </Link>
              </Grid>
            </Grid>
          </Grid>
        </form>
      </Box>
    </Container>
  );
};

export default SignUp;
