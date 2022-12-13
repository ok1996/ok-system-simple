package cn.iosd.starter.s3.domain.resp;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 存储对象列表
 *
 * @author ok1996
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageObjectResp {
    /**
     * 对象列表信息,注：truncated 为true 存在下一页
     */
    private ObjectListing objectListing;
    /**
     * 对象列表数据
     */
    private List<S3ObjectSummary> summaries;
}
