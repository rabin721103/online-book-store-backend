package com.rabin.onlinebookstore.service;

import com.rabin.onlinebookstore.model.Cart;
import com.rabin.onlinebookstore.model.Order;
import com.rabin.onlinebookstore.repository.CartRepository;
import com.rabin.onlinebookstore.repository.OrderRepository;
import com.rabin.onlinebookstore.utils.CustomException;
import com.rabin.onlinebookstore.utils.ResponseWrapper;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }
    public List<Order> getOrdersByUser(long userId) {
        return orderRepository.getOrdersByUser(userId);
    }
    public List<Order> getAllOrders(String sortBy, int orderBy) {
        try {
            if (orderBy == 1) {
                return orderRepository.findAll(Sort.by(sortBy).ascending());
            } else {
                return orderRepository.findAll(Sort.by(sortBy).descending());
            }
        } catch (Exception exception) {
            throw new CustomException("Error fetching order");
        }
    }

    @Transactional
    public ResponseWrapper placeOrder(long userId) {
        List<Cart> cartList = cartRepository.findAllBooksByUser(userId);

        if (cartList == null) {
            return new ResponseWrapper(false,400 , "No item to place order",null);
        } else {
            List<Order> orderList = new ArrayList<>();
            for (Cart cart : cartList) {
                Order order = new Order(cart);
                orderList.add(order);
            }
            try {
                orderRepository.saveAll(orderList);
                cartRepository.deleteAll(cartList);
                return new ResponseWrapper(true, 200, "Order Placed successfully", null);
            } catch (DataAccessException exception) {
                return new ResponseWrapper(false,500,  "Error placing order", null);
            }
        }
    }
    public ResponseWrapper updateOrderStatus(long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return new ResponseWrapper(false, 400,"Can not find order at the moment", null);
        } else {
            order.setStatus(status);
            order.setUpdatedOn(LocalDateTime.now());
            orderRepository.save(order);
            return new ResponseWrapper(true,200, "Order updated successfully", null);
        }
    }


}
