package org.fastddd.spring.transaction;

import org.fastddd.core.session.DefaultTransactionalSessionFactory;
import org.fastddd.core.session.TransactionalSessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;


public class EnhancedDataSourceTransactionManager extends DataSourceTransactionManager {

    private TransactionalSessionFactory sessionFactory = DefaultTransactionalSessionFactory.get();

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        super.doBegin(transaction, definition);
        sessionFactory.registerSession(true);
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) {
        sessionFactory.requireSession().commit();
        super.doCommit(status);
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) {
        sessionFactory.requireSession().rollback();
        super.doRollback(status);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        try {
            super.doCleanupAfterCompletion(transaction);
            sessionFactory.requireSession().cleanupAfterCompletion();
        } finally {
            sessionFactory.closeSession();
        }
    }
}
