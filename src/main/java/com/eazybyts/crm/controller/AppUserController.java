package com.eazybyts.crm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eazybyts.crm.dto.AccountDto;
import com.eazybyts.crm.dto.ApiResponseDto;
import com.eazybyts.crm.dto.AppUserDto;
import com.eazybyts.crm.dto.CompanyDto;
import com.eazybyts.crm.dto.ContactDto;
import com.eazybyts.crm.dto.SalesDto;
import com.eazybyts.crm.dto.TaskDto;
import com.eazybyts.crm.dto.VendorDto;
import com.eazybyts.crm.enums.UserRoleEnum;
import com.eazybyts.crm.service.AccountService;
import com.eazybyts.crm.service.AppUserService;
import com.eazybyts.crm.service.CompanyService;
import com.eazybyts.crm.service.ContactService;
import com.eazybyts.crm.service.SalesService;
import com.eazybyts.crm.service.TaskService;
import com.eazybyts.crm.service.VendorService;
import com.eazybyts.crm.util.CrmUtility;
import com.eazybyts.crm.util.ThreadLocalUtility;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/crm/user/")
public class AppUserController {
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ContactService contactService;
	@Autowired
	private SalesService salesService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private CrmUtility crmUtility;
	
	@PostMapping("create")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponseDto saveUser(@RequestBody AppUserDto appUserDto)	{
		crmUtility.validateNewUserRequest(appUserDto);
		appUserDto.setParentId((Long)ThreadLocalUtility.get().get("principalId"));
		appUserDto.setUserRole(UserRoleEnum.USER.name());
		return appUserService.createUser(appUserDto);
	}
	
	@PostMapping("find")
	@ResponseStatus(code = HttpStatus.OK)
	public AppUserDto findUser(@RequestBody Map<String, Long> payload)	{
		return appUserService.fetchAppUserDetails(payload.get("userId"));
	}
	
	@PostMapping("findAll")
	@ResponseStatus(code = HttpStatus.OK)
	public List<AppUserDto> findUsersByAdmin()	{
		return appUserService.findAllUsersByParentId((Long)ThreadLocalUtility.get().get("principalId"));
	}
	
	@PostMapping("company/create")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponseDto saveCompany(@Valid @RequestBody CompanyDto companyDto)	{
		return companyService.saveCompany(companyDto);
	}
	
	@PostMapping("company/find")
	@ResponseStatus(code = HttpStatus.OK)
	public CompanyDto findCompany(@RequestBody Map<String, Long> payload)	{
		return companyService.findCompany(payload.get("companyId"));
	}
	
	@PostMapping("account/create")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponseDto saveAccount(@RequestBody AccountDto accountDto)	{
		crmUtility.validateNewAccountRequest(accountDto);
		return accountService.saveAccount(accountDto);
	}
	
	@PostMapping("account/find")
	@ResponseStatus(code = HttpStatus.OK)
	public AccountDto findAnAccount(@RequestBody Map<String, Long> payload)	{
		return accountService.findAccount(payload.get("accountId"));
	}
	
	@PostMapping("accounts/find")
	@ResponseStatus(code = HttpStatus.OK)
	public List<AccountDto> listAccountsOfUser()	{
		return accountService.findAllAccountsOfUser
				((Long)ThreadLocalUtility.get().get("principalId"));
	}
	
	@PostMapping("account/contact/create")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponseDto saveContact(@RequestBody ContactDto contactDto)	{
		crmUtility.validateNewContactRequest(contactDto);
		return contactService.addContact(contactDto);
	}
	
	@PostMapping("account/contact/find")
	@ResponseStatus(code = HttpStatus.OK)
	public ContactDto findContact(@RequestBody Map<String, Long> payload)	{
		return contactService.findContact(payload.get("contactId"));
	}
	
	@PostMapping("account/contacts")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ContactDto> findAccountContacts(@RequestBody Map<String, Long> payload)	{
		return contactService.findContactsByAccountId(payload.get("accountId"));
	}
	
