import React, { useEffect, useState } from 'react';
import {
  DialogActions,
  TextField,
  DialogContent,
  Dialog,
  DialogTitle,
  Button,
  Divider,
  Paper,
  Box,
  Typography,
  CircularProgress,
  List,
  ListItem,
  ListItemText,
  Avatar
} from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { format } from 'date-fns';
import { useRouter } from 'next/router';

export default function ModifyEvent() {
  const apiUrl = process.env.NEXT_PUBLIC_API_URL;
  const router = useRouter();
  const [events, setEvents] = useState(null);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { adoptionID, email } = router.query;
  const [openDialog, setOpenDialog] = useState(false);

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${apiUrl}/events/adoption-center/${adoptionID}`, {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });
        if (!response.ok) {
          throw new Error("Failed to fetch events");
        }
        const data = await response.json();
        setEvents(data);
      } catch (error) {
        console.error("Failed to fetch events");
        setError("Failed to fetch events");
      } finally {
        setLoading(false);
      }
    };
    fetchEvents();
  }, [adoptionID, apiUrl]);

  const formatDateTime = (dateTime) => {
    return format(new Date(dateTime), 'PPPpp');
  };

  const handleDelete = async (eventID) => {
    if (window.confirm("Are you sure you want to delete this event?")) {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${apiUrl}/events/deleteEvent/${eventID}`, {
          method: 'DELETE',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });
        if (!response.ok) {
          throw new Error("Failed to delete the event.");
        }
        setEvents((prevEvents) => prevEvents.filter(event => event.eventID !== eventID));
      } catch (error) {
        console.error("Error deleting event: ", error);
        setError("Failed to delete the event.");
      }
    }
  };

  const handleOpenDialog = (event) => {
    setSelectedEvent({
      ...event,
      startDateTime: null, // Initialize to null for empty field
      endDateTime: null, // Initialize to null for empty field
    });
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setSelectedEvent(null);
  };

  const handleModifyEvent = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`${apiUrl}/events/updateEvent`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(selectedEvent)
      });
      if (!response.ok) {
        throw new Error("Failed to modify event.");
      }
      const updatedEvent = await response.json();
      setEvents((prevEvents) =>
        prevEvents.map((event) =>
          event.eventID === updatedEvent.eventID ? updatedEvent : event
        )
      );
      handleCloseDialog();
    } catch (error) {
      console.error("Failed to modify event: ", error);
      setError("Failed to modify event.");
    }
  };

  const handleBack = () => {
    router.push(`/adoptionHome?email=${email}`);
  };

  return (
    <Box sx={{ marginTop: 4, paddingX: 4 }}>
      <Typography variant="h4" sx={{ marginBottom: 2, fontWeight: 'bold', color: '#1976d2' }}>
        Modify Events
      </Typography>
      <Paper elevation={3} sx={{ padding: 3, backgroundColor: '#f5f5f5', borderRadius: 3 }}>
        <List>
          {events && events.length > 0 ? (
            events.map((event) => (
              <React.Fragment key={event.eventID}>
                <ListItem sx={{ padding: 2 }}>
                  <ListItemText
                    primary={event.title}
                    secondary={`Start: ${formatDateTime(event.startDateTime)}, End: ${formatDateTime(event.endDateTime)}`}
                    primaryTypographyProps={{ variant: 'h6', color: '#333' }}
                    secondaryTypographyProps={{ variant: 'body2', color: 'textSecondary' }}
                  />
                  <Avatar
                    src={
                      event.eventPicture && event.eventPicture.imageData
                        ? `data:image/png;base64,${event.eventPicture.imageData}`
                        : null
                    }
                    sx={{ width: 100, height: 100 }}
                  />
                  <Button onClick={() => handleOpenDialog(event)}>Modify</Button>
                  <Button
                    variant="outlined"
                    color="error"
                    onClick={() => handleDelete(event.eventID)}
                    sx={{ marginLeft: 1 }}
                  >
                    Delete
                  </Button>
                </ListItem>
                <Divider />
              </React.Fragment>
            ))
          ) : (
            <Typography>No events found for this adoption center.</Typography>
          )}
        </List>
      </Paper>

      <Button variant="contained" sx={{ backgroundColor: '#1976d2', marginTop: 4 }} onClick={handleBack}>
        Back
      </Button>

      {selectedEvent && (
        <Dialog open={openDialog} onClose={handleCloseDialog} fullWidth maxWidth="sm">
          <DialogTitle>Modify Event</DialogTitle>
          <DialogContent sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <TextField
              label="Title"
              value={selectedEvent.title || ''}
              onChange={(e) => setSelectedEvent({ ...selectedEvent, title: e.target.value })}
              fullWidth
            />
            <TextField
              label="Description"
              value={selectedEvent.description || ''}
              onChange={(e) => setSelectedEvent({ ...selectedEvent, description: e.target.value })}
              fullWidth
              multiline
            />
            <TextField
              label="Location"
              value={selectedEvent.location || ''}
              onChange={(e) => setSelectedEvent({ ...selectedEvent, location: e.target.value })}
              fullWidth
            />
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <DateTimePicker
                label="Start Date & Time"
                value={selectedEvent.startDateTime}
                onChange={(newValue) =>
                  setSelectedEvent({ ...selectedEvent, startDateTime: newValue })
                }
                renderInput={(params) => <TextField {...params} fullWidth />}
              />
              <DateTimePicker
                label="End Date & Time"
                value={selectedEvent.endDateTime}
                onChange={(newValue) =>
                  setSelectedEvent({ ...selectedEvent, endDateTime: newValue })
                }
                renderInput={(params) => <TextField {...params} fullWidth />}
              />
            </LocalizationProvider>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleCloseDialog}>Cancel</Button>
            <Button onClick={handleModifyEvent} variant="contained" color="primary">
              Save Changes
            </Button>
          </DialogActions>
        </Dialog>
      )}
    </Box>
  );
}