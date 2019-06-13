package io.swagger.QueryBuilder.Specifications;

import io.swagger.QueryBuilder.GenericSpecification;
import io.swagger.QueryBuilder.SpecSearchCriteria;
import io.swagger.model.Transaction;

public class TransactionSpecification extends GenericSpecification<Transaction> {

    public TransactionSpecification(SpecSearchCriteria criteria) {
        super(criteria);
        enumConverter();
    }

    private void enumConverter(){
        Object anEnum1 = Transaction.CategoryEnum.fromValue(criteria.getValue().toString());
        Object anEnum2 = Transaction.StatusEnum.fromValue(criteria.getValue().toString());
        if(anEnum1 != null){
            criteria.setValue(anEnum1);
        }else if(anEnum2 != null){
            criteria.setValue(anEnum2);
        }
    }
}
