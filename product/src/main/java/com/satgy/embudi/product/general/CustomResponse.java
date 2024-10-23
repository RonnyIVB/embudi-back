package com.satgy.embudi.product.general;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class CustomResponse {
    int totalPages;
    int pageSize; // items por página
    long totalItems;
    int pageNumber; // pagina actual
    Object content;

    private static CustomResponse create(int totalPages, int pageSize, long totalItems, int pageNumber, Object content){
        return new CustomResponse(totalPages, pageSize, totalItems, pageNumber, content);
    }

    public static CustomResponse createFromPage(Page pagedResult, int pageSize, int pageNumber){
        if(pagedResult.hasContent()) {
            return CustomResponse.create(pagedResult.getTotalPages(), pageSize, pagedResult.getTotalElements(), pageNumber, pagedResult.getContent());
        } else {
            return CustomResponse.create(pagedResult.getTotalPages(), pageSize, pagedResult.getTotalElements(), pageNumber, new ArrayList<>());
            //return CustomResponse.crear(0, 0, 0, 0, new ArrayList<>());
        }
    }

    public static CustomResponse createFromQuery(int pageSize, int pageNumber, List list){
        //List lista = q.getResultList(); 
        int totalItems = list.size();

        int totalPages = totalItems / pageSize;

        if (totalItems % pageSize > 0) totalPages ++;

        if (pageNumber >= totalPages || pageNumber < 0){
            return CustomResponse.create(totalPages, pageSize, totalItems, pageNumber, new ArrayList());
        } else {
            int begin = pageNumber * pageSize;
            int finish = pageNumber * pageSize + pageSize;
            if (finish > totalItems) finish = totalItems;
            return CustomResponse.create(totalPages, pageSize, totalItems, pageNumber, list.subList(begin, finish));
        }
    }

    public CustomResponse(int totalPages, int pageSize, long totalItems, int pageNumber, Object content) {
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.pageNumber = pageNumber;
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
