import React, { useState, useEffect } from 'react';
import { Box, Card, CardContent, Typography, CircularProgress, Alert, Grid,Button } from '@mui/material';
import { useRouter } from 'next/router';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

export default function ViewCenters() {
    const [adoptionCenters, setAdoptionCenters] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState(null);
    const apiUrl = process.env.NEXT_PUBLIC_API_URL;
    const router = useRouter();

    useEffect(() => {
        const fetchAdoptionCenters = async () => {
            try {
                const response = await fetch(`${apiUrl}/adoption-centers`);
                if (!response.ok) {
                    throw new Error("Failed to fetch Adoption Centers");
                }
                const data = await response.json();
                setAdoptionCenters(data);
            } catch (error) {
                console.error("Error fetching adoption centers:", error);
                setMessage("Failed to load adoption centers.");
            } finally {
                setLoading(false);
            }
        };

        fetchAdoptionCenters();
    }, [apiUrl]);

    if (loading) {
        return <CircularProgress />;
    }

    const handleClick = (adoptionID) => {
        router.push({
      pathname: `/centerPublicProfile`,
      query: { adoptionID },
    });
    };


    return (
        <Box sx={{ padding: 4, backgroundColor: '#f4f6f8' }}> {/* Light greyish background for contrast */}
            <Button
                startIcon={<ArrowBackIcon />}
                variant="outlined"
                sx={{ marginBottom: 2, color: '#1976d2', borderColor: '#1976d2' }}
                onClick={() => router.push('/')}
            >
                Back to Home
            </Button>
           
            <Typography variant="h4" gutterBottom sx={{ color: '#000', marginBottom: 3, fontWeight: 'bold'}}>
                View Adoption Centers
            </Typography>

            {message && (
                <Alert severity="error" sx={{ marginBottom: 2 }}>
                    {message}
                </Alert>
            )}

            <Grid container spacing={3}>
                {adoptionCenters.map((center) => (
                    <Grid item xs={12} sm={6} md={4} key={center.adoptionID}>
                        <Card
                            onClick={() => handleClick(center.adoptionID)}
                            sx={{
                                boxShadow: 4,
                                borderRadius: 2,
                                height: '100%',
                                backgroundColor: '#fff',
                                border: '2px solid transparent',
                                cursor: 'pointer',
                                transition: 'transform 0.3s, box-shadow 0.3s',
                                '&:hover': {
                                    borderColor: '#1976d2', // Blue border on hover
                                    backgroundColor: '#e3f2fd', // Light blue background on hover
                                    transform: 'scale(1.05)', // Slight zoom effect on hover
                                    boxShadow: '6px 6px 20px rgba(0,0,0,0.2)', // Add depth with shadow
                                },
                            }}
                        >
                            <CardContent>
                                <Typography variant="h6" sx={{ fontWeight: 'bold', marginBottom: 1, color: '#1976d2' }}> {/* Blue title */}
                                    {center.centerName}
                                </Typography>
                                <Typography variant="body2" sx={{ color: '#424242', marginBottom: 1 }}> 
                                    Address:
                                </Typography>
                                <Typography variant="body2" sx={{ color: '#616161' }}> {/* Grey description */}
                                    {center.description}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Box>
    );
}