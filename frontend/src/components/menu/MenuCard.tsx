import React from 'react';
import { Button, Card, CardContent, CardMedia, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import menu from '../../assets/img/menu.png';

type MenuCardProps = {
  id: number;
  name: string;
  price: number;
};

function MenuCard({ id, name, price }: MenuCardProps) {
  const navigate = useNavigate();

  return (
    <Card sx={{ display: 'flex', padding: 2, borderRadius: 5, maxWidth: 300 }}>
      <CardContent sx={{ display: 'flex', flexDirection: 'column', flex: 1 }}>
        <Typography sx={{ flex: 1 }} variant="h6">
          {name}
        </Typography>
        <Typography sx={{ flex: 2 }}>{price}$</Typography>
        <Button
          sx={{ flex: 1 }}
          variant="contained"
          onClick={() => {
            navigate(`/menus/${id}`);
          }}
        >
          Details
        </Button>
      </CardContent>
      <CardMedia sx={{ width: 140, height: 140 }} image={menu} />
    </Card>
  );
}

export default MenuCard;
