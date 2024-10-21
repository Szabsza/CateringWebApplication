export type Menu = {
  id: number;
  name: string;
  category: string;
  description: string;
  ingredients: string;
  price: number;
};

export type MenuFormData = {
  name: string;
  category: string;
  description: string;
  ingredients: string;
  price: number;
};

export type MenuFormValidity = {
  name: boolean;
  category: boolean;
  description: boolean;
  ingredients: boolean;
  price: boolean;
};
