import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Typography, Divider, IconButton } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import VStack from '../util/wrapper/VStack';
import { useOrder, useUpdateOrder } from '../../hooks/OrderHooks';
import HStack from '../util/wrapper/HStack';
import MenuAvatar from '../menu/MenuAvatar';
import menuImg from '../../assets/img/menu.png';
import { MenuInfo } from '../menu/MenuInfo';
import { OrderFormData } from '../../types/OrderTypes';
import BackButton from '../util/button/BackButton';
import { OrderForm } from './OrderForm';

function OrderDetailsPage() {
  const { orderId = '0' } = useParams();
  const navigate = useNavigate();

  const { data: order, status } = useOrder(parseInt(orderId, 10));

  const { mutate: updateOrder } = useUpdateOrder(parseInt(orderId, 10));

  const [editMode, setEditMode] = useState<boolean>(false);
  const [orderFormData, setOrderFormData] = useState<OrderFormData>({
    customerName: '',
    deliveryAddress: '',
    date: new Date().toDateString(),
    totalPrice: 0,
    menuId: 0,
  });

  useEffect(() => {
    if (order) {
      setOrderFormData({
        customerName: order.customerName,
        deliveryAddress: order.deliveryAddress,
        date: order.date,
        totalPrice: order.totalPrice,
        menuId: order.menuId,
      });
    }
  }, [order]);

  const handleOnEditClick = () => {
    setEditMode(!editMode);
  };

  const handleOnEditSubmit = () => {
    if (order) {
      updateOrder([order.id, orderFormData]);
      setEditMode(!editMode);
    }
  };

  return (
    <VStack sx={{ marginRight: 'auto', marginLeft: 'auto', marginTop: 5, maxWidth: 1200, gap: 2 }}>
      <HStack
        sx={{ boxShadow: 3, borderRadius: 3, flex: 1, padding: 1 }}
        onClick={() => navigate(`/menus/${order?.menuId}`)}
      >
        <MenuAvatar src={menuImg} sx={{ width: 150, height: 150, margin: 3 }} />
        {status === 'success' && <MenuInfo menuId={order.menuId} type="horizontal" />}
      </HStack>
      {status === 'success' &&
        (editMode ? (
          <VStack sx={{ boxShadow: 3, borderRadius: 3, flex: 1, padding: 10, gap: 2 }}>
            <BackButton sx={{ alignSelf: 'start' }} onClick={handleOnEditClick} />
            <OrderForm
              sx={{ gap: 2, marginTop: 2 }}
              handleSubmit={handleOnEditSubmit}
              formData={orderFormData}
              setFormData={setOrderFormData}
              defaultValues={orderFormData}
            />
          </VStack>
        ) : (
          <VStack sx={{ boxShadow: 3, borderRadius: 3, flex: 1, padding: 10, gap: 2, paddingBottom: 7 }}>
            <Typography sx={{ flex: 1, textAlign: 'center' }} variant="h3">
              Customer Info
            </Typography>
            <Divider>Customer`s Name</Divider>
            <Typography sx={{ flex: 1, textAlign: 'center' }} variant="h5">
              {order.customerName}
            </Typography>
            <Divider>Delivery Address</Divider>
            <Typography sx={{ flex: 1, textAlign: 'center' }} variant="h5">
              {order.deliveryAddress}
            </Typography>
            <IconButton
              sx={{ marginLeft: 'auto', marginRight: 'auto', boxShadow: 2 }}
              color="primary"
              onClick={() => {
                setEditMode(true);
              }}
            >
              <EditIcon />
            </IconButton>
          </VStack>
        ))}
    </VStack>
  );
}

export default OrderDetailsPage;
