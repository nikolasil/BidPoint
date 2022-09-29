import {
  Container,
  Typography,
  Box,
  List,
  ListItem,
  ListItemAvatar,
  Avatar,
  Divider,
  ListItemText,
  TextField,
} from '@mui/material';
import React, { useEffect, useRef } from 'react';
import { useSelector } from 'react-redux';
import SendIcon from '@mui/icons-material/Send';
import { Preview } from '@mui/icons-material';
import { useFormik } from 'formik';
import moment from 'moment';

const ChatMessages = (props) => {
  const auth = useSelector((state) => state.auth);
  const bottomRef = useRef(null);
  const textRef = useRef(null);
  const { user, oldMessages, receivedMessage, sendData } = props;
  const [messages, setMessages] = React.useState(oldMessages);

  const formik = useFormik({
    initialValues: {
      message: '',
    },
    onSubmit: (values, { resetForm }) => {
      console.log('onSubmit');
      sendData(user.username, values.message);
      resetForm({ message: '' });
    },
  });

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [user, messages]);

  useEffect(() => {
    if (receivedMessage.content != '')
      setMessages((prev) => [...prev, receivedMessage]);
  }, [receivedMessage]);

  useEffect(() => {
    setMessages(oldMessages);
  }, [oldMessages]);
  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        // alignItems: 'space-between',
        // paddingLeft: '16px',
      }}
    >
      <List
        sx={{
          height: '75vh',
          width: '70vw',
          bgcolor: 'background.paper',
          overflow: 'auto',
        }}
      >
        {messages.map((message) => (
          <>
            {message.sender == auth.user.username ? (
              <ListItem
                key={message.id}
                style={{
                  display: 'flex',
                  justifyContent: 'flex-end',
                  textAlign: 'right',
                }}
              >
                <ListItemText
                  primary={moment
                    .utc(
                      message.dateCreated.substring(
                        0,
                        message.dateCreated.length - 5
                      )
                    )
                    .local()
                    .format('DD/MM/YYYY HH:mm:ss')
                    .toString()}
                  secondary={
                    <Typography
                      variant="h7"
                      color="green"
                      sx={{ display: 'inline' }}
                    >
                      {message.content}
                    </Typography>
                  }
                />
                <ListItemAvatar>
                  <Avatar>
                    {auth.user.firstname.substring(0, 1) +
                      auth.user.lastname.substring(0, 1)}
                  </Avatar>
                </ListItemAvatar>
              </ListItem>
            ) : (
              <ListItem alignItems="flex-start">
                <ListItemAvatar>
                  <Avatar>
                    {user.firstname.substring(0, 1) +
                      user.lastname.substring(0, 1)}
                  </Avatar>
                </ListItemAvatar>
                <ListItemText
                  primary={moment
                    .utc(
                      message.dateCreated.substring(
                        0,
                        message.dateCreated.length - 5
                      )
                    )
                    .local()
                    .format('DD/MM/YYYY HH:mm:ss')
                    .toString()}
                  secondary=<Typography
                    variant="h7"
                    color="blue"
                    sx={{ display: 'inline' }}
                  >
                    {message.content}
                  </Typography>
                />
              </ListItem>
            )}
          </>
        ))}
        <div ref={bottomRef} />
      </List>
      <form onSubmit={formik.handleSubmit}>
        <TextField
          fullWidth
          ref={textRef}
          id="message"
          name="message"
          value={formik.values.message}
          onChange={formik.handleChange}
          error={formik.touched.message && Boolean(formik.errors.message)}
          helperText={formik.touched.message && formik.errors.message}
          InputProps={{
            endAdornment: (
              <SendIcon
                sx={{
                  '&:last-child td, &:last-child th': { border: 0 },
                  cursor: 'pointer',
                  '&:hover': {
                    color: 'blue',
                  },
                }}
                onClick={(e) => {
                  e.stopPropagation();
                  formik.submitForm();
                }}
              />
            ),
          }}
        />
      </form>
    </Box>
  );
};

export default ChatMessages;
