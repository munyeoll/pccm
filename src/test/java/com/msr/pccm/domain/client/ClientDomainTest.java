package com.msr.pccm.domain.client;

import com.wsm.domain.client.entity.Client;
import com.wsm.domain.client.entity.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClientDomainTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Create client")
    void createClient() {
        String clientNo = "tst0001";
        String clientName = "이테스";
        String phoneNo = "010-1111-2222";
        String emailAddr = "test@test.com";
        String beginYmd = "20230515";
        String endYmd = "29991231";

        Client newClient = Client.builder()
                .clientNo(clientNo)
                .clientName(clientName)
                .phoneNo(phoneNo)
                .emailAddr(emailAddr)
                .beginYmd(beginYmd)
                .endYmd(endYmd)
                .build();

        Client entity = clientRepository.save(newClient);

        assertThat(entity.getClientNo()).isEqualTo(clientNo);
        assertThat(entity.getClientName()).isEqualTo(clientName);
        assertThat(entity.getPhoneNo()).isEqualTo(phoneNo);
        assertThat(entity.getEmailAddr()).isEqualTo(emailAddr);
        assertThat(entity.getBeginYmd()).isEqualTo(beginYmd);
        assertThat(entity.getEndYmd()).isEqualTo(endYmd);
        assertThat(entity.getDeleteYn()).isEqualTo("N");
    }
}
