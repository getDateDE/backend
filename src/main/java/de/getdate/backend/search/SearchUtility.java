package de.getdate.backend.search;

import java.util.List;
import java.util.function.Function;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import de.getdate.backend.data.BaseEntity;
import de.getdate.backend.data.CrudRepository;
import de.getdate.backend.data.Specification;
import de.getdate.backend.data.StringId;
import de.getdate.backend.dto.CollectionDto;

public class SearchUtility {
  private static final SearchRequestFactory searchRequestFactory = new DefaultSearchRequestFactory();

  public static <T> List<T> getBySearchRequest(CrudRepository<?, ?> repository, SearchRequest searchRequest) {
    Specification[] specifications = searchRequest.getSpecificationsAsArray();
    return repository.findAll(searchRequest.getPage(), searchRequest.getPageSize(),
        specifications);
  }

  public static <T extends BaseEntity<?>> Response getSpecificResponse(CrudRepository<T, ?> repository, Function<List<T>, List<?>> dtoFunction, SearchParams searchParams) {
    var searchRequest = searchRequestFactory.create(searchParams);
    var entities = SearchUtility.<T>getBySearchRequest(repository, searchRequest);
    var entityDtos = dtoFunction.apply(entities);

    var specifications = searchRequest.<T>getSpecificationsAsArray();
    var collectionData = new CollectionDto(entityDtos, repository.count(specifications));

    return Response.ok(collectionData).build();
  }

  public static <T extends BaseEntity<ID>, ID> Response getSingleResponse(CrudRepository<T, ID> repository, Function<T, ?> dtoFunction, ID id) {
    var entity = repository.findById(id);
    return entity != null ? Response.ok(dtoFunction.apply(entity)).build()
        : Response.status(Status.NOT_FOUND).build();
  }

  public static <T extends BaseEntity<?>> Response getAllResponse(CrudRepository<T, ?> repository, Function<List<T>, List<?>> dtoFunction, SearchParams searchParams) {
    var entities = repository.findAll(searchParams.getPage(), searchParams.getPageSize());
    var collectionData = new CollectionDto(dtoFunction.apply(entities), repository.count());

    return Response.ok(collectionData).build();
  }

}
