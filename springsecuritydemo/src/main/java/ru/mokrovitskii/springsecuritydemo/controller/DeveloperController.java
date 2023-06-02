package ru.mokrovitskii.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.*;
import ru.mokrovitskii.springsecuritydemo.model.Developer;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/developer")
public class DeveloperController {

    private final List<Developer> developers = new ArrayList<>(
            List.of(
                    new Developer(1L, "Ivan", "Ivanov"),
                    new Developer(2L, "Sergey", "Sergeyev"),
                    new Developer(3L, "Petr", "Petrov")
            )
    );

    @GetMapping
    public List<Developer> getAll() {
        return developers;
    }

    @GetMapping("/{id}")
    public Developer getById(@PathVariable("id") Long id) {
        return developers.stream().filter(developer -> developer.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public Developer create(@RequestBody Developer developer) {
        this.developers.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        this.developers.removeIf(developer -> developer.getId().equals(id));
    }
}
