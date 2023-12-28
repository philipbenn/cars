package dat3.car.repository;

import dat3.car.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

  boolean existsByCar_IdAndRentalDate(int id, LocalDate data);
}
