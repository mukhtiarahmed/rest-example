package com.hackerrank.assignment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.assignment.domain.Colour;
import com.hackerrank.assignment.domain.Hobby;

import java.io.*;

/**
 * Created by hp on 1/21/2020.
 */
public class Test {

    public static void main(String[] args) throws  Exception {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "./rest-example/hobbies.csv"));
            String line =  reader.readLine();
            while (( line = reader.readLine()) != null) {
                line = line.trim();
                Hobby hobby = new Hobby();
                hobby.setName(line);
                System.out.println(hobby);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
