import React from 'react';
import { Tooltip } from '@mui/material';
import CachedIcon from '@mui/icons-material/Cached';

const RefreshButton = (props) => {
  return (
    <Tooltip title={props.tooltip}>
      <CachedIcon
        sx={{
          '&:hover': { color: 'blue', cursor: 'pointer' },
        }}
        onClick={() => {
          props.fetch();
        }}
      />
    </Tooltip>
  );
};

export default RefreshButton;
