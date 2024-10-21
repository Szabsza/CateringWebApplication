import React from 'react';
import { Avatar, Typography } from '@mui/material';
import burger from '../../assets/img/burger.png';
import VStack from '../util/wrapper/VStack';
import HStack from '../util/wrapper/HStack';

function Greeting() {
  return (
    <HStack sx={{ margin: 'auto', padding: 10, gap: 10 }}>
      <VStack>
        <Typography sx={{ flex: 3 }} variant="h2">
          Welcome!
        </Typography>
        <Typography sx={{ flex: 1 }} variant="h6">
          Are you a chef at your place? Inviting a few friends over for dinner? Working a lot and don`t have time to
          cook? Going with the gang to the mountains? Want good food for a baptism, doctoral celebration, memorial
          service, or a Champions League night? Organizing a business event with 6, 16, or 160 guests? You`ve come to
          the right website.
        </Typography>
      </VStack>
      <Avatar sx={{ width: 200, height: 200 }} variant="square" src={burger} />
    </HStack>
  );
}

export default Greeting;
