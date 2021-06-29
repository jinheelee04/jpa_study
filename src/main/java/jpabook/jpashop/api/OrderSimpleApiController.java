package jpabook.jpashop.api;

import io.swagger.annotations.ApiOperation;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository
            ;
    @ApiOperation(value = "상품 주문", notes = "version 1 \n상품을 주문한다.")
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
       List<Order> all = orderRepository.findAllByString(new OrderSearch());
       return all;
    }
}
