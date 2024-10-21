import React from 'react';
import { Typography, Divider } from '@mui/material';
import VStack from '../util/wrapper/VStack';
import HStack from '../util/wrapper/HStack';
import { useMenu } from '../../hooks/MenuHooks';

type MenuInfoProps = {
  menuId: number;
  type: 'horizontal' | 'vertical';
};

export function MenuInfo({ menuId, type }: MenuInfoProps) {
  const { data: menu, status } = useMenu(menuId);

  if (type === 'horizontal' && status === 'success') {
    return (
      <HStack sx={{ width: '100%', marginTop: 3 }}>
        <VStack sx={{ flex: 2, gap: 1 }}>
          <Typography sx={{ fontSize: 20 }}>Name</Typography>
          <Divider />
          <Typography>{menu.name}</Typography>
        </VStack>

        <VStack sx={{ flex: 2, gap: 1 }}>
          <Typography sx={{ fontSize: 20 }}>Category</Typography>
          <Divider />
          <Typography>{menu.category}</Typography>
        </VStack>

        <VStack sx={{ flex: 2, gap: 1 }}>
          <Typography sx={{ fontSize: 20 }}>Description</Typography>
          <Divider />
          <Typography>{menu.description}</Typography>
        </VStack>

        <VStack sx={{ flex: 2, gap: 1 }}>
          <Typography sx={{ fontSize: 20 }}>Ingredients</Typography>
          <Divider />
          <Typography>{menu.ingredients}</Typography>
        </VStack>

        <VStack sx={{ flex: 1, gap: 1 }}>
          <Typography sx={{ fontSize: 20 }}>Price</Typography>
          <Divider />
          <Typography>{menu.price}$</Typography>
        </VStack>
      </HStack>
    );
  }

  if (type === 'vertical' && status === 'success') {
    return (
      <VStack sx={{ margin: 5, flex: 1 }}>
        <Typography sx={{ flex: 1, textAlign: 'center' }} variant="h3">
          {menu.name}
        </Typography>

        <Divider>Category</Divider>

        <Typography sx={{ flex: 1, textAlign: 'center' }} variant="h5">
          {menu.category}
        </Typography>

        <Divider>Description</Divider>

        <Typography sx={{ flex: 1, textAlign: 'center' }} variant="h5">
          {menu.description}
        </Typography>

        <Divider>Ingredients</Divider>

        <Typography sx={{ flex: 1, textAlign: 'center' }} variant="h5">
          {menu.ingredients}
        </Typography>

        <Divider>Price</Divider>

        <Typography sx={{ flex: 1, textAlign: 'center' }} variant="h5">
          {menu.price}$
        </Typography>
      </VStack>
    );
  }

  return <Typography>error</Typography>;
}
