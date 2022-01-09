const CompressionPlugin = require('compression-webpack-plugin')

module.exports = (env) => {
    const { VITE_BUILD_COMPRESS } = env
    const compressList = VITE_BUILD_COMPRESS.split(',')
    var config = {}
    if (compressList.includes('gzip')) {
        // http://doc.ruoyi.vip/ruoyi-vue/other/faq.html#使用gzip解压缩静态文件
        config.algorithm = 'gzip'
    }
    if (compressList.includes('brotli')) {
        config.algorithm = 'gzip'
    }
    return new CompressionPlugin(config)
}