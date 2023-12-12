package com.rabin.onlinebookstore.controller;

import com.rabin.onlinebookstore.model.Order;
import com.rabin.onlinebookstore.model.Users;
import com.rabin.onlinebookstore.service.OrderService;
import com.rabin.onlinebookstore.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/user-orders")
    public ResponseWrapper getAllOrdersByUserId(HttpServletRequest request){
        int userId = (int) request.getAttribute("userId");
        List<Order> orderList = orderService.getAllOrdersByUserId(userId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(200);
        response.setMessage("Order retrieved successfully");
        response.setSuccess(true);
        response.setResponse(orderList);
        return response;
    }
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping()
    public ResponseWrapper placeOrder(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        try {
            return orderService.placeOrder(userId);
        } catch (Exception exception) {
            return new ResponseWrapper(false,500,  "Error placing orders ", null);
        }
    }
    @GetMapping()
    public ResponseWrapper getOrdersByUser(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        List<Order> orderList = orderService.getOrdersByUser(userId);
        return new ResponseWrapper(true, 200, "Orders fetched",orderList );
    }
}
