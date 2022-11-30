package io.diana.calculaterate.service.criteria;

import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;

public class FilterCriteria implements Serializable, Criteria {

    private StringFilter filter;

    public FilterCriteria() {
    }

    public FilterCriteria(FilterCriteria filter) {
        this.filter = filter.filter == null ? null : filter.filter.copy();
    }

    @Override
    public Criteria copy() {
        return new FilterCriteria(this);
    }

    public StringFilter getFilter() {
        return filter;
    }

    public void setFilter(StringFilter filter) {
        this.filter = filter;
    }
}
