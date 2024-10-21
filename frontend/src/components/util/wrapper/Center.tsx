import React from 'react';
import Box, { BoxProps } from '@mui/material/Box';

interface CenterProps extends BoxProps {
  children: React.ReactNode[] | React.ReactNode;
}

function Center({ sx, children, ...rest }: CenterProps) {
  return (
    <Box
      sx={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        ...sx,
      }}
      {...rest}
    >
      {children}
    </Box>
  );
}

export default Center;
