package com.adoption.service;

import com.adoption.dto.GetRankCtrRespDTO;
import com.adoption.dto.PutAdoptionCtrRespDTO;
import com.adoption.exception.DataNotFoundException;
import com.adoption.exception.DatabaseInteractFailedException;

public interface EventService {

  public PutAdoptionCtrRespDTO addAnimalCtr(Long id) throws DatabaseInteractFailedException;

  public GetRankCtrRespDTO animalRankByCtr() throws DataNotFoundException;

}
