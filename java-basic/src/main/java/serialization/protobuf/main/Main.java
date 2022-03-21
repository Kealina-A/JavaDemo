package serialization.protobuf.main;

import serialization.protobuf.AddressBook;
import serialization.protobuf.Person;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {

    public static void main (String[] args) throws Exception {
        Person john =
                Person.newBuilder()
                        .setId(1234)
                        .setName("John Doe")
                        .setEmail("jdoe@example.com")
                        .addPhones(
                                Person.PhoneNumber.newBuilder()
                                        .setNumber("555-4321")
                                        .setType(Person.PhoneType.HOME))
                        .build();


        AddressBook.Builder addressBook = AddressBook.newBuilder();
        addressBook.addPerson(john);

        String file="./Person";
        addressBook.build().writeTo(new FileOutputStream(file));

        AddressBook read =
                AddressBook.parseFrom(new FileInputStream(file));

        System.out.println(read);

    }
}
