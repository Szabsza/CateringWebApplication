import React, { useCallback, useState } from 'react';
import { FormControl, TextField, Button } from '@mui/material';
import { MenuFormData, MenuFormValidity } from '../../types/MenuTypes';

type MenuFormProps = {
  formData: MenuFormData;
  setFormData: (_: MenuFormData) => void;
  defaultValues: MenuFormData;
  handleSubmit: () => void;
  sx?: React.CSSProperties;
};

export function MenuForm({ formData, setFormData, handleSubmit, defaultValues, sx = {} }: MenuFormProps) {
  const [isValid, setIsValid] = useState<MenuFormValidity>({
    name: formData.name.length !== 0,
    category: formData.category.length !== 0,
    description: formData.description.length !== 0,
    ingredients: formData.ingredients.length !== 0,
    price: !Number.isNaN(formData.price) && formData.price > 0,
  });

  const handleNameChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { value } = event.target;
    setFormData({ ...formData, name: value });
    setIsValid({ ...isValid, name: value.length !== 0 });
  };

  const handleCategoryChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { value } = event.target;
    setFormData({ ...formData, category: value });
    setIsValid({ ...isValid, category: value.length !== 0 });
  };

  const handleDescriptionChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { value } = event.target;
    setFormData({ ...formData, description: value });
    setIsValid({ ...isValid, description: value.length !== 0 });
  };

  const handleIngredientsChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { value } = event.target;
    setFormData({ ...formData, ingredients: value });
    setIsValid({ ...isValid, ingredients: value.length !== 0 });
  };

  const handlePriceChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { value } = event.target;
    setFormData({ ...formData, price: parseInt(value, 10) });
    setIsValid({ ...isValid, price: !Number.isNaN(parseInt(value, 10)) && parseInt(value, 10) > 0 });
  };

  const checkValidity = useCallback((): boolean => {
    setIsValid({
      name: formData.name.length !== 0,
      category: formData.category.length !== 0,
      description: formData.description.length !== 0,
      ingredients: formData.ingredients.length !== 0,
      price: !Number.isNaN(formData.price) && formData.price > 0,
    });

    return isValid.name && isValid.category && isValid.description && isValid.ingredients && isValid.price;
  }, [formData.name, formData.category, formData.description, formData.ingredients, formData.price]);

  return (
    <FormControl sx={sx}>
      {isValid.name ? (
        <TextField variant="outlined" label="Menu name" defaultValue={defaultValues.name} onChange={handleNameChange} />
      ) : (
        <TextField
          error
          variant="outlined"
          label="Menu name"
          helperText="Name cannot be empty!"
          defaultValue={defaultValues.name}
          onChange={handleNameChange}
        />
      )}

      {isValid.category ? (
        <TextField
          variant="outlined"
          label="Category"
          defaultValue={defaultValues.category}
          onChange={handleCategoryChange}
        />
      ) : (
        <TextField
          error
          variant="outlined"
          label="Category"
          helperText="Category cannot be empty"
          defaultValue={defaultValues.category}
          onChange={handleCategoryChange}
        />
      )}

      {isValid.description ? (
        <TextField
          variant="outlined"
          label="Description"
          multiline
          maxRows={2}
          defaultValue={defaultValues.description}
          onChange={handleDescriptionChange}
        />
      ) : (
        <TextField
          error
          variant="outlined"
          label="Description"
          multiline
          maxRows={2}
          helperText="Description cannot be empty!"
          defaultValue={defaultValues.description}
          onChange={handleDescriptionChange}
        />
      )}

      {isValid.ingredients ? (
        <TextField
          variant="outlined"
          label="Ingredients"
          multiline
          maxRows={2}
          defaultValue={defaultValues.ingredients}
          onChange={handleIngredientsChange}
        />
      ) : (
        <TextField
          error
          variant="outlined"
          label="Ingredients"
          multiline
          maxRows={2}
          helperText="Ingredients cannot be empty!"
          defaultValue={defaultValues.ingredients}
          onChange={handleIngredientsChange}
        />
      )}

      {isValid.price ? (
        <TextField
          variant="outlined"
          type="number"
          label="Price"
          defaultValue={defaultValues.price}
          onChange={handlePriceChange}
        />
      ) : (
        <TextField
          error
          variant="outlined"
          type="number"
          label="Price"
          helperText="Price must be a number and greater than 0!"
          defaultValue={defaultValues.price}
          onChange={handlePriceChange}
        />
      )}

      <Button
        sx={{ margin: 'auto' }}
        variant="contained"
        onClick={() => {
          if (checkValidity()) {
            handleSubmit();
          }
        }}
      >
        Submit
      </Button>
    </FormControl>
  );
}
