package com.rabin.onlinebookstore.controller;

import com.rabin.onlinebookstore.service.OrderService;
import com.rabin.onlinebookstore.utils.ResponseWrapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
   private final OrderService orderService;


    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping()
    public ResponseWrapper getAllOrders(@RequestParam(name = "sort", defaultValue = "date") String sort,
                                        @RequestParam(name = "order", defaultValue = "1") int order) {
        return new ResponseWrapper(true,200,"All orders fetched", orderService.getAllOrders(sort, order));
    }

    @PutMapping("/{orderId}")
    public ResponseWrapper updateOrderStatus(@PathVariable long orderId, @RequestBody String status) {
        String statusStr = status.replaceAll("\"", "");
        return orderService.updateOrderStatus(orderId, statusStr);
    }
}
