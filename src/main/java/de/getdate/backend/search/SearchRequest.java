package de.getdate.backend.search;

import java.util.List;
import de.getdate.backend.data.Specification;

public class SearchRequest {
  private List<Specification> specifications;

  private int page;
  private int pageSize;

  private Specification[] specificationsArray;

  public SearchRequest(List<Specification> specifications, int page, int pageSize) {
    this.specifications = specifications;
    this.page = page;
    this.pageSize = pageSize;
  }

  public List<Specification> getSpecifications() {
    return specifications;
  }

  public <T> Specification<T>[] getSpecificationsAsArray() {
    if (specificationsArray == null) {
      return specificationsArray = specifications.toArray(Specification[]::new);
    }

    return specificationsArray;
  }

  public int getPage() {
    return page;
  }

  public int getPageSize() {
    return pageSize;
  }

}
