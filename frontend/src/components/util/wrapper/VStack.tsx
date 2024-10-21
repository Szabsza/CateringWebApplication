import React from 'react';
import Box, { BoxProps } from '@mui/material/Box';

function VStack({ children, sx, ...rest }: BoxProps) {
  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        ...sx,
      }}
      {...rest}
    >
      {children}
    </Box>
  );
}

export default VStack;
