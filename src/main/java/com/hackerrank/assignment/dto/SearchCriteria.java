package com.hackerrank.assignment.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public class SearchCriteria implements Serializable {

    public static final String ASC = "ASC";

    public static final String DESC = "DESC";

    private String searchString;

    private int page;

    private int pageSize;

    private String sortColumn;

    private String sortOrder = ASC;

    private boolean activeOnly;


    public static SearchCriteriaBuilder builder() {
        return new SearchCriteriaBuilder();
    }

    public static final class SearchCriteriaBuilder {
        private String searchString;
        private int page;
        private int pageSize;
        private String sortColumn;
        private String sortOrder;
        private boolean activeOnly;

        private SearchCriteriaBuilder() {
        }


        public SearchCriteriaBuilder withSearchString(String searchString) {
            this.searchString = searchString;
            return this;
        }

        public SearchCriteriaBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public SearchCriteriaBuilder withPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public SearchCriteriaBuilder withSortColumn(String sortColumn) {
            this.sortColumn = sortColumn;
            return this;
        }

        public SearchCriteriaBuilder withSortOrder(String sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public SearchCriteriaBuilder withActiveOnly(boolean activeOnly) {
            this.activeOnly = activeOnly;
            return this;
        }

        public SearchCriteria build() {
            return new SearchCriteria(searchString, page, pageSize, sortColumn, sortOrder, activeOnly);
        }
    }
}
