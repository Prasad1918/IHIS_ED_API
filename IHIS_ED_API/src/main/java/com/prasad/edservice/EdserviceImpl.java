package com.prasad.edservice;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.prasad.binding.ElizibilityBinding;
import com.prasad.entiry.CitizenAppEntity;
import com.prasad.entiry.CotriggerEntity;
import com.prasad.entiry.DCIncomEntity;
import com.prasad.entiry.DcCaseEntity;
import com.prasad.entiry.DcEducationEntity;
import com.prasad.entiry.DcKidEntity;
import com.prasad.entiry.EDEligibleDtlsEntity;
import com.prasad.entiry.PlanEntity;
import com.prasad.repository.CitizenAppRepository;
import com.prasad.repository.CotriggerRepository;
import com.prasad.repository.DcCaseRepository;
import com.prasad.repository.DcEducationRepository;
import com.prasad.repository.DcIncomeRepository;
import com.prasad.repository.DcKidsRepository;
import com.prasad.repository.DcPlanRepository;
import com.prasad.repository.EdEligibilityRepository;

public class EdserviceImpl implements EdService {

	@Autowired
	DcCaseRepository dcCaseRepository;
	@Autowired
	DcPlanRepository dcPlanRepo;
	@Autowired
	DcIncomeRepository dcIncome;
	@Autowired
	private DcKidsRepository dckidsRepo;
	@Autowired
	private CitizenAppRepository citizenAppRepo;
	@Autowired
	DcEducationRepository dcEducationRepo;
	@Autowired
	CotriggerRepository cotriggers;
	@Autowired
	EdEligibilityRepository eligiblerepo;
//\
	ElizibilityBinding response = new ElizibilityBinding();

	@Override
	public ElizibilityBinding determineEligiblity(Long CaseNum) {
		// TODO Auto-generated method stub
		Integer planid = null;
		String planName = null;
		Integer appId=null;
		Optional<DcCaseEntity> dcCaeEntity = dcCaseRepository.findById(CaseNum);
		if (dcCaeEntity.isPresent()) {
			planid = dcCaeEntity.get().getPlanId();
			appId=	dcCaeEntity.get().getAppId();
			

		}
		else {
			return null;
		}
		Optional<PlanEntity> planentity = dcPlanRepo.findById(planid);

		if (planentity.isPresent()) {
			planName = planentity.get().getPlanName();
		}
		DCIncomEntity incomeentity = dcIncome.findByCaseNum(CaseNum);

		List<DcKidEntity> dckidsentity = dckidsRepo.findByCaseNum(CaseNum);

		dcIncome.findById(CaseNum);
		if ("SNAP".equals(planName)) {
			if (incomeentity.getSalryIncome() > 300) {
				response.setDenialResion("High Income");
				response.setPlanStatus("Denied");
			}

		} else if ("CCAP".equals(planName)) {
			 boolean ageFlage =true;
			 boolean nokidsFlag=false;
			if (!dckidsentity.isEmpty()) {

				/*
				 * dckidsentity.forEach(kids->{
				 * 
				 * });
				 */
          
				for (DcKidEntity kids : dckidsentity) {
					LocalDate dob = kids.getDob();
					LocalDate now = LocalDate.now();
					//Period 
				Period dataDiff=	Period.between(dob, now);
			Integer yers=	dataDiff.getYears();
			if(yers>16) {
				
				//response.setPlanStatus("Denied");
				//response.setDenialResion("Child age is Higer");
				ageFlage=false;
			}
				}
			}
			else {
				response.setDenialResion("No kids");
				nokidsFlag=true;
			}
			if(incomeentity.getSalryIncome()>300 && !ageFlage) {
				response.setDenialResion("Income High and age is grester then 16");
			}
			if(incomeentity.getSalryIncome()>300) {
				response.setDenialResion("Income High");
			}
			if(!ageFlage) {
				response.setDenialResion("Age is >16");
			}
			

		} else if ("Medicaid".equals(planName)) {
			
		Double salaryIncome=	incomeentity.getSalryIncome();
		Double propertyIncome=	incomeentity.getPropertyIncome();
		Double rentIncome=	incomeentity.getRentIncome();
		
		if(salaryIncome>300) {
			response.setDenialResion("Higt salary Income");
			
		}
if(propertyIncome>0) {
	response.setDenialResion("Property iincome high");
}
if(rentIncome>0) {
	
	response.setDenialResion("Rental incomes is hight");
	
}
if(propertyIncome>0&&rentIncome>0) {
	
	response.setDenialResion("Properts and Resntal income is Available");
}
		} else if ("Medicare".equals(planName)) {
			
	Optional<CitizenAppEntity>	citizenEntity=	citizenAppRepo.findById(appId);
	            
CitizenAppEntity citizenapp=	citizenEntity.get();

      LocalDate dobcitizen=        citizenapp.getDob();
      
    LocalDate today= LocalDate.now();
    
 Period period=   Period.between(dobcitizen, today);
int age= period.getYears();
 if(age <65) {
	 
	 response.setDenialResion("Age is less then 60 yers ");
 }
		} else if ("RIW".equals(planName)) {

		DcEducationEntity eduEntity=	dcEducationRepo.findByCaseNum(CaseNum);
	Integer yer=	eduEntity.getGradutionYear();
	 if(yer<=0) {
		 response.setDenialResion("Not Gratuated");
	 }
	 if(incomeentity.getSalryIncome()>0) {
		 response.setDenialResion("Already Employed");
	 }
		}
		
		response.setPlanName(planName);
		if(response.getDenialResion()!=null) {
			response.setPlanStatus("DENIED");
		}
		else {
			response.setPlanStatus("APPROVED");
			response.setPlanStartDate(LocalDate.now().plusDays(1));
			response.setPlanEndDate(LocalDate.now().plusMonths(3));
			response.setBenefitAmout(356.00);
		}
		
		EDEligibleDtlsEntity eliEntity= new EDEligibleDtlsEntity();
		BeanUtils.copyProperties(CaseNum, eliEntity);
		
		eligiblerepo.save(eliEntity);
		
		  CotriggerEntity trigger=new CotriggerEntity();
		  trigger.setNumber(CaseNum);
		  trigger.setTrgStatus("Pending");
		  cotriggers.save(trigger);
		
		
		return response;
	}

}
