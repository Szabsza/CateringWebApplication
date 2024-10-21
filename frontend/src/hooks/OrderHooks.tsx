import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { createOrder, deleteOrder, getOrderById, getOrders, updateOrder } from '../api/orderApi';

export const useOrders = () => {
  return useQuery({
    queryKey: ['orders'],
    queryFn: () => getOrders(),
  });
};

export const useOrder = (id: number) => {
  return useQuery({
    queryKey: ['orders', `${id}`],
    queryFn: () => getOrderById(id),
  });
};

export const useCreateOrder = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: createOrder,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['orders'] });
    },
    onError: (error) => {
      console.log(error);
    },
  });
};

export const useUpdateOrder = (id: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: updateOrder,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['orders', `${id}`] });
    },
    onError: (error) => {
      console.log(error);
    },
  });
};

export const useDeleteOrder = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: deleteOrder,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['orders'] });
    },
    onError: (error) => {
      console.log(error);
    },
  });
};
