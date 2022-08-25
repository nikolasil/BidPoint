import { Button } from '@mui/material';
import React, { useState } from 'react';

const Carousel = (props) => {
  const [index, setIndex] = useState(0);

  return (
    <div>
      <img
        width="200px"
        height="200px"
        src={
          'data:' +
          props.images[index].fileType +
          ';base64,' +
          props.images[index].fileData
        }
      />
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
    </div>
  );
};

export default Carousel;
