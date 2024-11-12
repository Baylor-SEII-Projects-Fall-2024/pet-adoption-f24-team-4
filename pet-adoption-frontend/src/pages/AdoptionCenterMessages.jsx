import React, { useState, useEffect } from 'react';
import {
  TextField,
  Button,
  List,
  ListItem,
  ListItemText,
  Box,
  Typography,
  Paper,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
  Snackbar
} from '@mui/material';
import { useRouter } from 'next/router';

export default function AdoptionCenterMessages() {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const [nonAdoptionCenterUsers, setNonAdoptionCenterUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(''); 
  const [userMessages, setUserMessages] = useState([]); 
  const [centerID, setCenterID] = useState(null);
  const [notification, setNotification] = useState(null);
  const apiUrl = process.env.NEXT_PUBLIC_API_URL;
  const router = useRouter();
  const selectedReceiver = nonAdoptionCenterUsers.find(user => user.id === selectedUser);
  const { email } = router.query;

  useEffect(() => {
    fetchNonAdoptionCenterUsers();
    fetchCenterID();
  }, [email]);

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (selectedUser) {
        fetchMessagesForUser(selectedUser);
      }
    }, 120000);

    return () => clearInterval(intervalId);
  }, [selectedUser]);

  const fetchNonAdoptionCenterUsers = async () => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const response = await fetch(`${apiUrl}/users/non-adoption-center`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setNonAdoptionCenterUsers(data);
        } else {
          console.error('Error fetching non-adoption center users:', response.status);
        }
      } catch (error) {
        console.error('Failed to fetch non-adoption center users:', error);
      }
    }
  };

  const fetchCenterID = async () => {
    const token = localStorage.getItem('token');
    if (token && email) {
      try {
        const response = await fetch(`${apiUrl}/users/adoption-center/${email}`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setCenterID(data);
        } else {
          console.error('Center not found for the provided email.');
        }
      } catch (error) {
        console.error('Failed to fetch center ID:', error);
      }
    }
  };

  const fetchMessagesForUser = async (userId) => {
    const token = localStorage.getItem('token');
    if (token && centerID) {
      try {
        const response = await fetch(`${apiUrl}/user-messages/adoption-center/${centerID}/user/${userId}`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setUserMessages(data);

          const newMessage = data[data.length - 1];
          if (newMessage && newMessage.senderID !== centerID) {
            setNotification(`New message from user: ${newMessage.content}`);
          }
        } else {
          console.error('Error fetching messages for user:', response.status);
        }
      } catch (error) {
        console.error('Failed to fetch messages for user:', error);
      }
    }
  };

  const handleSendMessage = async () => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const response = await fetch(`${apiUrl}/user-messages`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({
            content: newMessage,
            senderID: Number(centerID),
            receiverID: Number(selectedUser),
            senderName: 'Adoption Center',
            receiverName: `${selectedReceiver.firstName} ${selectedReceiver.lastName}`,
          }),
        });
        if (response.ok) {
          setNewMessage('');
          fetchMessagesForUser(selectedUser);
        } else {
          console.error('Error sending message:', response.status);
        }
      } catch (error) {
        console.error('Failed to send message:', error);
      }
    }
  };

  const handleUserSelect = (userId) => {
    setSelectedUser(userId);
    fetchMessagesForUser(userId);
  };

  const handleCloseNotification = () => {
    setNotification(null);
  };

  return (
    <Box p={3} display="flex" flexDirection="column" alignItems="center">
      <Paper elevation={3} sx={{ maxWidth: 500, width: '100%', p: 3 }}>
        <Typography variant="h6" gutterBottom>
          Messages with User
        </Typography>
        
        <FormControl fullWidth sx={{ mb: 3 }}>
          <InputLabel>Select User</InputLabel>
          <Select
            value={selectedUser}
            onChange={(e) => handleUserSelect(e.target.value)}
            fullWidth
          >
            {nonAdoptionCenterUsers.map((user) => (
              <MenuItem key={user.id} value={user.id}>
                {user.firstName} {user.lastName}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <List sx={{ maxHeight: 300, overflow: 'auto', mb: 2 }}>
          {userMessages.map((message) => {
            const isSenderCenter = message.senderID === centerID;
            return (
              <ListItem
                key={message.id}
                sx={{
                  alignSelf: isSenderCenter ? 'flex-end' : 'flex-start',
                  bgcolor: isSenderCenter ? 'primary.light' : 'grey.200',
                  borderRadius: 2,
                  mb: 1,
                  px: 2,
                  py: 1,
                  maxWidth: '80%',
                }}
              >
                <ListItemText
                  primary={
                    <strong>
                      {isSenderCenter ? 'Adoption Center' : 'User'}
                    </strong>
                  }
                  secondary={message.content}
                  primaryTypographyProps={{
                    color: isSenderCenter ? 'primary.contrastText' : 'text.primary',
                  }}
                />
              </ListItem>
            );
          })}
        </List>

        <Box display="flex" alignItems="center">
          <TextField
            variant="outlined"
            fullWidth
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
            placeholder="Type your message..."
            sx={{ mr: 1 }}
          />
          <Button variant="contained" color="primary" onClick={handleSendMessage}>
            Send
          </Button>
        </Box>
      </Paper>

      <Snackbar
        open={!!notification}
        autoHideDuration={4000}
        onClose={handleCloseNotification}
        message={notification}
      />
    </Box>
  );
}