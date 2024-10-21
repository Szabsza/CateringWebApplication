package edu.bbte.idde.csim2126.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.bbte.idde.csim2126.backend.model.Menu;
import edu.bbte.idde.csim2126.backend.service.MenuService;
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
@WebServlet("/menus")
public class MenuServlet extends HttpServlet {
    private static final MenuService menuService = ServiceFactory.getInstance().getMenuService();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private boolean isValidMenu(Menu menu) {
        return menu.getName() != null
                && menu.getCategory() != null
                && menu.getDescription() != null
                && menu.getIngredients() != null
                && menu.getPrice() != null;
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
            String categoryParam = req.getParameter("category");
            if (idParam != null) {
                try {
                    Long id = Long.parseLong(idParam);
                    Menu menu = menuService.getMenu(id);

                    if (menu != null) {
                        objectMapper.writeValue(resp.getWriter(), objectMapper.convertValue(menu, ObjectNode.class));
                        log.info("GET /menus {}", HttpServletResponse.SC_OK);
                    } else {
                        sendErrorJson(resp, "Not found.");
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        log.info("GET /menus {} error: Not found.", HttpServletResponse.SC_NOT_FOUND);
                    }
                } catch (NumberFormatException e) {
                    sendErrorJson(resp, "Invalid number format.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    log.info("GET /menus {} error: Invalid number format.", HttpServletResponse.SC_BAD_REQUEST);
                }
            } else if (categoryParam != null) {
                objectMapper.writeValue(resp.getWriter(),
                        objectMapper.convertValue(menuService.getMenusByCategory(categoryParam), ArrayNode.class));
                log.info("GET /menus {}", HttpServletResponse.SC_OK);
            } else {
                objectMapper.writeValue(resp.getWriter(),
                        objectMapper.convertValue(menuService.listMenus(), ArrayNode.class));
                log.info("GET /menus {}", HttpServletResponse.SC_OK);
            }

        } catch (ServiceException e) {
            sendErrorJson(resp, "Get request on menus failed...");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Get request on menus failed...", e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            try {
                Menu menu = objectMapper.readValue(req.getReader(), Menu.class);

                if (!isValidMenu(menu)) {
                    sendErrorJson(resp, "Missing or invalid property in the POST request body.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    log.info("POST /menus {} error: Missing or invalid property in the request body.",
                            HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                try {
                    menuService.createMenu(menu);
                    objectMapper.writeValue(resp.getWriter(), objectMapper.convertValue(menu, ObjectNode.class));
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    log.info("POST /menus {}", HttpServletResponse.SC_CREATED);

                } catch (NumberFormatException e) {
                    sendErrorJson(resp, "Invalid number format.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    log.info("POST /menus {} error: Invalid number format.", HttpServletResponse.SC_BAD_REQUEST);
                }

            } catch (IOException e) {
                sendErrorJson(resp, "Invalid Menu input.");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                log.info("POST /menus {} error: Invalid Menu input.", HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (ServiceException e) {
            sendErrorJson(resp, "Post request on menus failed...");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Post request on menus failed...", e);
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

                log.info("DELETE /menus {} error: Missing 'id' parameter.", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                Long id = Long.parseLong(idParam);
                if (menuService.getMenu(id) == null) {
                    sendErrorJson(resp, "There is no such menu with this 'id'.");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

                    log.info("DELETE /menus {} error: There is no such menu with this 'id'.",
                            HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                menuService.deleteMenuById(id);

                sendMessageJson(resp, "Deleted successfully!");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

                log.info("DELETE /menus {}", HttpServletResponse.SC_NO_CONTENT);

            } catch (NumberFormatException e) {
                sendErrorJson(resp, "Invalid number format.");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                log.info("DELETE /menus {} error: Invalid number format.", HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (ServiceException e) {
            sendErrorJson(resp, "Delete request on menus failed...");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Delete request on menus failed...", e);
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

                log.info("PUT /menus {} error: Missing 'id' parameter.", HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                Menu menu = objectMapper.readValue(req.getReader(), Menu.class);

                if (!isValidMenu(menu)) {
                    sendErrorJson(resp, "Missing or invalid property in the PUT request body.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    log.info("PUT /menus {} error: Missing or invalid property in the request body.",
                            HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                try {
                    Long id = Long.parseLong(idParam);
                    if (menuService.getMenu(id) == null) {
                        sendErrorJson(resp, "There is no such menu with this 'id'.");
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

                        log.info("PUT /menus {} error: There is no such menu with this 'id'.",
                                HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    menu.setId(id);
                    menuService.updateMenu(menu);

                    objectMapper.writeValue(resp.getWriter(), objectMapper.convertValue(menu, ObjectNode.class));
                    resp.setStatus(HttpServletResponse.SC_OK);

                    log.info("PUT /menus {}", HttpServletResponse.SC_OK);

                } catch (NumberFormatException e) {
                    sendErrorJson(resp, "Invalid number format.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    log.info("PUT /menus {} error: Invalid number format.", HttpServletResponse.SC_BAD_REQUEST);
                }

            } catch (IOException e) {
                sendErrorJson(resp, "Invalid Menu input.");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                log.info("PUT /menus {} error: Invalid Menu input.", HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (ServiceException e) {
            sendErrorJson(resp, "Put request on menus failed...");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Put request on menus failed...", e);
        }

    }

}
