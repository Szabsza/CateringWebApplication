import React from 'react';
import { IconButton, ListItem, Typography } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import DiningRoundedIcon from '@mui/icons-material/DiningRounded';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import PaymentIcon from '@mui/icons-material/Payment';
import InfoIcon from '@mui/icons-material/Info';
import { useNavigate } from 'react-router-dom';
import HStack from '../util/wrapper/HStack';
import Center from '../util/wrapper/Center';
import { useMenu } from '../../hooks/MenuHooks';
import { useDeleteOrder } from '../../hooks/OrderHooks';

type OrderListItemProps = {
  id: number;
  date: string;
  menuId: number;
};

function OrderListItem({ id, date, menuId }: OrderListItemProps) {
  const navigate = useNavigate();

  const { data: menu } = useMenu(menuId);

  const { mutate: deleteOrder } = useDeleteOrder();

  const handleOnDeleteClick = () => {
    deleteOrder(id);
  };

  return (
    <ListItem sx={{ boxShadow: 3, padding: 3, borderRadius: 2, maxWidth: 1200, margin: 'auto' }}>
      <HStack sx={{ gap: 1, margin: 'auto', marginLeft: 2, width: '100%' }}>
        <Center>
          <DiningRoundedIcon sx={{ fontSize: 30 }} />
        </Center>
        <Center>
          <Typography>{menu?.name}</Typography>
        </Center>

        <Center>
          <CalendarMonthIcon sx={{ fontSize: 30 }} />
        </Center>
        <Center>
          <Typography sx={{ margin: 'auto' }}>{date}</Typography>
        </Center>

        <Center>
          <PaymentIcon sx={{ fontSize: 30 }} />
        </Center>
        <Center>
          <Typography sx={{ margin: 'auto' }}>{menu?.price}$</Typography>
        </Center>

        <IconButton
          sx={{ marginRight: 1, marginLeft: 'auto', boxShadow: 3 }}
          color="primary"
          onClick={() => {
            navigate(`/orders/${id}`);
          }}
        >
          <InfoIcon sx={{ fontSize: 30 }} />
        </IconButton>

        <IconButton sx={{ marginRight: 0, boxShadow: 3 }} color="primary" onClick={() => handleOnDeleteClick()}>
          <DeleteIcon sx={{ fontSize: 30 }} />
        </IconButton>
      </HStack>
    </ListItem>
  );
}

export default OrderListItem;
