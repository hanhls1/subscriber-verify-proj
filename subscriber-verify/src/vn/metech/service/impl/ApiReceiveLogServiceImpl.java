package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import vn.metech.repository.jpa.ApiReceiveLogCrudRepository;
import vn.metech.service.IApiReceiveLogService;

@Service
public class ApiReceiveLogServiceImpl implements IApiReceiveLogService {

	private ApiReceiveLogCrudRepository apiReceiveLogCrudRepository;

	public ApiReceiveLogServiceImpl(ApiReceiveLogCrudRepository apiReceiveLogCrudRepository) {
		this.apiReceiveLogCrudRepository = apiReceiveLogCrudRepository;
	}

}
