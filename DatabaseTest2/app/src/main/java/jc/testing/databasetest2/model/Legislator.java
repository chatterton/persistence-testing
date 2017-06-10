package jc.testing.databasetest2.model;

import org.immutables.value.Value;

@Value.Immutable
public interface Legislator {

    String id();
    String name();
    Party party();
    String religion();
    int termCount();

    enum Party {
        DEMOCRAT,
        REPUBLICAN,
        OTHER
    }

}
