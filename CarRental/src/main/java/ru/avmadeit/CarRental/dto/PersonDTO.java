package ru.avmadeit.CarRental.dto;

import ru.avmadeit.CarRental.models.Car;
import ru.avmadeit.CarRental.models.Person;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class PersonDTO {

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 100, message = "Name should contain from 2 to 100 characters")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 1900, message = "Year must be more then 1900")
    @Max(value = 2004, message = "You are under then 18")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    private List<CarDTO.CarResponse> cars;

    public PersonDTO(){}

    public PersonDTO(Person person) {
        this.fullName = person.getFullName();
        this.yearOfBirth = person.getYearOfBirth();
        this.cars = new ArrayList<CarDTO.CarResponse>();
        if (person.getCars()!=null){
            for (Car c: person.getCars()) {
                this.cars.add(new CarDTO.CarResponse(c));
            }
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<CarDTO.CarResponse> getCars() {
        return cars;
    }

    public void setCars(List<CarDTO.CarResponse> cars) {
        this.cars = cars;
    }

    public static class PersonResponse{

        private String fullName;

        private int yearOfBirth;

        public PersonResponse() {
        }

        public PersonResponse(Person person) {
            this.fullName = person.getFullName();
            this.yearOfBirth = person.getYearOfBirth();
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public int getYearOfBirth() {
            return yearOfBirth;
        }

        public void setYearOfBirth(int yearOfBirth) {
            this.yearOfBirth = yearOfBirth;
        }
    }
}
