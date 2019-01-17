package vk.framework.spring.aws;

import com.amazonaws.services.cloudfront.AmazonCloudFront;
import com.amazonaws.services.cloudfront.model.CreateInvalidationRequest;
import com.amazonaws.services.cloudfront.model.CreateInvalidationResult;
import com.amazonaws.services.cloudfront.model.GetInvalidationRequest;
import com.amazonaws.services.cloudfront.model.GetInvalidationResult;
import com.amazonaws.services.cloudfront.model.Invalidation;
import com.amazonaws.services.cloudfront.model.InvalidationBatch;
import com.amazonaws.services.cloudfront.model.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CfInvalidateService {
	@Autowired(required = false)
	private AmazonCloudFront amazonCloudFront;

	public Invalidation setInvalidation(String distributionId, String path) throws Exception {
		Paths paths = (new Paths()).withItems(new String[]{path}).withQuantity(Integer.valueOf(1));
		InvalidationBatch invalidationBatch = (new InvalidationBatch()).withPaths(paths)
				.withCallerReference("IN" + System.nanoTime());
		CreateInvalidationRequest request = new CreateInvalidationRequest();
		request.setDistributionId(distributionId);
		request.setInvalidationBatch(invalidationBatch);
		CreateInvalidationResult result = null;

		try {
			result = this.amazonCloudFront.createInvalidation(request);
		} catch (Exception arg7) {
			return null;
		}

		return result.getInvalidation();
	}

	public Invalidation getInvalidation(String distributionId) throws Exception {
		GetInvalidationRequest request = new GetInvalidationRequest();
		request.setDistributionId(distributionId);
		GetInvalidationResult result = this.amazonCloudFront.getInvalidation(request);
		return result.getInvalidation();
	}
}