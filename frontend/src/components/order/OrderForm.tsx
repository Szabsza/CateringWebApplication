import React, { useCallback, useState } from 'react';
import { FormControl, TextField, Button } from '@mui/material';
import { OrderFormData, OrderFormValidity } from '../../types/OrderTypes';

type OrderFormProps = {
  formData: OrderFormData;
  setFormData: (_: OrderFormData) => void;
  defaultValues: OrderFormData;
  handleSubmit: () => void;
  sx?: React.CSSProperties;
  showExtraDetails?: boolean;
};

export function OrderForm({
  formData,
  setFormData,
  handleSubmit,
  defaultValues,
  sx = {},
  showExtraDetails = false,
}: OrderFormProps) {
  const [isValid, setIsValid] = useState<OrderFormValidity>({
    customerName: formData.customerName.length !== 0,
    date: formData.date.length !== 0,
    deliveryAddress: formData.deliveryAddress.length !== 0,
    totalPrice: !Number.isNaN(formData.totalPrice),
    menuId: !Number.isNaN(formData.menuId),
  });

  const handleCustomerNameChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { value } = event.target;
    setFormData({ ...formData, customerName: value });
    setIsValid({ ...isValid, customerName: value.length !== 0 });
  };

  const handleDeliveryAddressChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { value } = event.target;
    setFormData({ ...formData, deliveryAddress: value });
    setIsValid({ ...isValid, deliveryAddress: value.length !== 0 });
  };

  const checkValidity = useCallback((): boolean => {
    setIsValid({
      customerName: formData.customerName.length !== 0,
      date: formData.date.length !== 0,
      deliveryAddress: formData.deliveryAddress.length !== 0,
      totalPrice: !Number.isNaN(formData.totalPrice),
      menuId: !Number.isNaN(formData.menuId),
    });

    return isValid.customerName && isValid.date && isValid.deliveryAddress && isValid.menuId && isValid.totalPrice;
  }, [formData.customerName, formData.date, formData.deliveryAddress, formData.menuId, formData.totalPrice]);

  return (
    <FormControl sx={sx}>
      {isValid.customerName ? (
        <TextField
          variant="outlined"
          label="Your name"
          defaultValue={defaultValues.customerName}
          onChange={handleCustomerNameChange}
        />
      ) : (
        <TextField
          error
          variant="outlined"
          label="Your name"
          helperText="Your name cannor be empty!"
          defaultValue={defaultValues.customerName}
          onChange={handleCustomerNameChange}
        />
      )}

      {isValid.deliveryAddress ? (
        <TextField
          variant="outlined"
          label="Address"
          defaultValue={defaultValues.deliveryAddress}
          onChange={handleDeliveryAddressChange}
        />
      ) : (
        <TextField
          error
          variant="outlined"
          label="Address"
          helperText="Address cannot be empty!"
          defaultValue={defaultValues.deliveryAddress}
          onChange={handleDeliveryAddressChange}
        />
      )}

      {showExtraDetails && (
        <>
          {isValid.totalPrice ? (
            <TextField disabled variant="outlined" label="Price" defaultValue={defaultValues.totalPrice} />
          ) : (
            <TextField
              error
              disabled
              variant="outlined"
              label="Price"
              helperText="Price must be a number!"
              defaultValue={defaultValues.totalPrice}
            />
          )}

          {isValid.menuId ? (
            <TextField disabled variant="outlined" label="Menu ID" defaultValue={defaultValues.menuId} />
          ) : (
            <TextField
              error
              disabled
              variant="outlined"
              label="Menu ID"
              helperText="Menu does not exists!"
              defaultValue={defaultValues.menuId}
            />
          )}
        </>
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
