package com.sgwannabig.smallgift.springboot.service.shop;

import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.domain.Shop;
import com.sgwannabig.smallgift.springboot.domain.ShopStatus;
import lombok.Getter;

@Getter
public class RegistShopCommand {
  private Shop shop;

  public RegistShopCommand(Manager manager) {
    this.shop = Shop.builder()
        .shopName(manager.getBusinessName())
        .shopAddress(manager.getAddress())
        .shopTelephone(manager.getBusinessTel())
        .status(ShopStatus.APPROVAL)
        .manager(manager)
        .build();
  }
}
