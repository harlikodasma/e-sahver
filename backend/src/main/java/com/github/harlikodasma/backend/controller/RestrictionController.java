package com.github.harlikodasma.backend.controller;

import com.github.harlikodasma.backend.model.Restriction;
import com.github.harlikodasma.backend.service.RestrictionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/restriction")
public class RestrictionController {

    @Autowired
    RestrictionService restrictionService;

    @ApiOperation("Set restriction value")
    @PostMapping("set")
    public void setRestriction(@RequestBody Restriction restriction) {
        restrictionService.updateRestriction(restriction);
    }

    @ApiOperation("Get restriction value")
    @GetMapping("get")
    public List<Restriction> getRestriction() {
        return restrictionService.getRestriction();
    }
}
