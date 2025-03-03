package hiber;

import hiber.config.AppConfig;
import hiber.dao.UserDao;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        CarService carService = context.getBean(CarService.class);

        Car car1 = new Car("BMW", 5);
        Car car2 = new Car("Mercedes", 220);
        Car car3 = new Car("Audi", 8);
        Car car4 = new Car("Toyota", 2023);

        carService.add(car1);
        carService.add(car2);
        carService.add(car3);
        carService.add(car4);


        User user1 = new User("Иван", "Иванов", "ivanov@gogle.com");
        User user2 = new User("Сидор", "Сидоров", "sidorov@gmail.com");
        User user3 = new User("Алиса", "Алисовна", "alisovna@gmail.com");
        User user4 = new User("Николай", "Николаев", "nikolaev@gmail.com");
        User user5 = new User("Анна", "Иванова", "ivanova@gmail.com");
        User user6 = new User("Михаил", "Михайлов", "mihaylov@gmail.com");

//        userService.add(user1);
//        userService.add(user2);
//        userService.add(user3);
//        userService.add(user4);
        userService.add(user5);
        userService.add(user6);


        List<User> users = userService.listUsers();
        List<Car> cars = carService.listCar();


        car1.setUser(user1);
        user1.setCar(car1);
        userService.add(user1);

        car2.setUser(user2);
        user2.setCar(car2);
        userService.add(user2);

        car3.setUser(user3);
        user3.setCar(car3);
        userService.add(user3);

        car4.setUser(user4);
        user4.setCar(car4);
        userService.add(user4);


        String searchModel = "BMW";
        int searchSeries = 5;
        List<User> users_car = userService.getUserByCarModelAndSeries(searchModel, searchSeries);
        if (users.isEmpty()) {
            System.out.println("Пользователи не найдены");
        } else {
            System.out.println("Пользователи найдены");
        }

        context.close();
    }
}
