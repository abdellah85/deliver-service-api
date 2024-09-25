package com.carrefour.driveanddeliver.service;

import com.carrefour.driveanddeliver.service.dto.DeliveryDTO;
import com.carrefour.driveanddeliver.service.dto.EnumDTO;

import java.util.List;

public interface DeliveryService {

    DeliveryDTO createDelivery(DeliveryDTO deliveryDTO);

    DeliveryDTO getDeliveryById(Long id);

    List<EnumDTO> getDeliveryMethods();

}