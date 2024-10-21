import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import MainPage from './pages/MainPage';
import MenusPage from './pages/MenusPage';
import MenuDetailsPage from './components/menu/MenuDetailsPage';
import OrdersPage from './pages/OrdersPage';
import OrderDetailsPage from './components/order/OrderDetailsPage';
import Navbar from './components/util/Navbar';

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 10000,
      refetchInterval: 30000,
    },
  },
});

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/menus" element={<MenusPage />} />
          <Route path="/menus/:menuId" element={<MenuDetailsPage />} />
          <Route path="/orders" element={<OrdersPage />} />
          <Route path="/orders/:orderId" element={<OrderDetailsPage />} />
        </Routes>
        <ReactQueryDevtools buttonPosition="bottom-left" initialIsOpen={false} />
      </BrowserRouter>
    </QueryClientProvider>
  );
}

export default App;
