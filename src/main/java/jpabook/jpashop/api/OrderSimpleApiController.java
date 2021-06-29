package jpabook.jpashop.api;

import io.swagger.annotations.ApiOperation;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /**
     * V1. 엔터티 직접 노출
     * - Hibernate5Module 모듈 등록, Lazy=null 처리
     * - 양방향 관계 문제 발생(무한 루프 걸림) -> @JsonIgnore
     * @return
     */
    @ApiOperation(value = "상품 주문", notes = "version 1 \n상품을 주문한다.")
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
       List<Order> all = orderRepository.findAllByString(new OrderSearch());
       for(Order order : all){
           order.getMember().getName(); //Lazy 강제 초기화
           order.getDelivery().getAddress(); //Lazy 강제 초기화
       }
       return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        //ORDER 2개
        //N + 1 -> 1 + 회원 N + 배송 N
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

       return orders.stream().map(SimpleOrderDto::new).collect(toList());

    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName(); //LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }
}
