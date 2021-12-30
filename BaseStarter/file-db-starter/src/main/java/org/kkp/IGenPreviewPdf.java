package org.kkp;

/**
 * 生成预览PDF
 */
public interface IGenPreviewPdf {
    /**
     * 转换
     *
     * @param path path
     * @retu byte[]
     */
    byte[] convert(String path);
}
