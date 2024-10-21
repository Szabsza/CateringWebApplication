import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { getMenus, getMenuById, updateMenu, createMenu, deleteMenu } from '../api/menuApi';

export const useMenus = () => {
  return useQuery({
    queryKey: ['menus'],
    queryFn: () => getMenus(),
  });
};

export const useMenu = (id: number) => {
  return useQuery({
    queryKey: ['menus', `${id}`],
    queryFn: () => getMenuById(id),
  });
};

export const useCreateMenu = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: createMenu,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['menus'] });
    },
    onError: (error) => {
      console.log(error);
    },
  });
};

export const useUpdateMenu = (id: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: updateMenu,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['menus', `${id}`] });
    },
    onError: (error) => {
      console.log(error);
    },
  });
};

export const useDeleteMenu = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: deleteMenu,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['orders'] });
      queryClient.invalidateQueries({ queryKey: ['menus'] });
    },
    onError: (error) => {
      console.log(error);
    },
  });
};
