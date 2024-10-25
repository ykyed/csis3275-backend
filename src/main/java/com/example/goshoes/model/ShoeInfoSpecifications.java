package com.example.goshoes.model;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;

public class ShoeInfoSpecifications {

	public static Specification<ShoeInfo> hasBrand(List<String> brands) {
        return (root, query, criteriaBuilder) -> root.get("brand").in(brands);
    }

    public static Specification<ShoeInfo> hasStyle(List<String> styles) {
        return (root, query, criteriaBuilder) -> root.get("style").in(styles);
    }

    public static Specification<ShoeInfo> hasColor(List<String> colors) {
        return (root, query, criteriaBuilder) -> root.get("color").in(colors);
    }

    public static Specification<ShoeInfo> hasSizeAndQuantity(List<Double> sizes) {
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> sizeJoin = root.join("sizes");
            return criteriaBuilder.and(
                    sizeJoin.get("size").in(sizes),
                    criteriaBuilder.greaterThan(sizeJoin.get("quantity"), 1)
            );
        };
    }
}
