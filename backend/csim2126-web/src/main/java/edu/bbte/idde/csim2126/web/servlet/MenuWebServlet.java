package edu.bbte.idde.csim2126.web.servlet;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import edu.bbte.idde.csim2126.backend.model.Menu;
import edu.bbte.idde.csim2126.backend.service.MenuService;
import edu.bbte.idde.csim2126.backend.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@WebServlet(value = "/template-menus", name = "MenuWebServlet")
public class MenuWebServlet extends HttpServlet {
    private static final MenuService menuService = ServiceFactory.getInstance().getMenuService();
    private static final Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/templates", ".hbs"));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, List<Menu>> model = new ConcurrentHashMap<>();
        model.put("menus", menuService.listMenus());

        Template template = handlebars.compile("menus");
        template.apply(model, resp.getWriter());

        resp.setStatus(HttpServletResponse.SC_OK);
        log.info("GET /template-menus {}", HttpServletResponse.SC_OK);
    }
}
