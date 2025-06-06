package br.com.fiap.cash_up_api.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.cash_up_api.controller.TransactionController.TransactionFilter;
import br.com.fiap.cash_up_api.model.Transaction;
import jakarta.persistence.criteria.Predicate;

public class TransactionSpecification {
    public static Specification<Transaction> withFilters(TransactionFilter filter){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.description() != null && !filter.description().isBlank()){
                predicates.add(
                    cb.like(
                        cb.lower(root.get("Description")), "%" + filter.description().toLowerCase() + "%"
                        )
                    );
            }
            
            if(filter.startDate() != null && filter.enDate() != null){ //as duas datas como critério
                predicates.add(
                    cb.between(root.get("date"), filter.startDate(),filter.enDate())
                );
            }

            if(filter.startDate() != null && filter.enDate() == null){ //apenas a data inicial como critério
                predicates.add(cb.equal(root.get("date"), filter.startDate()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
