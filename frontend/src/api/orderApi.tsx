import axios from 'axios';
import { Order, OrderFormData } from '../types/OrderTypes';

const orderApi = axios.create({
  baseURL: 'http://localhost:8080/api/orders',
  headers: { Accept: 'application/json' },
});

export const getOrders = async (): Promise<Order[]> => {
  const { data } = await orderApi.get('');
  return data;
};

export const getOrderById = async (id: number): Promise<Order> => {
  const { data } = await orderApi.get(`/${id}`);
  return data;
};

export const createOrder = async (formData: OrderFormData) => {
  await orderApi.post('', formData);
};

export const deleteOrder = async (id: number) => {
  const { data } = await orderApi.delete(`/${id}`);
  return data;
};

export const updateOrder = async ([id, formData]: [number, OrderFormData]): Promise<Order> => {
  const { data } = await orderApi.put(`/${id}`, formData);
  return data;
};
