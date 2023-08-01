package com.jtang.springboot.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

import com.jtang.springboot.biz.entities.*;
import com.jtang.springboot.biz.repo.*;
import com.jtang.springboot.biz.service.BizExpenseReportService;
import com.jtang.springboot.biz.service.ReferenceDataProvider;
import com.jtang.springboot.biz.service.impl.DefaultReferenceDataProvider;
import com.jtang.springboot.biz.service.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jtang.springboot.biz.service.FileProcessorService;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class BizExpenseReportApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReferenceDataProvider.class);
	@MockBean
	private BizExpenseAccountRepository accRepo;
	@MockBean
	private BizExpenseBusinessRepository bizRepo;
	@MockBean
	private BizExpenseCategoryRepository catRepo;
	@MockBean
	private BizExpenseCatToAccRepository catToAccRepo;
	@MockBean
	private BizExpenseTaxSeasonRepository taxRepo;
	@MockBean
	private BizExpenseTransactionRepository transRepo;
	@Autowired
	private ReferenceDataProvider rdp;
	@Autowired
	private FileProcessorService service;
	@Autowired
	private BizExpenseReportService bizExpenseReportService;

	List<Account> accounts = TestDataGenerator.createMockAccounts();
	List<Business> businesses = TestDataGenerator.createMockBusinesses();
	List<Category> categories = TestDataGenerator.createMockCategories();
	File file = new File("src/test/resources/sheet.xlsx");

	@Test
	void testExcelWithFile() {
		when(accRepo.findAll()).thenReturn(accounts);
		when(bizRepo.findAll()).thenReturn(businesses);
		when(catRepo.findAll()).thenReturn(categories);
		rdp.init();

		List<Transaction> transactions = service.readTransactions(file);
		System.out.println();
		LOGGER.info("Transaction size: {}", transactions.size());
		assertEquals(transactions.size(), 2);
	}

	@Test
	void testExcelWithStream() throws FileNotFoundException {
		when(accRepo.findAll()).thenReturn(accounts);
		when(bizRepo.findAll()).thenReturn(businesses);
		when(catRepo.findAll()).thenReturn(categories);
		rdp.init();

		List<Transaction> transactions = service.readTransactions(new FileInputStream(file));
		System.out.println();
		LOGGER.info("Transaction size: {}", transactions.size());
		assertEquals(transactions.size(), 2);
	}


	@Test
	void testRDPAccount() {
		when(accRepo.findAll()).thenReturn(accounts);
		rdp.init();
		assertEquals(rdp.getAccountFromId(1).getName(), "Utilities");
		assertEquals(rdp.getAccountFromName("Account2").getDescription(), "he he he haw");
	}

	@Test
	void testRDPBusiness() {
		when(bizRepo.findAll()).thenReturn(businesses);
		rdp.init();
		assertEquals(rdp.getBusinessFromId(1).getName(), "Financial Service");
		assertEquals(rdp.getBusinessFromName("153 Orange").getDescription(), "more money");
		assertNull(rdp.getBusinessFromId(4));
	}

	@Test
	void testRDPCategory() {
		when(catRepo.findAll()).thenReturn(categories);
		rdp.init();
		assertEquals(rdp.getCategoryFromId(1).getName(), "Utilities");
		assertEquals(rdp.getCategoryFromName("Category2").getDescription(), "cat2");
	}

	@Test
	void testSummary() throws ParseException {
		when(accRepo.findAll()).thenReturn(TestDataGenerator.createMockAccounts());
		when(bizRepo.findAll()).thenReturn(TestDataGenerator.createMockBusinesses());
		when(catRepo.findAll()).thenReturn(TestDataGenerator.createMockCategories());
		when(transRepo.findAll()).thenReturn(TestDataGenerator.createMockTransactions(false));
		when(taxRepo.findAll()).thenReturn(TestDataGenerator.createMockTaxSeasons());
		rdp.init();
		ExpenseSummary summary = bizExpenseReportService.getSummaryTable(1);
		System.out.println(summary);
		assertEquals(summary.getSummary().keySet().size(), rdp.getAccounts(1).size());
		assertEquals(summary.getSummary().get(rdp.getAccountFromName("Utilities")).
				get(rdp.getBusinessFromName("153 Orange")), 26.03);
		assertEquals(summary.getSummary().get(rdp.getAccountFromName("Utilities")).
				get(rdp.getBusinessFromName("Financial Service")), 113.0);
	}

	//save one record
}
