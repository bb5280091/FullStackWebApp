package com.adoption.service;

import com.adoption.dto.DeleteAdoptionsRespDTO;
import com.adoption.dto.GetAnimalByAnimalIdRespDTO;
import com.adoption.dto.GetAnimalsByUserIdRespDTO;
import com.adoption.dto.GetAnimalsRespDTO;
import com.adoption.dto.GetCommentCityRespDTO;
import com.adoption.dto.GetCommentSpeciesRespDTO;
import com.adoption.dto.GetFilteredAnimalsRespDTO;
import com.adoption.dto.GetUserSubscriptionRespDTO;
import com.adoption.dto.NewAnimalReqDTO;
import com.adoption.dto.NewAnimalRespDTO;
import com.adoption.dto.NewUserReqDTO;
import com.adoption.dto.NewUserRespDTO;
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


public interface UserService {

  DeleteAdoptionsRespDTO deleteAdoption(String id) throws DatabaseInteractFailedException;

  GetAnimalsRespDTO queryAllAnimals(int page);

  GetFilteredAnimalsRespDTO queryFilteredAnimals(String cityId, String sex, String speciesId, int page);

  GetAnimalByAnimalIdRespDTO queryAnimalByAnimalId(Long animalId) throws DataNotFoundException;

  GetAnimalsByUserIdRespDTO queryAnimalByUserId(Long userId) throws DataNotFoundException;

  NewAnimalRespDTO createAnimalInfo(NewAnimalReqDTO request);

  UpdateAnimalRespDTO updateAnimalByUserId(UpdateAnimalReqDTO request) throws DataNotFoundException;

  NewUserRespDTO createUserInfo(NewUserReqDTO request);

  UpdateUserRespDTO updateUserByUserId(UpdateUserReqDTO request);

  GetUserSubscriptionRespDTO queryUserSubscriptions(Long userId) throws DataNotFoundException;

  PutUserSubscriptionRespDTO subscriptionAnimal(PutUserSubscriptionReqDTO request)
      throws DatabaseInteractFailedException, UpdateFailedException;

  PutUserUnsubscriptionRespDTO unsubscriptionAnimal(PutUserUnsubscriptionReqDTO request)
      throws DatabaseInteractFailedException;


  public GetCommentCityRespDTO queryAllCity();

  public GetCommentSpeciesRespDTO queryAllSpecies();
  
}
