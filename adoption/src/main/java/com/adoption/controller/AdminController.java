package com.adoption.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.adoption.dto.GetUsersByGoogleAccountRespDTO;
import com.adoption.dto.GetUsersByUserIdRespDTO;
import com.adoption.dto.GetUsersRespDTO;
import com.adoption.dto.PutPostStatusReqDTO;
import com.adoption.dto.PutPostStatusRespDTO;
import com.adoption.dto.PutUsersStatusReqDTO;
import com.adoption.dto.PutUsersStatusRespDTO;
import com.adoption.exception.DataNotFoundException;
import com.adoption.exception.DatabaseInteractFailedException;
import com.adoption.service.AdminService;


@RestController
@CrossOrigin("http://localhost:4200")
public class AdminController {

  @Autowired
  AdminService service;

  @Secured("ROLE_ADMIN")
  @GetMapping(value = "/users")
  public GetUsersRespDTO queryAllUsers() {
    return service.queryAllUsers();
  }
  
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @GetMapping(value = "/users", params = "userId")
  public GetUsersByUserIdRespDTO queryUser(@RequestParam(name = "userId") Long request)
      throws DataNotFoundException {
    return service.queryUser(request);
  }
  
  @Secured("ROLE_ADMIN")
  @GetMapping(value = "/users", params = "googleAccount")
  public GetUsersByGoogleAccountRespDTO queryUserByMail(String googleAccount) throws DataNotFoundException{
    return service.queryUserByMail(googleAccount);
  }
  
  @Secured("ROLE_ADMIN")
  @PutMapping(value = "/users/status", params = "userId")
  public PutUsersStatusRespDTO modifyUserStatus(@RequestParam(name = "userId") Long userId,
      @RequestBody PutUsersStatusReqDTO request) throws DatabaseInteractFailedException {
    return service.modifyUserStatus(userId, request);
  }

  // 後來新加的，找不到ID要丟東西
  @Secured("ROLE_ADMIN")
  @PutMapping(value = "/adoptions/status")
  public PutPostStatusRespDTO modifyPostStatus(@RequestBody PutPostStatusReqDTO request)
      throws DatabaseInteractFailedException {
    return service.modifyPostStatus(request);
  }

}
