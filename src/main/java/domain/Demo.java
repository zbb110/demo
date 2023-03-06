package domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pub_service-addr")
public class Demo {
    @ExcelProperty("源ip")
    private String srcIp;
    @ExcelProperty("名称")
    private String name;
    @ExcelProperty("备注")
    private String remark;
}
