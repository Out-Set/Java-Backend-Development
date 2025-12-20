package com.app1.entity;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@JsonPropertyOrder({"Segment" , "Version" , "MemberReference" , "EnquiryMemberID" ,"SubjectReturnCode","EnquiryControlNumber","DateProcessed","TimeProcessed","name",
	"DateofBirth","gender","ID_Type","ID_Number","Issue_Date","Expiration_Date","Enr_Enq_id","Telephone","Tel_Extn","Tel_Type","Enr_Enq_pt","email_id",
	"account_type_empseg","datereported_employment","occupation_code","income","net_gross_indicator","monthly_annual_indicator","DateofEntry_ErrorCode_em","ErrorCode_em",
	"Date_CIBILRemarksCode_em","CIBILRemarksCode_em","DisputeRemarksCode_em","DisputeRemarksCode1_em","DisputeRemarksCode2_em","Acct_number","scorename","ScoreCardName",
	"ScoreCardVersion","ScoreDate","score","ExclusionCode1","ExclusionCode2","ExclusionCode3","ExclusionCode4","ExclusionCode5","ExclusionCode6","ExclusionCode7",
	"ExclusionCode8","ExclusionCode9","ExclusionCode10","ReasonCode1","ReasonCode2","ReasonCode3","ReasonCode4","ReasonCode5","ErrorCode","address","StateCode","PINCode",
	"address_cat","res_code","DateReported_address","MemberShortName","Enr_Enq_pa","ReportingMemberShortName","AccountNumber","AccountType","Live/Closed","OwnershipIndicator",
	"DateOpenedDisbursed","DateofLastPayment","DateClosed","DateReported_trades","HighCreditSanctionedAmount","CurrentBalance","AmountOverdue","Paymt_hst_01","Paymt_hst_02",
	"Paymt_hst_03","Paymt_hst_04","Paymt_hst_05","Paymt_hst_06","Paymt_hst_07","Paymt_hst_08","Paymt_hst_09","Paymt_hst_10","Paymt_hst_11,Paymt_hst_12","Paymt_hst_13",
	"Paymt_hst_14","Paymt_hst_15","Paymt_hst_16","Paymt_hst_17","Paymt_hst_18","Paymt_hst_19","Paymt_hst_20","Paymt_hst_21","Paymt_hst_22","Paymt_hst_23","Paymt_hst_24",
	"Paymt_hst_25","Paymt_hst_26","Paymt_hst_27","Paymt_hst_28","Paymt_hst_29","Paymt_hst_30","Paymt_hst_31","Paymt_hst_32","Paymt_hst_33","Paymt_hst_34","Paymt_hst_35",
	"Paymt_hst_36","Pay_Hist_Start_Date","pay_hist_end_date","suit_filed_status","Credit_Facility_Status","collateral_type","collateral_value","credit_limit","cash_limit","ROI",
	"tenure","emi_amt","writeoff_amt_tot","writeoff_amt_prin","settlement_amt","paymt_freq","actual_paymt_amt","DateofEntry_ErrorCode_tl","ErrorCode_tl",
	"Date_CIBILRemarksCode_tl","CIBILRemarksCode_tl","DisputeRemarksCode_tl","DisputeRemarksCode1_tl","DisputeRemarksCode2_tl","DateofEnquiry","EnquiringMemberShortName",
	"EnquiryPurpose","EnquiryAmount","DisputeRemarksLine1","DisputeRemarksLine2","DisputeRemarksLine3","DisputeRemarksLine4","DisputeRemarksLine5","DisputeRemarksLine6",
	"Secured/Unsecured"})
