package servlet;

import model.Creator;
import services.CreatorService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/creators")
public class CreatorServlet extends HttpServlet {

    private CreatorService creatorService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        creatorService = (CreatorService) config.
                getServletContext().
                getAttribute("creatorService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("creators", creatorService.findAll());
        request.getRequestDispatcher("creators.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        creatorService.create(requestToCreator(request));
        response.sendRedirect("/creators");
    }

    private Creator requestToCreator(HttpServletRequest request) {
        String createId = request.getParameter("creatId");
        return Creator.builder().id(createId == null ? null : Long.parseLong(createId))
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .dateOfCreate(LocalDateTime.parse(request.getParameter("dateOfBirth")))
                .build();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String creatorId = req.getParameter("creatorId");
        creatorService.deleteById(Long.parseLong("creatorId"));
        resp.sendRedirect("/creators");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        creatorService.update(requestToCreator(req));
        resp.sendRedirect("/creators");
    }
}
