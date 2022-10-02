import React, { useState } from 'react';
import Avatar from '@mui/material/Avatar';

var stringToColour = function (str) {
  if (str == '') return 'gray';
  var hash = 0;
  for (var i = 0; i < str.length; i++) {
    hash = str.charCodeAt(i) + ((hash << 5) - hash);
  }
  var colour = '#';
  for (var i = 0; i < 3; i++) {
    var value = (hash >> (i * 8)) & 0xff;
    colour += ('00' + value.toString(16)).substr(-2);
  }
  return colour;
};

const MyAvatar = (props) => {
  return (
    <Avatar
      style={{
        backgroundColor: stringToColour(props.username),
      }}
    >
      {props.name}
    </Avatar>
  );
};

export default MyAvatar;
