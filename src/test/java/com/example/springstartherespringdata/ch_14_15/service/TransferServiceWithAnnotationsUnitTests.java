package com.example.springstartherespringdata.ch_14_15.service;

import com.example.springstartherespringdata.ch_14_15.model.Account;
import com.example.springstartherespringdata.ch_14_15.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransferServiceWithAnnotationsUnitTests {
    /*Assumptions - identify and control dependencies*/
    @Mock
    private AccountRepository accountRepository;

    /*With @InjectMocks annotation, you create the object to test and instruct
      the framework to inject all the mocks (created with @Mock) in its parameters*/
    @InjectMocks
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
        transferService.transferMoney(sender.getId(), receiver.getId(), new BigDecimal(100));

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