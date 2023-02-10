package ru.avmadeit.CarRental.dto;


import ru.avmadeit.CarRental.models.Car;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;

public class CarDTO {

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 100, message = "Name should contain from 2 to 100 characters")
    @Column(name = "model")
    private String model;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 100, message = "Name should contain from 2 to 100 characters")
    @Column(name = "manufacturer")
    private String manufacturer;

    @Min(value = 2000, message = "Year must be more then 2000")
    @Column(name = "year")
    private int year;

    private ArrayList<PersonDTO.PersonResponse> person;

    public CarDTO(){}

    public CarDTO(Car car) {
        this.model = car.getModel();
        this.manufacturer = car.getManufacturer();
        this.year = car.getYear();
        this.person = new ArrayList<PersonDTO.PersonResponse>();
        if (car.getPerson()!=null){
            this.person.add(new PersonDTO.PersonResponse(car.getPerson()));
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<PersonDTO.PersonResponse> getPerson() {
        return person;
    }

    public void setPerson(ArrayList<PersonDTO.PersonResponse> person) {
        this.person = person;
    }

    public static class CarResponse{
        private String model;

        private String manufacturer;

        private int year;

        public CarResponse(){}

        public CarResponse(Car car) {
            this.model = car.getModel();
            this.manufacturer = car.getManufacturer();
            this.year = car.getYear();
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }
}
