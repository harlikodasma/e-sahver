package com.github.harlikodasma.backend;

import com.github.harlikodasma.backend.controller.RestrictionController;
import com.github.harlikodasma.backend.model.Restriction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RestrictionTests {

    @Autowired
    RestrictionController restrictionController;

    @Test
    void setAndGetRestriction() {
        Restriction setRestriction = new Restriction(80L, 5);
        restrictionController.setRestriction(setRestriction);

        List<Restriction> getRestriction = restrictionController.getRestriction();
        Assertions.assertEquals(getRestriction.get(0).getItemLimit(), 5);
    }
}
