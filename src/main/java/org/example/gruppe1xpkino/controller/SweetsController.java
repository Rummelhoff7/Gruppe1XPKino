package org.example.gruppe1xpkino.controller;


import org.example.gruppe1xpkino.model.Sweets;
import org.example.gruppe1xpkino.repository.SweetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sweets")
public class SweetsController {

    @Autowired
    SweetsRepository sweetsRepository;

    @GetMapping()
    public List<Sweets> getAllSweets() {
        return sweetsRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable int id) {
        if (!sweetsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sweetsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sweets> updateSweet(@PathVariable int id, @RequestBody Sweets updatedSweet) {
        return sweetsRepository.findById(id)
                .map(sweet -> {
                    sweet.setName(updatedSweet.getName());
                    sweet.setPrice(updatedSweet.getPrice());
                    sweet.setImg(updatedSweet.getImg());
                    sweetsRepository.save(sweet);
                    return ResponseEntity.ok(sweet);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
