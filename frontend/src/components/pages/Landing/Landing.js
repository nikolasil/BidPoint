import React, { useEffect, useState } from 'react';
import {
  Box,
  Container,
  Avatar,
  Typography,
  Grid,
  Link,
  TextField,
} from '@mui/material';
import LoadingButton from '@mui/lab/LoadingButton';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { loginUser } from '../../../actions/auth';
import { useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import { useSelector, useDispatch } from 'react-redux';
import * as yup from 'yup';
import { loadUser } from '../../../actions/auth';

const validationSchema = yup.object().shape({
  username: yup.string().required(),
  password: yup.string().required(),
});

const Landing = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const auth = useSelector((state) => state.auth);
  const [hasSubmitted, setHasSubmitted] = useState(false);

  const formik = useFormik({
    initialValues: {
      username: '',
      password: '',
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      setHasSubmitted(true);
      console.log('onSubmit');
      dispatch(loginUser(JSON.stringify(values, null, 2)));
    },
  });

  // in first initial render, check for user in local storage
  useEffect(() => {
    console.log('Check for user in local storage');
    dispatch(loadUser());
  }, []);

  // check for login user
  useEffect(() => {
    if (hasSubmitted && !auth.isLoading && auth.isAuthenticated) {
      console.log('Check for user in local storage');
      dispatch(loadUser());
      setHasSubmitted(false);
    } else if (!hasSubmitted && !auth.isLoading && auth.isAuthenticated) {
      if (auth.isAdmin) {
        navigate('/admin');
      } else {
        navigate('/home');
      }
    }
  }, [auth]);

  return (
    <Container component="main" maxWidth="xs">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign in
        </Typography>
        <form onSubmit={formik.handleSubmit}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="username"
            label="username"
            name="username"
            autoComplete="username"
            autoFocus
            value={formik.values.username}
            onChange={formik.handleChange}
            error={formik.touched.username && Boolean(formik.errors.username)}
            helperText={formik.touched.username && formik.errors.username}
          />

          <TextField
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
            value={formik.values.password}
            onChange={formik.handleChange}
            error={formik.touched.password && Boolean(formik.errors.password)}
            helperText={formik.touched.password && formik.errors.password}
          />
          <LoadingButton
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
            loading={auth.isLoading}
            loadingIndicator="Loading…"
          >
            Sign In
          </LoadingButton>
          <Grid container>
            <Grid item xs>
              <Link href="#" variant="body2">
                Forgot password?
              </Link>
            </Grid>
            <Grid item>
              <Link href="#" variant="body2">
                {"Don't have an account? Sign Up"}
              </Link>
            </Grid>
          </Grid>
        </form>
      </Box>
    </Container>
  );
};

export default Landing;
