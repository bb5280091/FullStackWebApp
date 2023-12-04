package com.adoption.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.adoption.dto.DeleteAdoptionsRespDTO;
import com.adoption.dto.GetAnimalByAnimalIdRespDTO;
import com.adoption.dto.GetAnimalsByUserIdRespDTO;
import com.adoption.dto.GetAnimalsRespDTO;
import com.adoption.dto.GetCommentCityRespDTO;
import com.adoption.dto.GetCommentSpeciesRespDTO;
import com.adoption.dto.GetDiscussionRespDTO;
import com.adoption.dto.GetFilteredAnimalsRespDTO;
import com.adoption.dto.GetMessageRespDTO;
import com.adoption.dto.GetUserSubscriptionRespDTO;
import com.adoption.dto.NewAnimalReqDTO;
import com.adoption.dto.NewAnimalRespDTO;
import com.adoption.dto.PutUserSubscriptionReqDTO;
import com.adoption.dto.PutUserSubscriptionRespDTO;
import com.adoption.dto.PutUserUnsubscriptionReqDTO;
import com.adoption.dto.PutUserUnsubscriptionRespDTO;
import com.adoption.dto.UpdateAnimalReqDTO;
import com.adoption.dto.UpdateAnimalRespDTO;
import com.adoption.dto.UpdateUserReqDTO;
import com.adoption.dto.UpdateUserRespDTO;
import com.adoption.exception.DataNotFoundException;
import com.adoption.exception.DatabaseInteractFailedException;
import com.adoption.exception.UpdateFailedException;
import com.adoption.service.MessageService;
import com.adoption.service.UserService;

@RestController
@CrossOrigin("http://localhost:4200")
public class UserController {

  @Autowired
  UserService service;

  @Autowired
  MessageService messageService;


  @Secured("ROLE_USER")
  @DeleteMapping(value = "/adoptions", params = "id")
  public DeleteAdoptionsRespDTO deleteAdoption(@RequestParam(name = "id") String id)
      throws DatabaseInteractFailedException {
    return service.deleteAdoption(id);
  }


  // 其實跟下面那隻一樣，只是代入條件cityId, sex, speciesId皆為空
  @GetMapping(value = "/adoptions", params = "page")
  public GetAnimalsRespDTO queryAllAnimals(@RequestParam int page ) {
    return service.queryAllAnimals(page);
  }


  @GetMapping(value = "/adoptions", params = {"cityId", "sex", "speciesId","page"})
  public GetFilteredAnimalsRespDTO queryFilteredAnimals(@RequestParam String cityId,
      @RequestParam String sex, @RequestParam String speciesId,@RequestParam int page) {
    return service.queryFilteredAnimals(cityId, sex, speciesId, page);
  }


  @GetMapping(value = "/adoptions", params = "animalId")
  public GetAnimalByAnimalIdRespDTO queryAnimalByAnimalId(@RequestParam Long animalId)
      throws DataNotFoundException {
    return service.queryAnimalByAnimalId(animalId);
  }


  @GetMapping(value = "/adoptions", params = "userId")
  public GetAnimalsByUserIdRespDTO queryAnimalByUserId(@RequestParam Long userId)
      throws DataNotFoundException {
    return service.queryAnimalByUserId(userId);
  }


  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @PostMapping(value = "/adoptions")
  public NewAnimalRespDTO createAnimalInfo(@RequestBody NewAnimalReqDTO request) {
    return service.createAnimalInfo(request);
  }


  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @PutMapping(value = "/adoptions")
  public UpdateAnimalRespDTO updateAnimalByUserId(@RequestBody UpdateAnimalReqDTO request)
      throws DataNotFoundException {
    return service.updateAnimalByUserId(request);
  }


  // 無此user要丟
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @PutMapping(value = "/users")
  public UpdateUserRespDTO updateUserByUserId(@RequestBody UpdateUserReqDTO request) {
    return service.updateUserByUserId(request);
  }


  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @GetMapping(value = "/users/subscription", params = "userId")
  public GetUserSubscriptionRespDTO queryUserSubscriptions(@RequestParam Long userId)
      throws DataNotFoundException {
    return service.queryUserSubscriptions(userId);
  }


  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @PutMapping(value = "/users/subscription")
  public PutUserSubscriptionRespDTO subscriptionAnimal(
      @RequestBody PutUserSubscriptionReqDTO request)
      throws DatabaseInteractFailedException, UpdateFailedException {
    return service.subscriptionAnimal(request);
  }


  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @PutMapping(value = "/users/unsubscription")
  public PutUserUnsubscriptionRespDTO unsubscriptionAnimal(
      @RequestBody PutUserUnsubscriptionReqDTO request) throws DatabaseInteractFailedException {
    return service.unsubscriptionAnimal(request);
  }


  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @GetMapping(value = "/messages", params = "userId")
  public GetMessageRespDTO queryAllMessage(@RequestParam Long userId) {
    return messageService.queryAllMessage(userId);
  }


  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @GetMapping(value = "/messages", params = {"userId", "otherId"})
  public GetMessageRespDTO queryChatroomMessage(@RequestParam Long userId,
      @RequestParam Long otherId) {
    return messageService.queryChatroomMessage(userId, otherId);
  }

  
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @GetMapping(value = "/discussion", params = "animalId")
  public GetDiscussionRespDTO queryDiscussionByAnimalId(@RequestParam Long animalId) {
    return messageService.queryDiscussionByAnimalId(animalId);
  }


  @GetMapping(value = "/comment/city")
  public GetCommentCityRespDTO queryAllCity() {
    return service.queryAllCity();
  }


  @GetMapping(value = "/comment/species")
  public GetCommentSpeciesRespDTO queryAllSpecies() {
    return service.queryAllSpecies();
  }

}
