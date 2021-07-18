package com.SpringTest.data.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "studentdetails")
@Builder
@EqualsAndHashCode
@ToString
public class StudentDetails {
    @Id
    @GeneratedValue
    private Integer rollNumber;

    @Column(name = "Name")
    private String name;

    @Column(name = "Age")
    private int age;

    @Column(name = "Phone")
    private String phone;


    @Override
    public String toString() {
        return "{" +
                "rollNumber=" + rollNumber +
                ", name=" + name +
                ", age=" + age +
                ", phone=" + phone +
                '}';
    }
}
