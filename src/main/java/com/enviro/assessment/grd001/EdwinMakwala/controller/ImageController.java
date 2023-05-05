package com.enviro.assessment.grd001.EdwinMakwala.controller;

import com.enviro.assessment.grd001.EdwinMakwala.Exception.ProfileDoesNotExistException;
import com.enviro.assessment.grd001.EdwinMakwala.entity.AccountProfile;
import com.enviro.assessment.grd001.EdwinMakwala.repository.AccountProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/image")
public class ImageController {

    /** I am aware that constructor injection is better practise :) **/
    @Autowired
    AccountProfileRepository accountProfileRepository;

    @GetMapping("{name}/{surname}")
    public ResponseEntity<String> getHttpImageLink(@PathVariable String name, @PathVariable String surname) {

        AccountProfile profile = accountProfileRepository.findByAccountHolderNameAndAccountHolderSurname(name, surname)
                        .orElseThrow(()->
                        new ProfileDoesNotExistException("Account profile: " + name + ", " + surname + " does not exist"));

        String imageLink = profile.getHttpImageLink().substring(6);
        FileSystemResource resource = new FileSystemResource(imageLink);

        return new ResponseEntity<>(resource.getPath(), HttpStatus.OK);
    }

}
