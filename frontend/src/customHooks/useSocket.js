import { useCallback, useEffect, useState } from 'react';
import io from 'socket.io-client';
import { useSelector } from 'react-redux';

export const useSocket = () => {
  const auth = useSelector((state) => state.auth);
  const [socket, setSocket] = useState();
  const [receivedMessage, setReceivedMessage] = useState({
    dateCreated: '',
    content: '',
    sender: '',
    receiver: '',
  });
  const [usersOnline, setUsersOnline] = useState([]);
  const [isConnected, setConnected] = useState(false);
  const sendData = useCallback(
    (receiver, message) => {
      console.log('sendData to ' + receiver + ', message: ' + message);
      socket.emit('send_message', {
        content: message,
        sender: auth.user.username,
        receiver: receiver,
      });
    },
    [socket]
  );
  useEffect(() => {
    const s = io('http://localhost:8003', {
      reconnection: false,
      query: `username=${auth.user.username}`,
    });
    setSocket(s);
    s.on('connect', () => setConnected(true));
    s.on('receive_message', (res) => {
      console.log('receive_message', res);
      setReceivedMessage(res);
    });
    s.on('users_online', (res) => {
      console.log('users_online', res);
      setUsersOnline(res);
    });
    return () => {
      s.disconnect();
    };
  }, []);

  return { isConnected, sendData, receivedMessage, usersOnline };
};
