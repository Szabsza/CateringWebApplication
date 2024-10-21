package edu.bbte.idde.csim2126.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.bbte.idde.csim2126.backend.model.Order;
import edu.bbte.idde.csim2126.backend.service.OrderService;
import edu.bbte.idde.csim2126.backend.service.ServiceException;
import edu.bbte.idde.csim2126.backend.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    public static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    public static final ObjectMapper objectMapper = new ObjectMapper();

    private boolean isValidOrder(Order order) {
        return order.getCustomerName() != null
                && order.getDate() != null
                && order.getTotalPrice() != null
                && order.getDeliveryAddress() != null;
    }

    private void sendErrorJson(HttpServletResponse resp, String message) throws IOException {
        ObjectNode errorJson = objectMapper.createObjectNode();
        errorJson.put("error", message);

        objectMapper.writeValue(resp.getWriter(), errorJson);
    }

    private void sendMessageJson(HttpServletResponse resp, String message) throws IOException {
        ObjectNode json = objectMapper.createObjectNode();
        json.put("message", message);

        objectMapper.writeValue(resp.getWriter(), message);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            String idParam = req.getParameter("id");
            String customerNameParam = req.getParameter("customerName");
            if (idParam != null) {
                try {
                    Long id = Long.parseLong(idParam);
                    Order order = orderService.getOrder(id);

                    if (order != null) {
                        objectMapper.writeValue(resp.getWriter(), objectMapper.convertValue(order, ObjectNode.class));
                        log.info("GET /orders {}", HttpServletResponse.SC_OK);
                    } else {
                        sendErrorJson(resp, "Not found.");
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        log.info("GET /orders {} error: Not found.", HttpServletResponse.SC_NOT_FOUND);
                    }
                } catch (NumberFormatException e) {
                    sendErrorJson(resp, "Invalid number format.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    log.info("GET /orders {} error: Invalid number format.", HttpServletResponse.SC_BAD_REQUEST);
                }
            } else if (customerNameParam != null) {
                objectMapper.writeValue(resp.getWriter(), objectMapper.convertValue(
                        orderService.getOrdersByCustomersName(customerNameParam), ArrayNode.class));
                log.info("GET /orders {}", HttpServletResponse.SC_OK);
            } else {
                objectMapper.writeValue(resp.getWriter(),
                        objectMapper.convertValue(orderService.listOrders(), ArrayNode.class));
                log.info("GET /orders {}", HttpServletResponse.SC_OK);
            }

        } catch (ServiceException e) {
            sendErrorJson(resp, "Get request on orders failed...");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Get request on orders failed...", e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            try {
                Order order = objectMapper.readValue(req.getReader(), Order.class);

                if (!isValidOrder(order)) {
                    sendErrorJson(resp, "Missing or invalid property in the POST request body.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    log.info("POST /orders {} error: Missing or invalid property in the request body.",
                            HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                try {
                    orderService.createOrder(order);
                    objectMapper.writeValue(resp.getWriter(), objectMapper.convertValue(order, ObjectNode.class));
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    log.info("POST /orders {}", HttpServletResponse.SC_CREATED);

                } catch (NumberFormatException e) {
                    sendErrorJson(resp, "Invalid number format.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    log.info("POST /orders {} error: Invalid number format.", HttpServletResponse.SC_BAD_REQUEST);
                }

            } catch (IOException e) {
                sendErrorJson(resp, "Invalid Order input.");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                log.info("POST /orders {} error: Invalid Order input.", HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (ServiceException e) {
            sendErrorJson(resp, "Post request on orders failed...");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Post request on orders failed...", e);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            String idParam = req.getParameter("id");
            if (idParam == null) {
                sendErrorJson(resp, "Missing 'id' parameter in the DELETE request.");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                log.info("DELETE /orders {} error: Missing 'id' parameter.", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                Long id = Long.parseLong(idParam);
                if (orderService.getOrder(id) == null) {
                    sendErrorJson(resp, "There is no such order with this 'id'.");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

                    log.info("DELETE /orders {} error: There is no such order with this 'id'.",
                            HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                orderService.deleteOrderById(id);

                sendMessageJson(resp, "Deleted successfully!");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

                log.info("DELETE /orders {}", HttpServletResponse.SC_NO_CONTENT);

            } catch (NumberFormatException e) {
                sendErrorJson(resp, "Invalid number format.");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                log.info("DELETE /orders {} error: Invalid number format.", HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (ServiceException e) {
            sendErrorJson(resp, "Delete request on orders failed...");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Delete request on orders failed...", e);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            String idParam = req.getParameter("id");
            if (idParam == null) {
                sendErrorJson(resp, "Missing 'id' parameter in the PUT request.");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                log.info("PUT /orders {} error: Missing 'id' parameter.", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                Order order = objectMapper.readValue(req.getReader(), Order.class);

                if (!isValidOrder(order)) {
                    sendErrorJson(resp, "Missing or invalid property in the request body.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    log.info("PUT /orders {} error: Missing or invalid property in the request body.",
                            HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                try {
                    Long id = Long.parseLong(idParam);
                    if (orderService.getOrder(id) == null) {
                        sendErrorJson(resp, "There is no such order with this 'id'.");
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

                        log.info("PUT /orders {} error: There is no such order with this 'id'.",
                                HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    order.setId(id);
                    orderService.updateOrder(order);

                    objectMapper.writeValue(resp.getWriter(), objectMapper.convertValue(order, ObjectNode.class));
                    resp.setStatus(HttpServletResponse.SC_OK);

                    log.info("PUT /orders {}", HttpServletResponse.SC_OK);

                } catch (NumberFormatException e) {
                    sendErrorJson(resp, "Invalid number format.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    log.info("PUT /orders {} error: Invalid number format.", HttpServletResponse.SC_BAD_REQUEST);
                }

            } catch (IOException e) {
                sendErrorJson(resp, "Invalid Order input.");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                log.info("PUT /orders {} error: Invalid Order input.", HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (ServiceException e) {
            sendErrorJson(resp, "Put request on orders failed...");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Put request on orders failed...", e);
        }

    }

}
