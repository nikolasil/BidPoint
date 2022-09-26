import { LoadingButton } from '@mui/lab';
import { Container, Box, Typography, Stack, Button } from '@mui/material';
import { useFormik } from 'formik';
import React, { useEffect, useState } from 'react';
import { importItems, exportItems } from '../../../actions/admin';
import { useSelector, useDispatch } from 'react-redux';
const Items = () => {
  const dispatch = useDispatch();
  const admin = useSelector((state) => state.admin);
  const loading = admin.items.import.isLoading || admin.items.export.isLoading;
  const formikImportXml = useFormik({
    initialValues: {
      file: null,
    },
    onSubmit: (values) => {
      console.log('Import Items Xml');
      dispatch(importItems(values.file, 'xml'));
    },
  });
  const formikExportXml = useFormik({
    initialValues: {
      file: null,
    },
    onSubmit: (values) => {
      console.log('Export Items Xml');
      dispatch(exportItems('xml'));
    },
  });
  const formikImportJson = useFormik({
    initialValues: {
      file: null,
    },
    onSubmit: (values) => {
      console.log('Import Items Json');
      dispatch(importItems(values.file, 'json'));
    },
  });
  const formikExportJson = useFormik({
    initialValues: {
      file: null,
    },
    onSubmit: (values) => {
      console.log('Export Items Json');
      dispatch(exportItems('json'));
    },
  });
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
          <Stack
            minWidth={'90%'}
            direction="row"
            justifyContent="center"
            alignItems="center"
            spacing={2}
          >
            <form onSubmit={formikImportXml.handleSubmit}>
              <LoadingButton
                variant="contained"
                fullWidth
                sx={{ mt: 3, mb: 2 }}
                loadingIndicator="Loading…"
                loading={loading}
                onChange={(event) => {
                  console.log(event.target.files[0]);
                  formikImportXml.setFieldValue('file', event.target.files[0]);
                  formikImportXml.submitForm();
                }}
                component="label"
              >
                Uplaod Items XML
                <input type="file" accept=".xml" hidden />
              </LoadingButton>
            </form>
            <form onSubmit={formikExportXml.handleSubmit}>
              <LoadingButton
                variant="outlined"
                fullWidth
                sx={{ mt: 3, mb: 2 }}
                loadingIndicator="Loading…"
                loading={loading}
                onClick={() => {
                  formikExportXml.submitForm();
                }}
                component="label"
              >
                Export Items XML
              </LoadingButton>
            </form>
          </Stack>
          <Stack
            minWidth={'90%'}
            direction="row"
            justifyContent="center"
            alignItems="center"
            spacing={2}
          >
            <form onSubmit={formikImportJson.handleSubmit}>
              <LoadingButton
                variant="contained"
                fullWidth
                sx={{ mt: 3, mb: 2 }}
                loadingIndicator="Loading…"
                loading={loading}
                onChange={(event) => {
                  console.log(event.target.files[0]);
                  formikImportJson.setFieldValue('file', event.target.files[0]);
                  formikImportJson.submitForm();
                }}
                component="label"
              >
                Uplaod Items JSON
                <input type="file" accept=".json" hidden />
              </LoadingButton>
            </form>
            <form onSubmit={formikExportJson.handleSubmit}>
              <LoadingButton
                variant="outlined"
                fullWidth
                sx={{ mt: 3, mb: 2 }}
                loadingIndicator="Loading…"
                loading={loading}
                onClick={() => {
                  formikExportJson.submitForm();
                }}
                component="label"
              >
                Export Items JSON
              </LoadingButton>
            </form>
          </Stack>
        </Stack>
      </Box>
    </Container>
  );
};

export default Items;
