package com.wiktorski.mybudget;

import com.wiktorski.mybudget.model.DTO.ReportDTO;
import com.wiktorski.mybudget.model.entity.Category;
import com.wiktorski.mybudget.model.entity.Income;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.service.BudgetService;
import com.wiktorski.mybudget.service.PaymentService;
import com.wiktorski.mybudget.service.ReportingService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportingServiceTests {

//    @Mock
    private static PaymentService paymentServiceMock;

//    @Mock
    private static BudgetService budgetServiceMock;

    @InjectMocks
    private static ReportingService reportingService;

    @BeforeClass
    public static void init() {
        paymentServiceMock = Mockito.mock(PaymentService.class);
        when(paymentServiceMock.getPayments(any()))
                .thenReturn(Arrays.asList(
                        Payment.builder().name("test").id(1).price(10).category(new Category("testCat", "#000000", new User())).build(),
                        Payment.builder().name("test2").id(1).price(20).category(new Category("testCat2", "#000001", new User())).build(),
                        Payment.builder().name("test2").id(1).price(15).category(new Category("testCat2", "#000002", new User())).build()));

        budgetServiceMock = Mockito.mock(BudgetService.class);
        when(budgetServiceMock.getIncome(new ReportDTO()))
                .thenReturn(Arrays.asList(
                        Income.builder().id(1).title("income test").user(new User()).value(2000).build(),
                        Income.builder().id(2).title("income test2").user(new User()).value(1000).build(),
                        Income.builder().id(3).title("income test3").user(new User()).value(500).build()));
    }

    @Test
    public void getPaymentsReportTest() {
        Assert.assertEquals(3, paymentServiceMock.getPayments(new ReportDTO()).size());


        ReportDTO returnedReport = reportingService.getPaymentsReport(new ReportDTO());
    }


}
