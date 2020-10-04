package de.getdate.backend.search;

import javax.ws.rs.QueryParam;

public class SearchParams {
  @QueryParam("query")
  private String query;

  @QueryParam("page")
  private int page;

  @QueryParam("pageSize")
  private int pageSize;

  public SearchParams() {
  }

  SearchParams(String query, int page, int pageSize) {
    this.query = query;
    this.page = page;
    this.pageSize = pageSize;
  }

  public String getQuery() {
    return query;
  }

  public int getPage() {
    return page != 0 ? page : 1;
  }

  public int getPageSize() {
    return pageSize != 0 ? pageSize : 1;
  }

}
