package de.getdate.backend.data.jpa;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import de.getdate.backend.data.EqualsUtil;
import de.getdate.backend.data.Specification;

public class ComparisonSpecification<T> implements Specification<T> {
  public enum ComparisonType {
    STARTS_WITH, EQUALS, NOT_EQUALS, GREATER_THAN, GREATER_EQUALS_THAN, LESS_THAN, LESS_EQUALS_THAN;
  }

  private static final Pattern PATH_SPLIT_PATTERN = Pattern.compile("\\.");

  // @formatter:off
  private static final List<ComparisonType> NUMBER_ONLY_COMPARISON_TYPES = Collections.unmodifiableList(Arrays.asList(
    ComparisonType.GREATER_THAN, ComparisonType.GREATER_EQUALS_THAN,
    ComparisonType.LESS_THAN, ComparisonType.LESS_EQUALS_THAN
  ));
  // @formatter:on

  private ComparisonType comparisonType;
  private String columnName;
  private Object expected;

  public ComparisonSpecification(ComparisonType comparisonType, String columnName,
      Object excepted) {
    if (!isValidComparisonOperation(comparisonType, excepted)) {
      throw new RuntimeException("The comparison type '" + comparisonType
          + "' is not supported for the type '" + excepted.getClass().getName() + "'");
    }

    this.comparisonType = comparisonType;
    this.columnName = columnName;
    this.expected = excepted;
  }

  private boolean isValidComparisonOperation(ComparisonType comparisonType, Object object) {
    if (isBadNumberComparison(object, comparisonType)) {
      return false;
    }

    return true;
  }

  private boolean isBadNumberComparison(Object object, ComparisonType comparisonType) {
    return !(object instanceof Number) && NUMBER_ONLY_COMPARISON_TYPES.contains(comparisonType);
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder) {
    switch (comparisonType) {
      case STARTS_WITH:
        return criteriaBuilder.like(getPath(root, columnName), expected.toString() + "%");
      case EQUALS:
        Path<?> path = getPath(root, columnName);
        fixExpectedObject(path);
        return criteriaBuilder.equal(path, expected);
      case NOT_EQUALS:
        path = getPath(root, columnName);
        fixExpectedObject(path);
        return criteriaBuilder.notEqual(path, expected);
      case GREATER_THAN:
        return criteriaBuilder.gt(getPath(root, columnName), (Number) expected);
      case GREATER_EQUALS_THAN:
        return criteriaBuilder.ge(getPath(root, columnName), (Number) expected);
      case LESS_THAN:
        return criteriaBuilder.lt(getPath(root, columnName), (Number) expected);
      case LESS_EQUALS_THAN:
        return criteriaBuilder.le(getPath(root, columnName), (Number) expected);
      default:
        return criteriaBuilder.and();
    }
  }

  private void fixExpectedObject(Path path) {
    if (isEnumPath(path)) {
      expected = Enum.valueOf(path.getJavaType(), expected.toString().toUpperCase());
    }
  }

  private <R> Path<R> getPath(Root<T> root, String attributeName) {
    Path path = root;

    String[] pathParts = PATH_SPLIT_PATTERN.split(attributeName);
    for (String part : pathParts) {
      path = path.get(part);
      if (isSimpleValueObjectPath(path) && !isValidExpectedType(path)) {
        path = path.get(getSimpleValueObjectAttribute(path));
      }
    }
    return path;
  }

  private boolean isSimpleValueObjectPath(Path<?> path) {
    return path.getJavaType().isAnnotationPresent(SimpleValueObject.class);
  }

  private boolean isValidExpectedType(Path<?> path) {
    return path.getJavaType().equals(expected.getClass());
  }

  private boolean isEnumPath(Path<?> path) {
    return path.getJavaType().isEnum();
  }

  private String getSimpleValueObjectAttribute(Path<?> path) {
    SimpleValueObject simpleValueObject =
        (SimpleValueObject) path.getJavaType().getAnnotation(SimpleValueObject.class);
    return simpleValueObject.value();
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof ComparisonSpecification)) {
      return false;
    }
    ComparisonSpecification<?> other = (ComparisonSpecification<?>) object;

    if (!EqualsUtil.compareEquals(expected, other.expected)) {
      return false;
    }

    return comparisonType.equals(other.comparisonType) && columnName.equals(other.columnName);
  }

  @Override
  public String toString() {
    return "ComparisonSpecification [columnName=" + columnName + ", comparisonType="
        + comparisonType + ", expected=" + expected + "]";
  }

}
