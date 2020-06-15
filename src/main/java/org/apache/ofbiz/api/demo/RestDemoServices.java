package org.apache.ofbiz.api.demo;

import java.util.List;
import java.util.Map;

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityExpr;
import org.apache.ofbiz.entity.condition.EntityFunction;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;

public class RestDemoServices {

	/**
	 * 
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> searchProductsByGoodIdentificationValue(DispatchContext dctx,
			Map<String, Object> context) {

		Map<String, Object> serviceResult = ServiceUtil.returnSuccess();
		Delegator delegator = dctx.getDelegator();
		String idFragment = (String) context.get("idFragment");
		List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition(
				EntityCondition.makeCondition("goodIdentificationTypeId", EntityOperator.IN,
						UtilMisc.toList("INVOICE_EXPORT")),
				EntityOperator.AND, EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("idValue"),
						EntityOperator.LIKE, EntityFunction.UPPER(((String) "%" + idFragment + "%").toUpperCase()))));
		try {
			List<GenericValue> productGIViewList = EntityQuery.use(delegator).from("ProductAndGoodIdentification")
					.where(exprs).queryList();
			serviceResult.put("products", productGIViewList);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ServiceUtil.returnError(e.getMessage());
		}
		return serviceResult;
	}
	
	
	
	public static Map<String, Object> testComplexInParams(DispatchContext dctx,
			Map<String, Object> context) {

		Map<String, Object> serviceResult = ServiceUtil.returnSuccess();
		System.out.println("context "+context);
		return serviceResult;
	}

}
