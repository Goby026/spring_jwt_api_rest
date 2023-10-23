package com.dev.pc.controllers;

import com.dev.pc.models.Year;
import com.dev.pc.services.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/v1")
public class YearController {
    @Autowired
    private YearService service;

    @GetMapping("/years")
    public ResponseEntity<HashMap<String, List<Year>>> list() throws Exception {
        List<Year> years = service.listar();
        HashMap<String, List<Year>> resp = new HashMap<>();
        resp.put("years", years);
        return new ResponseEntity<HashMap<String, List<Year>>>(resp, HttpStatus.OK);
    }
}
