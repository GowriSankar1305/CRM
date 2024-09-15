package com.eazybyts.crm.service.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.eazybyts.crm.dto.AddressDto;
import com.eazybyts.crm.entity.Address;
import com.eazybyts.crm.service.AddressService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

	@Override
	public Address dtoToEntity(AddressDto addressDto) {
		Address addrs = new Address();
		if(Objects.isNull(addressDto))	{
			log.info("----- received address dto is null ----");
			return addrs;
		}
		addrs.setCity(addressDto.getCity());
		addrs.setCountry(addressDto.getCountry());
		addrs.setState(addressDto.getState());
		addrs.setStreet(addressDto.getStreet());
		addrs.setZipCode(addressDto.getZipCode());
		return addrs;
	}

	@Override
	public AddressDto entityToDto(Address address) {
		AddressDto addrsDto = new AddressDto();
		if(Objects.isNull(address))	{
			log.info("----- received address entity is null ----");
			return addrsDto;
		}
		addrsDto.setAddressId(address.getAddressId());
		addrsDto.setCity(address.getCity());
		addrsDto.setCountry(address.getCity());
		addrsDto.setState(address.getState());
		addrsDto.setStreet(address.getStreet());
		addrsDto.setZipCode(address.getZipCode());
		return addrsDto;
	}

}
