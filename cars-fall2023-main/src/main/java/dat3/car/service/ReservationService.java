package dat3.car.service;

import dat3.car.dto.ReservationRequest;
import dat3.car.dto.ReservationResponse;
import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.entity.Reservation;
import dat3.car.repository.CarRepository;
import dat3.car.repository.MemberRepository;
import dat3.car.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

  CarRepository carRepository;
  MemberRepository memberRepository;
  ReservationRepository reservationRepository;
  MemberService memberService;

  public ReservationService(CarRepository carRepository, MemberRepository memberRepository, ReservationRepository reservationRepository) {
    this.carRepository = carRepository;
    this.memberRepository = memberRepository;
    this.reservationRepository = reservationRepository;
  }

  public ReservationResponse reserveCar(ReservationRequest body){
    if(body.getDate().isBefore(LocalDate.now())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Date in past not allowed");
    }
    Member member = memberRepository.findById(body.getUserName()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No member with this id found"));
    Car car = carRepository.findById(body.getCarId()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Car with this id found"));
    if(reservationRepository.existsByCar_IdAndRentalDate(car.getId(),body.getDate())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Car is reserved for this date");
    }
    Reservation res = reservationRepository.save(new Reservation(body.getDate(),car,member));
    return  new ReservationResponse(res);
  }

  public List<ReservationResponse> getReservations(){
    List<Reservation> reservations = reservationRepository.findAll();
    List<ReservationResponse> response = reservations.stream().map(res-> new ReservationResponse(res)).collect(Collectors.toList());
    return response;
  }
  public List<ReservationResponse> getReservationsForUser(String username) {
    Member member = memberRepository.findById(username).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found"));
    List<ReservationResponse> reservations = member.getReservations().stream().map(r->new ReservationResponse(r)).toList();
    return reservations;
  }
}
