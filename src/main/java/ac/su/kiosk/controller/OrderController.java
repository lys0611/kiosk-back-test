package ac.su.kiosk.controller;

import ac.su.kiosk.constant.PaymentStatus;
import ac.su.kiosk.domain.Customer;
import ac.su.kiosk.domain.Kiosk;
import ac.su.kiosk.domain.Order;
import ac.su.kiosk.domain.OrderModuleDTO;
import ac.su.kiosk.dto.IAMPortDTO;
import ac.su.kiosk.dto.OrderDTO;
import ac.su.kiosk.repository.CustomerRepository;
import ac.su.kiosk.repository.KioskRepository;
import ac.su.kiosk.repository.OrderModuleDTORepository;
import ac.su.kiosk.repository.OrderRepository;
import ac.su.kiosk.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderModuleDTORepository orderModuleDTORepository;
    private final CustomerRepository customerRepository;
    private final KioskRepository kioskRepository;
    private final OrderRepository orderRepository;




    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        Order savedOrder = new Order();
        Optional<Customer> customerOptional = customerRepository.findById(orderDTO.getCustomerId());
        if(customerOptional.isPresent()) {
            savedOrder.setCustomer(customerOptional.get());
        }
        Optional<Kiosk> kioskOptional = kioskRepository.findById(orderDTO.getKioskId());
        if(kioskOptional.isPresent()) {
            savedOrder.setKiosk(kioskOptional.get());
        }
        savedOrder.setDateTime(orderDTO.getDatetime());
        savedOrder.setTotalPrice(orderDTO.getTotalPrice());
        savedOrder.setPackaged(orderDTO.isPackaged());
        savedOrder.setPaymentUid(orderDTO.getPaymentUid());
        Optional<OrderModuleDTO> orderModuleDTOOptional = orderModuleDTORepository.findByPaymentUid(orderDTO.getPaymentUid());
        if(orderModuleDTOOptional.isPresent()) {
            savedOrder.setOrderModuleDTO(orderModuleDTOOptional.get());
        }
        orderRepository.save(savedOrder);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @PostMapping("/iamPortDto")
    public void createIamPortDto(@RequestBody IAMPortDTO iamPortDTO) {
        OrderModuleDTO orderModuleDTO = orderModuleDTORepository.findByOrderUid(iamPortDTO.getOrderUid()).get();
        orderModuleDTO.setPaymentUid(iamPortDTO.getPaymentUid());
        orderModuleDTO.setPrice(iamPortDTO.getPrice());
        orderModuleDTO.setStatus(PaymentStatus.OK);
        orderModuleDTORepository.save(orderModuleDTO);
    }

}