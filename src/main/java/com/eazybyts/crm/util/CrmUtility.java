package com.eazybyts.crm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.eazybyts.crm.dto.AccountDto;
import com.eazybyts.crm.dto.AppUserDto;
import com.eazybyts.crm.dto.ContactDto;
import com.eazybyts.crm.dto.VendorDto;
import com.eazybyts.crm.exception.InvalidInputException;
import com.eazybyts.crm.service.AccountService;
import com.eazybyts.crm.service.AppUserService;
import com.eazybyts.crm.service.ContactService;
import com.eazybyts.crm.service.VendorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CrmUtility {

	@Autowired
	private AppUserService appUserService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private ContactService contactService;
	
	private static final String emailRegx = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	private static final String amtRegex = "^([1-9]{1}[0-9]{0,2}(,?[0-9]{3})*|0)(\\.[0-9]{1,2})?$";

	Pattern emailPattern = Pattern.compile(emailRegx);
	Pattern amtPattern = Pattern.compile(amtRegex);
	
	public void validateNewAdminRequest(AppUserDto adminDto)	{
		List<String> errors = new ArrayList<>();
		if(!StringUtils.hasText(adminDto.getFullName()))	{
			errors.add("Full name is required!");
		}
		if(!StringUtils.hasText(adminDto.getEmailId()))	{
			errors.add("Email id is required!");
		}
		else	{
			if(!emailPattern.matcher(adminDto.getEmailId()).matches())	{
				errors.add("Email id is invalid!");
			}
			else	{
				if(appUserService.doesEmailExists(adminDto.getEmailId()))	{
					errors.add("Email id already exists!");
				}
			}
		}
		if(!StringUtils.hasText(adminDto.getMobileNo()))	{
			errors.add("Mobile no is required!");
		}
		String pwd = adminDto.getPassword();
		String cfmPwd = adminDto.getConfirmPassword();
		if(!StringUtils.hasText(pwd))	{
			errors.add("Password is required!");
		}
		if(!StringUtils.hasText(cfmPwd))	{
			errors.add("Confirm password is required!");
		}
		if(StringUtils.hasText(pwd) && StringUtils.hasText(cfmPwd))	{
			if(!pwd.equals(cfmPwd))	{
				errors.add("Password and confirm password are not matching!");
			}
		}
		if(!CollectionUtils.isEmpty(errors))	{
			log.info("errors list-----------> ",errors);
			throw new InvalidInputException(errors);
		}
	}
	
	public void validateNewUserRequest(AppUserDto userDto)	{
		List<String> errors = new ArrayList<>();
		if(!StringUtils.hasText(userDto.getFirstName()))	{
			errors.add("First name is required");
		}
		if(!StringUtils.hasText(userDto.getLastName()))	{
			errors.add("Last name is required");
		}
		if(!StringUtils.hasText(userDto.getMobileNo()))	{
			errors.add("Mobile no is required");
		}
		if(!StringUtils.hasText(userDto.getEmailId()))	{
			errors.add("Email id is required");
		}
		else	{
			if(!emailPattern.matcher(userDto.getEmailId()).matches())	{
				errors.add("Email id is invalid");
			}
			else	{
				if(appUserService.doesEmailExists(userDto.getEmailId()))	{
					errors.add("Email id already exists!");
				}
			}
		}
		if(!StringUtils.hasText(userDto.getPassword()))	{
			errors.add("Password is required");
		}
		if(!CollectionUtils.isEmpty(errors))	{
			throw new InvalidInputException(errors);
		}
	}
	
	public void validateNewAccountRequest(AccountDto accDto)	{
		List<String> errors = new ArrayList<>();
		if(!StringUtils.hasText(accDto.getAccountName()))	{
			errors.add("Account name is required!");
		}
		else	{
			if(accountService.doesAccountNameExists(accDto.getAccountName()))	{
				errors.add("Account name already exists!");
			}
		}
		log.info("amount--------> {}",accDto.getAnnualRevenue());
		if(StringUtils.hasText(accDto.getAnnualRevenue()) &&
				!amtPattern.matcher(accDto.getAnnualRevenue()).matches())	{
			errors.add("Annual revenue is invalid!");
		}
		if(!CollectionUtils.isEmpty(errors))	{
			throw new InvalidInputException(errors);
		}
		
	}
	
	public void validateNewVendorRequest(VendorDto vendorDto)	{
		List<String> errors = new ArrayList<>();
		if(!StringUtils.hasText(vendorDto.getVendorName()))	{
			errors.add("Vendor name cannot be empty");
		}
		else	{
			if(vendorService.doesVendorNameExists(vendorDto.getVendorName()))	{
				errors.add("Vendor name already exists!");
			}
		}
		if(!StringUtils.hasText(vendorDto.getEmail()))	{
			errors.add("Email cannot be empty");
		}
		else	{
			if(!emailPattern.matcher(vendorDto.getEmail()).matches())	{
				errors.add("Email is not valid!");
			}
			else	{
				if(vendorService.doesVendorEmailExists(vendorDto.getEmail()))	{
					errors.add("Email id already exists!");
				}
			}
		}
		if(!CollectionUtils.isEmpty(errors))	{
			throw new InvalidInputException(errors);
		}
	}
	
	public void validateNewContactRequest(ContactDto contactDto)	{
		List<String> errors = new ArrayList<>();
		if(!StringUtils.hasText(contactDto.getFirstName()))	{
			errors.add("First name cannot be empty");
		}
		if(!StringUtils.hasText(contactDto.getLastName()))	{
			errors.add("Last name cannot be empty!");
		}
		if(!StringUtils.hasText(contactDto.getMobile()))	{
			errors.add("Mobile no cannot be empty");
		}
		if(!StringUtils.hasText(contactDto.getEmail()))	{
			errors.add("Email id cannot be empty");
		}
		else	{
			if(!emailPattern.matcher(contactDto.getEmail()).matches())	{
				errors.add("Email id is not valid!");
			}
			else	{
				if(contactService.doesEmailIdExists(contactDto.getEmail()))	{
					errors.add("Email id already exists!");
				}
			}
		}
		if(!CollectionUtils.isEmpty(errors))	{
			throw new InvalidInputException(errors);
		}
	}
}
