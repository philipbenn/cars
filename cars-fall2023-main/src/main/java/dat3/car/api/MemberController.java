package dat3.car.api;

import dat3.car.dto.MemberRequest;
import dat3.car.dto.MemberResponse;
import dat3.car.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/members")
class MemberController {

  MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  //Security Admin Only
  @GetMapping
  List<MemberResponse> getMembers() {
    return memberService.getMembersV2(false, true);
  }


  //Security ADMIN
  @GetMapping(path = "/{username}")
  MemberResponse getMemberById(@PathVariable String username) throws Exception {
    return memberService.findById(username);
  }

  //Security --> Anonymous
  @PostMapping()
  MemberResponse addMember(@RequestBody MemberRequest body) {
    return memberService.addMember(body);
  }

  //Security Admin
  @PutMapping("/{username}")
  void editMember(@RequestBody MemberRequest body, @PathVariable String username) {
    memberService.editMember(body, username);
  }

  //Security CURRENT USER
  @PutMapping
  public ResponseEntity<Boolean> editLoggedINMember(@RequestBody MemberRequest body, Principal principal) {
    String username = principal.getName();
    memberService.editMember(body, username);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  //Security ADMIN
  @PatchMapping("/ranking/{username}/{value}")
  void setRankingForUser(@PathVariable String username, @PathVariable int value) {
    memberService.setRankingForUser(username, value);
  }

  // Security ADMIN
  @DeleteMapping("/{username}")
  void deleteMemberByUsername(@PathVariable String username) {
    memberService.deleteMemberByUsername(username);
  }


}

