package com.example.springstartherespringdata.ch_14_15.service;

import com.example.springstartherespringdata.ch_14_15.model.Account;
import com.example.springstartherespringdata.ch_14_15.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/*
 * While integration testing Spring knows the tested object and manages it
 * as it would in a running app. Integration tests take a longer time to execute
 * because they have to configure the Spring context.
 * To save time, the best approach is to rely on unit tests to validate
 * your apps’ components’ logic and use the integration tests only
 * to validate how they integrate with the framework.
 */
@SpringBootTest
class TransferServiceSpringIntegrationTests {
    /*Assumptions - identify and control dependencies*/
    /*The @MockBean annotation is a Spring Boot annotation. If you have a plain Spring app
      and not a Spring Boot one as presented here, you won’t be able to use @MockBean.
      However, you can still use the same approach by annotating the configuration class
      with @ExtendsWith(SpringExtension.class).*/
    @MockBean   //Create a mock object that is also part of the Spring context.
    private AccountRepository accountRepository;

    /*Inject the real object from the Spring context whose behavior you’ll test*/
    @Autowired
    private TransferService transferService;

    @Test
    void moneyTransferHappyFlow() {
        /*Assumptions - identify and control dependencies - Arrange*/
        Account sender = new Account();
        sender.setId(1L);
        sender.setAmount(new BigDecimal(1000));

        Account receiver = new Account();
        receiver.setId(2L);
        receiver.setAmount(new BigDecimal(1000));

        given(accountRepository.findById(sender.getId())).willReturn(Optional.of(sender));
        given(accountRepository.findById(receiver.getId())).willReturn(Optional.of(receiver));

        /*Call - Execution - Act*/
        transferService.transferMoney(1, 2, new BigDecimal(100));

        /*Validations - Assert*/
        verify(accountRepository).changeAmount(1, new BigDecimal(900));
        verify(accountRepository).changeAmount(2, new BigDecimal(1100));
    }

    @Test
    void moneyTransferDestinationAccountNotFoundFlow() {
        /*Assumption*/
        Account sender = new Account();
        sender.setId(1L);
        sender.setAmount(new BigDecimal(1000));

        given(accountRepository.findById(1L)).willReturn(Optional.of(sender));
        given(accountRepository.findById(2L)).willReturn(Optional.empty());

        /*Call with validation*/
        assertThrows(AccountNotFoundException.class,
                () -> transferService.transferMoney(1, 2, new BigDecimal(1000)));
    }
}