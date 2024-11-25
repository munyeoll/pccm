package com.msr.pccm.domain.pscg;

import com.pccm.domain.pscg.entity.Pscg;
import com.pccm.domain.pscg.entity.PscgRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PscgDomainTest {

    @Autowired
    PscgRepository pscgRepository;

    @Test
    void createPscg() {
        String pscgNo = "TST0001";
        String pscgName = "테스트상담사";
        String phoneNo = "010-2222-3333";

        Pscg newPscg = Pscg.builder()
                .pscgNo(pscgNo)
                .pscgName(pscgName)
                .phoneNo(phoneNo)
                .build();

        Pscg entity = pscgRepository.save(newPscg);

        assertThat(pscgNo).isEqualTo(entity.getPscgNo());
        assertThat(pscgName).isEqualTo(entity.getPscgName());
        assertThat(phoneNo).isEqualTo(entity.getPhoneNo());
        assertThat("N").isEqualTo(entity.getDeleteYn());
    }
}
