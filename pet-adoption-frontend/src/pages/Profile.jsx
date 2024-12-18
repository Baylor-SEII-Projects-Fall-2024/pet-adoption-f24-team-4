import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import { Stack, Typography, AppBar, Toolbar, Button, Avatar, Dialog, DialogTitle, DialogContent, DialogActions, TextField, Snackbar, Box,Card } from '@mui/material';
import { red } from '@mui/material/colors';
import { ClosedCaptionDisabledSharp, MarkEmailUnread } from '@mui/icons-material';
import NavBar from '@/components/NavBar'
import AdoptionNavBar from '@/components/AdoptionNavBar'

export default function Profile() {
  const [anchorEl, setAnchorEl] = useState(null);
  const [openDialog, setOpenDialog] = useState(false);
  const [profilePicture, setProfilePicture] = useState(null);
  const router = useRouter();
  const { email, adoptionID } = router.query; // Use email from query parameters
 
  const [user, setUser] = useState(null);

  const [firstName, setFirstName] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [password, setPassword] = useState(null);
  const [newPassword, setNewPassword] = useState(null);
  const [currentPassword, setCurrentPassword] = useState(null);
  const [profilePictureFile, setProfilePictureFile] = useState(null);
  const [passwordMessage,setPasswordMessage] = useState('');
  const [passColor, setPassColor] = useState(null);
  const [isEditingFirstName, setIsEditingFirstName] = useState(false);
  const [isEditingLastName, setIsEditingLastName] = useState(false);
  const [isEditingEmail, setIsEditingEmail] = useState(false);
  const [currentFirstName, setCurrentFirstName] = useState(false)
  const [currentLastName,setCurrentLastName] = useState(false)
  const [newEmail, setNewEmail]  = useState('');
  const [newFirstName, setNewFirstName]  = useState('');
  const [newLastName, setNewLastName]  = useState('');
  const [firstNameError, setFirstNameError] = useState('');
  const [lastNameError, setLastNameError] = useState('');
  const [emailError, setEmailError] = useState('');

  const apiUrl = process.env.NEXT_PUBLIC_API_URL;


  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      setProfilePictureFile(file); // Store the file for uploading later
    }
  };

  const updateProfilePicture = (newPictureUrl) => {
    setProfilePicture(newPictureUrl);
  };

  
  const editFirstName = () => {
    setIsEditingFirstName(true); 
  }

  const editLastName = () => {
    setIsEditingLastName(true);
    
  }
  const editEmail = () => {
    setIsEditingEmail(true);
  }


  const changeFirstName = async (e) =>{
    setIsEditingFirstName(false)
    if (newFirstName.length < 2 || newFirstName.length > 50 && isEditingFirstName) {
      setFirstNameError('First name must be between 2 and 50 characters.');
      return;
    } else if (!/^[a-zA-Z]+$/.test(newFirstName) && isEditingFirstName) {
        setFirstNameError('First name can only contain alphabetic characters.');
        return;
    } else {
        setFirstNameError('');
    }
    
    try{
      
      const token = localStorage.getItem('token');
      const response = await fetch(`${apiUrl}/changeFirstName`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify({ email: user.emailAddress, firstName: newFirstName }),
      });
      
      if (!response.ok) {
        const errorMessage = await response.text();
        console.log('aw man :(')
        console.log(errorMessage);
        //setIsSuccess(false);
        //setMessage(errorMessage); // Display error message from backend
        //setTokenStored(false); // Reset token stored status
        return;
      }
      setCurrentFirstName(newFirstName)


    }catch (error) {
      console.error("Error logging in: ", error);
      //setMessage("NOOB");
    }


  }


  const changeLastName = async (e) =>{
    
    if (newLastName.length < 2 || newLastName.length > 50 && isEditingLastName) {
      setLastNameError('Last name must be between 2 and 50 characters.');
      setIsEditingLastName(false)
      return
    } else if (!/^[a-zA-Z]+$/.test(newLastName) && isEditingLastName) {

        setLastNameError('Last name can only contain alphabetic characters.');
        setIsEditingLastName(false)
        return
    } else {
        setLastNameError('');
    }

    try{
      
      const token = localStorage.getItem('token');
      const response = await fetch(`${apiUrl}/changeLastName`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify({ email: user.emailAddress, lastName: newLastName }),
      });
      
      if (!response.ok) {
        const errorMessage = await response.text();
        console.log('aw man :(')
        console.log(errorMessage);
        //setIsSuccess(false);
        //setMessage(errorMessage); // Display error message from backend
        //setTokenStored(false); // Reset token stored status
        return;
      }
      setCurrentLastName(newLastName)


    }catch (error) {
      console.error("Error logging in: ", error);
      //setMessage("NOOB");
    }
    setIsEditingLastName(false)


  }

  const changeEmail = async (e) => {
    console.log('GOOOOOOOOOOO')

    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(newEmail) && isEditingEmail) {
      setEmailError('Please enter a valid email address.');
      setIsEditingEmail(false);
      return;
    } else {
      setEmailError('');
    }
    console.log('YOUR DONE')
      
  
    setIsEditingEmail(false);
    console.log("We Cahingin")
    console.log(email)
    console.log(password)
    try {
      console.log(`${apiUrl}/changeEmail`);
      console.log('hi')
      const token = localStorage.getItem('token');
      console.log(token);

      console.log(user.emailAddress)

      const reponse = await fetch(`${apiUrl}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ emailAddress: email, password: password  }),
      });

    if (!reponse.ok) {
      const errorMessage = await reponse.text();
      console.log('aw mn :(')
      console.log(errorMessage);
      setEmailError('Error: Password is Wrong')
      //setIsSuccess(false);
      //setMessage(errorMessage); // Display error message from backend
      //setTokenStored(false); // Reset token stored status
      return;
    }
    const result = await reponse.json();

    if(result.token) {

      // Store the JWT token in localStorage
      
      localStorage.setItem('token', result.token);
    }
    else{
      console.log('WORNG')
      return;
      
    }

    const response = await fetch(`${apiUrl}/changeEmail`, {
      method: 'PUT',
      headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({ email: user.emailAddress, newEmail: newEmail }),
    });



    if (!response.ok) {
      const errorMessage = await response.text();
      console.log(errorMessage);
      setEmailError('ERROR: Email is Already Taken')
      //setIsSuccess(false);
      //setMessage(errorMessage); // Display error message from backend
      //setTokenStored(false); // Reset token stored status
      return;
    }
    else{
      user.emailAddress = newEmail;

      const reponse = await fetch(`${apiUrl}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ emailAddress: newEmail, password: password  }),
      });
      const res = await reponse.json();

      if(res.token) {

        // Store the JWT token in localStorage
        
        localStorage.setItem('token', res.token);
      }

      router.push(`/Profile?email=${newEmail}&userID=${user.id}`);
    }
    
    

    } catch (error) {
      console.error("Error logging in: ", error);
      }


  }

  const deleteAccount = async (e) => {

    e.preventDefault();
    
    try {
      const response = await fetch(`${apiUrl}/profile-delete`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },


        body: JSON.stringify({ email: email, password: password })
      });

      
      if (!response.ok) {
          throw new Error("Bad network response");
      }
      const result = await response.json();
      
      if(response.status == 200){
        console.log('WE LIKE FOTNITE')
      }
    } catch (error) {
      console.error("Error logging in: ", error);
      //setMessage("NOOB");
      }
      router.push(`/`);
  }

  const changePassword = async (e) => {
    e.preventDefault();
    console.log('LKJSDN');
    console.log(currentPassword);
    console.log(password);
    console.log(newPassword);
    console.log(`${apiUrl}/profile`);
    console.log(email);

    try {
      const response = await fetch(`${apiUrl}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ emailAddress: email, password: currentPassword }),
      });
      console.log('WE ETNERED');
      console.log(email);
      console.log(currentPassword);


      console.log(`${apiUrl}/login`);
      console.log("Response status:", response.status);

      if (!response.ok) {
        //setIsSuccess(false);
        //setMessage(errorMessage); // Display error message from backend
        //setTokenStored(false); // Reset token stored status

      }

      const result = await response.json();


      localStorage.setItem('token', result.token);
    

  // Check if token exists in the response
  //if (result.token) {
    let valid = true;

    console.log('The New PAssword')
    console.log(newPassword)
    if (newPassword.length < 8) {

        setPasswordMessage('Password must be at least 8 characters long.');
        valid = false;
    } else if (!/(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}/.test(newPassword)) {
      setPasswordMessage('Password must contain uppercase, lowercase, number, and special character.');
        valid = false;
    } else {
      setPasswordMessage('');
    }
    console.log('adadasdasdasdadsdsa')

    if (result.token && newPassword && valid) {
        setPassword(newPassword);
        try {
          const token = localStorage.getItem('token'); // Get the token from local storage
            
            const response = await fetch(`${apiUrl}/profile`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`, // Add token to headers
                },
                body: JSON.stringify({ email: email, password: newPassword }),
            });

            if (!response.ok) {
                throw new Error("Bad network response");
            }
            const result = await response.json();

            if (response.status === 200) {
                setPassColor('green');
                setPasswordMessage('Password Successfully Changed');
                console.log('WE LIKE FOTNITE');
            }
        } catch (error) {

            console.error("Error changing password: ", error);
            setPassColor('red');
            setPasswordMessage('Error changing password');

        }
    } else {
        
        console.log('SETTING')
        setPassColor('red');
        if(!(result.token && newPassword)){
          setPasswordMessage('Please Enter the Correct Password'); 
        }
        
    }

  }catch (error) {
    console.error("Error logging in: ", error);
  }
};

  const handleSave = async () => {
    console.log('WEHIWRIF');
    
    if (profilePictureFile) {
        const formData = new FormData();
        formData.append('image', profilePictureFile);
        
        try {
            const token = localStorage.getItem('token'); // Get the token from local storage
            
            const response = await fetch(`${apiUrl}/user/profile-image/${email}`, {
                method: 'POST',
                body: formData,
                headers: {
                    'Authorization': `Bearer ${token}`, // Add token to the headers
                },
            });

            if (!response.ok) {
                throw new Error('Failed to upload image');
            }

            // Fetch the updated user data after the image upload
            const userResponse = await fetch(`${apiUrl}/users/email/${email}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`, // Include token for the GET request
                    'Content-Type': 'application/json',
                },
            });

            // Get the updated user data
            const updatedUser = await userResponse.json();
            console.log('WEHIWRIF');
            console.log(updatedUser);

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
};

  useEffect(() => {
    const fetchUser = async () => {
        if (email) {
            const token = localStorage.getItem('token'); // Get the token from local storage
            console.log(email)
            try {
                const response = await fetch(`${apiUrl}/users/email/${email}`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`, // Include the token in the headers
                        'Content-Type': 'application/json',
                    },
                });
                console.log(`Bearer ${token}`);
                console.log(`${apiUrl}/profile`);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                
                const data = await response.json();
                setUser(data); // Set the user data
                console.log(data)
                setCurrentFirstName(data.firstName)
                setCurrentLastName(data.lastName)

                if (data.profilePicture && data.profilePicture.imageData) {
                    setProfilePicture(`data:image/png;base64,${data.profilePicture.imageData}`);
                }
                setPassword(data.password);
            } catch (error) {
                console.error('Error fetching user:', error);
                setError('User not found.'); // Update error state
            } finally {
                setLoading(false); // Loading is done
            }
        }
    };

    fetchUser();
}, [email]);
  if (loading) {
    return <div>Loading...</div>; // Show a loading message while fetching
  }

  if (error) {
    return <div>{error}</div>;
  }

  if (!user) {
    return <div>User not found.</div>;
  }

  return (
    <main>
      {adoptionID ? <AdoptionNavBar/> : <NavBar />}
      <Stack sx={{ paddingTop: 0 }} gap={2}>
      <Typography variant="h6" component='span' gutterBottom sx={{ fontWeight: 'bold', color: '#black' }}>
        Account Info
        </Typography>
        
        <Card sx={{ borderRadius: 1, backgroundColor: 'lightBlue', boxShadow: 3, padding: 2 }}>
        <Box sx={{ display: 'flex', alignItems: 'center' }}> {/* This Box acts as a flex container */}
        {isEditingFirstName ?
        <Box>
        <TextField
        label="First Name"
        variant="outlined"
        margin="normal"
        value={newFirstName}
        onChange={(e) => setNewFirstName(e.target.value)}
        required
        sx={{ borderRadius: 2 }}
        />
      <Button
      variant="contained"
      onClick={changeFirstName}
      sx={{
        marginTop: 2.8,
        paddingY: 1,
        paddingX: 3,
        borderRadius: 2.5,
        backgroundColor: '#1976d2',
        '&:hover': {
          backgroundColor: '#1565c0',
        },
      }}
    >
      Save
    </Button> 
    </Box>  
        :
        <Box>
        <Typography 
        variant="h6" 
        component="span" 
        gutterBottom
        sx={{
          color: '#000000', // Corrected the color value
          marginRight: 2 // Adds spacing between the text and the button
        }}
      >
        First name: {currentFirstName}
      </Typography>

      <Button
        variant="contained"
        onClick={editFirstName}
        sx={{
          marginBottom: 0,
          paddingY: 1,
          paddingX: 3,
          borderRadius: 2.5,
          backgroundColor: '#1976d2',
          '&:hover': {
            backgroundColor: '#1565c0',
          },
        }}
      >
        Edit
      </Button>
      <Typography variant="h6" component='span' gutterBottom sx={{  color: 'red' }}>
        {firstNameError}
      </Typography>

      </Box>  
        }
    
    </Box>
    </Card>
    

    <Card sx={{ borderRadius: 1, backgroundColor: 'lightBlue', boxShadow: 3, padding: 2 }}>
    <Box sx={{ display: 'flex', alignItems: 'center' }}> {/* This Box acts as a flex container */}
    {isEditingLastName ?
        <Box>
        <TextField
        label="Last Name"
        variant="outlined"
        margin="normal"
        value={newLastName}
        onChange={(e) => setNewLastName(e.target.value)}
        required
        sx={{ borderRadius: 2 }}
        />
      <Button
      variant="contained"
      onClick={changeLastName}
      sx={{
        marginTop: 2.8,
        paddingY: 1,
        paddingX: 3,
        borderRadius: 2.5,
        backgroundColor: '#1976d2',
        '&:hover': {
          backgroundColor: '#1565c0',
        },
      }}
    >
      Save
    </Button> 
    </Box>  
        :
        <Box>
        <Typography 
        variant="h6" 
        component="span" 
        gutterBottom
        sx={{
          color: '#000000', // Corrected the color value
          marginRight: 2 // Adds spacing between the text and the button
        }}
      >
        Last name: {currentLastName}
      </Typography>

      <Button
        variant="contained"
        onClick={editLastName}
        sx={{
          marginBottom: 0,
          paddingY: 1,
          paddingX: 3,
          borderRadius: 2.5,
          backgroundColor: '#1976d2',
          '&:hover': {
            backgroundColor: '#1565c0',
          },
        }}
      >
        Edit
      </Button>
      <Typography variant="h6" component='span' gutterBottom sx={{  color: 'red' }}>
        {lastNameError}
      </Typography>
      </Box>  
        }
      
    </Box>
    </Card>

    <Card sx={{ borderRadius: 1, backgroundColor: 'lightBlue', boxShadow: 3, padding: 2 }}>
      <Box sx={{ display: 'flex', alignItems: 'center' }}> {/* This Box acts as a flex container */}
    
      {isEditingEmail ?
      <Box>
      <TextField
      label="Email"
      variant="outlined"
      margin="normal"
      value={newEmail}
      onChange={(e) => setNewEmail(e.target.value)}
      required
      sx={{ borderRadius: 2 }}
      />
    <TextField
      label="Password"
      variant="outlined"
      margin="normal"
      onChange={(e) => setPassword(e.target.value)}
      required
      sx={{ borderRadius: 2 }}
    />
    <Button
    variant="contained"
    onClick={changeEmail}
    sx={{
      marginTop: 2.8,
      paddingY: 1,
      paddingX: 3,
      borderRadius: 2.5,
      backgroundColor: '#1976d2',
      '&:hover': {
        backgroundColor: '#1565c0',
      },
    }}
  >
    Save
  </Button> 
  <br />

  
  </Box> 
    
          :
          <Box>
        <Typography variant="h6" component='span' gutterBottom sx={{  color: 'black' }}>
        Email Address: {user.emailAddress}
        
        </Typography>
        <Button
        variant="contained"
        onClick={editEmail}
        sx={{
          marginBottom: 0,
          paddingY: 1,
          paddingX: 3,
          borderRadius: 2.5,
          backgroundColor: '#1976d2',
          '&:hover': {
            backgroundColor: '#1565c0',
          },
        }}
      >
        Edit
      </Button>
      <Typography variant="h6" component='span' gutterBottom sx={{  color: 'red' }}>
        {emailError}
      </Typography>
      </Box>
         }
        
      
      
    </Box>
    </Card>

      <table>
          
          <tr>
            <td >
            <section className='square'>
              <Typography variant="h6">Change Password:</Typography>
              <form>
                <TextField
                  label="Current Password"
                  variant="outlined"
                  //fullWidth
                  margin="normal"
                  value={currentPassword}
                  onChange={(e) => setCurrentPassword(e.target.value)}
                  required
                  sx={{ borderRadius: 2 }}
                />
                <TextField
                  label="New Password"
                  
                  variant="outlined"
                  //fullWidth
                  margin="normal"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  required
                  sx={{ borderRadius: 2 }}
                />
                <Button
                  onClick={changePassword}
                  //type="submit"
                  variant="contained"
                  fullWidth
                  sx={{
                      marginBottom:0,
                      paddingY: 1.5,
                      borderRadius: 2.5,
                      marginLeft: 0,
                      backgroundColor: '#1976d2',
                      '&:hover': {
                          backgroundColor: '#1565c0',
                      },
                    }}
                  >
                    Change Password
                </Button>
                
              </form>
              <Typography sx={{color: `${passColor}`,fontSize: 18 }}>{passwordMessage}</Typography>
    

            </section>
            </td>

            <td> 
              <section className='square'>
                <Button
                  onClick={deleteAccount}
                  //type="submit"
                  variant="contained"
                  fullWidth
                  fullHeight
                  sx={{
                      marginBottom:0.5,
                      paddingY: 0,
                      borderRadius: 1.5,
                      marginLeft: 0,
                      backgroundColor: 'red',
                      '&:hover': {
                          backgroundColor: 'darkRed',
                      },
                    }}
                  >
                      DELETE ACCOUNT
                </Button>
              </section>

            </td>
            <td> 
              <section className='square'>
              <Typography variant="h6">Change Profile Picture:</Typography>
               <Stack marginTop={2}>
               <Avatar
                  src={profilePicture} // Use the uploaded profile picture here
                  sx={{ marginLeft: 7
                    , width: 225
                    , height: 225 }}
            />
            <Typography variant="body1">Profile Picture</Typography>
            <input
              accept="image/*"
              style={{ display: 'none' }}
              id="profile-picture-upload"
              type="file"
              onChange={handleFileChange}
            />
            <label htmlFor="profile-picture-upload">
              <Button variant="contained" component="span" >
                Upload Profile Picture
              </Button>
            

              <Button onClick={handleSave} color="primary">
            Save
          </Button>
          </label>
          </Stack>


              </section> 

            </td>
        

          </tr>
       


        </table>
      
        

      </Stack>

      {/* More components such as Dialog for editing, Snackbar for notifications can be added here similar to CustomerHomePage */}
    </main>
  );
}