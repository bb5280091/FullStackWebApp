package com.adoption.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.adoption.dto.GetRankCtrRespDTO;
import com.adoption.dto.PutAdoptionCtrRespDTO;
import com.adoption.dto.SimpleAnimal;
import com.adoption.entity.AnimalEntity;
import com.adoption.exception.DataNotFoundException;
import com.adoption.exception.DatabaseInteractFailedException;
import com.adoption.repository.AnimalRepository;
import com.adoption.repository.CityRepository;
import com.adoption.repository.SpeciesRepository;
import com.adoption.service.EventService;

@Service
public class EventServiceImpl implements EventService {

  @Autowired
  private AnimalRepository animalRepository;
  
  @Autowired
  private SpeciesRepository speciesRepository;
  
  @Autowired
  private CityRepository cityRepository;
  

  //型態應該改成long不是string
  @Override

  public PutAdoptionCtrRespDTO addAnimalCtr(Long id) throws DatabaseInteractFailedException {
    Long transId = id; 
    try {
      animalRepository.addAnimalCtr(transId);
    } catch (Exception e) {
      throw new DatabaseInteractFailedException();
    }
    PutAdoptionCtrRespDTO response = new PutAdoptionCtrRespDTO();
    response.setStatusCode("0000");
    response.setStatus("Sucess");
    return  response;
  }

  
  @Override
  public GetRankCtrRespDTO animalRankByCtr() throws DataNotFoundException {
    List<AnimalEntity> rankAnimals = animalRepository.queryAnimalByRankCtr();
    if(rankAnimals.isEmpty()) {
      throw new DataNotFoundException();
    }
    
    List<SimpleAnimal> dtoAnimals = new ArrayList<>();
    int count = 0;
    for(AnimalEntity rankanimal : rankAnimals) {
      if (count >= 3) break; 
      SimpleAnimal animal = new SimpleAnimal();
      animal.setId(rankanimal.getId());
      animal.setName(rankanimal.getName());
      List<String> speciesName =  speciesRepository.qureySpeciesNameBySpeciesId(rankanimal.getSpeciesId());
      animal.setSpeciesName(speciesName.get(0));
      animal.setSex(rankanimal.getSex());
      List<String> cityName = cityRepository.qureyCityNameByCityId(rankanimal.getCityId());
      animal.setCityName(cityName.get(0));

      animal.setConditionAffidavit(rankanimal.getConditionAffidavit());
      animal.setConditionFollowup(rankanimal.getConditionFollowUp());
      animal.setConditionAgeLimit(rankanimal.getConditionAgeLimit());
      animal.setConditionParentalPermission(rankanimal.getConditionParentalPermission());
      animal.setIntroduction(rankanimal.getIntroduction());
      animal.setPhoto(rankanimal.getPhoto());
      dtoAnimals.add(animal);

      count++;

    }
    GetRankCtrRespDTO response = new GetRankCtrRespDTO();
    response.setAnimals(dtoAnimals);
    return response;
  }

}
