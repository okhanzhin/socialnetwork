package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:dao-context.xml", "classpath*:test-context.xml"})
public class RequestDaoTest extends PrepareDaoTest {
    private static final String GROUP_REQUEST_TYPE = "group";
    private static final String USER_REQUEST_TYPE = "user";

    @Autowired
    private RequestDao requestDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private GroupDao groupDao;

    @Test
    public void create() {
        Account account = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        Group group = groupDao.create(new Group("First Group"));
        Request exceptedRequest = new Request(account.getAccountID(), group.getGroupID(), GROUP_REQUEST_TYPE, Request.Status.UNCONFIRMED);
        Request actualRequest = requestDao.create(exceptedRequest);
        exceptedRequest.setRequestID(actualRequest.getRequestID());

        assertEquals(exceptedRequest, actualRequest);
    }

    @Test
    public void getByCreatorRecipientID() {
        Account account = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        Group group = groupDao.create(new Group("First Group"));
        Request exceptedRequest = requestDao.create(new Request(account.getAccountID(), group.getGroupID(), GROUP_REQUEST_TYPE, Request.Status.UNCONFIRMED));
        Request actualRequest = requestDao.getByCreatorRecipientID(account.getAccountID(), group.getGroupID());

        assertEquals(exceptedRequest, actualRequest);
    }

    @Test
    public void update() {
        Account account = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        Group group = groupDao.create(new Group("First Group"));
        Request exceptedRequest = requestDao.create(new Request(account.getAccountID(), group.getGroupID(), GROUP_REQUEST_TYPE, Request.Status.UNCONFIRMED));
        exceptedRequest.setStatus(Request.Status.ACCEPTED);
        requestDao.update(exceptedRequest);

        assertEquals(exceptedRequest, requestDao.getByCreatorRecipientID(account.getAccountID(), group.getGroupID()));
    }

    @Test
    public void getGroupRequestsList() {
        List<Account> accounts = new ArrayList<>();
        Account account1 = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        accounts.add(account1);
        Account account2 = accountDao.create(new Account("Sidorov", "Sidr", "sidorov@gmail.com", "SidorovPas", LocalDate.now()));
        accounts.add(account2);
        Account account3 = accountDao.create(new Account("Ivanov", "Ivan", "ivanov@gmail.com", "IvanovPas", LocalDate.now()));
        accounts.add(account3);
        Group group = groupDao.create(new Group("First Group"));
        ArrayList<Request> exceptedRequests = new ArrayList<>();
        for (Account account : accounts) {
            Request request = requestDao.create(new Request(account.getAccountID(), group.getGroupID(), GROUP_REQUEST_TYPE, Request.Status.UNCONFIRMED));
            exceptedRequests.add(request);
        }

        assertEquals(exceptedRequests, requestDao.getGroupRequestsList(group));
    }

    @Test
    public void getPendingRequestsList() {
        List<Account> accounts = new ArrayList<>();
        Account creatorAccount = accountDao.create(new Account("Khanzhin", "Oleg", "khanzhin@gmail.com", "Pas", LocalDate.now()));
        Account account1 = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        accounts.add(account1);
        Account account2 = accountDao.create(new Account("Sidorov", "Sidr", "sidorov@gmail.com", "SidorovPas", LocalDate.now()));
        accounts.add(account2);
        Account account3 = accountDao.create(new Account("Ivanov", "Ivan", "ivanov@gmail.com", "IvanovPas", LocalDate.now()));
        accounts.add(account3);

        ArrayList<Request> exceptedRequests = new ArrayList<>();
        for (Account account : accounts) {
            Request request = requestDao.create(new Request(account.getAccountID(), creatorAccount.getAccountID(), USER_REQUEST_TYPE, Request.Status.UNCONFIRMED));
            exceptedRequests.add(request);
        }

        assertEquals(exceptedRequests, requestDao.getPendingRequestsList(creatorAccount));
    }

    @Test
    public void getOutgoingRequestsList() {
        List<Account> accounts = new ArrayList<>();
        Account creatorAccount = accountDao.create(new Account("Khanzhin", "Oleg", "khanzhin@gmail.com", "Pas", LocalDate.now()));
        Account account1 = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        accounts.add(account1);
        Account account2 = accountDao.create(new Account("Sidorov", "Sidr", "sidorov@gmail.com", "SidorovPas", LocalDate.now()));
        accounts.add(account2);
        Account account3 = accountDao.create(new Account("Ivanov", "Ivan", "ivanov@gmail.com", "IvanovPas", LocalDate.now()));
        accounts.add(account3);

        ArrayList<Request> exceptedRequests = new ArrayList<>();
        for (Account account : accounts) {
            Request request = requestDao.create(new Request(creatorAccount.getAccountID(), account.getAccountID(), USER_REQUEST_TYPE, Request.Status.UNCONFIRMED));
            exceptedRequests.add(request);
        }

        assertEquals(exceptedRequests, requestDao.getOutgoingRequestsList(creatorAccount));
    }

    @Test
    public void getAcceptedRequestsList() {
        List<Account> accounts = new ArrayList<>();
        Account creatorAccount = accountDao.create(new Account("Khanzhin", "Oleg", "khanzhin@gmail.com", "Pas", LocalDate.now()));
        Account account1 = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        accounts.add(account1);
        Account account2 = accountDao.create(new Account("Sidorov", "Sidr", "sidorov@gmail.com", "SidorovPas", LocalDate.now()));
        accounts.add(account2);
        Account account3 = accountDao.create(new Account("Ivanov", "Ivan", "ivanov@gmail.com", "IvanovPas", LocalDate.now()));
        accounts.add(account3);

        ArrayList<Request> exceptedRequests = new ArrayList<>();
        for (Account account : accounts) {
            Request request = requestDao.create(new Request(creatorAccount.getAccountID(), account.getAccountID(), USER_REQUEST_TYPE, Request.Status.ACCEPTED));
            exceptedRequests.add(request);
        }

        assertEquals(exceptedRequests, requestDao.getAcceptedRequestsList(creatorAccount));
    }
}