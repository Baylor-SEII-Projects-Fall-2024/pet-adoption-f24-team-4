import React, { useState, useEffect } from 'react';
import { AppBar, Toolbar, IconButton, Typography, Drawer, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Box, Avatar, MenuItem, Menu } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import PetsIcon from '@mui/icons-material/Pets';
import GroupsIcon from '@mui/icons-material/Groups';
import ContactsIcon from '@mui/icons-material/Contacts';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { useRouter } from 'next/router';

function AdoptionNavBar() {
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const router = useRouter();
  const { email } = router.query;
  const [user, setUser] = useState(null);
  const [profilePicture, setProfilePicture] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const apiUrl = process.env.NEXT_PUBLIC_API_URL;

  useEffect(() => {
    const fetchUser = async () => {
      if (email) {
        setLoading(true);
        try {
          const token = localStorage.getItem('token');
          const response = await fetch(`${apiUrl}/users/email/${encodeURIComponent(email)}`, {
            headers: {
              Authorization: `Bearer ${token}`,
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

  const toggleDrawer = (open) => (event) => {
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
      return;
    }
    setDrawerOpen(open);
  };

  const handleAvatarClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
  };

  const handleLoginInfo = () => {
    const token = localStorage.getItem('token');
    if (token) {
      router.push(`/Profile?email=${email}`);
    } else {
      console.error('No token found in local storage.');
    }
  };

  const handleNavigation = (path) => {
    router.push(`${path}?email=${email}&userID=${user.id}`);
    handleCloseMenu();
  };


  if (loading) {
    return;
  }

  if (error) {
    return <div>{error}</div>;
  }

  if (!user) {
    return <div>User not found.</div>;
  }

  return (
    <main>
      <AppBar position="static" sx={{ padding: 2 }}>
        <Toolbar>
          <img
            src="/Friends_Logo.png"
            alt="Logo"
            onClick={() => handleNavigation('/adoptionHome')}
            style={{ width: 60, height: 60, cursor: 'pointer', marginRight: 25, marginLeft: -10 }}
          />



          <Typography variant="h6" component="div" sx={{ fontWeight: 'bold', marginRight: 'auto' }}>
            Whisker Works
          </Typography>

          <Typography variant="h6" component="div" sx={{ flexGrow: 1, fontSize: 24, fontWeight: 'bold', textAlign: 'center' }}>
            {user.center?.centerName || 'Adoption Center'}
          </Typography>

          <Avatar
            alt={user?.firstName || 'User'}
            src={profilePicture}
            sx={{ marginLeft: 2, width: 65, height: 65 }}
            onClick={handleAvatarClick}
          />

          <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleCloseMenu}>
            <MenuItem onClick={handleLoginInfo}>Edit Account Information</MenuItem>
            <MenuItem
              onClick={() => {
                localStorage.removeItem('token');
                handleNavigation('/');
              }}
            >
              Logout
            </MenuItem>
          </Menu>
        </Toolbar>
      </AppBar>
    </main>
  );
}

export default AdoptionNavBar;
