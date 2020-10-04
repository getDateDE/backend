package de.getdate.backend.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import de.getdate.backend.data.Specification;
import de.getdate.backend.data.jpa.ComparisonSpecification;
import de.getdate.backend.data.jpa.ComparisonSpecification.ComparisonType;

// @formatter:off
/**
 * Simple implementation of a {@link SearchRequestFactory} that parses queries like<br />
 * - key='value'
 * - key!=1
 * - key<=1.5
 * - key.child>1.5
 * - key.child='abc'
 */
// @formatter:on
public class DefaultSearchRequestFactory implements SearchRequestFactory {
  static final Pattern SEARCH_QUERY_PATTERN = Pattern
      .compile("([\\w.]+)([^']+?)('(?:.*?)'|[+-]?(?:[0-9]*[.])?[0-9]+)(?:;|$)");

  @Override
  public SearchRequest create(SearchParams searchParams) {
    var specifications = createSpecifications(searchParams);
    return new SearchRequest(specifications, searchParams.getPage(), searchParams.getPageSize());
  }

  private List<Specification> createSpecifications(SearchParams searchParams) {
    if (!StringUtils.isBlank(searchParams.getQuery())) {
      var specifications = new ArrayList<Specification>();
      Matcher matcher = SEARCH_QUERY_PATTERN.matcher(searchParams.getQuery());
      while (hasNextSpecification(matcher)) {
        specifications.add(createSpecifcation(matcher));
      }

      if (hasInvalidPairAmount(searchParams, matcher)) {
        throw new SearchRequestCreationException("One of your search pairs is incorrect.");
      }

      return specifications;
    }

    return Collections.emptyList();
  }

  private boolean hasNextSpecification(Matcher matcher) {
    return matcher.find();
  }

  private ComparisonSpecification createSpecifcation(Matcher matcher) {
    String key = extractKey(matcher);
    ComparisonType comparisonType = extractComparisonType(matcher);
    Object value = extractValue(matcher);
    return new ComparisonSpecification(comparisonType, key, value);
  }

  private boolean hasInvalidPairAmount(SearchParams searchParams, Matcher matcher) {
    int lastValidCharacterIndex = matcher.regionEnd();
    int lastQueryStringCharacterIndex = searchParams.getQuery().length();
    return lastValidCharacterIndex != lastQueryStringCharacterIndex;
  }

  private String extractKey(Matcher matcher) {
    return matcher.group(1);
  }

  // @formatter:off
  private ComparisonType extractComparisonType(Matcher matcher) {
    String comparisonType = matcher.group(2);
    switch (comparisonType) {
      case "=": return ComparisonType.EQUALS;
      case "!=": return ComparisonType.NOT_EQUALS;
      case ">": return ComparisonType.GREATER_THAN;
      case ">=": return ComparisonType.GREATER_EQUALS_THAN;
      case "<": return ComparisonType.LESS_THAN;
      case "<=": return ComparisonType.LESS_EQUALS_THAN;
      case "=>": return ComparisonType.STARTS_WITH;
      default: throw new SearchRequestCreationException("Comparison type not supported: " + comparisonType);
    }
  }
  // @formatter:on

  private Object extractValue(Matcher matcher) {
    String value = matcher.group(3);
    if (isTextValue(value)) {
      return value.substring(1, value.lastIndexOf("'"));
    }

    return NumberUtils.createBigDecimal(value);
  }

  private boolean isTextValue(String value) {
    return value.startsWith("'") && value.endsWith("'");
  }

}
