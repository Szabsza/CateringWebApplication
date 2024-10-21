import React from 'react';
import Box, { BoxProps } from '@mui/material/Box';

function HStack({ children, sx, ...rest }: BoxProps) {
  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'row',
        ...sx,
      }}
      {...rest}
    >
      {children}
    </Box>
  );
}

export default HStack;
