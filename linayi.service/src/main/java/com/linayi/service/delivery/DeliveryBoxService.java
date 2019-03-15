package com.linayi.service.delivery;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.linayi.entity.delivery.DeliveryBox;
import com.linayi.entity.procurement.ProcurementTask;

public interface DeliveryBoxService {
	DeliveryBox add(DeliveryBox deliveryBox);
	
	void boxing(List<ProcurementTask> procurementTaskList);

}
