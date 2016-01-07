package com.ani.octopus.service.agent.service.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor zhaoyu
 * @date 16-1-6
 * @since JDK 1.7
 */
class Dog implements Serializable {
    int age;
    String name;

    public Dog() {
    }

    public Dog(int age, String name) {
        this.age = age;
        this.name = name;
    }

}

class Stub implements Serializable {
    int num;
    String name;
    List<Dog> dogList;

    public Stub() {
    }

    public Stub(int num, String name, List<Dog> dogList) {
        this.num = num;
        this.name = name;
        this.dogList = dogList;
    }

}

public class JsonTest {
    public static void main(String[] args) throws JsonProcessingException {
        Dog dog = new Dog(1, "dog1");
        List<Dog> dogList = new ArrayList<>();
        dogList.add(dog);

        Stub stub = new Stub(1, "stub1", dogList);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(stub);
        System.out.println(result);
    }
}
