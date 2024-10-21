import React from 'react';
import { AppBar, Button, Divider, IconButton } from '@mui/material';
import ShoppingCartOutlinedIcon from '@mui/icons-material/ShoppingCartOutlined';
import HomeIcon from '@mui/icons-material/Home';
import { useNavigate } from 'react-router-dom';
import HStack from './wrapper/HStack';

function Navbar() {
  const navigate = useNavigate();

  return (
    <AppBar position="static" sx={{ height: 60 }}>
      <HStack sx={{ gap: 1, margin: 'auto', marginLeft: 2, marginRight: 2 }}>
        <IconButton
          onClick={() => {
            navigate('/');
          }}
        >
          <HomeIcon sx={{ color: 'white', fontSize: 30 }} />
        </IconButton>

        <Divider orientation="vertical" />

        <Button
          variant="contained"
          onClick={() => {
            navigate('/menus');
          }}
        >
          Menus
        </Button>
        <IconButton
          sx={{ marginRight: 0, marginLeft: 'auto' }}
          onClick={() => {
            navigate('/orders');
          }}
        >
          <ShoppingCartOutlinedIcon sx={{ color: 'white', fontSize: 30 }} />
        </IconButton>
      </HStack>
    </AppBar>
  );
}

export default Navbar;
