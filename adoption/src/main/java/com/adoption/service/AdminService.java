package com.adoption.service;

import com.adoption.dto.GetUsersByGoogleAccountRespDTO;
import com.adoption.dto.GetUsersByUserIdRespDTO;
import com.adoption.dto.GetUsersRespDTO;
import com.adoption.dto.PutPostStatusReqDTO;
import com.adoption.dto.PutPostStatusRespDTO;
import com.adoption.dto.PutUsersStatusReqDTO;
import com.adoption.dto.PutUsersStatusRespDTO;
import com.adoption.exception.DataNotFoundException;
import com.adoption.exception.DatabaseInteractFailedException;

public interface AdminService {

  public GetUsersRespDTO queryAllUsers();

  public GetUsersByUserIdRespDTO queryUser(Long userId) throws DataNotFoundException;

  public GetUsersByGoogleAccountRespDTO queryUserByMail(String googleAccount) throws DataNotFoundException;
  
  public PutUsersStatusRespDTO modifyUserStatus(Long userId, PutUsersStatusReqDTO request)
      throws DatabaseInteractFailedException;

  public PutPostStatusRespDTO modifyPostStatus(PutPostStatusReqDTO request)
      throws DatabaseInteractFailedException;


}
