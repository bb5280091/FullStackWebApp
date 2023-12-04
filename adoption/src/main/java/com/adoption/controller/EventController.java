package com.adoption.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.adoption.dto.GetRankCtrRespDTO;
import com.adoption.dto.PutAdoptionCtrRespDTO;
import com.adoption.exception.DataNotFoundException;
import com.adoption.exception.DatabaseInteractFailedException;
import com.adoption.service.EventService;


@RestController
@CrossOrigin("http://localhost:4200")
public class EventController {

  @Autowired
  EventService service;

  @PutMapping(value = "/adoptions/ctr", params = "id")
  public PutAdoptionCtrRespDTO addAnimalCtr(@RequestParam(name = "id") Long id)
      throws DatabaseInteractFailedException {
    return service.addAnimalCtr(id);
  }

  @GetMapping(value = "/adoptions/rankCtr")
  public GetRankCtrRespDTO animalRankByCtr() throws DataNotFoundException {
    return service.animalRankByCtr();
  }
}
