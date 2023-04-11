package com.digdes.school.query;

import com.digdes.school.comparison.ComparisonExpressions;

import java.util.List;
import java.util.Map;

public class Query {
    private String comand;
    private Map<String, Object> changes;
    private boolean flagWhere;
    private List<ComparisonExpressions> conditions;

    public String getComand() {
        return comand;
    }

    public void setComand(String comand) {
        this.comand = comand;
    }

    public Map<String, Object> getChanges() {
        return changes;
    }

    public void setChanges(Map<String, Object> changes) {
        this.changes = changes;
    }

    public boolean isFlagWhere() {
        return flagWhere;
    }

    public void setFlagWhere(boolean flagWhere) {
        this.flagWhere = flagWhere;
    }

    public List<ComparisonExpressions> getConditions() {
        return conditions;
    }

    public void setConditions(List<ComparisonExpressions> conditions) {
        this.conditions = conditions;
    }
}
