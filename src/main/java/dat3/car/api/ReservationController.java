package dat3.car.api;

import dat3.car.dto.ReservationRequest;
import dat3.car.dto.ReservationResponse;
import dat3.car.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
  ReservationService service;

  public ReservationController(ReservationService service) {
    this.service = service;
  }

  @PostMapping
  ReservationResponse makeReservation(@RequestBody ReservationRequest res){
    ReservationResponse r = service.reserveCar(res);
    return r;
  }

  //ADMIN
  @GetMapping
  public List<ReservationResponse> getReservations(){
    List<ReservationResponse> res = service.getReservations();
    return res;
  }

  //USER (the current USER)
  @GetMapping("/reservations-for-authenticated")
  public List<ReservationResponse> getReservationsForUser(Principal principal){
    List<ReservationResponse> res = service.getReservationsForUser(principal.getName());
    return res;
  }

  //ADMIN
  @GetMapping("/{userName}")
  public List<ReservationResponse> getReservationsForUser(@PathVariable String userName){
    List<ReservationResponse> res = service.getReservationsForUser(userName);
    return res;
  }
}
