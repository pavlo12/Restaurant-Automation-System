package com.mainacad.myproject.controler;


import com.mainacad.myproject.entities.Order;
import com.mainacad.myproject.entities.User;
import com.mainacad.myproject.services.OrderService;
import com.mainacad.myproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String orders(Model model) {
        return "orders";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> allOrders(Model model, UriComponentsBuilder ucBuilder) {

        User user = userService.getActiveUser();
        if (user == null) {
            return new ResponseEntity<>(Ajax.errorResponse("Ви не авторизовані!"), HttpStatus.OK);
        }
        List<Order> res = orderService.orderList(user);

        for (Order order : res) {
            OrderService.changeSum(order);
        }
        //res.add(user.getMyOrder());
        System.out.println(res);

        return new ResponseEntity<>(Ajax.successResponse(res), HttpStatus.OK);
    }

    @RequestMapping(value = "/new-order", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getOrder() {

        User user = userService.getActiveUser();

        if (user == null) {
            Ajax.errorResponse("Ви не авторизовані!");
        }

        if(user.getMyOrder() == null) {
            return Ajax.errorResponse("немає");
        }
        return Ajax.successResponse(user.getMyOrder());
    }

    @RequestMapping(value = "/create-new-order", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> orderNewAdd(Model model) {

        User user = userService.getActiveUser();

        if (user == null) {
            Ajax.errorResponse("Ви не авторизовані!");
        }
        user.setMyOrder(new Order());
        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "/add-order", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> orderAdd(Model model) {

        User user = userService.getActiveUser();

        if (user == null) {
            Ajax.errorResponse("Ви не авторизовані!");
        }

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

    @RequestMapping(value = "/set-count-person", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> setCoutPerson(@RequestParam("countPerson") int countPerson,
                                      Model model) {

        //int c = Integer.getInteger(countPerson);
        userService.getActiveUser().getMyOrder().setCountPerson(countPerson);

        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "/set-order-date", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> setOrderDate(@RequestParam("startDate") String startDate,
                                     @RequestParam("endDate") String endDate,
                                     Model model) {

        orderService.setDateForOrder(startDate, endDate);

        System.out.println(userService.getActiveUser().getMyOrder().getDateTimeFrom());
        System.out.println(userService.getActiveUser().getMyOrder().getDateTimeBefore());

        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "/new-order/{id-dish}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePatientById(@PathVariable("id-dish") long dishId){

        orderService.deleteOrderedDish(dishId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/new-order/{id-dish}", method = RequestMethod.PUT, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> updatePatient(@RequestParam("newCount") int newCount, @PathVariable("id-dish") long dishId){


        orderService.changeCountOrderedDish(dishId, newCount);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{idOr}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getPatientById(@PathVariable("idOr") long id){
        Order order = orderService.getOrderById(id);
        if(order == null) {
            System.out.println("Нема!!!");
            return Ajax.errorResponse("Нема");
        }
        return Ajax.successResponse(order);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Map<String, Object> deleteOrderById(@PathVariable("id") long id){
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return Ajax.errorResponse("Order with id = " + id + "does not exist");
        }
        orderService.deleteOrder(id);
        return Ajax.emptyResponse();
    }

}

