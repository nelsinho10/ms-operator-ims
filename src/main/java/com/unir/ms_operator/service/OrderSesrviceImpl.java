package com.unir.ms_operator.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.unir.ms_operator.data.OrderProductRepository;
import com.unir.ms_operator.data.OrderRepository;
import com.unir.ms_operator.model.pojo.Customer;
import com.unir.ms_operator.model.pojo.Order;
import com.unir.ms_operator.model.pojo.OrderProduct;
import com.unir.ms_operator.model.pojo.OrderProductId;
import com.unir.ms_operator.model.pojo.Product;
import com.unir.ms_operator.model.pojo.StockProduct;
import com.unir.ms_operator.model.request.CreateOrderRequest;

@Service
public class OrderSesrviceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Override
    public List<Order> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.isEmpty() ? null : orders;
    }

    @Override
    public Order createOrder(CreateOrderRequest request) {
        if (request != null) {
            Customer customer = Customer.builder().id(request.getCustomerId()).build();
            Order order = Order.builder().customer(customer).status(true).type(request.getType()).build();
            Order orderCreated = orderRepository.save(order);
            List<StockProduct> bodyStock = new ArrayList<>();

            System.out.println("Products in request: " + Arrays.toString(request.getProducts()));

            // Save products in order
            Arrays.stream(request.getProducts()).forEach(product -> {
                System.out.println("Processing product code: " + product.getProductId());
                OrderProductId orderProductId = new OrderProductId(orderCreated.getId(), product.getProductId());
                OrderProduct orderProduct = OrderProduct.builder().id(orderProductId).build();
                orderProductRepository.save(orderProduct);
                System.out.println("Saved product code: " + product.getProductId());

                // Update stock
                StockProduct stockProduct = new StockProduct(product.getProductId(), product.getQuantity());
                bodyStock.add(stockProduct);
                System.out.println("Updated stock for product code: " + product.getProductId());
            });

            // Update stock in elastic
            String baseUrl = environment.getProperty("MS_SEARCH_URL");
            // String url = "http://localhost:8082/elastic/products/stock";
            String url = baseUrl + "/elastic/products/stock";
            restTemplate.postForObject(url, bodyStock, Product[].class);

            // Print products in order
            List<OrderProduct> orderProducts = orderProductRepository.findAll();
            System.out.println("Products in order: " + orderProducts.size());
            return orderCreated;

        } else {
            return null;
        }
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrder'");
    }

    @Override
    public Boolean deleteOrder(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOrder'");
    }

    @Override
    public Boolean changeStatus(Long id, Boolean status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeStatus'");
    }

    @Override
    public List<Product> getProductsByOrderId(Long id) {

        if (id != null) {
            List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(id);
            List<String> productsCode = orderProducts.isEmpty() ? null
                    : orderProducts.stream().map(op -> op.getId().getProductCode())
                            .toList();
            String baseUrl = environment.getProperty("MS_SEARCH_URL");
            List<Product> products = productsCode.stream().map(productCode -> {
                // String url = "http://localhost:8082/elastic/products/" + productCode + "/details";
                String url = baseUrl + "/elastic/products/" + productCode + "/details";
                return restTemplate.getForObject(url, Product.class);
            }).toList();

            return products;
        }
        return null;
    }

    @Override
    public Order getOrder(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrder'");
    }

}
