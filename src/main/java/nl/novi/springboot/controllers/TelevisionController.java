package nl.novi.springboot.controllers;

import nl.novi.springboot.exceptions.RecordNotFoundException;
import nl.novi.springboot.model.Televisions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping ("/televisions")
public class TelevisionController {

    private List<Televisions> television = new ArrayList<>();

    @GetMapping()
    public ArrayList<Televisions> fetchAllTvs() {
        return (ArrayList<Televisions>) television;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getSingleTv(@PathVariable Long id) {
        if (id >= 0 && id < television.size()) {
            throw new RecordNotFoundException("De Televisie met id " + id + "zit in de database maar ik zeg lekker niet welke het is!");
        } else {
            throw new RecordNotFoundException(id);
        }
    }

    @PostMapping
    public ResponseEntity<Televisions> addTelevision(@RequestBody Televisions television) {
        this.television.add(television);
        return new ResponseEntity<>(television, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Televisions> updateTelevision(@PathVariable int id, @RequestBody Televisions updatedTelevision) {
        if (id < 0 || id >= television.size()) {
            throw new RecordNotFoundException("Television not found at index: " + id);
        }

        Televisions existingTv = television.get(id);

        existingTv.setBrand(updatedTelevision.getBrand());
        existingTv.setModel(updatedTelevision.getModel());
        existingTv.setColor(updatedTelevision.getColor());

        return ResponseEntity.ok(existingTv);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelevision(@PathVariable int id) {
        if (id < 0 || id >= television.size()) {
            throw new RecordNotFoundException(id);
        }
        television.remove(id);
        return ResponseEntity.noContent().build();
    }
}