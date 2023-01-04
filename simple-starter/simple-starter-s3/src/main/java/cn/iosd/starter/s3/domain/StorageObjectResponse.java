package cn.iosd.starter.s3.domain;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;

/**
 * 存储对象列表
 *
 * @author ok1996
 */
public class StorageObjectResponse {
    /**
     * 对象列表信息,注：truncated 为true 存在下一页
     */
    private ObjectListing objectListing;
    /**
     * 对象列表数据
     */
    private List<S3ObjectSummary> summaries;

    public ObjectListing getObjectListing() {
        return objectListing;
    }

    public void setObjectListing(ObjectListing objectListing) {
        this.objectListing = objectListing;
    }

    public List<S3ObjectSummary> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<S3ObjectSummary> summaries) {
        this.summaries = summaries;
    }
}
