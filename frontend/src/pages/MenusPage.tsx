import { Typography, Fab, Modal, Divider } from '@mui/material';
import Grid from '@mui/material/Grid';
import AddIcon from '@mui/icons-material/Add';
import { useState } from 'react';
import MenuCard from '../components/menu/MenuCard';
import { useCreateMenu, useMenus } from '../hooks/MenuHooks';
import Center from '../components/util/wrapper/Center';
import { MenuForm } from '../components/menu/MenuForm';
import { MenuFormData } from '../types/MenuTypes';
import VStack from '../components/util/wrapper/VStack';
import BackButton from '../components/util/button/BackButton';

function MenusPage() {
  const { data: menus, status } = useMenus();

  const { mutate: createMenu } = useCreateMenu();

  const [isModalOpen, setModalOpen] = useState<boolean>(false);
  const [formData, setFormData] = useState<MenuFormData>({
    name: '',
    category: '',
    description: '',
    ingredients: '',
    price: 0,
  });

  const handleCreateClick = () => {
    setModalOpen(true);
  };

  const handleFormSubmit = () => {
    setModalOpen(false);
    createMenu(formData);
  };

  const handleOnBackButtonClick = () => {
    setModalOpen(false);
  };

  return (
    <Center sx={{ margin: 2, position: 'relative' }}>
      {status === 'success' ? (
        <Grid sx={{ margin: 2, gap: 1, alignItems: 'center', justifyContent: 'center' }} spacing={1} container>
          {menus.length !== 0 ? (
            menus.map((menu) => (
              <Grid sx={{ maxWidth: 300, minWidth: 300 }} item md={2} key={menu.id}>
                <MenuCard id={menu.id} name={menu.name} price={menu.price} />
              </Grid>
            ))
          ) : (
            <Grid item md={12}>
              There are no menus yet!
            </Grid>
          )}
        </Grid>
      ) : (
        <Typography>error</Typography>
      )}
      <Modal open={isModalOpen} onClose={() => setModalOpen(false)}>
        <VStack sx={{ backgroundColor: 'Background', margin: 'auto', height: '100%' }}>
          <BackButton
            sx={{ alignSelf: 'start', marginTop: 4, marginBottom: 4, marginLeft: 4 }}
            onClick={handleOnBackButtonClick}
          />
          <VStack sx={{ marginLeft: 'auto', marginRight: 'auto' }}>
            <Typography variant="h2" sx={{ textAlign: 'center' }}>
              Create Menu
            </Typography>
            <Divider />
            <MenuForm
              sx={{ marginTop: 5, gap: 2 }}
              formData={formData}
              setFormData={setFormData}
              handleSubmit={handleFormSubmit}
              defaultValues={formData}
            />
          </VStack>
        </VStack>
      </Modal>
      <Fab
        color="primary"
        sx={{ margin: 0, top: 'auto', right: 20, bottom: 20, left: 'auto', position: 'fixed', boxShadow: 5 }}
        onClick={() => handleCreateClick()}
      >
        <AddIcon />
      </Fab>
    </Center>
  );
}

export default MenusPage;
