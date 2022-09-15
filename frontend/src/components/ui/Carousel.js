import { Button, Stack } from '@mui/material';
import React, { useState } from 'react';
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
const Carousel = (props) => {
  const [index, setIndex] = useState(0);

  return (
    <Stack
      direction="row"
      justifyContent="space-between"
      alignItems="center"
      maxWidth={props.width}
    >
      <NavigateBeforeIcon
        sx={{
          visibility: props.images.length <= 1 ? 'hidden' : 'visible',
          '&:hover': { color: 'blue', cursor: 'pointer' },
        }}
        onClick={(event) => {
          event.stopPropagation();
          if (index != 0) {
            setIndex(index - 1);
          } else setIndex(props.images.length - 1);
          console.log(index);
        }}
      >
        Back
      </NavigateBeforeIcon>
      {props.images.length > 0 ? (
        <img
          width={props.width}
          height={props.height}
          src={
            props.isBase64
              ? 'data:' +
                props.images[index].fileType +
                ';base64,' +
                props.images[index].fileData
              : props.images[index]
          }
        />
      ) : (
        <img
          width={props.width}
          height={props.height}
          src="/no-image-available.jpg"
        />
      )}
      <NavigateNextIcon
        sx={{
          visibility: props.images.length <= 1 ? 'hidden' : 'visible',
          '&:hover': { color: 'blue', cursor: 'pointer' },
        }}
        onClick={(event) => {
          event.stopPropagation();
          if (index < props.images.length - 1) {
            setIndex(index + 1);
          } else setIndex(0);
          console.log(index);
        }}
      >
        Next
      </NavigateNextIcon>
    </Stack>
  );
};

export default Carousel;