@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class FileRowData {

	private String Segment; 
	private String Version;
	private String MemberReference; 
	private String EnquiryMemberID; 
	private String SubjectReturnCode; 
	private String EnquiryControlNumber; 
	private String DateProcessed;
	private String TimeProcessed; 
	private String name; 
	private String DateofBirth;
	private String gender; 
	private String ID_Type;
	private String ID_Number;
	private String Issue_Date; 
	private String Expiration_Date;
	private String Enr_Enq_id;
	private String Telephone; 
	private String Tel_Extn; 
	private String Tel_Type; 
	private String Enr_Enq_pt; 
	private String email_id; 
	private String account_type_empseg; 
	private String datereported_employment; 
	private String occupation_code; 
	private String income; 
	private String net_gross_indicator;
	private String monthly_annual_indicator;
	private String DateofEntry_ErrorCode_em; 
	private String ErrorCode_em;
	private String Date_CIBILRemarksCode_em;
	private String CIBILRemarksCode_em;
	private String DisputeRemarksCode_em;
	private String DisputeRemarksCode1_em;
	private String DisputeRemarksCode2_em;
	private String Acct_number;
	private String scorename; 
	private String ScoreCardName;
	private String ScoreCardVersion;
	private String ScoreDate;
	private String score; 
	private String ExclusionCode1; 
	private String ExclusionCode2; 
	private String ExclusionCode3; 
	private String ExclusionCode4; 
	private String ExclusionCode5;
	private String ExclusionCode6; 
	private String ExclusionCode7; 
	private String ExclusionCode8; 
	private String ExclusionCode9;
	private String ExclusionCode10; 
	private String ReasonCode1; 
	private String ReasonCode2; 
	private String ReasonCode3; 
	private String ReasonCode4;
	private String ReasonCode5;
	private String ErrorCode;
	private String address; 
	private String StateCode; 
	private String PINCode;
	private String address_cat;
	private String res_code;
	private String DateReported_address;
	private String MemberShortName;
	private String Enr_Enq_pa; 
	private String ReportingMemberShortName;
	private String AccountNumber; 
	private String AccountType; 
	private String Live_Closed; 
	private String OwnershipIndicator; 
	private String DateOpenedDisbursed;
	private String DateofLastPayment;
	private String DateClosed; 
	private String DateReported_trades; 
	private String HighCreditSanctionedAmount;
	private String CurrentBalance;
	private String AmountOverdue;
	private String Paymt_hst_01;
	private String Paymt_hst_02; 
	private String Paymt_hst_03;
	private String Paymt_hst_04;
	private String Paymt_hst_05; 
	private String Paymt_hst_06; 
	private String Paymt_hst_07; 
	private String Paymt_hst_08; 
	private String Paymt_hst_09;
	private String Paymt_hst_10;
	private String Paymt_hst_11;
	private String Paymt_hst_12; 
	private String Paymt_hst_13;
	private String Paymt_hst_14; 
	private String Paymt_hst_15; 
	private String Paymt_hst_16; 
	private String Paymt_hst_17;
	private String Paymt_hst_18; 
	private String Paymt_hst_19;
	private String Paymt_hst_20;
	private String Paymt_hst_21; 
	private String Paymt_hst_22; 
	private String Paymt_hst_23; 
	private String Paymt_hst_24;
	private String Paymt_hst_25;
	private String Paymt_hst_26; 
	private String Paymt_hst_27; 
	private String Paymt_hst_28;
	private String Paymt_hst_29;
	private String Paymt_hst_30;
	private String Paymt_hst_31; 
	private String Paymt_hst_32; 
	private String Paymt_hst_33; 
	private String Paymt_hst_34;
	private String Paymt_hst_35;
	private String Paymt_hst_36; 
	private String Pay_Hist_Start_Date; 
	private String pay_hist_end_date; 
	private String suit_filed_status; 
	private String Credit_Facility_Status;
	private String collateral_type; 
	private String collateral_value;
	private String credit_limit;
	private String cash_limit; 
	private String ROI; 
	private String tenure; 
	private String emi_amt;
	private String writeoff_amt_tot; 
	private String writeoff_amt_prin; 
	private String settlement_amt; 
	private String paymt_freq; 
	private String actual_paymt_amt;
	private String DateofEntry_ErrorCode_tl;
	private String ErrorCode_tl;
	private String Date_CIBILRemarksCode_tl; 
	private String CIBILRemarksCode_tl; 
	private String DisputeRemarksCode_tl;
	private String DisputeRemarksCode1_tl;
	private String DisputeRemarksCode2_tl;
	private String DateofEnquiry;
	private String EnquiringMemberShortName;
	private String EnquiryPurpose;
	private String EnquiryAmount; 
	private String DisputeRemarksLine1;
	private String DisputeRemarksLine2; 
	private String DisputeRemarksLine3; 
	private String DisputeRemarksLine4; 
	private String DisputeRemarksLine5; 
	private String DisputeRemarksLine6; 
	private String Secured_Unsecured;
}
