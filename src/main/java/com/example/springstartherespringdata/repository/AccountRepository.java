package com.example.springstartherespringdata.repository;

import com.example.springstartherespringdata.model.Account;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * SpringData repository implementation.
 * The first generic type value is the type of the model class representing the table.
 * The second is the type of the primary key field.
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
    /*When the method name starts with “find,” Spring Data knows you want to SELECT
something. Next, the word “Accounts” tells Spring Data what you want to SELECT. Spring
Data is so smart that I could have even named the method findByName. It would still
know what to select just because the method is in the AccountRepository interface. In
this example, I wanted to be more specific and make the operation name clear. After the
“By” in the method’s name, Spring Data expects to get the query’s condition (the WHERE
clause). SELECT * FROM ACCOUNT WHERE NAME = ?
    But often the best is to indicate SQL query explicitly*/
    @Query("SELECT * FROM account WHERE name=:name")
    //Remember that the parameter’s name
    // in the query should be the same as the method parameter’s name.
    // There shouldn’t be any spaces between the colon (:) and the parameter’s name.
    List<Account> findAccountsByName(String name);

    /*If you use UPDATE, INSERT, or DELETE, you also need to annotate
      the method with @Modifying*/
    @Modifying
    @Query("UPDATE account SET amount=:amount WHERE id=:id")
    void changeAmount(long id, BigDecimal amount);
}
