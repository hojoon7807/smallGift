package com.sgwannabig.smallgift.springboot.controller.manager;

import com.sgwannabig.smallgift.springboot.config.jwt.JwtProperties;
import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.domain.Shop;
import com.sgwannabig.smallgift.springboot.dto.manager.request.RegistManagerDto;
import com.sgwannabig.smallgift.springboot.dto.manager.response.RegistManagerResponseDto;
import com.sgwannabig.smallgift.springboot.service.jwt.JwtTokenProvider;
import com.sgwannabig.smallgift.springboot.service.manager.RegistManagerCommand;
import com.sgwannabig.smallgift.springboot.service.manager.RegistManagerUsecase;
import com.sgwannabig.smallgift.springboot.service.shop.RegistShopCommand;
import com.sgwannabig.smallgift.springboot.service.shop.RegistShopUsecase;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/manager")
@RequiredArgsConstructor
public class RegistManagerController {

  private final RegistManagerUsecase registManagerUsecase;
  private final RegistShopUsecase registShopUsecase;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "매니저 등록", notes = "매니저 등록을 위한 정보를 보내준")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "registManager", value = "매니저 등록 정보", required = true, paramType = "body", dataType = "RegistManagerDto"),
      @ApiImplicitParam(name = "businessRegistration", value = "매니저 등록을 사업자등록증 이미지 파일", required = true, dataType = "__file", paramType = "form"),
      @ApiImplicitParam(name = "mailOrderSalesRegistration", value = "매니저 등록을 통신판매 신고증 이미지 파일", required = true, dataType = "__file", paramType = "form")
  })
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 500, message = "서버에러"),
      @ApiResponse(code = 409, message = "이미 존재하는 사업자입니다"),
      @ApiResponse(code = 400, message = "잘못된 요청입니다")
  })
  public ResponseEntity<RegistManagerResponseDto> registManager(
      @RequestHeader(JwtProperties.HEADER_STRING)
      String token,
      @RequestPart("registManager")
      RegistManagerDto registManagerDto,
      @RequestPart
      MultipartFile businessRegistration,
      @RequestPart
      MultipartFile mailOrderSalesRegistration) {

    //Security Context 사용 방법으로 변경 고려
    String email = jwtTokenProvider.getMemberEmail(
        token.replace(JwtProperties.TOKEN_PREFIX, ""));
    Manager registManager = registManagerDto.toEntity();
    registManager.setEmail(email);
    Manager manager = registManagerUsecase.apply(
        new RegistManagerCommand(registManager, businessRegistration,
            mailOrderSalesRegistration));
    Shop shop = registShopUsecase.apply(new RegistShopCommand(manager));
    return ResponseEntity.ok(new RegistManagerResponseDto(manager.getId(), shop.getId()));
  }
}
