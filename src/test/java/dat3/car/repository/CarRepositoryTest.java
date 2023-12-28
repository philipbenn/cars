package dat3.car.repository;

import dat3.car.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;
    boolean dataInitialized = false;
    int car1Id, car2Id;
    @BeforeEach
    void setUp() {
        if(!dataInitialized){
            Car car1 = carRepository.save(new Car("Volvo","V70",1999,20));
            car1Id = car1.getId();
            Car car2 = carRepository.save(new Car("XXX","YYY",100,20));
            car2Id = car2.getId();
        }
    }

    @Test
    public void testById() {
        Car car1 = carRepository.findById(car1Id).get();
        assertEquals("Volvo", car1.getBrand());
    }
    @Test
    public void testCount() {
        assertEquals(2, carRepository.count());
    }
    @Test
    public void testGetByBrand() {
        assertEquals(1, carRepository.getByBrand("Volvo").size());
    }
}