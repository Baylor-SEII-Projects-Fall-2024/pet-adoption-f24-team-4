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
  Snackbar,
} from '@mui/material';
import { useRouter } from 'next/router';

export default function Message() {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const [adoptionCenters, setAdoptionCenters] = useState([]);
  const [selectedCenter, setSelectedCenter] = useState('');
  const [centerMessages, setCenterMessages] = useState([]);
  const [userID, setUserID] = useState(null);
  const [userName, setUserName] = useState(null);
  const [notification, setNotification] = useState(null);
  const apiUrl = process.env.NEXT_PUBLIC_API_URL;
  const router = useRouter();
  const { email } = router.query;

  useEffect(() => {
    fetchAdoptionCenters();
    fetchUserID();
  }, [email]);

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (selectedCenter) {
        fetchMessagesForCenter(selectedCenter);
      }
    }, 120000);

    return () => clearInterval(intervalId);
  }, [selectedCenter]);

  const fetchAdoptionCenters = async () => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const response = await fetch(`${apiUrl}/adoption-centers`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setAdoptionCenters(data);
        } else {
          console.error('Error fetching adoption centers:', response.status);
        }
      } catch (error) {
        console.error('Failed to fetch adoption centers:', error);
      }
    }
  };

  const fetchUserID = async () => {
    const token = localStorage.getItem('token');
    if (token && email) {
      try {
        const response = await fetch(`${apiUrl}/users/email/${email}`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setUserID(data.id);
          setUserName(data.firstName);
        } else {
          console.error('User not found for the provided email.');
        }
      } catch (error) {
        console.error('Failed to fetch user ID:', error);
      }
    }
  };

  const fetchMessagesForCenter = async (centerId) => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const response = await fetch(`${apiUrl}/user-messages/adoption-center/${centerId}/user/${userID}`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setCenterMessages(data);

          const newMessage = data[data.length - 1];
          if (newMessage && newMessage.senderID !== userID) {
            setNotification(`New message from Adoption Center: ${newMessage.content}`);
          }
        } else {
          console.error('Error fetching messages for center:', response.status);
        }
      } catch (error) {
        console.error('Failed to fetch messages for center:', error);
      }
    }
  };

  const handleSendMessage = async () => {
    if (newMessage.trim() === '' || !selectedCenter || !userID) return;
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
            senderID: userID,
            receiverID: selectedCenter,
          }),
        });
        if (response.ok) {
          setNewMessage('');
          fetchMessagesForCenter(selectedCenter);
        } else {
          console.error('Error sending message:', response.status);
        }
      } catch (error) {
        console.error('Failed to send message:', error);
      }
    }
  };

  const handleCenterSelect = (centerId) => {
    setSelectedCenter(centerId);
    fetchMessagesForCenter(centerId);
  };

  const handleCloseNotification = () => {
    setNotification(null);
  };

  return (
    <Box p={3} display="flex" flexDirection="column" alignItems="center">
      <Paper elevation={3} sx={{ maxWidth: 500, width: '100%', p: 3 }}>
        <Typography variant="h6" gutterBottom>
          Messages with Adoption Center
        </Typography>

        <FormControl fullWidth sx={{ mb: 3 }}>
          <InputLabel>Select Adoption Center</InputLabel>
          <Select value={selectedCenter} onChange={(e) => handleCenterSelect(e.target.value)} fullWidth>
            {adoptionCenters.map((center) => (
              <MenuItem key={center.adoptionID} value={center.adoptionID}>
                {center.centerName}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <List sx={{ maxHeight: 300, overflow: 'auto', mb: 2 }}>
          {centerMessages.map((message) => {
            const isFromUser = message.senderID === userID;
            return (
              <ListItem
                key={message.id}
                sx={{
                  alignSelf: isFromUser ? 'flex-end' : 'flex-start',
                  bgcolor: isFromUser ? 'primary.light' : 'grey.200',
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
                      {isFromUser ? 'User' : 'Adoption Center'}
                    </strong>
                  }
                  secondary={message.content}
                  primaryTypographyProps={{
                    color: isFromUser ? 'primary.contrastText' : 'text.primary',
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

      <Snackbar open={!!notification} autoHideDuration={4000} onClose={handleCloseNotification} message={notification} />
    </Box>
  );
}