import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { IconButton } from '@mui/material';

type BackButtonProps = {
  onClick: () => void;
  sx?: React.CSSProperties;
};

function BackButton({ onClick, sx = {} }: BackButtonProps) {
  return (
    <IconButton sx={sx} onClick={() => onClick()}>
      <ArrowBackIcon />
    </IconButton>
  );
}

export default BackButton;
