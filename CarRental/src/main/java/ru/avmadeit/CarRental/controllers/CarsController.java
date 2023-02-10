package ru.avmadeit.CarRental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.avmadeit.CarRental.models.Car;
import ru.avmadeit.CarRental.models.Person;
import ru.avmadeit.CarRental.services.CarsService;
import ru.avmadeit.CarRental.services.PeopleService;


import javax.validation.Valid;

@Controller
@RequestMapping("/cars")
public class CarsController {

    private CarsService carsService;
    private PeopleService peopleService;

    @Autowired
    public CarsController(CarsService carsService, PeopleService peopleService) {
        this.carsService = carsService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("cars", carsService.findAll());
        return "cars/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("car", carsService.findOne(id));

        Person carOwner = carsService.getCarOwner(id);

        if (carOwner != null)
            model.addAttribute("owner", carOwner);
        else
            model.addAttribute("people", peopleService.findAll());

        return "cars/show";
    }

    @GetMapping("/new")
    public String newCar (@ModelAttribute("car") Car car){
        return "cars/new";
    }

    @PostMapping()
    public String create (@ModelAttribute("car") @Valid Car car, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "cars/new";

        carsService.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/{id}/edit")
    public String edit (@PathVariable("id") int id, Model model){
        model.addAttribute("car", carsService.findOne(id));
        return "cars/edit";
    }

    @PatchMapping("/{id}")
    public String update (@ModelAttribute("car") @Valid Car car
            , BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "cars/edit";

        carsService.update(id,car);
        return "redirect:/cars";
    }

    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") int id){
        carsService.delete(id);
        return "redirect:/cars";
    }

    @PatchMapping("/{id}/release")
    public String release (@PathVariable("id") int id){
        carsService.release(id);
        return "redirect:/cars/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        carsService.assign(id,selectedPerson);
        return "redirect:/cars/" + id;
    }

    @GetMapping("/search")
    public String newSearch (){
        return "cars/search";
    }

    @PostMapping("/search")
    public String search(@RequestParam("query") String query, Model model){
        model.addAttribute("cars", carsService.findByModel(query));
        return "cars/search";
    }
}

