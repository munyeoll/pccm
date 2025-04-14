var WsmTabulator = (function () {
    // ===========================================================================
    // private 영역
    // ===========================================================================
    const _private = {
        validators: {
            email: (value) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value),
            phone: (value) => /^\d{2,3}-\d{3,4}-\d{4}$/.test(value),
            engNum: (value) => /^[a-zA-Z0-9]+$/.test(value),
        },

        validatorFunc: (cell, value, parameters) => {
            if (_.isEmpty(value)) return true;
            const format = parameters.format;
            if (!format || !_private.validators[format]) return true;
            return _private.validators[format](value);
        },

        getValidator: (dataType, title) => {
            const messages = {
                engNum: `[${title}]는 영문 혹은 숫자만 입력 가능합니다.`,
                phone: '올바른 전화번호 형식(예: 010-1234-5678)이 아닙니다.',
                email: '올바른 이메일 형식이 아닙니다.',
            };
            return [{
                type: _private.validatorFunc,
                parameters: {
                    format: dataType,
                    errorMsg: messages[dataType],
                }
            }];
        },

        columnBuilder: {
            base(option) {
                return {
                    title: option.title,
                    field: option.field,
                    width: option.width,
                    visible: _.isNil(option.visible) ? true : option.visible,
                    hozAlign: _.isEmpty(option.align) ? 'center' : option.align,
                    headerHozAlign: 'center',
                };
            },
            text(option) {
                const col = this.base(option);
                col.editor = 'input';
                if (["email", "phone", "engNum"].includes(option.dataType)) {
                    col.validator = _private.getValidator(option.dataType, option.title);
                }
                return col;
            },
            list(option) {
                const col = this.base(option);
                col.editor = 'list';
                col.editorParams = {
                    values: option.listItem || [],
                };
                col.formatter = (cell) => {
                    const value = cell.getValue();
                    const match = clientTypeCode.find(opt => opt.value === value);
                    return match ? match.label : value;
                };
                return col;
            },
            date(option) {
                const col = this.base(option);
                Object.assign(col, {
                    editor: 'date',
                    formatter: 'datetime',
                    formatterParams: {
                        inputFormat: 'yyyyMMdd',
                        outputFormat: 'yyyy-MM-dd',
                        invalidPlaceholder: '(invalid date)',
                    },
                    editorParams: {
                        min: '19000101',
                        max: '29991231',
                        format: 'yyyyMMdd',
                        verticalNavigation: 'table',
                    },
                });
                return col;
            }
        }
    };

    // ===========================================================================
    // public 영역
    // ===========================================================================
    const publicApi = {};

    publicApi.column = {
        columns: [],
        requiredFields: [],
        build() {
            const result = this.columns;
            this.columns = [];
            return result;
        },
        addText(option) {
            this.columns.push(_private.columnBuilder.text(option));
            return this;
        },
        addList(option) {
            this.columns.push(_private.columnBuilder.list(option));
            return this;
        },
        addDate(option) {
            this.columns.push(_private.columnBuilder.date(option));
            return this;
        }
    };

    publicApi.newTabulator = function (element, option, modules) {
        const tabulatorObj = {
            editedRowIds: {
                created: {}, modified: {}, deleted: {},
            },
            currentRowId: 0,
            requiredFields: option.requiredFields || [],
            columns: _.cloneDeep(option.columns || []),
        };

        tabulatorObj.columns.forEach((col) => col.label = col.title);

        // 필수 표기 및 편집 설정
        option.columns.forEach(col => {
            col.editable = editableFunc;
            col.cellEdited = cellModifiedFunc;
            if (tabulatorObj.requiredFields.includes(col.field)) {
                col.title = "<span style='color: red;'>*</span> " + col.title;
            }
        });

        /**
         * 시스템 필드 추가
         */
        option.columns.push({ title: "gridRowId", field: "gridRowId", visible: false });
        option.columns.push({ title: "status", field: "rowStatus", visible: false });

        if (option.useDelete) {
            option.columns.unshift({
                title: "삭제", field: "deleteChk", hozAlign: "center", headerHozAlign: "center",
                headerSort: false,
                formatter: cell => `<input type='checkbox' ${cell.getValue() ? "checked" : ""}/>`,
                cellClick: (e, cell) => {
                    const current = cell.getValue() || false;
                    cell.setValue(!current);
                },
                cellEdited: deleteChkEditedFunc
            });
        }

        if (option.useStatus) {
            option.columns.unshift({
                title: "상태", field: "statusLabel", width: 50, headerSort: false,
                hozAlign: "center", headerHozAlign: "center"
            });
        }

        /**
         * Tabulator 생성
         */
        tabulatorObj.tabulator = new Tabulator(element, option, modules);

        /**
         * 이벤트 등록
         */
        tabulatorObj.tabulator.on("dataLoaded", data => {
            tabulatorObj.editedRowIds = { created: {}, modified: {}, deleted: {} };
            _.forEach(data, row => {
                row.gridRowId = tabulatorObj.currentRowId++;
                row.deleteChk = false;
            });
        });

        tabulatorObj.tabulator.on("validationFailed", (cell, value, validators) => {
            alert(validators[0].parameters.errorMsg);
        });

        /**
         * 기능 메서드
         */
        function deleteChkEditedFunc(cell) {
            const rowData = cell.getRow().getData();
            const gridRowId = rowData.gridRowId;

            if (rowData.deleteChk) {
                if (tabulatorObj.editedRowIds.created[gridRowId]) {
                    delete tabulatorObj.editedRowIds.created[gridRowId];
                    cell.getRow().delete();
                    return;
                }
                rowData.status = "D";
                cell.getRow().getCell("statusLabel").setValue("삭제");
                tabulatorObj.editedRowIds.deleted[gridRowId] = rowData;
            } else {
                if (tabulatorObj.editedRowIds.modified[gridRowId]) {
                    rowData.status = "M";
                    cell.getRow().getCell("statusLabel").setValue("수정");
                } else {
                    rowData.status = "";
                    cell.getRow().getCell("statusLabel").setValue("");
                }
                delete tabulatorObj.editedRowIds.deleted[gridRowId];
            }
        }

        function cellModifiedFunc(cell) {
            const rowData = cell.getRow().getData();
            const gridRowId = rowData.gridRowId;
            if (tabulatorObj.editedRowIds.created[gridRowId]) return;

            if (!tabulatorObj.editedRowIds.modified[gridRowId]) {
                rowData.status = "M";
                tabulatorObj.editedRowIds.modified[gridRowId] = rowData;
            }
            cell.getRow().getCell("statusLabel").setValue("수정");
        }

        function editableFunc(cell) {
            const rowData = cell.getRow().getData();
            if (cell.getColumn().getField() === "clientNo") {
                return rowData.status === "C";
            }
            return !tabulatorObj.editedRowIds.deleted[rowData.gridRowId];
        }

        /**
         * 외부 공개 메서드
         */
        tabulatorObj.addRow = (addObj) => {
            addObj.gridRowId = tabulatorObj.currentRowId++;
            tabulatorObj.tabulator.addRow(addObj).then(row => {
                tabulatorObj.editedRowIds.created[addObj.gridRowId] = row.getData();
            });
        };

        tabulatorObj.replaceData = (data) => tabulatorObj.tabulator.replaceData(data);

        tabulatorObj.getRows = () => tabulatorObj.tabulator.getRows();

        tabulatorObj.getModifiedData = () => {
            return tabulatorObj.getRows().map(row => row.getData())
                .filter(data => ["C", "M", "D"].includes(data.status));
        };

        tabulatorObj.validateRequiredFields = () => {
            if (_.isEmpty(tabulatorObj.requiredFields)) return true;

            let valid = true;
            tabulatorObj.getRows().forEach(row => {
                const data = row.getData();
                if (!["C", "M", "D"].includes(data.status)) return;

                tabulatorObj.requiredFields.forEach(field => {
                    if (_.isEmpty(data[field])) {
                        const label = _.find(tabulatorObj.columns, { field }).label;
                        alert(`[${label}]는 필수값 입니다.`);
                        valid = false;
                    }
                });
            });
            return valid;
        };

        return tabulatorObj;
    };

    return publicApi;
})();
