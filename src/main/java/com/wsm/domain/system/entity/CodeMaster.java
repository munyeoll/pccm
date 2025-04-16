package com.wsm.domain.system.entity;

import com.wsm.domain.common.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Builder
public class CodeMaster extends CommonEntity {

    @Id
    @Column(length = 20)
    private String codeId;

    @Column(nullable = false, length = 100)
    private String codeName;

    @Column(length = 1)
    private String deleteYn;

    @Column
    private LocalDateTime deletedDate;

    @Column(length = 300)
    private String note;

    @OneToMany(mappedBy = "codeMaster", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CodeDetail> codeDetails;

    public void update(String codeName, String note) {
        this.codeName = codeName;
        this.note = note;
    }

    public void delete() {
        this.deleteYn = "Y";
        this.deletedDate = LocalDateTime.now();
    }
}
