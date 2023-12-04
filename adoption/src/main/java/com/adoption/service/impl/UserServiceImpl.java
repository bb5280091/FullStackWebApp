package com.adoption.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.adoption.dto.Animals;
import com.adoption.dto.CityMapping;
import com.adoption.dto.DeleteAdoptionsRespDTO;
import com.adoption.dto.GetAnimalByAnimalIdRespDTO;
import com.adoption.dto.GetAnimalsByUserIdRespDTO;
import com.adoption.dto.GetAnimalsRespDTO;
import com.adoption.dto.GetCommentCityRespDTO;
import com.adoption.dto.GetCommentSpeciesRespDTO;
import com.adoption.dto.GetFilteredAnimalsRespDTO;
import com.adoption.dto.GetUserSubscriptionRespDTO;
import com.adoption.dto.MinimalistAnimal;
import com.adoption.dto.NewAnimalReqDTO;
import com.adoption.dto.NewAnimalRespDTO;
import com.adoption.dto.NewUserReqDTO;
import com.adoption.dto.NewUserRespDTO;
import com.adoption.dto.PutUserSubscriptionReqDTO;
import com.adoption.dto.PutUserSubscriptionRespDTO;
import com.adoption.dto.PutUserUnsubscriptionReqDTO;
import com.adoption.dto.PutUserUnsubscriptionRespDTO;
import com.adoption.dto.SpeciesMapping;
import com.adoption.dto.UpdateAnimalReqDTO;
import com.adoption.dto.UpdateAnimalRespDTO;
import com.adoption.dto.UpdateUserReqDTO;
import com.adoption.dto.UpdateUserRespDTO;
import com.adoption.entity.AnimalEntity;
import com.adoption.entity.CityEntity;
import com.adoption.entity.SpeciesEntity;
import com.adoption.entity.UserEntity;
import com.adoption.enums.ReturnCodeEnum;
import com.adoption.exception.DataNotFoundException;
import com.adoption.exception.DatabaseInteractFailedException;
import com.adoption.exception.UpdateFailedException;
import com.adoption.repository.AnimalRepository;
import com.adoption.repository.CityRepository;
import com.adoption.repository.SpeciesRepository;
import com.adoption.repository.UserRepository;
import com.adoption.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private AnimalRepository animalRepository;
  @Autowired
  private CityRepository cityRepository;
  @Autowired
  private SpeciesRepository speciesRepository;
  @Autowired
  private UserRepository userRepository;

  @Override
  public DeleteAdoptionsRespDTO deleteAdoption(String id) throws DatabaseInteractFailedException {
    Long transId = Long.parseLong(id);

    try {
      animalRepository.deleteById(transId);
    } catch (Exception e) {
      throw new DatabaseInteractFailedException();
    }
    DeleteAdoptionsRespDTO response = new DeleteAdoptionsRespDTO();
    response.setStatusCode(ReturnCodeEnum.SUCCESS.getCode());
    response.setStatus(ReturnCodeEnum.SUCCESS.getDesc());
    return response;
  }

  @Override
  public GetAnimalsRespDTO queryAllAnimals(int page) {
    GetAnimalsRespDTO getAnimalsRespDTO = new GetAnimalsRespDTO();
    List<Animals> animalsList = new ArrayList<>();
    Page<AnimalEntity> entities =
        animalRepository.findAllOrderByPublishDateDesc(PageRequest.of(page, // 查詢的頁數，從0起算
            6, // 查詢的每頁筆數
            Sort.by("publishDate").descending())); // 依storeName欄位升冪排序

    for (AnimalEntity entity : entities) {
      Animals animal = new Animals();
      animal.setAge(entity.getAge());
      animal.setCity(entity.getCityId());
      animal.setColor(entity.getColor());
      animal.setConditionAffidavit(entity.getConditionAffidavit());
      animal.setConditionAgeLimit(entity.getConditionAgeLimit());
      animal.setConditionFollowUp(entity.getConditionFollowUp());
      animal.setConditionParentalPermission(entity.getConditionParentalPermission());
      animal.setId(entity.getId());
      animal.setIntroduction(entity.getIntroduction());
      animal.setLigation(entity.getLigation());
      animal.setName(entity.getName());
      animal.setPhoto(entity.getPhoto());
      animal.setSex(entity.getSex());
      animal.setSize(entity.getAnimalSize());
      animal.setSpecies(entity.getSpeciesId());
      animal.setType(entity.getType());
      if (entity.getPostStatus().equals("Y")) {
        animal.setPostStatus("已發布");
      } else {
        animal.setPostStatus("審核中");
      }
      animal.setPublishDate(entity.getPublishDate());
      animal.setUserId(entity.getUserId());
      animalsList.add(animal);
    }
    getAnimalsRespDTO.setAnimals(animalsList);
    getAnimalsRespDTO.setPageNumber(entities.getNumberOfElements());// 本頁筆數
    getAnimalsRespDTO.setPageSize(entities.getSize());// 每頁筆數
    getAnimalsRespDTO.setTotalCount(entities.getTotalElements());// 全部筆數
    getAnimalsRespDTO.setTotalPage(entities.getTotalPages()); // 全部頁數

    return getAnimalsRespDTO;
  }

  @Override
  public GetFilteredAnimalsRespDTO queryFilteredAnimals(String cityId, String sex, String speciesId,
      int page) {
    GetFilteredAnimalsRespDTO getFilteredAnimalsRespDTORespDTO = new GetFilteredAnimalsRespDTO();
    List<Animals> animalsList = new ArrayList<>();

    Page<AnimalEntity> entities = animalRepository.findByCityIdAndSexAndSpeciesId(cityId, sex,
        speciesId, PageRequest.of(page, 6, Sort.by("publishDate").descending()));

    for (AnimalEntity entity : entities) {
      Animals animal = new Animals();
      animal.setAge(entity.getAge());
      animal.setCity(entity.getCityId());
      animal.setColor(entity.getColor());
      animal.setConditionAffidavit(entity.getConditionAffidavit());
      animal.setConditionAgeLimit(entity.getConditionAgeLimit());
      animal.setConditionFollowUp(entity.getConditionFollowUp());
      animal.setConditionParentalPermission(entity.getConditionParentalPermission());
      animal.setId(entity.getId());
      animal.setIntroduction(entity.getIntroduction());
      animal.setLigation(entity.getLigation());
      animal.setName(entity.getName());
      animal.setPhoto(entity.getPhoto());
      animal.setSex(entity.getSex());
      animal.setSize(entity.getAnimalSize());
      animal.setSpecies(entity.getSpeciesId());
      animal.setType(entity.getType());
      animal.setUserId(entity.getUserId());
      animalsList.add(animal);
    }
    getFilteredAnimalsRespDTORespDTO.setAnimals(animalsList);
    getFilteredAnimalsRespDTORespDTO.setPageNumber(entities.getNumberOfElements());// 本頁筆數
    getFilteredAnimalsRespDTORespDTO.setPageSize(entities.getSize());// 每頁筆數
    getFilteredAnimalsRespDTORespDTO.setTotalCount(entities.getTotalElements());// 全部筆數
    getFilteredAnimalsRespDTORespDTO.setTotalPage(entities.getTotalPages()); // 全部頁數

    return getFilteredAnimalsRespDTORespDTO;
  }

  @Override
  public GetAnimalByAnimalIdRespDTO queryAnimalByAnimalId(Long animalId)
      throws DataNotFoundException {
    GetAnimalByAnimalIdRespDTO getAnimalByAnimalIdSRespDTO = new GetAnimalByAnimalIdRespDTO();
    List<Animals> animalsList = new ArrayList<>();
    List<AnimalEntity> entities = animalRepository.findById(animalId);
    if (entities.isEmpty()) {
      // 若找不到
      throw new DataNotFoundException();
    }
    Animals animal = new Animals();
    animal.setAge(entities.get(0).getAge());
    animal.setCity(entities.get(0).getCityId());// 改
    animal.setColor(entities.get(0).getColor());
    animal.setConditionAffidavit(entities.get(0).getConditionAffidavit());
    animal.setConditionAgeLimit(entities.get(0).getConditionAgeLimit());
    animal.setConditionFollowUp(entities.get(0).getConditionFollowUp());
    animal.setConditionParentalPermission(entities.get(0).getConditionParentalPermission());
    animal.setId(entities.get(0).getId());
    animal.setIntroduction(entities.get(0).getIntroduction());
    animal.setLigation(entities.get(0).getLigation());
    animal.setName(entities.get(0).getName());
    animal.setPhoto(entities.get(0).getPhoto());
    animal.setSex(entities.get(0).getSex());
    animal.setSize(entities.get(0).getAnimalSize());
    animal.setSpecies(entities.get(0).getSpeciesId());// 改
    animal.setType(entities.get(0).getType());
    animal.setUserId(entities.get(0).getUserId());
    animal.setPublishDate(entities.get(0).getPublishDate());// 後來加的
    // 後來修改
    if (entities.get(0).getPostStatus().equals("Y")) {
      animal.setPostStatus("已發布");
    } else {
      animal.setPostStatus("審核中");
    }
    animalsList.add(animal);
    getAnimalByAnimalIdSRespDTO.setAnimals(animalsList);

    return getAnimalByAnimalIdSRespDTO;
  }

  @Override
  public GetAnimalsByUserIdRespDTO queryAnimalByUserId(Long userId) throws DataNotFoundException {
    GetAnimalsByUserIdRespDTO getAnimalsByUserIdRespDTO = new GetAnimalsByUserIdRespDTO();
    List<Animals> animalsList = new ArrayList<>();
    List<AnimalEntity> entities = animalRepository.findByUserIdOrderByPublishDateDesc(userId);
    if (entities.isEmpty()) {
      // 若找不到
      throw new DataNotFoundException();
    }

    for (AnimalEntity entity : entities) {
      Animals animal = new Animals();
      animal.setAge(entity.getAge());
      animal.setCity(entity.getCityId());
      animal.setColor(entity.getColor());
      animal.setConditionAffidavit(entity.getConditionAffidavit());
      animal.setConditionAgeLimit(entity.getConditionAgeLimit());
      animal.setConditionFollowUp(entity.getConditionFollowUp());
      animal.setConditionParentalPermission(entity.getConditionParentalPermission());
      animal.setId(entity.getId());
      animal.setIntroduction(entity.getIntroduction());
      animal.setLigation(entity.getLigation());
      animal.setName(entity.getName());
      animal.setPhoto(entity.getPhoto());
      animal.setSex(entity.getSex());
      animal.setSize(entity.getAnimalSize());
      animal.setType(entity.getType());
      animal.setSpecies(entity.getSpeciesId());
      animal.setType(entity.getType());
      if (entity.getPostStatus().equals("Y")) {
        animal.setPostStatus("已發布");
      } else {
        animal.setPostStatus("審核中");
      }
      animal.setPublishDate(entity.getPublishDate());
      animal.setUserId(entity.getUserId());
      animalsList.add(animal);
    }
    getAnimalsByUserIdRespDTO.setAnimals(animalsList);
    return getAnimalsByUserIdRespDTO;
  }


  @Override
  public NewAnimalRespDTO createAnimalInfo(NewAnimalReqDTO request) {
    NewAnimalRespDTO newAnimalRespDTO = new NewAnimalRespDTO();
    AnimalEntity animalEntity = new AnimalEntity();
    animalEntity.setAge(request.getAnimal().getAge());
    animalEntity.setAnimalSize(request.getAnimal().getSize());
    animalEntity.setCityId(request.getAnimal().getCity());
    animalEntity.setColor(request.getAnimal().getColor());
    animalEntity.setConditionAffidavit(request.getAnimal().getConditionAffidavit());
    animalEntity.setConditionAgeLimit(request.getAnimal().getConditionAgeLimit());
    animalEntity.setConditionFollowUp(request.getAnimal().getConditionFollowUp());
    animalEntity
        .setConditionParentalPermission(request.getAnimal().getConditionParentalPermission());
    animalEntity.setCtr("0");// it will be 0 in the begin
    animalEntity.setIntroduction(request.getAnimal().getIntroduction());
    animalEntity.setLigation(request.getAnimal().getLigation());
    animalEntity.setName(request.getAnimal().getName());
    animalEntity.setPhoto(request.getAnimal().getPhoto());
    animalEntity.setPostStatus("N");// it will be N in the begin
    Date date = new Date();
    Timestamp ts = new Timestamp(date.getTime());
    System.out.println(ts);
    animalEntity.setPublishDate(ts);// fetch the timestamp
    animalEntity.setSex(request.getAnimal().getSex());
    animalEntity.setSpeciesId(request.getAnimal().getSpecies());
    animalEntity.setType(request.getAnimal().getType());
    animalEntity.setUserId(request.getAnimal().getUserId());
    animalRepository.save(animalEntity);

    newAnimalRespDTO.setStatusCode(ReturnCodeEnum.SUCCESS.getCode());
    newAnimalRespDTO.setStatus(ReturnCodeEnum.SUCCESS.getDesc());

    return newAnimalRespDTO;
  }

  @Override
  public UpdateAnimalRespDTO updateAnimalByUserId(UpdateAnimalReqDTO request)
      throws DataNotFoundException {

    UpdateAnimalRespDTO updateAnimalRespDTO = new UpdateAnimalRespDTO();
    List<AnimalEntity> animalEntity = animalRepository.findById(request.getAnimal().getId());
    if (animalEntity.isEmpty()) {
      // 若找不到
      throw new DataNotFoundException();
    }
    animalEntity.get(0).setAge(request.getAnimal().getAge());
    animalEntity.get(0).setAnimalSize(request.getAnimal().getSize());
    animalEntity.get(0).setCityId(request.getAnimal().getCity());
    animalEntity.get(0).setColor(request.getAnimal().getColor());
    animalEntity.get(0).setConditionAffidavit(request.getAnimal().getConditionAffidavit());
    animalEntity.get(0).setConditionAgeLimit(request.getAnimal().getConditionAgeLimit());
    animalEntity.get(0).setConditionFollowUp(request.getAnimal().getConditionFollowUp());
    animalEntity.get(0)
        .setConditionParentalPermission(request.getAnimal().getConditionParentalPermission());
    animalEntity.get(0).setCtr("0");// it will be 0 in the begin
    animalEntity.get(0).setIntroduction(request.getAnimal().getIntroduction());
    animalEntity.get(0).setLigation(request.getAnimal().getLigation());
    animalEntity.get(0).setName(request.getAnimal().getName());
    animalEntity.get(0).setPhoto(request.getAnimal().getPhoto());
    animalEntity.get(0).setPostStatus("N");// it will be N in the begin
    Date date = new Date();
    Timestamp ts = new Timestamp(date.getTime());
    System.out.println(ts);
    animalEntity.get(0).setPublishDate(ts);// fetch the timestamp
    animalEntity.get(0).setSex(request.getAnimal().getSex());
    animalEntity.get(0).setSpeciesId(request.getAnimal().getSpecies());
    animalEntity.get(0).setType(request.getAnimal().getType());
    animalEntity.get(0).setUserId(request.getAnimal().getUserId());// 不可更改
    animalRepository.save(animalEntity.get(0));

    updateAnimalRespDTO.setStatusCode(ReturnCodeEnum.SUCCESS.getCode());
    updateAnimalRespDTO.setStatus(ReturnCodeEnum.SUCCESS.getDesc());

    return updateAnimalRespDTO;
  }


  @Override
  public UpdateUserRespDTO updateUserByUserId(UpdateUserReqDTO request) {
    UpdateUserRespDTO updateUserRespDTO = new UpdateUserRespDTO();
    List<UserEntity> userEntity = userRepository.findByUserId(request.getUser().getUserId());
    if (userEntity.isEmpty()) {
      // 若找不到
    }
    System.out.println(userEntity);
    userEntity.get(0).setName(request.getUser().getName());
    userEntity.get(0).setMobile(request.getUser().getMobile());
    userEntity.get(0).setGoogleAccount(userEntity.get(0).getGoogleAccount());// 這個不應該可以更改
    userEntity.get(0).setStatus(userEntity.get(0).getStatus());
    userRepository.save(userEntity.get(0));

    updateUserRespDTO.setReturnCode(ReturnCodeEnum.SUCCESS.getCode());
    updateUserRespDTO.setReturnDesc(ReturnCodeEnum.SUCCESS.getDesc());

    return updateUserRespDTO;
  }

  @Override
  public GetUserSubscriptionRespDTO queryUserSubscriptions(Long userId)
      throws DataNotFoundException {
    String userSubcription = userRepository.findUserSubscription(userId);
    if (userSubcription == null) {
      throw new DataNotFoundException();
    }

    List<String> stringList = Arrays.asList(userSubcription.split(","));
    List<Long> transUserSubcription =
        stringList.stream().map(Long::parseLong).collect(Collectors.toList());
    List<MinimalistAnimal> subcriptionList = new ArrayList<>();
    for (Long animalId : transUserSubcription) {
      List<AnimalEntity> animals = animalRepository.findById(animalId);
      MinimalistAnimal animalInfo = new MinimalistAnimal();
      animalInfo.setId(animals.get(0).getId());
      animalInfo.setName(animals.get(0).getName());
      subcriptionList.add(animalInfo);
    }
    GetUserSubscriptionRespDTO response = new GetUserSubscriptionRespDTO();
    response.setMinimalistAnimals(subcriptionList);

    return response;
  }


  @Override
  public PutUserSubscriptionRespDTO subscriptionAnimal(PutUserSubscriptionReqDTO request)
      throws DatabaseInteractFailedException, UpdateFailedException {
    // 取得資料庫是否有寫入紀錄
    String userSubcription = userRepository.findUserSubscription(request.getUserId());
    List<Long> userNewSuscription;
    // 無則進行新增List，若有取得該資料，將String轉型為List<Long>再將id加入
    if (userSubcription == null) {
      userNewSuscription = new ArrayList<>();
      userNewSuscription.add(request.getId());
    } else {
      List<String> stringList = Arrays.asList(userSubcription.split(","));
      userNewSuscription = stringList.stream().map(Long::parseLong).collect(Collectors.toList());
      // 怕使用者一直按同一個寵物的愛心，先尋找是否有這個id，若沒有存在過，則加入List當中，若有相同值則回傳UpdateException表示該值已重複
      if (!userNewSuscription.contains(request.getId())) {
        userNewSuscription.add(request.getId());
      } else {
        throw new UpdateFailedException();
      }

    }
    // List<Long>進行轉型轉回String 回存資料庫
    String transUserNewSuscription = String.join(",",
        userNewSuscription.stream().map(String::valueOf).collect(Collectors.toList()));
    System.out.println(transUserNewSuscription);
    try {
      userRepository.updateUserSubscription(transUserNewSuscription, request.getUserId());
    } catch (Exception e) {
      e.printStackTrace();
      throw new DatabaseInteractFailedException();
    }
    PutUserSubscriptionRespDTO response = new PutUserSubscriptionRespDTO();
    response.setStatus(ReturnCodeEnum.SUCCESS.getDesc());
    response.setStatusCode(ReturnCodeEnum.SUCCESS.getCode());
    return response;
  }

  @Override
  public NewUserRespDTO createUserInfo(NewUserReqDTO request) {
    NewUserRespDTO newUserRespDTO = new NewUserRespDTO();
    UserEntity userEntity = new UserEntity();
    userEntity.setName(request.getUser().getName());
    userEntity.setMobile(request.getUser().getMobile());
    userEntity.setGoogleAccount(request.getUser().getGoogleAccount());
    userEntity.setStatus("Y");// 新帳號一開始為開通
    userRepository.save(userEntity);

    newUserRespDTO.setReturnCode(ReturnCodeEnum.SUCCESS.getCode());
    newUserRespDTO.setReturnDesc(ReturnCodeEnum.SUCCESS.getDesc());

    return newUserRespDTO;
  }

  public PutUserUnsubscriptionRespDTO unsubscriptionAnimal(PutUserUnsubscriptionReqDTO request)
      throws DatabaseInteractFailedException {
    // 取得使用者目前的訂閱字串
    String userSubcription = userRepository.findUserSubscription(request.getUserId());

    // 並將String轉型為List<Long>
    List<String> stringList = Arrays.asList(userSubcription.split(","));
    List<Long> userNewSuscription =
        stringList.stream().map(Long::parseLong).collect(Collectors.toList());
    
    // 找到list當中的要傳進的id，並刪除
    userNewSuscription.remove(request.getId());
    String transUserNewSuscription = String.join(",",
        userNewSuscription.stream().map(String::valueOf).collect(Collectors.toList()));
    try {
      userRepository.updateUserSubscription(transUserNewSuscription, request.getUserId());
    } catch (Exception e) {
      throw new DatabaseInteractFailedException();
    }
    PutUserUnsubscriptionRespDTO response = new PutUserUnsubscriptionRespDTO();
    response.setStatus(ReturnCodeEnum.SUCCESS.getDesc());
    response.setStatusCode(ReturnCodeEnum.SUCCESS.getCode());
    return response;
  }

  @Override
  public GetCommentCityRespDTO queryAllCity() {
    List<CityEntity> cityList = cityRepository.findAllCity();
    List<CityMapping> cities = new ArrayList<>();
    for (CityEntity city : cityList) {
      CityMapping dtoCity = new CityMapping();
      dtoCity.setCityId(city.getCityId());
      dtoCity.setCityName(city.getCityName());
      cities.add(dtoCity);
    }
    GetCommentCityRespDTO response = new GetCommentCityRespDTO();
    response.setCities(cities);
    return response;
  }

  @Override
  public GetCommentSpeciesRespDTO queryAllSpecies() {
    List<SpeciesEntity> speciesList = speciesRepository.findAllSpecies();
    List<SpeciesMapping> dtoSpeciesList = new ArrayList<>();
    for (SpeciesEntity species : speciesList) {
      SpeciesMapping dtoSpecies = new SpeciesMapping();
      dtoSpecies.setSpeciesId(species.getSpeciesId());
      dtoSpecies.setSpeciesName(species.getSpeciesName());
      dtoSpeciesList.add(dtoSpecies);
    }
    GetCommentSpeciesRespDTO response = new GetCommentSpeciesRespDTO();
    response.setSpecies(dtoSpeciesList);
    return response;
  }

}
