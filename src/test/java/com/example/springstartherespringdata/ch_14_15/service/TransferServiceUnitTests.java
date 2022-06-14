package com.example.springstartherespringdata.ch_14_15.service;

import com.example.springstartherespringdata.ch_14_15.model.Account;
import com.example.springstartherespringdata.ch_14_15.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TransferServiceUnitTests {

    @Test
    @DisplayName("Test the amount is transferred " +
            "from one account to another if no exception occurs.")
    void moneyTransferHappyFlow() {
        /*Assumptions - identify and control dependencies - Arrange*/
        AccountRepository accountRepository = mock(AccountRepository.class);
        TransferService transferService = new TransferService(accountRepository);

        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal(1000));

        Account receiver = new Account();
        receiver.setId(2);
        receiver.setAmount(new BigDecimal(1000));

        given(accountRepository.findById(sender.getId())).willReturn(Optional.of(sender));
        given(accountRepository.findById(receiver.getId())).willReturn(Optional.of(receiver));

        /*Call - Execution - Act*/
        transferService.transferMoney(sender.getId(), receiver.getId(), new BigDecimal(100));

        /*Validations - Assert*/
        verify(accountRepository).changeAmount(1, new BigDecimal(900));
        verify(accountRepository).changeAmount(2, new BigDecimal(1100));
    }
}