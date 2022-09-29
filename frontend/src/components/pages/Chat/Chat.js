import { Container, Typography, Box } from '@mui/material';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  getAllUsers,
  getMessagesOfSenderAndReceiver,
} from '../../../actions/chat';
import { useSocket } from '../../../customHooks/useSocket';
import ChatMessages from './ChatMessages';
import ChatUsersList from './ChatUsersList';

const Chat = () => {
  const dispatch = useDispatch();
  const chat = useSelector((state) => state.chat);
  const { isConnected, sendData, receivedMessage, usersOnline } = useSocket();
  const [selectedUser, setSelectedUser] = React.useState('');

  useEffect(() => {
    dispatch(getAllUsers());
  }, []);

  useEffect(() => {
    if (selectedUser != '')
      dispatch(getMessagesOfSenderAndReceiver(selectedUser));
  }, [selectedUser]);

  useEffect(() => {
    if (isConnected) {
      console.log('connected');
    }
  }, [isConnected]);

  return (
    <Container component="main" maxWidth="95vw">
      <Box
        sx={{
          marginTop: 1,
          marginBottom: 2,
          display: 'flex',
          flexDirection: 'row',
          alignItems: 'space-between',
          // paddingLeft: '16px',
        }}
      >
        <ChatUsersList
          selectedUser={selectedUser}
          setSelectedUser={setSelectedUser}
          users={chat.users.list}
        />
        <ChatMessages
          user={chat.users.list.find((user) => user.username == selectedUser)}
          oldMessages={chat.messages.list}
          receivedMessage={receivedMessage}
          sendData={sendData}
        />
      </Box>
    </Container>
  );
};

export default Chat;
