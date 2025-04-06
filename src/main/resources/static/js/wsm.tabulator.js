var WsmTabulator = (function() {
    var tabulator = {};

    var column = {
        columns: [],
        build: function() {
            return columns;
        },
        addText: function() {

        }
    };

    function newTabulator(element, option, modules) {
        return new Tabulator(element, option, modules);
    }

    tabulator.newTabulator = newTabulator;

    return tabulator;
})();