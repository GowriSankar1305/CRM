package com.eazybyts.crm.service;

import com.eazybyts.crm.dto.AddressDto;
import com.eazybyts.crm.entity.Address;

public interface AddressService {
	public Address dtoToEntity(AddressDto addressDto);
	public AddressDto entityToDto(Address address);
}
