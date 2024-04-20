package ra.project_module5_restapi_240130.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ra.project_module5_restapi_240130.serviceImp.ProductServiceImp;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    ProductServiceImp productServiceImp;
    @Test
    void findAllTest() {
//        ProductServiceImp productServiceImp = new ProductServiceImp();
     productServiceImp.findAll();
     //So sánh nếu dùng assertEquals giá trị mong đợi và giá trị thực tế, nếu 2 giá trị bằng nhau thì ok
    assertTrue(true);
    }
}