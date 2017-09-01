package com.mainacad.myproject.controler;

import com.mainacad.myproject.entities.Order;
import com.mainacad.myproject.entities.OrderedDish;
import com.mainacad.myproject.entities.User;
import com.mainacad.myproject.entities.Dish;
import com.mainacad.myproject.repository.UserDao;
import com.mainacad.myproject.services.MenuService;
import com.mainacad.myproject.services.OrderService;
import com.mainacad.myproject.services.TablesService;
import com.mainacad.myproject.services.UserService;
import org.apache.poi.openxml4j.opc.internal.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Controller
@Repository
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    //User user = userService.getUser((long)201);

    User user = null;

    @Autowired
    private MenuService menuService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TablesService tablesService;

    @Autowired
    private OrderService orderService;



    @RequestMapping("/tables")
    public String tables(Model model) {
        model.addAttribute("table", tablesService.listTable());
        System.out.println(tablesService.listTable());
        return "tables";
    }


    @RequestMapping(value = "/menu/add-table", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> tableAdd(@RequestParam("tableId") int tableId) {
        if (tableId == 0) {
            return Ajax.emptyResponse();
        }
        //dataService.persist(data);
        System.out.println("tableId = " + tableId);
        System.out.println(tablesService.getTable(tableId));


        //System.out.println(user.getMyOrder());


        if (user == null) {
            user = userService.initUser();
        }
        if (user.getMyOrder() == null) {
            user.setMyOrder(new Order());
            user.getMyOrder().setCustomer(user);
        }

        System.out.println(user.getMyOrder());

        tablesService.addTable(tableId, user.getMyOrder());

        System.out.println(user.getMyOrder());
        return Ajax.emptyResponse();

    }

    @RequestMapping("/menu")
    public String menu(Model model) {
        //model.addAttribute("employees", referenceService.listOfEmployees());
        return "menu";
    }

    @RequestMapping(value = "/first_dish", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getFirstDish() {
            List<Dish> result =  menuService.listDishes("перше");

            System.out.println(menuService.listDishes("перше"));
            return Ajax.successResponse(result);
    }


    @RequestMapping(value = "/second_dish", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getSecondDish() {
        List<Dish> result =  menuService.listDishes("друге");


        System.out.println(menuService.listDishes("друге"));
        return Ajax.successResponse(result);
    }

    @RequestMapping(value = "/hot_snack", method = RequestMethod.GET)
    public ResponseEntity getHotSnack() {
        List<Dish> result =  menuService.listDishes("гаряча закуска");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("myheader", "Привіт!!!");
        System.out.println(menuService.listDishes("гаряча закуска"));
        return new ResponseEntity<>(Ajax.successResponse(result), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/cold_snack", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getColdSnack() {
        List<Dish> result =  menuService.listDishes("холодна закуска");

        System.out.println(menuService.listDishes("холодна закуска"));
        return Ajax.successResponse(result);
    }

    @RequestMapping(value = "/garnish", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getGarnish() {
        List<Dish> result =  menuService.listDishes("гарнір");

        System.out.println(menuService.listDishes("гарнір"));
        return Ajax.successResponse(result);
    }

    @RequestMapping(value = "/dessert", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getDessert() {
        List<Dish> result =  menuService.listDishes("десерт");

        System.out.println(menuService.listDishes("десерт"));
        return Ajax.successResponse(result);
    }

    @RequestMapping(value = "/menu/add-dish-to-order", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> menuAdd(@RequestParam("dishId") long dishId,
                                @RequestParam("countDish") int countDish,
                                Model model) {
            if (dishId == 0) {
                return Ajax.emptyResponse();
            }
            //dataService.persist(data);
            System.out.println("dishId = " + dishId + "  count: " + countDish);
            System.out.println(menuService.getDish(dishId));


        //System.out.println(user.getMyOrder());

        if (user == null) {
            user = userService.initUser();
        }
        if (user.getMyOrder() == null) {
            user.setMyOrder(new Order());
            user.getMyOrder().setCustomer(user);
        }

        System.out.println(user.getMyOrder());

        System.out.println(user.getMyOrders());


        menuService.addDish(dishId, countDish, user.getMyOrder());
        //orderService.addOrder(user.getMyOrder());
        System.out.println(user.getMyOrder());
        return Ajax.emptyResponse();

    }

    @RequestMapping(value = "/menu/add-order", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> orderAdd(Model model) {

            if (user.getMyOrder() == null || user.getMyOrder().getOrderedDishes().size() == 0) {
                return Ajax.errorResponse("dishNull");
            }

            if(user.getMyOrder().getTableOrdered() == null) {
                return Ajax.errorResponse("tableNull");
            }


            orderService.addOrder(user.getMyOrder());

            user.setMyOrder(null);

            return Ajax.emptyResponse();
    }


//    @RequestMapping("/orders")
//    public String orders(Model model) {
//
//        if (user == null) {
//            user = userService.getUser((long) 201);
//        }
//        List<Order> res = orderService.orderList(user);
//
//        for (Order order : res) {
//            OrderService.changeSum(order);
//        }
//        if (user.getMyOrder()!=null) {
//            res.add(user.getMyOrder());
//        }
//        System.out.println(res);
//        model.addAttribute("orders", res);
//        return "orders";
//    }

//    @RequestMapping(value = "/all_orders", method = RequestMethod.GET)
//    public @ResponseBody
//    Map<String, Object> orders2(Model model) {
//
//        if (user == null) {
//            user = userService.getUser((long) 201);
//        }
//        List<Order> res = orderService.orderList(user);
//
//        for (Order order : res) {
//            OrderService.changeSum(order);
//        }
//        //res.add(user.getMyOrder());
//        System.out.println(res);
//
//        return Ajax.successResponse(res);
//    }
//
//        @RequestMapping("/orders")
//    public String orders(Model model) {
//        return "orders";
//    }

//    @RequestMapping(value = "/orders", method = RequestMethod.GET)
//    public ResponseEntity<?> orders2(Model model) {
//
//        if (user == null) {
//            user = userService.getUser((long) 201);
//        }
//        List<Order> res = orderService.orderList(user);
//
//        for (Order order : res) {
//            OrderService.changeSum(order);
//        }
//        //res.add(user.getMyOrder());
//        System.out.println(res);
//
//       return new ResponseEntity<>(Ajax.successResponse(res), HttpStatus.OK);
//    }

    @RequestMapping("/orders")
    public String orders(Model model) {
        return "orders";
    }


    @RequestMapping(value = "/order/{idOr}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientById(@PathVariable("idOr") long id){
        Order order = orderService.getOrderById(id);
        if(order == null) {
            System.out.println("Нема!!!");
            Ajax.errorResponse("Нема");
        }
        return new ResponseEntity<Object>(order, HttpStatus.OK);
    }

}
