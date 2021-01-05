package vn.metech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.dto.*;
import vn.metech.entity.UserService;
import vn.metech.repository.UserServiceJpaRepository;
import vn.metech.shared.ActionResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/service")
public class UserServiceController {

	@Autowired
	private UserServiceJpaRepository userServiceJpaRepository;

	@GetMapping("/get-all")
	public ActionResult getAllService() {

//		List<UserService> userServices = userServiceJpaRepository.findAll();
//		Map<String, UserServiceDto> response = new HashMap<>();
//		List<ServiceTypeDto> serviceTypes = new ArrayList<>();
//		for (ServiceType st : ServiceType.values()) {
//			serviceTypes.add(new ServiceTypeDto(st));
//			response.put(st.name(), new UserServiceDto(st.name()));
//		}
//
//		for (UserService us : userServices) {
//			if (StringUtils.isEmpty(us.getServiceType())) continue;
//
//			UserServiceDto users = response.get(us.getServiceType());
//			if (users == null) continue;
//
//			users.getUserIds().add(us.getUserId());
//		}
//		UserServiceResponse userServiceResponse = new UserServiceResponse();
//		userServiceResponse.setServiceTypes(serviceTypes);
//		userServiceResponse.setUserServices(new ArrayList<>(response.values()));

		return new ActionResult(Arrays.stream(ServiceType.values()).map(ServiceTypeDto::new).collect(Collectors.toList()));
	}

	@PostMapping("/update")
	public ActionResult updateUsersToServices(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestBody UserServiceUpdateDto userServiceDto) {
		userServiceJpaRepository
						.deleteInBatch(userServiceJpaRepository.findAllByUserId(userServiceDto.getUserId()));
		int count = 0;
		for (String serviceCode : userServiceDto.getServiceCodes()) {
			UserService userService = new UserService();
			userService.setCreatedBy(userId);
			userService.setUserId(userServiceDto.getUserId());
			userService.setServiceType(serviceCode);
			userServiceJpaRepository.save(userService);
			count++;
		}

		return new ActionResult(count);
	}

	@PostMapping("/find-by-user-id")
	public ActionResult findServicesByUser(
					@RequestHeader(Auth.USER_KEY) String reqUserId,
					@RequestBody ServiceByUserRequest req) {
		List<UserService> userServices = userServiceJpaRepository.findAllByUserId(req.getUserId());

		List<ServiceTypeDto> serviceTypes = userServices
						.stream()
						.map(us -> new ServiceTypeDto(ServiceType.valueOf(us.getServiceType())))
						.collect(Collectors.toList());

		UserServiceListByUser userServiceListByUser = new UserServiceListByUser();
		userServiceListByUser.setUserId(req.getUserId());
		userServiceListByUser.setServices(serviceTypes);

		return new ActionResult(userServiceListByUser);
	}
}
