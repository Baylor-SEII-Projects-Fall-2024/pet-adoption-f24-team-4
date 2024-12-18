import React, { useEffect, useState } from 'react';
import AdoptionNavBar from '@/components/AdoptionNavBar';
import {
  Box,
  Stack,
  Typography,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Snackbar,
} from '@mui/material';
import { useRouter } from 'next/router';

export default function AdoptionHome() {
  const [anchorEl, setAnchorEl] = useState(null);
  const [openDialog, setOpenDialog] = useState(false);
  const [profilePicture, setProfilePicture] = useState(null);
  const [profilePictureFile, setProfilePictureFile] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const router = useRouter();
  const { email } = router.query;
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const apiUrl = process.env.NEXT_PUBLIC_API_URL;

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
  };

  const handleOpenDialog = () => {
    setOpenDialog(true);
    handleCloseMenu();
  };

  const logoutAction = () => {
    localStorage.setItem('validUser', JSON.stringify(null));
    router.push(`/`);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setProfilePictureFile(null); // Reset the file after closing the dialog
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      setProfilePictureFile(file); // Store the file for uploading later
    }
  };

  const handleMessage = () => {
    const token = localStorage.getItem('token');
    if (token) {
      router.push(`/AdoptionCenterMessages?email=${email}&userID=${user.firstName}`);
    }
  };

  const handleSave = async () => {
    if (profilePictureFile) {
      const formData = new FormData();
      formData.append('image', profilePictureFile);

      try {
        const response = await fetch(`${apiUrl}/user/profile-image/${email}`, {
          method: 'POST',
          body: formData,
        });

        if (!response.ok) {
          throw new Error('Failed to upload image');
        }

        // Get the updated user data
        const updatedUser = await response.json();

        // Update profile picture state
        if (updatedUser.profilePicture && updatedUser.profilePicture.imageData) {
          setProfilePicture(`data:image/png;base64,${updatedUser.profilePicture.imageData}`);
        } else {
          setProfilePicture(null);
        }

        setSnackbarOpen(true);
        window.location.reload(); // Reload to refresh user data
      } catch (error) {
        console.error('Error uploading profile picture:', error);
      }
    }
    handleCloseDialog();
  };

  useEffect(() => {
    const fetchUser = async () => {
      if (email) {
        setLoading(true);
        console.log('Fetching user with email:', email);

        try {
          const token = localStorage.getItem('token');
          const response = await fetch(`${apiUrl}/users/email/${encodeURIComponent(email)}`, {
            headers: {
              Authorization: `Bearer ${token}`, // Add token to headers
            },
          });
          if (!response.ok) {
            if (response.status === 404) {
              setError('User not found.');
              return;
            }
            throw new Error('Network response was not ok');
          }

          const data = await response.json();
          console.log('Fetched user data:', data);
          setUser(data);

          if (data.profilePicture && data.profilePicture.imageData) {
            setProfilePicture(`data:image/png;base64,${data.profilePicture.imageData}`);
          }
        } catch (error) {
          console.error('Error fetching user:', error);
          setError('User not found.');
        } finally {
          setLoading(false);
        }
      }
    };

    fetchUser();
  }, [email]);

  const handleCloseSnackbar = () => {
    setSnackbarOpen(false);
  };

  if (loading) {
    return <div>Loading...</div>; // Show a loading message while fetching
  }

  if (error) {
    return <div>{error}</div>; // Show the error message if there's an error
  }

  if (!user) {
    return <div>User not found.</div>; // Show a fallback if user data isn't available
  }

  const handleAddPet = () => {
    const adoptionID = user.center.adoptionID;
    router.push({
      pathname: '/addPet',
      query: { adoptionID, email },
    });
  };

  const handleModifyPet = () => {
    const adoptionID = user.center.adoptionID;
    router.push({
      pathname: '/modifyPet',
      query: { adoptionID, email },
    });
  };

  const handleModifyAdoptionCenter = () => {
    const adoptionID = user.center.adoptionID;
    router.push({
      pathname: '/modifyAdoptionCenter',
      query: { adoptionID, email },
    });
  };

  const handleModifyEvents = () => {
    const adoptionID = user.center.adoptionID;
    router.push({
      pathname: '/modifyEvents',
      query: { adoptionID, email },
    });
  };

  const handleAddEvent = () => {
    const adoptionID = user.center.adoptionID;
    router.push({
      pathname: '/addEvent',
      query: { adoptionID, email },
    });
  };

  const handleModifyAdoptionCenterProfile = () => {
    const adoptionID = user.center.adoptionID;
    router.push({
      pathname: '/modifyAdoptionCenterProfile',
      query: { adoptionID, email },
    });
  };

  return (
    <main>
      <AdoptionNavBar adoptionID={user.center.adoptionID}/>
      <Box sx={{ paddingBottom: 8 }}>
        <Stack spacing={3} direction="row" sx={{ marginTop: 4, marginLeft: 4 }}>
          <Box
            sx={{
              width: 300,
              padding: 4,
              borderRadius: 2,
              boxShadow: 3,
              backgroundColor: '#fff',
            }}
          >
            <Typography variant="h5" sx={{ mb: 2, color: '#333', fontWeight: 'bold' }}>
              Add Pets
            </Typography>
            <Button
              variant="contained"
              onClick={handleAddPet}
              sx={{ backgroundColor: '#1976d2', color: '#fff', fontWeight: 'bold' }}
            >
              Add Pets
            </Button>
          </Box>

          <Box
            sx={{
              width: 300,
              padding: 4,
              borderRadius: 2,
              boxShadow: 3,
              backgroundColor: '#fff',
            }}
          >
            <Typography variant="h5" sx={{ mb: 2, color: '#333', fontWeight: 'bold' }}>
              Modify Pets
            </Typography>
            <Button
              variant="contained"
              onClick={handleModifyPet}
              sx={{ backgroundColor: '#1976d2', color: '#fff', fontWeight: 'bold' }}
            >
              Modify Pets
            </Button>
          </Box>

          <Box
            sx={{
              width: 300,
              padding: 4,
              borderRadius: 2,
              boxShadow: 3,
              backgroundColor: '#fff',
            }}
          >
            <Typography variant="h5" sx={{ mb: 2, color: '#333', fontWeight: 'bold' }}>
              Modify Adoption Center Information
            </Typography>
            <Button
              variant="contained"
              onClick={handleModifyAdoptionCenter}
              sx={{ backgroundColor: '#1976d2', color: '#fff', fontWeight: 'bold' }}
            >
              Modify Adoption Center Information
            </Button>
          </Box>

          <Box
            sx={{
              width: 300,
              padding: 4,
              borderRadius: 2,
              boxShadow: 3,
              backgroundColor: '#fff',
            }}
          >
            <Typography variant="h5" sx={{ mb: 2, color: '#333', fontWeight: 'bold' }}>
              Add Events
            </Typography>
            <Button
              variant="contained"
              onClick={handleAddEvent}
              sx={{ backgroundColor: '#1976d2', color: '#fff', fontWeight: 'bold' }}
            >
              Add Events
            </Button>
          </Box>

          <Box
            sx={{
              width: 300,
              padding: 4,
              borderRadius: 2,
              boxShadow: 3,
              backgroundColor: '#fff',
            }}
          >
            <Typography variant="h5" sx={{ mb: 2, color: '#333', fontWeight: 'bold' }}>
              Modify Events
            </Typography>
            <Button
              variant="contained"
              onClick={handleModifyEvents}
              sx={{ backgroundColor: '#1976d2', color: '#fff', fontWeight: 'bold' }}
            >
              Modify Events
            </Button>
          </Box>

          <Box
            sx={{
              width: 300,
              padding: 4,
              borderRadius: 2,
              boxShadow: 3,
              backgroundColor: '#fff',
              textAlign: 'center',
            }}
          >
            <Typography variant="h5" sx={{ mb: 2, color: '#333', fontWeight: 'bold' }}>
              Check out your messages!
            </Typography>
            <Button
              variant="contained"
              color="primary"
              onClick={handleMessage}
              sx={{ fontWeight: 'bold' }}
            >
              Send/View Messages
            </Button>
          </Box>
        </Stack>
      </Box>

      <Dialog open={openDialog} onClose={handleCloseDialog}>
        <DialogTitle>Edit Personal Information</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="First Name"
            fullWidth
            variant="outlined"
            defaultValue={user.firstName}
          />
          <TextField
            margin="dense"
            label="Last Name"
            fullWidth
            variant="outlined"
            defaultValue={user.lastName}
          />
          <TextField
            margin="dense"
            label="Address"
            fullWidth
            variant="outlined"
          />
          <Stack marginTop={2}>
            <Typography variant="body1" sx={{ fontWeight: 'bold' }}>
              Profile Picture
            </Typography>
            <input
              accept="image/*"
              style={{ display: 'none' }}
              id="profile-picture-upload"
              type="file"
              onChange={handleFileChange}
            />
            <label htmlFor="profile-picture-upload">
              <Button variant="contained" component="span" sx={{ marginTop: 1 }}>
                Upload Profile Picture
              </Button>
            </label>
          </Stack>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} sx={{ color: '#1976d2' }}>
            Cancel
          </Button>
          <Button onClick={handleSave} sx={{ color: '#1976d2' }}>
            Save
          </Button>
        </DialogActions>
      </Dialog>

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={4000}
        onClose={handleCloseSnackbar}
        message="Profile picture updated successfully"
      />
    </main>
  );
}
