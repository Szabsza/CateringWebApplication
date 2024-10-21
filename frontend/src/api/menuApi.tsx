import axios from 'axios';
import { Menu, MenuFormData } from '../types/MenuTypes';

const menuApi = axios.create({
  baseURL: 'http://localhost:8080/api/menus',
  headers: { Accept: 'application/json' },
});

export const getMenus = async (): Promise<Menu[]> => {
  const { data } = await menuApi.get('');
  return data;
};

export const getMenuById = async (id: number): Promise<Menu> => {
  const { data } = await menuApi.get(`/${id}`);
  return data;
};

export const createMenu = async (formData: MenuFormData) => {
  await menuApi.post('', formData);
};

export const deleteMenu = async (id: number) => {
  const { data } = await menuApi.delete(`/${id}`);
  return data;
};

export const updateMenu = async ([id, formData]: [number, MenuFormData]): Promise<Menu> => {
  const { data } = await menuApi.put(`/${id}`, formData);
  return data;
};
