import React, { useRef, useEffect, useState } from 'react';
import { Box, Container } from '@mui/material';
import { FormControlUnstyledContext } from '@mui/base';

function isOverflowActive(element) {
  return (
    element.offsetHeight < element.scrollHeight ||
    element.offsetWidth < element.scrollWidth
  );
}

const PageContainer = (props) => {
  const page = props.page;

  return (
    <Box
      fullWidth
      style={{
        overflowY: 'auto',
        maxHeight: 'calc(100vh - 64px)',
      }}
    >
      {page}
    </Box>
  );
};

export default PageContainer;
