package com.adoption.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.adoption.dto.GetUsersByGoogleAccountRespDTO;
import com.adoption.dto.GetUsersByUserIdRespDTO;
import com.adoption.dto.GetUsersRespDTO;
import com.adoption.dto.PutPostStatusReqDTO;
import com.adoption.dto.PutPostStatusRespDTO;
import com.adoption.dto.PutUsersStatusReqDTO;
import com.adoption.dto.PutUsersStatusRespDTO;
import com.adoption.dto.User;
import com.adoption.dto.Users;
import com.adoption.entity.UserEntity;
import com.adoption.enums.ReturnCodeEnum;
import com.adoption.exception.DataNotFoundException;
import com.adoption.exception.DatabaseInteractFailedException;
import com.adoption.repository.AnimalRepository;
import com.adoption.repository.UserRepository;
import com.adoption.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AnimalRepository animalRepository;

  @Override
  public GetUsersRespDTO queryAllUsers() {
    List<UserEntity> userList = userRepository.findAllUsers();

    // 確認是否有存在資料
    if (userList.isEmpty()) {
      System.err.println("查無資料");
    }
    // 裝入dto當中
    List<Users> users = new ArrayList<>();
    for (UserEntity user : userList) {
      Users dtoUser = new Users();
      dtoUser.setUserId(user.getUserId());
      dtoUser.setName(user.getName());
      dtoUser.setMobile(user.getMobile());
      dtoUser.setGoogleAccount(user.getGoogleAccount());
      dtoUser.setStaus(user.getStatus());
      users.add(dtoUser);
    }
    GetUsersRespDTO response = new GetUsersRespDTO();
    response.setUsers(users);
    return response;
  }

  @Override
  public GetUsersByUserIdRespDTO queryUser(Long userId) throws DataNotFoundException {
    List<UserEntity> userBox = userRepository.findUserByUserId(userId);

    // 避免無搜尋到資料，將user以List的方式進行包裝
    if (userBox.isEmpty()) {
      throw new DataNotFoundException();
    }
    // 得第0項資料為搜尋資料
    UserEntity user = userBox.get(0);
    // 放入dto當中
    User dtoUser = new User();
    dtoUser.setUserId(user.getUserId());
    dtoUser.setName(user.getName());
    dtoUser.setMobile(user.getMobile());
    dtoUser.setGoogleAccount(user.getGoogleAccount());
    dtoUser.setStaus(user.getStatus());
    GetUsersByUserIdRespDTO response = new GetUsersByUserIdRespDTO();
    response.setUsers(dtoUser);
    return response;
  }


  @Override
  public GetUsersByGoogleAccountRespDTO queryUserByMail(String googleAccount)
      throws DataNotFoundException {
    List<UserEntity> userList = userRepository.findUserByGoogleAccount(googleAccount);
    
    // 避免無搜尋到資料，將user以List的方式進行包裝
    if (userList.isEmpty()) {
      throw new DataNotFoundException();
    }
    List<Users> users = new ArrayList<>();
    for (UserEntity user : userList) {
      Users dtoUser = new Users();
      dtoUser.setUserId(user.getUserId());
      dtoUser.setName(user.getName());
      dtoUser.setMobile(user.getMobile());
      dtoUser.setGoogleAccount(user.getGoogleAccount());
      dtoUser.setStaus(user.getStatus());
      users.add(dtoUser);
    }
    GetUsersByGoogleAccountRespDTO response = new GetUsersByGoogleAccountRespDTO();
    response.setUsers(users);
    return response;
  }


  @Override
  public PutUsersStatusRespDTO modifyUserStatus(Long userId, PutUsersStatusReqDTO request)
      throws DatabaseInteractFailedException {

    String status = request.getStatus();

    // 將使用者狀態寫入table內，若錯誤則拋出Exception
    try {
      userRepository.modifyUserStatus(userId, status);
    } catch (Exception e) {
      throw new DatabaseInteractFailedException();
    }
    PutUsersStatusRespDTO response = new PutUsersStatusRespDTO();
    response.setStatus(ReturnCodeEnum.SUCCESS.getCode());
    response.setStatusCode(ReturnCodeEnum.SUCCESS.getDesc());
    return response;
  }


  @Override
  public PutPostStatusRespDTO modifyPostStatus(PutPostStatusReqDTO request)
      throws DatabaseInteractFailedException {
    String status = request.getStatus();
    Long id = request.getId();
    
    // 將使用者狀態寫入table內，若錯誤則拋出Exception
    try {
      animalRepository.modifyPostStatus(id, status);
    } catch (Exception e) {
      throw new DatabaseInteractFailedException();
    }
    PutPostStatusRespDTO response = new PutPostStatusRespDTO();
    response.setStatusCode(ReturnCodeEnum.SUCCESS.getCode());
    response.setStatus(ReturnCodeEnum.SUCCESS.getDesc());
    return response;
  }

}
