var WsmTabulator = (function() {
    let tabulator = {};

    /**
     * 이메일 유효성 검사
     */
    function isValidEmail(value) {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 기본 이메일 패턴
        return regex.test(value);
    }

    /**
     * 전화번호 유효성 검사 (한국 기준: 숫자와 '-' 포함)
     */
    function isValidPhoneNumber(value) {
        const regex = /^\d{2,3}-\d{3,4}-\d{4}$/; // 형식: 010-1234-5678 또는 02-123-4567
        return regex.test(value);
    }

    /**
     * 영문&숫자 유효성 검사 (영문과 숫자만 사용 가능)
     */
    function isValidOnlyEngAndNumber(value) {
        const regex = /^[a-zA-Z0-9]+$/;
        return regex.test(value);
    }

    /**
     * Cell 유효성 검사
     */
    function validatorFunc(cell, value, parameters) {
        if(_.isEmpty(value)) {
            return true;
        }

        let format = parameters.format;
        if(!_.isEmpty(format)) {
            if(format === 'email') {
                return isValidEmail(value);
            }
            else if(format === 'phone') {
                return isValidPhoneNumber(value);
            }
            else if(format === 'engNum') {
                return isValidOnlyEngAndNumber(value);
            }
        }

        return true;
    }

    tabulator.column = {
        columns: [],
        build: function() {
            let returnValue = this.columns;
            this.columns = [];
            return returnValue;
        },
        add: function(option) {
            this.columns.push(option);
            return this;
        },
        addText: function(option) {
            let columnInfo = {
                title: option.title,
                field: option.field,
                width: option.width,
                visible: _.isNil(option.visible) ? true : option.visible,
                hozAlign: _.isEmpty(option.align) ? 'center' : option.align, // 데이터 정렬
                headerHozAlign: 'center', // 헤더 정렬
                editor: 'input',
            };

            // 영문,숫자
            if(option.dataType === "engNum") {
                columnInfo.validator = [{
                    type: (cell, value, parameters) => validatorFunc(cell, value, parameters),
                    parameters: {
                        format: 'engNum',
                        errorMsg: '[' + option.title + ']는 영문 혹은 숫자만 입력 가능합니다.',
                    }
                }];
            }
            // 휴대전화
            if(option.dataType === "phone") {
                columnInfo.validator = [{
                    type: (cell, value, parameters) => validatorFunc(cell, value, parameters),
                    parameters: {
                        format: 'phone',
                        errorMsg: '올바른 전화번호 형식(예: 010-1234-5678)이 아닙니다.',
                    }
                }];
            }
            // 이메일
            if(option.dataType === "email") {
                columnInfo.validator = [{
                    type: (cell, value, parameters) => validatorFunc(cell, value, parameters),
                    parameters: {
                        format: 'email',
                        errorMsg: '올바른 이메일 형식이 아닙니다.',
                    }
                }];
            }

            this.columns.push(columnInfo);
            return this;
        },
        addList: function(option) {
            let columnInfo = {
                title: option.title,
                field: option.field,
                width: option.width,
                visible: _.isNil(option.visible) ? true : option.visible,
                hozAlign: _.isEmpty(option.align) ? 'center' : option.align, // 데이터 정렬
                headerHozAlign: 'center', // 헤더 정렬
                editor: 'list',
                editorParams: {
                    values: _.isEmpty(option.listItem) ? [] : option.listItem
                },
                formatter: (cell) => {
                    const value = cell.getValue();
                    const match = clientTypeCode.find(option => option.value === value);
                    return match ? match.label : value;
                },
            };

            this.columns.push(columnInfo);
            return this;
        },
        addDate: function(option) {
            let columnInfo = {
                title: option.title,
                field: option.field,
                width: option.width,
                visible: _.isNil(option.visible) ? true : option.visible,
                hozAlign: _.isEmpty(option.align) ? 'center' : option.align, // 데이터 정렬
                headerHozAlign: 'center', // 헤더 정렬
                editor: 'date',
                formatter: "datetime",
                formatterParams:{
                    inputFormat: "yyyyMMdd",
                    outputFormat: "yyyy-MM-dd",
                    invalidPlaceholder: "(invalid date)",
                },
                editorParams:{
                    min:"19000101",
                    max:"29991231",
                    format: "yyyyMMdd",
                    verticalNavigation: "table",
                },
            };

            this.columns.push(columnInfo);
            return this;
        }
    };

    tabulator.newTabulator = function(element, option, modules) {
        let tabulatorObj = {
                editedRowIds: {
                    created:  {}, // 신규
                    modified: {}, // 수정
                    deleted:  {}, // 삭제
                },
                currentRowId: 0,
            };

        option = option || {};
        let columns = option.columns || [];
        let requiredFields = option.requiredFields || [];

        /*
         * 필수 항목 표기
         */
        columns.forEach((col) => {
            col.editable = (cell) => editableFunc(cell);
            col.cellEdited = (cell) => cellModifiedFunc(cell);
            if (requiredFields.indexOf(col.field) !== -1) {
                col.title = "<span style='color: red;'>*</span> " + col.title;
            }
        });

        /*
         * 그리드 필수 항목 추가
         */
        // row id
        columns.push({title: "gridRowId", field: "gridRowId", width: 50, visible: false});
        // row status
        columns.push({title: "status", field: "rowStatus", width: 50, visible: false});

        /*
         * 그리드 옵션 항목 추가
         */
        // 삭제
        if(option.useDelete) {
            columns.unshift({title: "삭제", field: "deleteChk", headerSort: false, headerHozAlign: "center", hozAlign: "center",
                formatter: function(cell) {
                    let value = cell.getValue();
                    return value
                        ? "<input type='checkbox' checked />"
                        : "<input type='checkbox' />";
                },
                cellClick: function(e, cell) {
                    let value = cell.getValue();
                    if(typeof value === "undefined") {
                        value = false;
                    }
                    cell.setValue(!value); // 체크박스 상태 토글
                },
                cellEdited: (cell) => deleteChkEditedFunc(cell),
            });
        }
        // 상태
        if(option.useStatus) {
            columns.unshift({title: "상태", field: "statusLabel", width: 50, headerSort: false, headerHozAlign: "center", hozAlign: "center"});
        }

        // Tabulator 객체 생성
        tabulatorObj.tabulator = new Tabulator(element, option, modules);

        /**
         * Cell 삭제 후처리
         */
        function deleteChkEditedFunc(cell) {
            let rowData = cell.getRow().getData();
            let gridRowId = rowData.gridRowId;

            if(rowData.deleteChk) {
                // 추가 행을 삭제 체크하면 제거한다.
                if(!_.isEmpty(tabulatorObj.editedRowIds.created[gridRowId])) {
                    delete tabulatorObj.editedRowIds.created[gridRowId];
                    cell.getRow().delete();
                    return;
                }

                // 삭제 체크 처리
                cell.getRow().getCell("statusLabel").setValue("삭제");
                rowData.status = "D";
                tabulatorObj.editedRowIds.deleted[gridRowId] = rowData;
            } else {
                // 삭제 취소 시 그전에 수정 상태 였다면 수정 상태로 맹글어 줌
                if(!_.isEmpty(tabulatorObj.editedRowIds.modified[gridRowId])) {
                    rowData.status = "M";
                    cell.getRow().getCell("statusLabel").setValue("수정");
                } else {
                    rowData.status = "";
                    cell.getRow().getCell("statusLabel").setValue("");
                }
                delete tabulatorObj.editedRowIds.deleted[gridRowId];
            }
        }

        /**
         * Cell 수정 후처리
         */
        function cellModifiedFunc(cell) {
            let rowData = cell.getRow().getData();
            let gridRowId = rowData.gridRowId;

            // 추가 행은 수정으로 변경 하지 않음
            if(!_.isEmpty(tabulatorObj.editedRowIds.created[gridRowId])) return;

            let existing = tabulatorObj.editedRowIds.modified[gridRowId];
            cell.getRow().getCell("statusLabel").setValue("수정");
            if (!existing) {
                rowData.status = "M";
                tabulatorObj.editedRowIds.modified[gridRowId] = rowData;
            }
        }

        /**
         * Cell 수정 가능여부
         */
        function editableFunc(cell) {
            let rowData = cell.getRow().getData();

            // 고객번호는 수정 불가
            if(cell.getColumn().getField() === "clientNo") {
                if(rowData.status === "C") return true;
                else return false;
            }

            return !(tabulatorObj.editedRowIds.deleted[rowData.gridRowId]);
        }

        // 데이터 로드 후처리
        tabulatorObj.tabulator.on("dataLoaded", (data) => {
            tabulatorObj.editedRowIds.created  = {};
            tabulatorObj.editedRowIds.modified = {};
            tabulatorObj.editedRowIds.deleted  = {};
            if(data && data.length > 0) {
                data.forEach(row => {
                    row.gridRowId = (tabulatorObj.currentRowId)++;
                    row.deleteChk = false;
                });
            }
        });

        // 유효성검사
        tabulatorObj.tabulator.on("validationFailed", (cell, value, validators) => {
            alert(validators[0].parameters.errorMsg);
        });

        // 행추가 이벤트
        tabulatorObj.addRow = (addObj) => {
            addObj.gridRowId = (tabulatorObj.currentRowId)++;
            tabulatorObj.tabulator.addRow(addObj).then(
                row => {
                    tabulatorObj.editedRowIds.created[row.getData().gridRowId] = row.getData()
                }
            );
        }

        // 데이터 바인딩
        tabulatorObj.replaceData = (data) => {
            tabulatorObj.tabulator.replaceData(data);
        }

        // Get all rows
        tabulatorObj.getRows = () => {
            return tabulatorObj.tabulator.getRows();
        }

        // Get modified rows
        tabulatorObj.getModifiedData = () => {
            let rows = tabulatorObj.tabulator.getRows();
            let data = [];
            rows.forEach(row => {
                const rowData = row.getData();
                if(["C","M","D"].indexOf(rowData.status) !== -1) {
                    data.push(rowData);
                }
            });
            return data;
        }

        return tabulatorObj;
    };

    return tabulator;
})();