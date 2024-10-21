import { Box, Avatar } from '@mui/material';
import React from 'react';

type MenuAvatarProps = {
  src: string;
  sx?: React.CSSProperties;
};

function MenuAvatar({ src, sx = {} }: MenuAvatarProps) {
  return (
    <Box sx={sx}>
      <Avatar sx={{ height: '100%', width: '100%' }} src={src} variant="square" />
    </Box>
  );
}

export default MenuAvatar;
