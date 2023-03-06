package com.example.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EasyExcelUtil {


    //-------------------------------------------------------------- 读取文件解析监听类 start ----------------------------------------------------

    /**
     * <p>ClassName：ExcelListener</p >
     * <p>Description：读取文件解析监听类，此类供外部实例化使用需要设置为静态类</p >
     * <p>Date：2021/9/2</p >
     */
    public static class ExcelListener<T> extends AnalysisEventListener<T> {

        /**
         * <p>存放读取后的数据</p >
         *
         * @date 2021/9/2 0:10
         */
        public List<T> datas = new ArrayList<>();

        /**
         * <p>读取数据，一条一条读取</p >
         *
         * @date 2021/9/2 0:15
         */
        @Override
        public void invoke(T t, AnalysisContext analysisContext) {
            datas.add(t);
        }

        /**
         * <p>解析完毕之后执行</p >
         *
         * @date 2021/9/2 0:06
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            log.info("读取数据条数：{}条！", datas.size());
        }

        public List<T> getDatas() {
            return this.datas;
        }

    }
    //-------------------------------------------------------------- 读取文件解析监听类 end ----------------------------------------------------


    //-------------------------------------------------------------- 导出excel表格，设置自适应列宽配置类 start ----------------------------------------------------

    /**
     * <p>ClassName：Custemhandler</p >
     * <p>Description：设置自适应列宽配置类</p >
     * <p>Date：2021/10/14</p >
     */
    public static class Custemhandler extends AbstractColumnWidthStyleStrategy {

        private static final int MAX_COLUMN_WIDTH = 255;
        //因为在自动列宽的过程中，有些设置地方让列宽显得紧凑，所以做出了个判断
        private static final int COLUMN_WIDTH = 20;
        private Map<Integer, Map<Integer, Integer>> CACHE = new HashMap(8);


        @Override
        protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
            boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
            if (needSetWidth) {
                Map<Integer, Integer> maxColumnWidthMap = CACHE.get(writeSheetHolder.getSheetNo());
                if (maxColumnWidthMap == null) {
                    maxColumnWidthMap = new HashMap(16);
                    CACHE.put(writeSheetHolder.getSheetNo(), maxColumnWidthMap);
                }

                Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
                if (columnWidth >= 0) {
                    if (columnWidth > MAX_COLUMN_WIDTH) {
                        columnWidth = MAX_COLUMN_WIDTH;
                    } else {
                        if (columnWidth < COLUMN_WIDTH) {
                            columnWidth = columnWidth * 2;
                        }
                    }

                    Integer maxColumnWidth = (Integer) ((Map) maxColumnWidthMap).get(cell.getColumnIndex());
                    if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                        ((Map) maxColumnWidthMap).put(cell.getColumnIndex(), columnWidth);
                        writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
                    }
                }
            }
        }


        private Integer dataLength(List<WriteCellData<?>> cellDataList, Cell cell, Boolean isHead) {
            if (isHead) {
                return cell.getStringCellValue().getBytes().length;
            } else {
                CellData cellData = cellDataList.get(0);
                CellDataTypeEnum type = cellData.getType();
                if (type == null) {
                    return -1;
                } else {
                    switch (type) {
                        case STRING:
                            return cellData.getStringValue().getBytes().length;
                        case BOOLEAN:
                            return cellData.getBooleanValue().toString().getBytes().length;
                        case NUMBER:
                            return cellData.getNumberValue().toString().getBytes().length;
                        default:
                            return -1;
                    }
                }
            }
        }
    }
    //-------------------------------------------------------------- 导出excel表格，设置自适应列宽配置类 end -----------------------------------------------------


    /**
     * <p> 读取Excel文件返回数据集合，不包含表头，默认读取第一个sheet数据 </p >
     *
     * @param inputStream   输入流
     * @param tClass        数据映射类
     * @param excelListener 读取监听类
     * @return List 结果集
     * @date 2021/9/2 0:17
     */
    public static <T> List<T> readExcel(InputStream inputStream, Class<T> tClass, ExcelListener<T> excelListener) {
        if (inputStream == null || tClass == null || excelListener == null) {
            return null;
        }
        ExcelReaderBuilder read = EasyExcel.read(inputStream, tClass, excelListener);
        read.sheet().doRead();
        return excelListener.getDatas();
    }

    /**
     * <p> 读取Excel文件返回数据集合，不包含表头，读取第x个sheet数据，不设置sheet就读取全部 </p >
     *
     * @param inputStream   输入流
     * @param tClass        数据映射类
     * @param excelListener 读取监听类
     * @return List 结果集
     * @date 2021/9/2 0:17
     */
    public static <T> List<T> readExcel(InputStream inputStream, Integer sheetNo, Class<T> tClass, ExcelListener<T> excelListener) {
        if (inputStream == null || tClass == null || excelListener == null) {
            return null;
        }
        ExcelReaderBuilder read = EasyExcel.read(inputStream, tClass, excelListener);
        if (sheetNo != null) {
            read.sheet(sheetNo).doRead();
        } else {
            ExcelReader excelReader = read.build();
            excelReader.readAll();
            excelReader.finish();
        }
        return excelListener.getDatas();
    }
}
