package nl.Inholland.QueryBuilder.Specifications;

import nl.Inholland.QueryBuilder.GenericSpecification;
import nl.Inholland.QueryBuilder.SpecSearchCriteria;
import nl.Inholland.enumerations.CategoryEnum;
import nl.Inholland.enumerations.StatusEnum;
import nl.Inholland.model.Transactions.Transaction;

public class TransactionSpecification extends GenericSpecification<Transaction> {

    public TransactionSpecification(SpecSearchCriteria criteria) {
        super(criteria);
        enumConverter();
    }

    private void enumConverter(){ // Makes it possible to query enums
        Object anEnum1 = CategoryEnum.fromValue(criteria.getValue().toString());
        Object anEnum2 = StatusEnum.fromValue(criteria.getValue().toString());
        if(anEnum1 != null){
            criteria.setValue(anEnum1);
        }else if(anEnum2 != null){
            criteria.setValue(anEnum2);
        }
    }
}
