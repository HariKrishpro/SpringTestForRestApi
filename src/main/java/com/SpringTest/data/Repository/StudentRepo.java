package com.SpringTest.data.Repository;

import com.SpringTest.data.Entity.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<StudentDetails, Integer> {

}