	@PostMapping("contacts")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ContactDto> findUserContacts(Map<String, Long> payload)	{
		return contactService.findContactsByOwnerId(payload.get("userId"));
	}
	
	@PostMapping("sales/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponseDto addSales(@RequestBody @Valid SalesDto salesDto)	{
		return salesService.addSales(salesDto);
	}
	
	@PostMapping("sale/find")
	@ResponseStatus(code = HttpStatus.OK)
	public SalesDto findSale(@RequestBody Map<String, Long> payload)	{
		return salesService.findSalesById(payload.get("salesId"));
	}

	@PostMapping("account/sales/find")
	@ResponseStatus(code = HttpStatus.OK)
	public List<SalesDto> findAccountSales(@RequestBody Map<String, Long> payload)	{
		return salesService.findSalesByAccountId(payload.get("accountId"));
	}

	@PostMapping("contact/sales/find")
	@ResponseStatus(code = HttpStatus.OK)
	public List<SalesDto> findContactSales(@RequestBody Map<String, Long> payload)	{
		return salesService.findSalesByContactId(payload.get("contactId"));
	}
	
	@PostMapping("sales/find")
	@ResponseStatus(code = HttpStatus.OK)
	public List<SalesDto> findUserSales(@RequestBody Map<String, Long> payload)	{
		return salesService.findSalesByUserId(payload.get("userId"));
	}
	
	@PostMapping("task/create")
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponseDto createTask(@RequestBody @Valid TaskDto taskDto)	{
		return taskService.addTask(taskDto);
	}
	
	@PostMapping("task/find")
	@ResponseStatus(code = HttpStatus.OK)
	public TaskDto findATask(@RequestBody Map<String, Long> payload)	{
		return taskService.findTask(payload.get("taskId"));
	}

	@PostMapping("tasks/assignee/find")
	@ResponseStatus(code = HttpStatus.OK)
	public List<TaskDto> findTasksOfAssignee(@RequestBody Map<String, Long> payload)	{
		return taskService.findTasksOfAssignee(payload.get("id"));
	}
	
	@PostMapping("tasks/owner/find")
	@ResponseStatus(code = HttpStatus.OK)
	public List<TaskDto> findTasksOfOwner(@RequestBody Map<String, Long> payload)	{
		return taskService.findTasksByOwner(payload.get("id"));
	}
	
	@PostMapping("vendor/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponseDto addVendor(@RequestBody @Valid VendorDto vendorDto)	{
		crmUtility.validateNewVendorRequest(vendorDto);
		return vendorService.createVendor(vendorDto);
	}
	
	@PostMapping("vendor/find")
	@ResponseStatus(code = HttpStatus.OK)
	public VendorDto findVendor(@RequestBody Map<String, Long> payload)	{
		return vendorService.findVendor(payload.get("vendorId"));
	}
	
	@PostMapping("owner/vendors")
	@ResponseStatus(code = HttpStatus.OK)
	public List<VendorDto> listVendors(@RequestBody Map<String, Long> payload)	{
		return vendorService.findVendorsByOwner(payload.get("ownerId"));
	}
	
	@PostMapping("vendors")
	@ResponseStatus(code = HttpStatus.OK)
	public List<VendorDto> listVendorsOfUser()	{
		return vendorService.findVendorsByUser((Long)ThreadLocalUtility.get().get("principalId"));
	}
	
	@PostMapping("accPg/ddValues")
	@ResponseStatus(code = HttpStatus.OK)
	public Map<String, List<String>> populateAccPageDropdownValues()	{
		return appUserService.populateAccPageDropdownValues();
	}
	
	@PostMapping("vendorPg/ddValues")
	@ResponseStatus(code = HttpStatus.OK)
	public Map<String, List<String>> populateVendorPgDropdownvalues()	{
		return vendorService.populateVendorPgDdValues();
	}
	@PostMapping("contactPg/ddValues")
	@ResponseStatus(code = HttpStatus.OK)
	public Map<String, List<String>> populateContactPgDropdownvalues()	{
		return contactService.populateContactPgDdValues();
	}
}
