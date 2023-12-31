package com.jtang.springboot.biz.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//unique ID (incremental)
//Source (BOFA, costco)
//Date
//Description (transaction description)
//Amount ($)
//Adjusted Amount
//Category (FK)
//Business Unit (FK)
//Account (FK)
//Notes (personal notes)
//Workbook id/tax season

@Entity
@Table(name="transactions")
public class Transaction { // each row in the rawdata table
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transaction_id")
	private int id;
	
	@Column(name="source")
	private String source;
	
	@Column(name="date")
	private Date date;

	@Column(name="description")
	private String description;

	@Column(name="amount")
	private Double amount;
	
	@Column(name="adjusted_amount")
	private Double adjustedAmount;

	@Column(name="applied_amount")
	private Double appliedAmount;

	@Column(name="category_id")
	private int categoryId;
	
	@Column(name="business_id")
	private int businessId;
	
	@Column(name="account_id")
	private int accountId;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="tax_season_id")
	private int taxSeasonId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getAdjustedAmount() {
		return adjustedAmount;
	}
	public void setAdjustedAmount(Double adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}
	public Double getAppliedAmount() {
		return appliedAmount;
	}
	public void setAppliedAmount(Double appliedAmount) {
		this.appliedAmount = appliedAmount;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getBusinessId() {
		return businessId;
	}
	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getTaxSeasonId() {
		return taxSeasonId;
	}
	public void setTaxSeasonId(int taxSeason) {
		this.taxSeasonId = taxSeason;
	}

	@Override
	public String toString() {
		return "Transaction{" +
				"id=" + id +
				", source='" + source + '\'' +
				", date=" + date +
				", description='" + description + '\'' +
				", amount=" + amount +
				", adjustedAmount=" + adjustedAmount +
				", appliedAmount=" + appliedAmount +
				", categoryId=" + categoryId +
				", businessId=" + businessId +
				", accountId=" + accountId +
				", notes='" + notes + '\'' +
				", taxSeasonId=" + taxSeasonId +
				'}';
	}
}
