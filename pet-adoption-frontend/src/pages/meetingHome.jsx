import React, { useEffect, useState } from 'react';
import Head from 'next/head';
import { Box, Card, CardContent, Typography } from '@mui/material';

export default function MeetingHome() {
    const [meetings, setMeetings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch("http://localhost:8080/MeetingHome");
                if (response.ok) {
                    const data = await response.json();
                    setMeetings(data);
                } else {
                    throw new Error('Failed to fetch meetings');
                }
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchMeetings();
    }, []);

    if (loading) return <p>Loading meetings...</p>;
    if (error) return <p>Error: {error}</p>;

    // Handle cancelling a meeting
    

    return (
        <div>
            <h2>All Meetings</h2>
            <table>
                <thead>
                    <tr>
                        <th>Meeting ID</th>
                        <th>Pet Name</th>
                        <th>User Name</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    {meetings.length === 0 ? (
                        <tr>
                            <td colSpan="4">No meetings available</td>
                        </tr>
                    ) : (
                        meetings.map((meeting) => (
                            <tr key={meeting.id}>
                                <td>{meeting.id}</td>
                                <td>{meeting.pet ? meeting.pet.name : 'N/A'}</td>
                                <td>{meeting.user ? meeting.user.name : 'N/A'}</td>
                                <td>{new Date(meeting.date).toLocaleDateString()}</td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </div>
    );
}
