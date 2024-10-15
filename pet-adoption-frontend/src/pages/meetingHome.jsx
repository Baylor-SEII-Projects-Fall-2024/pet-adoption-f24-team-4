import React, { useEffect, useState } from 'react';
import Head from 'next/head';
import { Box, Card, CardContent, Typography } from '@mui/material';

export default function AdoptionHome() {
    const [data, setData] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch("http://localhost:8080/MeetingHome");
                if (!response.ok) {
                    throw new Error("Failed to fetch meetings");
                }
                const result = await response.text();
                setData(result);
            } catch (error) {
                console.error('Error fetching meetings:', error);
            }
        };

        fetchData();
    }, []);

    // Handle cancelling a meeting
    const handleCancel = async (meetingId) => {
        try {
            const response = await fetch(`http://localhost:8080/MeetingHome/${meetingId}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                throw new Error("Failed to cancel the meeting");
            }
            // Remove the cancelled meeting from the UI
            setMeetings(meetings.filter(meeting => meeting.id !== meetingId));
        } catch (error) {
            console.error('Error cancelling meeting:', error);
        }
    };

    return (
        <Box>
            <Head>
                <title>Meeting Home</title>
            </Head>
            <Typography variant="h3">Your Meetings</Typography>
            <Card>
                <CardContent>
                    {meetings.length > 0 ? (
                        <List>
                            {meetings.map(meeting => (
                                <ListItem key={meeting.id}>
                                    <ListItemText primary={`Meeting with ${meeting.name}`} secondary={`Date: ${meeting.date}`} />
                                    <Button variant="contained" color="secondary" onClick={() => handleCancel(meeting.id)}>
                                        Cancel
                                    </Button>
                                </ListItem>
                            ))}
                        </List>
                    ) : (
                        <Typography>No meetings scheduled.</Typography>
                    )}
                </CardContent>
            </Card>
        </Box>
    );
}