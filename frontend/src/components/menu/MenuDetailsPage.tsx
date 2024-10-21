import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Divider, Typography, IconButton, Modal } from '@mui/material';
import { useNavigate, useParams } from 'react-router-dom';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { useDeleteMenu, useMenu, useUpdateMenu } from '../../hooks/MenuHooks';
import menuImg from '../../assets/img/menu.png';
import VStack from '../util/wrapper/VStack';
import HStack from '../util/wrapper/HStack';
import MenuAvatar from './MenuAvatar';
import BackButton from '../util/button/BackButton';
import { MenuForm } from './MenuForm';
import { MenuFormData } from '../../types/MenuTypes';
import Center from '../util/wrapper/Center';
import { OrderForm } from '../order/OrderForm';
import { useCreateOrder } from '../../hooks/OrderHooks';
import { OrderFormData } from '../../types/OrderTypes';
import { MenuInfo } from './MenuInfo';

function MenuDetailsPage() {
  const { menuId = '0' } = useParams();
  const navigate = useNavigate();

  const { data: menu, status, error } = useMenu(parseInt(menuId, 10));

  const { mutate: updateMenu } = useUpdateMenu(parseInt(menuId, 10));
  const { mutate: deleteMenu } = useDeleteMenu();

  const { mutate: createOrder } = useCreateOrder();

  const [editMode, setEditMode] = useState<boolean>(false);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [menuFormData, setMenuFormData] = useState<MenuFormData>({
    name: '',
    category: '',
    description: '',
    ingredients: '',
    price: 0,
  });
  const [orderFormData, setOrderFormData] = useState<OrderFormData>({
    customerName: '',
    deliveryAddress: '',
    date: new Date().toDateString(),
    totalPrice: 0,
    menuId: 0,
  });

  useEffect(() => {
    if (menu) {
      setMenuFormData({
        name: menu.name,
        category: menu.category,
        description: menu.description,
        ingredients: menu.ingredients,
        price: menu.price,
      });
      setOrderFormData({
        ...orderFormData,
        totalPrice: menu.price,
        menuId: menu.id,
      });
    }
  }, [menu]);

  const handleOnEditClick = () => {
    setEditMode(!editMode);
  };

  const handleOnDeleteClick = () => {
    if (menu) {
      deleteMenu(menu.id);
      navigate('/menus');
    }
  };

  const handleOnEditSubmit = () => {
    if (menu) {
      updateMenu([menu.id, menuFormData]);
      setEditMode(!editMode);
    }
  };

  const handleFormSubmit = () => {
    setIsModalOpen(false);
    createOrder(orderFormData);
  };

  const handleOnBackButtonClick = () => {
    setIsModalOpen(false);
  };

  const handleOnorderClick = () => {
    setIsModalOpen(true);
  };

  return (
    <HStack sx={{ marginRight: 'auto', marginLeft: 'auto', marginTop: 10, maxWidth: 1200 }}>
      <Modal open={isModalOpen} onClose={() => setIsModalOpen(false)}>
        <VStack sx={{ backgroundColor: 'Background', margin: 'auto', height: '100%' }}>
          <BackButton
            sx={{ alignSelf: 'start', marginTop: 4, marginBottom: 4, marginLeft: 4 }}
            onClick={handleOnBackButtonClick}
          />
          <VStack sx={{ marginLeft: 'auto', marginRight: 'auto' }}>
            <Typography variant="h2" sx={{ textAlign: 'center' }}>
              Make an Order!
            </Typography>
            <Divider />
            <OrderForm
              sx={{ marginTop: 5, gap: 2 }}
              formData={orderFormData}
              setFormData={setOrderFormData}
              handleSubmit={handleFormSubmit}
              defaultValues={orderFormData}
            />
          </VStack>
        </VStack>
      </Modal>
      <HStack sx={{ boxShadow: 3, borderRadius: 3, flex: 1, padding: 5 }}>
        <Center>
          <MenuAvatar sx={{ width: '100%', height: '100%' }} src={menuImg} />
        </Center>
        <Divider orientation="vertical" />
        {status === 'success' &&
          (editMode ? (
            <VStack sx={{ marginLeft: 5, marginRight: 5, flex: 1 }}>
              <BackButton sx={{ alignSelf: 'start' }} onClick={handleOnEditClick} />
              <MenuForm
                sx={{ gap: 2, marginTop: 2 }}
                handleSubmit={handleOnEditSubmit}
                formData={menuFormData}
                setFormData={setMenuFormData}
                defaultValues={menuFormData}
              />
            </VStack>
          ) : (
            <VStack sx={{ margin: 5, flex: 1 }}>
              <MenuInfo menuId={menu.id} type="vertical" />
              <ButtonGroup sx={{ margin: 'auto' }} variant="contained">
                <Button onClick={() => handleOnorderClick()}>Order</Button>
                <IconButton onClick={() => handleOnEditClick()}>
                  <EditIcon />
                </IconButton>
                <IconButton onClick={() => handleOnDeleteClick()}>
                  <DeleteIcon />
                </IconButton>
              </ButtonGroup>
            </VStack>
          ))}
        {error && <Typography>error</Typography>}
      </HStack>
    </HStack>
  );
}

export default MenuDetailsPage;
