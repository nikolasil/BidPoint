import { Button, Stack } from '@mui/material';
import React, { useState } from 'react';

const Carousel = (props) => {
  const [index, setIndex] = useState(0);

  return (
    <Stack direction="column">
      <img
        width="320px"
        height="320px"
        src={
          'data:' +
          props.images[index].fileType +
          ';base64,' +
          props.images[index].fileData
        }
      />
      <Stack
        direction="row"
        justifyContent="space-between"
        alignItems="flex-start"
        maxWidth="320px"
      >
        <Button
          onClick={() => {
            if (index != 0) {
              setIndex(index - 1);
            } else setIndex(props.images.length - 1);
            console.log(index);
          }}
        >
          Back
        </Button>
        <Button
          onClick={() => {
            if (index < props.images.length - 1) {
              setIndex(index + 1);
            } else setIndex(0);
            console.log(index);
          }}
        >
          Next
        </Button>
      </Stack>
    </Stack>
  );
};

export default Carousel;
