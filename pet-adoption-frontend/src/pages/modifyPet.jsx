import React, { useState, useEffect } from 'react';
import {
    TextField, Button, Autocomplete, Box, MenuItem, Typography, Paper, Grid, CircularProgress, Dialog, DialogTitle, DialogContent, DialogActions,
    Avatar,
} from '@mui/material';
import { useRouter } from 'next/router';

const ModifyPet = () => {
    const apiUrl = process.env.NEXT_PUBLIC_API_URL;
    const router = useRouter();
    const { adoptionID, email } = router.query;
    const [pets, setPets] = useState([]);
    const [selectedPet, setSelectedPet] = useState(null);
    const [options, setOptions] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [openDialog, setOpenDialog] = useState(false);
    const [petPicture, setPetPicture] = useState(null);
    const [previewUrl, setPreviewUrl] = useState(null); // New state for image preview URL

    useEffect(() => {
        const fetchOptions = async () => {
            const token = localStorage.getItem('token');
            const optionsRes = await fetch(`${apiUrl}/getOptions`, {
                headers: { 'Authorization': `Bearer ${token}` },
            });
            const optionsData = await optionsRes.json();
            setOptions(optionsData);
        };

        const fetchPets = async () => {
            const token = localStorage.getItem('token');
            const petsRes = await fetch(`${apiUrl}/pets/${adoptionID}`, {
                headers: { 'Authorization': `Bearer ${token}` },
            });
            const petsData = await petsRes.json();
            const formattedPets = petsData.map(pet => ({
                ...pet,
                species: formatOption(pet.species),
                coatLength: formatOption(pet.coatLength),
                furType: formatOption(pet.furType),
                petSize: formatOption(pet.petSize),
                healthStatus: formatOption(pet.healthStatus),
                sex: formatOption(pet.sex),
                furColor: pet.furColor.map(color => formatOption(color)),
                temperament: pet.temperament.map(temp => formatOption(temp)),
                dogBreed: pet.dogBreed ? pet.dogBreed.map(breed => formatOption(breed)) : [],
                catBreed: pet.catBreed ? pet.catBreed.map(breed => formatOption(breed)) : [],
            }));
            setPets(formattedPets);
            setLoading(false);
        };

        fetchOptions();
        fetchPets();
    }, [adoptionID]);

    const formatOption = (str) => {
        if (!str) return '';
        return str
            .toLowerCase()
            .split('_')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    };

    const handleDeletePet = async (petId) => {
        const token = localStorage.getItem('token');
        await fetch(`${apiUrl}/deletePet`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' },
            body: JSON.stringify({ id: petId }),
        });
        setPets((prevPets) => prevPets.filter((pet) => pet.id !== petId));
    };
     const handleBack = () => {
      router.push(`/adoptionHome?email=${email}`)
    };

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        setPetPicture(file);
        if (file) {
            setPreviewUrl(URL.createObjectURL(file)); // Create a URL for the image preview
        }
    };

    const handleUploadImage = async (petId) => {
        const formData = new FormData();
        formData.append('image', petPicture);
        const token = localStorage.getItem('token');
        await fetch(`${apiUrl}/pet/pet-image/${petId}`, {
            method: 'POST',
            headers: { 'Authorization': `Bearer ${token}` },
            body: formData,
        });
    };

    const handleUpdatePet = async () => {
        const token = localStorage.getItem('token');
        await fetch(`${apiUrl}/updatePet`, {
            method: 'PUT',
            headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' },
            body: JSON.stringify(selectedPet),
        });
        setPets((prevPets) =>
            prevPets.map((pet) => (pet.id === selectedPet.id ? selectedPet : pet))
        );
        setOpenDialog(false);
    };

    const handleFieldChange = (name, value) => {
        setSelectedPet((prevData) => ({ ...prevData, [name]: value }));
    };

    const handleDialogOpen = (pet) => {
        const formattedPet = {
            ...pet,
            species: formatOption(pet.species),
            coatLength: formatOption(pet.coatLength),
            furType: formatOption(pet.furType),
            petSize: formatOption(pet.petSize),
            healthStatus: formatOption(pet.healthStatus),
            sex: formatOption(pet.sex),
            furColor: pet.furColor.map(formatOption),
            temperament: pet.temperament.map(formatOption),
            dogBreed: pet.dogBreed ? pet.dogBreed.map(formatOption) : [],
            catBreed: pet.catBreed ? pet.catBreed.map(formatOption) : [],
        };
        setSelectedPet(formattedPet);
        setPreviewUrl(pet.profilePicture ? `data:image/png;base64,${pet.profilePicture.imageData}` : null);
        setOpenDialog(true);
    };

    // Case-insensitive comparison function for Autocomplete
    const isOptionEqualToValue = (option, value) => 
        option.toLowerCase() === value.toLowerCase();

    if (loading) return <CircularProgress />;
    if (error) return <Typography color="error">{error}</Typography>;

    return (
        <Box sx={{ padding: 3 }}>
            <Button variant="outlined" sx={{ mt: 2 }} onClick={handleBack}>
              Back
            </Button>
            <Typography variant="h4" align="center" gutterBottom>
                Modify Pets
            </Typography>
            <Paper sx={{ padding: 2, backgroundColor: '#f5f5f5' }}>
                {pets.map((pet) => (
                    <Box key={pet.id} sx={{ marginBottom: 2, borderBottom: '1px solid #ddd', paddingBottom: 2 }}>
                        <Typography variant="h6">{pet.name}</Typography>
                        <Typography variant="body2" color="textSecondary">
                            Species: {pet.species}, Age: {pet.age}, Weight: {pet.weight} lbs, Size: {pet.petSize}
                        </Typography>
                        <Button onClick={() => handleDialogOpen(pet)} sx={{ mt: 1, mr: 1 }}>
                            Modify
                        </Button>
                        <Button color="error" onClick={() => handleDeletePet(pet.id)} sx={{ mt: 1 }}>
                            Delete
                        </Button>
                    </Box>
                ))}
            </Paper>

            <Dialog open={openDialog} onClose={() => setOpenDialog(false)} maxWidth="sm" fullWidth>
                <DialogTitle>Modify Pet</DialogTitle>
                <DialogContent>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sx={{ textAlign: 'center' }}>
                            {previewUrl && (
                                <Avatar
                                    src={previewUrl}
                                    alt="Pet Picture"
                                    sx={{ width: 120, height: 120, margin: 'auto' }}
                                />
                            )}
                        </Grid>
                        <Grid item xs={12}><TextField label="Name" name="name" value={selectedPet?.name || ''} onChange={(e) => handleFieldChange('name', e.target.value)} fullWidth required /></Grid>
                        <Grid item xs={6}><TextField label="Weight" name="weight" value={selectedPet?.weight || ''} onChange={(e) => handleFieldChange('weight', e.target.value)} type="number" fullWidth /></Grid>
                        <Grid item xs={6}><TextField label="Age" name="age" value={selectedPet?.age || ''} onChange={(e) => handleFieldChange('age', e.target.value)} type="number" fullWidth /></Grid>
                        <Grid item xs={6}>
                            <TextField
                                select
                                label="Species"
                                name="species"
                                value={selectedPet?.species || ''}
                                onChange={(e) => handleFieldChange('species', e.target.value)}
                                fullWidth
                            >
                                {options.species.map((option) => (
                                    <MenuItem key={option} value={option}>
                                        {formatOption(option)}
                                    </MenuItem>
                                ))}
                            </TextField>
                        </Grid>

                        {/* Additional Fields */}
                        <Grid item xs={6}><TextField select label="Fur Type" name="furType" value={selectedPet?.furType || ''} onChange={(e) => handleFieldChange('furType', e.target.value)} fullWidth>{options.furType.map((option) => <MenuItem key={option} value={option}>{formatOption(option)}</MenuItem>)}</TextField></Grid>
                        <Grid item xs={12}><Autocomplete multiple options={options.temperament} value={selectedPet?.temperament || []} onChange={(e, newValue) => handleFieldChange('temperament', newValue)} isOptionEqualToValue={isOptionEqualToValue} renderInput={(params) => <TextField {...params} label="Temperament" />} fullWidth /></Grid>
                        <Grid item xs={6}><TextField select label="Health Status" name="healthStatus" value={selectedPet?.healthStatus || ''} onChange={(e) => handleFieldChange('healthStatus', e.target.value)} fullWidth>{options.healthStatus.map((option) => <MenuItem key={option} value={option}>{formatOption(option)}</MenuItem>)}</TextField></Grid>
                        <Grid item xs={6}><TextField select label="Sex" name="sex" value={selectedPet?.sex || ''} onChange={(e) => handleFieldChange('sex', e.target.value)} fullWidth>{options.sex.map((option) => <MenuItem key={option} value={option}>{formatOption(option)}</MenuItem>)}</TextField></Grid>

                        {/* Conditional Breed Field */}
                        {selectedPet?.species === 'Dog' && (
                            <Grid item xs={12}>
                                <Autocomplete
                                    multiple
                                    options={options.dogBreed}
                                    value={selectedPet?.dogBreed || []}
                                    onChange={(e, newValue) => handleFieldChange('dogBreed', newValue)}
                                    isOptionEqualToValue={isOptionEqualToValue}
                                    renderInput={(params) => <TextField {...params} label="Dog Breed" />}
                                    fullWidth
                                />
                            </Grid>
                        )}
                        {selectedPet?.species === 'Cat' && (
                            <Grid item xs={12}>
                                <Autocomplete
                                    multiple
                                    options={options.catBreed}
                                    value={selectedPet?.catBreed || []}
                                    onChange={(e, newValue) => handleFieldChange('catBreed', newValue)}
                                    isOptionEqualToValue={isOptionEqualToValue}
                                    renderInput={(params) => <TextField {...params} label="Cat Breed" />}
                                    fullWidth
                                />
                            </Grid>
                        )}
                    </Grid>
                    <input type="file" onChange={handleFileChange} />
                    <Button onClick={() => handleUploadImage(selectedPet.id)} variant="contained" sx={{ mt: 2 }}>
                        Upload Image
                    </Button>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setOpenDialog(false)}>Cancel</Button>
                    <Button onClick={handleUpdatePet} variant="contained" color="primary">
                        Save Changes
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
};

export default ModifyPet;