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
} from '@mui/material';
import React, { useEffect } from 'react';

const ChatUsersList = (props) => {
  const { selectedUser, setSelectedUser, users } = props;
  if (users.length === 0) return <></>;
  return (
    <List
      sx={{
        width: '25vw',
        height: '85vh',
        bgcolor: 'background.paper',
        overflow: 'auto',
      }}
    >
      {users.map((user) => (
        <>
          <ListItem
            alignItems="flex-start"
            key={user.username}
            sx={{
              '&:last-child td, &:last-child th': { border: 0 },
              cursor: 'pointer',
              backgroundColor: selectedUser == user.username ? '#EBEBEB' : '',
              '&:hover': {
                backgroundColor: '#EBEBEB',
              },
            }}
            onClick={() => setSelectedUser(user.username)}
          >
            <ListItemAvatar>
              <Avatar>
                {user.firstname.substring(0, 1) + user.lastname.substring(0, 1)}
              </Avatar>
            </ListItemAvatar>
            <ListItemText
              primary={user.firstname + ' ' + user.lastname}
              secondary={
                <Typography
                  sx={{ display: 'inline' }}
                  component="span"
                  variant="body2"
                  color="text.primary"
                >
                  {user.username}
                </Typography>
              }
            />
          </ListItem>
          <Divider variant="inset" component="li" />
        </>
      ))}
    </List>
  );
};

export default ChatUsersList;
