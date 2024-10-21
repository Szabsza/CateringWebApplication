export type Order = {
  id: number;
  customerName: string;
  deliveryAddress: string;
  date: string;
  totalPrice: number;
  menuId: number;
};

export type OrderFormData = {
  customerName: string;
  deliveryAddress: string;
  date: string;
  totalPrice: number;
  menuId: number;
};

export type OrderFormValidity = {
  customerName: boolean;
  deliveryAddress: boolean;
  date: boolean;
  totalPrice: boolean;
  menuId: boolean;
};
