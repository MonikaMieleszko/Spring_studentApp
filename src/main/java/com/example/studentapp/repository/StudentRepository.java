package com.example.studentapp.repository;

import com.example.studentapp.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//używamy interfejs, bo to jest warstwa bezpośredniego dostępu z bazą danych
//ułatwia testowanie
@Repository
//jaka tabela i jakiego typu pole jest brane jako klucz główny
public interface StudentRepository extends JpaRepository<StudentModel, Long> {

    //TODO metody, query
}
