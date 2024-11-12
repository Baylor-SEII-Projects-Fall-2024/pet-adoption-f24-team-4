import React, { useEffect, useState } from 'react';
import {
  Box,
  Card,
  CardContent,
  Typography,
  Avatar,
  CircularProgress,
  Grid,
  Divider 
} from '@mui/material';
import { format } from 'date-fns';
import { useRouter } from 'next/router';

export default function ViewEvents() {
  const apiUrl = process.env.NEXT_PUBLIC_API_URL;
  const router = useRouter();
  const { email } = router.query;
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${apiUrl}/events/all`, {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });
        if (!response.ok) {
          throw new Error("Failed to fetch events");
        }
        const data = await response.json();
        
        // Fetch adoption center details for each event
        const eventsWithCenters = await Promise.all(
          data.map(async (event) => {
            if (event.center) return event; // Center already included, skip fetch

            const centerResponse = await fetch(`${apiUrl}/adoption-centers/${event.adoptionID}`, {
              headers: { 'Authorization': `Bearer ${token}` }
            });
            if (centerResponse.ok) {
              const centerData = await centerResponse.json();
              event.center = centerData; // Attach center details to event
            }
            return event;
          })
        );

        setEvents(eventsWithCenters);
      } catch (error) {
        console.error("Error fetching events:", error);
        setError("Failed to fetch events");
      } finally {
        setLoading(false);
      }
    };

    if (email) {
      fetchEvents();
    }
  }, [apiUrl, email]);

  const formatDateTime = (dateTime) => format(new Date(dateTime), 'PPPpp');

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', marginTop: 4 }}>
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', marginTop: 4 }}>
        <Typography color="error">{error}</Typography>
      </Box>
    );
  }

  return (
    <Box sx={{ marginTop: 4, paddingX: 4 }}>
      <Typography variant="h4" sx={{ marginBottom: 4, fontWeight: 'bold', color: '#1976d2' }}>
        All Events
      </Typography>
      <Grid container spacing={3}>
        {events.map((event) => (
          <Grid item xs={12} md={6} lg={4} key={event.eventID}>
            <Card elevation={3}>
              <CardContent>
                {event.eventPicture && event.eventPicture.imageData && (
                  <Avatar
                    src={`data:image/png;base64,${event.eventPicture.imageData}`}
                    alt="Event Image"
                    sx={{ width: 80, height: 80, marginBottom: 2 }}
                  />
                )}
                <Typography variant="h5" sx={{ fontWeight: 'bold', color: '#1976d2' }}>
                  {event.title}
                </Typography>
                <Typography sx={{ color: 'text.secondary', marginBottom: 1 }}>
                  Location: {event.location}
                </Typography>
                <Typography sx={{ color: 'text.secondary', marginBottom: 1 }}>
                  Start: {formatDateTime(event.startDateTime)}
                </Typography>
                <Typography sx={{ color: 'text.secondary', marginBottom: 1 }}>
                  End: {formatDateTime(event.endDateTime)}
                </Typography>
                <Typography variant="body2" sx={{ marginTop: 1 }}>
                  {event.description}
                </Typography>
                <Divider sx={{ marginY: 2 }} />
                <Typography variant="subtitle1" sx={{ fontWeight: 'bold' }}>
                  Adoption Center:
                </Typography>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                  {event.center ? event.center.centerName : "Unknown Center"}
                </Typography>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                  {event.center ? event.center.buildingAddress : "No Address Available"}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}