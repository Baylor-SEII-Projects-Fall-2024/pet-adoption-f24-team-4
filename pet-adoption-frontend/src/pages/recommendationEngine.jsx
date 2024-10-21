import React, { useEffect, useState } from 'react';
import { Stack, Typography, AppBar, Toolbar, Button, Drawer, ListItem, List, ListItemText, ListItemButton, Box, ListItemIcon, Card, CardMedia, CardActions, Avatar, Menu, MenuItem, Dialog, DialogTitle, DialogContent, TextField, DialogActions, Snackbar } from '@mui/material';
import HouseIcon from '@mui/icons-material/House';
import GroupsIcon from '@mui/icons-material/Groups';
import ContactsIcon from '@mui/icons-material/Contacts';
import MenuIcon from '@mui/icons-material/Menu';
import PetsIcon from '@mui/icons-material/Pets';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
import { useRouter } from 'next/router';
import NavBar from './nav_bar';

export default function RecommendationEnginePage() {
  const [state, setState] = useState({ left: false });
  const router = useRouter();
  const [userEmail, setUserEmail] = useState(null);
  const [profilePicture, setProfilePicture] = useState(null);
  const [anchorEl, setAnchorEl] = useState(null);
  const [openDialog, setOpenDialog] = useState(false);
  const [profilePictureFile, setProfilePictureFile] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [user, setUser] = useState(null);
  const [isLiked, setIsLiked] = useState(null);
  const apiUrl = process.env.NEXT_PUBLIC_API_URL;

  // Fetch user data when page loads
  useEffect(() => {
    if (router.isReady) {
      const { email } = router.query;
      if (email) {
        setUserEmail(email);
        fetchUserData(email);
      }
    }
  }, [router.isReady, router.query]);

  // Function to fetch user data (including profile picture)
  const fetchUserData = async (email) => {
    try {
      const response = await fetch(`${apiUrl}/users/email/${email}`);
      if (!response.ok) throw new Error('Failed to fetch user data');
      const data = await response.json();
      setUser(data);
      if (data.profilePicture && data.profilePicture.imageData) {
        setProfilePicture(`data:image/png;base64,${data.profilePicture.imageData}`);
      }
    } catch (error) {
      console.error('Error fetching user:', error);
    }
  };

  // Function to handle avatar click for menu
  const handleAvatarClick = (event) => setAnchorEl(event.currentTarget);
  const handleCloseMenu = () => setAnchorEl(null);

  // Open dialog for editing personal information
  const handleOpenDialog = () => {
    setOpenDialog(true);
    handleCloseMenu();
  };

  // Close dialog for editing personal information
  const handleCloseDialog = () => {
    setOpenDialog(false);
    setProfilePictureFile(null);
  };

  // Handle file upload for profile picture
  const handlePfFile = (event) => {
    const file = event.target.files[0];
    if (file) {
      setProfilePictureFile(file);
    }
  };

  // Save the profile picture to the backend
  const handleSave = async () => {
    if (profilePictureFile) {
      const formData = new FormData();
      formData.append('image', profilePictureFile);

      try {
        const response = await fetch(`${apiUrl}/user/profile-image/${userEmail}`, {
          method: 'POST',
          body: formData,
        });
        if (!response.ok) throw new Error('Failed to upload image');
        const updatedUser = await response.json();
        setProfilePicture(`data:image/png;base64,${updatedUser.profilePicture.imageData}`);
        setSnackbarOpen(true);
      } catch (error) {
        console.error('Error uploading profile picture:', error);
      }
    }
    handleCloseDialog();
  };

  // Handle like or dislike button
  const handleYes = () => setIsLiked(true);
  const handleNo = () => setIsLiked(false);

  // Drawer toggle logic
  const toggleDrawer = (anchor, open) => (event) => {
    if (event.type === 'keydown' && (event.keyCode === 'Tab' || event.keyCode === 'Shift')) {
      return;
    }
    setState({ ...state, [anchor]: open });
  };

  // List items for the drawer
  const list = (anchor) => (
    <Box sx={{ width: 250 }} onClick={toggleDrawer(anchor, false)} onKeyDown={toggleDrawer(anchor, false)}>
      <List>
        {['Home', 'Adopt', 'Meeting', 'Contact'].map((text) => (
          <ListItem key={text} disablePadding>
            <ListItemButton onClick={() => {
              const path = text === 'Home' ? '/customer-home' : '/recommendation-engine';
              router.push({ pathname: path, query: { email: userEmail || '' } });
            }}>
              <ListItemIcon>
                {text === 'Home' && <HouseIcon />}
                {text === 'Adopt' && <PetsIcon />}
                {text === 'Meeting' && <GroupsIcon />}
                {text === 'Contact' && <ContactsIcon />}
              </ListItemIcon>
              <ListItemText primary={text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Box>
  );

  return (
    <main>
      <NavBar />
      <Stack sx={{ paddingTop: 5 }} alignItems="center" gap={2}>
        <Typography variant="h3">Start Matching!</Typography>
        <Typography variant="body1" color="text.secondary">Adopt Now :D</Typography>

        <Card sx={{ maxWidth: 600 }}>
          <CardMedia component="img" alt="Pet Image" height="500" width="400" sx={{ objectFit: 'cover' }} />
          <CardActions sx={{ justifyContent: 'space-between' }}>
            <Button size="large" color="primary" onClick={handleYes} startIcon={<CheckCircleIcon sx={{ fontSize: 60 }} />} />
            <Button size="large" color="secondary" onClick={handleNo} startIcon={<CancelIcon sx={{ fontSize: 60 }} />} />
          </CardActions>
        </Card>

        {isLiked !== null && (
          <Typography variant="h5" sx={{ marginTop:  2 }}>
            {isLiked ? "You liked this pet!" : "You disliked this pet."}
          </Typography>
        )}
      </Stack>
    </main>
  );
}