import React from 'react';
import { Typography } from '@mui/material';
import { useOrders } from '../hooks/OrderHooks';
import OrderListItem from '../components/order/OrderListItem';
import VStack from '../components/util/wrapper/VStack';

function OrdersPage() {
  const { data: orders, status } = useOrders();

  return (
    <VStack sx={{ margin: 2 }}>
      {status === 'success' && orders.length !== 0 ? (
        orders.map((order) => <OrderListItem key={order.id} id={order.id} date={order.date} menuId={order.menuId} />)
      ) : (
        <Typography>No Orders Yet</Typography>
      )}
    </VStack>
  );
}

export default OrdersPage;
