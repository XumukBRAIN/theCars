package servlet;

import model.Car;
import model.OffRoadCar;
import model.RetroCar;
import model.SportCar;
import services.CarService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/cars")
public class CarServlet extends HttpServlet {

    private CarService carService;
    private Map<String, Class<? extends Car>> disc;

    @Override
    public void init(ServletConfig config) throws ServletException {
        carService = (CarService) config.getServletContext().getAttribute("carService");

        disc = new HashMap<>();
        disc.put("sportCar", SportCar.class);
        disc.put("retroCar", RetroCar.class);
        disc.put("offRoadCar", OffRoadCar.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Car> cars;
        String creatorId = request.getParameter("creatorId");
        if(creatorId != null){
            request.setAttribute("creatorId", creatorId);
            cars = carService.findForCreatorId(Long.parseLong(creatorId));
        }else {
            cars = carService.findAll();
        }
        request.setAttribute("cars", cars);
        request.getRequestDispatcher("cars.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Car car = requestToCar(request);
        Long authorId = Long.parseLong(request.getParameter("creatorId"));

        carService.createForCreator(car, authorId);
    }

    private Car requestToCar(HttpServletRequest request) {
        String strId = request.getParameter("id");
        Long id = strId == null? null: Long.parseLong(strId);
        String brand = request.getParameter("brand");
        for (Map.Entry<String, Class<? extends Car>> entry : disc.entrySet()) {
            String parameter = request.getParameter(entry.getKey());
            if (parameter != null && !parameter.isEmpty()) {
                try {
                    Car car = entry.getValue().newInstance();
                    car.setId(id);
                    car.setBrand(brand);
                    Field field = entry.getValue().getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(car, parameter);
                    return car;
                } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        carService.update(requestToCar(req));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String bookId = req.getParameter("id");
        carService.deleteById(Long.parseLong(bookId));
    }
}
