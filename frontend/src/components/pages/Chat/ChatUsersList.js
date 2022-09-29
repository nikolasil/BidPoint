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
  TablePagination,
} from '@mui/material';
import React, { useEffect } from 'react';
import { styled } from '@mui/material/styles';
import Badge from '@mui/material/Badge';

const StyledBadgeGreen = styled(Badge)(({ theme }) => ({
  '& .MuiBadge-badge': {
    backgroundColor: '#44b700',
    color: '#44b700',
    boxShadow: `0 0 0 2px ${theme.palette.background.paper}`,
    '&::after': {
      position: 'absolute',
      top: 0,
      left: 0,
      width: '100%',
      height: '100%',
      borderRadius: '50%',
      animation: 'ripple 1.2s infinite ease-in-out',
      border: '1px solid currentColor',
      content: '""',
    },
  },
  '@keyframes ripple': {
    '0%': {
      transform: 'scale(.8)',
      opacity: 1,
    },
    '100%': {
      transform: 'scale(2.4)',
      opacity: 0,
    },
  },
}));

const StyledBadgeGrey = styled(Badge)(({ theme }) => ({
  '& .MuiBadge-badge': {
    backgroundColor: 'grey',
    color: 'grey',
    boxShadow: `0 0 0 2px ${theme.palette.background.paper}`,
    '&::after': {
      position: 'absolute',
      top: 0,
      left: 0,
      width: '100%',
      height: '100%',
      borderRadius: '50%',
      border: '1px solid currentColor',
      content: '""',
    },
  },
}));

const ChatUsersList = (props) => {
  const { usersOnline, selectedUser, setSelectedUser, users } = props;
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
              {usersOnline.some(
                (userOnline) => userOnline.username === user.username
              ) ? (
                <StyledBadgeGreen
                  overlap="circular"
                  anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
                  variant="dot"
                >
                  <Avatar>
                    {user.firstname.substring(0, 1) +
                      user.lastname.substring(0, 1)}
                  </Avatar>
                </StyledBadgeGreen>
              ) : (
                <StyledBadgeGrey
                  overlap="circular"
                  anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
                  variant="dot"
                >
                  <Avatar>
                    {user.firstname.substring(0, 1) +
                      user.lastname.substring(0, 1)}
                  </Avatar>
                </StyledBadgeGrey>
              )}
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
      {/* <TablePagination rowsPerPageOptions={[]} /> */}
    </List>
  );
};

export default ChatUsersList;
